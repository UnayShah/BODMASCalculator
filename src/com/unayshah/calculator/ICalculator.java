package com.unayshah.calculator;

public interface ICalculator {
	void addOperation(String operation);

	void addOperands(String operand);

	String evaluateExpression();

	String evaluateExpression(String expression);

	void evaluate() throws Exception;

	void setExpression(String expression);

	int extractOperand(int i, char[] tokens);

	int extractStringOperator(int i, char[] tokens);

	void evaluateExpressionOnParenthesisClose() throws Exception;

	void evaluateExpression(int i, char[] tokens) throws Exception;
	
	String ConvertToIntegerIfPossible(String result);

}
