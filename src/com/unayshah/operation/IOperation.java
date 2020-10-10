package com.unayshah.operation;

public interface IOperation {
	double add(double A, double B);
	double subtract(double A, double B);
	double multiply(double A, double B);
	double divide(double A, double B);
	double percent(double A);
	Integer getOperation(String operationString);
	boolean hasPrecedence(String currentOperation, String topOfOperations);
	public boolean isParanthesis(String s);
}
