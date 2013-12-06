package pbi.computations.adaptation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import pbi.computations.data.InterpolatedFunction;
import pbi.computations.part.ComputationCube;
import pbi.computations.part.ReferenceCube;
import pbi.computations.partitions.ReferencePartitioner;

public class HAdaptationMultiThreadComputationReferencePartitioner {

	private ReferencePartitioner referencePartitioner;
	private int numberOfThreads;
	private List<Integer> tiersOfEqualSize;

	public HAdaptationMultiThreadComputationReferencePartitioner(
			ReferencePartitioner referencePartitioner, int numberOfThreads) {

		this.referencePartitioner = referencePartitioner;
		this.numberOfThreads = numberOfThreads;

	}

	private void computeTiersOfEqualSize(List<ReferenceCube> cubesToCompute) {
		int currentLvl = cubesToCompute.get(0).getLevelOfDivision();
		int currentNumber = 0;
		this.tiersOfEqualSize = new ArrayList<Integer>();
		for (ReferenceCube rc : cubesToCompute) {
			currentNumber++;
			if (rc.getLevelOfDivision() == currentLvl) {
				// currentNumber++;
			} else {
				currentLvl = rc.getLevelOfDivision();
				tiersOfEqualSize.add(currentNumber - 1);
			}
		}
		tiersOfEqualSize.add(currentNumber - 1);

	}

	private double computeCubesOfEqualSize(int startNr,
			List<ReferenceCube> cubesToCompute, double desiredErrorRate,
			int maxLevelOfDivision, int size,
			InterpolatedFunction interpolatedFunction) {

		HAdaptationWorkerThread[] workerArray = new HAdaptationWorkerThread[numberOfThreads];
		double tierMaxError = 0;

		int cubesPerThread = size / numberOfThreads;
		for (int t = 0; t < numberOfThreads - 1; t++) {

			if (cubesPerThread >= 1) {
				HAdaptationWorkerThread worker = new HAdaptationWorkerThread(
						startNr, startNr + cubesPerThread - 1, cubesToCompute,
						desiredErrorRate, maxLevelOfDivision,
						interpolatedFunction);
				workerArray[t] = worker;
				startNr += cubesPerThread;
				worker.start();
			}

		}
		int lastThreadNr = workerArray.length - 1;
		workerArray[lastThreadNr] = new HAdaptationWorkerThread(startNr,
				startNr + size - 1 - cubesPerThread * (numberOfThreads - 1),
				cubesToCompute, desiredErrorRate, maxLevelOfDivision,
				interpolatedFunction);
		workerArray[lastThreadNr].start();

		/*
		 * try { Thread.sleep(1000); } catch (InterruptedException e1) {
		 * 
		 * e1.printStackTrace(); }
		 */

		try {
			for (int t = 0; t < numberOfThreads - 1; t++) {

				if (cubesPerThread >= 1) {
					workerArray[t].join();
					if (workerArray[t].getMaxError() > tierMaxError)
						tierMaxError = workerArray[t].getMaxError();
				}

			}

			workerArray[lastThreadNr].join();
			if (workerArray[lastThreadNr].getMaxError() > tierMaxError)
				tierMaxError = workerArray[lastThreadNr].getMaxError();
		} catch (Exception e) {
			System.exit(-1);
		}

		return tierMaxError;
	}

	public void startAdaptiveMultiThreadComputation(
			InterpolatedFunction interpolatedFunction, double desiredErrorRate,
			int maxLevelOfDivision) {




		List<ReferenceCube> cubesToCompute = referencePartitioner
				.adaptMesh(interpolatedFunction);

		while (cubesToCompute.size() != 0) {
			computeTiersOfEqualSize(cubesToCompute);

			double iterationMaxError = 0;
			int startNr = 0;
			for (int nrOfTier = 0; nrOfTier < tiersOfEqualSize.size(); nrOfTier++) {
				int size;
				if (nrOfTier == 0) {
					size = tiersOfEqualSize.get(0) + 1;
				} else {
					size = tiersOfEqualSize.get(nrOfTier)
							- tiersOfEqualSize.get(nrOfTier - 1);
				}

				double error = computeCubesOfEqualSize(startNr, cubesToCompute,
						desiredErrorRate, maxLevelOfDivision, size,
						interpolatedFunction);
				if (error > iterationMaxError)
					iterationMaxError = error;
				startNr = tiersOfEqualSize.get(nrOfTier) + 1;
			}

			System.out.println("iteration max error: " + iterationMaxError);
			double rr =0; 
			int maxLvl = 0; 
			for(ComputationCube cc : referencePartitioner.getCubes()){
				if(cc.getLevelOfDivision() > maxLvl)
					maxLvl = cc.getLevelOfDivision();
				//rr+=cc.getError(); 
			}
			System.out.println("iteration total error: " + rr);
			ReferenceCube.write(referencePartitioner.getCubes().size());
			System.out.println(referencePartitioner.getCubes().size());
			System.out.println("max " + maxLvl);

			cubesToCompute = referencePartitioner
					.adaptMesh(interpolatedFunction);
		}
		
	}

}
