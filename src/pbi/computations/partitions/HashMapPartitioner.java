package pbi.computations.partitions;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import pbi.computations.part.HashMapCube;

public class HashMapPartitioner {

	private int maxLevelOfDivision;
	private Map<HashMapKey, HashMapCube> map;

	private int maxIntSize;
	private Map<HashMapKey, HashMapCube> tmpMap;

	public HashMapPartitioner(HashMapCube initialCube, int maxLevelOfDivision) {

		this.maxLevelOfDivision = maxLevelOfDivision;
		map = new HashMap<HashMapKey, HashMapCube>();
		maxIntSize = (int) Math.pow(2, maxLevelOfDivision);
		HashMapKey initialKey = new HashMapKey(0, maxIntSize, maxIntSize);
		map.put(initialKey, initialCube);

	}

	public void printStructure() {

		HashMapKey key;
		HashMapCube cube;
		System.out.println("rozmiar" + map.size());
		for (Map.Entry<HashMapKey, HashMapCube> entry : map.entrySet()) {
			key = entry.getKey();
			cube = entry.getValue();
			key.printKey();
			cube.printCube();

		}
	}

	private int getBigInt(int level) {
		int intSize = 1;
		for (int i = 0; i < maxLevelOfDivision - level + 1; i++)
			intSize *= 2;

		return intSize;
	}

	public Map<HashMapKey, HashMapCube> getCubes() {
		return map;
	}

	public void divide(HashMapKey hmk) throws Exception {

		selectCubesKeysToDivide(hmk);
		HashMapKey key;
		HashMapCube cube;
		List<HashMapKey> keyList;
		List<HashMapCube> cubeList;

		for (Map.Entry<HashMapKey, HashMapCube> entry : tmpMap.entrySet()) {

			key = entry.getKey();
			cube = entry.getValue();
			keyList = key.divide(maxLevelOfDivision - cube.getLevelOfDivision()
					- 1);
			cubeList = cube.divide();

			map.remove(key);

			for (int i = 0; i < 8; i++) {
				map.put(keyList.get(i), cubeList.get(i));

			}
			if (cubeList.get(0).getLevelOfDivision() > maxLevelOfDivision) {
				System.out.println("za duzy podzial");
				throw new Exception("za duzy podzial");
			}

		}

		tmpMap = null;
	}

	// hmk klucz pierwszego szescianu do podzialu
	public void selectCubesKeysToDivide(HashMapKey hmk) {

		tmpMap = new HashMap<HashMapKey, HashMapCube>();
		tmpMap.put(hmk, map.get(hmk));
		select(hmk);

	}

	public void select(HashMapKey hmk) {

		List<HashMapKey> bigNeigbourList = getBiggerNeighboursKeys(hmk);

		for (HashMapKey bnk : bigNeigbourList) {
			if (!tmpMap.containsKey(bnk)) {
				tmpMap.put(bnk, map.get(bnk));

				select(bnk);
			}

		}

	}

	// returns neighbours keys that do not allow this element to divide
	public List<HashMapKey> getBiggerNeighboursKeys(HashMapKey key) {

		List<HashMapKey> neighbourList = new ArrayList<HashMapKey>();

		HashMapKey k;

		// face neighbours
		if ((k = getPotentialLeftBigNeighbourKey(key)) != null)
			neighbourList.add(k);
		if ((k = getPotentialRightBigNeighbourKey(key)) != null)
			neighbourList.add(k);
		if ((k = getPotentialTopBigNeighbourKey(key)) != null)
			neighbourList.add(k);
		if ((k = getPotentialBotBigNeighbourKey(key)) != null)
			neighbourList.add(k);
		if ((k = getPotentialNearBigNeighbourKey(key)) != null)
			neighbourList.add(k);
		if ((k = getPotentialFarBigNeighbourKey(key)) != null)
			neighbourList.add(k);

		// cross neighbours

		if ((k = getPotentialLeftBotCrossBigNeighbourKey(key)) != null)
			neighbourList.add(k);
		if ((k = getPotentialLeftTopCrossBigNeighbourKey(key)) != null)
			neighbourList.add(k);
		if ((k = getPotentialLeftNearCrossBigNeighbourKey(key)) != null)
			neighbourList.add(k);
		if ((k = getPotentialLeftFarCrossBigNeighbourKey(key)) != null)
			neighbourList.add(k);

		if ((k = getPotentialRightBotCrossBigNeighbourKey(key)) != null)
			neighbourList.add(k);
		if ((k = getPotentialRightTopCrossBigNeighbourKey(key)) != null)
			neighbourList.add(k);
		if ((k = getPotentialRightNearCrossBigNeighbourKey(key)) != null)
			neighbourList.add(k);
		if ((k = getPotentialRightFarCrossBigNeighbourKey(key)) != null)
			neighbourList.add(k);

		if ((k = getPotentialBotFarCrossBigNeighbourKey(key)) != null)
			neighbourList.add(k);
		if ((k = getPotentialBotNearCrossBigNeighbourKey(key)) != null)
			neighbourList.add(k);
		if ((k = getPotentialTopFarCrossBigNeighbourKey(key)) != null)
			neighbourList.add(k);
		if ((k = getPotentialTopNearCrossBigNeighbourKey(key)) != null)
			neighbourList.add(k);

		return neighbourList;

	}

	// max 6*4 = 24 min 6*1 = 6

	public HashMapKey getPotentialRightTopCrossBigNeighbourKey(HashMapKey key) {

		HashMapCube cube = map.get(key);
		int bigInt = getBigInt(cube.getLevelOfDivision());
		int equalInt = bigInt / 2;

		HashMapKey potentialCrossNeighbourKey = new HashMapKey(0, 0, 0);

		potentialCrossNeighbourKey.x = key.x + equalInt;
		potentialCrossNeighbourKey.y = key.y;
		potentialCrossNeighbourKey.z = key.z + bigInt;

		if (map.containsKey(potentialCrossNeighbourKey)
				&& map.get(potentialCrossNeighbourKey).getLevelOfDivision() + 1 == map
						.get(key).getLevelOfDivision())
			return potentialCrossNeighbourKey;

		potentialCrossNeighbourKey.y += equalInt;
		if (map.containsKey(potentialCrossNeighbourKey)
				&& map.get(potentialCrossNeighbourKey).getLevelOfDivision() + 1 == map
						.get(key).getLevelOfDivision())
			return potentialCrossNeighbourKey;

		return null;

	}

