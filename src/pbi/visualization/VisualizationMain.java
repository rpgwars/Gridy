package pbi.visualization;

import java.awt.Frame;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;


import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import pbi.computations.adaptation.HAdaptationMultiThreadComputationReferencePartitioner;
import pbi.computations.data.DataStructure;
import pbi.computations.data.ExampleFunctionElementSpace2;
import pbi.computations.data.ExampleFunctionVisualization2;
import pbi.computations.data.ExampleFunctionVisualization4;
import pbi.computations.data.SimpleFunction;
import pbi.computations.part.ReferenceCube;
import pbi.computations.partitions.ReferencePartitioner;



public class VisualizationMain {

	static ReferencePartitioner x;

	private static final int FPS = 60; // animator's target frames per second

	public static void main(String[] args) {

		ReferenceCube initialCube = new ReferenceCube(0.0, 1.0, 1.0, 1.0, 0.0,
				0.0, 0, null);
		ReferencePartitioner p = new ReferencePartitioner(initialCube);

		HAdaptationMultiThreadComputationReferencePartitioner x = new HAdaptationMultiThreadComputationReferencePartitioner(
				p, 4);

		if (args.length == 2) {
			System.out.println(new File(".").getAbsolutePath());
			File f = new File(args[0]);
			if (!f.exists()) {
				System.out.println("Data file does not exist");
				System.exit(0);
			}

			initialCube = new ReferenceCube(0.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0,
					null);
			p = new ReferencePartitioner(initialCube);

			x = new HAdaptationMultiThreadComputationReferencePartitioner(p, 4);

			try {

				x.startAdaptiveMultiThreadComputation(
						new DataStructure(args[0]),
						Double.parseDouble(args[1]), 5);

			} catch (NumberFormatException ex) {
				System.out.println("Wrong number format (second argument) "
						+ args[1]);
				System.exit(0);
			}

		}

		else {

			System.out
					.println("Enter a number from 1 to 3 to show example visualization\n"
							+ "1) (x>=0) 2x (x<0) 2*cos(2*x)\n"
							+ "2) x*x*x + y*y*y + z*z*z + 1\n"
							+ "3) 3*x*x + 2*x*z - 4*y + x*x*y\n");
			BufferedReader bufferRead = new BufferedReader(
					new InputStreamReader(System.in));

			int nr = 1;
			try {
				String s = bufferRead.readLine();
				nr = Integer.parseInt(s);
			} catch (IOException e) {
				System.exit(0);
			} catch (NumberFormatException ex) {
				System.exit(0);
			}

			initialCube = new ReferenceCube(0.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0,
					null);
			p = new ReferencePartitioner(initialCube);
			x = new HAdaptationMultiThreadComputationReferencePartitioner(p, 4);

			switch (nr) {
			case 1:
				initialCube = new ReferenceCube(-3.0, 3.0, 3.0, 3.0, -3.0,
						-3.0, 0, null);
				p = new ReferencePartitioner(initialCube);
				x = new HAdaptationMultiThreadComputationReferencePartitioner(
						p, 4);
				x.startAdaptiveMultiThreadComputation(
						new ExampleFunctionVisualization4(), 0.01, 20);
				break;
			case 2:
				x.startAdaptiveMultiThreadComputation(
						new ExampleFunctionVisualization2(), 0.1, 20);
				break;
			case 3:
				x.startAdaptiveMultiThreadComputation(
						new ExampleFunctionElementSpace2(), 0.01, 20);
				break;
			default:
				System.exit(0);
			}

		}
		//zz

	}
	/*
	 * iteration max error: 75.6735903753962 iteration max error:
	 * 35.21050427200105 iteration max error: 15.546478075058147 iteration max
	 * error: 4.989184368551952 iteration max error: 1.9435853406514507
	 * iteration max error: 0.9540596011787315 iteration max error:
	 * 0.5170182244282246 iteration max error: 0.33976690755990135 iteration max
	 * error: 0.19579215590505167
	 */
}
