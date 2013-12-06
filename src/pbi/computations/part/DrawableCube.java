package pbi.computations.part;

import pbi.computations.function.Dimension;

public abstract class DrawableCube extends ComputationCube {

	private static double scalingWidth;
	private static double xBalance;
	private static double yBalance;
	private static double zBalance;

	private boolean graphicalDataComputated;

	// private boolean exactVertexDataComputated;

	public DrawableCube(double xTopLeftFar, double yTopLeftFar,
			double zTopLeftFar, double xBotRightNear, double yBotRightNear,
			double zBotRightNear, int levelOfDivision,
			double[] lastCoordinates, CubePosition cubePosition) {
		super(xTopLeftFar, yTopLeftFar, zTopLeftFar, xBotRightNear,
				yBotRightNear, zBotRightNear, levelOfDivision, lastCoordinates,
				cubePosition);

		if (cubePosition == null) {
			double xWidth = xBotRightNear - xTopLeftFar;
			double yWidth = yTopLeftFar - yBotRightNear;
			double zWidth = zTopLeftFar - zBotRightNear;
			scalingWidth = Math.max(Math.max(xWidth, yWidth), zWidth) / 2.0;

			xBalance = (xBotRightNear + xTopLeftFar) / 2.0;
			yBalance = (yTopLeftFar + yBotRightNear) / 2.0;
			zBalance = (zTopLeftFar + zBotRightNear) / 2.0;

		}

		graphicalDataComputated = false;
		// exactVertexDataComputated = false;

	}

	public void saveCubeQuadStripCoordinatesCoordinates(double[] buffer,
			int offset) {

		double xl = (this.xTopLeftFar - xBalance) / scalingWidth;
		double xr = (this.xBotRightNear - xBalance) / scalingWidth;
		double yl = (this.yBotRightNear - yBalance) / scalingWidth;
		double yr = (this.yTopLeftFar - yBalance) / scalingWidth;
		double zl = (this.zBotRightNear - zBalance) / scalingWidth;
		double zr = (this.zTopLeftFar - zBalance) / scalingWidth;

		// near face
		buffer[offset++] = xl;
		buffer[offset++] = yl;
		buffer[offset++] = zl;

		buffer[offset++] = xr;
		buffer[offset++] = yl;
		buffer[offset++] = zl;

		buffer[offset++] = xl;
		buffer[offset++] = yl;
		buffer[offset++] = zr;

		buffer[offset++] = xr;
		buffer[offset++] = yl;
		buffer[offset++] = zr;

		// top face
		buffer[offset++] = xl;
		buffer[offset++] = yr;
		buffer[offset++] = zr;

		buffer[offset++] = xr;
		buffer[offset++] = yr;
		buffer[offset++] = zr;

		// far face
		buffer[offset++] = xl;
		buffer[offset++] = yr;
		buffer[offset++] = zl;

		buffer[offset++] = xr;
		buffer[offset++] = yr;
		buffer[offset++] = zl;

		// bot face
		buffer[offset++] = xl;
		buffer[offset++] = yl;
		buffer[offset++] = zl;

		buffer[offset++] = xr;
		buffer[offset++] = yl;
		buffer[offset++] = zl;

	}

	private static final int maxQuadsPerStripExponent = 6;
	private static final int numberOfDataPerQuad = 16;
	private double[] faceVertexAndColourData;// = new double[6 *
												// getNumberOfQuadsPerFace(levelOfDivision)*numberOfDataPerQuad];
	private double[] faceVertexAndColourApproximationQualityData;// = new
																	// double[6
																	// *
																	// getNumberOfQuadsPerFace(levelOfDivision)*numberOfDataPerQuad];

	// private double[] exactVertexValuesAndCoordinates = new double[8 *(3 + 3 +
	// 1)];

	public static int getNumberOfQuadsPerFaceStrip(int levelOfDivision) {
		if (levelOfDivision >= maxQuadsPerStripExponent)
			return 1;
		return (int) Math.pow(2, maxQuadsPerStripExponent - levelOfDivision);
	}

	public static int getNumberOfQuadsPerFace(int levelOfDivision) {
		return (int) Math.pow(getNumberOfQuadsPerFaceStrip(levelOfDivision), 2);
	}

