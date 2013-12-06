package pbi.computations.part;

import java.util.List;
import java.util.ArrayList;

import pbi.computations.data.InterpolatedFunction;

public class HashMapCube extends ComputationCube {

	/*
	 * private Edge topLeftEdge; private Edge topRightEdge; private Edge
	 * topFarEdge; private Edge topNearEdge; private Edge botLeftEdge; private
	 * Edge botRightEdge; private Edge botFarEdge; private Edge botNearEdge;
	 */

	public HashMapCube(double xTopLeftFar, double yTopLeftFar,
			double zTopLeftFar, double xBotRightNear, double yBotRightNear,
			double zBotRightNear, int levelOfDivision, CubePosition cubePosition) {

		super(xTopLeftFar, yTopLeftFar, zTopLeftFar, xBotRightNear,
				yBotRightNear, zBotRightNear, levelOfDivision, null,
				cubePosition);

	}

	public HashMapCube(double xTopLeftFar, double yTopLeftFar,
			double zTopLeftFar, double xBotRightNear, double yBotRightNear,
			double zBotRightNear, int levelOfDivision,
			double[] lastCoordinates, CubePosition cubePosition) {

		super(xTopLeftFar, yTopLeftFar, zTopLeftFar, xBotRightNear,
				yBotRightNear, zBotRightNear, levelOfDivision, lastCoordinates,
				cubePosition);

	}

	@Override
	public boolean equals(Object o) {
		return super.equals(o);
	}

	public List<HashMapCube> divide() {

		List<HashMapCube> list = new ArrayList<HashMapCube>();
		double dx = (xBotRightNear - xTopLeftFar) / 2.0;
		double dy = (yTopLeftFar - yBotRightNear) / 2.0;
		double dz = (zTopLeftFar - zBotRightNear) / 2.0;
		HashMapCube c1 = new HashMapCube(xTopLeftFar, yTopLeftFar, zTopLeftFar,
				xBotRightNear - dx, yBotRightNear + dy, zBotRightNear + dz,
				levelOfDivision + 1, currentCoordinates,
				CubePosition.TOP_LEFT_FAR);
		HashMapCube c2 = new HashMapCube(xTopLeftFar + dx, yTopLeftFar,
				zTopLeftFar, xBotRightNear, yBotRightNear + dy, zBotRightNear
						+ dz, levelOfDivision + 1, currentCoordinates,
				CubePosition.TOP_RIGHT_FAR);
		HashMapCube c3 = new HashMapCube(xTopLeftFar, yTopLeftFar - dy,
				zTopLeftFar, xBotRightNear - dx, yBotRightNear, zBotRightNear
						+ dz, levelOfDivision + 1, currentCoordinates,
				CubePosition.TOP_LEFT_NEAR);
		HashMapCube c4 = new HashMapCube(xTopLeftFar + dx, yTopLeftFar - dy,
				zTopLeftFar, xBotRightNear, yBotRightNear, zBotRightNear + dz,
				levelOfDivision + 1, currentCoordinates,
				CubePosition.TOP_RIGHT_NEAR);
		HashMapCube c5 = new HashMapCube(xTopLeftFar, yTopLeftFar, zTopLeftFar
				- dz, xBotRightNear - dx, yBotRightNear + dy, zBotRightNear,
				levelOfDivision + 1, currentCoordinates,
				CubePosition.BOT_LEFT_FAR);
		HashMapCube c6 = new HashMapCube(xTopLeftFar + dx, yTopLeftFar,
				zTopLeftFar - dz, xBotRightNear, yBotRightNear + dy,
				zBotRightNear, levelOfDivision + 1, currentCoordinates,
				CubePosition.BOT_RIGHT_FAR);
		HashMapCube c7 = new HashMapCube(xTopLeftFar, yTopLeftFar - dy,
				zTopLeftFar - dz, xBotRightNear - dx, yBotRightNear,
				zBotRightNear, levelOfDivision + 1, currentCoordinates,
				CubePosition.BOT_LEFT_NEAR);
		HashMapCube c8 = new HashMapCube(xTopLeftFar + dx, yTopLeftFar - dy,
				zTopLeftFar - dz, xBotRightNear, yBotRightNear, zBotRightNear,
				levelOfDivision + 1, currentCoordinates,
				CubePosition.BOT_RIGHT_NEAR);
		list.add(c1);
		list.add(c2);
		list.add(c3);
		list.add(c4);
		list.add(c5);
		list.add(c6);
		list.add(c7);
		list.add(c8);
		return list;
	}

	@Override
	public void setComputationCubes(InterpolatedFunction interpolatedFunction) {

	}

}
