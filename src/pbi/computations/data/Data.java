package pbi.computations.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Data implements InterpolatedFunction {

	//chyba wystarczy dodac ze jesli left = right to left = left -1
	
	private double[][][] inputMatrix;
	
	private double dx; 
	private double dy; 
	private double dz; 
	
	private double xSize; 
	private double ySize; 
	private double zSize; 
	
	private int nrOfXPixels;
	private int nrOfYPixels;
	private int nrOfZPixels;
	
	public Data(double xSize, double ySize, double zSize, String fileName) throws IOException{
		
		this.xSize = xSize; 
		this.ySize = ySize; 
		this.zSize = zSize; 
		
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		String[] line = reader.readLine().split(" ");
		
		nrOfXPixels = Integer.valueOf(line[0]);
		nrOfYPixels = Integer.valueOf(line[1]);
		nrOfZPixels = Integer.valueOf(line[2]);
		
		dx = xSize / (nrOfXPixels - 1);
		dy = ySize / (nrOfYPixels - 1);
		dz = zSize / (nrOfZPixels - 1);
		
		inputMatrix = new double[nrOfZPixels][nrOfYPixels][nrOfXPixels]; 
		
		 
		for(int i = 0; i<nrOfZPixels; i++){
			for(int j = 0; j<nrOfYPixels; j++){
				
				line = reader.readLine().split(" "); 
				for(int k = 0; k<nrOfXPixels; k++){
					inputMatrix[i][j][k] = Integer.valueOf(line[k]); 
				}
				
			}
			
		}

	}
	
	public Data(String fileName) throws IOException{
		this(1,1,1,fileName); 
	}
	
	
	private int getLeftXPixelNumber(double x){
		if(x == xSize)
			return nrOfXPixels - 2; 
		return (int) Math.floor(x / dx); 
		
	}
	
	private int getLeftYPixelNumber(double y){
		if(y == ySize)
			return nrOfYPixels - 2; 
		return (int) Math.floor(y / dy); 
		
	}
	
	private int getLeftZPixelNumber(double z){
		if(z == zSize)
			return nrOfZPixels - 2; 
		return (int) Math.floor(z / dz); 
		
	}
	
	private int getRightXPixelNumber(double x){
		if(x == 0)
			return 1; 
		return (int) Math.ceil(x / dx); 
		
	}
	
	private int getRightYPixelNumber(double y){
		if(y == 0)
			return 1; 
		return (int) Math.ceil(y / dy); 
		
	}
	
	private int getRightZPixelNumber(double z){
		if(z == 0)
			return 1; 
		return (int) Math.ceil(z / dz); 
		
	}
	
	
	@Override
	public double computeValue(double x, double y, double z) {
		
		int xLeftNr = getLeftXPixelNumber(x); 
		int yLeftNr = getLeftYPixelNumber(y);
		int zLeftNr = getLeftZPixelNumber(z); 
		
		int xRightNr = getRightXPixelNumber(x); 
		int yRightNr = getRightYPixelNumber(y);
		int zRightNr = getRightZPixelNumber(z);
		
		double leftBotNearValue = inputMatrix[zLeftNr][yLeftNr][xLeftNr];
		double rightBotNearValue = inputMatrix[zLeftNr][yLeftNr][xRightNr];
		double rightBotFarValue = inputMatrix[zLeftNr][yRightNr][xRightNr];
		double leftBotFarValue = inputMatrix[zLeftNr][yRightNr][xLeftNr];
		double leftTopNearValue = inputMatrix[zRightNr][yLeftNr][xLeftNr];
		double rightTopNearValue = inputMatrix[zRightNr][yLeftNr][xRightNr];
		double rightTopFarValue = inputMatrix[zRightNr][yRightNr][xRightNr];
		double leftTopFarValue = inputMatrix[zRightNr][yRightNr][xLeftNr];
		
		double xFactor = (x - (xLeftNr*dx/xSize))/dx;
		double xRevertedFactor = 1 - xFactor;
		double yFactor = (y - (yLeftNr*dy/ySize))/dy;
		double yRevertedFactor = 1 - yFactor; 
		double zFactor = (z - (zLeftNr*dz/zSize))/dz;
		double zRevertedFactor = 1 - zFactor;
		
		return leftBotNearValue*xRevertedFactor*yRevertedFactor*zRevertedFactor +
			   rightBotNearValue*xFactor*yRevertedFactor*zRevertedFactor +
			   rightBotFarValue*xFactor*yFactor*zRevertedFactor +
			   leftBotFarValue*xRevertedFactor*yFactor*zRevertedFactor +
			   
			   leftTopNearValue*xRevertedFactor*yRevertedFactor*zFactor +
			   rightTopNearValue*xFactor*yRevertedFactor*zFactor +
			   rightTopFarValue*xFactor*yFactor*zFactor +
			   leftTopFarValue*xRevertedFactor*yFactor*zFactor; 
		
	}

	@Override
	public double computeDxValue(double x, double y, double z) {

		int xLeftNr = getLeftXPixelNumber(x); 
		int yLeftNr = getLeftYPixelNumber(y);
		int zLeftNr = getLeftZPixelNumber(z); 
		
		int xRightNr = getRightXPixelNumber(x); 
		int yRightNr = getRightYPixelNumber(y);
		int zRightNr = getRightZPixelNumber(z);
		
		double leftBotNearValue = inputMatrix[zLeftNr][yLeftNr][xLeftNr];
		double rightBotNearValue = inputMatrix[zLeftNr][yLeftNr][xRightNr];
		double rightBotFarValue = inputMatrix[zLeftNr][yRightNr][xRightNr];
		double leftBotFarValue = inputMatrix[zLeftNr][yRightNr][xLeftNr];
		double leftTopNearValue = inputMatrix[zRightNr][yLeftNr][xLeftNr];
		double rightTopNearValue = inputMatrix[zRightNr][yLeftNr][xRightNr];
		double rightTopFarValue = inputMatrix[zRightNr][yRightNr][xRightNr];
		double leftTopFarValue = inputMatrix[zRightNr][yRightNr][xLeftNr];
		
		
		double yFactor = (y - (yLeftNr*dy/ySize))/dy;
		double yRevertedFactor = 1 - yFactor; 
		double zFactor = (z - (zLeftNr*dz/zSize))/dz;
		double zRevertedFactor = 1 - zFactor;
		
		return leftBotNearValue*(-1/dx)*yRevertedFactor*zRevertedFactor +
			   rightBotNearValue*(1/dx)*yRevertedFactor*zRevertedFactor +
			   rightBotFarValue*(1/dx)*yFactor*zRevertedFactor +
			   leftBotFarValue*(-1/dx)*yFactor*zRevertedFactor +
			   
			   leftTopNearValue*(-1/dx)*yRevertedFactor*zFactor +
			   rightTopNearValue*(1/dx)*yRevertedFactor*zFactor +
			   rightTopFarValue*(1/dx)*yFactor*zFactor +
			   leftTopFarValue*(-1/dx)*yFactor*zFactor;
	}

	@Override
	public double computeDyValue(double x, double y, double z) {


		int xLeftNr = getLeftXPixelNumber(x); 
		int yLeftNr = getLeftYPixelNumber(y);
		int zLeftNr = getLeftZPixelNumber(z); 
		
		int xRightNr = getRightXPixelNumber(x); 
		int yRightNr = getRightYPixelNumber(y);
		int zRightNr = getRightZPixelNumber(z);
		
		double leftBotNearValue = inputMatrix[zLeftNr][yLeftNr][xLeftNr];
		double rightBotNearValue = inputMatrix[zLeftNr][yLeftNr][xRightNr];
		double rightBotFarValue = inputMatrix[zLeftNr][yRightNr][xRightNr];
		double leftBotFarValue = inputMatrix[zLeftNr][yRightNr][xLeftNr];
		double leftTopNearValue = inputMatrix[zRightNr][yLeftNr][xLeftNr];
		double rightTopNearValue = inputMatrix[zRightNr][yLeftNr][xRightNr];
		double rightTopFarValue = inputMatrix[zRightNr][yRightNr][xRightNr];
		double leftTopFarValue = inputMatrix[zRightNr][yRightNr][xLeftNr];
		
		double xFactor = (x - (xLeftNr*dx/xSize))/dx;
		double xRevertedFactor = 1 - xFactor;
		double zFactor = (z - (zLeftNr*dz/zSize))/dz;
		double zRevertedFactor = 1 - zFactor;
		
		return leftBotNearValue*xRevertedFactor*(-1/dy)*zRevertedFactor +
			   rightBotNearValue*xFactor*(-1/dy)*zRevertedFactor +
			   rightBotFarValue*xFactor*(1/dy)*zRevertedFactor +
			   leftBotFarValue*xRevertedFactor*(1/dy)*zRevertedFactor +
			   
			   leftTopNearValue*xRevertedFactor*(-1/dy)*zFactor +
			   rightTopNearValue*xFactor*(-1/dy)*zFactor +
			   rightTopFarValue*xFactor*(1/dy)*zFactor +
			   leftTopFarValue*xRevertedFactor*(1/dy)*zFactor;
	}

	@Override
	public double computeDzValue(double x, double y, double z) {


		int xLeftNr = getLeftXPixelNumber(x); 
		int yLeftNr = getLeftYPixelNumber(y);
		int zLeftNr = getLeftZPixelNumber(z); 
		
		int xRightNr = getRightXPixelNumber(x); 
		int yRightNr = getRightYPixelNumber(y);
		int zRightNr = getRightZPixelNumber(z);
		
		double leftBotNearValue = inputMatrix[zLeftNr][yLeftNr][xLeftNr];
		double rightBotNearValue = inputMatrix[zLeftNr][yLeftNr][xRightNr];
		double rightBotFarValue = inputMatrix[zLeftNr][yRightNr][xRightNr];
		double leftBotFarValue = inputMatrix[zLeftNr][yRightNr][xLeftNr];
		double leftTopNearValue = inputMatrix[zRightNr][yLeftNr][xLeftNr];
		double rightTopNearValue = inputMatrix[zRightNr][yLeftNr][xRightNr];
		double rightTopFarValue = inputMatrix[zRightNr][yRightNr][xRightNr];
		double leftTopFarValue = inputMatrix[zRightNr][yRightNr][xLeftNr];
		
		double xFactor = (x - (xLeftNr*dx/xSize))/dx;
		double xRevertedFactor = 1 - xFactor;
		double yFactor = (y - (yLeftNr*dy/ySize))/dy;
		double yRevertedFactor = 1 - yFactor; 

		
		return leftBotNearValue*xRevertedFactor*yRevertedFactor*(-1/dz) +
			   rightBotNearValue*xFactor*yRevertedFactor*(-1/dz) +
			   rightBotFarValue*xFactor*yFactor*(-1/dz) +
			   leftBotFarValue*xRevertedFactor*yFactor*(-1/dz) +
			   
			   leftTopNearValue*xRevertedFactor*yRevertedFactor*(1/dz) +
			   rightTopNearValue*xFactor*yRevertedFactor*(1/dz) +
			   rightTopFarValue*xFactor*yFactor*(1/dz) +
			   leftTopFarValue*xRevertedFactor*yFactor*(1/dz);
	}

}
