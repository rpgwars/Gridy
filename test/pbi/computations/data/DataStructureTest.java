package pbi.computations.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;

import junit.framework.Assert;

import org.junit.Test;

import pbi.computations.data.DataStructure;

public class DataStructureTest {

	@Test
	public void sixHalfBallsTest() {
		DataStructure dataStructure = new DataStructure("sixHalfBalls.dat");

		// values at certain points
		Assert.assertEquals(1.1, dataStructure.computeValue(0.992, 1.0, 0.8));
		Assert.assertEquals(1.1, dataStructure.computeValue(0.7, 0.0, 0.2));
		Assert.assertEquals(8.8, dataStructure.computeValue(0.5, 0.0, 0.5));

		// x derivative values at certain points
		Assert.assertEquals(0.0, dataStructure.computeDxValue(0.95, 0.95, 0.8));
		Assert.assertEquals(0.0, dataStructure.computeDxValue(0.7, 0.0, 0.2));
		Assert.assertTrue(0.0 != dataStructure.computeDxValue(0.95, 0.31, 0.37));

		// y derivative values at certain points
		Assert.assertEquals(0.0, dataStructure.computeDyValue(0.95, 0.95, 0.8));
		Assert.assertEquals(0.0, dataStructure.computeDyValue(0.7, 0.0, 0.2));
		Assert.assertTrue(0.0 != dataStructure.computeDyValue(0.99, 0.74, 0.58));

		// z derivative values at certain points
		Assert.assertEquals(0.0, dataStructure.computeDzValue(0.95, 0.95, 0.8));
		Assert.assertEquals(0.0, dataStructure.computeDzValue(0.7, 0.0, 0.2));
		Assert.assertTrue(0.0 != dataStructure.computeDzValue(0.99, 0.65, 0.71));
	}

	@Test
	public void ThreeD100Test() {
		DataStructure dataStructure = new DataStructure("3d100.dat");

		// values at certain points
		Assert.assertEquals(1.0, dataStructure.computeValue(0.98, 1.0, 0.7));
		Assert.assertEquals(1.0, dataStructure.computeValue(0.75, 0.0, 0.2));
		Assert.assertEquals(2.0, dataStructure.computeValue(0.2, 0.21, 0.19));
		Assert.assertEquals(2.0, dataStructure.computeValue(0.24, 0.18, 0.22));

		// x derivative values at certain points
		Assert.assertEquals(0.0, dataStructure.computeDxValue(0.95, 0.95, 0.8));
		Assert.assertEquals(0.0, dataStructure.computeDxValue(0.7, 0.0, 0.2));
		Assert.assertTrue(0.0 != dataStructure.computeDxValue(0.98, 0.97, 0.80));

		// y derivative values at certain points
		Assert.assertEquals(0.0, dataStructure.computeDyValue(0.95, 0.95, 0.8));
		Assert.assertEquals(0.0, dataStructure.computeDyValue(0.7, 0.0, 0.2));
		Assert.assertTrue(0.0 != dataStructure.computeDyValue(0.86, 0.87, 0.37));

		// z derivative values at certain points
		Assert.assertEquals(0.0, dataStructure.computeDzValue(0.95, 0.95, 0.8));
		Assert.assertEquals(0.0, dataStructure.computeDzValue(0.7, 0.0, 0.2));
		Assert.assertTrue(0.0 != dataStructure.computeDzValue(0.99, 0.95, 0.4));
	}

	@Test
	public void readDataTest() {
		DataStructure dataStructure = new DataStructure();
		double[][][] matrix = dataStructure.readData("testData.dat");
		Assert.assertEquals(3.4, matrix[1][0][0]);
		Assert.assertEquals(11.12, matrix[1][0][1]);
		Assert.assertEquals(7.8, matrix[1][1][0]);
		Assert.assertEquals(15.16, matrix[1][1][1]);
		Assert.assertEquals(1.2, matrix[0][0][0]);
		Assert.assertEquals(9.10, matrix[0][0][1]);
		Assert.assertEquals(5.6, matrix[0][1][0]);
		Assert.assertEquals(13.14, matrix[0][1][1]);
	}

	@Test
	public void loadDataToMatrixAndMapThemToZeroOneTest() throws Exception {
		// given
		DataStructure dataStructure = new DataStructure();
		double[][][] matrix = new double[2][2][2];
		BufferedReader file = new BufferedReader(new FileReader("testData.dat"));
		file.readLine();

		// when
		dataStructure
				.loadDataToMatrixAndMapThemToZeroOne(file, matrix, 2, 2, 2);

		// then
		Assert.assertTrue(isEqualWithEpsilon(0.15, matrix[1][0][0]));
		Assert.assertTrue(isEqualWithEpsilon(0.71, matrix[1][0][1]));
		Assert.assertTrue(isEqualWithEpsilon(0.47, matrix[1][1][0]));
		Assert.assertTrue(isEqualWithEpsilon(1.0, matrix[1][1][1]));
		Assert.assertTrue(isEqualWithEpsilon(0.0, matrix[0][0][0]));
		Assert.assertTrue(isEqualWithEpsilon(0.56, matrix[0][0][1]));
		Assert.assertTrue(isEqualWithEpsilon(0.31, matrix[0][1][0]));
		Assert.assertTrue(isEqualWithEpsilon(0.85, matrix[0][1][1]));
	}

	@Test
	public void evaluateLinearCoefficientsTest() {
		//given
		double[][] values = fillMatrixForCoefficientsTest();
		DataStructure dataStructure = new DataStructure();
		double[] expectedValues = fillExpectedValuesForCoefficientsTest();
		
		//then
		Assert.assertTrue(Arrays.equals(expectedValues,
				dataStructure.evaluateLinearCoefficients(values)));

	}


	private double[] fillExpectedValuesForCoefficientsTest() {
		double[] result = new double[4];
		result[0] = 0.0;
		result[1] = 0.0;
		result[2] = 0.0;
		result[3] = 3.0;
		return result;
	}

	private double[][] fillMatrixForCoefficientsTest() {
		double[][] result = new double[4][5];
		result[0][0] = 1.0;
		result[1][1] = 1.0;
		result[2][2] = 1.0;
		result[3][3] = 1.0;
		result[3][3] = 1.0;
		result[0][4] = 3.0;
		result[1][4] = 3.0;
		result[2][4] = 3.0;
		result[3][4] = 3.0;

		return result;

	}

	private boolean isEqualWithEpsilon(double d, double e) {
		double eps = 0.01;
		return Math.abs(d - e) < eps;
	}
}