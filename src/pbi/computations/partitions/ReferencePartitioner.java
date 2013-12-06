package pbi.computations.partitions;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import pbi.computations.data.InterpolatedFunction;
import pbi.computations.part.DrawableCube;
import pbi.computations.part.ReferenceCube;

public class ReferencePartitioner {

	private List<ReferenceCube> cubeList;
	private List<ReferenceCube> tmp;
	private List<ReferenceCube> uncomputedCubes;
	private List<ReferenceCube> cubesToAdapt;

	public ReferencePartitioner(ReferenceCube initialCube) {

		cubeList = new ArrayList<ReferenceCube>();
		uncomputedCubes = null;
		cubesToAdapt = null;
		cubeList.add(initialCube);

	}

	public List<ReferenceCube> adaptMesh(
			InterpolatedFunction interpolatedFunction) {

		if (uncomputedCubes == null) {
			uncomputedCubes = new ArrayList<ReferenceCube>();
			cubesToAdapt = new ArrayList<ReferenceCube>();
			cubesToAdapt.add(cubeList.get(0));
		} else {

			for (ReferenceCube rc : cubesToAdapt) {
				if (!rc.isErrKLowEnough())
					this.divide(rc);

			}
			cubesToAdapt.clear();
			List<ReferenceCube> tmp = uncomputedCubes;
			uncomputedCubes = cubesToAdapt;
			cubesToAdapt = tmp;

		}

		Collections.sort(cubesToAdapt);
		for (ReferenceCube rc : cubesToAdapt) {
			rc.setComputationCubes(interpolatedFunction);
		}
		return cubesToAdapt;

	}

	public List<? extends DrawableCube> startComputation(
			InterpolatedFunction function) {
		Collections.sort(cubeList);
		for (ReferenceCube rc : cubeList) {
			rc.setComputationCubes(function);
			rc.computeCooficients(null);
		}

		return (List<? extends DrawableCube>) cubeList;

	}

	private void selectCubesToDivide(ReferenceCube rc) {

		tmp = new ArrayList<ReferenceCube>();
		tmp.add(rc);
		select(rc);
		Collections.sort(tmp);

	}

	private void addAndSelectCube(ReferenceCube rc, List<ReferenceCube> list) {

		ReferenceCube cube;
		if (list != null) {

			cube = list.get(0);

			if (cube.getLevelOfDivision() < rc.getLevelOfDivision()
					&& !tmp.contains(cube)) {
				tmp.add(cube);

				select(cube);

			}
		}
	}

