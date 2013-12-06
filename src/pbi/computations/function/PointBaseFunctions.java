package pbi.computations.function;

import java.util.ArrayList;
import java.util.List;

public enum PointBaseFunctions {

	POINT_BOT_LEFT_NEAR {

		TripArgFunction baseFunction = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - x) * (1 - y) * (1 - z);
			}

		};

		TripArgFunction baseFunctionDx = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return -(1 - y) * (1 - z);
			}

		};

		TripArgFunction baseFunctionDy = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return -(1 - x) * (1 - z);
			}

		};

		TripArgFunction baseFunctionDz = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return -(1 - x) * (1 - y);
			}

		};

		TripArgFunction baseFunctionDxSquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - y) * (1 - y) * (1 - z) * (1 - z);
			}

		};

		TripArgFunction baseFunctionDySquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - x) * (1 - x) * (1 - z) * (1 - z);
			}

		};

		TripArgFunction baseFunctionDzSquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - x) * (1 - x) * (1 - y) * (1 - y);
			}

		};

		@Override
		public TripArgFunction getBaseFunction() {
			return baseFunction;
		}

		TripArgFunction[] baseFunctionDerivatives = new TripArgFunction[] {
				baseFunctionDx, baseFunctionDy, baseFunctionDz };

		@Override
		public TripArgFunction[] getBaseFunctionDerivatives() {
			return baseFunctionDerivatives;
		}

		TripArgFunction[] baseFunctionDerivativesSquare = new TripArgFunction[] {
				baseFunctionDxSquare, baseFunctionDySquare,
				baseFunctionDzSquare };

		@Override
		public TripArgFunction[] getBaseFunctionDerivativesSquare() {
			return baseFunctionDerivativesSquare;
		}
	},

	POINT_BOT_RIGHT_NEAR {

		TripArgFunction baseFunction = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return x * (1 - y) * (1 - z);
			}

		};

		TripArgFunction baseFunctionDx = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - y) * (1 - z);
			}

		};

		TripArgFunction baseFunctionDy = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return -x * (1 - z);
			}

		};

		TripArgFunction baseFunctionDz = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return -x * (1 - y);
			}

		};

		TripArgFunction baseFunctionDxSquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - y) * (1 - y) * (1 - z) * (1 - z);
			}

		};

		TripArgFunction baseFunctionDySquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return x * x * (1 - z) * (1 - z);
			}

		};

		TripArgFunction baseFunctionDzSquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return x * x * (1 - y) * (1 - y);
			}

		};

		@Override
		public TripArgFunction getBaseFunction() {
			return baseFunction;
		}

		TripArgFunction[] baseFunctionDerivatives = new TripArgFunction[] {
				baseFunctionDx, baseFunctionDy, baseFunctionDz };

		@Override
		public TripArgFunction[] getBaseFunctionDerivatives() {
			return baseFunctionDerivatives;
		}

		TripArgFunction[] baseFunctionDerivativesSquare = new TripArgFunction[] {
				baseFunctionDxSquare, baseFunctionDySquare,
				baseFunctionDzSquare };

		@Override
		public TripArgFunction[] getBaseFunctionDerivativesSquare() {
			return baseFunctionDerivativesSquare;
		}
	},

	POINT_BOT_RIGHT_FAR {

		TripArgFunction baseFunction = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return x * y * (1 - z);
			}

		};

		TripArgFunction baseFunctionDx = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return y * (1 - z);
			}

		};

		TripArgFunction baseFunctionDy = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return x * (1 - z);
			}

		};

		TripArgFunction baseFunctionDz = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return -x * y;
			}

		};

		TripArgFunction baseFunctionDxSquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return y * y * (1 - z) * (1 - z);
			}

		};

		TripArgFunction baseFunctionDySquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return x * x * (1 - z) * (1 - z);
			}

		};

		TripArgFunction baseFunctionDzSquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return x * x * y * y;
			}

		};

		@Override
		public TripArgFunction getBaseFunction() {
			return baseFunction;
		}

		TripArgFunction[] baseFunctionDerivatives = new TripArgFunction[] {
				baseFunctionDx, baseFunctionDy, baseFunctionDz };

		@Override
		public TripArgFunction[] getBaseFunctionDerivatives() {
			return baseFunctionDerivatives;
		}

		TripArgFunction[] baseFunctionDerivativesSquare = new TripArgFunction[] {
				baseFunctionDxSquare, baseFunctionDySquare,
				baseFunctionDzSquare };

		@Override
		public TripArgFunction[] getBaseFunctionDerivativesSquare() {
			return baseFunctionDerivativesSquare;
		}
	},

	POINT_BOT_LEFT_FAR {

		TripArgFunction baseFunction = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - x) * y * (1 - z);
			}

		};

		TripArgFunction baseFunctionDx = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return -y * (1 - z);
			}

		};

		TripArgFunction baseFunctionDy = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - x) * (1 - z);
			}

		};

		TripArgFunction baseFunctionDz = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return -(1 - x) * y;
			}

		};

		TripArgFunction baseFunctionDxSquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return y * y * (1 - z) * (1 - z);
			}

		};

		TripArgFunction baseFunctionDySquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - x) * (1 - x) * (1 - z) * (1 - z);
			}

		};

		TripArgFunction baseFunctionDzSquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - x) * (1 - x) * y * y;
			}

		};

		@Override
		public TripArgFunction getBaseFunction() {
			return baseFunction;
		}

		TripArgFunction[] baseFunctionDerivatives = new TripArgFunction[] {
				baseFunctionDx, baseFunctionDy, baseFunctionDz };

		@Override
		public TripArgFunction[] getBaseFunctionDerivatives() {
			return baseFunctionDerivatives;
		}

		TripArgFunction[] baseFunctionDerivativesSquare = new TripArgFunction[] {
				baseFunctionDxSquare, baseFunctionDySquare,
				baseFunctionDzSquare };

		@Override
		public TripArgFunction[] getBaseFunctionDerivativesSquare() {
			return baseFunctionDerivativesSquare;
		}
	},

	POINT_TOP_LEFT_NEAR {

		TripArgFunction baseFunction = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - x) * (1 - y) * z;
			}

		};

		TripArgFunction baseFunctionDx = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return -(1 - y) * z;
			}

		};

		TripArgFunction baseFunctionDy = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return -(1 - x) * z;
			}

		};

		TripArgFunction baseFunctionDz = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - x) * (1 - y);
			}

		};

		TripArgFunction baseFunctionDxSquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - y) * (1 - y) * z * z;
			}

		};

		TripArgFunction baseFunctionDySquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - x) * (1 - x) * z * z;
			}

		};

		TripArgFunction baseFunctionDzSquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - x) * (1 - x) * (1 - y) * (1 - y);
			}

		};

		@Override
		public TripArgFunction getBaseFunction() {
			return baseFunction;
		}

		TripArgFunction[] baseFunctionDerivatives = new TripArgFunction[] {
				baseFunctionDx, baseFunctionDy, baseFunctionDz };

		@Override
		public TripArgFunction[] getBaseFunctionDerivatives() {
			return baseFunctionDerivatives;
		}

		TripArgFunction[] baseFunctionDerivativesSquare = new TripArgFunction[] {
				baseFunctionDxSquare, baseFunctionDySquare,
				baseFunctionDzSquare };

		@Override
		public TripArgFunction[] getBaseFunctionDerivativesSquare() {
			return baseFunctionDerivativesSquare;
		}
	},

	POINT_TOP_RIGHT_NEAR {

		TripArgFunction baseFunction = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return x * (1 - y) * z;
			}

		};

		TripArgFunction baseFunctionDx = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - y) * z;
			}

		};

		TripArgFunction baseFunctionDy = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return -x * z;
			}

		};

		TripArgFunction baseFunctionDz = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return x * (1 - y);
			}

		};

		TripArgFunction baseFunctionDxSquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - y) * (1 - y) * z * z;
			}

		};

		TripArgFunction baseFunctionDySquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return x * x * z * z;
			}

		};

		TripArgFunction baseFunctionDzSquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return x * x * (1 - y) * (1 - y);
			}

		};

		@Override
		public TripArgFunction getBaseFunction() {
			return baseFunction;
		}

		TripArgFunction[] baseFunctionDerivatives = new TripArgFunction[] {
				baseFunctionDx, baseFunctionDy, baseFunctionDz };

		@Override
		public TripArgFunction[] getBaseFunctionDerivatives() {
			return baseFunctionDerivatives;
		}

		TripArgFunction[] baseFunctionDerivativesSquare = new TripArgFunction[] {
				baseFunctionDxSquare, baseFunctionDySquare,
				baseFunctionDzSquare };

		@Override
		public TripArgFunction[] getBaseFunctionDerivativesSquare() {
			return baseFunctionDerivativesSquare;
		}
	},

	POINT_TOP_RIGHT_FAR {

		TripArgFunction baseFunction = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return x * y * z;
			}

		};

		TripArgFunction baseFunctionDx = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return y * z;
			}

		};

		TripArgFunction baseFunctionDy = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return x * z;
			}

		};

		TripArgFunction baseFunctionDz = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return x * y;
			}

		};

		TripArgFunction baseFunctionDxSquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return y * y * z * z;
			}

		};

		TripArgFunction baseFunctionDySquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return x * x * z * z;
			}

		};

		TripArgFunction baseFunctionDzSquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return x * x * y * y;
			}

		};

		@Override
		public TripArgFunction getBaseFunction() {
			return baseFunction;
		}

		TripArgFunction[] baseFunctionDerivatives = new TripArgFunction[] {
				baseFunctionDx, baseFunctionDy, baseFunctionDz };

		@Override
		public TripArgFunction[] getBaseFunctionDerivatives() {
			return baseFunctionDerivatives;
		}

		TripArgFunction[] baseFunctionDerivativesSquare = new TripArgFunction[] {
				baseFunctionDxSquare, baseFunctionDySquare,
				baseFunctionDzSquare };

		@Override
		public TripArgFunction[] getBaseFunctionDerivativesSquare() {
			return baseFunctionDerivativesSquare;
		}
	},

	POINT_TOP_LEFT_FAR {

		TripArgFunction baseFunction = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - x) * y * z;
			}

		};

		TripArgFunction baseFunctionDx = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return -y * z;
			}

		};

		TripArgFunction baseFunctionDy = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - x) * z;
			}

		};

		TripArgFunction baseFunctionDz = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - x) * y;
			}

		};

		TripArgFunction baseFunctionDxSquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return y * y * z * z;
			}

		};

		TripArgFunction baseFunctionDySquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - x) * (1 - x) * z * z;
			}

		};

		TripArgFunction baseFunctionDzSquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - x) * (1 - x) * y * y;
			}

		};

		@Override
		public TripArgFunction getBaseFunction() {
			return baseFunction;
		}

		TripArgFunction[] baseFunctionDerivatives = new TripArgFunction[] {
				baseFunctionDx, baseFunctionDy, baseFunctionDz };

		@Override
		public TripArgFunction[] getBaseFunctionDerivatives() {
			return baseFunctionDerivatives;
		}

		TripArgFunction[] baseFunctionDerivativesSquare = new TripArgFunction[] {
				baseFunctionDxSquare, baseFunctionDySquare,
				baseFunctionDzSquare };

		@Override
		public TripArgFunction[] getBaseFunctionDerivativesSquare() {
			return baseFunctionDerivativesSquare;
		}
	};

	public abstract TripArgFunction getBaseFunction();

	public abstract TripArgFunction[] getBaseFunctionDerivatives();

	public abstract TripArgFunction[] getBaseFunctionDerivativesSquare();

	public TripArgFunction getBaseFunctionDerivative(Dimension dimension) {
		switch (dimension) {
		case dx:
			return this.getBaseFunctionDerivatives()[0];
		case dy:
			return this.getBaseFunctionDerivatives()[1];
		case dz:
			return this.getBaseFunctionDerivatives()[2];
		default:
			return null;
		}

	}

	public TripArgFunction getBaseFunctionDerivativeSquare(Dimension dimension) {
		switch (dimension) {
		case dx:
			return this.getBaseFunctionDerivativesSquare()[0];
		case dy:
			return this.getBaseFunctionDerivativesSquare()[1];
		case dz:
			return this.getBaseFunctionDerivativesSquare()[2];
		default:
			return null;
		}

	}

	private static List<TripArgFunction> pointBaseFunctionDxCombination;
	private static List<TripArgFunction> pointBaseFunctionDyCombination;
	private static List<TripArgFunction> pointBaseFunctionDzCombination;
	static {

		pointBaseFunctionDxCombination = new ArrayList<TripArgFunction>(8);
		pointBaseFunctionDxCombination
				.add(PointBaseFunctions.POINT_BOT_LEFT_NEAR
						.getBaseFunctionDerivative(Dimension.dx));
		pointBaseFunctionDxCombination
				.add(PointBaseFunctions.POINT_BOT_RIGHT_NEAR
						.getBaseFunctionDerivative(Dimension.dx));
		pointBaseFunctionDxCombination
				.add(PointBaseFunctions.POINT_BOT_RIGHT_FAR
						.getBaseFunctionDerivative(Dimension.dx));
		pointBaseFunctionDxCombination
				.add(PointBaseFunctions.POINT_BOT_LEFT_FAR
						.getBaseFunctionDerivative(Dimension.dx));
		pointBaseFunctionDxCombination
				.add(PointBaseFunctions.POINT_TOP_LEFT_NEAR
						.getBaseFunctionDerivative(Dimension.dx));
		pointBaseFunctionDxCombination
				.add(PointBaseFunctions.POINT_TOP_RIGHT_NEAR
						.getBaseFunctionDerivative(Dimension.dx));
		pointBaseFunctionDxCombination
				.add(PointBaseFunctions.POINT_TOP_RIGHT_FAR
						.getBaseFunctionDerivative(Dimension.dx));
		pointBaseFunctionDxCombination
				.add(PointBaseFunctions.POINT_TOP_LEFT_FAR
						.getBaseFunctionDerivative(Dimension.dx));

		pointBaseFunctionDyCombination = new ArrayList<TripArgFunction>(8);
		pointBaseFunctionDyCombination
				.add(PointBaseFunctions.POINT_BOT_LEFT_NEAR
						.getBaseFunctionDerivative(Dimension.dy));
		pointBaseFunctionDyCombination
				.add(PointBaseFunctions.POINT_BOT_RIGHT_NEAR
						.getBaseFunctionDerivative(Dimension.dy));
		pointBaseFunctionDyCombination
				.add(PointBaseFunctions.POINT_BOT_RIGHT_FAR
						.getBaseFunctionDerivative(Dimension.dy));
		pointBaseFunctionDyCombination
				.add(PointBaseFunctions.POINT_BOT_LEFT_FAR
						.getBaseFunctionDerivative(Dimension.dy));
		pointBaseFunctionDyCombination
				.add(PointBaseFunctions.POINT_TOP_LEFT_NEAR
						.getBaseFunctionDerivative(Dimension.dy));
		pointBaseFunctionDyCombination
				.add(PointBaseFunctions.POINT_TOP_RIGHT_NEAR
						.getBaseFunctionDerivative(Dimension.dy));
		pointBaseFunctionDyCombination
				.add(PointBaseFunctions.POINT_TOP_RIGHT_FAR
						.getBaseFunctionDerivative(Dimension.dy));
		pointBaseFunctionDyCombination
				.add(PointBaseFunctions.POINT_TOP_LEFT_FAR
						.getBaseFunctionDerivative(Dimension.dy));

		pointBaseFunctionDzCombination = new ArrayList<TripArgFunction>(8);
		pointBaseFunctionDzCombination
				.add(PointBaseFunctions.POINT_BOT_LEFT_NEAR
						.getBaseFunctionDerivative(Dimension.dz));
		pointBaseFunctionDzCombination
				.add(PointBaseFunctions.POINT_BOT_RIGHT_NEAR
						.getBaseFunctionDerivative(Dimension.dz));
		pointBaseFunctionDzCombination
				.add(PointBaseFunctions.POINT_BOT_RIGHT_FAR
						.getBaseFunctionDerivative(Dimension.dz));
		pointBaseFunctionDzCombination
				.add(PointBaseFunctions.POINT_BOT_LEFT_FAR
						.getBaseFunctionDerivative(Dimension.dz));
		pointBaseFunctionDzCombination
				.add(PointBaseFunctions.POINT_TOP_LEFT_NEAR
						.getBaseFunctionDerivative(Dimension.dz));
		pointBaseFunctionDzCombination
				.add(PointBaseFunctions.POINT_TOP_RIGHT_NEAR
						.getBaseFunctionDerivative(Dimension.dz));
		pointBaseFunctionDzCombination
				.add(PointBaseFunctions.POINT_TOP_RIGHT_FAR
						.getBaseFunctionDerivative(Dimension.dz));
		pointBaseFunctionDzCombination
				.add(PointBaseFunctions.POINT_TOP_LEFT_FAR
						.getBaseFunctionDerivative(Dimension.dz));

	}

	public static List<TripArgFunction> getPointBaseFunctionCombination(
			Dimension dimension) {

		switch (dimension) {
		case dx:
			return pointBaseFunctionDxCombination;
		case dy:
			return pointBaseFunctionDyCombination;
		case dz:
			return pointBaseFunctionDzCombination;
		default:
			throw new RuntimeException("Unkwnon dimension");

		}

	}

}
