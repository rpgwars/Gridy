package pbi.visualization;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import pbi.computations.function.Dimension;
import pbi.computations.part.DrawableCube;

public class DrawableCubeUtils implements Comparator<DrawableCube> {

	private static final double error = 0.0000000000001;

	private Dimension currentDimension;
	private boolean ascending;
	private List<DrawableCube> ascendingXSortedDrawableCubesList;
	private List<DrawableCube> ascendingYSortedDrawableCubesList;
	private List<DrawableCube> ascendingZSortedDrawableCubesList;

	private List<DrawableCube> descendingXSortedDrawableCubesList;
	private List<DrawableCube> descendingYSortedDrawableCubesList;
	private List<DrawableCube> descendingZSortedDrawableCubesList;

	private List<Integer> ascendingXCubesInTierNumbers;
	private List<Integer> descendingXCubesInTierNumbers;

	private List<DrawableCube> drawableCubesList;

	private int ascendingXTierNumber;
	private int descendingXTierNumber;

	private double[] domainBoundaries;

	private final Random random;

	@Override
	public int compare(DrawableCube drawableCube1, DrawableCube drawableCube2) {

		if (ascending) {
			switch (currentDimension) {
			case dx:
				if (drawableCube1.getCooridinate()[0] > drawableCube2
						.getCooridinate()[0])
					return 1;
				else if (drawableCube1.getCooridinate()[0] < drawableCube2
						.getCooridinate()[0])
					return -1;
				else
					return 0;
			case dy:
				if (drawableCube1.getCooridinate()[4] > drawableCube2
						.getCooridinate()[4])
					return 1;
				else if (drawableCube1.getCooridinate()[4] < drawableCube2
						.getCooridinate()[4])
					return -1;
				else
					return 0;
			case dz:
				if (drawableCube1.getCooridinate()[5] > drawableCube2
						.getCooridinate()[5])
					return 1;
				else if (drawableCube1.getCooridinate()[5] < drawableCube2
						.getCooridinate()[5])
					return -1;
				else
					return 0;
			}
		} else {
			switch (currentDimension) {
			case dx:
				if (drawableCube1.getCooridinate()[3] > drawableCube2
						.getCooridinate()[3])
					return -1;
				else if (drawableCube1.getCooridinate()[3] < drawableCube2
						.getCooridinate()[3])
					return 1;
				else
					return 0;
			case dy:
				if (drawableCube1.getCooridinate()[1] > drawableCube2
						.getCooridinate()[1])
					return -1;
				else if (drawableCube1.getCooridinate()[1] < drawableCube2
						.getCooridinate()[1])
					return 1;
				else
					return 0;
			case dz:
				if (drawableCube1.getCooridinate()[2] > drawableCube2
						.getCooridinate()[2])
					return -1;
				else if (drawableCube1.getCooridinate()[2] < drawableCube2
						.getCooridinate()[2])
					return 1;
				else
					return 0;
			}
		}

		throw new RuntimeException("comparison error");
	}

