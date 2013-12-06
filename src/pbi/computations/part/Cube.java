package pbi.computations.part;

public abstract class Cube implements Comparable<Cube> {

	protected double xTopLeftFar;
	protected double yTopLeftFar;
	protected double zTopLeftFar;

	protected double xBotRightNear;
	protected double yBotRightNear;
	protected double zBotRightNear;

	protected double[] currentCoordinates;
	protected double[] lastCoordinates;

	protected double[] cooficients;
	protected CubePosition cubePosition;

	protected int levelOfDivision;

	protected Cube leftAdjacentCube = null;
	protected Cube rightAdjacentCube = null;
	protected Cube nearAdjacentCube = null;
	protected Cube farAdjacentCube = null;
	protected Cube topAdjacentCube = null;
	protected Cube botAdjacentCube = null;

	protected Cube botNearAdjacentCube = null;
	protected Cube botRightAdjacentCube = null;
	protected Cube botFarAdjacentCube = null;
	protected Cube botLeftAdjacentCube = null;
	protected Cube topNearAdjacentCube = null;
	protected Cube topRightAdjacentCube = null;
	protected Cube topFarAdjacentCube = null;
	protected Cube topLeftAdjacentCube = null;
	protected Cube leftNearAdjacentCube = null;
	protected Cube rightNearAdjacentCube = null;
	protected Cube rightFarAdjacentCube = null;
	protected Cube leftFarAdjacentCube = null;

	public Cube(double xTopLeftFar, double yTopLeftFar, double zTopLeftFar,
			double xBotRightNear, double yBotRightNear, double zBotRightNear,
			int levelOfDivision, double[] lastCoordinates,
			CubePosition cubePosition) {
		this.xBotRightNear = xBotRightNear;
		this.yBotRightNear = yBotRightNear;
		this.zBotRightNear = zBotRightNear;
		this.xTopLeftFar = xTopLeftFar;
		this.yTopLeftFar = yTopLeftFar;
		this.zTopLeftFar = zTopLeftFar;
		this.levelOfDivision = levelOfDivision;
		this.lastCoordinates = lastCoordinates;

		currentCoordinates = new double[6];
		currentCoordinates[0] = xTopLeftFar;
		currentCoordinates[1] = yTopLeftFar;
		currentCoordinates[2] = zTopLeftFar;
		currentCoordinates[3] = xBotRightNear;
		currentCoordinates[4] = yBotRightNear;
		currentCoordinates[5] = zBotRightNear;
		this.cubePosition = cubePosition;

	}

	public double[] getCooficients() {
		return cooficients;
	}

	public void printCube() {
		System.out.println("wspolrzedne gornego lewego dalekiego "
				+ xTopLeftFar + " " + yTopLeftFar + " " + zTopLeftFar
				+ "poziom " + levelOfDivision);
		System.out.println("wspolrzedne dolnego prawego bliskiego "
				+ xBotRightNear + " " + yBotRightNear + " " + zBotRightNear
				+ "poziom " + levelOfDivision);

	}

	public int getLevelOfDivision() {
		return levelOfDivision;
	}

	public double[] getCooridinate() {
		return currentCoordinates;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Cube))
			return false;

		Cube c = (Cube) o;
		if (c.getLevelOfDivision() == levelOfDivision) {
			double e = 0.0005;
			double[] d1 = this.getCooridinate();
			double[] d2 = c.getCooridinate();
			for (int i = 0; i < 3; i++) {

				if (Math.abs(d1[i] - d2[i]) > e)
					return false;

			}

			return true;
		}
		return false;

	}

	public int compareTo(Cube rc) {
		int level = rc.getLevelOfDivision();
		if (levelOfDivision < level)
			return -1;
		if (levelOfDivision == level)
			return 0;
		return 1;

	}

}
