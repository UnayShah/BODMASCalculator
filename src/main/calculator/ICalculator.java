package main.calculator;

public interface ICalculator {
	void addOperation(String operation);

	void addOperands(String operand);

	String evaluateExpression();

	void evaluate();

	void setExpression(String expression);
}