	@SuppressWarnings("unchecked")
	public DrawableCubeUtils(List<? extends DrawableCube> drawableCubesList) {

		this.random = new Random();
		random.setSeed(System.currentTimeMillis());
		this.drawableCubesList = (List<DrawableCube>) drawableCubesList;

		ascending = true;
		ascendingXSortedDrawableCubesList = new ArrayList<DrawableCube>(
				drawableCubesList);
		this.currentDimension = Dimension.dx;
		Collections.sort(ascendingXSortedDrawableCubesList, this);
		ascendingYSortedDrawableCubesList = new ArrayList<DrawableCube>(
				drawableCubesList);
		this.currentDimension = Dimension.dy;
		Collections.sort(ascendingYSortedDrawableCubesList, this);
		ascendingZSortedDrawableCubesList = new ArrayList<DrawableCube>(
				drawableCubesList);
		this.currentDimension = Dimension.dz;
		Collections.sort(ascendingZSortedDrawableCubesList, this);

		ascending = false;
		descendingXSortedDrawableCubesList = new ArrayList<DrawableCube>(
				drawableCubesList);
		this.currentDimension = Dimension.dx;
		Collections.sort(descendingXSortedDrawableCubesList, this);
		descendingYSortedDrawableCubesList = new ArrayList<DrawableCube>(
				drawableCubesList);
		this.currentDimension = Dimension.dy;
		Collections.sort(descendingYSortedDrawableCubesList, this);
		descendingZSortedDrawableCubesList = new ArrayList<DrawableCube>(
				drawableCubesList);
		this.currentDimension = Dimension.dz;
		Collections.sort(descendingZSortedDrawableCubesList, this);

		ascendingXCubesInTierNumbers = new ArrayList<Integer>();
		descendingXCubesInTierNumbers = new ArrayList<Integer>();
		double d = ascendingXSortedDrawableCubesList.get(0).getCooridinate()[0];
		int cnt = 0;
		boolean b = false;
		for (DrawableCube dc : ascendingXSortedDrawableCubesList) {
			if (Math.abs(dc.getCooridinate()[0] - d) < error) {
				cnt++;
				b = true;

			} else {
				ascendingXCubesInTierNumbers.add(cnt);
				d = dc.getCooridinate()[0];
				cnt++;
				b = false;
			}
		}

		if (b) {
			ascendingXCubesInTierNumbers.add(cnt);
		}

		d = descendingXSortedDrawableCubesList.get(0).getCooridinate()[3];
		cnt = 0;
		b = false;
		for (DrawableCube dc : descendingXSortedDrawableCubesList) {

			if (Math.abs(dc.getCooridinate()[3] - d) < error) {
				cnt++;
				b = true;
			} else {
				descendingXCubesInTierNumbers.add(cnt);
				d = dc.getCooridinate()[3];
				cnt++;
				b = false;
			}
		}

		if (b) {
			descendingXCubesInTierNumbers.add(cnt);
		}

		ascendingXTierNumber = 0;
		descendingXTierNumber = descendingXCubesInTierNumbers.size() - 1;

		domainBoundaries = new double[6];
		domainBoundaries[0] = ascendingXSortedDrawableCubesList.get(0)
				.getCooridinate()[0];
		domainBoundaries[1] = descendingXSortedDrawableCubesList.get(0)
				.getCooridinate()[3];
		domainBoundaries[2] = ascendingYSortedDrawableCubesList.get(0)
				.getCooridinate()[4];
		domainBoundaries[3] = descendingYSortedDrawableCubesList.get(0)
				.getCooridinate()[1];
		domainBoundaries[4] = ascendingZSortedDrawableCubesList.get(0)
				.getCooridinate()[5];
		domainBoundaries[5] = descendingZSortedDrawableCubesList.get(0)
				.getCooridinate()[2];

	}

	private List<DrawableCube> selectFaceCubes(List<DrawableCube> sortedCubes,
			double xLeftBoundary, double xRightBoundary, int coordinateNumber,
			boolean all) {

		List<DrawableCube> faceCubes = new ArrayList<DrawableCube>();
		double faceCoordinate = sortedCubes.get(0).getCooridinate()[coordinateNumber];
		for (DrawableCube dc : sortedCubes) {
			if ((Math.abs(faceCoordinate
					- dc.getCooridinate()[coordinateNumber]) < error)
					|| all) {
				if (dc.getCooridinate()[0] > xLeftBoundary
						&& dc.getCooridinate()[3] < xRightBoundary)
					faceCubes.add(dc);
			} else
				return faceCubes;
		}

		return faceCubes;
	}