	public HashMapKey getPotentialRightBotCrossBigNeighbourKey(HashMapKey key) {

		HashMapCube cube = map.get(key);
		int bigInt = getBigInt(cube.getLevelOfDivision());
		int equalInt = bigInt / 2;

		HashMapKey potentialCrossNeighbourKey = new HashMapKey(0, 0, 0);

		potentialCrossNeighbourKey.x = key.x + equalInt;
		potentialCrossNeighbourKey.y = key.y;
		potentialCrossNeighbourKey.z = key.z - equalInt;

		if (map.containsKey(potentialCrossNeighbourKey)
				&& map.get(potentialCrossNeighbourKey).getLevelOfDivision() + 1 == map
						.get(key).getLevelOfDivision())
			return potentialCrossNeighbourKey;

		potentialCrossNeighbourKey.y += equalInt;
		if (map.containsKey(potentialCrossNeighbourKey)
				&& map.get(potentialCrossNeighbourKey).getLevelOfDivision() + 1 == map
						.get(key).getLevelOfDivision())
			return potentialCrossNeighbourKey;

		return null;

	}

	public HashMapKey getPotentialRightFarCrossBigNeighbourKey(HashMapKey key) {

		HashMapCube cube = map.get(key);
		int bigInt = getBigInt(cube.getLevelOfDivision());
		int equalInt = bigInt / 2;

		HashMapKey potentialCrossNeighbourKey = new HashMapKey(0, 0, 0);

		potentialCrossNeighbourKey.x = key.x + equalInt;
		potentialCrossNeighbourKey.y = key.y + bigInt;
		potentialCrossNeighbourKey.z = key.z;

		if (map.containsKey(potentialCrossNeighbourKey)
				&& map.get(potentialCrossNeighbourKey).getLevelOfDivision() + 1 == map
						.get(key).getLevelOfDivision())
			return potentialCrossNeighbourKey;

		potentialCrossNeighbourKey.z += equalInt;
		if (map.containsKey(potentialCrossNeighbourKey)
				&& map.get(potentialCrossNeighbourKey).getLevelOfDivision() + 1 == map
						.get(key).getLevelOfDivision())
			return potentialCrossNeighbourKey;

		return null;

	}

	public HashMapKey getPotentialRightNearCrossBigNeighbourKey(HashMapKey key) {

		HashMapCube cube = map.get(key);
		int bigInt = getBigInt(cube.getLevelOfDivision());
		int equalInt = bigInt / 2;

		HashMapKey potentialCrossNeighbourKey = new HashMapKey(0, 0, 0);

		potentialCrossNeighbourKey.x = key.x + equalInt;
		potentialCrossNeighbourKey.y = key.y - equalInt;
		potentialCrossNeighbourKey.z = key.z;

		if (map.containsKey(potentialCrossNeighbourKey)
				&& map.get(potentialCrossNeighbourKey).getLevelOfDivision() + 1 == map
						.get(key).getLevelOfDivision())
			return potentialCrossNeighbourKey;

		potentialCrossNeighbourKey.z += equalInt;
		if (map.containsKey(potentialCrossNeighbourKey)
				&& map.get(potentialCrossNeighbourKey).getLevelOfDivision() + 1 == map
						.get(key).getLevelOfDivision())
			return potentialCrossNeighbourKey;

		return null;

	}

	public HashMapKey getPotentialLeftTopCrossBigNeighbourKey(HashMapKey key) {

		HashMapCube cube = map.get(key);
		int bigInt = getBigInt(cube.getLevelOfDivision());
		int equalInt = bigInt / 2;

		HashMapKey potentialCrossNeighbourKey = new HashMapKey(0, 0, 0);

		potentialCrossNeighbourKey.x = key.x - bigInt;
		potentialCrossNeighbourKey.y = key.y;
		potentialCrossNeighbourKey.z = key.z + bigInt;

		if (map.containsKey(potentialCrossNeighbourKey)
				&& map.get(potentialCrossNeighbourKey).getLevelOfDivision() + 1 == map
						.get(key).getLevelOfDivision())
			return potentialCrossNeighbourKey;

		potentialCrossNeighbourKey.y += equalInt;
		if (map.containsKey(potentialCrossNeighbourKey)
				&& map.get(potentialCrossNeighbourKey).getLevelOfDivision() + 1 == map
						.get(key).getLevelOfDivision())
			return potentialCrossNeighbourKey;

		return null;

	}

	public HashMapKey getPotentialLeftBotCrossBigNeighbourKey(HashMapKey key) {

		HashMapCube cube = map.get(key);
		int bigInt = getBigInt(cube.getLevelOfDivision());
		int equalInt = bigInt / 2;

		HashMapKey potentialCrossNeighbourKey = new HashMapKey(0, 0, 0);

		potentialCrossNeighbourKey.x = key.x - bigInt;
		potentialCrossNeighbourKey.y = key.y;
		potentialCrossNeighbourKey.z = key.z - equalInt;

		if (map.containsKey(potentialCrossNeighbourKey)
				&& map.get(potentialCrossNeighbourKey).getLevelOfDivision() + 1 == map
						.get(key).getLevelOfDivision())
			return potentialCrossNeighbourKey;

		potentialCrossNeighbourKey.y += equalInt;
		if (map.containsKey(potentialCrossNeighbourKey)
				&& map.get(potentialCrossNeighbourKey).getLevelOfDivision() + 1 == map
						.get(key).getLevelOfDivision())
			return potentialCrossNeighbourKey;

		return null;

	}

	public HashMapKey getPotentialLeftFarCrossBigNeighbourKey(HashMapKey key) {

		HashMapCube cube = map.get(key);
		int bigInt = getBigInt(cube.getLevelOfDivision());
		int equalInt = bigInt / 2;

		HashMapKey potentialCrossNeighbourKey = new HashMapKey(0, 0, 0);

		potentialCrossNeighbourKey.x = key.x - bigInt;
		potentialCrossNeighbourKey.y = key.y + bigInt;
		potentialCrossNeighbourKey.z = key.z;

		if (map.containsKey(potentialCrossNeighbourKey)
				&& map.get(potentialCrossNeighbourKey).getLevelOfDivision() + 1 == map
						.get(key).getLevelOfDivision())
			return potentialCrossNeighbourKey;

		potentialCrossNeighbourKey.z += equalInt;
		if (map.containsKey(potentialCrossNeighbourKey)
				&& map.get(potentialCrossNeighbourKey).getLevelOfDivision() + 1 == map
						.get(key).getLevelOfDivision())
			return potentialCrossNeighbourKey;

		return null;

	}

