package pbi.computations.function;

import java.util.ArrayList;
import java.util.List;

public enum EdgeBaseFunctions {

	EDGE_BOT_NEAR {

		TripArgFunction baseFunction = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - x) * x * (1 - y) * (1 - z);
			}

		};

		TripArgFunction baseFunctionDx = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - 2 * x) * (1 - y) * (1 - z);
			}

		};

		TripArgFunction baseFunctionDy = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (x * x - x) * (1 - z);
			}
		};

		TripArgFunction baseFunctionDz = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (x * x - x) * (1 - y);
			}
		};

		TripArgFunction baseFunctionDxSquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - 2 * x) * (1 - y) * (1 - z) * (1 - 2 * x) * (1 - y)
						* (1 - z);
			}

		};

		TripArgFunction baseFunctionDySquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (x * x - x) * (1 - z) * (x * x - x) * (1 - z);
			}
		};

		TripArgFunction baseFunctionDzSquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (x * x - x) * (1 - y) * (x * x - x) * (1 - y);
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

	EDGE_RIGHT_BOT {

		TripArgFunction baseFunction = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return x * (1 - y) * y * (1 - z);
			}

		};

		TripArgFunction baseFunctionDx = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (y - y * y) * (1 - z);
			}

		};

		TripArgFunction baseFunctionDy = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return x * (1 - 2 * y) * (1 - z);
			}
		};

		TripArgFunction baseFunctionDz = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return x * (y * y - y);
			}
		};

		TripArgFunction baseFunctionDxSquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (y - y * y) * (1 - z) * (y - y * y) * (1 - z);
			}

		};

		TripArgFunction baseFunctionDySquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return x * (1 - 2 * y) * (1 - z) * x * (1 - 2 * y) * (1 - z);
			}
		};

		TripArgFunction baseFunctionDzSquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return x * (y * y - y) * x * (y * y - y);
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

	EDGE_BOT_FAR {

		TripArgFunction baseFunction = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return x * (1 - x) * y * (1 - z);
			}

		};

		TripArgFunction baseFunctionDx = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - 2 * x) * y * (1 - z);
			}

		};

		TripArgFunction baseFunctionDy = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (x - x * x) * (1 - z);
			}
		};

		TripArgFunction baseFunctionDz = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (x * x - x) * y;
			}
		};

		TripArgFunction baseFunctionDxSquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - 2 * x) * y * (1 - z) * (1 - 2 * x) * y * (1 - z);
			}

		};

		TripArgFunction baseFunctionDySquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (x - x * x) * (1 - z) * (x - x * x) * (1 - z);
			}
		};

		TripArgFunction baseFunctionDzSquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (x * x - x) * y * (x * x - x) * y;
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

	EDGE_LEFT_BOT {

		TripArgFunction baseFunction = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - x) * (1 - y) * y * (1 - z);
			}

		};

		TripArgFunction baseFunctionDx = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (y * y - y) * (1 - z);
			}

		};

		TripArgFunction baseFunctionDy = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - x) * (1 - 2 * y) * (1 - z);
			}
		};

		TripArgFunction baseFunctionDz = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (y * y - y) * (1 - x);
			}
		};

		TripArgFunction baseFunctionDxSquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (y * y - y) * (1 - z) * (y * y - y) * (1 - z);
			}

		};

		TripArgFunction baseFunctionDySquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - x) * (1 - 2 * y) * (1 - z) * (1 - x) * (1 - 2 * y)
						* (1 - z);
			}
		};

		TripArgFunction baseFunctionDzSquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (y * y - y) * (1 - x) * (y * y - y) * (1 - x);
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

	EDGE_TOP_NEAR {

		TripArgFunction baseFunction = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - x) * x * (1 - y) * z;
			}

		};

		TripArgFunction baseFunctionDx = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - 2 * x) * (1 - y) * z;
			}

		};

		TripArgFunction baseFunctionDy = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (x * x - x) * z;
			}
		};

		TripArgFunction baseFunctionDz = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - x) * x * (1 - y);
			}
		};

		TripArgFunction baseFunctionDxSquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - 2 * x) * (1 - y) * z * (1 - 2 * x) * (1 - y) * z;
			}

		};

		TripArgFunction baseFunctionDySquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (x * x - x) * z * (x * x - x) * z;
			}
		};

		TripArgFunction baseFunctionDzSquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - x) * x * (1 - y) * (1 - x) * x * (1 - y);
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

	EDGE_RIGHT_TOP {

		TripArgFunction baseFunction = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return x * (1 - y) * y * z;
			}

		};

		TripArgFunction baseFunctionDx = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return y * (1 - y) * z;
			}

		};

		TripArgFunction baseFunctionDy = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return x * (1 - 2 * y) * z;
			}
		};

		TripArgFunction baseFunctionDz = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return x * (1 - y) * y;
			}
		};

		TripArgFunction baseFunctionDxSquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return y * (1 - y) * z * y * (1 - y) * z;
			}

		};

		TripArgFunction baseFunctionDySquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return x * (1 - 2 * y) * z * x * (1 - 2 * y) * z;
			}
		};

		TripArgFunction baseFunctionDzSquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return x * (1 - y) * y * x * (1 - y) * y;
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

	EDGE_TOP_FAR {

		TripArgFunction baseFunction = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - x) * x * y * z;
			}

		};

		TripArgFunction baseFunctionDx = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - 2 * x) * y * z;
			}

		};

		TripArgFunction baseFunctionDy = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - x) * x * z;
			}
		};

		TripArgFunction baseFunctionDz = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - x) * x * y;
			}
		};

		TripArgFunction baseFunctionDxSquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - 2 * x) * y * z * (1 - 2 * x) * y * z;
			}

		};

		TripArgFunction baseFunctionDySquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - x) * x * z * (1 - x) * x * z;
			}
		};

		TripArgFunction baseFunctionDzSquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - x) * x * y * (1 - x) * x * y;
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

	EDGE_LEFT_TOP {

		TripArgFunction baseFunction = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - x) * (1 - y) * y * z;
			}

		};

		TripArgFunction baseFunctionDx = new TripArgFunction() {
			// tutaj
			@Override
			public double computeValue(double x, double y, double z) {
				return (y * y - y) * z;
			}

		};

		TripArgFunction baseFunctionDy = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - x) * (1 - 2 * y) * z;
			}
		};

		TripArgFunction baseFunctionDz = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - x) * (y - y * y);
			}
		};

		TripArgFunction baseFunctionDxSquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (y * y - y) * z * (y * y - y) * z;
			}

		};

		TripArgFunction baseFunctionDySquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - x) * (1 - 2 * y) * z * (1 - x) * (1 - 2 * y) * z;
			}
		};

		TripArgFunction baseFunctionDzSquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - x) * (y - y * y) * (1 - x) * (y - y * y);
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

	EDGE_LEFT_NEAR {

		TripArgFunction baseFunction = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - x) * (1 - y) * (1 - z) * z;
			}

		};

		TripArgFunction baseFunctionDx = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (z * z - z) * (1 - y);
			}

		};

		TripArgFunction baseFunctionDy = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (z * z - z) * (1 - x);
			}
		};

		TripArgFunction baseFunctionDz = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - x) * (1 - y) * (1 - 2 * z);
			}
		};

		TripArgFunction baseFunctionDxSquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (z * z - z) * (1 - y) * (z * z - z) * (1 - y);
			}

		};

		TripArgFunction baseFunctionDySquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (z * z - z) * (1 - x) * (z * z - z) * (1 - x);
			}
		};

		TripArgFunction baseFunctionDzSquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - x) * (1 - y) * (1 - 2 * z) * (1 - x) * (1 - y)
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

	EDGE_RIGHT_NEAR {

		TripArgFunction baseFunction = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return x * (1 - y) * (1 - z) * z;
			}

		};

		TripArgFunction baseFunctionDx = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - y) * (1 - z) * z;
			}

		};

		TripArgFunction baseFunctionDy = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (z * z - z) * x;
			}
		};

		TripArgFunction baseFunctionDz = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return x * (1 - y) * (1 - 2 * z);
			}
		};

		TripArgFunction baseFunctionDxSquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - y) * (1 - z) * z * (1 - y) * (1 - z) * z;
			}

		};

		TripArgFunction baseFunctionDySquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (z * z - z) * x * (z * z - z) * x;
			}
		};

		TripArgFunction baseFunctionDzSquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return x * (1 - y) * (1 - 2 * z) * x * (1 - y) * (1 - 2 * z);
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

	EDGE_RIGHT_FAR {

		TripArgFunction baseFunction = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return x * y * (1 - z) * z;
			}

		};

		TripArgFunction baseFunctionDx = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return y * (1 - z) * z;
			}

		};

		TripArgFunction baseFunctionDy = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (z - z * z) * x;
			}
		};

		TripArgFunction baseFunctionDz = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return x * y * (1 - 2 * z);
			}
		};

		TripArgFunction baseFunctionDxSquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return y * (1 - z) * z * y * (1 - z) * z;
			}

		};

		TripArgFunction baseFunctionDySquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (z - z * z) * x * (z - z * z) * x;
			}
		};

		TripArgFunction baseFunctionDzSquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return x * y * (1 - 2 * z) * x * y * (1 - 2 * z);
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

	EDGE_LEFT_FAR {

		TripArgFunction baseFunction = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - x) * y * (1 - z) * z;
			}

		};

		TripArgFunction baseFunctionDx = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (z * z - z) * y;
			}

		};

		TripArgFunction baseFunctionDy = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - x) * (z - z * z);
			}
		};

		TripArgFunction baseFunctionDz = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - x) * y * (1 - 2 * z);
			}
		};

		TripArgFunction baseFunctionDxSquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (z * z - z) * y * (z * z - z) * y;
			}

		};

		TripArgFunction baseFunctionDySquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - x) * (z - z * z) * (1 - x) * (z - z * z);
			}
		};

		TripArgFunction baseFunctionDzSquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - x) * y * (1 - 2 * z) * (1 - x) * y * (1 - 2 * z);
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

	private static List<TripArgFunction> edgeBaseFunctionDxCombination;
	private static List<TripArgFunction> edgeBaseFunctionDyCombination;
	private static List<TripArgFunction> edgeBaseFunctionDzCombination;
	static {

		edgeBaseFunctionDxCombination = new ArrayList<TripArgFunction>(12);
		edgeBaseFunctionDxCombination.add(EdgeBaseFunctions.EDGE_BOT_NEAR
				.getBaseFunctionDerivative(Dimension.dx));
		edgeBaseFunctionDxCombination.add(EdgeBaseFunctions.EDGE_RIGHT_BOT
				.getBaseFunctionDerivative(Dimension.dx));
		edgeBaseFunctionDxCombination.add(EdgeBaseFunctions.EDGE_BOT_FAR
				.getBaseFunctionDerivative(Dimension.dx));
		edgeBaseFunctionDxCombination.add(EdgeBaseFunctions.EDGE_LEFT_BOT
				.getBaseFunctionDerivative(Dimension.dx));
		edgeBaseFunctionDxCombination.add(EdgeBaseFunctions.EDGE_TOP_NEAR
				.getBaseFunctionDerivative(Dimension.dx));
		edgeBaseFunctionDxCombination.add(EdgeBaseFunctions.EDGE_RIGHT_TOP
				.getBaseFunctionDerivative(Dimension.dx));
		edgeBaseFunctionDxCombination.add(EdgeBaseFunctions.EDGE_TOP_FAR
				.getBaseFunctionDerivative(Dimension.dx));
		edgeBaseFunctionDxCombination.add(EdgeBaseFunctions.EDGE_LEFT_TOP
				.getBaseFunctionDerivative(Dimension.dx));
		edgeBaseFunctionDxCombination.add(EdgeBaseFunctions.EDGE_LEFT_NEAR
				.getBaseFunctionDerivative(Dimension.dx));
		edgeBaseFunctionDxCombination.add(EdgeBaseFunctions.EDGE_RIGHT_NEAR
				.getBaseFunctionDerivative(Dimension.dx));
		edgeBaseFunctionDxCombination.add(EdgeBaseFunctions.EDGE_RIGHT_FAR
				.getBaseFunctionDerivative(Dimension.dx));
		edgeBaseFunctionDxCombination.add(EdgeBaseFunctions.EDGE_LEFT_FAR
				.getBaseFunctionDerivative(Dimension.dx));

		edgeBaseFunctionDyCombination = new ArrayList<TripArgFunction>(12);
		edgeBaseFunctionDyCombination.add(EdgeBaseFunctions.EDGE_BOT_NEAR
				.getBaseFunctionDerivative(Dimension.dy));
		edgeBaseFunctionDyCombination.add(EdgeBaseFunctions.EDGE_RIGHT_BOT
				.getBaseFunctionDerivative(Dimension.dy));
		edgeBaseFunctionDyCombination.add(EdgeBaseFunctions.EDGE_BOT_FAR
				.getBaseFunctionDerivative(Dimension.dy));
		edgeBaseFunctionDyCombination.add(EdgeBaseFunctions.EDGE_LEFT_BOT
				.getBaseFunctionDerivative(Dimension.dy));
		edgeBaseFunctionDyCombination.add(EdgeBaseFunctions.EDGE_TOP_NEAR
				.getBaseFunctionDerivative(Dimension.dy));
		edgeBaseFunctionDyCombination.add(EdgeBaseFunctions.EDGE_RIGHT_TOP
				.getBaseFunctionDerivative(Dimension.dy));
		edgeBaseFunctionDyCombination.add(EdgeBaseFunctions.EDGE_TOP_FAR
				.getBaseFunctionDerivative(Dimension.dy));
		edgeBaseFunctionDyCombination.add(EdgeBaseFunctions.EDGE_LEFT_TOP
				.getBaseFunctionDerivative(Dimension.dy));
		edgeBaseFunctionDyCombination.add(EdgeBaseFunctions.EDGE_LEFT_NEAR
				.getBaseFunctionDerivative(Dimension.dy));
		edgeBaseFunctionDyCombination.add(EdgeBaseFunctions.EDGE_RIGHT_NEAR
				.getBaseFunctionDerivative(Dimension.dy));
		edgeBaseFunctionDyCombination.add(EdgeBaseFunctions.EDGE_RIGHT_FAR
				.getBaseFunctionDerivative(Dimension.dy));
		edgeBaseFunctionDyCombination.add(EdgeBaseFunctions.EDGE_LEFT_FAR
				.getBaseFunctionDerivative(Dimension.dy));

		edgeBaseFunctionDzCombination = new ArrayList<TripArgFunction>(12);
		edgeBaseFunctionDzCombination.add(EdgeBaseFunctions.EDGE_BOT_NEAR
				.getBaseFunctionDerivative(Dimension.dz));
		edgeBaseFunctionDzCombination.add(EdgeBaseFunctions.EDGE_RIGHT_BOT
				.getBaseFunctionDerivative(Dimension.dz));
		edgeBaseFunctionDzCombination.add(EdgeBaseFunctions.EDGE_BOT_FAR
				.getBaseFunctionDerivative(Dimension.dz));
		edgeBaseFunctionDzCombination.add(EdgeBaseFunctions.EDGE_LEFT_BOT
				.getBaseFunctionDerivative(Dimension.dz));
		edgeBaseFunctionDzCombination.add(EdgeBaseFunctions.EDGE_TOP_NEAR
				.getBaseFunctionDerivative(Dimension.dz));
		edgeBaseFunctionDzCombination.add(EdgeBaseFunctions.EDGE_RIGHT_TOP
				.getBaseFunctionDerivative(Dimension.dz));
		edgeBaseFunctionDzCombination.add(EdgeBaseFunctions.EDGE_TOP_FAR
				.getBaseFunctionDerivative(Dimension.dz));
		edgeBaseFunctionDzCombination.add(EdgeBaseFunctions.EDGE_LEFT_TOP
				.getBaseFunctionDerivative(Dimension.dz));
		edgeBaseFunctionDzCombination.add(EdgeBaseFunctions.EDGE_LEFT_NEAR
				.getBaseFunctionDerivative(Dimension.dz));
		edgeBaseFunctionDzCombination.add(EdgeBaseFunctions.EDGE_RIGHT_NEAR
				.getBaseFunctionDerivative(Dimension.dz));
		edgeBaseFunctionDzCombination.add(EdgeBaseFunctions.EDGE_RIGHT_FAR
				.getBaseFunctionDerivative(Dimension.dz));
		edgeBaseFunctionDzCombination.add(EdgeBaseFunctions.EDGE_LEFT_FAR
				.getBaseFunctionDerivative(Dimension.dz));

	}

	public static List<TripArgFunction> getEdgeBaseFunctionCombination(
			Dimension dimension) {

		switch (dimension) {
		case dx:
			return edgeBaseFunctionDxCombination;
		case dy:
			return edgeBaseFunctionDyCombination;
		case dz:
			return edgeBaseFunctionDzCombination;
		default:
			throw new RuntimeException("Unkwnon dimension");

		}

	}

}
