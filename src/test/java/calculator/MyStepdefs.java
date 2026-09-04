package calculator;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

public class MyStepdefs {
    private Calculator calculator;
    private int value1;
    private int value2;
    private double result;
    private ArithmeticException divisionError;

    @Before
    public void before() {
        calculator = new Calculator();
    }

    @Given("^Two input values, (-?\\d+) and (-?\\d+)$")
    public void twoInputValuesAnd(int first, int second) {
        value1 = first;
        value2 = second;
    }

    @When("^I select the \"([+*/^])\" operation$")
    public void iSelectTheOperation(String operator) {
        switch (operator) {
            case "*":
                result = calculator.multiply(value1, value2);
                break;
            case "/":
                result = calculator.divide(value1, value2);
                break;
            case "^":
                result = calculator.power(value1, value2);
                break;
            case "+":
                result = calculator.add(value1, value2);
                break;
            default:
                throw new IllegalArgumentException("Unsupported operator: " + operator);
        }
    }

    @Then("^I expect the result (-?\\d+(?:\\.\\d+)?)$")
    public void iExpectTheResult(double expected) {
        Assert.assertEquals(expected, result, 0.0001);
    }

    @When("^I try to divide the two values$")
    public void iTryToDivideTheTwoValues() {
        divisionError = null;
        try {
            calculator.divide(value1, value2);
        } catch (ArithmeticException error) {
            divisionError = error;
        }
    }

    @Then("^I receive a division by zero error$")
    public void iReceiveADivisionByZeroError() {
        Assert.assertNotNull(divisionError);
        Assert.assertEquals("Cannot divide by zero", divisionError.getMessage());
    }
}