	private void select(ReferenceCube rc) {
		List<ReferenceCube> list;
		// ReferenceCube cube;

		// select cubes to divide from cubes that share face
		list = rc.getLeftNeighbours();
		addAndSelectCube(rc, list);

		list = rc.getRightNeighbours();
		addAndSelectCube(rc, list);

		list = rc.getTopNeighbours();
		addAndSelectCube(rc, list);

		list = rc.getBotNeighbours();
		addAndSelectCube(rc, list);

		list = rc.getNearNeighbours();
		addAndSelectCube(rc, list);

		list = rc.getFarNeighbours();
		addAndSelectCube(rc, list);

		// select cubes to divide from cubes that share edge
		List<ReferenceCube> leftNeighbours = rc.getLeftNeighbours();
		List<ReferenceCube> rightNeighbours = rc.getRightNeighbours();
		List<ReferenceCube> topNeighbours = rc.getTopNeighbours();
		List<ReferenceCube> botNeighbours = rc.getBotNeighbours();

		// edges on the left face
		if (leftNeighbours != null) {
			ReferenceCube leftBigOrEqualNeighbour = leftNeighbours.get(0);

			// equal
			if (leftBigOrEqualNeighbour.getLevelOfDivision() == rc
					.getLevelOfDivision()) {

				list = leftBigOrEqualNeighbour.getFarNeighbours();
				addAndSelectCube(rc, list);

				list = leftBigOrEqualNeighbour.getBotNeighbours();
				addAndSelectCube(rc, list);

				list = leftBigOrEqualNeighbour.getNearNeighbours();
				addAndSelectCube(rc, list);

				list = leftBigOrEqualNeighbour.getTopNeighbours();
				addAndSelectCube(rc, list);

			}
			// big
			if (leftBigOrEqualNeighbour.getLevelOfDivision() < rc
					.getLevelOfDivision()) {

				if (leftBigOrEqualNeighbour.getRightNeighbours().get(0) == rc) {

					list = leftBigOrEqualNeighbour.getTopNeighbours();
					addAndSelectCube(rc, list);
					list = leftBigOrEqualNeighbour.getFarNeighbours();
					addAndSelectCube(rc, list);

				}

				else if (leftBigOrEqualNeighbour.getRightNeighbours().get(1) == rc) {

					list = leftBigOrEqualNeighbour.getTopNeighbours();
					addAndSelectCube(rc, list);
					list = leftBigOrEqualNeighbour.getNearNeighbours();
					addAndSelectCube(rc, list);

				}

				else if (leftBigOrEqualNeighbour.getRightNeighbours().get(2) == rc) {

					list = leftBigOrEqualNeighbour.getBotNeighbours();
					addAndSelectCube(rc, list);
					list = leftBigOrEqualNeighbour.getFarNeighbours();
					addAndSelectCube(rc, list);

				}

				else {

					list = leftBigOrEqualNeighbour.getBotNeighbours();
					addAndSelectCube(rc, list);
					list = leftBigOrEqualNeighbour.getNearNeighbours();
					addAndSelectCube(rc, list);

				}

			}

		}

		if (rightNeighbours != null) {
			ReferenceCube rightBigOrEqualNeighbour = rightNeighbours.get(0);

			// equal
			if (rightBigOrEqualNeighbour.getLevelOfDivision() == rc
					.getLevelOfDivision()) {

				list = rightBigOrEqualNeighbour.getFarNeighbours();
				addAndSelectCube(rc, list);

				list = rightBigOrEqualNeighbour.getBotNeighbours();
				addAndSelectCube(rc, list);

				list = rightBigOrEqualNeighbour.getNearNeighbours();
				addAndSelectCube(rc, list);

				list = rightBigOrEqualNeighbour.getTopNeighbours();
				addAndSelectCube(rc, list);

			}

			// big
			if (rightBigOrEqualNeighbour.getLevelOfDivision() < rc
					.getLevelOfDivision()) {

				if (rightBigOrEqualNeighbour.getLeftNeighbours().get(0) == rc) {

					list = rightBigOrEqualNeighbour.getTopNeighbours();
					addAndSelectCube(rc, list);
					list = rightBigOrEqualNeighbour.getFarNeighbours();
					addAndSelectCube(rc, list);

				}

				else if (rightBigOrEqualNeighbour.getLeftNeighbours().get(1) == rc) {

					list = rightBigOrEqualNeighbour.getTopNeighbours();
					addAndSelectCube(rc, list);
					list = rightBigOrEqualNeighbour.getNearNeighbours();
					addAndSelectCube(rc, list);

				}

				else if (rightBigOrEqualNeighbour.getLeftNeighbours().get(2) == rc) {

					list = rightBigOrEqualNeighbour.getBotNeighbours();
					addAndSelectCube(rc, list);
					list = rightBigOrEqualNeighbour.getFarNeighbours();
					addAndSelectCube(rc, list);

				}

				else {

					list = rightBigOrEqualNeighbour.getBotNeighbours();
					addAndSelectCube(rc, list);
					list = rightBigOrEqualNeighbour.getNearNeighbours();
					addAndSelectCube(rc, list);

				}

			}

		}

		if (topNeighbours != null) {
			ReferenceCube topBigOrEqualNeighbour = topNeighbours.get(0);

			// equal
			if (topBigOrEqualNeighbour.getLevelOfDivision() == rc
					.getLevelOfDivision()) {

				list = topBigOrEqualNeighbour.getFarNeighbours();
				addAndSelectCube(rc, list);

				list = topBigOrEqualNeighbour.getNearNeighbours();
				addAndSelectCube(rc, list);

			}

			// big
			if (topBigOrEqualNeighbour.getLevelOfDivision() < rc
					.getLevelOfDivision()) {

				if (topBigOrEqualNeighbour.getBotNeighbours().get(0) == rc
						|| topBigOrEqualNeighbour.getBotNeighbours().get(1) == rc) {

					list = topBigOrEqualNeighbour.getFarNeighbours();
					addAndSelectCube(rc, list);

				}

				else {

					list = topBigOrEqualNeighbour.getNearNeighbours();
					addAndSelectCube(rc, list);
				}

			}

		}

		if (botNeighbours != null) {
			ReferenceCube botBigOrEqualNeighbour = botNeighbours.get(0);

			// equal
			if (botBigOrEqualNeighbour.getLevelOfDivision() == rc
					.getLevelOfDivision()) {

				list = botBigOrEqualNeighbour.getFarNeighbours();
				addAndSelectCube(rc, list);

				list = botBigOrEqualNeighbour.getNearNeighbours();
				addAndSelectCube(rc, list);

			}

			// big
			if (botBigOrEqualNeighbour.getLevelOfDivision() < rc
					.getLevelOfDivision()) {

				if (botBigOrEqualNeighbour.getTopNeighbours().get(0) == rc
						|| botBigOrEqualNeighbour.getTopNeighbours().get(1) == rc) {

					list = botBigOrEqualNeighbour.getFarNeighbours();
					addAndSelectCube(rc, list);

				}

				else {

					list = botBigOrEqualNeighbour.getNearNeighbours();
					addAndSelectCube(rc, list);
				}

			}

		}

	}

