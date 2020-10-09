package com.unayshah.calculator;

public class CalculatorTest {
	public static void main(String[] args) {
		ICalculator iCalculator = CalculatorFactory.getInstance();
		String expression = "4*4+8/4";
		iCalculator.setExpression(expression);
		System.out.println(iCalculator.evaluateExpression());
	}
}
