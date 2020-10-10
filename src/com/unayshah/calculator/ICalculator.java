package com.unayshah.calculator;

public interface ICalculator {
	void addOperation(String operation);

	void addOperands(String operand);

	String evaluateExpression();

	String evaluateExpression(String expression);

	void evaluate();

	void setExpression(String expression);

	int extractOperand(int i, char[] tokens);

	int extractStringOperator(int i, char[] tokens);

	void evaluateExpressionOnParanthesisClose();

	void evaluateExpression(int i, char[] tokens);
	
	String ConvertToIntegerIfPossible(String result);

}