	public HashMapKey getPotentialLeftNearCrossBigNeighbourKey(HashMapKey key) {

		HashMapCube cube = map.get(key);
		int bigInt = getBigInt(cube.getLevelOfDivision());
		int equalInt = bigInt / 2;

		HashMapKey potentialCrossNeighbourKey = new HashMapKey(0, 0, 0);

		potentialCrossNeighbourKey.x = key.x - bigInt;
		potentialCrossNeighbourKey.y = key.y - equalInt;
		potentialCrossNeighbourKey.z = key.z;

		if (map.containsKey(potentialCrossNeighbourKey)
				&& map.get(potentialCrossNeighbourKey).getLevelOfDivision() + 1 == map
						.get(key).getLevelOfDivision())
			return potentialCrossNeighbourKey;

		potentialCrossNeighbourKey.z += equalInt;
		if (map.containsKey(potentialCrossNeighbourKey)
				&& map.get(potentialCrossNeighbourKey).getLevelOfDivision() + 1 == map
						.get(key).getLevelOfDivision())
			return potentialCrossNeighbourKey;

		return null;

	}

	public HashMapKey getPotentialTopFarCrossBigNeighbourKey(HashMapKey key) {

		HashMapCube cube = map.get(key);
		int bigInt = getBigInt(cube.getLevelOfDivision());
		int equalInt = bigInt / 2;

		HashMapKey potentialCrossNeighbourKey = new HashMapKey(0, 0, 0);

		potentialCrossNeighbourKey.x = key.x;
		potentialCrossNeighbourKey.y = key.y + bigInt;
		potentialCrossNeighbourKey.z = key.z + bigInt;

		if (map.containsKey(potentialCrossNeighbourKey)
				&& map.get(potentialCrossNeighbourKey).getLevelOfDivision() + 1 == map
						.get(key).getLevelOfDivision())
			return potentialCrossNeighbourKey;

		potentialCrossNeighbourKey.x -= equalInt;
		if (map.containsKey(potentialCrossNeighbourKey)
				&& map.get(potentialCrossNeighbourKey).getLevelOfDivision() + 1 == map
						.get(key).getLevelOfDivision())
			return potentialCrossNeighbourKey;

		return null;

	}

	public HashMapKey getPotentialTopNearCrossBigNeighbourKey(HashMapKey key) {

		HashMapCube cube = map.get(key);
		int bigInt = getBigInt(cube.getLevelOfDivision());
		int equalInt = bigInt / 2;

		HashMapKey potentialCrossNeighbourKey = new HashMapKey(0, 0, 0);

		potentialCrossNeighbourKey.x = key.x;
		potentialCrossNeighbourKey.y = key.y - equalInt;
		potentialCrossNeighbourKey.z = key.z + bigInt;

		if (map.containsKey(potentialCrossNeighbourKey)
				&& map.get(potentialCrossNeighbourKey).getLevelOfDivision() + 1 == map
						.get(key).getLevelOfDivision())
			return potentialCrossNeighbourKey;

		potentialCrossNeighbourKey.x -= equalInt;
		if (map.containsKey(potentialCrossNeighbourKey)
				&& map.get(potentialCrossNeighbourKey).getLevelOfDivision() + 1 == map
						.get(key).getLevelOfDivision())
			return potentialCrossNeighbourKey;

		return null;

	}

	public HashMapKey getPotentialBotFarCrossBigNeighbourKey(HashMapKey key) {

		HashMapCube cube = map.get(key);
		int bigInt = getBigInt(cube.getLevelOfDivision());
		int equalInt = bigInt / 2;

		HashMapKey potentialCrossNeighbourKey = new HashMapKey(0, 0, 0);

		potentialCrossNeighbourKey.x = key.x;
		potentialCrossNeighbourKey.y = key.y + bigInt;
		potentialCrossNeighbourKey.z = key.z - equalInt;

		if (map.containsKey(potentialCrossNeighbourKey)
				&& map.get(potentialCrossNeighbourKey).getLevelOfDivision() + 1 == map
						.get(key).getLevelOfDivision())
			return potentialCrossNeighbourKey;

		potentialCrossNeighbourKey.x -= equalInt;
		if (map.containsKey(potentialCrossNeighbourKey)
				&& map.get(potentialCrossNeighbourKey).getLevelOfDivision() + 1 == map
						.get(key).getLevelOfDivision())
			return potentialCrossNeighbourKey;

		return null;

	}

	public HashMapKey getPotentialBotNearCrossBigNeighbourKey(HashMapKey key) {

		HashMapCube cube = map.get(key);
		int bigInt = getBigInt(cube.getLevelOfDivision());
		int equalInt = bigInt / 2;

		HashMapKey potentialCrossNeighbourKey = new HashMapKey(0, 0, 0);

		potentialCrossNeighbourKey.x = key.x;
		potentialCrossNeighbourKey.y = key.y - equalInt;
		potentialCrossNeighbourKey.z = key.z - equalInt;

		if (map.containsKey(potentialCrossNeighbourKey)
				&& map.get(potentialCrossNeighbourKey).getLevelOfDivision() + 1 == map
						.get(key).getLevelOfDivision())
			return potentialCrossNeighbourKey;

		potentialCrossNeighbourKey.x -= equalInt;
		if (map.containsKey(potentialCrossNeighbourKey)
				&& map.get(potentialCrossNeighbourKey).getLevelOfDivision() + 1 == map
						.get(key).getLevelOfDivision())
			return potentialCrossNeighbourKey;

		return null;

	}

	public HashMapKey getPotentialLeftBigNeighbourKey(HashMapKey key) {

		// kolejnosc szukania na scianie: najpierw o rozmiar wiekszy, potem
		// rowny rozmiarem, a potem mniejsze
		HashMapCube cube = map.get(key);

		int bigInt = getBigInt(cube.getLevelOfDivision());
		int equalInt = bigInt / 2;

		HashMapKey potentialNeighbourKey = new HashMapKey(0, 0, 0);

		// sasiad wiekszy

		potentialNeighbourKey.x = key.x - bigInt;
		potentialNeighbourKey.y = key.y;
		potentialNeighbourKey.z = key.z;
		if (map.containsKey(potentialNeighbourKey)
				&& map.get(potentialNeighbourKey).getLevelOfDivision() + 1 == map
						.get(key).getLevelOfDivision())
			return potentialNeighbourKey;

		potentialNeighbourKey.y += equalInt;
		if (map.containsKey(potentialNeighbourKey)
				&& map.get(potentialNeighbourKey).getLevelOfDivision() + 1 == map
						.get(key).getLevelOfDivision())
			return potentialNeighbourKey;

		potentialNeighbourKey.z += equalInt;
		if (map.containsKey(potentialNeighbourKey)
				&& map.get(potentialNeighbourKey).getLevelOfDivision() + 1 == map
						.get(key).getLevelOfDivision())
			return potentialNeighbourKey;

		potentialNeighbourKey.y -= equalInt;
		if (map.containsKey(potentialNeighbourKey)
				&& map.get(potentialNeighbourKey).getLevelOfDivision() + 1 == map
						.get(key).getLevelOfDivision())
			return potentialNeighbourKey;

		return null;

	}

