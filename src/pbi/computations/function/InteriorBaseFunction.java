package pbi.computations.function;

public enum InteriorBaseFunction {

	INTERIOR {

		TripArgFunction baseFunction = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - x) * x * (1 - y) * y * (1 - z) * z;
			}

		};

		TripArgFunction baseFunctionDx = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - 2 * x) * (1 - y) * y * (1 - z) * z;
			}

		};

		TripArgFunction baseFunctionDy = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - x) * x * (1 - 2 * y) * (1 - z) * z;
			}

		};

		TripArgFunction baseFunctionDz = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - x) * x * (1 - y) * y * (1 - 2 * z);
			}

		};

		TripArgFunction baseFunctionDxSquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - 2 * x) * (1 - y) * y * (1 - z) * z * (1 - 2 * x)
						* (1 - y) * y * (1 - z) * z;
			}

		};

		TripArgFunction baseFunctionDySquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - x) * x * (1 - 2 * y) * (1 - z) * z * (1 - x) * x
						* (1 - 2 * y) * (1 - z) * z;
			}

		};

		TripArgFunction baseFunctionDzSquare = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				return (1 - x) * x * (1 - y) * y * (1 - 2 * z) * (1 - x) * x
						* (1 - y) * y * (1 - 2 * z);
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

}
