package pbi.computations.partitions;

import java.util.List;
import java.util.ArrayList;

public class HashMapKey {

	public int x;
	public int y;
	public int z;

	public HashMapKey(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;

	}

	/*
	 * public int getEqualIntSize(int maxLevelOfDivision){ int exponent =
	 * maxLevelOfDivision - levelOfDivision; int intSize = 1; for(int
	 * i=0;i<exponent; i++) intSize*=2;
	 * 
	 * return intSize; }
	 * 
	 * public int getBiggerIntSize(int maxLevelOfDivision){ return
	 * getEqualIntSize(maxLevelOfDivision+1); }
	 * 
	 * public int getSmallerIntSize(int maxLevelOfDivision){
	 * if(getEqualIntSize(maxLevelOfDivision)==1)
	 * System.out.println("Za mala kostka"); return
	 * getEqualIntSize(maxLevelOfDivision-1); }
	 */

	private int getNewSize(int exponent) {
		int intSize = 1;
		for (int i = 0; i < exponent; i++)
			intSize *= 2;

		return intSize;
	}

	public void printKey() {
		System.out.println(x + " " + y + " " + z);
	}

	public List<HashMapKey> divide(int exponent) {
		List<HashMapKey> list = new ArrayList<HashMapKey>();
		int newSize = getNewSize(exponent);
		HashMapKey k1 = new HashMapKey(x, y, z);
		HashMapKey k2 = new HashMapKey(x + newSize, y, z);
		HashMapKey k3 = new HashMapKey(x, y - newSize, z);
		HashMapKey k4 = new HashMapKey(x + newSize, y - newSize, z);
		HashMapKey k5 = new HashMapKey(x, y, z - newSize);
		HashMapKey k6 = new HashMapKey(x + newSize, y, z - newSize);
		HashMapKey k7 = new HashMapKey(x, y - newSize, z - newSize);
		HashMapKey k8 = new HashMapKey(x + newSize, y - newSize, z - newSize);
		list.add(k1);
		list.add(k2);
		list.add(k3);
		list.add(k4);
		list.add(k5);
		list.add(k6);
		list.add(k7);
		list.add(k8);
		return list;

	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof HashMapKey))
			return false;

		HashMapKey hmk = (HashMapKey) o;
		if (hmk.x == this.x && hmk.y == this.y && hmk.z == this.z)
			return true;
		return false;

	}

	@Override
	public int hashCode() {
		return x * 32 + y * 8 + z * 2;
	}

}
