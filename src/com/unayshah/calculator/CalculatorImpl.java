package com.unayshah.calculator;

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

		for (int i = 0; i < tokens.length; i++) {
			if (tokens[i] == ' ') {
				continue;
			} else if (tokens[i] >= '0' && tokens[i] <= '9') {
				i = extractOperand(i, tokens);
			} else if (tokens[i] >= 'a' && tokens[i] <= 'z') {
				i = extractStringOperator(i, tokens);
			} else if (String.valueOf(tokens[i]).equals(OperationConstants.SYMBOL_PARANTHESIS_OPEN)) {
				addOperation(String.valueOf(tokens[i]));
			} else if (String.valueOf(tokens[i]).equals(OperationConstants.SYMBOL_PARANTHESIS_CLOSE)) {
				evaluateExpressionOnParanthesisClose();
			} else {
				evaluateExpression(i, tokens);
			}
		}
		while (!operations.isEmpty()) {
			evaluate();
		}
		return ConvertToIntegerIfPossible(operands.pop());
	}

	public int extractOperand(int i, char[] tokens) {
		StringBuffer sbuf = new StringBuffer();
		while (i < tokens.length && tokens[i] >= '0' && tokens[i] <= '9') {
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

	public void evaluateExpressionOnParanthesisClose() {
		while (!operations.isEmpty() && !operations.peek().equals("(")) {
			evaluate();
		}
		if (!operations.isEmpty())
			operations.pop();
	}

	public void evaluateExpression(int i, char[] tokens) {
		while (!operations.isEmpty() && operationImpl.hasPrecedence(String.valueOf(tokens[i]), operations.peek())) {
			evaluate();
		}
		addOperation(String.valueOf(tokens[i]));
	}

	public void evaluate() {
		String operationsTemp = operations.pop();
		switch (operationsTemp) {
		case OperationConstants.SYMBOL_DIVIDE:
			addOperands(String
					.valueOf(operationImpl.divide(Double.valueOf(operands.pop()), Double.valueOf(operands.pop()))));
			break;
		case OperationConstants.SYMBOL_MULTIPLY:
			addOperands(String
					.valueOf(operationImpl.multiply(Double.valueOf(operands.pop()), Double.valueOf(operands.pop()))));
			break;
		case OperationConstants.SYMBOL_ADD:
			addOperands(
					String.valueOf(operationImpl.add(Double.valueOf(operands.pop()), Double.valueOf(operands.pop()))));
			break;
		case OperationConstants.SYMBOL_SUBTRACT:
			addOperands(String
					.valueOf(operationImpl.subtract(Double.valueOf(operands.pop()), Double.valueOf(operands.pop()))));
			break;
		case OperationConstants.SYMBOL_PERCENT:
			if (!operations.isEmpty()) {
				if (operations.peek().equals(OperationConstants.SYMBOL_ADD)) {
					addOperands(String.valueOf(
							operationImpl.add(1, operationImpl.divide(100.0, Double.valueOf(operands.pop())))));
					operations.pop();
					addOperation("*");
				} else if (operations.peek().equals(OperationConstants.SYMBOL_SUBTRACT)) {
					addOperands(String.valueOf(
							operationImpl.subtract(operationImpl.divide(100.0, Double.valueOf(operands.pop())), 1)));
					operations.pop();
					addOperation("*");
				} else{
					addOperands(String.valueOf(operationImpl.divide(100.0, Double.valueOf(operands.pop()))));
					if(!operations.peek().equals(OperationConstants.SYMBOL_MULTIPLY) && !operations.peek().equals(OperationConstants.SYMBOL_DIVIDE)) {
						addOperands(OperationConstants.SYMBOL_MULTIPLY);
					}
				}
				
			}
			else {
				addOperands(String.valueOf(operationImpl.divide(100.0, Double.valueOf(operands.pop()))));
			}
//			if (!operations.isEmpty()) {
//				operations.pop();
//				addOperation("*");
//			}
			break;
		}
	}

	public String ConvertToIntegerIfPossible(String result) {
		return Double.valueOf(String.valueOf(Integer.valueOf(result.split("\\.")[0]))).equals(Double.valueOf(result))
				? String.valueOf(Integer.valueOf(result.split("\\.")[0]))
				: result;
	}
}