	public double[] getFaceVertexAndColourData(boolean approximationQuality) {

		if (!graphicalDataComputated) {
			faceVertexAndColourData = new double[6
					* getNumberOfQuadsPerFace(levelOfDivision)
					* numberOfDataPerQuad];
			faceVertexAndColourApproximationQualityData = new double[6
					* getNumberOfQuadsPerFace(levelOfDivision)
					* numberOfDataPerQuad];
			int numberOfQuadsPerFaceStrip = getNumberOfQuadsPerFaceStrip(levelOfDivision);

			double xQuadDif = (xBotRightNear - xTopLeftFar)
					/ (double) numberOfQuadsPerFaceStrip;
			double yQuadDif = (yTopLeftFar - yBotRightNear)
					/ (double) numberOfQuadsPerFaceStrip;
			double zQuadDif = (zTopLeftFar - zBotRightNear)
					/ (double) numberOfQuadsPerFaceStrip;

			int bufferPosition = 0;

			setFaceVertexAndColourApproximationQualityData(xQuadDif, yQuadDif,
					zQuadDif, xTopLeftFar, yBotRightNear, zBotRightNear,
					Dimension.dy, bufferPosition);
			bufferPosition += getNumberOfQuadsPerFace(levelOfDivision)
					* numberOfDataPerQuad;
			setFaceVertexAndColourApproximationQualityData(xQuadDif, yQuadDif,
					zQuadDif, xTopLeftFar, yTopLeftFar, zBotRightNear,
					Dimension.dy, bufferPosition);
			bufferPosition += getNumberOfQuadsPerFace(levelOfDivision)
					* numberOfDataPerQuad;
			setFaceVertexAndColourApproximationQualityData(xQuadDif, yQuadDif,
					zQuadDif, xTopLeftFar, yBotRightNear, zTopLeftFar,
					Dimension.dz, bufferPosition);
			bufferPosition += getNumberOfQuadsPerFace(levelOfDivision)
					* numberOfDataPerQuad;
			setFaceVertexAndColourApproximationQualityData(xQuadDif, yQuadDif,
					zQuadDif, xTopLeftFar, yBotRightNear, zBotRightNear,
					Dimension.dz, bufferPosition);
			bufferPosition += getNumberOfQuadsPerFace(levelOfDivision)
					* numberOfDataPerQuad;
			setFaceVertexAndColourApproximationQualityData(xQuadDif, yQuadDif,
					zQuadDif, xTopLeftFar, yBotRightNear, zBotRightNear,
					Dimension.dx, bufferPosition);
			bufferPosition += getNumberOfQuadsPerFace(levelOfDivision)
					* numberOfDataPerQuad;
			setFaceVertexAndColourApproximationQualityData(xQuadDif, yQuadDif,
					zQuadDif, xBotRightNear, yBotRightNear, zBotRightNear,
					Dimension.dx, bufferPosition);
			bufferPosition += getNumberOfQuadsPerFace(levelOfDivision)
					* numberOfDataPerQuad;

			bufferPosition = 0;

			setFaceVertexAndColourData(xQuadDif, yQuadDif, zQuadDif,
					xTopLeftFar, yBotRightNear, zBotRightNear, Dimension.dy,
					bufferPosition);
			bufferPosition += getNumberOfQuadsPerFace(levelOfDivision)
					* numberOfDataPerQuad;
			setFaceVertexAndColourData(xQuadDif, yQuadDif, zQuadDif,
					xTopLeftFar, yTopLeftFar, zBotRightNear, Dimension.dy,
					bufferPosition);
			bufferPosition += getNumberOfQuadsPerFace(levelOfDivision)
					* numberOfDataPerQuad;
			setFaceVertexAndColourData(xQuadDif, yQuadDif, zQuadDif,
					xTopLeftFar, yBotRightNear, zTopLeftFar, Dimension.dz,
					bufferPosition);
			bufferPosition += getNumberOfQuadsPerFace(levelOfDivision)
					* numberOfDataPerQuad;
			setFaceVertexAndColourData(xQuadDif, yQuadDif, zQuadDif,
					xTopLeftFar, yBotRightNear, zBotRightNear, Dimension.dz,
					bufferPosition);
			bufferPosition += getNumberOfQuadsPerFace(levelOfDivision)
					* numberOfDataPerQuad;
			setFaceVertexAndColourData(xQuadDif, yQuadDif, zQuadDif,
					xTopLeftFar, yBotRightNear, zBotRightNear, Dimension.dx,
					bufferPosition);
			bufferPosition += getNumberOfQuadsPerFace(levelOfDivision)
					* numberOfDataPerQuad;
			setFaceVertexAndColourData(xQuadDif, yQuadDif, zQuadDif,
					xBotRightNear, yBotRightNear, zBotRightNear, Dimension.dx,
					bufferPosition);
			bufferPosition += getNumberOfQuadsPerFace(levelOfDivision)
					* numberOfDataPerQuad;

			graphicalDataComputated = true;

		}

		if (approximationQuality)
			return faceVertexAndColourApproximationQualityData;
		return faceVertexAndColourData;
	}

