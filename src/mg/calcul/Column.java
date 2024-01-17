package mg.calcul;

import org.apache.commons.math3.fraction.Fraction;

public class Column {
    private String description;
    private Fraction fraction;

    public String getDescription() {
        return description;
    }

    public Fraction getFraction() {
        return fraction;
    }

    void setFraction(Fraction fraction) {
        this.fraction = fraction;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFraction(int numerator, int denominator) {
        setFraction(new Fraction(numerator, denominator));
    }

    public void setFraction(int value) {
        setFraction(new Fraction(value));
    }

    public Column(int numerator, int denominator, String description) {
        setFraction(numerator, denominator);
        setDescription(description);
    }

    public Column(int number, String description) {
        setFraction(number);
        setDescription(description);
    }

    public Column(Fraction number, String description) {
        setFraction(number);
        setDescription(description);
    }

}
