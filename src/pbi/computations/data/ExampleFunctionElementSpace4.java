package pbi.computations.data;

import pbi.computations.function.TripArgFunction;

public class ExampleFunctionElementSpace4 implements InterpolatedFunction {

	TripArgFunction interpolatedFunctionDx;
	TripArgFunction interpolatedFunctionDy;
	TripArgFunction interpolatedFunctionDz;

	public ExampleFunctionElementSpace4() {
		interpolatedFunctionDx = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return 2 * (1 - 2 * x) * (1 - y) * y * (1 - z) * z;
			}
		};

		interpolatedFunctionDy = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return 2 * (1 - x) * x * (1 - 2 * y) * (1 - z) * z;
			}
		};

		interpolatedFunctionDz = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return 2 * (1 - x) * x * (1 - y) * y * (1 - 2 * z);
			}
		};
	}

	@Override
	public double computeValue(double x, double y, double z) {

		return 2 * (1 - x) * x * (1 - y) * y * (1 - z) * z;

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
