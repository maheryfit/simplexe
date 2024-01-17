package mg.calcul;

import org.apache.commons.math3.fraction.Fraction;

import java.util.*;

public class Simplex {
    private Row[] constraints;
    private Row functionGoal;
    private Column value;

    public Row[] getConstraints() {
        return constraints;
    }

    public Column getValue() {
        return value;
    }

    public Row getConstraint(int index) {
        return constraints[index];
    }

    public Row getFunctionGoal() {
        return functionGoal;
    }

    public void setConstraints(Row[] constraints) {
        this.constraints = constraints;
    }

    private void setFunctionGoal(Row functionGoal) {
        this.functionGoal = functionGoal;
    }

    public Simplex addConstraint(Row constraint) {
        List<Row> rowList = Arrays.asList(this.constraints);
        rowList.add(constraint);
        this.constraints = new Row[rowList.size()];
        int i = 0;
        for (Row row : rowList) {
            this.constraints[i] = row;
            i++;
        }
        return this;
    }

    /**
     *
     */
    public Simplex(LinkedList<Row> equations) {
        extractFunctionFoal(equations);
        extractConstraints(equations);
        extractValue(equations);
    }

    public Simplex() {
    }

    /**
     *
     */
    private void extractFunctionFoal(LinkedList<Row> equations) {
        int lastIndex = equations.size() - 1;
        setFunctionGoal(equations.get(lastIndex));
    }

    /**
     *
     */
    private void extractConstraints(LinkedList<Row> equations) {
        constraints = new Row[equations.size() - 1];
        for (int i = 0; i < constraints.length; i++) {
            constraints[i] = equations.get(i);
        }
    }

    /**
     *
     */
    private void extractValue(LinkedList<Row> equations) {
        int lastRowIndex = equations.size() - 1;
        this.value = new Column(equations.get(lastRowIndex).getValue(), equations.get(lastRowIndex).getDescription());
    }

    /**
     *
     */
    private HashMap<Integer, Fraction> getCalculRowForPivot(int columnIndex) throws Exception {
        HashMap<Integer, Fraction> hashMap = new HashMap<>();
        int i = 0;
        Fraction fr;
        for (Row constraint : this.constraints) {
            if (constraint.getEquation(columnIndex).getFraction().doubleValue() > 0) {
                fr = constraint.getValue().divide(constraint.getEquation(columnIndex).getFraction());
                hashMap.put(i, fr);
            }
            i++;
        }

        if (hashMap.isEmpty())
            throw new Exception("Cette fonction ne possède aucune solution");
        return hashMap;
    }

    private int getIndexRowPivot(int column) throws Exception {
        int indexReturn = -1;
        HashMap<Integer, Fraction> hashMap = getCalculRowForPivot(column);
        boolean firstTime = true;
        for (Integer key : hashMap.keySet()) {
            if (firstTime) {
                firstTime = false;
                indexReturn = key;
            } else {
                if (hashMap.get(key).doubleValue() < hashMap.get(indexReturn).doubleValue()) {
                    indexReturn = key;
                }
            }
        }
        return indexReturn;
    }

    private void resolveSimplexForOne(int resolution) throws Exception {
        int indexColumnPivot;
        if (resolution == 1) {
            indexColumnPivot = functionGoal.getIndexColumnMin();
        } else {
            indexColumnPivot = functionGoal.getIndexColumnMax();
        }
        int indexRowPivot = getIndexRowPivot(indexColumnPivot);
        Row rowPivot = this.constraints[indexRowPivot];
        rowPivot.calculRowPivot(indexColumnPivot);
        String description = this.functionGoal.getEquations()[indexColumnPivot].getDescription();
        for (int i = 0; i < this.constraints.length; i++) {
            if (i == indexRowPivot) {
                this.constraints[i] = rowPivot;
                this.constraints[i].setDescription(description);
            } else {
                this.constraints[i] = this.constraints[i].calculRow(rowPivot, indexColumnPivot);
            }
        }
        this.functionGoal = this.functionGoal.calculRow(rowPivot, indexColumnPivot);
        this.value.setFraction(this.functionGoal.getValue());
    }

