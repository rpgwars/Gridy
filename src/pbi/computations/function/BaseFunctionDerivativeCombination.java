package pbi.computations.function;

import java.util.List;

public class BaseFunctionDerivativeCombination implements TripArgFunction {

	private List<TripArgFunction> baseFunctionCombination;
	private double[] cooficients;
	private double[] lr;
	private int offset;
	private Dimension dimension;

	// List<Double> wyniki = new ArrayList<Double>();

	public BaseFunctionDerivativeCombination(int size) {

	}

	public void setBaseFunctionCombination(
			List<TripArgFunction> baseFunctionCombination,
			double[] cooficients, int offset) {
		this.baseFunctionCombination = baseFunctionCombination;
		this.cooficients = cooficients;
		this.offset = offset;
	}

	public void setMappings(double[] lr) {
		this.lr = lr;

	}

	public void setDerivativeDirection(Dimension dimension) {
		this.dimension = dimension;
	}

	private double scale(double value) {
		switch (dimension) {
		case dx:
			return value / (lr[1] - lr[0]);
		case dy:
			return value / (lr[3] - lr[2]);
		case dz:
			return value / (lr[5] - lr[4]);
		default:
			throw new RuntimeException();
		}
	}

	@Override
	public double computeValue(double x, double y, double z) {
		int i = 0;
		double value = 0;
		double totalValue = 0;
		// wyniki = new ArrayList<Double>();
		for (TripArgFunction baseFunction : baseFunctionCombination) {

			value = cooficients[offset + i]
					* baseFunction.computeValue((x - lr[0]) / (lr[1] - lr[0]),
							(y - lr[2]) / (lr[3] - lr[2]), (z - lr[4])
									/ (lr[5] - lr[4]));

			value = scale(value);

			i++;
			// wyniki.add(value);
			totalValue += value;
			// System.out.println("part value " + value);
		}
		return totalValue;
	}

}
