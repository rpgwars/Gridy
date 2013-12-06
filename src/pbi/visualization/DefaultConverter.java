package pbi.visualization;

public class DefaultConverter implements ValueToColorConverter {

	private final double[] color;
	private final double minValue;
	private final double maxValue;

	private final double a1;
	private final double b1;

	private final double a2;
	private final double b2;

	private final double width;

	public DefaultConverter(double minValue, double maxValue) {
		color = new double[3];
		this.minValue = minValue;
		this.maxValue = maxValue;

		a1 = 1.0 / (maxValue - minValue);
		b1 = -1.0 / (maxValue - minValue) * minValue;

		a2 = 1.0 / (minValue - maxValue);
		b2 = -1.0 / (minValue - maxValue) * maxValue;

		width = maxValue - minValue;
	}

	@Override
	public double[] convert(double value) {
		if (value > maxValue) {
			color[0] = 1.0;
			color[1] = 1.0;
			color[2] = 1.0;
			return color;
		}
		if (value < minValue) {
			color[0] = 0.0;
			color[1] = 0.0;
			color[2] = 0.0;
			return color;
		}
		color[0] = (a1 * value + b1);
		color[1] = (a2 * value + b2);
		if (value < (minValue + 0.3 * width)) {
			// color[2] = 5.0/width*value + 5.0/(minValue -
			// maxValue)*(0.9*minValue + 0.1*maxValue);
			color[2] = 10.0 / 3.0 / (maxValue - minValue) * value + 10.0 / 3.0
					/ (minValue - maxValue) * minValue;
		} else {
			color[2] = -5.0 / width * value + 5.0 / (maxValue - minValue)
					* (minValue / 2.0 + maxValue / 2.0);
		}
		if (color[2] < 0)
			color[2] = 0;
		// if(color[2] > 1)
		// color[2] = 1;

		return color;
	}

	public double[] getScaleInformation() {
		double[] info = new double[6];
		info[0] = minValue;
		info[1] = minValue + 1.0 / 5.0 * width;
		info[2] = minValue + 2.0 / 5.0 * width;
		info[3] = minValue + 3.0 / 5.0 * width;
		info[4] = minValue + 4.0 / 5.0 * width;
		info[5] = maxValue;

		return info;

	}

}
