package pbi.computations.data;

import pbi.computations.function.TripArgFunction;

public class ExampleFunctionElementSpace3 implements InterpolatedFunction {

	TripArgFunction interpolatedFunctionDx;
	TripArgFunction interpolatedFunctionDy;
	TripArgFunction interpolatedFunctionDz;

	public ExampleFunctionElementSpace3() {
		interpolatedFunctionDx = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return 4 * x * z * z * y * y + 2 * x * z - 2 * z - 1;

			}
		};

		interpolatedFunctionDy = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return 4 * x * x * z * z * y + 6 * y + z;

			}
		};

		interpolatedFunctionDz = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return 4 * x * x * z * y * y + x * x - 2 * x + y;

			}
		};
	}

	@Override
	public double computeValue(double x, double y, double z) {

		return 2 * x * x * z * z * y * y + x * x * z + 3 * y * y - 2 * x * z
				+ z * y - x - 5;

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
