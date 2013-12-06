package pbi.computations.function;

import java.util.ArrayList;
import java.util.List;

public class TripArgFunctionProduct implements TripArgFunction {

	List<TripArgFunction> functionList;

	public TripArgFunctionProduct() {
		functionList = new ArrayList<TripArgFunction>();
	}

	public void addFunction(TripArgFunction function) {
		functionList.add(function);
	}

	@Override
	public double computeValue(double x, double y, double z) {
		double result = 1;
		for (TripArgFunction function : functionList) {
			result *= function.computeValue(x, y, z);

		}

		return result;
	}

}
