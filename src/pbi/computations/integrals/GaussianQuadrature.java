package pbi.computations.integrals;

import pbi.computations.function.*;

public class GaussianQuadrature {

	private static final double[] roots = new double[] { -0.86113, -0.33998,
			0.33998, 0.86113 };
	private static final double[] weights = new double[] { 0.34785, 0.65214,
			0.65214, 0.34785 };

	private double revertNormalization(double x, double lower, double upper) {
		return (lower + upper) / 2.0 + (upper - lower) / 2.0 * x;
	}

	public double definiteDoubleIntegral(double lower1, double upper1,
			double lower2, double upper2, DoubArgFunction functionToIntegrate) {

		double integral = 0;
		for (int i = 0; i < roots.length; i++) {
			for (int j = 0; j < roots.length; j++) {

				double v = weights[i]
						* weights[j]
						* functionToIntegrate.computeValue(
								revertNormalization(roots[i], lower1, upper1),
								revertNormalization(roots[j], lower2, upper2));
				integral += v;
			}
		}

		return integral * (upper1 - lower1) * (upper2 - lower2) / 4.0;

	}

	public double definiteTripleIntegral(double lower1, double upper1,
			double lower2, double upper2, double lower3, double upper3,
			TripArgFunction functionToIntegrate) {

		double integral = 0;
		for (int i = 0; i < roots.length; i++) {
			for (int j = 0; j < roots.length; j++) {
				for (int k = 0; k < roots.length; k++) {

					double v = weights[i]
							* weights[j]
							* weights[k]
							* functionToIntegrate.computeValue(
									revertNormalization(roots[i], lower1,
											upper1),
									revertNormalization(roots[j], lower2,
											upper2),
									revertNormalization(roots[k], lower3,
											upper3));
					integral += v;

				}
			}
		}

		return integral * (upper1 - lower1) * (upper2 - lower2)
				* (upper3 - lower3) / 8.0;

	}

	// one of three dimension in direction of integration
	public double definiteIntegral(double lower1, double upper1, double v1,
			double v2, TripArgFunction functionToIntegrate, Dimension dimension) {

		double integral = 0;
		for (int i = 0; i < roots.length; i++) {
			double v;

			switch (dimension) {
			case dx:
				v = weights[i]
						* functionToIntegrate.computeValue(
								revertNormalization(roots[i], lower1, upper1),
								v1, v2);
				break;
			case dy:
				v = weights[i]
						* functionToIntegrate.computeValue(v1,
								revertNormalization(roots[i], lower1, upper1),
								v2);
				break;
			case dz:
				v = weights[i]
						* functionToIntegrate.computeValue(v1, v2,
								revertNormalization(roots[i], lower1, upper1));
				break;
			default:
				throw new RuntimeException("Unknown dimension");
			}

			integral += v;

		}

		return integral * (upper1 - lower1) / 2.0;

	}

	// only of of three dimensions that is not in direction of integration
	public double definiteDoubleIntegral(double lower1, double upper1,
			double lower2, double upper2, double v1,
			TripArgFunction functionToIntegrate, Dimension dimension) {

		double integral = 0;
		for (int i = 0; i < roots.length; i++) {
			for (int j = 0; j < roots.length; j++) {
				double v;
				switch (dimension) {
				case dx:
					v = weights[i]
							* weights[j]
							* functionToIntegrate.computeValue(
									v1,
									revertNormalization(roots[i], lower1,
											upper1),
									revertNormalization(roots[j], lower2,
											upper2));
					break;
				case dy:
					v = weights[i]
							* weights[j]
							* functionToIntegrate.computeValue(
									revertNormalization(roots[i], lower1,
											upper1),
									v1,
									revertNormalization(roots[j], lower2,
											upper2));
					break;
				case dz:
					v = weights[i]
							* weights[j]
							* functionToIntegrate.computeValue(
									revertNormalization(roots[i], lower1,
											upper1),
									revertNormalization(roots[j], lower2,
											upper2), v1);
					break;
				default:
					throw new RuntimeException("Unknown dimension");
				}
				integral += v;
			}
		}

		return integral * (upper1 - lower1) * (upper2 - lower2) / 4.0;

	}

}
