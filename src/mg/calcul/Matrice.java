package mg.calcul;
public class Matrice {
    private int ligne;
    private int colonne;
    private double[][] matrix;

    public double[][] getMatrix() {
        return matrix;
    }

    public int getColonne() {
        return colonne;
    }

    public int getLigne() {
        return ligne;
    }

    private void setColonne(int colonne) throws Exception {
        if (colonne - 1 != ligne)
            throw new Exception("Reverify the length of column !", new Throwable("Must be equal to ligne + 1"));
        this.colonne = colonne;
    }

    private void setLigne(int ligne) {
        this.ligne = ligne;
    }

    private void setMatrix(double[][] matrix) {
        this.matrix = matrix;
    }

    public Matrice(double[][] matrix) throws Exception {
        setMatrix(matrix);
        setLigne(matrix.length);
        setColonne(matrix[0].length);
    }

    public double[] resolve() {
        triangularisation();
        double[][] matrice = extractMatrice();
        double[] vecteur = extractVecteur();
        double[] valeurRetour = new double[this.ligne];
        for (int i = 0; i < this.ligne; i++) {
            valeurRetour[i] = vecteur[i] / matrice[i][i];
        }
        return valeurRetour;
    }

    private void triangularisation() {
        triangularisationInf();
        triangularisationSup();
        triangularisationInf();
        triangularisationSup();
    }

    private void triangularisationInf() {
        double[][] matr = extractMatrice();
        double[] vecteur = extractVecteur();
        int rowTemp = 0;
        for(int k = 0; k < this.ligne - 1; k++) {
            if (matr[k][k] == 0) {
                rowTemp = findRowPivot(matr, k);
                swapRow(matr, k, rowTemp);
            }
            for(int i = k + 1; i < this.ligne; i++) {
                for(int j = k + 1; j < this.ligne; j++) {
                    matr[i][j] -= (matr[i][k] / matr[k][k]) * matr[k][j];
                }
                vecteur[i] -= (matr[i][k] / matr[k][k]) * vecteur[k];
                matr[i][k] = 0;
            }
        }
        fusionMatrice(matr, vecteur);
    }

    private void triangularisationSup() {
        double[][] matr = extractMatrice();
        double[] vecteur = extractVecteur();
        vecteur = inverseVecteur(vecteur);
        matr = inverseMatrice(matr);
        fusionMatrice(matr, vecteur);
    }

    private double[][] inverseMatrice(double[][] matrix) {
        int ligne = 0;
        int colonne;
        double[][] matrice = newMatrice();
        for (int i = matrix.length - 1; i >= 0 ; i--) {
            colonne = 0;
            for (int j = matrix[0].length - 1; j >=0 ; j--) {
                matrice[ligne][colonne] = matrix[i][j];
                colonne++;
            }
            ligne++;
        }
        return matrice;
    }

    private double[] inverseVecteur(double[] vecteur) {
        int index = 0;
        double[] vect = new double[vecteur.length];
        for (int i = vecteur.length - 1; i >= 0; i--) {
            vect[index] = vecteur[i];
            index++;
        }
        return vect;
    }

    private void swapRow(double[][] matrix, int oldRow, int newRow) {
        double temp = 0;
        for (int i = 0; i < matrix[0].length; i++) {
            temp = matrix[oldRow][i];
            matrix[oldRow][i] = matrix[newRow][i];
            matrix[newRow][i] = temp;
        }
    }

    private int findRowPivot(double[][] matrice, int colonne) {
        int temp = colonne;
        for (int i = colonne; i < this.ligne; i++) {
            if (matrice[i][colonne] != 0)
                temp = i;
        }
        return temp;
    }

    private void fusionMatrice(double[][] matrice, double[] vecteur) {
        int lastIndex = this.colonne;
        for (int i = 0; i < this.ligne; i++) {
            for (int j = 0; j < this.ligne; j++) {
                this.matrix[i][j] = matrice[i][j];
            }
            this.matrix[i][lastIndex - 1] = vecteur[i];
        }
    }

    private double[] extractVecteur() {
        double[] vecteur2membre = new double[this.ligne];
        int i;
        int lastIndex = this.ligne;
        for (i = 0;  i < this.ligne; i++) {
            vecteur2membre[i] = this.matrix[i][lastIndex];
        }
        return vecteur2membre;
    }

    private double[][] extractMatrice() {
        double[][] mat = newMatrice();
        for (int i = 0; i < this.ligne; i++) {
            for (int j = 0; j < this.ligne; j++) {
                mat[i][j] = this.matrix[i][j];
            }
        }
        return mat;
    }

    private double[][] newMatrice() {
        double[][] mat = new double[this.ligne][];
        for (int i = 0; i < this.ligne; i++) {
            mat[i] = new double[this.ligne];
        }
        return mat;
    }
}
