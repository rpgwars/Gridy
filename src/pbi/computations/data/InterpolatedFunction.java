package pbi.computations.data;

import pbi.computations.function.TripArgFunction;

public interface InterpolatedFunction extends TripArgFunction {

	double computeDxValue(double x, double y, double z);

	double computeDyValue(double x, double y, double z);

	double computeDzValue(double x, double y, double z);
	/*
	 * protected boolean dx; protected boolean dy; protected boolean dz;
	 * 
	 * 
	 * public void setDx() { dx = true; dy = false; dz = false;
	 * 
	 * }
	 * 
	 * 
	 * 
	 * public void setDy() { dx = false; dy = true; dz = false;
	 * 
	 * }
	 * 
	 * 
	 * 
	 * public void setDz() { dx = false; dy = false; dz = true;
	 * 
	 * }
	 * 
	 * 
	 * 
	 * public void setNormal() { dx = false; dy = false; dz = false;
	 * 
	 * }
	 * 
	 * public double computeValue(double[] point){ return computeValue(point[0],
	 * point[1], point[2]); }
	 */

}
