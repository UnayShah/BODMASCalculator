package com.unayshah;

import com.unayshah.calculator.CalculatorFactory;
import com.unayshah.calculator.ICalculator;

public class CalculatorTest {
	public static void main(String[] args) {
		ICalculator iCalculator = CalculatorFactory.getInstance();
		String expression = "10*10%25%";
		iCalculator.setExpression(expression);
		System.out.println(iCalculator.evaluateExpression());
	}
}