	public HashMapKey getPotentialRightBigNeighbourKey(HashMapKey key) {

		// kolejnosc szukania na scianie: najpierw o rozmiar wiekszy, potem
		// rowny rozmiarem, a potem mniejsze
		HashMapCube cube = map.get(key);

		int bigInt = getBigInt(cube.getLevelOfDivision());
		int equalInt = bigInt / 2;

		HashMapKey potentialNeighbourKey = new HashMapKey(0, 0, 0);

		// sasiad wiekszy

		potentialNeighbourKey.x = key.x + equalInt;
		potentialNeighbourKey.y = key.y;
		potentialNeighbourKey.z = key.z;

		if (map.containsKey(potentialNeighbourKey)
				&& map.get(potentialNeighbourKey).getLevelOfDivision() + 1 == map
						.get(key).getLevelOfDivision())
			return potentialNeighbourKey;

		potentialNeighbourKey.y += equalInt;

		if (map.containsKey(potentialNeighbourKey)
				&& map.get(potentialNeighbourKey).getLevelOfDivision() + 1 == map
						.get(key).getLevelOfDivision())
			return potentialNeighbourKey;

		potentialNeighbourKey.z += equalInt;

		if (map.containsKey(potentialNeighbourKey)
				&& map.get(potentialNeighbourKey).getLevelOfDivision() + 1 == map
						.get(key).getLevelOfDivision())
			return potentialNeighbourKey;

		potentialNeighbourKey.y -= equalInt;
		if (map.containsKey(potentialNeighbourKey)
				&& map.get(potentialNeighbourKey).getLevelOfDivision() + 1 == map
						.get(key).getLevelOfDivision())
			return potentialNeighbourKey;

		return null;

	}

	public HashMapKey getPotentialTopBigNeighbourKey(HashMapKey key) {

		// kolejnosc szukania na scianie: najpierw o rozmiar wiekszy, potem
		// rowny rozmiarem, a potem mniejsze
		HashMapCube cube = map.get(key);
		int bigInt = getBigInt(cube.getLevelOfDivision());
		int equalInt = bigInt / 2;

		HashMapKey potentialNeighbourKey = new HashMapKey(0, 0, 0);

		// sasiad wiekszy

		potentialNeighbourKey.x = key.x;
		potentialNeighbourKey.y = key.y;
		potentialNeighbourKey.z = key.z + bigInt;

		if (map.containsKey(potentialNeighbourKey)
				&& map.get(potentialNeighbourKey).getLevelOfDivision() + 1 == map
						.get(key).getLevelOfDivision())
			return potentialNeighbourKey;

		potentialNeighbourKey.y += equalInt;
		if (map.containsKey(potentialNeighbourKey)
				&& map.get(potentialNeighbourKey).getLevelOfDivision() + 1 == map
						.get(key).getLevelOfDivision())
			return potentialNeighbourKey;

		potentialNeighbourKey.x -= equalInt;
		if (map.containsKey(potentialNeighbourKey)
				&& map.get(potentialNeighbourKey).getLevelOfDivision() + 1 == map
						.get(key).getLevelOfDivision())
			return potentialNeighbourKey;

		potentialNeighbourKey.y -= equalInt;
		if (map.containsKey(potentialNeighbourKey)
				&& map.get(potentialNeighbourKey).getLevelOfDivision() + 1 == map
						.get(key).getLevelOfDivision())
			return potentialNeighbourKey;

		return null;

	}

	public HashMapKey getPotentialBotBigNeighbourKey(HashMapKey key) {

		// kolejnosc szukania na scianie: najpierw o rozmiar wiekszy, potem
		// rowny rozmiarem, a potem mniejsze
		HashMapCube cube = map.get(key);

		int bigInt = getBigInt(cube.getLevelOfDivision());
		int equalInt = bigInt / 2;

		HashMapKey potentialNeighbourKey = new HashMapKey(0, 0, 0);

		// sasiad wiekszy

		potentialNeighbourKey.x = key.x;
		potentialNeighbourKey.y = key.y;
		potentialNeighbourKey.z = key.z - equalInt;

		if (map.containsKey(potentialNeighbourKey)
				&& map.get(potentialNeighbourKey).getLevelOfDivision() + 1 == map
						.get(key).getLevelOfDivision())
			return potentialNeighbourKey;

		potentialNeighbourKey.y += equalInt;
		if (map.containsKey(potentialNeighbourKey)
				&& map.get(potentialNeighbourKey).getLevelOfDivision() + 1 == map
						.get(key).getLevelOfDivision())
			return potentialNeighbourKey;

		potentialNeighbourKey.x -= equalInt;
		if (map.containsKey(potentialNeighbourKey)
				&& map.get(potentialNeighbourKey).getLevelOfDivision() + 1 == map
						.get(key).getLevelOfDivision())
			return potentialNeighbourKey;

		potentialNeighbourKey.y -= equalInt;
		if (map.containsKey(potentialNeighbourKey)
				&& map.get(potentialNeighbourKey).getLevelOfDivision() + 1 == map
						.get(key).getLevelOfDivision())
			return potentialNeighbourKey;

		return null;

	}

	public HashMapKey getPotentialNearBigNeighbourKey(HashMapKey key) {

		// kolejnosc szukania na scianie: najpierw o rozmiar wiekszy, potem
		// rowny rozmiarem, a potem mniejsze
		HashMapCube cube = map.get(key);

		int bigInt = getBigInt(cube.getLevelOfDivision());
		int equalInt = bigInt / 2;
		HashMapKey potentialNeighbourKey = new HashMapKey(0, 0, 0);

		// sasiad wiekszy

		potentialNeighbourKey.x = key.x;
		potentialNeighbourKey.y = key.y - equalInt;
		potentialNeighbourKey.z = key.z;
		if (map.containsKey(potentialNeighbourKey)
				&& map.get(potentialNeighbourKey).getLevelOfDivision() + 1 == map
						.get(key).getLevelOfDivision())
			return potentialNeighbourKey;

		potentialNeighbourKey.x -= equalInt;
		if (map.containsKey(potentialNeighbourKey)
				&& map.get(potentialNeighbourKey).getLevelOfDivision() + 1 == map
						.get(key).getLevelOfDivision())
			return potentialNeighbourKey;

		potentialNeighbourKey.z += equalInt;
		if (map.containsKey(potentialNeighbourKey)
				&& map.get(potentialNeighbourKey).getLevelOfDivision() + 1 == map
						.get(key).getLevelOfDivision())
			return potentialNeighbourKey;

		potentialNeighbourKey.x += equalInt;
		if (map.containsKey(potentialNeighbourKey)
				&& map.get(potentialNeighbourKey).getLevelOfDivision() + 1 == map
						.get(key).getLevelOfDivision())
			return potentialNeighbourKey;

		return null;

	}