    public void resolveSimplex(int resolution) throws Exception {
        // Résolution = 0 (maximisation) sinon Résolution = 1 (minimisation)
        if (resolution == 0) {
            while (!this.functionGoal.isAllNegatif()) {
                Arrays.stream(this.getConstraints()).forEach(row -> {
                    Arrays.stream(row.getEquations()).forEach(column -> {
                        System.out.print(column.getFraction() + " || ");
                    });
                    System.out.println(row.getValue() + " || " + row.getDescription());
                });
                Arrays.stream(this.getFunctionGoal().getEquations()).forEach(column -> {
                    System.out.print(column.getFraction() + " || ");
                });
                System.out.println(this.value.getFraction() + " || " + this.functionGoal.getDescription());
                System.out.println("================================================================");
                this.resolveSimplexForOne(resolution);
            }
        } else {
            this.resolveSimplexForOne(resolution);
            while (!functionGoal.isAllNegatif()) {
                Arrays.stream(this.getConstraints()).forEach(row -> {
                    Arrays.stream(row.getEquations()).forEach(column -> {
                        System.out.print(column.getFraction() + " || ");
                    });
                    System.out.println(row.getValue() + " || " + row.getDescription());
                });
                Arrays.stream(this.getFunctionGoal().getEquations()).forEach(column -> {
                    System.out.print(column.getFraction() + " || ");
                });
                System.out.println(this.value.getFraction() + " || " + this.functionGoal.getDescription());
                System.out.println("================================================================");
                this.resolveSimplexForOne(resolution);
            }
        }
    }

    private List<Integer> getColumnAVToRemove(Column[] columns) {
        List<Integer> integerList = new LinkedList<>();
        for (int i = 0; i < columns.length; i++) {
            if (columns[i].getDescription().toLowerCase().startsWith("va")) {
                integerList.add(i);
            }
        }
        return integerList;
    }

    private List<Integer> getAllIndexRowWithVariableArtificial() {
        List<Integer> integerList = new ArrayList<>();
        for (int i = 0; i < this.constraints.length; i++) {
            if (this.constraints[i].getDescription().toLowerCase().startsWith("va")) {
                integerList.add(i);
            }
        }
        return integerList;
    }

    private List<Row> getAllRowWithVariableArtificial() {
        List<Row> rowsArtificial = new ArrayList<>();
        List<Integer> integerList = getAllIndexRowWithVariableArtificial();
        for (Integer i : integerList) {
            rowsArtificial.add(new Row(this.constraints[i].getEquations(), this.constraints[i].getValue(),
                    this.constraints[i].getDescription()));
        }
        return rowsArtificial;
    }

    private Row calculNewGoalFunction() throws Exception {
        List<Row> tabs = this.getAllRowWithVariableArtificial();
        if (tabs.isEmpty())
            throw new Exception("Wrong method call", new Throwable("There is no artificial variable"));
        Row rowReturn = tabs.get(0);
        int columnSize = rowReturn.getEquations().length;
        Fraction test;
        if (tabs.size() == 1) {
            int j = 0;
            for (Column column : rowReturn.getEquations()) {
                if (column.getDescription().toLowerCase().startsWith("va")) {
                    rowReturn.getEquations()[j].setFraction(new Fraction(0));
                } else {
                    test = column.getFraction().multiply(-1);
                    rowReturn.getEquations()[j].setFraction(new Fraction(test.getNumerator(), test.getDenominator()));
                }
                j++;
            }
            Fraction fr = rowReturn.getValue();
            rowReturn.setValue(new Fraction(fr.getNumerator(), fr.getDenominator()));
            return rowReturn;
        }
        for (int i = 1; i < tabs.size(); i++) {
            for (int j = 0; j < columnSize; j++) {
                if (rowReturn.getEquations()[j].getDescription().toLowerCase().startsWith("va")) {
                    rowReturn.getEquations()[j].setFraction(new Fraction(0));
                } else {
                    test = tabs.get(i).getEquations()[j].getFraction().add(rowReturn.getEquations()[j].getFraction());
                    rowReturn.getEquations()[j].setFraction(new Fraction(test.getNumerator(), test.getDenominator()));
                }
            }
            test = rowReturn.getValue().add(tabs.get(i).getValue());
            rowReturn.setValue(new Fraction(test.getNumerator(), test.getDenominator()));
        }
        // Inversion de la fonction objectif
        for (int i = 0; i < rowReturn.getEquations().length; i++) {
            rowReturn.getEquations()[i].setFraction(rowReturn.getEquations()[i].getFraction().multiply(-1));
        }
        return rowReturn;
    }

