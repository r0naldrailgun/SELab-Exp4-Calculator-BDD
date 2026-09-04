package calculator;

public class Calculator {
    public int add(int a, int b) {
        return a + b;
    }

    public int multiply(int first, int second) {
        return first * second;
    }

    public double divide(int first, int second) {
        if (second == 0) {
            throw new ArithmeticException("Cannot divide by zero");
        }
        return (double) first / second;
    }

    public double power(int base, int exponent) {
        if (exponent < 0) {
            throw new IllegalArgumentException("Exponent must be zero or positive");
        }

        double result = 1;
        for (int i = 0; i < exponent; i++) {
            result *= base;
        }
        return result;
    }
}
