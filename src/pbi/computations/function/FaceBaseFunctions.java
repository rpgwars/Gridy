package pbi.computations.function;

import java.util.ArrayList;
import java.util.List;

public enum FaceBaseFunctions {

	FACE_NEAR {

		TripArgFunction baseFunction = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - x) * x * (1 - y) * (1 - z) * z;
			}

		};

		TripArgFunction baseFunctionDx = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - 2 * x) * (1 - y) * (z - z * z);
			}

		};

		TripArgFunction baseFunctionDy = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (x * x - x) * (z - z * z);
			}
		};

		TripArgFunction baseFunctionDz = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (x - x * x) * (1 - y) * (1 - 2 * z);
			}
		};

		TripArgFunction baseFunctionDxSquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - 2 * x) * (1 - y) * (z - z * z) * (1 - 2 * x)
						* (1 - y) * (z - z * z);
			}

		};

		TripArgFunction baseFunctionDySquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (x * x - x) * (z - z * z) * (x * x - x) * (z - z * z);
			}
		};

		TripArgFunction baseFunctionDzSquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (x - x * x) * (1 - y) * (1 - 2 * z) * (x - x * x)
						* (1 - y) * (1 - 2 * z);
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

	FACE_RIGHT {

		TripArgFunction baseFunction = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return x * (1 - y) * y * (1 - z) * z;
			}

		};

		TripArgFunction baseFunctionDx = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (y - y * y) * (z - z * z);
			}

		};

		TripArgFunction baseFunctionDy = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return x * (1 - 2 * y) * (z - z * z);
			}
		};

		TripArgFunction baseFunctionDz = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return x * (y - y * y) * (1 - 2 * z);
			}
		};

		TripArgFunction baseFunctionDxSquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (y - y * y) * (z - z * z) * (y - y * y) * (z - z * z);
			}

		};

		TripArgFunction baseFunctionDySquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return x * (1 - 2 * y) * (z - z * z) * x * (1 - 2 * y)
						* (z - z * z);
			}
		};

		TripArgFunction baseFunctionDzSquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return x * (y - y * y) * (1 - 2 * z) * x * (y - y * y)
						* (1 - 2 * z);
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

	FACE_FAR {

		TripArgFunction baseFunction = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return x * (1 - x) * y * (1 - z) * z;
			}

		};

		TripArgFunction baseFunctionDx = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - 2 * x) * y * (z - z * z);
			}

		};

		TripArgFunction baseFunctionDy = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (x - x * x) * (z - z * z);
			}
		};

		TripArgFunction baseFunctionDz = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (x - x * x) * y * (1 - 2 * z);
			}
		};

		TripArgFunction baseFunctionDxSquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - 2 * x) * y * (z - z * z) * (1 - 2 * x) * y
						* (z - z * z);
			}

		};

		TripArgFunction baseFunctionDySquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (x - x * x) * (z - z * z) * (x - x * x) * (z - z * z);
			}
		};

		TripArgFunction baseFunctionDzSquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (x - x * x) * y * (1 - 2 * z) * (x - x * x) * y
						* (1 - 2 * z);
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

	FACE_LEFT {

		TripArgFunction baseFunction = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - x) * (1 - y) * y * (1 - z) * z;
			}

		};

		TripArgFunction baseFunctionDx = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (y * y - y) * (z - z * z);
			}

		};

		TripArgFunction baseFunctionDy = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - x) * (1 - 2 * y) * (z - z * z);
			}
		};

		TripArgFunction baseFunctionDz = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - x) * (y - y * y) * (1 - 2 * z);
			}
		};

		TripArgFunction baseFunctionDxSquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (y - y * y) * (z - z * z) * (y - y * y) * (z - z * z);
			}

		};

		TripArgFunction baseFunctionDySquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - x) * (1 - 2 * y) * (z - z * z) * (1 - x)
						* (1 - 2 * y) * (z - z * z);
			}
		};

		TripArgFunction baseFunctionDzSquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - x) * (y - y * y) * (1 - 2 * z) * (1 - x)
						* (y - y * y) * (1 - 2 * z);
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

	FACE_TOP {

		TripArgFunction baseFunction = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - x) * x * (1 - y) * y * z;
			}

		};

		TripArgFunction baseFunctionDx = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - 2 * x) * (y - y * y) * z;
			}

		};

		TripArgFunction baseFunctionDy = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (x - x * x) * (1 - 2 * y) * z;
			}
		};

		TripArgFunction baseFunctionDz = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (x - x * x) * (y - y * y);
			}
		};

		TripArgFunction baseFunctionDxSquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - 2 * x) * (y - y * y) * z * (1 - 2 * x)
						* (y - y * y) * z;
			}

		};

		TripArgFunction baseFunctionDySquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (x - x * x) * (1 - 2 * y) * z * (x - x * x)
						* (1 - 2 * y) * z;
			}
		};

		TripArgFunction baseFunctionDzSquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (x - x * x) * (y - y * y) * (x - x * x) * (y - y * y);
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

	FACE_BOT {

		TripArgFunction baseFunction = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - x) * x * y * (1 - y) * (1 - z);
			}

		};

		TripArgFunction baseFunctionDx = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - 2 * x) * y * (1 - y) * (1 - z);
			}

		};

		TripArgFunction baseFunctionDy = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (x - x * x) * (1 - 2 * y) * (1 - z);
			}
		};

		TripArgFunction baseFunctionDz = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (x * x - x) * (y - y * y);
			}
		};

		TripArgFunction baseFunctionDxSquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - 2 * x) * y * (1 - y) * (1 - z) * (1 - 2 * x) * y
						* (1 - y) * (1 - z);
			}

		};

		TripArgFunction baseFunctionDySquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (x - x * x) * (1 - 2 * y) * (1 - z) * (x - x * x)
						* (1 - 2 * y) * (1 - z);
			}
		};

		TripArgFunction baseFunctionDzSquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (x * x - x) * (y - y * y) * (x * x - x) * (y - y * y);
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

	private static List<TripArgFunction> faceBaseFunctionDxCombination;
	private static List<TripArgFunction> faceBaseFunctionDyCombination;
	private static List<TripArgFunction> faceBaseFunctionDzCombination;
	static {

		faceBaseFunctionDxCombination = new ArrayList<TripArgFunction>(6);
		faceBaseFunctionDxCombination.add(FaceBaseFunctions.FACE_NEAR
				.getBaseFunctionDerivative(Dimension.dx));
		faceBaseFunctionDxCombination.add(FaceBaseFunctions.FACE_RIGHT
				.getBaseFunctionDerivative(Dimension.dx));
		faceBaseFunctionDxCombination.add(FaceBaseFunctions.FACE_FAR
				.getBaseFunctionDerivative(Dimension.dx));
		faceBaseFunctionDxCombination.add(FaceBaseFunctions.FACE_LEFT
				.getBaseFunctionDerivative(Dimension.dx));
		faceBaseFunctionDxCombination.add(FaceBaseFunctions.FACE_TOP
				.getBaseFunctionDerivative(Dimension.dx));
		faceBaseFunctionDxCombination.add(FaceBaseFunctions.FACE_BOT
				.getBaseFunctionDerivative(Dimension.dx));

		faceBaseFunctionDyCombination = new ArrayList<TripArgFunction>(6);
		faceBaseFunctionDyCombination.add(FaceBaseFunctions.FACE_NEAR
				.getBaseFunctionDerivative(Dimension.dy));
		faceBaseFunctionDyCombination.add(FaceBaseFunctions.FACE_RIGHT
				.getBaseFunctionDerivative(Dimension.dy));
		faceBaseFunctionDyCombination.add(FaceBaseFunctions.FACE_FAR
				.getBaseFunctionDerivative(Dimension.dy));
		faceBaseFunctionDyCombination.add(FaceBaseFunctions.FACE_LEFT
				.getBaseFunctionDerivative(Dimension.dy));
		faceBaseFunctionDyCombination.add(FaceBaseFunctions.FACE_TOP
				.getBaseFunctionDerivative(Dimension.dy));
		faceBaseFunctionDyCombination.add(FaceBaseFunctions.FACE_BOT
				.getBaseFunctionDerivative(Dimension.dy));

		faceBaseFunctionDzCombination = new ArrayList<TripArgFunction>(6);
		faceBaseFunctionDzCombination.add(FaceBaseFunctions.FACE_NEAR
				.getBaseFunctionDerivative(Dimension.dz));
		faceBaseFunctionDzCombination.add(FaceBaseFunctions.FACE_RIGHT
				.getBaseFunctionDerivative(Dimension.dz));
		faceBaseFunctionDzCombination.add(FaceBaseFunctions.FACE_FAR
				.getBaseFunctionDerivative(Dimension.dz));
		faceBaseFunctionDzCombination.add(FaceBaseFunctions.FACE_LEFT
				.getBaseFunctionDerivative(Dimension.dz));
		faceBaseFunctionDzCombination.add(FaceBaseFunctions.FACE_TOP
				.getBaseFunctionDerivative(Dimension.dz));
		faceBaseFunctionDzCombination.add(FaceBaseFunctions.FACE_BOT
				.getBaseFunctionDerivative(Dimension.dz));

	}

	public static List<TripArgFunction> getFaceBaseFunctionCombination(
			Dimension dimension) {

		switch (dimension) {
		case dx:
			return faceBaseFunctionDxCombination;
		case dy:
			return faceBaseFunctionDyCombination;
		case dz:
			return faceBaseFunctionDzCombination;
		default:
			throw new RuntimeException("Unkwnon dimension");

		}

	}

}