	public List<DrawableCube> getVisibleCubes(boolean all) {
		List<DrawableCube> visibleCubes = new ArrayList<DrawableCube>();
		int firstNumberOfVisibleCube;
		int lastNumebrOfVisibleCube;
		if (ascendingXTierNumber == 0) {
			firstNumberOfVisibleCube = 0;
			lastNumebrOfVisibleCube = ascendingXCubesInTierNumbers.get(0) - 1;
		} else {
			firstNumberOfVisibleCube = ascendingXCubesInTierNumbers
					.get(ascendingXTierNumber - 1);
			lastNumebrOfVisibleCube = ascendingXCubesInTierNumbers
					.get(ascendingXTierNumber) - 1;
		}

		double xLeftBoundaryCoordinate = ascendingXSortedDrawableCubesList.get(
				firstNumberOfVisibleCube).getCooridinate()[0];

		for (int i = firstNumberOfVisibleCube; i <= lastNumebrOfVisibleCube; i++) {

			visibleCubes.add(ascendingXSortedDrawableCubesList.get(i));
		}

		// desc
		if (descendingXTierNumber == (descendingXCubesInTierNumbers.size() - 1)) {
			firstNumberOfVisibleCube = 0;
			lastNumebrOfVisibleCube = descendingXCubesInTierNumbers.get(0) - 1;

		} else {
			firstNumberOfVisibleCube = descendingXCubesInTierNumbers
					.get(descendingXCubesInTierNumbers.size()
							- descendingXTierNumber - 2);
			lastNumebrOfVisibleCube = descendingXCubesInTierNumbers
					.get(descendingXCubesInTierNumbers.size()
							- descendingXTierNumber - 1) - 1;
		}

		double xRightBoundaryCoordinate = descendingXSortedDrawableCubesList
				.get(firstNumberOfVisibleCube).getCooridinate()[3];

		for (int i = firstNumberOfVisibleCube; i <= lastNumebrOfVisibleCube; i++) {

			visibleCubes.add(descendingXSortedDrawableCubesList.get(i));
		}

		if (all) {
			visibleCubes.addAll(selectFaceCubes(
					ascendingYSortedDrawableCubesList, xLeftBoundaryCoordinate,
					xRightBoundaryCoordinate, 4, true));
		} else {
			visibleCubes.addAll(selectFaceCubes(
					ascendingYSortedDrawableCubesList, xLeftBoundaryCoordinate,
					xRightBoundaryCoordinate, 4, false));
			visibleCubes.addAll(selectFaceCubes(
					ascendingZSortedDrawableCubesList, xLeftBoundaryCoordinate,
					xRightBoundaryCoordinate, 5, false));
			visibleCubes
					.addAll(selectFaceCubes(descendingYSortedDrawableCubesList,
							xLeftBoundaryCoordinate, xRightBoundaryCoordinate,
							1, false));
			visibleCubes
					.addAll(selectFaceCubes(descendingZSortedDrawableCubesList,
							xLeftBoundaryCoordinate, xRightBoundaryCoordinate,
							2, false));
		}

		return visibleCubes;
	}

	public void addTier(boolean left, List<DrawableCube> allDataCubes,
			List<DrawableCube> visibleDataCubes) {

		if (left) {
			if (ascendingXTierNumber > 0) {
				ascendingXTierNumber--;

			}
		} else {
			if (descendingXTierNumber < (descendingXCubesInTierNumbers.size() - 1)) {
				descendingXTierNumber++;

			}
		}

		allDataCubes.clear();
		allDataCubes.addAll(getVisibleCubes(true));
		visibleDataCubes.clear();
		visibleDataCubes.addAll(getVisibleCubes(false));

	}

	public void removeTier(boolean left, List<DrawableCube> allDataCubes,
			List<DrawableCube> visibleDataCubes) {

		if (left) {
			if (ascendingXTierNumber < (ascendingXCubesInTierNumbers.size() - 1)) {
				ascendingXTierNumber++;

			}
		} else {
			if (descendingXTierNumber > 0) {
				descendingXTierNumber--;

			}
		}

		allDataCubes.clear();
		allDataCubes.addAll(getVisibleCubes(true));
		visibleDataCubes.clear();
		visibleDataCubes.addAll(getVisibleCubes(false));

	}

	public List<DrawableCube> getAllCubes() {
		return drawableCubesList;
	}

	public double getFunctionLowValue() {
		double min = drawableCubesList.get(0).getRandomPointValue();
		for (DrawableCube dc : drawableCubesList) {
			for (int i = 0; i < 3; i++) {
				min = Math.min(min, dc.getRandomPointValue());
			}
		}
		return min;
	}

	public double getFunctionHighValue() {
		double max = drawableCubesList.get(0).getRandomPointValue();
		for (DrawableCube dc : drawableCubesList) {
			for (int i = 0; i < 3; i++) {
				max = Math.max(max, dc.getRandomPointValue());
			}
		}
		return max;
	}

	public double[] getDomainBoundaries() {

		return domainBoundaries;

	}

}
