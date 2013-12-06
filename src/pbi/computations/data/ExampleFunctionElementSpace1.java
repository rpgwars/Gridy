package pbi.computations.data;

import pbi.computations.function.TripArgFunction;

public class ExampleFunctionElementSpace1 implements InterpolatedFunction {

	TripArgFunction interpolatedFunctionDx;
	TripArgFunction interpolatedFunctionDy;
	TripArgFunction interpolatedFunctionDz;

	// (1-x)*(1-y)*(1-z) + x*z*(1-z)*y*(1-y) + (1-x)*y*z*(1-z)
	public ExampleFunctionElementSpace1() {
		interpolatedFunctionDx = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return -(1 - y) * (1 - z) + z * (1 - z) * y * (1 - y) - y * z
						* (1 - z);
			}
		};

		interpolatedFunctionDy = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return -(1 - x) * (1 - z) + x * z * (1 - z) * (1 - 2 * y)
						+ (1 - x) * z * (1 - z);
			}
		};

		interpolatedFunctionDz = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return -(1 - x) * (1 - y) + x * (1 - 2 * z) * y * (1 - y)
						+ (1 - x) * y * (1 - 2 * z);
			}
		};
	}

	@Override
	public double computeValue(double x, double y, double z) {
		return (1 - x) * (1 - y) * (1 - z) + x * z * (1 - z) * y * (1 - y)
				+ (1 - x) * y * z * (1 - z);

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
