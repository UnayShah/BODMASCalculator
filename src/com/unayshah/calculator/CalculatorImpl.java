package com.unayshah.calculator;

import java.util.EmptyStackException;
import java.util.Stack;

import com.unayshah.calculator.resources.constants.OperationConstants;
import com.unayshah.operation.OperationFactory;
import com.unayshah.operation.OperationImpl;

public class CalculatorImpl implements ICalculator {
	Stack<String> operations;
	Stack<String> operands;

	String expression;

	OperationImpl operationImpl;

	public CalculatorImpl() {
		operations = new Stack<String>();
		operands = new Stack<String>();
		operationImpl = OperationFactory.getInstance();
	}

	public CalculatorImpl(String expression) {
		operations = new Stack<String>();
		operands = new Stack<String>();
		this.expression = expression;
		operationImpl = OperationFactory.getInstance();
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public void addOperation(String operation) {
		operations.add(operation);
	}

	public void addOperands(String operand) {
		operands.add(operand);
	}

	public String evaluateExpression() {
		return evaluateExpression(this.expression);
	}

	public String evaluateExpression(String expression) {
		char[] tokens = expression.toCharArray();
		try {
			for (int i = 0; i < tokens.length; i++) {
				if (tokens[i] == ' ') {
					continue;
				} else if ((tokens[i] >= '0' && tokens[i] <= '9') || tokens[i] == '.') {
					i = extractOperand(i, tokens);
				} else if (tokens[i] >= 'a' && tokens[i] <= 'z') {
					i = extractStringOperator(i, tokens);
				} else if (String.valueOf(tokens[i]).equals(OperationConstants.SYMBOL_PARANTHESIS_OPEN)) {
					addOperation(String.valueOf(tokens[i]));
				} else if (String.valueOf(tokens[i]).equals(OperationConstants.SYMBOL_PARANTHESIS_CLOSE)) {
					evaluateExpressionOnParenthesisClose();
				} else {
					evaluateExpression(i, tokens);
				}
			}
			while (!operations.isEmpty()) {
				evaluate();
			}
		} catch (Exception e) {
			return "Divide By Zero";
		}
		if (!operands.isEmpty())
			return ConvertToIntegerIfPossible(operands.pop());
		else
			return "";
	}

	public int extractOperand(int i, char[] tokens) {
		StringBuffer sbuf = new StringBuffer();
		while (i < tokens.length && ((tokens[i] >= '0' && tokens[i] <= '9') || (tokens[i] == '.'))) {
			sbuf.append(tokens[i]);
			i++;
		}
		addOperands(sbuf.toString());
		i--;
		return i;
	}

	public int extractStringOperator(int i, char[] tokens) {
		StringBuffer sbuf = new StringBuffer();
		while (tokens[i] >= 'a' && tokens[i] <= 'z') {
			sbuf.append(tokens[i]);
			i++;
		}
		addOperation(sbuf.toString());
		i--;
		return i;
	}

	public void evaluateExpressionOnParenthesisClose() throws Exception {
		try {
			while (!operations.isEmpty() && !operations.peek().equals("(")) {
				evaluate();
			}
		} catch (Exception e) {
			throw new Exception();
		}
		if (!operations.isEmpty())
			operations.pop();
	}

	public void evaluateExpression(int i, char[] tokens) throws Exception {
		try {
			while (!operations.isEmpty() && !operands.isEmpty()
					&& operationImpl.hasPrecedence(String.valueOf(tokens[i]), operations.peek())) {
				evaluate();
			}
		} catch (Exception e) {
			throw new Exception();
		}
//		if(operands.isEmpty())addOperands("0");
		addOperation(String.valueOf(tokens[i]));
	}

	public void evaluate() throws Exception {
		String operationsTemp = operations.pop();
		String operand1 = "", operand2 = "";
		if (operands.isEmpty()) {
			addOperation(operationsTemp);
			return;
		}
		try {
			switch (operationsTemp) {
			case OperationConstants.SYMBOL_DIVIDE:
				operand1 = operands.pop();
				operand2 = operands.pop();
				if (operand2 == "0") {
					throw new Exception();
				}
				addOperands(String.valueOf(operationImpl.divide(Double.valueOf(operand1), Double.valueOf(operand2))));
				break;
			case OperationConstants.SYMBOL_MULTIPLY:
				operand1 = operands.pop();
				operand2 = operands.pop();
				addOperands(String.valueOf(operationImpl.multiply(Double.valueOf(operand1), Double.valueOf(operand2))));
				break;
			case OperationConstants.SYMBOL_ADD:
				operand1 = operands.pop();
				operand2 = operands.pop();
				addOperands(String.valueOf(operationImpl.add(Double.valueOf(operand1), Double.valueOf(operand2))));
				break;
			case OperationConstants.SYMBOL_SUBTRACT:
				operand1 = operands.pop();
				operand2 = operands.pop();
				addOperands(String.valueOf(operationImpl.subtract(Double.valueOf(operand1), Double.valueOf(operand2))));
				break;
			case OperationConstants.SYMBOL_PERCENT:
				operand1 = operands.pop();
				if (!operations.isEmpty()) {
					if (operations.peek().equals(OperationConstants.SYMBOL_ADD)) {
						addOperands(String
								.valueOf(operationImpl.add(1, operationImpl.divide(100.0, Double.valueOf(operand1)))));
						operations.pop();
						addOperation("*");
					} else if (operations.peek().equals(OperationConstants.SYMBOL_SUBTRACT)) {
						addOperands(String.valueOf(
								operationImpl.subtract(operationImpl.divide(100.0, Double.valueOf(operand1)), 1)));
						operations.pop();
						addOperation("*");
					} else {
						addOperands(String.valueOf(operationImpl.divide(100.0, Double.valueOf(operand1))));
						if (!operations.peek().equals(OperationConstants.SYMBOL_MULTIPLY)
								&& !operations.peek().equals(OperationConstants.SYMBOL_DIVIDE)) {
							addOperands(OperationConstants.SYMBOL_MULTIPLY);
						}
					}

				} else {
					addOperands(String.valueOf(operationImpl.divide(100.0, Double.valueOf(operand1))));
				}
				break;
			}
		} catch (EmptyStackException e) {
			System.err.println("Error");
			addOperands(operand1);
		} catch (Exception e) {
			throw new Exception();
		}
	}

	public String ConvertToIntegerIfPossible(String result) {
		try {
		return Double.valueOf(String.valueOf(Integer.valueOf(result.split("\\.")[0]))).equals(Double.valueOf(result))
				? String.valueOf(Integer.valueOf(result.split("\\.")[0]))
				: result;
		}catch(Exception e) {
			return result;
		}
	}
}
