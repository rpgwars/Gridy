package Partitions;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ArrayList;

import pbi.computations.part.*;
import pbi.computations.partitions.*;

public class PartitionsTest {

	public void test1() throws Exception {

		ReferenceCube initialCube2 = new ReferenceCube(0.0, 1073741824.0,
				1073741824.0, 1073741824.0, 0.0, 0.0, 0, null);
		ReferencePartitioner p2 = new ReferencePartitioner(initialCube2);

		// p2.printStructure();
		HashMapCube initialCube = new HashMapCube(0.0, 1073741824.0,
				1073741824.0, 1073741824.0, 0.0, 0.0, 0, null);
		HashMapPartitioner p = new HashMapPartitioner(initialCube, 30);
		Random r = new Random();

		for (int i = 0; i < 130; i++) {
			List<ReferenceCube> c1 = p2.getCubes();
			Map<HashMapKey, HashMapCube> c2 = p.getCubes();
			System.out.println(c1.size() + " " + c2.size());
			if (c1.size() != c2.size()) {
				System.out.println("rozne rozmiary " + c1.size() + " "
						+ c2.size());
				throw new Exception();
			}
			int x = r.nextInt(c1.size());
			ReferenceCube rc = c1.get(x);
			double[] cooordinates = rc.getCooridinate();
			HashMapKey hk = new HashMapKey((int) Math.round(cooordinates[0]),
					(int) Math.round(cooordinates[1]),
					(int) Math.round(cooordinates[2]));
			p2.divide(rc);
			p.divide(hk);
			c1 = p2.getCubes();
			c2 = p.getCubes();

			for (Cube c : c1) {

				if (!c2.containsValue(c)) {

					throw new Exception();
				}

			}
		}

	}

	public void speedTest(int nr) throws Exception {

		ReferenceCube initialCube2 = new ReferenceCube(0.0, 1073741824.0,
				1073741824.0, 1073741824.0, 0.0, 0.0, 0, null);
		ReferencePartitioner p2 = new ReferencePartitioner(initialCube2);
		long start;

		// p2.printStructure();
		HashMapCube initialCube = new HashMapCube(0.0, 1073741824.0,
				1073741824.0, 1073741824.0, 0.0, 0.0, 0, null);
		HashMapPartitioner p = new HashMapPartitioner(initialCube, 30);

		Random r = new Random();

		List<HashMapKey> keys = new ArrayList<HashMapKey>();
		start = System.currentTimeMillis();
		for (int i = 0; i < nr; i++) {
			List<ReferenceCube> c1 = p2.getCubes();
			//
			int x = r.nextInt(c1.size());
			ReferenceCube rc = c1.get(x);
			double[] cooordinates = rc.getCooridinate();
			HashMapKey hk = new HashMapKey((int) Math.round(cooordinates[0]),
					(int) Math.round(cooordinates[1]),
					(int) Math.round(cooordinates[2]));
			keys.add(hk);
			p2.divide(rc);
			//
			c1 = p2.getCubes();
			// c2 = p.getCubes();
		}
		System.out.println("ref time " + (System.currentTimeMillis() - start));
		start = System.currentTimeMillis();
		for (int i = 0; i < nr; i++) {
			// Map<HashMapKey, HashMapCube> c2 = p.getCubes();
			p.divide(keys.get(i));
			// p.divide(new HashMapKey(0,8,8));

		}
		System.out.println("hash time " + (System.currentTimeMillis() - start));
		System.out.println(p.getCubes().size() + " " + p2.getCubes().size());

	}

	public static void main(String[] args) {

		PartitionsTest t = new PartitionsTest();
		try {
			t.test1();
			t.speedTest(1000);
			System.out.println("ok");
		} catch (Exception e) {
			System.out.println("test nieudany!");
			// System.out.println(e.getCause().toString());
			e.printStackTrace();
		}

	}
}
