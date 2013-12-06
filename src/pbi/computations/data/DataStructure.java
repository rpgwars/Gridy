package pbi.computations.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DataStructure implements InterpolatedFunction {

	private static final String DATA_FILE = "3d100.dat";// "sixHalfBalls.dat";
	private final double[][][] matrix;
	// liczba pikseli dla poszczegolnych wymiarow
	private int dX;
	private int dY;
	private int dZ;
	// liczba przedzialow dla poszczegolnych wymiarow
	private int dimX;
	private int dimY;
	private int dimZ;
	// odleglosci pomiedzy pikselami dla poszczegolnych wymiarow
	private double dxD;
	private double dyD;
	private double dzD;

	public DataStructure() {
		this(DATA_FILE);
	}

	public DataStructure(String file) {
		System.out.println("DataStructure | Reading data from file: " + file);
		matrix = readData(file);
	}

	// domyslny zakres do testow
	double[][][] readData(String dataFile) {
		BufferedReader file = null;
		double[][][] loadedMatrix = null;
		try {
			file = new BufferedReader(new FileReader(dataFile));
			String[] dim = file.readLine().split(" ");
			dX = Integer.valueOf(dim[0]);
			dY = Integer.valueOf(dim[1]);
			dZ = Integer.valueOf(dim[2]);
			dimX = dX - 1;
			dimY = dY - 1;
			dimZ = dZ - 1;
			dxD = 1.0 / dimX;
			dyD = 1.0 / dimY;
			dzD = 1.0 / dimZ;

			System.out
					.println("DataStructure | Creating structure with dimensions: "
							+ dX + " " + dY + " " + dZ);

			loadedMatrix = new double[dX][dY][dZ];

			loadRawDataToMatrix(file, loadedMatrix, dZ, dY, dX);
			// wartosci moga byc mapowane do [0,1]
			// loadDataToMatrixAndMapThemToZeroOne(file,loadedMatrix,dZ,dY,
			// dX);
			System.out.println("DataStructure | Created structure");

		} catch (IOException e) {
			System.out.println("DataStructure | Error while reading file: "
					+ dataFile);
		} finally {
			try {
				file.close();
			} catch (IOException e) {
				System.out.println("DataStructure | Could not close file: "
						+ dataFile);
			}
		}
		return loadedMatrix;
	}

	private void loadRawDataToMatrix(BufferedReader file,
			double[][][] loadedMatrix, int dZ, int dY, int dX)
			throws IOException {

		for (int k = 0; k < dZ; k++) {
			for (int j = 0; j < dY; j++) {
				String[] line = file.readLine().split(" ");
				for (int i = 0; i < dX; i++) {
					loadedMatrix[i][j][k] = Double.valueOf(line[i]);
				}
			}
		}

	}

	// mapowanie wartoœci do [0,1]
	void loadDataToMatrixAndMapThemToZeroOne(BufferedReader file,
			double[][][] loadedMatrix, int dZ, int dY, int dX)
			throws IOException {
		double min = Double.MAX_VALUE;
		double max = Double.MIN_VALUE;
		for (int k = 0; k < dZ; k++) {
			for (int j = 0; j < dY; j++) {
				String[] line = file.readLine().split(" ");
				for (int i = 0; i < dX; i++) {
					loadedMatrix[i][j][k] = Double.valueOf(line[i]);
					if (loadedMatrix[i][j][k] < min) {
						min = loadedMatrix[i][j][k];
					} else if (loadedMatrix[i][j][k] > max) {
						max = loadedMatrix[i][j][k];
					}
				}
			}
		}
		double div = max - min;
		for (int k = 0; k < dZ; k++) {
			for (int j = 0; j < dY; j++) {
				for (int i = 0; i < dX; i++) {
					loadedMatrix[i][j][k] = (loadedMatrix[i][j][k] - min) / div;
				}
			}
		}

	}

	// x,y,z musza byc z przedzialu [0,1]
	@Override
	public double computeValue(double x, double y, double z) {

		// pobieranie wartosci - ekstrapolacja funkcja liniowa - najpierw
		// wypelniamy macierz ukladu rownan, a potem wyliczamy wspolczynniki
		// funkcji liniowej
		double[][] filledMatrix = fillMatrixWithValues(x, y, z);
		double[] coeff = evaluateLinearCoefficients(filledMatrix);
		return x * coeff[0] + y * coeff[1] + z * coeff[2] + coeff[3];

	}

	@Override
	public double computeDxValue(double x, double y, double z) {

		if (mapXToPixel(x) == 0) {
			return (matrix[1][mapYToPixel(y)][mapZToPixel(z)] - matrix[0][mapYToPixel(y)][mapZToPixel(z)])
					/ dxD;
		}
		if (x == 1.0) {
			return (matrix[dimX][mapYToPixel(y)][mapZToPixel(z)] - matrix[dimX - 1][mapYToPixel(y)][mapZToPixel(z)])
					/ dxD;
		}
		return (matrix[mapXToPixel(x) + 1][mapYToPixel(y)][mapZToPixel(z)] - matrix[mapXToPixel(x)][mapYToPixel(y)][mapZToPixel(z)])
				/ dxD;
	}

	@Override
	public double computeDyValue(double x, double y, double z) {
		if (mapYToPixel(y) == 0) {
			return (matrix[mapXToPixel(x)][1][mapZToPixel(z)] - matrix[mapXToPixel(x)][0][mapZToPixel(z)])
					/ dyD;
		}
		if (y == 1.0) {
			return (matrix[mapXToPixel(x)][dimY][mapZToPixel(z)] - matrix[mapXToPixel(x)][dimY - 1][mapZToPixel(z)])
					/ dyD;
		}
		return (matrix[mapXToPixel(x)][mapYToPixel(y) + 1][mapZToPixel(z)] - matrix[mapXToPixel(x)][mapYToPixel(y)][mapZToPixel(z)])
				/ dyD;
	}

	@Override
	public double computeDzValue(double x, double y, double z) {
		if (mapXToPixel(z) == 0) {
			return (matrix[mapXToPixel(x)][mapYToPixel(y)][1] - matrix[mapXToPixel(x)][mapYToPixel(y)][0])
					/ dzD;
		}
		if (z == 1.0) {
			return (matrix[mapXToPixel(x)][mapYToPixel(y)][dimZ] - matrix[mapXToPixel(x)][mapYToPixel(y)][dimZ - 1])
					/ dzD;
		}
		return (matrix[mapXToPixel(x)][mapYToPixel(y)][mapZToPixel(z) + 1] - matrix[mapXToPixel(x)][mapYToPixel(y)][mapZToPixel(z)])
				/ dzD;
	}

	private double mappedX(int x) {
		return (double) x * dxD;
	}

	private double mappedY(int y) {
		return (double) y * dyD;
	}

	private double mappedZ(int z) {
		return (double) z * dzD;
	}

	private void fillRow(int x, int y, int z, double[] row) {
		row[0] = 1.0;
		row[1] = mappedX(x);
		row[2] = mappedY(y);
		row[3] = mappedZ(z);
		row[4] = matrix[x][y][z];
	}

	private int mapXToPixel(double x) {
		return x == 1.0 ? dimX - 1 : (int) Math.floor(x * dimX);
	}

	private int mapYToPixel(double y) {
		return y == 1.0 ? dimY - 1 : (int) Math.floor(y * dimY);
	}

	private int mapZToPixel(double z) {
		return z == 1.0 ? dimZ - 1 : (int) Math.floor(z * dimZ);
	}

	// zakladamy ze x,y i z sa z [0,1]
	private double[][] fillMatrixWithValues(double x, double y, double z) {
		double[][] result = new double[4][5];
		// wyciaganie 4 najblizszycy punktow
		// najpierw szescian dookola
		int xMin = mapXToPixel(x);
		int yMin = mapYToPixel(y);
		int zMin = mapZToPixel(z);

		int xMax = xMin + 1;
		int yMax = yMin + 1;
		int zMax = zMin + 1;
		// teraz cztery najblizsze
		// cztery punkty do liczenia odleglosci (wierzcholki)
		// xMin,yMin,zMax
		// xMin,yMax,zMin
		// xMax,yMax,zMax
		// xMax,yMin,zMin
		// wspolczynniki sa sumami odleglosci od punktow otaczajacego szescianu
		// (metryka taksowkowa)
		double coeffSum[] = new double[4];
		coeffSum[0] = x - mappedX(xMin) + y - mappedY(yMin) + mappedZ(zMax) - z;
		coeffSum[1] = mappedX(xMax) - x + y - mappedY(yMin) + z - mappedZ(zMin);
		coeffSum[2] = mappedX(xMax) - x + mappedY(yMax) - y + mappedZ(zMax) - z;
		coeffSum[3] = mappedX(xMax) - x + y - mappedY(yMin) + z - mappedZ(zMin);

		int choice = getMin(coeffSum);
		// System.out.println(choice);
		switch (choice) {
		case 0:
			// punkty ( i wartosc) lezace najblizej zadanego punktu, ktore beda
			// uzyte do interpolacji
			// wspolrzedne punktow maja taki sam przedzial, jak dane wejsciowe,
			// czyli np dla srodkowego
			// szescianu przynajmniej jedna z wartosci musi byc wieksza od zera
			fillRow(xMin, yMin, zMax, result[0]);
			fillRow(xMin, yMin, zMin, result[1]);
			fillRow(xMax, yMin, zMax, result[2]);
			fillRow(xMin, yMax, zMax, result[3]);

			break;
		case 1:
			fillRow(xMin, yMax, zMin, result[0]);
			fillRow(xMin, yMin, zMin, result[2]);
			fillRow(xMax, yMax, zMin, result[1]);
			fillRow(xMin, yMax, zMax, result[3]);

			break;
		case 2:
			fillRow(xMax, yMax, zMax, result[0]);
			fillRow(xMax, yMax, zMin, result[2]);
			fillRow(xMin, yMax, zMax, result[1]);
			fillRow(xMax, yMin, zMax, result[3]);

			break;
		case 3:
			fillRow(xMax, yMin, zMin, result[0]);
			fillRow(xMax, yMin, zMax, result[2]);
			fillRow(xMin, yMin, zMin, result[1]);
			fillRow(xMax, yMax, zMin, result[3]);
		}

		return result;
	}

	private int getMin(double[] coeffSum) {
		double minVal = coeffSum[0];
		int min = 0;
		for (int i = 1; i < 4; i++) {
			if (coeffSum[i] < minVal) {
				minVal = coeffSum[i];
				min = i;
			}
		}

		return min;
	}

	double[] evaluateLinearCoefficients(double[][] m) {
		for (int i = 0; i < 4; i++) {
			if (m[i][i] == 0.0) {
				switchRow(i, m);
			}
			double div = m[i][i];
			for (int k = 0; k < 5; k++) {
				m[i][k] /= div;
			}
			for (int j = 1; j < 4; j++) {
				div = m[j][i];
				if (m[j][i] != 0) {
					for (int k = i; k < 5; k++) {
						m[j][k] /= div;
						m[j][k] -= m[i][k];
					}
				}
			}

		}

		return new double[] { m[1][4], m[2][4], m[3][4], m[0][4] };
	}

	private void switchRow(int i, double[][] m) {
		if (i == 3) {
			throw new RuntimeException("Can't solve that matrix");
		}
		for (int j = i; j < 4; j++) {
			if (m[j][i] != 0) {
				// zamien i-ty wiersz z j-tym
				double[] tmp = new double[5];
				for (int k = 0; k < 5; k++) {
					tmp[k] = m[j][k];
					m[j][k] = m[i][k];
					m[i][k] = tmp[k];
				}
				return;
			}
		}
		throw new RuntimeException("Can't solve that matrix");
	}

	@SuppressWarnings("unused")
	private void printMatrix(double[][] m) {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 5; j++) {
				System.out.print(m[i][j]);
			}
			System.out.print("\n");
		}

	}

}