	public void divide(ReferenceCube cube) {

		selectCubesToDivide(cube);

		for (ReferenceCube rc : tmp) {

			List<ReferenceCube> list = rc.divide();

			setInternalNeighbours(list);
			manageLeftFace(list, rc);
			manageRightFace(list, rc);
			manageBotFace(list, rc);
			manageTopFace(list, rc);
			manageFarFace(list, rc);
			manageNearFace(list, rc);

			cubeList.remove(rc);
			cubeList.addAll(list);
			if (uncomputedCubes != null)
				uncomputedCubes.addAll(list);

		}

		tmp = null;

	}

	public ReferenceCube findCube(double x, double y, double z) {
		for (ReferenceCube rc : cubeList) {
			double[] coordinates = rc.getCooridinate();
			if (coordinates[0] == x && coordinates[1] == y
					&& coordinates[2] == z)
				return rc;
		}
		return null;
	}

	public List<ReferenceCube> getCubes() {
		return cubeList;
	}

	private void manageLeftFace(List<ReferenceCube> internallyCorrectList,
			ReferenceCube oldCube) {

		if (oldCube.getLeftNeighbours() == null)
			return;
		ReferenceCube topLeftFar = internallyCorrectList.get(0);
		ReferenceCube topLeftNear = internallyCorrectList.get(2);
		ReferenceCube botLeftFar = internallyCorrectList.get(4);
		ReferenceCube botLeftNear = internallyCorrectList.get(6);

		List<ReferenceCube> oldCubeNeighbours = oldCube.getLeftNeighbours();
		if (oldCubeNeighbours.size() == 4) {

			ReferenceCube topFarNeighbour = oldCubeNeighbours.get(0);
			ReferenceCube topNearNeighbour = oldCubeNeighbours.get(1);
			ReferenceCube botFarNeighbour = oldCubeNeighbours.get(2);
			ReferenceCube botNearNeighbour = oldCubeNeighbours.get(3);
			List<ReferenceCube> neighbours;

			neighbours = topFarNeighbour.getRightNeighbours();
			neighbours.clear();
			neighbours.add(topLeftFar);
			neighbours = new ArrayList<ReferenceCube>();
			neighbours.add(topFarNeighbour);
			topLeftFar.setLeftNeighbours(neighbours);

			neighbours = topNearNeighbour.getRightNeighbours();
			neighbours.clear();
			neighbours.add(topLeftNear);
			neighbours = new ArrayList<ReferenceCube>();
			neighbours.add(topNearNeighbour);
			topLeftNear.setLeftNeighbours(neighbours);

			neighbours = botFarNeighbour.getRightNeighbours();
			neighbours.clear();
			neighbours.add(botLeftFar);
			neighbours = new ArrayList<ReferenceCube>();
			neighbours.add(botFarNeighbour);
			botLeftFar.setLeftNeighbours(neighbours);

			neighbours = botNearNeighbour.getRightNeighbours();
			neighbours.clear();
			neighbours.add(botLeftNear);
			neighbours = new ArrayList<ReferenceCube>();
			neighbours.add(botNearNeighbour);
			botLeftNear.setLeftNeighbours(neighbours);

		}

		else {

			ReferenceCube leftNeighbour = oldCubeNeighbours.get(0);
			List<ReferenceCube> neighbours;

			neighbours = new ArrayList<ReferenceCube>();
			neighbours.add(topLeftFar);
			neighbours.add(topLeftNear);
			neighbours.add(botLeftFar);
			neighbours.add(botLeftNear);
			leftNeighbour.getRightNeighbours().clear();
			leftNeighbour.getRightNeighbours().addAll(neighbours);

			for (ReferenceCube rc : neighbours) {

				neighbours = new ArrayList<ReferenceCube>();
				neighbours.add(leftNeighbour);
				rc.setLeftNeighbours(neighbours);
			}

		}

	}