	private void setFaceVertexAndColourData(double xQuadDif, double yQuadDif,
			double zQuadDif, double x, double y, double z, Dimension dimension,
			int bufferPosition) {

		int numberOfQuadsPerFaceStrip = getNumberOfQuadsPerFaceStrip(levelOfDivision);

		double xCopy = x;
		double yCopy = y;

		for (int i = 0; i < numberOfQuadsPerFaceStrip; i++) {
			for (int j = 0; j < numberOfQuadsPerFaceStrip; j++) {
				faceVertexAndColourData[bufferPosition++] = (x - xBalance)
						/ scalingWidth;
				faceVertexAndColourData[bufferPosition++] = (y - yBalance)
						/ scalingWidth;
				faceVertexAndColourData[bufferPosition++] = (z - zBalance)
						/ scalingWidth;
				faceVertexAndColourData[bufferPosition++] = computeValue(x, y,
						z);

				switch (dimension) {
				case dy:
				case dz:
					x += xQuadDif;
					break;
				case dx:
					y += yQuadDif;
					break;
				}

				faceVertexAndColourData[bufferPosition++] = (x - xBalance)
						/ scalingWidth;
				faceVertexAndColourData[bufferPosition++] = (y - yBalance)
						/ scalingWidth;
				faceVertexAndColourData[bufferPosition++] = (z - zBalance)
						/ scalingWidth;
				faceVertexAndColourData[bufferPosition++] = computeValue(x, y,
						z);

				switch (dimension) {
				case dz:
					y += yQuadDif;
					break;
				case dx:
				case dy:
					z += zQuadDif;
					break;
				}
				faceVertexAndColourData[bufferPosition++] = (x - xBalance)
						/ scalingWidth;
				faceVertexAndColourData[bufferPosition++] = (y - yBalance)
						/ scalingWidth;
				faceVertexAndColourData[bufferPosition++] = (z - zBalance)
						/ scalingWidth;
				faceVertexAndColourData[bufferPosition++] = computeValue(x, y,
						z);

				switch (dimension) {
				case dy:
				case dz:
					x -= xQuadDif;
					break;
				case dx:
					y -= yQuadDif;
					break;
				}
				faceVertexAndColourData[bufferPosition++] = (x - xBalance)
						/ scalingWidth;
				faceVertexAndColourData[bufferPosition++] = (y - yBalance)
						/ scalingWidth;
				faceVertexAndColourData[bufferPosition++] = (z - zBalance)
						/ scalingWidth;
				faceVertexAndColourData[bufferPosition++] = computeValue(x, y,
						z);
				switch (dimension) {
				case dz:
					x += xQuadDif;
					y -= yQuadDif;
					break;
				case dx:
					y += yQuadDif;
					z -= zQuadDif;
					break;
				case dy:
					x += xQuadDif;
					z -= zQuadDif;
					break;
				}

			}
			switch (dimension) {
			case dz:
				x = xCopy;
				y += yQuadDif;
				break;
			case dy:
				x = xCopy;
				z += zQuadDif;
				break;
			case dx:
				y = yCopy;
				z += zQuadDif;
				break;
			}

		}

	}

