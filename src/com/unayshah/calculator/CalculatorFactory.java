package com.unayshah.calculator;

public class CalculatorFactory {
	public static CalculatorImpl getInstance() {
		return new CalculatorImpl();
	}
}