	private void manageRightFace(List<ReferenceCube> internallyCorrectList,
			ReferenceCube oldCube) {

		if (oldCube.getRightNeighbours() == null)
			return;
		ReferenceCube topRightFar = internallyCorrectList.get(1);
		ReferenceCube topRightNear = internallyCorrectList.get(3);
		ReferenceCube botRightFar = internallyCorrectList.get(5);
		ReferenceCube botRightNear = internallyCorrectList.get(7);

		List<ReferenceCube> oldCubeNeighbours = oldCube.getRightNeighbours();
		if (oldCubeNeighbours.size() == 4) {

			ReferenceCube topFarNeighbour = oldCubeNeighbours.get(0);
			ReferenceCube topNearNeighbour = oldCubeNeighbours.get(1);
			ReferenceCube botFarNeighbour = oldCubeNeighbours.get(2);
			ReferenceCube botNearNeighbour = oldCubeNeighbours.get(3);
			List<ReferenceCube> neighbours;

			neighbours = topFarNeighbour.getLeftNeighbours();
			neighbours.clear();
			neighbours.add(topRightFar);
			neighbours = new ArrayList<ReferenceCube>();
			neighbours.add(topFarNeighbour);
			topRightFar.setRightNeighbours(neighbours);

			neighbours = topNearNeighbour.getLeftNeighbours();
			neighbours.clear();
			neighbours.add(topRightNear);
			neighbours = new ArrayList<ReferenceCube>();
			neighbours.add(topNearNeighbour);
			topRightNear.setRightNeighbours(neighbours);

			neighbours = botFarNeighbour.getLeftNeighbours();
			neighbours.clear();
			neighbours.add(botRightFar);
			neighbours = new ArrayList<ReferenceCube>();
			neighbours.add(botFarNeighbour);
			botRightFar.setRightNeighbours(neighbours);

			neighbours = botNearNeighbour.getLeftNeighbours();
			neighbours.clear();
			neighbours.add(botRightNear);
			neighbours = new ArrayList<ReferenceCube>();
			neighbours.add(botNearNeighbour);
			botRightNear.setRightNeighbours(neighbours);

		}

		else {

			ReferenceCube rightNeighbour = oldCubeNeighbours.get(0);
			List<ReferenceCube> neighbours;

			neighbours = new ArrayList<ReferenceCube>();
			neighbours.add(topRightFar);
			neighbours.add(topRightNear);
			neighbours.add(botRightFar);
			neighbours.add(botRightNear);
			rightNeighbour.getLeftNeighbours().clear();
			rightNeighbour.getLeftNeighbours().addAll(neighbours);

			for (ReferenceCube rc : neighbours) {
				neighbours = new ArrayList<ReferenceCube>();
				neighbours.add(rightNeighbour);
				rc.setRightNeighbours(neighbours);
			}

		}

	}

