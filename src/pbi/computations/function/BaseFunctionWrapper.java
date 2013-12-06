package pbi.computations.function;

public class BaseFunctionWrapper implements TripArgFunction {

	private TripArgFunction wrappedFunction;
	private double[] lr;
	double xDif;
	double yDif;
	double zDif;

	public void setWrappedFunction(TripArgFunction wrappedFunction) {
		this.wrappedFunction = wrappedFunction;
	}

	public void setLr(double[] lr) {
		this.lr = lr;
		xDif = lr[1] - lr[0];
		yDif = lr[3] - lr[2];
		zDif = lr[5] - lr[4];
	}

	@Override
	public double computeValue(double x, double y, double z) {
		return wrappedFunction.computeValue((x - lr[0]) / (xDif), (y - lr[2])
				/ yDif, (z - lr[4]) / zDif);
	}

}
