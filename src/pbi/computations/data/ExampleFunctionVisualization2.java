package pbi.computations.data;

import pbi.computations.function.TripArgFunction;

public class ExampleFunctionVisualization2 implements InterpolatedFunction {

	TripArgFunction interpolatedFunctionDx;
	TripArgFunction interpolatedFunctionDy;
	TripArgFunction interpolatedFunctionDz;

	public ExampleFunctionVisualization2() {
		interpolatedFunctionDx = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return y * y * y + z * z * z;
			}
		};

		interpolatedFunctionDy = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return x * x * x + z * z * z;
			}
		};

		interpolatedFunctionDz = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return x * x * x + y * y * y;
			}
		};
	}

	@Override
	public double computeValue(double x, double y, double z) {

		return x * x * x + y * y * y + z * z * z + 1;

	}

	@Override
	public double computeDxValue(double x, double y, double z) {
		return interpolatedFunctionDx.computeValue(x, y, z);
	}

	@Override
	public double computeDyValue(double x, double y, double z) {
		return interpolatedFunctionDy.computeValue(x, y, z);
	}

	@Override
	public double computeDzValue(double x, double y, double z) {
		return interpolatedFunctionDz.computeValue(x, y, z);
	}

}