	private void manageBotFace(List<ReferenceCube> internallyCorrectList,
			ReferenceCube oldCube) {

		if (oldCube.getBotNeighbours() == null)
			return;
		ReferenceCube botLeftFar = internallyCorrectList.get(4);
		ReferenceCube botRightFar = internallyCorrectList.get(5);
		ReferenceCube botLeftNear = internallyCorrectList.get(6);
		ReferenceCube botRightNear = internallyCorrectList.get(7);

		List<ReferenceCube> oldCubeNeighbours = oldCube.getBotNeighbours();
		if (oldCubeNeighbours.size() == 4) {

			ReferenceCube leftFarNeighbour = oldCubeNeighbours.get(0);
			ReferenceCube rightFarNeighbour = oldCubeNeighbours.get(1);
			ReferenceCube leftNearNeighbour = oldCubeNeighbours.get(2);
			ReferenceCube rightNearNeighbour = oldCubeNeighbours.get(3);
			List<ReferenceCube> neighbours;

			neighbours = leftFarNeighbour.getTopNeighbours();
			neighbours.clear();
			neighbours.add(botLeftFar);
			neighbours = new ArrayList<ReferenceCube>();
			neighbours.add(leftFarNeighbour);
			botLeftFar.setBotNeighbours(neighbours);

			neighbours = rightFarNeighbour.getTopNeighbours();
			neighbours.clear();
			neighbours.add(botRightFar);
			neighbours = new ArrayList<ReferenceCube>();
			neighbours.add(rightFarNeighbour);
			botRightFar.setBotNeighbours(neighbours);

			neighbours = leftNearNeighbour.getTopNeighbours();
			neighbours.clear();
			neighbours.add(botLeftNear);
			neighbours = new ArrayList<ReferenceCube>();
			neighbours.add(leftNearNeighbour);
			botLeftNear.setBotNeighbours(neighbours);

			neighbours = rightNearNeighbour.getTopNeighbours();
			neighbours.clear();
			neighbours.add(botRightNear);
			neighbours = new ArrayList<ReferenceCube>();
			neighbours.add(rightNearNeighbour);
			botRightNear.setBotNeighbours(neighbours);

		}

		else {

			ReferenceCube botNeighbour = oldCubeNeighbours.get(0);
			List<ReferenceCube> neighbours;

			neighbours = new ArrayList<ReferenceCube>();
			neighbours.add(botLeftFar);
			neighbours.add(botRightFar);
			neighbours.add(botLeftNear);
			neighbours.add(botRightNear);
			botNeighbour.getTopNeighbours().clear();
			botNeighbour.getTopNeighbours().addAll(neighbours);

			for (ReferenceCube rc : neighbours) {
				neighbours = new ArrayList<ReferenceCube>();
				neighbours.add(botNeighbour);
				rc.setBotNeighbours(neighbours);
			}

		}

	}

