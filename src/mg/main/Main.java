package mg.main;

import mg.calcul.Column;
import mg.calcul.Row;
import mg.calcul.Simplex;

import java.util.Arrays;
import java.util.LinkedList;

public class Main {

    public static void main(String[] args) throws Exception {
        int N = 3;
        int i;
        double[][] A = new double[N][];
        for (i = 0; i < A.length; i++) {
            A[i] = new double[N + 1];
        }

        A[0][0] = 1;
        A[0][1] = 2;
        A[0][2] = 3;
        A[0][3] = 4;
        A[1][0] = 2;
        A[1][1] = 4;
        A[1][2] = 5;
        A[1][3] = 9;
        A[2][0] = 7;
        A[2][1] = 8;
        A[2][2] = 9;
        A[2][3] = 29;

        /*
         * Matrice matrice = new Matrice(A);
         * double[] result = matrice.resolve();
         * for (double res : result) {
         * System.out.println(res);
         * }
         */

        LinkedList<Column> columnLinkedList = new LinkedList<>();
        columnLinkedList.add(new Column(2, "t1"));
        columnLinkedList.add(new Column(1, "t2"));
        columnLinkedList.add(new Column(0, "t3"));
        columnLinkedList.add(new Column(-1, "x1"));
        columnLinkedList.add(new Column(0, "x2"));
        columnLinkedList.add(new Column(1, "VA-A1"));
        columnLinkedList.add(new Column(0, "VA-A2"));
        columnLinkedList.add(new Column(40, "égalité"));

        LinkedList<Column> columnLinkedListA = new LinkedList<>();
        columnLinkedListA.add(new Column(2, "x1"));
        columnLinkedListA.add(new Column(1, "x2"));
        columnLinkedListA.add(new Column(1, "t1"));
        columnLinkedListA.add(new Column(0, "t2"));
        columnLinkedListA.add(new Column(0, "t3"));
        columnLinkedListA.add(new Column(800, "égalité"));

        /*
         * columnLinkedList.add(new Column(2, "x1"));
         * columnLinkedList.add(new Column(3, "x2"));
         * columnLinkedList.add(new Column(1, "x3"));
         * columnLinkedList.add(new Column(1, "x4"));
         * columnLinkedList.add(new Column(0, "x5"));
         * columnLinkedList.add(new Column(0, "x6"));
         * columnLinkedList.add(new Column(5, "equality"));
         */

        LinkedList<Column> columnLinkedList1 = new LinkedList<>();
        columnLinkedList1.add(new Column(1, "t1"));
        columnLinkedList1.add(new Column(2, "t2"));
        columnLinkedList1.add(new Column(1, "t3"));
        columnLinkedList1.add(new Column(0, "x1"));
        columnLinkedList1.add(new Column(-1, "x2"));
        columnLinkedList1.add(new Column(0, "VA-A1"));
        columnLinkedList1.add(new Column(1, "VA-A2"));
        columnLinkedList1.add(new Column(50, "égalité"));

        LinkedList<Column> columnLinkedList1B = new LinkedList<>();
        columnLinkedList1B.add(new Column(1, "x1"));
        columnLinkedList1B.add(new Column(2, "x2"));
        columnLinkedList1B.add(new Column(0, "t1"));
        columnLinkedList1B.add(new Column(1, "t2"));
        columnLinkedList1B.add(new Column(0, "t3"));
        columnLinkedList1B.add(new Column(700, "égalité"));

        /*
         * columnLinkedList1.add(new Column(4, "x1"));
         * columnLinkedList1.add(new Column(1, "x2"));
         * columnLinkedList1.add(new Column(2, "x3"));
         * columnLinkedList1.add(new Column(0, "x4"));
         * columnLinkedList1.add(new Column(1, "x5"));
         * columnLinkedList1.add(new Column(0, "x6"));
         * columnLinkedList1.add(new Column(11, "equality"));
         */

        LinkedList<Column> columnLinkedList2 = new LinkedList<>();
        columnLinkedList2.add(new Column(2, "x1"));
        columnLinkedList2.add(new Column(5, "x2"));
        columnLinkedList2.add(new Column(0, "x3"));
        columnLinkedList2.add(new Column(0, "x4"));
        columnLinkedList2.add(new Column(-1, "x5"));
        columnLinkedList2.add(new Column(0, "VA-A1"));
        columnLinkedList2.add(new Column(0, "VA-A2"));
        columnLinkedList2.add(new Column(1, "VA-A3"));
        columnLinkedList2.add(new Column(420, "égalité"));

        LinkedList<Column> columnLinkedList2C = new LinkedList<>();
        columnLinkedList2C.add(new Column(0, "x1"));
        columnLinkedList2C.add(new Column(1, "x2"));
        columnLinkedList2C.add(new Column(0, "t1"));
        columnLinkedList2C.add(new Column(0, "t2"));
        columnLinkedList2C.add(new Column(1, "t3"));
        columnLinkedList2C.add(new Column(300, "égalité"));

        /*
         * columnLinkedList2.add(new Column(3, "x1"));
         * columnLinkedList2.add(new Column(4, "x2"));
         * columnLinkedList2.add(new Column(2, "x3"));
         * columnLinkedList2.add(new Column(0, "x4"));
         * columnLinkedList2.add(new Column(0, "x5"));
         * columnLinkedList2.add(new Column(1, "x6"));
         * columnLinkedList2.add(new Column(8, "equality"));
         */

        LinkedList<Column> fonctionObjectif = new LinkedList<>();
        fonctionObjectif.add(new Column(-800, "t1"));
        fonctionObjectif.add(new Column(-700, "t2"));
        fonctionObjectif.add(new Column(-300, "t3"));
        fonctionObjectif.add(new Column(0, "x1"));
        fonctionObjectif.add(new Column(0, "x2"));
        fonctionObjectif.add(new Column(0, "VA-A1"));
        fonctionObjectif.add(new Column(0, "VA-A2"));
        fonctionObjectif.add(new Column(0, "égalité"));

        LinkedList<Column> fonctionObjectif2 = new LinkedList<>();
        fonctionObjectif2.add(new Column(40, "t1"));
        fonctionObjectif2.add(new Column(50, "t2"));
        fonctionObjectif2.add(new Column(0, "t3"));
        fonctionObjectif2.add(new Column(0, "x1"));
        fonctionObjectif2.add(new Column(0, "x2"));
        fonctionObjectif2.add(new Column(0, "égalité"));

        LinkedList<Row> rowLinkedList = new LinkedList<>();

        LinkedList<Row> rowLinkedList2 = new LinkedList<>();
        /*
         * rowLinkedList.add(new Row(columnLinkedList, "x4"));
         * rowLinkedList.add(new Row(columnLinkedList1, "x5"));
         * rowLinkedList.add(new Row(columnLinkedList2, "x6"));
         * rowLinkedList.add(new Row(fonctionObjectif, "Égalité"));
         * rowLinkedList.add(new Row(columnLinkedList, "VA-A1"));
         * rowLinkedList.add(new Row(columnLinkedList1, "VA-A2"));
         */
        rowLinkedList.add(new Row(columnLinkedList, "VA-A1"));
        rowLinkedList.add(new Row(columnLinkedList1, "VA-A2"));
        rowLinkedList.add(new Row(fonctionObjectif, "égalité"));

        rowLinkedList2.add(new Row(columnLinkedListA, "t1"));
        rowLinkedList2.add(new Row(columnLinkedList1B, "t2"));
        rowLinkedList2.add(new Row(columnLinkedList2C, "t3"));
        rowLinkedList2.add(new Row(fonctionObjectif2, "égalité"));

        Simplex simplexMax = new Simplex(rowLinkedList2);
        simplexMax.resolveSimplex(0);

        System.out.println("Maximisation EX 01");

        Arrays.stream(simplexMax.getConstraints()).forEach(row -> {
            Arrays.stream(row.getEquations()).forEach(column -> {
                System.out.print(column.getFraction() + " || ");
            });
            System.out.println(row.getValue() + " " + row.getDescription());
        });
        Arrays.stream(simplexMax.getFunctionGoal().getEquations()).forEach(column -> {
            System.out.print(column.getFraction() + " || ");
        });
        System.out.println(simplexMax.getValue().getFraction());

        System.out.println(
                "==================================================================================================");
        System.out.println("Minimisation EX 02");
        Simplex simplex = new Simplex(rowLinkedList);
        simplex = simplex.resolveMinimisationUsing2phases();

        Arrays.stream(simplex.getConstraints()).forEach(row -> {
            Arrays.stream(row.getEquations()).forEach(column -> {
                System.out.print(column.getFraction() + " || ");
            });
            System.out.println(row.getValue() + " " + row.getDescription());
        });
        Arrays.stream(simplex.getFunctionGoal().getEquations()).forEach(column -> {
            System.out.print(column.getFraction() + " || ");
        });
        System.out.println(simplex.getValue().getFraction());
    }
}