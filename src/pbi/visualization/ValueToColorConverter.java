package pbi.visualization;

public interface ValueToColorConverter {

	double[] convert(double value);

	double[] getScaleInformation();

}