	private void manageTopFace(List<ReferenceCube> internallyCorrectList,
			ReferenceCube oldCube) {

		if (oldCube.getTopNeighbours() == null)
			return;
		ReferenceCube topLeftFar = internallyCorrectList.get(0);
		ReferenceCube topRightFar = internallyCorrectList.get(1);
		ReferenceCube topLeftNear = internallyCorrectList.get(2);
		ReferenceCube topRightNear = internallyCorrectList.get(3);

		List<ReferenceCube> oldCubeNeighbours = oldCube.getTopNeighbours();
		if (oldCubeNeighbours.size() == 4) {

			ReferenceCube leftFarNeighbour = oldCubeNeighbours.get(0);
			ReferenceCube rightFarNeighbour = oldCubeNeighbours.get(1);
			ReferenceCube leftNearNeighbour = oldCubeNeighbours.get(2);
			ReferenceCube rightNearNeighbour = oldCubeNeighbours.get(3);
			List<ReferenceCube> neighbours;

			neighbours = leftFarNeighbour.getBotNeighbours();
			neighbours.clear();
			neighbours.add(topLeftFar);
			neighbours = new ArrayList<ReferenceCube>();
			neighbours.add(leftFarNeighbour);
			topLeftFar.setTopNeighbours(neighbours);

			neighbours = rightFarNeighbour.getBotNeighbours();
			neighbours.clear();
			neighbours.add(topRightFar);
			neighbours = new ArrayList<ReferenceCube>();
			neighbours.add(rightFarNeighbour);
			topRightFar.setTopNeighbours(neighbours);

			neighbours = leftNearNeighbour.getBotNeighbours();
			neighbours.clear();
			neighbours.add(topLeftNear);
			neighbours = new ArrayList<ReferenceCube>();
			neighbours.add(leftNearNeighbour);
			topLeftNear.setTopNeighbours(neighbours);

			neighbours = rightNearNeighbour.getBotNeighbours();
			neighbours.clear();
			neighbours.add(topRightNear);
			neighbours = new ArrayList<ReferenceCube>();
			neighbours.add(rightNearNeighbour);
			topRightNear.setTopNeighbours(neighbours);

		}

		else {

			ReferenceCube topNeighbour = oldCubeNeighbours.get(0);
			List<ReferenceCube> neighbours;

			neighbours = new ArrayList<ReferenceCube>();
			neighbours.add(topLeftFar);
			neighbours.add(topRightFar);
			neighbours.add(topLeftNear);
			neighbours.add(topRightNear);
			topNeighbour.getBotNeighbours().clear();
			topNeighbour.getBotNeighbours().addAll(neighbours);

			for (ReferenceCube rc : neighbours) {
				neighbours = new ArrayList<ReferenceCube>();
				neighbours.add(topNeighbour);
				rc.setTopNeighbours(neighbours);
			}

		}

	}

	private void manageNearFace(List<ReferenceCube> internallyCorrectList,
			ReferenceCube oldCube) {

		if (oldCube.getNearNeighbours() == null)
			return;
		ReferenceCube topLeftNear = internallyCorrectList.get(2);
		ReferenceCube topRightNear = internallyCorrectList.get(3);
		ReferenceCube botLeftNear = internallyCorrectList.get(6);
		ReferenceCube botRightNear = internallyCorrectList.get(7);

		List<ReferenceCube> oldCubeNeighbours = oldCube.getNearNeighbours();
		if (oldCubeNeighbours.size() == 4) {

			ReferenceCube leftTopNeighbour = oldCubeNeighbours.get(0);
			ReferenceCube rightTopNeighbour = oldCubeNeighbours.get(1);
			ReferenceCube leftBotNeighbour = oldCubeNeighbours.get(2);
			ReferenceCube rightBotNeighbour = oldCubeNeighbours.get(3);
			List<ReferenceCube> neighbours;

			neighbours = leftTopNeighbour.getFarNeighbours();
			neighbours.clear();
			neighbours.add(topLeftNear);
			neighbours = new ArrayList<ReferenceCube>();
			neighbours.add(leftTopNeighbour);
			topLeftNear.setNearNeighbours(neighbours);

			neighbours = rightTopNeighbour.getFarNeighbours();
			neighbours.clear();
			neighbours.add(topRightNear);
			neighbours = new ArrayList<ReferenceCube>();
			neighbours.add(rightTopNeighbour);
			topRightNear.setNearNeighbours(neighbours);

			neighbours = leftBotNeighbour.getFarNeighbours();
			neighbours.clear();
			neighbours.add(botLeftNear);
			neighbours = new ArrayList<ReferenceCube>();
			neighbours.add(leftBotNeighbour);
			botLeftNear.setNearNeighbours(neighbours);

			neighbours = rightBotNeighbour.getFarNeighbours();
			neighbours.clear();
			neighbours.add(botRightNear);
			neighbours = new ArrayList<ReferenceCube>();
			neighbours.add(rightBotNeighbour);
			botRightNear.setNearNeighbours(neighbours);

		}

		else {

			ReferenceCube nearNeighbour = oldCubeNeighbours.get(0);
			List<ReferenceCube> neighbours;

			neighbours = new ArrayList<ReferenceCube>();
			neighbours.add(topLeftNear);
			neighbours.add(topRightNear);
			neighbours.add(botLeftNear);
			neighbours.add(botRightNear);
			nearNeighbour.getFarNeighbours().clear();
			nearNeighbour.getFarNeighbours().addAll(neighbours);

			for (ReferenceCube rc : neighbours) {
				neighbours = new ArrayList<ReferenceCube>();
				neighbours.add(nearNeighbour);
				rc.setNearNeighbours(neighbours);
			}

		}

	}

