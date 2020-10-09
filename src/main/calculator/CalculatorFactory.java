package main.calculator;

public class CalculatorFactory {
	public static CalculatorImpl getInstance() {
		return new CalculatorImpl();
	}
}
