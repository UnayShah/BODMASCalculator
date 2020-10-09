package main.operation;

public class OperationFactory {
	public static OperationImpl getInstance() {
		return new OperationImpl();
	}
}
