package Part;

import java.util.List;
import java.util.Random;

import pbi.computations.data.ExampleFunctionElementSpace3;
import pbi.computations.part.ComputationCube;
import pbi.computations.part.ReferenceCube;
import pbi.computations.partitions.ReferencePartitioner;

public class ComputationTest {

	static ReferencePartitioner x;

	public static void createStructure(int nrOfDivisions) {

		ReferenceCube initialCube2 = new ReferenceCube(0.0, 2, 2, 2, 0.0, 0.0,
				0, null);
		x = new ReferencePartitioner(initialCube2);

		Random r = new Random();

		for (int i = 0; i < nrOfDivisions; i++) {
			List<ReferenceCube> c1 = x.getCubes();

			int rnd = r.nextInt(c1.size());
			ReferenceCube rc = c1.get(rnd);

			x.divide(rc);

		}

	}

	public static void main(String[] args) {

		createStructure(100);
		List<? extends ComputationCube> list = x
				.startComputation(new ExampleFunctionElementSpace3());

		ComputationCube ccLast = list.get(0);
		for (ComputationCube cc : list) {
			if (ccLast.getLevelOfDivision() > cc.getLevelOfDivision()) {
				throw new RuntimeException("wrog ordering");
			}

			cc.checkDifrences();
			cc.isErrorLowEnough(0.00000001, 100);
			if (!cc.isErrKLowEnough()) {
				System.out.println(("blad normy sobolewa!"));
				System.exit(0);
			}
		}
	}

}
