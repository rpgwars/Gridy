package pbi.computations.part;

import java.util.List;
import java.util.ArrayList;
import java.io.PrintWriter; 

import pbi.computations.data.InterpolatedFunction;

public class ReferenceCube extends DrawableCube {


	private static PrintWriter centralPoints = null;
	static { /*
		try {
			centralPoints = new PrintWriter("central.txt", "UTF-8");
		} catch (Exception e) {
		}*/
	}
	
	public static void write(int size){
		//centralPoints.println("Ilosc elementow siatki: " + size); 
	}
	
	public static void close(){
		//centralPoints.close();
	}


	private List<ReferenceCube> leftNeighbours;
	private List<ReferenceCube> rightNeighbours;
	private List<ReferenceCube> botNeighbours;
	private List<ReferenceCube> topNeighbours;
	private List<ReferenceCube> nearNeighbours;
	private List<ReferenceCube> farNeighbours;

	public ReferenceCube(double xTopLeftFar, double yTopLeftFar,
			double zTopLeftFar, double xBotRightNear, double yBotRightNear,
			double zBotRightNear, int levelOfDivision, CubePosition cubePosition) {
		this(xTopLeftFar, yTopLeftFar, zTopLeftFar, xBotRightNear,
				yBotRightNear, zBotRightNear, levelOfDivision, null,
				cubePosition);
	}

	public ReferenceCube(double xTopLeftFar, double yTopLeftFar,
			double zTopLeftFar, double xBotRightNear, double yBotRightNear,
			double zBotRightNear, int levelOfDivision,
			double[] lastCoordinates, CubePosition cubePosition) {
		super(xTopLeftFar, yTopLeftFar, zTopLeftFar, xBotRightNear,
				yBotRightNear, zBotRightNear, levelOfDivision, lastCoordinates,
				cubePosition);
		leftNeighbours = null;
		rightNeighbours = null;
		botNeighbours = null;
		topNeighbours = null;
		nearNeighbours = null;
		farNeighbours = null;

	}

