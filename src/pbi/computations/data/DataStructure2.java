package pbi.computations.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DataStructure2 implements InterpolatedFunction {
	private static final String DATA_FILE = "3d100.dat";//"data/3d100.dat"; //"data/sixHalfBalls.dat";
	private final double[][][] matrix;
	// liczba pikseli dla poszczegolnych wymiarow
	private int dX;
	private int dY;
	private int dZ;
	//liczba przedzialow dla poszczegolnych wymiarow
	private int dimX;
	private int dimY;
	private int dimZ;
	// odleglosci pomiedzy pikselami dla poszczegolnych wymiarow
	private double dxD;
	private double dyD;
	private double dzD;

	public DataStructure2() {
		this(DATA_FILE);
	}

	public DataStructure2(String file) {
		System.out.println("DataStructure | Reading data from file: " + file);
		matrix = readData(file);
	}

	private double[][][] readData(String dataFile) {
		BufferedReader file = null;
		double[][][] loadedMatrix = null;
		try {
			file = new BufferedReader(new FileReader(dataFile));
			String[] dim = file.readLine().split(" ");
			dX = Integer.valueOf(dim[0]);
			dY = Integer.valueOf(dim[1]);
			dZ = Integer.valueOf(dim[2]);
			dimX = dX-1;
			dimY = dY-1;
			dimZ = dZ-1;
			dxD = 1.0 / dimX;
			dyD = 1.0 / dimY;
			dzD = 1.0 / dimZ;

			System.out
					.println("DataStructure | Creating structure with dimensions: "
							+ dX + " " + dY + " " + dZ);

			loadedMatrix = new double[dX][dY][dZ];

			// double min = Double.MAX_VALUE;
			// double max = Double.MIN_VALUE;
			for (int k = 0; k < dZ; k++) {
				for (int j = 0; j < dY; j++) {
					String[] line = file.readLine().split(" ");
					for (int i = 0; i < dX; i++) {
						loadedMatrix[i][j][k] = Double.valueOf(line[i]);
						// if (loadedMatrix[i][j][k] < min) {
						// min = loadedMatrix[i][j][k];
						// } else if (loadedMatrix[i][j][k] > max) {
						// max = loadedMatrix[i][j][k];
						// }
					}
				}
			}
			// mapowanie wartosci pikseli do [0,1]
			// double div = max - min;
			// for (int k = 0; k < dimZ; k++) {
			// for (int j = 0; j < dimY; j++) {
			// for (int i = 0; i < dimX; i++) {
			// loadedMatrix[i][j][k] = (loadedMatrix[i][j][k] - min)
			// / div;
			// }
			// }
			// }

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

	

	// x,y,z musza byc z przedzialu [0,1]
	@Override
	public double computeValue(double x, double y, double z) {
		
		
		int xLeftPixel = mapXToPixel(x);
		int xRightPixel = xLeftPixel + 1; 
		int yBotPixel = mapYToPixel(y);
		int yTopPixel = yBotPixel + 1; 
		int zNearPixel = mapZToPixel(z);
		int zFarPixel = zNearPixel + 1; 
		
		double xl = xLeftPixel*dxD;
		double yl = yBotPixel*dyD;
		double zl = zNearPixel*dzD; 
		
		double retValue = matrix[xLeftPixel][yBotPixel][zNearPixel]*(1-(x - xl))*(1-(y-yl))*(1-(z - zl));
		retValue += matrix[xRightPixel][yBotPixel][zNearPixel]*(x - xl)*(1-(y-yl))*(1-(z - zl));
		retValue += matrix[xLeftPixel][yTopPixel][zNearPixel]*(1-(x - xl))*(y-yl)*(1-(z - zl));
		retValue += matrix[xRightPixel][yTopPixel][zNearPixel]*(x - xl)*(y-yl)*(1-(z - zl));
		retValue += matrix[xLeftPixel][yBotPixel][zFarPixel]*(1-(x - xl))*(1-(y-yl))*(z - zl);
		retValue += matrix[xRightPixel][yBotPixel][zFarPixel]*(x - xl)*(1-(y-yl))*(z - zl);
		retValue += matrix[xLeftPixel][yTopPixel][zFarPixel]*(1-(x - xl))*(y-yl)*(z - zl);
		retValue += matrix[xRightPixel][yTopPixel][zFarPixel]*(x - xl)*(y-yl)*(z - zl);
		
		return retValue;

	}
	
	@Override
	public double computeDxValue(double x, double y, double z) {
		
		int xLeftPixel = mapXToPixel(x);
		int xRightPixel = xLeftPixel + 1; 
		int yBotPixel = mapYToPixel(y);
		int yTopPixel = yBotPixel + 1; 
		int zNearPixel = mapZToPixel(z);
		int zFarPixel = zNearPixel + 1; 
		
		double xl = xLeftPixel*dxD;
		double yl = yBotPixel*dyD;
		double zl = zNearPixel*dzD; 
		
		double retValue = matrix[xLeftPixel][yBotPixel][zNearPixel]*(-1)*(1-(y-yl))*(1-(z - zl));
		retValue += matrix[xRightPixel][yBotPixel][zNearPixel]*(1)*(1-(y-yl))*(1-(z - zl));
		retValue += matrix[xLeftPixel][yTopPixel][zNearPixel]*(-1)*(y-yl)*(1-(z - zl));
		retValue += matrix[xRightPixel][yTopPixel][zNearPixel]*(1)*(y-yl)*(1-(z - zl));
		retValue += matrix[xLeftPixel][yBotPixel][zFarPixel]*(-1)*(1-(y-yl))*(z - zl);
		retValue += matrix[xRightPixel][yBotPixel][zFarPixel]*(1)*(1-(y-yl))*(z - zl);
		retValue += matrix[xLeftPixel][yTopPixel][zFarPixel]*(-1)*(y-yl)*(z - zl);
		retValue += matrix[xRightPixel][yTopPixel][zFarPixel]*(1)*(y-yl)*(z - zl);
		
		return retValue;
	}

	@Override
	public double computeDyValue(double x, double y, double z) {
		int xLeftPixel = mapXToPixel(x);
		int xRightPixel = xLeftPixel + 1; 
		int yBotPixel = mapYToPixel(y);
		int yTopPixel = yBotPixel + 1; 
		int zNearPixel = mapZToPixel(z);
		int zFarPixel = zNearPixel + 1; 
		
		double xl = xLeftPixel*dxD;
		double yl = yBotPixel*dyD;
		double zl = zNearPixel*dzD; 
		
		double retValue = matrix[xLeftPixel][yBotPixel][zNearPixel]*(1-(x - xl))*(-1)*(1-(z - zl));
		retValue += matrix[xRightPixel][yBotPixel][zNearPixel]*(x - xl)*(-1)*(1-(z - zl));
		retValue += matrix[xLeftPixel][yTopPixel][zNearPixel]*(1-(x - xl))*(1)*(1-(z - zl));
		retValue += matrix[xRightPixel][yTopPixel][zNearPixel]*(x - xl)*(1)*(1-(z - zl));
		retValue += matrix[xLeftPixel][yBotPixel][zFarPixel]*(1-(x - xl))*(-1)*(z - zl);
		retValue += matrix[xRightPixel][yBotPixel][zFarPixel]*(x - xl)*(-1)*(z - zl);
		retValue += matrix[xLeftPixel][yTopPixel][zFarPixel]*(1-(x - xl))*(1)*(z - zl);
		retValue += matrix[xRightPixel][yTopPixel][zFarPixel]*(x - xl)*(1)*(z - zl);
		
		return retValue;
	}

	@Override
	public double computeDzValue(double x, double y, double z) {
		int xLeftPixel = mapXToPixel(x);
		int xRightPixel = xLeftPixel + 1; 
		int yBotPixel = mapYToPixel(y);
		int yTopPixel = yBotPixel + 1; 
		int zNearPixel = mapZToPixel(z);
		int zFarPixel = zNearPixel + 1; 
		
		double xl = xLeftPixel*dxD;
		double yl = yBotPixel*dyD;
		double zl = zNearPixel*dzD; 
		
		double retValue = matrix[xLeftPixel][yBotPixel][zNearPixel]*(1-(x - xl))*(1-(y-yl))*(-1);
		retValue += matrix[xRightPixel][yBotPixel][zNearPixel]*(x - xl)*(1-(y-yl))*(-1);
		retValue += matrix[xLeftPixel][yTopPixel][zNearPixel]*(1-(x - xl))*(y-yl)*(-1);
		retValue += matrix[xRightPixel][yTopPixel][zNearPixel]*(x - xl)*(y-yl)*(-1);
		retValue += matrix[xLeftPixel][yBotPixel][zFarPixel]*(1-(x - xl))*(1-(y-yl))*(1);
		retValue += matrix[xRightPixel][yBotPixel][zFarPixel]*(x - xl)*(1-(y-yl))*(1);
		retValue += matrix[xLeftPixel][yTopPixel][zFarPixel]*(1-(x - xl))*(y-yl)*(1);
		retValue += matrix[xRightPixel][yTopPixel][zFarPixel]*(x - xl)*(y-yl)*(1);
		
		return retValue;
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



}
