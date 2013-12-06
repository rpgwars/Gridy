
package Part;

import static org.junit.Assert.fail;

import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import pbi.computations.data.InterpolatedFunction;
import pbi.computations.function.TripArgFunction;
import pbi.computations.part.ComputationCube;
import pbi.computations.part.ReferenceCube;
import pbi.computations.partitions.ReferencePartitioner;



public class SobolevNormTest {

	private ReferencePartitioner x;
	private InterpolatedFunction randomTestFunction;


	public void createFunction(){
		Random r = new Random(System.currentTimeMillis());
		final int numOfSubFunctions = r.nextInt(5) + 3;
		final int[] coffiecnts = new int[numOfSubFunctions*4];
		for(int i = 0; i < numOfSubFunctions; i++){
			coffiecnts[4*i] = r.nextInt(20);
			coffiecnts[4*i+1] = r.nextInt(3);
			coffiecnts[4*i+2] = r.nextInt(3);
			coffiecnts[4*i+3] = r.nextInt(3);
		}

		final TripArgFunction function = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				double retVal = 0;
				for(int i = 0; i < numOfSubFunctions ; i++){

					int multiplier = coffiecnts[4*i];
					int xPower = coffiecnts[4*i + 1];
					int yPower = coffiecnts[4*i + 2];
					int zPower = coffiecnts[4*i + 3];
					retVal += multiplier*Math.pow(x, xPower)*Math.pow(y, yPower)*Math.pow(z, zPower);

				}
				return retVal;
			}
		};

		final TripArgFunction dxFunction = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				double retVal = 0;
				for(int i = 0; i < numOfSubFunctions ; i++){

					int multiplier = coffiecnts[4*i];
					int xPower = coffiecnts[4*i + 1] - 1;
					int yPower = coffiecnts[4*i + 2];
					int zPower = coffiecnts[4*i + 3];
					if(xPower > -1)
						retVal += multiplier*(xPower+1)*Math.pow(x, xPower)*Math.pow(y, yPower)*Math.pow(z, zPower);


				}
				return retVal;
			}
		};

		final TripArgFunction dyFunction = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				double retVal = 0;
				for(int i = 0; i < numOfSubFunctions ; i++){

					int multiplier = coffiecnts[4*i];
					int xPower = coffiecnts[4*i + 1];
					int yPower = coffiecnts[4*i + 2] - 1;
					int zPower = coffiecnts[4*i + 3];
					if(yPower > -1)
						retVal += multiplier*(yPower+1)*Math.pow(x, xPower)*Math.pow(y, yPower)*Math.pow(z, zPower);


				}
				return retVal;
			}
		};

		final TripArgFunction dzFunction = new TripArgFunction() {

			@Override
			public double computeValue(double x, double y, double z) {
				double retVal = 0;
				for(int i = 0; i < numOfSubFunctions ; i++){

					int multiplier = coffiecnts[4*i];
					int xPower = coffiecnts[4*i + 1];
					int yPower = coffiecnts[4*i + 2];
					int zPower = coffiecnts[4*i + 3] - 1;
					if(zPower > -1)
						retVal += multiplier*(zPower+1)*Math.pow(x, xPower)*Math.pow(y, yPower)*Math.pow(z, zPower);

				}
				return retVal;
			}
		};

		randomTestFunction = new InterpolatedFunction(){

			@Override
			public double computeValue(double x, double y, double z) {
				return function.computeValue(x, y, z);

			}

			@Override
			public double computeDxValue(double x, double y, double z) {
				return dxFunction.computeValue(x, y, z);
			}

			@Override
			public double computeDyValue(double x, double y, double z) {
				return dyFunction.computeValue(x, y, z);
			}

			@Override
			public double computeDzValue(double x, double y, double z) {
				return dzFunction.computeValue(x, y, z);
			}

		};

		System.out.println("test wykonany dla funkcji f(x,y,z) = ");
		for(int i = 0; i < numOfSubFunctions; i++){
			System.out.print(coffiecnts[4*i] + "*");
			System.out.print("x^" + coffiecnts[4*i+1] + "*");
			System.out.print("y^" + coffiecnts[4*i+2] + "*");
			System.out.print("z^" + coffiecnts[4*i+3]);
			if(i < numOfSubFunctions - 1 ){
				System.out.print(" + ");
			}

		}

	}

	public void createStructure(){

		int nrOfDivisions = 0;
		ReferenceCube initialCube2 = new ReferenceCube(-1.0,1,1,1,-1.0,-1.0,0, null);
		x = new ReferencePartitioner(initialCube2);

		Random r = new Random();


		for(int i=0;i<nrOfDivisions;i++){
			List<ReferenceCube> c1 = x.getCubes();

			int rnd = r.nextInt(c1.size());
			ReferenceCube rc = c1.get(rnd);

			x.divide(rc);

		}

	}

	@Before
	public void prepare(){
		createStructure();
		createFunction();
	}
	@Test
	public void test(){

		List<? extends ComputationCube> list = x.startComputation(randomTestFunction);

		ComputationCube ccLast = list.get(0);
		for(ComputationCube cc : list){
			if(ccLast.getLevelOfDivision() > cc.getLevelOfDivision()){
				throw new RuntimeException("wrog ordering");
			}

			cc.checkDifrences();
			cc.isErrorLowEnough(0.0000000001, 100);

			if(!cc.isErrKLowEnough()){

				fail("Blad normy soboleva");
			}
		}


	}

}