	public HashMapKey getPotentialFarBigNeighbourKey(HashMapKey key) {

		// kolejnosc szukania na scianie: najpierw o rozmiar wiekszy, potem
		// rowny rozmiarem, a potem mniejsze
		HashMapCube cube = map.get(key);

		int bigInt = getBigInt(cube.getLevelOfDivision());
		int equalInt = bigInt / 2;

		HashMapKey potentialNeighbourKey = new HashMapKey(0, 0, 0);

		// sasiad wiekszy

		potentialNeighbourKey.x = key.x;
		potentialNeighbourKey.y = key.y + bigInt;
		potentialNeighbourKey.z = key.z;
		if (map.containsKey(potentialNeighbourKey)
				&& map.get(potentialNeighbourKey).getLevelOfDivision() + 1 == map
						.get(key).getLevelOfDivision())
			return potentialNeighbourKey;

		potentialNeighbourKey.x -= equalInt;
		if (map.containsKey(potentialNeighbourKey)
				&& map.get(potentialNeighbourKey).getLevelOfDivision() + 1 == map
						.get(key).getLevelOfDivision())
			return potentialNeighbourKey;

		potentialNeighbourKey.z += equalInt;
		if (map.containsKey(potentialNeighbourKey)
				&& map.get(potentialNeighbourKey).getLevelOfDivision() + 1 == map
						.get(key).getLevelOfDivision())
			return potentialNeighbourKey;

		potentialNeighbourKey.x += equalInt;
		if (map.containsKey(potentialNeighbourKey)
				&& map.get(potentialNeighbourKey).getLevelOfDivision() + 1 == map
						.get(key).getLevelOfDivision())
			return potentialNeighbourKey;

		return null;

	}

	// /////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	// pamietaj ze w metodach poniezej brakuje sprawdzania rozmiaru! komentarze
	// w ostatniej metodzie moga sie przydac

	// max 6*4 = 24 min 6*1 = 6
	/*
	 * public void printPotentialNeighbours(HashMapKey key){
	 * System.out.println("left\n"); printPotentialLeftNeighbours(key);
	 * System.out.println("right\n"); printPotentialRightNeighbours(key);
	 * System.out.println("top\n"); printPotentialTopNeighbours(key);
	 * System.out.println("bottom\n"); printPotentialBotNeighbours(key);
	 * System.out.println("near\n"); printPotentialNearNeighbours(key);
	 * System.out.println("far\n"); printPotentialFarNeighbours(key);
	 * 
	 * }
	 */

