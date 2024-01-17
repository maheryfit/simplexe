package mg.calcul;

import org.apache.commons.math3.fraction.Fraction;

import java.util.Arrays;
import java.util.List;

public class Row {
    private String description;
    private Fraction value;
    private Column[] equation;

    public Fraction getValue() {
        return value;
    }

    public Column[] getEquations() {
        return equation;
    }

    public void setValue(Fraction value) {
        this.value = value;
    }

    public void setValue(int numerator, int denominator) {
        setValue(new Fraction(numerator, denominator));
    }

    public void setValue(int number) {
        setValue(new Fraction(number));
    }

    public Row addEquation(Column equation) {
        List<Column> equations = Arrays.asList(this.equation);
        equations.add(equation);
        this.equation = new Column[equations.size()];
        int i = 0;
        for (Column eqt : equations) {
            this.equation[i] = eqt;
            i++;
        }
        return this;
    }

    void setEquation(Column[] equation) {
        this.equation = equation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Column getEquation(int index) {
        return equation[index];
    }

    public Row(List<Column> columnList, String description) {
        setDescription(description);
        extractEquation(columnList);
        extractValue(columnList);
    }

    public Row(Column[] columns, Fraction value, String description) {
        setDescription(description);
        extractEquation(columns);
        this.value = value;
    }

    private void extractEquation(List<Column> columnList) {
        Column[] columns = new Column[columnList.size() - 1];
        for (int i = 0; i < columnList.size() - 1; i++) {
            columns[i] = columnList.get(i);
        }
        setEquation(columns);
    }

    private void extractEquation(Column[] columnsList) {
        Column[] columns = new Column[columnsList.length];
        int i = 0;
        for (Column column : columnsList) {
            columns[i] = new Column(column.getFraction().getNumerator(), column.getFraction().getDenominator(),
                    column.getDescription());
            i++;
        }
        setEquation(columns);
    }

    private void extractValue(List<Column> columnList) {
        Fraction fraction = columnList.get(columnList.size() - 1).getFraction();
        setValue(fraction);
    }

    Column getMax() throws Exception {
        int indexMax = getIndexColumnMax();
        return this.equation[indexMax];
    }

    int getIndexColumnMax() throws Exception {
        Fraction max = new Fraction(0);
        int indexMax = -1;
        int i = 0;
        for (Column column : this.equation) {
            if (column.getFraction().doubleValue() > max.doubleValue() && column.getFraction().doubleValue() != 0) {
                max = column.getFraction();
                indexMax = i;
            }
            i++;
        }
        if (indexMax == -1)
            throw new Exception(
                    "La ligne " + this.getDescription() + " de matrice ne contient que des colonnes négatifs");
        return indexMax;
    }

    int getIndexColumnMin() throws Exception {
        Fraction max = new Fraction(0);
        int indexMin = -1;
        int i = 0;
        for (Column column : this.equation) {
            if (column.getFraction().doubleValue() < max.doubleValue() && column.getFraction().doubleValue() != 0) {
                max = column.getFraction();
                indexMin = i;
            }
            i++;
        }
        if (indexMin == -1)
            throw new Exception(
                    "La ligne " + this.getDescription() + " de matrice ne contient que des colonnes positifs");
        return indexMin;
    }

    boolean isAllNegatif() {
        for (Column column : this.equation) {
            if (column.getFraction().doubleValue() > 0) {
                return false;
            }
        }
        return true;
    }

    boolean isAllPositif() {
        for (Column column : this.equation) {
            if (column.getFraction().doubleValue() < 0) {
                return false;
            }
        }
        return true;
    }

    void calculRowPivot(int columnIndex) {
        int i = 0;
        Fraction fraction = this.equation[columnIndex].getFraction();
        for (Column column : this.equation) {
            this.equation[i].setFraction(column.getFraction().divide(fraction));
            i++;
        }
        this.value = this.value.divide(fraction);
    }

    Row calculRow(Row rowPivot, int columnIndex) {
        Fraction fraction;
        fraction = this.equation[columnIndex].getFraction().divide(rowPivot.equation[columnIndex].getFraction());
        for (int i = 0; i < rowPivot.equation.length; i++) {
            this.equation[i].setFraction(
                    this.equation[i].getFraction().subtract(rowPivot.equation[i].getFraction().multiply(fraction)));
        }
        this.value = this.value.subtract(rowPivot.value.multiply(fraction));
        return this;
    }
}
