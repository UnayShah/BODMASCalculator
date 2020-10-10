package com.unayshah.operation;

import com.unayshah.calculator.resources.constants.OperationConstants;
import com.unayshah.calculator.resources.constants.PrecedenceConstants;

public class OperationImpl implements IOperation {

	@Override
	public double add(double A, double B) {
		return A + B;
	}

	@Override
	public double subtract(double A, double B) {
		return B - A;
	}

	@Override
	public double multiply(double A, double B) {
		return A * B;
	}

	@Override
	public double divide(double A, double B) {
		return B / A;
	}

	@Override
	public double percent(double A) {
		return A / 100;
	}

	@Override
	public Integer getOperation(String operationString) {
		switch (operationString) {
		case OperationConstants.SYMBOL_DIVIDE:
			return PrecedenceConstants.PRECEDENCE_DIVIDE;
		case OperationConstants.SYMBOL_MULTIPLY:
			return PrecedenceConstants.PRECEDENCE_MULTIPLY;
		case OperationConstants.SYMBOL_ADD:
			return PrecedenceConstants.PRECEDENCE__ADD;
		case OperationConstants.SYMBOL_SUBTRACT:
			return PrecedenceConstants.PRECEDENCE_SUBTRACT;
		case OperationConstants.SYMBOL_PERCENT:
			return PrecedenceConstants.PRECEDENCE_PERCENT;
		default:
			return Integer.MIN_VALUE;
		}
	}

	@Override
	public boolean isParanthesis(String s) {
		switch (s) {
		case OperationConstants.SYMBOL_PARANTHESIS_OPEN:
		case OperationConstants.SYMBOL_PARANTHESIS_CLOSE:
			return true;
		default:
			return false;
		}
	}

	@Override
	public boolean hasPrecedence(String currentOperation, String topOfOperations) {
		return getOperation(currentOperation) >= getOperation(topOfOperations) && !isParanthesis(currentOperation)
				&& !isParanthesis(topOfOperations);
	}

}