	/*
	 * public void printPotentialLeftNeighbours(HashMapKey key){
	 * 
	 * //kolejnosc szukania na scianie: najpierw o rozmiar wiekszy, potem rowny
	 * rozmiarem, a potem mniejsze HashMapCube cube = map.get(key);
	 * 
	 * int bigInt = getBigInt(cube.getLevelOfDivision()); int equalInt =
	 * bigInt/2; int smallInt = equalInt/4;
	 * 
	 * int equalLvl = cube.getLevelOfDivision(); int greaterLvl = equalLvl + 1;
	 * int lesserLvl = equalLvl -1;
	 * 
	 * HashMapKey potentialNeighbourKey = new HashMapKey(0,0,0);
	 * 
	 * 
	 * //sasiad wiekszy
	 * 
	 * potentialNeighbourKey.x = key.x - bigInt; potentialNeighbourKey.y =
	 * key.y; potentialNeighbourKey.z = key.z;
	 * 
	 * if(map.containsKey(potentialNeighbourKey) &&
	 * map.get(potentialNeighbourKey).getLevelOfDivision()==lesserLvl)
	 * potentialNeighbourKey.printKey();
	 * 
	 * potentialNeighbourKey.y += equalInt;
	 * 
	 * 
	 * if(map.containsKey(potentialNeighbourKey) &&
	 * map.get(potentialNeighbourKey).getLevelOfDivision()==lesserLvl)
	 * potentialNeighbourKey.printKey();
	 * 
	 * potentialNeighbourKey.z += equalInt;
	 * 
	 * 
	 * if(map.containsKey(potentialNeighbourKey) &&
	 * map.get(potentialNeighbourKey).getLevelOfDivision()==lesserLvl)
	 * potentialNeighbourKey.printKey();
	 * 
	 * 
	 * potentialNeighbourKey.y -= equalInt;
	 * 
	 * 
	 * if(map.containsKey(potentialNeighbourKey) &&
	 * map.get(potentialNeighbourKey).getLevelOfDivision()==lesserLvl)
	 * potentialNeighbourKey.printKey();
	 * 
	 * //sasiad rowny
	 * 
	 * potentialNeighbourKey.x = key.x - equalInt; potentialNeighbourKey.y =
	 * key.y; potentialNeighbourKey.z = key.z;
	 * 
	 * if(map.containsKey(potentialNeighbourKey) &&
	 * map.get(potentialNeighbourKey).getLevelOfDivision()==equalLvl)
	 * potentialNeighbourKey.printKey();
	 * 
	 * //sasiad mniejszy
	 * 
	 * potentialNeighbourKey.x = key.x - smallInt; potentialNeighbourKey.y =
	 * key.y; potentialNeighbourKey.z = key.z;
	 * 
	 * if(map.containsKey(potentialNeighbourKey) &&
	 * map.get(potentialNeighbourKey).getLevelOfDivision()==greaterLvl)
	 * potentialNeighbourKey.printKey(); //jesli znajdzie sie taki sasiad to
	 * pozotalych 4 tez powinno zawsze byc
	 * 
	 * potentialNeighbourKey.y -= smallInt;
	 * if(map.containsKey(potentialNeighbourKey) &&
	 * map.get(potentialNeighbourKey).getLevelOfDivision()==greaterLvl)
	 * potentialNeighbourKey.printKey();
	 * 
	 * potentialNeighbourKey.z -= smallInt;
	 * if(map.containsKey(potentialNeighbourKey) &&
	 * map.get(potentialNeighbourKey).getLevelOfDivision()==greaterLvl)
	 * potentialNeighbourKey.printKey();
	 * 
	 * potentialNeighbourKey.y += smallInt;
	 * if(map.containsKey(potentialNeighbourKey) &&
	 * map.get(potentialNeighbourKey).getLevelOfDivision()==greaterLvl)
	 * potentialNeighbourKey.printKey();
	 * 
	 * 
	 * }
	 * 
	 * public void printPotentialRightNeighbours(HashMapKey key){
	 * 
	 * //kolejnosc szukania na scianie: najpierw o rozmiar wiekszy, potem rowny
	 * rozmiarem, a potem mniejsze HashMapCube cube = map.get(key);
	 * 
	 * int bigInt = getBigInt(cube.getLevelOfDivision()); int equalInt =
	 * bigInt/2; int smallInt = equalInt/4;
	 * 
	 * int equalLvl = cube.getLevelOfDivision(); int greaterLvl = equalLvl + 1;
	 * int lesserLvl = equalLvl -1;
	 * 
	 * HashMapKey potentialNeighbourKey = new HashMapKey(0,0,0);
	 * 
	 * 
	 * //sasiad wiekszy
	 * 
	 * potentialNeighbourKey.x = key.x + equalInt; potentialNeighbourKey.y =
	 * key.y; potentialNeighbourKey.z = key.z;
	 * 
	 * if(map.containsKey(potentialNeighbourKey) &&
	 * map.get(potentialNeighbourKey).getLevelOfDivision()==lesserLvl)
	 * potentialNeighbourKey.printKey();
	 * 
	 * 
	 * potentialNeighbourKey.y += equalInt;
	 * 
	 * if(map.containsKey(potentialNeighbourKey) &&
	 * map.get(potentialNeighbourKey).getLevelOfDivision()==lesserLvl)
	 * potentialNeighbourKey.printKey();
	 * 
	 * potentialNeighbourKey.z += equalInt;
	 * 
	 * if(map.containsKey(potentialNeighbourKey) &&
	 * map.get(potentialNeighbourKey).getLevelOfDivision()==lesserLvl)
	 * potentialNeighbourKey.printKey();
	 * 
	 * potentialNeighbourKey.y -= equalInt;
	 * 
	 * if(map.containsKey(potentialNeighbourKey) &&
	 * map.get(potentialNeighbourKey).getLevelOfDivision()==lesserLvl)
	 * potentialNeighbourKey.printKey();
	 * 
	 * //sasiad rowny
	 * 
	 * potentialNeighbourKey.x = key.x + equalInt; potentialNeighbourKey.y =
	 * key.y; potentialNeighbourKey.z = key.z;
	 * 
	 * if(map.containsKey(potentialNeighbourKey) &&
	 * map.get(potentialNeighbourKey).getLevelOfDivision()==equalLvl)
	 * potentialNeighbourKey.printKey();
	 * 
	 * //sasiad mniejszy
	 * 
	 * potentialNeighbourKey.x = key.x + equalInt; potentialNeighbourKey.y =
	 * key.y; potentialNeighbourKey.z = key.z;
	 * 
	 * if(map.containsKey(potentialNeighbourKey) &&
	 * map.get(potentialNeighbourKey).getLevelOfDivision()==greaterLvl)
	 * potentialNeighbourKey.printKey();
	 * 
	 * //jesli znajdzie sie taki sasiad to pozotalych 4 tez powinno zawsze byc
	 * 
	 * potentialNeighbourKey.y -= smallInt;
	 * if(map.containsKey(potentialNeighbourKey) &&
	 * map.get(potentialNeighbourKey).getLevelOfDivision()==greaterLvl)
	 * potentialNeighbourKey.printKey();
	 * 
	 * potentialNeighbourKey.z -= smallInt;
	 * if(map.containsKey(potentialNeighbourKey) &&
	 * map.get(potentialNeighbourKey).getLevelOfDivision()==greaterLvl)
	 * potentialNeighbourKey.printKey();
	 * 
	 * potentialNeighbourKey.y += smallInt;
	 * if(map.containsKey(potentialNeighbourKey) &&
	 * map.get(potentialNeighbourKey).getLevelOfDivision()==greaterLvl)
	 * potentialNeighbourKey.printKey();
	 * 
	 * 
	 * }
	 * 
	 * public void printPotentialTopNeighbours(HashMapKey key){
	 * 
	 * //kolejnosc szukania na scianie: najpierw o rozmiar wiekszy, potem rowny
	 * rozmiarem, a potem mniejsze HashMapCube cube = map.get(key);
	 * 
	 * int bigInt = getBigInt(cube.getLevelOfDivision()); int equalInt =
	 * bigInt/2; int smallInt = equalInt/4;
	 * 
	 * 
	 * HashMapKey potentialNeighbourKey = new HashMapKey(0,0,0);
	 * 
	 * int equalLvl = cube.getLevelOfDivision(); int greaterLvl = equalLvl + 1;
	 * int lesserLvl = equalLvl -1;
	 * 
	 * //sasiad wiekszy
	 * 
	 * potentialNeighbourKey.x = key.x; potentialNeighbourKey.y = key.y;
	 * potentialNeighbourKey.z = key.z + bigInt;
	 * 
	 * if(map.containsKey(potentialNeighbourKey) &&
	 * map.get(potentialNeighbourKey).getLevelOfDivision()==lesserLvl)
	 * potentialNeighbourKey.printKey();
	 * 
	 * 
	 * potentialNeighbourKey.y += equalInt;
	 * if(map.containsKey(potentialNeighbourKey) &&
	 * map.get(potentialNeighbourKey).getLevelOfDivision()==lesserLvl)
	 * potentialNeighbourKey.printKey();
	 * 
	 * 
	 * potentialNeighbourKey.x -= equalInt;
	 * if(map.containsKey(potentialNeighbourKey) &&
	 * map.get(potentialNeighbourKey).getLevelOfDivision()==lesserLvl)
	 * potentialNeighbourKey.printKey();
	 * 
	 * 
	 * potentialNeighbourKey.y -= equalInt;
	 * if(map.containsKey(potentialNeighbourKey) &&
	 * map.get(potentialNeighbourKey).getLevelOfDivision()==lesserLvl)
	 * potentialNeighbourKey.printKey();
	 * 
	 * //sasiad rowny
	 * 
	 * potentialNeighbourKey.x = key.x; potentialNeighbourKey.y = key.y;
	 * potentialNeighbourKey.z = key.z + equalInt;
	 * 
	 * if(map.containsKey(potentialNeighbourKey) &&
	 * map.get(potentialNeighbourKey).getLevelOfDivision()==equalLvl)
	 * potentialNeighbourKey.printKey();
	 * 
	 * //sasiad mniejszy
	 * 
	 * potentialNeighbourKey.x = key.x; potentialNeighbourKey.y = key.y;
	 * potentialNeighbourKey.z = key.z + smallInt;
	 * 
	 * if(map.containsKey(potentialNeighbourKey) &&
	 * map.get(potentialNeighbourKey).getLevelOfDivision()==greaterLvl)
	 * potentialNeighbourKey.printKey();
	 * 
	 * //jesli znajdzie sie taki sasiad to pozotalych 4 tez powinno zawsze byc
	 * 
	 * potentialNeighbourKey.y -= smallInt;
	 * if(map.containsKey(potentialNeighbourKey) &&
	 * map.get(potentialNeighbourKey).getLevelOfDivision()==greaterLvl)
	 * potentialNeighbourKey.printKey();
	 * 
	 * potentialNeighbourKey.x += smallInt;
	 * if(map.containsKey(potentialNeighbourKey) &&
	 * map.get(potentialNeighbourKey).getLevelOfDivision()==greaterLvl)
	 * potentialNeighbourKey.printKey();
	 * 
	 * potentialNeighbourKey.y += smallInt;
	 * if(map.containsKey(potentialNeighbourKey) &&
	 * map.get(potentialNeighbourKey).getLevelOfDivision()==greaterLvl)
	 * potentialNeighbourKey.printKey();
	 * 
	 * 
	 * }
	 * 
	 * public void printPotentialBotNeighbours(HashMapKey key){
	 * 
	 * //kolejnosc szukania na scianie: najpierw o rozmiar wiekszy, potem rowny
	 * rozmiarem, a potem mniejsze HashMapCube cube = map.get(key);
	 * 
	 * int bigInt = getBigInt(cube.getLevelOfDivision()); int equalInt =
	 * bigInt/2; int smallInt = equalInt/4;
	 * 
	 * 
	 * HashMapKey potentialNeighbourKey = new HashMapKey(0,0,0);
	 * 
	 * int equalLvl = cube.getLevelOfDivision(); int greaterLvl = equalLvl + 1;
	 * int lesserLvl = equalLvl -1;
	 * 
	 * 
	 * //sasiad wiekszy
	 * 
	 * potentialNeighbourKey.x = key.x; potentialNeighbourKey.y = key.y;
	 * potentialNeighbourKey.z = key.z - equalInt;
	 * if(map.containsKey(potentialNeighbourKey) &&
	 * map.get(potentialNeighbourKey).getLevelOfDivision()==lesserLvl)
	 * potentialNeighbourKey.printKey();
	 * 
	 * 
	 * potentialNeighbourKey.y += equalInt;
	 * if(map.containsKey(potentialNeighbourKey) &&
	 * map.get(potentialNeighbourKey).getLevelOfDivision()==lesserLvl)
	 * potentialNeighbourKey.printKey();
	 * 
	 * 
	 * potentialNeighbourKey.x -= equalInt;
	 * if(map.containsKey(potentialNeighbourKey) &&
	 * map.get(potentialNeighbourKey).getLevelOfDivision()==lesserLvl)
	 * potentialNeighbourKey.printKey();
	 * 
	 * 
	 * potentialNeighbourKey.y -= equalInt;
	 * if(map.containsKey(potentialNeighbourKey) &&
	 * map.get(potentialNeighbourKey).getLevelOfDivision()==lesserLvl)
	 * potentialNeighbourKey.printKey();
	 * 
	 * //sasiad rowny
	 * 
	 * potentialNeighbourKey.x = key.x; potentialNeighbourKey.y = key.y;
	 * potentialNeighbourKey.z = key.z - equalInt;
	 * 
	 * if(map.containsKey(potentialNeighbourKey) &&
	 * map.get(potentialNeighbourKey).getLevelOfDivision()==equalLvl)
	 * potentialNeighbourKey.printKey();
	 * 
	 * //sasiad mniejszy
	 * 
	 * potentialNeighbourKey.x = key.x; potentialNeighbourKey.y = key.y;
	 * potentialNeighbourKey.z = key.z - equalInt;
	 * 
	 * if(map.containsKey(potentialNeighbourKey) &&
	 * map.get(potentialNeighbourKey).getLevelOfDivision()==greaterLvl)
	 * potentialNeighbourKey.printKey();
	 * 
	 * //jesli znajdzie sie taki sasiad to pozotalych 4 tez powinno zawsze byc
	 * 
	 * potentialNeighbourKey.y -= smallInt;
	 * if(map.containsKey(potentialNeighbourKey) &&
	 * map.get(potentialNeighbourKey).getLevelOfDivision()==greaterLvl)
	 * potentialNeighbourKey.printKey();
	 * 
	 * potentialNeighbourKey.x += smallInt;
	 * if(map.containsKey(potentialNeighbourKey) &&
	 * map.get(potentialNeighbourKey).getLevelOfDivision()==greaterLvl)
	 * potentialNeighbourKey.printKey();
	 * 
	 * potentialNeighbourKey.y += smallInt;
	 * if(map.containsKey(potentialNeighbourKey) &&
	 * map.get(potentialNeighbourKey).getLevelOfDivision()==greaterLvl)
	 * potentialNeighbourKey.printKey();
	 * 
	 * 
	 * }
	 * 
	 * public void printPotentialNearNeighbours(HashMapKey key){
	 * 
	 * //kolejnosc szukania na scianie: najpierw o rozmiar wiekszy, potem rowny
	 * rozmiarem, a potem mniejsze HashMapCube cube = map.get(key);
	 * 
	 * int bigInt = getBigInt(cube.getLevelOfDivision()); int equalInt =
	 * bigInt/2; int smallInt = equalInt/4;
	 * 
	 * int equalLvl = cube.getLevelOfDivision(); int greaterLvl = equalLvl + 1;
	 * int lesserLvl = equalLvl -1;
	 * 
	 * 
	 * HashMapKey potentialNeighbourKey = new HashMapKey(0,0,0);
	 * 
	 * 
	 * //sasiad wiekszy
	 * 
	 * potentialNeighbourKey.x = key.x; potentialNeighbourKey.y = key.y -
	 * equalInt; potentialNeighbourKey.z = key.z;
	 * 
	 * if(map.containsKey(potentialNeighbourKey) &&
	 * map.get(potentialNeighbourKey).getLevelOfDivision()==lesserLvl)
	 * potentialNeighbourKey.printKey();
	 * 
	 * 
	 * potentialNeighbourKey.x -= equalInt;
	 * if(map.containsKey(potentialNeighbourKey) &&
	 * map.get(potentialNeighbourKey).getLevelOfDivision()==lesserLvl)
	 * potentialNeighbourKey.printKey();
	 * 
	 * 
	 * potentialNeighbourKey.z += equalInt;
	 * if(map.containsKey(potentialNeighbourKey) &&
	 * map.get(potentialNeighbourKey).getLevelOfDivision()==lesserLvl)
	 * potentialNeighbourKey.printKey();
	 * 
	 * 
	 * potentialNeighbourKey.x += equalInt;
	 * if(map.containsKey(potentialNeighbourKey) &&
	 * map.get(potentialNeighbourKey).getLevelOfDivision()==lesserLvl)
	 * potentialNeighbourKey.printKey();
	 * 
	 * //sasiad rowny
	 * 
	 * potentialNeighbourKey.x = key.x; potentialNeighbourKey.y = key.y -
	 * equalInt; potentialNeighbourKey.z = key.z;
	 * 
	 * if(map.containsKey(potentialNeighbourKey) &&
	 * map.get(potentialNeighbourKey).getLevelOfDivision()==equalLvl)
	 * potentialNeighbourKey.printKey();
	 * 
	 * //sasiad mniejszy
	 * 
	 * potentialNeighbourKey.x = key.x; potentialNeighbourKey.y = key.y -
	 * equalInt; potentialNeighbourKey.z = key.z;
	 * 
	 * if(map.containsKey(potentialNeighbourKey) &&
	 * map.get(potentialNeighbourKey).getLevelOfDivision()==greaterLvl)
	 * potentialNeighbourKey.printKey();
	 * 
	 * //jesli znajdzie sie taki sasiad to pozotalych 4 tez powinno zawsze byc
	 * 
	 * potentialNeighbourKey.x += smallInt;
	 * if(map.containsKey(potentialNeighbourKey) &&
	 * map.get(potentialNeighbourKey).getLevelOfDivision()==greaterLvl)
	 * potentialNeighbourKey.printKey();
	 * 
	 * potentialNeighbourKey.z -= smallInt;
	 * if(map.containsKey(potentialNeighbourKey) &&
	 * map.get(potentialNeighbourKey).getLevelOfDivision()==greaterLvl)
	 * potentialNeighbourKey.printKey();
	 * 
	 * potentialNeighbourKey.x -= smallInt;
	 * if(map.containsKey(potentialNeighbourKey) &&
	 * map.get(potentialNeighbourKey).getLevelOfDivision()==greaterLvl)
	 * potentialNeighbourKey.printKey();
	 * 
	 * 
	 * }
	 * 
	 * 
	 * public void printPotentialFarNeighbours(HashMapKey key){
	 * 
	 * //kolejnosc szukania na scianie: najpierw o rozmiar wiekszy, potem rowny
	 * rozmiarem, a potem mniejsze HashMapCube cube = map.get(key);
	 * 
	 * int bigInt = getBigInt(cube.getLevelOfDivision()); int equalInt =
	 * bigInt/2; int smallInt = equalInt/4;
	 * 
	 * HashMapKey potentialNeighbourKey = new HashMapKey(0,0,0);
	 * 
	 * int equalLvl = cube.getLevelOfDivision(); int greaterLvl = equalLvl + 1;
	 * int lesserLvl = equalLvl -1;
	 * 
	 * //sasiad wiekszy
	 * 
	 * potentialNeighbourKey.x = key.x; potentialNeighbourKey.y = key.y +
	 * bigInt; potentialNeighbourKey.z = key.z;
	 * if(map.containsKey(potentialNeighbourKey) &&
	 * map.get(potentialNeighbourKey).getLevelOfDivision()==lesserLvl)
	 * potentialNeighbourKey.printKey();
	 * 
	 * 
	 * potentialNeighbourKey.x -= equalInt;
	 * if(map.containsKey(potentialNeighbourKey) &&
	 * map.get(potentialNeighbourKey).getLevelOfDivision()==lesserLvl)
	 * potentialNeighbourKey.printKey();
	 * 
	 * 
	 * potentialNeighbourKey.z += equalInt;
	 * if(map.containsKey(potentialNeighbourKey) &&
	 * map.get(potentialNeighbourKey).getLevelOfDivision()==lesserLvl)
	 * potentialNeighbourKey.printKey();
	 * 
	 * 
	 * potentialNeighbourKey.x += equalInt;
	 * if(map.containsKey(potentialNeighbourKey) &&
	 * map.get(potentialNeighbourKey).getLevelOfDivision()==lesserLvl)
	 * potentialNeighbourKey.printKey();
	 * 
	 * //sasiad rowny
	 * 
	 * potentialNeighbourKey.x = key.x; potentialNeighbourKey.y = key.y +
	 * equalInt; potentialNeighbourKey.z = key.z;
	 * if(map.containsKey(potentialNeighbourKey) &&
	 * map.get(potentialNeighbourKey).getLevelOfDivision()==equalLvl)
	 * potentialNeighbourKey.printKey();
	 * 
	 * //sasiad mniejszy
	 * 
	 * potentialNeighbourKey.x = key.x; potentialNeighbourKey.y = key.y +
	 * smallInt; potentialNeighbourKey.z = key.z;
	 * if(map.containsKey(potentialNeighbourKey) &&
	 * map.get(potentialNeighbourKey).getLevelOfDivision()==greaterLvl)
	 * potentialNeighbourKey.printKey();
	 * 
	 * //jesli znajdzie sie taki sasiad to pozotalych 4 tez powinno zawsze byc
	 * 
	 * potentialNeighbourKey.x += smallInt;
	 * if(map.containsKey(potentialNeighbourKey) &&
	 * map.get(potentialNeighbourKey).getLevelOfDivision()==greaterLvl)
	 * potentialNeighbourKey.printKey();
	 * 
	 * potentialNeighbourKey.z -= smallInt;
	 * if(map.containsKey(potentialNeighbourKey) &&
	 * map.get(potentialNeighbourKey).getLevelOfDivision()==greaterLvl)
	 * potentialNeighbourKey.printKey();
	 * 
	 * potentialNeighbourKey.x -= smallInt;
	 * if(map.containsKey(potentialNeighbourKey) &&
	 * map.get(potentialNeighbourKey).getLevelOfDivision()==greaterLvl)
	 * potentialNeighbourKey.printKey();
	 * 
	 * 
	 * }
	 * 
	 * 
	 * 
	 * public void divideAll(){
	 * 
	 * }
	 */

}
