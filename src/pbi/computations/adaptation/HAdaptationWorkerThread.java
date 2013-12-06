package pbi.computations.adaptation;

import java.util.List;

import pbi.computations.data.InterpolatedFunction;
import pbi.computations.part.ReferenceCube;

public class HAdaptationWorkerThread extends Thread {

	private List<ReferenceCube> cubesToComputeList;
	private int startCubeNr;
	private int endCubeNr;
	private double maxError;
	private double desiredErrorRate;
	private int maxLevelOfDivision;
	private InterpolatedFunction interpolatedFunction;

	public HAdaptationWorkerThread(int startCubeNr, int endCubeNr,
			List<ReferenceCube> cubesToComputeList, double desiredErrorRate,
			int maxLevelOfDivision, InterpolatedFunction interpolatedFunction) {
		this.cubesToComputeList = cubesToComputeList;
		this.startCubeNr = startCubeNr;
		this.endCubeNr = endCubeNr;
		this.desiredErrorRate = desiredErrorRate;
		this.maxLevelOfDivision = maxLevelOfDivision;
		this.interpolatedFunction = interpolatedFunction;

	}

	@Override
	public void run() {

		cubesToComputeList.get(startCubeNr).computeCooficients(
				interpolatedFunction);
		maxError = cubesToComputeList.get(startCubeNr).isErrorLowEnough(
				desiredErrorRate, maxLevelOfDivision);
		for (int i = startCubeNr + 1; i < endCubeNr + 1; i++) {
			cubesToComputeList.get(i).computeCooficients(interpolatedFunction);
			double error = cubesToComputeList.get(i).isErrorLowEnough(
					desiredErrorRate, maxLevelOfDivision);
			if (maxError < error)
				maxError = error;

		}

	}

	public double getMaxError() {

		return maxError;
	}

}
