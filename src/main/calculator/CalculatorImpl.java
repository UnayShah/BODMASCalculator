package main.calculator;

import java.util.Stack;

import main.operation.OperationFactory;
import main.operation.OperationImpl;
import resources.constants.OperationConstants;

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
		char[] tokens = expression.toCharArray();

		for (int i = 0; i < tokens.length; i++) {

			if (tokens[i] == ' ') {
				continue;
			} else if (tokens[i] >= '0' && tokens[i] <= '9') {
				StringBuffer sbuf = new StringBuffer();
				while (i < tokens.length && tokens[i] >= '0' && tokens[i] <= '9') {
					sbuf.append(tokens[i]);
					i++;
				}
				i--;
				addOperands(sbuf.toString());
			}

			else if (tokens[i] >= 'a' && tokens[i] <= 'z') {
				StringBuffer sbuf = new StringBuffer();
				while (tokens[i] >= 'a' && tokens[i] <= 'z') {
					sbuf.append(tokens[i]);
					i++;
				}
				i--;
				addOperation(sbuf.toString());
			}

			else if (tokens[i] == '(') {
				addOperation(String.valueOf(tokens[i]));
			}

			else if (tokens[i] == ')') {
				while (!operations.isEmpty() && !operations.peek().equals("(")) {
					evaluate();
				}
				if (!operations.isEmpty())
					operations.pop();
			} else {
				while (!operations.isEmpty()
						&& operationImpl.hasPrecedence(String.valueOf(tokens[i]), operations.peek())) {
					evaluate();
				}
				addOperation(String.valueOf(tokens[i]));
			}
		}
		while (!operations.isEmpty()) {
			evaluate();
		}
		return operands.pop();
	}

	public void evaluate() {
		switch (operations.pop()) {
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
		}
	}
}