    private void setNewGoalFunction() throws Exception {
        this.functionGoal.setDescription("égalité");
        Row row = calculNewGoalFunction();
        int i = 0;
        for (Column column : row.getEquations()) {
            this.functionGoal.getEquations()[i].setFraction(column.getFraction());
            i++;
        }
        Fraction fraction = row.getValue().multiply(-1);
        this.value.setFraction(new Fraction(fraction.getNumerator(), fraction.getDenominator()));
        this.functionGoal.setValue(this.value.getFraction());
    }

    private boolean isValueGoalFunctionEqualToZero() {
        int resp = this.value.getFraction().intValue();
        return resp == 0;
    }

    private boolean isAllVAOut() {
        for (Row tab : this.constraints) {
            if (tab.getDescription().toLowerCase().startsWith("va")) {
                return false;
            }
        }
        return true;
    }

    private Simplex getFirstPhase() throws Exception {
        this.setNewGoalFunction();
        Arrays.stream(this.getConstraints()).forEach(row -> {
            Arrays.stream(row.getEquations()).forEach(column -> {
                System.out.print(column.getFraction() + " || ");
            });
            System.out.println(row.getValue() + " || " + row.getDescription());
        });
        Arrays.stream(this.getFunctionGoal().getEquations()).forEach(column -> {
            System.out.print(column.getFraction() + " || ");
        });
        System.out.println(this.value.getFraction() + " || " + this.functionGoal.getDescription());
        System.out.println("================================================================");
        // Il faut que les variables artificielles soient tous sorties en même tant la
        // fonction objection soit égale à 0
        while (!isAllVAOut() && !isValueGoalFunctionEqualToZero()) {
            this.resolveSimplexForOne(1);
        }
        if ((!isAllVAOut() && isValueGoalFunctionEqualToZero())
                || (isAllVAOut() && !isValueGoalFunctionEqualToZero())) {
            throw new Exception("Vérifier il y a une erreur, la fonction valeur est " + isValueGoalFunctionEqualToZero()
                    + ", et les variables artificielles sont " + isAllVAOut());
        }
        return this;
    }

    private void removeColumnAV(Row row) {
        List<Integer> integerList = getColumnAVToRemove(row.getEquations());
        int size = row.getEquations().length;
        Column[] columns = row.getEquations();
        Column[] temp = new Column[size - integerList.size()];
        boolean enter;
        int k = 0;
        for (int i = 0; i < columns.length; i++) {
            enter = true;
            for (Integer integer : integerList) {
                if (integer == i) {
                    enter = false;
                    break;
                }
            }
            if (enter) {
                temp[k] = row.getEquation(i);
                k++;
            }
        }
        row.setEquation(temp);
    }

    private Row getFunctionGoalNull() {
        List<Column> columnList = new LinkedList<>();
        for (int i = 0; i < this.functionGoal.getEquations().length; i++) {
            columnList.add(new Column(0, this.functionGoal.getEquations()[i].getDescription()));
        }
        columnList.add(new Column(0, this.value.getDescription()));
        return new Row(columnList, this.functionGoal.getDescription());
    }

    private void replaceFunctionGoalByOriginal(Simplex simplex) {
        simplex.functionGoal = this.functionGoal;
        for (int i = 0; i < simplex.constraints.length; i++) {
            removeColumnAV(simplex.constraints[i]);
        }
        removeColumnAV(simplex.functionGoal);
        simplex.value = this.value;
    }

    private Simplex getSimplexInitial() {
        Simplex simplex = new Simplex();
        simplex.constraints = this.constraints;
        simplex.functionGoal = getFunctionGoalNull();
        simplex.value = new Column(new Fraction(simplex.functionGoal.getValue().getNumerator(),
                simplex.functionGoal.getValue().getDenominator()), this.getValue().getDescription());
        return simplex;
    }

    public Simplex resolveMaximisationUsing2phases() throws Exception {
        Simplex firstPhase = getSimplexInitial().getFirstPhase();
        replaceFunctionGoalByOriginal(firstPhase);
        firstPhase.resolveSimplex(0);
        return firstPhase;
    }

    public Simplex resolveMinimisationUsing2phases() throws Exception {
        Simplex firstPhase = getSimplexInitial().getFirstPhase();
        replaceFunctionGoalByOriginal(firstPhase);
        firstPhase.resolveSimplex(1);
        return firstPhase;
    }

}