	private void setFaceVertexAndColourApproximationQualityData(
			double xQuadDif, double yQuadDif, double zQuadDif, double x,
			double y, double z, Dimension dimension, int bufferPosition) {

		int numberOfQuadsPerFaceStrip = getNumberOfQuadsPerFaceStrip(levelOfDivision);

		double xCopy = x;
		double yCopy = y;

		double functionValue;

		for (int i = 0; i < numberOfQuadsPerFaceStrip; i++) {
			for (int j = 0; j < numberOfQuadsPerFaceStrip; j++) {
				faceVertexAndColourApproximationQualityData[bufferPosition++] = (x - xBalance)
						/ scalingWidth;
				faceVertexAndColourApproximationQualityData[bufferPosition++] = (y - yBalance)
						/ scalingWidth;
				faceVertexAndColourApproximationQualityData[bufferPosition++] = (z - zBalance)
						/ scalingWidth;
				functionValue = interpolatedFunction.computeValue(x, y, z);
				faceVertexAndColourApproximationQualityData[bufferPosition++] = Math
						.abs((computeValue(x, y, z) - functionValue)
								/ functionValue);

				switch (dimension) {
				case dy:
				case dz:
					x += xQuadDif;
					break;
				case dx:
					y += yQuadDif;
					break;
				}

				faceVertexAndColourApproximationQualityData[bufferPosition++] = (x - xBalance)
						/ scalingWidth;
				faceVertexAndColourApproximationQualityData[bufferPosition++] = (y - yBalance)
						/ scalingWidth;
				faceVertexAndColourApproximationQualityData[bufferPosition++] = (z - zBalance)
						/ scalingWidth;
				functionValue = interpolatedFunction.computeValue(x, y, z);
				faceVertexAndColourApproximationQualityData[bufferPosition++] = Math
						.abs((computeValue(x, y, z) - functionValue)
								/ functionValue);

				switch (dimension) {
				case dz:
					y += yQuadDif;
					break;
				case dx:
				case dy:
					z += zQuadDif;
					break;
				}
				faceVertexAndColourApproximationQualityData[bufferPosition++] = (x - xBalance)
						/ scalingWidth;
				faceVertexAndColourApproximationQualityData[bufferPosition++] = (y - yBalance)
						/ scalingWidth;
				faceVertexAndColourApproximationQualityData[bufferPosition++] = (z - zBalance)
						/ scalingWidth;
				functionValue = interpolatedFunction.computeValue(x, y, z);
				faceVertexAndColourApproximationQualityData[bufferPosition++] = Math
						.abs((computeValue(x, y, z) - functionValue)
								/ functionValue);

				switch (dimension) {
				case dy:
				case dz:
					x -= xQuadDif;
					break;
				case dx:
					y -= yQuadDif;
					break;
				}
				faceVertexAndColourApproximationQualityData[bufferPosition++] = (x - xBalance)
						/ scalingWidth;
				faceVertexAndColourApproximationQualityData[bufferPosition++] = (y - yBalance)
						/ scalingWidth;
				faceVertexAndColourApproximationQualityData[bufferPosition++] = (z - zBalance)
						/ scalingWidth;
				functionValue = interpolatedFunction.computeValue(x, y, z);
				faceVertexAndColourApproximationQualityData[bufferPosition++] = Math
						.abs((computeValue(x, y, z) - functionValue)
								/ functionValue);

				switch (dimension) {
				case dz:
					x += xQuadDif;
					y -= yQuadDif;
					break;
				case dx:
					y += yQuadDif;
					z -= zQuadDif;
					break;
				case dy:
					x += xQuadDif;
					z -= zQuadDif;
					break;
				}

			}
			switch (dimension) {
			case dz:
				x = xCopy;
				y += yQuadDif;
				break;
			case dy:
				x = xCopy;
				z += zQuadDif;
				break;
			case dx:
				y = yCopy;
				z += zQuadDif;
				break;
			}

		}

	}

	/*
	 * public double[] getExactVertexValuesAndCoordinates(){
	 * if(!exactVertexDataComputated){
	 * 
	 * 
	 * 
	 * 
	 * exactVertexDataComputated = true; int bufferPosition = 0;
	 * 
	 * setVertexData(xBotRightNear, yBotRightNear, zBotRightNear,
	 * bufferPosition); bufferPosition+=7; setVertexData(xTopLeftFar,
	 * yBotRightNear, zBotRightNear, bufferPosition); bufferPosition+=7;
	 * setVertexData(xBotRightNear, yTopLeftFar, zBotRightNear, bufferPosition);
	 * bufferPosition+=7; setVertexData(xTopLeftFar, yTopLeftFar, zBotRightNear,
	 * bufferPosition); bufferPosition+=7; setVertexData(xBotRightNear,
	 * yBotRightNear, zTopLeftFar, bufferPosition); bufferPosition+=7;
	 * setVertexData(xTopLeftFar, yBotRightNear, zTopLeftFar, bufferPosition);
	 * bufferPosition+=7; setVertexData(xBotRightNear, yTopLeftFar, zTopLeftFar,
	 * bufferPosition); bufferPosition+=7; setVertexData(xTopLeftFar,
	 * yTopLeftFar, zTopLeftFar, bufferPosition); bufferPosition+=7;
	 * 
	 * }
	 * 
	 * 
	 * return exactVertexValuesAndCoordinates;
	 * 
	 * }
	 */

	/*
	 * private void setVertexData(double x, double y, double z, int
	 * bufferPosition){
	 * 
	 * 
	 * exactVertexValuesAndCoordinates[bufferPosition++] = (x -
	 * xBalance)/scalingWidth; exactVertexValuesAndCoordinates[bufferPosition++]
	 * = (y - yBalance)/scalingWidth;
	 * exactVertexValuesAndCoordinates[bufferPosition++] = (z -
	 * zBalance)/scalingWidth;
	 * 
	 * exactVertexValuesAndCoordinates[bufferPosition++] = x;
	 * exactVertexValuesAndCoordinates[bufferPosition++] = y;
	 * exactVertexValuesAndCoordinates[bufferPosition++] = z;
	 * 
	 * exactVertexValuesAndCoordinates[bufferPosition++] = computeValue(x, y,
	 * z);
	 * 
	 * }
	 */

}