	private void manageFarFace(List<ReferenceCube> internallyCorrectList,
			ReferenceCube oldCube) {

		if (oldCube.getFarNeighbours() == null)
			return;
		ReferenceCube topLeftFar = internallyCorrectList.get(0);
		ReferenceCube topRightFar = internallyCorrectList.get(1);
		ReferenceCube botLeftFar = internallyCorrectList.get(4);
		ReferenceCube botRightFar = internallyCorrectList.get(5);

		List<ReferenceCube> oldCubeNeighbours = oldCube.getFarNeighbours();
		if (oldCubeNeighbours.size() == 4) {

			ReferenceCube leftTopNeighbour = oldCubeNeighbours.get(0);
			ReferenceCube rightTopNeighbour = oldCubeNeighbours.get(1);
			ReferenceCube leftBotNeighbour = oldCubeNeighbours.get(2);
			ReferenceCube rightBotNeighbour = oldCubeNeighbours.get(3);
			List<ReferenceCube> neighbours;

			neighbours = leftTopNeighbour.getNearNeighbours();
			neighbours.clear();
			neighbours.add(topLeftFar);
			neighbours = new ArrayList<ReferenceCube>();
			neighbours.add(leftTopNeighbour);
			topLeftFar.setFarNeighbours(neighbours);

			neighbours = rightTopNeighbour.getNearNeighbours();
			neighbours.clear();
			neighbours.add(topRightFar);
			neighbours = new ArrayList<ReferenceCube>();
			neighbours.add(rightTopNeighbour);
			topRightFar.setFarNeighbours(neighbours);

			neighbours = leftBotNeighbour.getNearNeighbours();
			neighbours.clear();
			neighbours.add(botLeftFar);
			neighbours = new ArrayList<ReferenceCube>();
			neighbours.add(leftBotNeighbour);
			botLeftFar.setFarNeighbours(neighbours);

			neighbours = rightBotNeighbour.getNearNeighbours();
			neighbours.clear();
			neighbours.add(botRightFar);
			neighbours = new ArrayList<ReferenceCube>();
			neighbours.add(rightBotNeighbour);
			botRightFar.setFarNeighbours(neighbours);

		}

		else {

			ReferenceCube farNeighbour = oldCubeNeighbours.get(0);
			List<ReferenceCube> neighbours;

			neighbours = new ArrayList<ReferenceCube>();
			neighbours.add(topLeftFar);
			neighbours.add(topRightFar);
			neighbours.add(botLeftFar);
			neighbours.add(botRightFar);
			farNeighbour.getNearNeighbours().clear();
			farNeighbour.getNearNeighbours().addAll(neighbours);

			for (ReferenceCube rc : neighbours) {
				neighbours = new ArrayList<ReferenceCube>();
				neighbours.add(farNeighbour);
				rc.setFarNeighbours(neighbours);
			}

		}

	}

