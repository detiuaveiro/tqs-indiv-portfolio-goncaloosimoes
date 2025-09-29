package ua;

/**
 * Basic calculator class with methods for addition, subtraction, multiplication, and division.
 */
public class TqsCalculator {

    public double add(double a, double b) {
        return a + b;
    }

    public double subtract(double a, double b) {
        return a - b;
    }

    public double multiply(double a, double b) {
        return a * b;
    }

    public double divide(double a, double b) {
        if (b == 0) {
            throw new IllegalArgumentException("Division by zero is not allowed.");
        }
        return a / b;
    }

    // Alínea (f)
    public double exp(double a, double b) { return Math.pow(a,b); }
    public double sqrt(double a) {
        if (a < 0) {
            throw new IllegalArgumentException("Square root of negative numbers are not allowed.");
        }
        return Math.sqrt(a);
    }
}
