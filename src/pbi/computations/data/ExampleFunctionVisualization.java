package pbi.computations.data;

import pbi.computations.function.TripArgFunction;

public class ExampleFunctionVisualization implements InterpolatedFunction {

	TripArgFunction interpolatedFunctionDx;
	TripArgFunction interpolatedFunctionDy;
	TripArgFunction interpolatedFunctionDz;

	public ExampleFunctionVisualization() {
		interpolatedFunctionDx = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return 2 * x;
			}
		};

		interpolatedFunctionDy = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return 2 * y;
			}
		};

		interpolatedFunctionDz = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return 2 * z;
			}
		};
	}

	@Override
	public double computeValue(double x, double y, double z) {

		return x * x + y * y + z * z;

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