	private void setInternalNeighbours(List<ReferenceCube> list) {
		List<ReferenceCube> neighbour;

		ReferenceCube topLeftFar = list.get(0);
		ReferenceCube topRightFar = list.get(1);
		ReferenceCube topLeftNear = list.get(2);
		ReferenceCube topRightNear = list.get(3);
		ReferenceCube botLeftFar = list.get(4);
		ReferenceCube botRightFar = list.get(5);
		ReferenceCube botLeftNear = list.get(6);
		ReferenceCube botRightNear = list.get(7);

		// topLeftFar
		neighbour = new ArrayList<ReferenceCube>();
		neighbour.add(topRightFar);
		topLeftFar.setRightNeighbours(neighbour);
		neighbour = new ArrayList<ReferenceCube>();
		neighbour.add(topLeftNear);
		topLeftFar.setNearNeighbours(neighbour);
		neighbour = new ArrayList<ReferenceCube>();
		neighbour.add(botLeftFar);
		topLeftFar.setBotNeighbours(neighbour);

		// topRightFar
		neighbour = new ArrayList<ReferenceCube>();
		neighbour.add(topLeftFar);
		topRightFar.setLeftNeighbours(neighbour);
		neighbour = new ArrayList<ReferenceCube>();
		neighbour.add(topRightNear);
		topRightFar.setNearNeighbours(neighbour);
		neighbour = new ArrayList<ReferenceCube>();
		neighbour.add(botRightFar);
		topRightFar.setBotNeighbours(neighbour);

		// topLeftNear
		neighbour = new ArrayList<ReferenceCube>();
		neighbour.add(topRightNear);
		topLeftNear.setRightNeighbours(neighbour);
		neighbour = new ArrayList<ReferenceCube>();
		neighbour.add(topLeftFar);
		topLeftNear.setFarNeighbours(neighbour);
		neighbour = new ArrayList<ReferenceCube>();
		neighbour.add(botLeftNear);
		topLeftNear.setBotNeighbours(neighbour);

		// topRightNear
		neighbour = new ArrayList<ReferenceCube>();
		neighbour.add(topLeftNear);
		topRightNear.setLeftNeighbours(neighbour);
		neighbour = new ArrayList<ReferenceCube>();
		neighbour.add(topRightFar);
		topRightNear.setFarNeighbours(neighbour);
		neighbour = new ArrayList<ReferenceCube>();
		neighbour.add(botRightNear);
		topRightNear.setBotNeighbours(neighbour);

		// botLeftFar
		neighbour = new ArrayList<ReferenceCube>();
		neighbour.add(botRightFar);
		botLeftFar.setRightNeighbours(neighbour);
		neighbour = new ArrayList<ReferenceCube>();
		neighbour.add(botLeftNear);
		botLeftFar.setNearNeighbours(neighbour);
		neighbour = new ArrayList<ReferenceCube>();
		neighbour.add(topLeftFar);
		botLeftFar.setTopNeighbours(neighbour);

		// botRightFar
		neighbour = new ArrayList<ReferenceCube>();
		neighbour.add(botLeftFar);
		botRightFar.setLeftNeighbours(neighbour);
		neighbour = new ArrayList<ReferenceCube>();
		neighbour.add(botRightNear);
		botRightFar.setNearNeighbours(neighbour);
		neighbour = new ArrayList<ReferenceCube>();
		neighbour.add(topRightFar);
		botRightFar.setTopNeighbours(neighbour);

		// botLeftNear
		neighbour = new ArrayList<ReferenceCube>();
		neighbour.add(botRightNear);
		botLeftNear.setRightNeighbours(neighbour);
		neighbour = new ArrayList<ReferenceCube>();
		neighbour.add(botLeftFar);
		botLeftNear.setFarNeighbours(neighbour);
		neighbour = new ArrayList<ReferenceCube>();
		neighbour.add(topLeftNear);
		botLeftNear.setTopNeighbours(neighbour);

		// botRightNear
		neighbour = new ArrayList<ReferenceCube>();
		neighbour.add(botLeftNear);
		botRightNear.setLeftNeighbours(neighbour);
		neighbour = new ArrayList<ReferenceCube>();
		neighbour.add(botRightFar);
		botRightNear.setFarNeighbours(neighbour);
		neighbour = new ArrayList<ReferenceCube>();
		neighbour.add(topRightNear);
		botRightNear.setTopNeighbours(neighbour);
	}

}