	public List<ReferenceCube> divide() {
		
		List<ReferenceCube> list = new ArrayList<ReferenceCube>();
		double dx = (xBotRightNear - xTopLeftFar) / 2.0;
		double dy = (yTopLeftFar - yBotRightNear) / 2.0;
		double dz = (zTopLeftFar - zBotRightNear) / 2.0;
		//centralPoints.println((xTopLeftFar + dx) + " " + (yBotRightNear + dy) + " " + (zBotRightNear + dz)); 
		ReferenceCube c1 = new ReferenceCube(xTopLeftFar, yTopLeftFar,
				zTopLeftFar, xBotRightNear - dx, yBotRightNear + dy,
				zBotRightNear + dz, levelOfDivision + 1, currentCoordinates,
				CubePosition.TOP_LEFT_FAR);
		ReferenceCube c2 = new ReferenceCube(xTopLeftFar + dx, yTopLeftFar,
				zTopLeftFar, xBotRightNear, yBotRightNear + dy, zBotRightNear
						+ dz, levelOfDivision + 1, currentCoordinates,
				CubePosition.TOP_RIGHT_FAR);
		ReferenceCube c3 = new ReferenceCube(xTopLeftFar, yTopLeftFar - dy,
				zTopLeftFar, xBotRightNear - dx, yBotRightNear, zBotRightNear
						+ dz, levelOfDivision + 1, currentCoordinates,
				CubePosition.TOP_LEFT_NEAR);
		ReferenceCube c4 = new ReferenceCube(xTopLeftFar + dx,
				yTopLeftFar - dy, zTopLeftFar, xBotRightNear, yBotRightNear,
				zBotRightNear + dz, levelOfDivision + 1, currentCoordinates,
				CubePosition.TOP_RIGHT_NEAR);
		ReferenceCube c5 = new ReferenceCube(xTopLeftFar, yTopLeftFar,
				zTopLeftFar - dz, xBotRightNear - dx, yBotRightNear + dy,
				zBotRightNear, levelOfDivision + 1, currentCoordinates,
				CubePosition.BOT_LEFT_FAR);
		ReferenceCube c6 = new ReferenceCube(xTopLeftFar + dx, yTopLeftFar,
				zTopLeftFar - dz, xBotRightNear, yBotRightNear + dy,
				zBotRightNear, levelOfDivision + 1, currentCoordinates,
				CubePosition.BOT_RIGHT_FAR);
		ReferenceCube c7 = new ReferenceCube(xTopLeftFar, yTopLeftFar - dy,
				zTopLeftFar - dz, xBotRightNear - dx, yBotRightNear,
				zBotRightNear, levelOfDivision + 1, currentCoordinates,
				CubePosition.BOT_LEFT_NEAR);
		ReferenceCube c8 = new ReferenceCube(xTopLeftFar + dx,
				yTopLeftFar - dy, zTopLeftFar - dz, xBotRightNear,
				yBotRightNear, zBotRightNear, levelOfDivision + 1,
				currentCoordinates, CubePosition.BOT_RIGHT_NEAR);
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
	public int hashCode() {

		double[] coord = this.getCooridinate();

		return (int) (coord[0] * 1000 + coord[1] * 100 + coord[2] * 10);
	}

	@Override
	public boolean equals(Object o) {
		return super.equals(o);
	}

	public List<ReferenceCube> getLeftNeighbours() {
		return leftNeighbours;
	}

	public void setLeftNeighbours(List<ReferenceCube> leftNeighbours) {
		this.leftNeighbours = leftNeighbours;
	}

	public List<ReferenceCube> getRightNeighbours() {
		return rightNeighbours;
	}

	public void setRightNeighbours(List<ReferenceCube> rightNeighbours) {
		this.rightNeighbours = rightNeighbours;
	}

	public List<ReferenceCube> getBotNeighbours() {
		return botNeighbours;
	}

	public void setBotNeighbours(List<ReferenceCube> botNeighbours) {
		this.botNeighbours = botNeighbours;
	}

	public List<ReferenceCube> getTopNeighbours() {
		return topNeighbours;
	}

	public void setTopNeighbours(List<ReferenceCube> topNeighbours) {
		this.topNeighbours = topNeighbours;
	}

	public List<ReferenceCube> getNearNeighbours() {
		return nearNeighbours;
	}

	public void setNearNeighbours(List<ReferenceCube> nearNeighbours) {
		this.nearNeighbours = nearNeighbours;
	}

	public List<ReferenceCube> getFarNeighbours() {
		return farNeighbours;
	}

	public void setFarNeighbours(List<ReferenceCube> farNeighbours) {
		this.farNeighbours = farNeighbours;
	}

	@Override
	public void setComputationCubes(InterpolatedFunction interpolatedFunction) {

		super.setComputationCubes(interpolatedFunction);
		ReferenceCube cube;

		if (leftNeighbours != null) {

			cube = leftNeighbours.get(0);
			if (cube.getLevelOfDivision() < levelOfDivision) {
				leftBotEdge = false;
				leftTopEdge = false;
				leftFarEdge = false;
				leftNearEdge = false;
				leftFace = false;

				leftBotNearPoint = false;
				leftTopNearPoint = false;
				leftTopFarPoint = false;
				leftBotFarPoint = false;

				leftAdjacentCube = cube;
				botLeftAdjacentCube = cube;
				topLeftAdjacentCube = cube;
				leftNearAdjacentCube = cube;
				leftFarAdjacentCube = cube;

			} else {
				if (cube.getLevelOfDivision() == levelOfDivision) {
					if (cube.getTopNeighbours() != null
							&& cube.getTopNeighbours().get(0)
									.getLevelOfDivision() < levelOfDivision) {
						leftTopEdge = false;
						leftTopNearPoint = false;
						leftTopFarPoint = false;

						topLeftAdjacentCube = cube.getTopNeighbours().get(0);
					}
					if (cube.getBotNeighbours() != null
							&& cube.getBotNeighbours().get(0)
									.getLevelOfDivision() < levelOfDivision) {
						leftBotEdge = false;
						leftBotNearPoint = false;
						leftBotFarPoint = false;

						botLeftAdjacentCube = cube.getBotNeighbours().get(0);
					}
					if (cube.getFarNeighbours() != null
							&& cube.getFarNeighbours().get(0)
									.getLevelOfDivision() < levelOfDivision) {
						leftFarEdge = false;
						leftBotFarPoint = false;
						leftTopFarPoint = false;

						leftFarAdjacentCube = cube.getFarNeighbours().get(0);
					}
					if (cube.getNearNeighbours() != null
							&& cube.getNearNeighbours().get(0)
									.getLevelOfDivision() < levelOfDivision) {
						leftNearEdge = false;
						leftBotNearPoint = false;
						leftTopNearPoint = false;

						leftNearAdjacentCube = cube.getNearNeighbours().get(0);
					}
				}
			}

		}

		if (rightNeighbours != null) {

			cube = rightNeighbours.get(0);
			if (cube.getLevelOfDivision() < levelOfDivision) {
				rightBotEdge = false;
				rightTopEdge = false;
				rightFarEdge = false;
				rightNearEdge = false;
				rightFace = false;

				rightBotNearPoint = false;
				rightTopNearPoint = false;
				rightTopFarPoint = false;
				rightBotFarPoint = false;

				rightAdjacentCube = cube;
				botRightAdjacentCube = cube;
				topRightAdjacentCube = cube;
				rightNearAdjacentCube = cube;
				rightFarAdjacentCube = cube;

			} else {
				if (cube.getLevelOfDivision() == levelOfDivision) {
					if (cube.getTopNeighbours() != null
							&& cube.getTopNeighbours().get(0)
									.getLevelOfDivision() < levelOfDivision) {
						rightTopEdge = false;
						rightTopNearPoint = false;
						rightTopFarPoint = false;

						topRightAdjacentCube = cube.getTopNeighbours().get(0);
					}
					if (cube.getBotNeighbours() != null
							&& cube.getBotNeighbours().get(0)
									.getLevelOfDivision() < levelOfDivision) {
						rightBotEdge = false;
						rightBotNearPoint = false;
						rightBotFarPoint = false;

						botRightAdjacentCube = cube.getBotNeighbours().get(0);
					}
					if (cube.getFarNeighbours() != null
							&& cube.getFarNeighbours().get(0)
									.getLevelOfDivision() < levelOfDivision) {
						rightFarEdge = false;
						rightBotFarPoint = false;
						rightTopFarPoint = false;

						rightFarAdjacentCube = cube.getFarNeighbours().get(0);
					}
					if (cube.getNearNeighbours() != null
							&& cube.getNearNeighbours().get(0)
									.getLevelOfDivision() < levelOfDivision) {
						rightNearEdge = false;
						rightBotNearPoint = false;
						rightTopNearPoint = false;

						rightNearAdjacentCube = cube.getNearNeighbours().get(0);
					}
				}
			}

		}

		if (topNeighbours != null) {

			cube = topNeighbours.get(0);
			if (cube.getLevelOfDivision() < levelOfDivision) {
				topNearEdge = false;
				topFarEdge = false;
				leftTopEdge = false;
				rightTopEdge = false;
				topFace = false;

				leftTopNearPoint = false;
				rightTopNearPoint = false;
				leftTopFarPoint = false;
				rightTopFarPoint = false;

				topAdjacentCube = cube;
				topFarAdjacentCube = cube;
				topNearAdjacentCube = cube;
				topLeftAdjacentCube = cube;
				topFarAdjacentCube = cube;

			} else {
				if (cube.getLevelOfDivision() == levelOfDivision) {
					if (cube.getFarNeighbours() != null
							&& cube.getFarNeighbours().get(0)
									.getLevelOfDivision() < levelOfDivision) {
						topFarEdge = false;
						leftTopFarPoint = false;
						rightTopFarPoint = false;

						topFarAdjacentCube = cube.getFarNeighbours().get(0);
					}
					if (cube.getNearNeighbours() != null
							&& cube.getNearNeighbours().get(0)
									.getLevelOfDivision() < levelOfDivision) {
						topNearEdge = false;
						leftTopNearPoint = false;
						rightTopNearPoint = false;

						topNearAdjacentCube = cube.getNearNeighbours().get(0);
					}

				}
			}

		}

		if (botNeighbours != null) {

			cube = botNeighbours.get(0);
			if (cube.getLevelOfDivision() < levelOfDivision) {
				botNearEdge = false;
				botFarEdge = false;
				leftBotEdge = false;
				rightBotEdge = false;
				botFace = false;

				leftBotNearPoint = false;
				rightBotNearPoint = false;
				leftBotFarPoint = false;
				rightBotFarPoint = false;

				botAdjacentCube = cube;
				botNearAdjacentCube = cube;
				botFarAdjacentCube = cube;
				botLeftAdjacentCube = cube;
				botRightAdjacentCube = cube;

			} else {
				if (cube.getLevelOfDivision() == levelOfDivision) {
					if (cube.getFarNeighbours() != null
							&& cube.getFarNeighbours().get(0)
									.getLevelOfDivision() < levelOfDivision) {
						botFarEdge = false;
						leftBotFarPoint = false;
						rightBotFarPoint = false;

						botFarAdjacentCube = cube.getFarNeighbours().get(0);
					}
					if (cube.getNearNeighbours() != null
							&& cube.getNearNeighbours().get(0)
									.getLevelOfDivision() < levelOfDivision) {
						botNearEdge = false;
						leftBotNearPoint = false;
						rightBotNearPoint = false;

						botNearAdjacentCube = cube.getNearNeighbours().get(0);
					}

				}
			}

		}

		if (nearNeighbours != null) {

			cube = nearNeighbours.get(0);
			if (cube.getLevelOfDivision() < levelOfDivision) {
				leftNearEdge = false;
				rightNearEdge = false;
				topNearEdge = false;
				botNearEdge = false;
				nearFace = false;

				leftBotNearPoint = false;
				rightBotNearPoint = false;
				leftTopNearPoint = false;
				rightTopNearPoint = false;

				nearAdjacentCube = cube;
				botNearAdjacentCube = cube;
				topNearAdjacentCube = cube;
				leftNearAdjacentCube = cube;
				rightNearAdjacentCube = cube;

			}

		}

		if (farNeighbours != null) {

			cube = farNeighbours.get(0);
			if (cube.getLevelOfDivision() < levelOfDivision) {
				leftFarEdge = false;
				rightFarEdge = false;
				topFarEdge = false;
				botFarEdge = false;
				farFace = false;

				leftBotFarPoint = false;
				rightBotFarPoint = false;
				leftTopFarPoint = false;
				rightTopFarPoint = false;

				farAdjacentCube = cube;
				botFarAdjacentCube = cube;
				topFarAdjacentCube = cube;
				leftFarAdjacentCube = cube;
				rightFarAdjacentCube = cube;

			}

		}

	}

}
