package pbi.computations.part;



import pbi.computations.data.InterpolatedFunction;
import pbi.computations.function.BaseFunctionDerivativeCombination;
import pbi.computations.function.BaseFunctionWrapper;
import pbi.computations.function.Dimension;
import pbi.computations.function.EdgeBaseFunctions;
import pbi.computations.function.FaceBaseFunctions;
import pbi.computations.function.InteriorBaseFunction;
import pbi.computations.function.PointBaseFunctions;
import pbi.computations.function.TripArgFunction;
import pbi.computations.function.TripArgFunctionProduct;
import pbi.computations.integrals.GaussianQuadrature;


class InterpolatedFunctionWrapper implements TripArgFunction{
	
	private boolean dx; 
	private boolean dy; 
	private boolean dz; 
	
	private InterpolatedFunction interpolatedFunction; 
	public InterpolatedFunctionWrapper(InterpolatedFunction interpolatedFunction){
		this.interpolatedFunction = interpolatedFunction;
		dz = false; 
		dy = false; 
		dx = false; 
	}
	
	@Override
	public double computeValue(double x, double y, double z) {
		if(dx)
			return interpolatedFunction.computeDxValue(x, y, z);
		if(dy)
			return interpolatedFunction.computeDyValue(x, y, z);
		if(dz)
			return interpolatedFunction.computeDzValue(x, y, z);
		return interpolatedFunction.computeValue(x, y, z);
	}
	
	public double computeValue(double[] point){
		return computeValue(point[0], point[1], point[2]);
	}
	
	public void setNormal(){
		dx = false; 
		dy = false; 
		dz = false; 
	}
	
	public void setDx(){
		dx = true; 
		dy = false; 
		dz = false;
	}
	
	public void setDy(){
		dx = false; 
		dy = true; 
		dz = false;
	}
	
	public void setDz(){
		dx = false; 
		dy = false; 
		dz = true;
	}
	
	
}

public abstract class ComputationCube extends Cube implements TripArgFunction{

	
	protected boolean leftFace; 
	protected boolean rightFace; 
	protected boolean topFace; 
	protected boolean botFace; 
	protected boolean farFace; 
	protected boolean nearFace; 
	
	
	protected boolean leftTopEdge; 
	protected boolean leftBotEdge; 
	protected boolean leftFarEdge; 
	protected boolean leftNearEdge;
	
	protected boolean rightTopEdge; 
	protected boolean rightBotEdge; 
	protected boolean rightFarEdge; 
	protected boolean rightNearEdge; 
	
	protected boolean topFarEdge; 
	protected boolean topNearEdge; 
	protected boolean botFarEdge; 
	protected boolean botNearEdge; 
	
	protected boolean leftBotNearPoint;
	protected boolean rightBotNearPoint;
	protected boolean rightBotFarPoint;
	protected boolean leftBotFarPoint;
	
	protected boolean leftTopNearPoint;
	protected boolean rightTopNearPoint;
	protected boolean rightTopFarPoint;
	protected boolean leftTopFarPoint; 
	
	
//	private double xTopLeftFar = currentCoordinates[0]; 
//	private double yTopLeftFar = currentCoordinates[1]; 
//	private double zTopLeftFar = currentCoordinates[2]; 
//	private double xBotRightNear = currentCoordinates[3]; 
//	private double yBotRightNear = currentCoordinates[4]; 
//	private double zBotRightNear = currentCoordinates[5];
	
	private double xDif = 1/(xBotRightNear - xTopLeftFar); 
	private double yDif = 1/(yTopLeftFar - yBotRightNear); 
	private double zDif = 1/(zTopLeftFar - zBotRightNear); 
	private double[] mapping = new double[6];
	
	private BaseFunctionDerivativeCombination pointDxCombination;
	private BaseFunctionDerivativeCombination pointDyCombination;
	private BaseFunctionDerivativeCombination pointDzCombination;
	
	private BaseFunctionDerivativeCombination edgeDxCombination;
	private BaseFunctionDerivativeCombination edgeDyCombination;
	private BaseFunctionDerivativeCombination edgeDzCombination;
	
	private BaseFunctionDerivativeCombination faceDxCombination;
	private BaseFunctionDerivativeCombination faceDyCombination;
	private BaseFunctionDerivativeCombination faceDzCombination;
	
	private BaseFunctionDerivativeCombinationCombination dxCombinationCombination;
	private BaseFunctionDerivativeCombinationCombination dyCombinationCombination;
	private BaseFunctionDerivativeCombinationCombination dzCombinationCombination;
	
	
	protected InterpolatedFunctionWrapper interpolatedFunction;
	private GaussianQuadrature quadrature;
	
	private boolean errKLowEnough;  
	private double errK; 
	private java.util.Random random = new java.util.Random();
	
	
	public ComputationCube(double xTopLeftFar, double yTopLeftFar,
			double zTopLeftFar, double xBotRightNear, double yBotRightNear,
			double zBotRightNear, int levelOfDivision, double[] lastCoordinates, CubePosition cubePosition) {
		
		super(xTopLeftFar, yTopLeftFar, zTopLeftFar, xBotRightNear, yBotRightNear,
				zBotRightNear, levelOfDivision, lastCoordinates, cubePosition);
		 
		cooficients = new double[27]; 
		for(int i=0; i< 27 ; i++){
			cooficients[i] = 0.0; 
		}
		quadrature = new GaussianQuadrature();
		
		random.setSeed(System.currentTimeMillis());
		
		
	}
	
	
	public void setComputationCubes(InterpolatedFunction interpolatedFunction){
		leftFace = true; 
		rightFace = true; 
		topFace = true; 
		botFace = true; 
		farFace = true; 
		nearFace = true; 
		
		leftTopEdge = true; 
		leftBotEdge = true; 
		leftFarEdge = true; 
		leftNearEdge = true;
		rightTopEdge = true; 
		rightBotEdge = true; 
		rightFarEdge = true; 
		rightNearEdge = true; 
		topFarEdge = true; 
		topNearEdge = true; 
		botFarEdge = true; 
		botNearEdge = true; 
		
		leftBotNearPoint = true;
		rightBotNearPoint = true;
		rightBotFarPoint = true;
		leftBotFarPoint = true;
		
		leftTopNearPoint = true;
		rightTopNearPoint = true;
		rightTopFarPoint = true;
		leftTopFarPoint = true; 
		
		this.interpolatedFunction = new InterpolatedFunctionWrapper(interpolatedFunction);
	}
	
	private void setPointEdgeCooficients(double bigNeighbourEdgeCooficient, int commonEdgeIndex, int commonPointIndex,
			int distinctEdgeIndex, int distinctPointIndex){
		
		bigNeighbourEdgeCooficient /= 4.0;
		cooficients[commonEdgeIndex] += bigNeighbourEdgeCooficient; 
		cooficients[commonPointIndex] += bigNeighbourEdgeCooficient;
		bigNeighbourEdgeCooficient /= 2.0; 
		cooficients[distinctEdgeIndex] += bigNeighbourEdgeCooficient; 
		cooficients[distinctPointIndex] += bigNeighbourEdgeCooficient; 
				
		
	}
	
	private void setPointEdgeCooficients(double bigNeighbourEdgeCooficient,
			int distinctEdgeIndex, int distinctPointIndex){
		
		bigNeighbourEdgeCooficient /= 8.0; 
		cooficients[distinctEdgeIndex] += bigNeighbourEdgeCooficient; 
		cooficients[distinctPointIndex] += bigNeighbourEdgeCooficient;
		
	}
	
	private void setPointEdgeCooficientsOnCrossElement(double bigNeighbourEdgeCooficient, int commonEdgeIndex, int commonPointIndex){
		bigNeighbourEdgeCooficient/=4.0; 
		cooficients[commonEdgeIndex] += bigNeighbourEdgeCooficient; 
		cooficients[commonPointIndex] += bigNeighbourEdgeCooficient;
		
	}
	
	private void setPointEdgeCooficientsOnTwoFaces(double bigNeighbourEdgeCooficient1, int commonEdgeIndex, int commonPointIndex,
			int distinctEdgeIndex1, int distinctPointIndex1, double bigNeighbourEdgeCooficient2, int distinctEdgeIndex2, int distinctPointIndex2){
		
		
		bigNeighbourEdgeCooficient1/=4.0; 
		bigNeighbourEdgeCooficient2/=4.0;
		
		cooficients[commonEdgeIndex] += bigNeighbourEdgeCooficient1; 
		cooficients[commonPointIndex] += bigNeighbourEdgeCooficient1;
		
		bigNeighbourEdgeCooficient1/=2.0; 
		bigNeighbourEdgeCooficient2/=2.0;
		
		cooficients[distinctEdgeIndex1] +=bigNeighbourEdgeCooficient1;
		cooficients[distinctPointIndex1] +=bigNeighbourEdgeCooficient1;
		
		cooficients[distinctEdgeIndex2] +=bigNeighbourEdgeCooficient2;
		cooficients[distinctPointIndex2] +=bigNeighbourEdgeCooficient2;
		
	}
	
	private void setPointEdgeFaceCooficients(double bigNeighbourFaceCooficient, int distinctEdgeIndex1, int distinctEdgeIndex2, int faceIndex, int pointIndex){
		
		bigNeighbourFaceCooficient/=16.0;
		
		cooficients[faceIndex] += bigNeighbourFaceCooficient;
		cooficients[distinctEdgeIndex1] += bigNeighbourFaceCooficient;
		cooficients[distinctEdgeIndex2] += bigNeighbourFaceCooficient;
		cooficients[pointIndex] += bigNeighbourFaceCooficient; 

	}
	
	public void computeCooficients(InterpolatedFunction interpolated){
		
	
		InterpolatedFunctionWrapper interpolatedFunction; 
		if(interpolated == null){
			interpolatedFunction = this.interpolatedFunction;
		}
		else{
			interpolatedFunction = new InterpolatedFunctionWrapper(interpolated);
		}
		
		xTopLeftFar = currentCoordinates[0]; 
		yTopLeftFar = currentCoordinates[1]; 
		zTopLeftFar = currentCoordinates[2]; 
		xBotRightNear = currentCoordinates[3]; 
		yBotRightNear = currentCoordinates[4]; 
		zBotRightNear = currentCoordinates[5];
		
		xDif = 1/(xBotRightNear - xTopLeftFar); 
		yDif = 1/(yTopLeftFar - yBotRightNear); 
		zDif = 1/(zTopLeftFar - zBotRightNear); 

		mapping[0] = xTopLeftFar;
		mapping[1] = xBotRightNear; 
		mapping[2] = yBotRightNear; 
		mapping[3] = yTopLeftFar; 
		mapping[4] = zBotRightNear; 
		mapping[5] = zTopLeftFar; 
		
		interpolatedFunction.setNormal(); 
		
		computePointCooficients();
		

		//a9 -- a20
		
		computeEdgeCooficients(false);
		

		computeFaceCooficients(false);


		pointDxCombination = new BaseFunctionDerivativeCombination(8); 
		pointDxCombination.setBaseFunctionCombination(PointBaseFunctions.getPointBaseFunctionCombination(Dimension.dx), cooficients, 0);
		pointDxCombination.setDerivativeDirection(Dimension.dx);
		pointDxCombination.setMappings(mapping);
		
		pointDyCombination = new BaseFunctionDerivativeCombination(8); 
		pointDyCombination.setBaseFunctionCombination(PointBaseFunctions.getPointBaseFunctionCombination(Dimension.dy), cooficients, 0);
		pointDyCombination.setDerivativeDirection(Dimension.dy);
		pointDyCombination.setMappings(mapping);
		
		pointDzCombination = new BaseFunctionDerivativeCombination(8); 
		pointDzCombination.setBaseFunctionCombination(PointBaseFunctions.getPointBaseFunctionCombination(Dimension.dz), cooficients, 0);
		pointDzCombination.setDerivativeDirection(Dimension.dz);
		pointDzCombination.setMappings(mapping);
		
		
		computeEdgeCooficients(true);
		
//		if(this.levelOfDivision == 2 && this.cubePosition == CubePosition.BOT_LEFT_FAR)
//			System.out.println("cof after edge 2" + cooficients[4]);

		//a21 -- a26				
		
		
			
		edgeDxCombination = new BaseFunctionDerivativeCombination(12); 
		edgeDxCombination.setBaseFunctionCombination(EdgeBaseFunctions.getEdgeBaseFunctionCombination(Dimension.dx), cooficients, 8);
		edgeDxCombination.setDerivativeDirection(Dimension.dx);
		edgeDxCombination.setMappings(mapping);
		
		edgeDyCombination = new BaseFunctionDerivativeCombination(12); 
		edgeDyCombination.setBaseFunctionCombination(EdgeBaseFunctions.getEdgeBaseFunctionCombination(Dimension.dy), cooficients, 8);
		edgeDyCombination.setDerivativeDirection(Dimension.dy);
		edgeDyCombination.setMappings(mapping);
				
		edgeDzCombination = new BaseFunctionDerivativeCombination(12); 
		edgeDzCombination.setBaseFunctionCombination(EdgeBaseFunctions.getEdgeBaseFunctionCombination(Dimension.dz), cooficients, 8);
		edgeDzCombination.setDerivativeDirection(Dimension.dz);
		edgeDzCombination.setMappings(mapping);
		
		computeFaceCooficients(true);
		
//		if(this.levelOfDivision == 2 && this.cubePosition == CubePosition.BOT_LEFT_FAR)
//			System.out.println("cof after face 2" + cooficients[4]);

		//a 27

		
		faceDxCombination = new BaseFunctionDerivativeCombination(6); 
		faceDxCombination.setBaseFunctionCombination(FaceBaseFunctions.getFaceBaseFunctionCombination(Dimension.dx), cooficients, 20);
		faceDxCombination.setDerivativeDirection(Dimension.dx);
		faceDxCombination.setMappings(mapping);
		
		faceDyCombination = new BaseFunctionDerivativeCombination(6); 
		faceDyCombination.setBaseFunctionCombination(FaceBaseFunctions.getFaceBaseFunctionCombination(Dimension.dy), cooficients, 20);
		faceDyCombination.setDerivativeDirection(Dimension.dy);
		faceDyCombination.setMappings(mapping);
				
		faceDzCombination = new BaseFunctionDerivativeCombination(6); 
		faceDzCombination.setBaseFunctionCombination(FaceBaseFunctions.getFaceBaseFunctionCombination(Dimension.dz), cooficients, 20);
		faceDzCombination.setDerivativeDirection(Dimension.dz);
		faceDzCombination.setMappings(mapping);
		
		dxCombinationCombination = new BaseFunctionDerivativeCombinationCombination
				(pointDxCombination, edgeDxCombination, faceDxCombination);
		
		dyCombinationCombination = new BaseFunctionDerivativeCombinationCombination
				(pointDyCombination, edgeDyCombination, faceDyCombination);
		
		dzCombinationCombination = new BaseFunctionDerivativeCombinationCombination
				(pointDzCombination, edgeDzCombination, faceDzCombination);

		
		BaseFunctionWrapper baseFunctionWrapper = new BaseFunctionWrapper(); 
		baseFunctionWrapper.setLr(mapping);
		cooficients[26] = computeInteriorCooficient(baseFunctionWrapper, xDif, yDif, zDif, dxCombinationCombination, dyCombinationCombination, dzCombinationCombination,
				xTopLeftFar, xBotRightNear, yBotRightNear, yTopLeftFar, zBotRightNear, zTopLeftFar); 
		
		interpolatedFunction.setNormal();
		
	
	}
	
	class BaseFunctionDerivativeCombinationCombination implements TripArgFunction{
		
		private TripArgFunction pointBaseFunctionDerivativeCombination; 
		private TripArgFunction edgeBaseFunctionDerivativeCombination;
		private TripArgFunction faceBaseFunctionDerivativeCombination;
		
		public BaseFunctionDerivativeCombinationCombination(TripArgFunction pointCombination, TripArgFunction edgeCombination, TripArgFunction faceCombination){
			
			pointBaseFunctionDerivativeCombination = pointCombination; 
			edgeBaseFunctionDerivativeCombination = edgeCombination; 
			faceBaseFunctionDerivativeCombination = faceCombination; 
			
		}

		@Override
		public double computeValue(double x, double y, double z) {
		
		
				
			return 
					pointBaseFunctionDerivativeCombination.computeValue(x, y, z) + 
					edgeBaseFunctionDerivativeCombination.computeValue(x, y, z) +
					faceBaseFunctionDerivativeCombination.computeValue(x, y, z);
		}
		

		
	}
	
	private double computeInteriorCooficient(BaseFunctionWrapper baseFunctionWrapper, double xDif, double yDif, double zDif, 
			TripArgFunction dxCombination, TripArgFunction dyCombination, TripArgFunction dzCombination, 
			double xl, double xr, double yl, double yr, double zl, double zr){
		
		TripArgFunctionProduct firstProduct = new TripArgFunctionProduct();
		TripArgFunctionProduct secondProduct = new TripArgFunctionProduct();
		TripArgFunctionProduct thirdProduct = new TripArgFunctionProduct();
		TripArgFunctionProduct fourthProduct = new TripArgFunctionProduct();
		TripArgFunctionProduct fifthProduct = new TripArgFunctionProduct();
		TripArgFunctionProduct sixthProduct = new TripArgFunctionProduct();
		
		double numerator; 
		double denominator;
		
		baseFunctionWrapper.setWrappedFunction(InteriorBaseFunction.INTERIOR.getBaseFunctionDerivativeSquare(Dimension.dx));
		
		denominator = xDif*xDif*
				quadrature.definiteTripleIntegral(xTopLeftFar, xBotRightNear, yBotRightNear, yTopLeftFar, zBotRightNear, zTopLeftFar, baseFunctionWrapper);
		
		baseFunctionWrapper.setWrappedFunction(InteriorBaseFunction.INTERIOR.getBaseFunctionDerivativeSquare(Dimension.dy));
		
		denominator += yDif*yDif*
				quadrature.definiteTripleIntegral(xTopLeftFar, xBotRightNear, yBotRightNear, yTopLeftFar, zBotRightNear, zTopLeftFar, baseFunctionWrapper);
		
		baseFunctionWrapper.setWrappedFunction(InteriorBaseFunction.INTERIOR.getBaseFunctionDerivativeSquare(Dimension.dz));
		
		denominator += zDif*zDif*
				quadrature.definiteTripleIntegral(xTopLeftFar, xBotRightNear, yBotRightNear, yTopLeftFar, zBotRightNear, zTopLeftFar, baseFunctionWrapper);
		
		
		baseFunctionWrapper.setWrappedFunction(InteriorBaseFunction.INTERIOR.getBaseFunctionDerivative(Dimension.dx));
		
		interpolatedFunction.setDx(); 
		firstProduct.addFunction(baseFunctionWrapper);
		firstProduct.addFunction(interpolatedFunction);
		double firstProductValue = xDif*quadrature.definiteTripleIntegral(xl, xr, yl, yr, zl, zr, firstProduct);
		
		secondProduct.addFunction(baseFunctionWrapper);
		secondProduct.addFunction(dxCombination);
		double secondProductValue = -xDif*quadrature.definiteTripleIntegral(xl, xr, yl, yr, zl, zr, secondProduct);
		
		
		baseFunctionWrapper.setWrappedFunction(InteriorBaseFunction.INTERIOR.getBaseFunctionDerivative(Dimension.dy));
		
		interpolatedFunction.setDy(); 
		thirdProduct.addFunction(baseFunctionWrapper);
		thirdProduct.addFunction(interpolatedFunction);
		double thirdProductValue = yDif*quadrature.definiteTripleIntegral(xl, xr, yl, yr, zl, zr, thirdProduct);
		
		fourthProduct.addFunction(baseFunctionWrapper);
		fourthProduct.addFunction(dyCombination);
		double fourthProductValue = -yDif*quadrature.definiteTripleIntegral(xl, xr, yl, yr, zl, zr, fourthProduct);
		
		baseFunctionWrapper.setWrappedFunction(InteriorBaseFunction.INTERIOR.getBaseFunctionDerivative(Dimension.dz));
		
		interpolatedFunction.setDz(); 
		fifthProduct.addFunction(baseFunctionWrapper);
		fifthProduct.addFunction(interpolatedFunction);
		double fifthProductValue = zDif*quadrature.definiteTripleIntegral(xl, xr, yl, yr, zl, zr, fifthProduct);
		
		sixthProduct.addFunction(baseFunctionWrapper);
		sixthProduct.addFunction(dzCombination);
		double sixthProductValue = -zDif*quadrature.definiteTripleIntegral(xl, xr, yl, yr, zl, zr, sixthProduct);
		

		numerator = firstProductValue + secondProductValue + thirdProductValue + fourthProductValue + fifthProductValue + sixthProductValue;	
		
		return numerator/denominator;  
	}
	
	
	
	private double computeFaceCooficientDxDy(BaseFunctionWrapper baseFunctionWrapper, double xDif, double yDif, 
			TripArgFunction edgeDxCombination, TripArgFunction edgeDyCombination, TripArgFunction pointDxCombination, TripArgFunction pointDyCombination,
			FaceBaseFunctions faceBaseFunction, 
			double z, double xl, double xr , double yl, double yr){
		
		double numerator; 
		double denominator; 
		
		TripArgFunctionProduct firstProduct = new TripArgFunctionProduct();
		TripArgFunctionProduct secondProduct = new TripArgFunctionProduct(); 
		TripArgFunctionProduct thirdProduct = new TripArgFunctionProduct();
		TripArgFunctionProduct fourthProduct = new TripArgFunctionProduct();
		TripArgFunctionProduct fifthProduct = new TripArgFunctionProduct();
		TripArgFunctionProduct sixthProduct = new TripArgFunctionProduct();
		
		
		baseFunctionWrapper.setWrappedFunction(faceBaseFunction.getBaseFunctionDerivativeSquare(Dimension.dx));
		
		denominator = xDif*xDif*
				quadrature.definiteDoubleIntegral(xl, xr, yl, yr, z, baseFunctionWrapper, Dimension.dz);

		baseFunctionWrapper.setWrappedFunction(faceBaseFunction.getBaseFunctionDerivativeSquare(Dimension.dy));
		
		denominator += yDif*yDif*
				quadrature.definiteDoubleIntegral(xl, xr, yl, yr, z, baseFunctionWrapper, Dimension.dz);
		
		
		
		baseFunctionWrapper.setWrappedFunction(faceBaseFunction.getBaseFunctionDerivative(Dimension.dx));
		firstProduct.addFunction(pointDxCombination);
		firstProduct.addFunction(baseFunctionWrapper);
		double firstProductValue = -xDif*quadrature.definiteDoubleIntegral(xl, xr, yl, yr, z, firstProduct, Dimension.dz);
		
		secondProduct.addFunction(edgeDxCombination); 
		secondProduct.addFunction(baseFunctionWrapper);
		double secondProductValue = -xDif*quadrature.definiteDoubleIntegral(xl, xr, yl, yr, z, secondProduct, Dimension.dz);
		
		baseFunctionWrapper.setWrappedFunction(faceBaseFunction.getBaseFunctionDerivative(Dimension.dy));
		thirdProduct.addFunction(pointDyCombination); 
		thirdProduct.addFunction(baseFunctionWrapper); 
		double thirdProductValue = -yDif*quadrature.definiteDoubleIntegral(xl, xr, yl, yr, z, thirdProduct, Dimension.dz);
		
		fourthProduct.addFunction(edgeDyCombination); 
		fourthProduct.addFunction(baseFunctionWrapper);
		double fourthProductValue = -yDif*quadrature.definiteDoubleIntegral(xl, xr, yl, yr, z, fourthProduct, Dimension.dz);
		
		
		baseFunctionWrapper.setWrappedFunction(faceBaseFunction.getBaseFunctionDerivative(Dimension.dx));
		interpolatedFunction.setDx(); 
		fifthProduct.addFunction(interpolatedFunction);
		fifthProduct.addFunction(baseFunctionWrapper);
		double fifthProductValue = xDif*quadrature.definiteDoubleIntegral(xl, xr, yl, yr, z, fifthProduct, Dimension.dz);
		
		baseFunctionWrapper.setWrappedFunction(faceBaseFunction.getBaseFunctionDerivative(Dimension.dy));
		interpolatedFunction.setDy(); 
		sixthProduct.addFunction(interpolatedFunction);
		sixthProduct.addFunction(baseFunctionWrapper);
		double sixthProductValue = yDif*quadrature.definiteDoubleIntegral(xl, xr, yl, yr, z, sixthProduct, Dimension.dz);
		
		numerator = firstProductValue + secondProductValue + thirdProductValue + fourthProductValue + fifthProductValue + sixthProductValue;
		
		return numerator/denominator; 
		

	}
	
	private double computeFaceCooficientDxDz(BaseFunctionWrapper baseFunctionWrapper, double xDif, double zDif, 
			TripArgFunction edgeDxCombination, TripArgFunction edgeDzCombination, TripArgFunction pointDxCombination, TripArgFunction pointDzCombination,
			FaceBaseFunctions faceBaseFunction, 
			double y, double xl, double xr , double zl, double zr){
		
		double numerator; 
		double denominator; 
		
		TripArgFunctionProduct firstProduct = new TripArgFunctionProduct();
		TripArgFunctionProduct secondProduct = new TripArgFunctionProduct(); 
		TripArgFunctionProduct thirdProduct = new TripArgFunctionProduct();
		TripArgFunctionProduct fourthProduct = new TripArgFunctionProduct();
		TripArgFunctionProduct fifthProduct = new TripArgFunctionProduct();
		TripArgFunctionProduct sixthProduct = new TripArgFunctionProduct();
		
		
		baseFunctionWrapper.setWrappedFunction(faceBaseFunction.getBaseFunctionDerivativeSquare(Dimension.dx));
		
		denominator = xDif*xDif*
				quadrature.definiteDoubleIntegral(xl, xr, zl, zr, y, baseFunctionWrapper, Dimension.dy);

		baseFunctionWrapper.setWrappedFunction(faceBaseFunction.getBaseFunctionDerivativeSquare(Dimension.dz));
		
		denominator += zDif*zDif*
				quadrature.definiteDoubleIntegral(xl, xr, zl, zr, y, baseFunctionWrapper, Dimension.dy);
		
		
		
		baseFunctionWrapper.setWrappedFunction(faceBaseFunction.getBaseFunctionDerivative(Dimension.dx));
		firstProduct.addFunction(pointDxCombination);
		firstProduct.addFunction(baseFunctionWrapper);
		double firstProductValue = -xDif*quadrature.definiteDoubleIntegral(xl, xr, zl, zr, y, firstProduct, Dimension.dy);
		
		secondProduct.addFunction(edgeDxCombination); 
		secondProduct.addFunction(baseFunctionWrapper);
		double secondProductValue = -xDif*quadrature.definiteDoubleIntegral(xl, xr, zl, zr, y, secondProduct, Dimension.dy);
		
		baseFunctionWrapper.setWrappedFunction(faceBaseFunction.getBaseFunctionDerivative(Dimension.dz));
		thirdProduct.addFunction(pointDzCombination); 
		thirdProduct.addFunction(baseFunctionWrapper); 
		double thirdProductValue = -zDif*quadrature.definiteDoubleIntegral(xl, xr, zl, zr, y, thirdProduct, Dimension.dy);
		
		fourthProduct.addFunction(edgeDzCombination); 
		fourthProduct.addFunction(baseFunctionWrapper);
		double fourthProductValue = -zDif*quadrature.definiteDoubleIntegral(xl, xr, zl, zr, y, fourthProduct, Dimension.dy);
		
		
		baseFunctionWrapper.setWrappedFunction(faceBaseFunction.getBaseFunctionDerivative(Dimension.dx));
		interpolatedFunction.setDx(); 
		fifthProduct.addFunction(interpolatedFunction);
		fifthProduct.addFunction(baseFunctionWrapper);
		double fifthProductValue = xDif*quadrature.definiteDoubleIntegral(xl, xr, zl, zr, y, fifthProduct, Dimension.dy);
		
		baseFunctionWrapper.setWrappedFunction(faceBaseFunction.getBaseFunctionDerivative(Dimension.dz));
		interpolatedFunction.setDz(); 
		sixthProduct.addFunction(interpolatedFunction);
		sixthProduct.addFunction(baseFunctionWrapper);
		double sixthProductValue = zDif*quadrature.definiteDoubleIntegral(xl, xr, zl, zr, y, sixthProduct, Dimension.dy);
		
		numerator = firstProductValue + secondProductValue + thirdProductValue + fourthProductValue + fifthProductValue + sixthProductValue;
		
		return numerator/denominator; 
		

	}
	
	private double computeFaceCooficientDyDz(BaseFunctionWrapper baseFunctionWrapper, double yDif, double zDif, 
			TripArgFunction edgeDyCombination, TripArgFunction edgeDzCombination, TripArgFunction pointDyCombination, TripArgFunction pointDzCombination,
			FaceBaseFunctions faceBaseFunction, 
			double x, double yl, double yr , double zl, double zr){
		
		double numerator; 
		double denominator; 
		
		TripArgFunctionProduct firstProduct = new TripArgFunctionProduct();
		TripArgFunctionProduct secondProduct = new TripArgFunctionProduct(); 
		TripArgFunctionProduct thirdProduct = new TripArgFunctionProduct();
		TripArgFunctionProduct fourthProduct = new TripArgFunctionProduct();
		TripArgFunctionProduct fifthProduct = new TripArgFunctionProduct();
		TripArgFunctionProduct sixthProduct = new TripArgFunctionProduct();
		
		
		baseFunctionWrapper.setWrappedFunction(faceBaseFunction.getBaseFunctionDerivativeSquare(Dimension.dy));
		
		denominator = yDif*yDif*
				quadrature.definiteDoubleIntegral(yl, yr, zl, zr, x, baseFunctionWrapper, Dimension.dx);

		baseFunctionWrapper.setWrappedFunction(faceBaseFunction.getBaseFunctionDerivativeSquare(Dimension.dz));
		
		denominator += zDif*zDif*
				quadrature.definiteDoubleIntegral(yl, yr, zl, zr, x, baseFunctionWrapper, Dimension.dx);
		
		
		
		baseFunctionWrapper.setWrappedFunction(faceBaseFunction.getBaseFunctionDerivative(Dimension.dy));
		firstProduct.addFunction(pointDyCombination);
		firstProduct.addFunction(baseFunctionWrapper);
		double firstProductValue = -yDif*quadrature.definiteDoubleIntegral(yl, yr, zl, zr, x, firstProduct, Dimension.dx);
		
		secondProduct.addFunction(edgeDyCombination); 
		secondProduct.addFunction(baseFunctionWrapper);
		double secondProductValue = -yDif*quadrature.definiteDoubleIntegral(yl, yr, zl, zr, x, secondProduct, Dimension.dx);
		
		baseFunctionWrapper.setWrappedFunction(faceBaseFunction.getBaseFunctionDerivative(Dimension.dz));
		thirdProduct.addFunction(pointDzCombination); 
		thirdProduct.addFunction(baseFunctionWrapper); 
		double thirdProductValue = -zDif*quadrature.definiteDoubleIntegral(yl, yr, zl, zr, x, thirdProduct, Dimension.dx);
		
		fourthProduct.addFunction(edgeDzCombination); 
		fourthProduct.addFunction(baseFunctionWrapper);
		double fourthProductValue = -zDif*quadrature.definiteDoubleIntegral(yl, yr, zl, zr, x, fourthProduct, Dimension.dx);
		
		
		baseFunctionWrapper.setWrappedFunction(faceBaseFunction.getBaseFunctionDerivative(Dimension.dy));
		interpolatedFunction.setDy(); 
		fifthProduct.addFunction(interpolatedFunction);
		fifthProduct.addFunction(baseFunctionWrapper);
		double fifthProductValue = yDif*quadrature.definiteDoubleIntegral(yl, yr, zl, zr, x, fifthProduct, Dimension.dx);
		
		baseFunctionWrapper.setWrappedFunction(faceBaseFunction.getBaseFunctionDerivative(Dimension.dz));
		interpolatedFunction.setDz(); 
		sixthProduct.addFunction(interpolatedFunction);
		sixthProduct.addFunction(baseFunctionWrapper);
		double sixthProductValue = zDif*quadrature.definiteDoubleIntegral(yl, yr, zl, zr, x, sixthProduct, Dimension.dx);
		
		numerator = firstProductValue + secondProductValue + thirdProductValue + fourthProductValue + fifthProductValue + sixthProductValue;
		
		return numerator/denominator; 
		

	}

	
	private double computeEdgeCooficientDx(BaseFunctionWrapper baseFunctionWrapper, double xDif, TripArgFunction pointDxCombination, EdgeBaseFunctions edgeBaseFunction, 
			double y, double z, double xl, double xr ){
		
		double numerator; 
		double denominator; 
		
		TripArgFunctionProduct firstProduct = new TripArgFunctionProduct();
		TripArgFunctionProduct secondProduct = new TripArgFunctionProduct(); 
		
		
		baseFunctionWrapper.setWrappedFunction(edgeBaseFunction.getBaseFunctionDerivativeSquare(Dimension.dx));
		
		denominator = xDif*xDif*
				quadrature.definiteIntegral(xl, xr, y, z, baseFunctionWrapper, Dimension.dx);
		
		baseFunctionWrapper.setWrappedFunction(edgeBaseFunction.getBaseFunctionDerivative(Dimension.dx));
		firstProduct.addFunction(pointDxCombination);
		firstProduct.addFunction(baseFunctionWrapper);
		double firstProductValue = -xDif*quadrature.definiteIntegral(xl, xr, y, z, firstProduct, Dimension.dx);
		
		interpolatedFunction.setDx(); 
		secondProduct.addFunction(interpolatedFunction);
		secondProduct.addFunction(baseFunctionWrapper);
		double sedondProductValue = xDif*quadrature.definiteIntegral(xl, xr, y, z, secondProduct, Dimension.dx);
		
		numerator = firstProductValue + sedondProductValue;
		return numerator/denominator; 
		

	}
	
	private double computeEdgeCooficientDy(BaseFunctionWrapper baseFunctionWrapper, double yDif, TripArgFunction pointDyCombination, EdgeBaseFunctions edgeBaseFunction,
			double x, double z, double yl, double yr){
		
		double numerator; 
		double denominator; 
		
		TripArgFunctionProduct firstProduct = new TripArgFunctionProduct();
		TripArgFunctionProduct secondProduct = new TripArgFunctionProduct(); 
		
		
		baseFunctionWrapper.setWrappedFunction(edgeBaseFunction.getBaseFunctionDerivativeSquare(Dimension.dy));
		
		denominator = yDif*yDif*
				quadrature.definiteIntegral(yl, yr, x, z, baseFunctionWrapper, Dimension.dy);
		
		baseFunctionWrapper.setWrappedFunction(edgeBaseFunction.getBaseFunctionDerivative(Dimension.dy));
		firstProduct.addFunction(pointDyCombination);
		firstProduct.addFunction(baseFunctionWrapper);
		double firstProductValue = -yDif*quadrature.definiteIntegral(yl, yr, x, z, firstProduct, Dimension.dy);
		
		interpolatedFunction.setDy(); 
		secondProduct.addFunction(interpolatedFunction);
		secondProduct.addFunction(baseFunctionWrapper);
		double sedondProductValue = yDif*quadrature.definiteIntegral(yl, yr, x, z, secondProduct, Dimension.dy);
		
		numerator = firstProductValue + sedondProductValue;
		return numerator/denominator; 
		

	}
	
	private double computeEdgeCooficientDz(BaseFunctionWrapper baseFunctionWrapper, double zDif, TripArgFunction pointDzCombination, EdgeBaseFunctions edgeBaseFunction,
			double x, double y, double zl, double zr){
		
		double numerator; 
		double denominator; 
		
		TripArgFunctionProduct firstProduct = new TripArgFunctionProduct();
		TripArgFunctionProduct secondProduct = new TripArgFunctionProduct(); 
		
		
		baseFunctionWrapper.setWrappedFunction(edgeBaseFunction.getBaseFunctionDerivativeSquare(Dimension.dz));
		
		denominator = zDif*zDif*
				quadrature.definiteIntegral(zl, zr, x, y, baseFunctionWrapper, Dimension.dz);
		
		baseFunctionWrapper.setWrappedFunction(edgeBaseFunction.getBaseFunctionDerivative(Dimension.dz));
		firstProduct.addFunction(pointDzCombination);
		firstProduct.addFunction(baseFunctionWrapper);
		double firstProductValue = -zDif*quadrature.definiteIntegral(zl, zr, x, y, firstProduct, Dimension.dz);
		
		interpolatedFunction.setDz(); 
		secondProduct.addFunction(interpolatedFunction);
		secondProduct.addFunction(baseFunctionWrapper);
		double sedondProductValue = zDif*quadrature.definiteIntegral(zl, zr, x, y, secondProduct, Dimension.dz);
		
		
		numerator = firstProductValue + sedondProductValue;
		
		return numerator/denominator; 
		

	}
	
	@Override
	public double computeValue(double x, double y, double z){
		
		
		double[] a = cooficients;
		
//		double lastXTopLeftFar = 0;
//		double lastYTopLeftFar = 0; 
//		double lastZTopLeftFar = 0;
//		double lastXBotRightNear = 0;
//		double lastYBotRightNear = 0;
//		double lastZBotRightNear = 0;
		

		// if(lastCoordinates != null){
		// lastXTopLeftFar = lastCoordinates[0];
		// lastYTopLeftFar = lastCoordinates[1];
		// lastZTopLeftFar = lastCoordinates[2];
		// lastXBotRightNear = lastCoordinates[3];
		// lastYBotRightNear = lastCoordinates[4];
		// lastZBotRightNear = lastCoordinates[5];
		// }
		
		double xDif = xBotRightNear - xTopLeftFar;
		double yDif = yTopLeftFar - yBotRightNear; 
		double zDif = zTopLeftFar - zBotRightNear;
		

		// double xLastDif = 0;
		// double yLastDif = 0;
		// double zLastDif = 0;
		//
		// if(lastCoordinates != null){
		//
		// xLastDif = lastXBotRightNear - lastXTopLeftFar;
		// yLastDif = lastYTopLeftFar - lastYBotRightNear;
		// zLastDif = lastZTopLeftFar - lastZBotRightNear;
		//
		// }
		
		double xl = currentCoordinates[0]; 
//		double lastXl = lastXTopLeftFar; 
		double yl = currentCoordinates[4]; 
//		double lastYl = lastYBotRightNear; 
		double zl = currentCoordinates[5]; 
//		double lastZl = lastZBotRightNear; 
		
		x = (x - xl)/xDif; 
		y = (y - yl)/yDif; 
		z = (z - zl)/zDif; 
		
		double pointValues = 0; 

					pointValues += a[0]*PointBaseFunctions.POINT_BOT_LEFT_NEAR.getBaseFunction().computeValue(x, y, z);
					pointValues += a[1]*PointBaseFunctions.POINT_BOT_RIGHT_NEAR.getBaseFunction().computeValue(x, y, z);
					pointValues += a[2]*PointBaseFunctions.POINT_BOT_RIGHT_FAR.getBaseFunction().computeValue(x, y, z);
					pointValues += a[3]*PointBaseFunctions.POINT_BOT_LEFT_FAR.getBaseFunction().computeValue(x, y, z);
					pointValues += a[4]*PointBaseFunctions.POINT_TOP_LEFT_NEAR.getBaseFunction().computeValue(x, y, z); 
					pointValues += a[5]*PointBaseFunctions.POINT_TOP_RIGHT_NEAR.getBaseFunction().computeValue(x, y, z);
					pointValues += a[6]*PointBaseFunctions.POINT_TOP_RIGHT_FAR.getBaseFunction().computeValue(x, y, z);
					pointValues += a[7]*PointBaseFunctions.POINT_TOP_LEFT_FAR.getBaseFunction().computeValue(x, y, z);
				
				
		
		double edgeValues = 0;

					edgeValues += a[8]*EdgeBaseFunctions.EDGE_BOT_NEAR.getBaseFunction().computeValue(x, y, z);
					edgeValues += a[9]*EdgeBaseFunctions.EDGE_RIGHT_BOT.getBaseFunction().computeValue(x, y, z);
					edgeValues += a[10]*EdgeBaseFunctions.EDGE_BOT_FAR.getBaseFunction().computeValue(x, y, z);
					edgeValues += a[11]*EdgeBaseFunctions.EDGE_LEFT_BOT.getBaseFunction().computeValue(x, y, z);
					edgeValues += a[12]*EdgeBaseFunctions.EDGE_TOP_NEAR.getBaseFunction().computeValue(x, y, z);
					edgeValues += a[13]*EdgeBaseFunctions.EDGE_RIGHT_TOP.getBaseFunction().computeValue(x, y, z);
					edgeValues += a[14]*EdgeBaseFunctions.EDGE_TOP_FAR.getBaseFunction().computeValue(x, y, z);
					edgeValues += a[15]*EdgeBaseFunctions.EDGE_LEFT_TOP.getBaseFunction().computeValue(x, y, z);
					edgeValues += a[16]*EdgeBaseFunctions.EDGE_LEFT_NEAR.getBaseFunction().computeValue(x, y, z);
					edgeValues += a[17]*EdgeBaseFunctions.EDGE_RIGHT_NEAR.getBaseFunction().computeValue(x, y, z);
					edgeValues += a[18]*EdgeBaseFunctions.EDGE_RIGHT_FAR.getBaseFunction().computeValue(x, y, z);
					edgeValues += a[19]*EdgeBaseFunctions.EDGE_LEFT_FAR.getBaseFunction().computeValue(x, y, z); 

		double faceValues = 0; 		

					faceValues += a[20]*FaceBaseFunctions.FACE_NEAR.getBaseFunction().computeValue(x, y, z);
					faceValues += a[21]*FaceBaseFunctions.FACE_RIGHT.getBaseFunction().computeValue(x, y, z);
					faceValues += a[22]*FaceBaseFunctions.FACE_FAR.getBaseFunction().computeValue(x, y, z);
					faceValues += a[23]*FaceBaseFunctions.FACE_LEFT.getBaseFunction().computeValue(x, y, z);
					faceValues += a[24]*FaceBaseFunctions.FACE_TOP.getBaseFunction().computeValue(x, y, z);
					faceValues += a[25]*FaceBaseFunctions.FACE_BOT.getBaseFunction().computeValue(x, y, z);

		  

		double internalValue = a[26] * InteriorBaseFunction.INTERIOR.getBaseFunction().computeValue(x, y, z);
		
		return pointValues + edgeValues + faceValues + internalValue;

	}
	
	private void computePointCooficients(){
		double lastXTopLeftFar = xTopLeftFar; 
		double lastYTopLeftFar = yTopLeftFar;
		double lastZTopLeftFar = zTopLeftFar;  
		double lastXBotRightNear = xBotRightNear;  
		double lastYBotRightNear = yBotRightNear; 
		double lastZBotRightNear = zBotRightNear; 
		
		if(lastCoordinates != null){
			
			lastXTopLeftFar = lastCoordinates[0]; 
			lastYTopLeftFar = lastCoordinates[1]; 
			lastZTopLeftFar = lastCoordinates[2]; 
			lastXBotRightNear = lastCoordinates[3]; 
			lastYBotRightNear = lastCoordinates[4]; 
			lastZBotRightNear = lastCoordinates[5];
		}
		
		double[] lastBotLeftNearPoint = new double[] {lastXTopLeftFar, lastYBotRightNear, lastZBotRightNear };
		double[] lastBotRightNearPoint = new double[] {lastXBotRightNear, lastYBotRightNear, lastZBotRightNear };
		double[] lastBotRightFarPoint = new double[] {lastXBotRightNear, lastYTopLeftFar, lastZBotRightNear };
		double[] lastBotLeftFarPoint = new double[] {lastXTopLeftFar, lastYTopLeftFar, lastZBotRightNear };
		double[] lastTopLeftNearPoint = new double[] {lastXTopLeftFar, lastYBotRightNear, lastZTopLeftFar };
		double[] lastTopRightNearPoint = new double[] {lastXBotRightNear, lastYBotRightNear, lastZTopLeftFar };
		double[] lastTopRightFarPoint = new double[] {lastXBotRightNear, lastYTopLeftFar, lastZTopLeftFar };
		double[] lastTopLeftFarPoint = new double[] {lastXTopLeftFar, lastYTopLeftFar, lastZTopLeftFar };
		
		double[] point1 = lastBotLeftNearPoint; 
		double[] point2 = lastBotRightNearPoint; 
		double[] point3 = lastBotRightFarPoint; 
		double[] point4 = lastBotLeftFarPoint; 
		double[] point5 = lastTopLeftNearPoint; 
		double[] point6 = lastTopRightNearPoint; 
		double[] point7 = lastTopRightFarPoint; 
		double[] point8 = lastTopLeftFarPoint; 
		
		//a1 -- a8 
		if(leftBotNearPoint)
			cooficients[0] = interpolatedFunction.computeValue(currentCoordinates[0], currentCoordinates[4], currentCoordinates[5]);
		else{
			switch(this.cubePosition){
				case BOT_LEFT_NEAR:
					cooficients[0] = interpolatedFunction.computeValue(lastXTopLeftFar, lastYBotRightNear, lastZBotRightNear);
					break;
				case BOT_RIGHT_NEAR:
					cooficients[0] = (interpolatedFunction.computeValue(lastXTopLeftFar, lastYBotRightNear, lastZBotRightNear) + 
							interpolatedFunction.computeValue(lastXBotRightNear, lastYBotRightNear, lastZBotRightNear))/2.0;
					break; 
				case BOT_RIGHT_FAR:
					cooficients[0] = (interpolatedFunction.computeValue(lastXTopLeftFar, lastYBotRightNear, lastZBotRightNear) + 
							interpolatedFunction.computeValue(lastXBotRightNear, lastYBotRightNear, lastZBotRightNear) + 
							interpolatedFunction.computeValue(lastXBotRightNear, lastYTopLeftFar, lastZBotRightNear) +
							interpolatedFunction.computeValue(lastXTopLeftFar, lastYTopLeftFar, lastZBotRightNear))/4.0;
					break; 
				case BOT_LEFT_FAR:
					cooficients[0] = (interpolatedFunction.computeValue(point1) + 
							interpolatedFunction.computeValue(point4))/2.0;
					break; 
				case TOP_LEFT_NEAR:
					cooficients[0] = (interpolatedFunction.computeValue(point1) +
							interpolatedFunction.computeValue(point5))/2.0;
					break; 
					
				case TOP_RIGHT_NEAR:
					cooficients[0] = ((interpolatedFunction.computeValue(lastXBotRightNear, lastYBotRightNear, lastZBotRightNear)) +
							(interpolatedFunction.computeValue(lastXBotRightNear, lastYBotRightNear, lastZTopLeftFar)) + 
							(interpolatedFunction.computeValue(lastXTopLeftFar, lastYBotRightNear, lastZBotRightNear)) + 
							(interpolatedFunction.computeValue(lastXTopLeftFar, lastYBotRightNear, lastZTopLeftFar)))/4.0;
					break; 
				case TOP_RIGHT_FAR:
					throw new RuntimeException("cof[0]");
				case TOP_LEFT_FAR:
					cooficients[0] = (interpolatedFunction.computeValue(point1) +
							interpolatedFunction.computeValue(point4) +
							interpolatedFunction.computeValue(point5) +
							interpolatedFunction.computeValue(point8))/4.0;								
			}
			
		}
		
		if(rightBotNearPoint)
			cooficients[1] = interpolatedFunction.computeValue(currentCoordinates[3], currentCoordinates[4], currentCoordinates[5]);
		else{
			switch(this.cubePosition){
				case BOT_LEFT_NEAR:
					cooficients[1] = (interpolatedFunction.computeValue(lastBotLeftNearPoint[0], lastBotLeftNearPoint[1], lastBotLeftNearPoint[2]) +
							interpolatedFunction.computeValue(lastBotRightNearPoint[0], lastBotRightNearPoint[1], lastBotRightNearPoint[2]))/2.0;
					break; 
				case BOT_RIGHT_NEAR:
					cooficients[1] = interpolatedFunction.computeValue(lastBotRightNearPoint);
					break; 
				case BOT_RIGHT_FAR:
					cooficients[1] = (interpolatedFunction.computeValue(lastBotRightNearPoint) + interpolatedFunction.computeValue(lastBotRightFarPoint))/2.0;
					break; 
				case BOT_LEFT_FAR:
					cooficients[1] = (interpolatedFunction.computeValue(point1) + interpolatedFunction.computeValue(point2) + 
							interpolatedFunction.computeValue(point3) + interpolatedFunction.computeValue(point4))/4.0; 
					break; 
				case TOP_LEFT_NEAR:
					cooficients[1] = (interpolatedFunction.computeValue(point1) + interpolatedFunction.computeValue(point2) + 
							interpolatedFunction.computeValue(point5) + interpolatedFunction.computeValue(point6))/4.0; 
					break; 
				case TOP_RIGHT_NEAR:
					cooficients[1] = (interpolatedFunction.computeValue(lastTopRightNearPoint) + interpolatedFunction.computeValue(lastBotRightNearPoint))/2.0; 
					break; 
				case TOP_RIGHT_FAR:
					cooficients[1] = (interpolatedFunction.computeValue(lastBotRightNearPoint) + interpolatedFunction.computeValue(lastBotRightFarPoint) + 
							interpolatedFunction.computeValue(lastTopRightFarPoint) + interpolatedFunction.computeValue(lastTopRightNearPoint))/4.0; 
					break; 
				case TOP_LEFT_FAR:
					throw new RuntimeException("cof[1]");
					
			}
		}
		
		if(rightBotFarPoint)
			cooficients[2] = interpolatedFunction.computeValue(currentCoordinates[3], currentCoordinates[1], currentCoordinates[5]);
		else{
			switch(this.cubePosition){
			case BOT_LEFT_NEAR:
				cooficients[2] = (interpolatedFunction.computeValue(point1) + interpolatedFunction.computeValue(point2) + interpolatedFunction.computeValue(point3) + interpolatedFunction.computeValue(point4))/4.0; 
				break; 
			case BOT_RIGHT_NEAR:
				cooficients[2] = (interpolatedFunction.computeValue(point2) + interpolatedFunction.computeValue(point3))/2.0; 
				break; 
			case BOT_RIGHT_FAR:
				cooficients[2] = (interpolatedFunction.computeValue(point3)); 
				break; 
			case BOT_LEFT_FAR:
				cooficients[2] = (interpolatedFunction.computeValue(point3) + interpolatedFunction.computeValue(point4))/2.0; 
				break; 
			case TOP_LEFT_NEAR:
				throw new RuntimeException("cof[2]");
				
			case TOP_RIGHT_NEAR:
				cooficients[2] = (interpolatedFunction.computeValue(point2) + interpolatedFunction.computeValue(point3) + interpolatedFunction.computeValue(point6) + interpolatedFunction.computeValue(point7))/4.0; 
				break; 
			case TOP_RIGHT_FAR:
				 cooficients[2] = (interpolatedFunction.computeValue(point3) + interpolatedFunction.computeValue(point7))/2.0; 
				break; 
			case TOP_LEFT_FAR:
				cooficients[2] = (interpolatedFunction.computeValue(point3) + interpolatedFunction.computeValue(point4) + interpolatedFunction.computeValue(point7) + interpolatedFunction.computeValue(point8))/4.0;
				break; 
				
			}
		}
		
		if(leftBotFarPoint)
			cooficients[3] = interpolatedFunction.computeValue(currentCoordinates[0], currentCoordinates[1], currentCoordinates[5]);
		else{
			switch(this.cubePosition){
			case BOT_LEFT_NEAR:
				cooficients[3] = (interpolatedFunction.computeValue(point1) + interpolatedFunction.computeValue(point4))/2.0;
				break; 
			case BOT_RIGHT_NEAR:
				cooficients[3] = (interpolatedFunction.computeValue(point1) + interpolatedFunction.computeValue(point4) + interpolatedFunction.computeValue(point3) + interpolatedFunction.computeValue(point2))/4.0;
				break; 
			case BOT_RIGHT_FAR:
				cooficients[3] = (interpolatedFunction.computeValue(point4) + interpolatedFunction.computeValue(point3))/2.0;
				break; 
			case BOT_LEFT_FAR:
				cooficients[3] = interpolatedFunction.computeValue(point4);
				break; 
			case TOP_LEFT_NEAR:
				cooficients[3] = (interpolatedFunction.computeValue(point1) + interpolatedFunction.computeValue(point4) + interpolatedFunction.computeValue(point5) + interpolatedFunction.computeValue(point8))/4.0; 
				break; 
			case TOP_RIGHT_NEAR:
				throw new RuntimeException("cof[3]");
				
			case TOP_RIGHT_FAR:
				cooficients[3] = (interpolatedFunction.computeValue(point3) + interpolatedFunction.computeValue(point4) + interpolatedFunction.computeValue(point7) + interpolatedFunction.computeValue(point8))/4.0; 
				break; 
			case TOP_LEFT_FAR:
				cooficients[3] = (interpolatedFunction.computeValue(point4) + interpolatedFunction.computeValue(point8))/2.0;
				break; 
				
			}
		}
		
		
		if(leftTopNearPoint)
			cooficients[4] = interpolatedFunction.computeValue(currentCoordinates[0], currentCoordinates[4], currentCoordinates[2]);
		else{
			switch(this.cubePosition){
			case BOT_LEFT_NEAR:
				cooficients[4] = (interpolatedFunction.computeValue(point1) + interpolatedFunction.computeValue(point5))/2.0; 
				break; 
			case BOT_RIGHT_NEAR:
				cooficients[4] = (interpolatedFunction.computeValue(point1) + interpolatedFunction.computeValue(point5) + interpolatedFunction.computeValue(point2) + interpolatedFunction.computeValue(point6))/4.0;
				break; 
			case BOT_RIGHT_FAR:
				throw new RuntimeException("cof[4]");
			case BOT_LEFT_FAR:
				cooficients[4] = (interpolatedFunction.computeValue(point1) + interpolatedFunction.computeValue(point4) + interpolatedFunction.computeValue(point5) + interpolatedFunction.computeValue(point8))/4.0; 
				break; 
			case TOP_LEFT_NEAR:
				cooficients[4]  = interpolatedFunction.computeValue(point5); 
				break; 
			case TOP_RIGHT_NEAR:
				cooficients[4] = (interpolatedFunction.computeValue(point5) + interpolatedFunction.computeValue(point6))/2.0; 
				break; 
			case TOP_RIGHT_FAR:
				cooficients[4] = (interpolatedFunction.computeValue(point5) + interpolatedFunction.computeValue(point6) + interpolatedFunction.computeValue(point7) + interpolatedFunction.computeValue(point8))/4.0; 
				break; 
			case TOP_LEFT_FAR:
				cooficients[4] = (interpolatedFunction.computeValue(point5) + interpolatedFunction.computeValue(point8))/2.0;
				break; 
				
			}
		}
		
		if(rightTopNearPoint)
			cooficients[5] = interpolatedFunction.computeValue(currentCoordinates[3], currentCoordinates[4], currentCoordinates[2]);
		else{
			switch(this.cubePosition){
			case BOT_LEFT_NEAR:
				cooficients[5] = (interpolatedFunction.computeValue(point1) + interpolatedFunction.computeValue(point2) + interpolatedFunction.computeValue(point5) + interpolatedFunction.computeValue(point6))/4.0; 
				break; 
			case BOT_RIGHT_NEAR:
				cooficients[5] = (interpolatedFunction.computeValue(point2) + interpolatedFunction.computeValue(point6))/2.0; 
				break; 
			case BOT_RIGHT_FAR:
				cooficients[5] = (interpolatedFunction.computeValue(point2) + interpolatedFunction.computeValue(point3) + interpolatedFunction.computeValue(point7) + interpolatedFunction.computeValue(point6))/4.0;
				break; 
			case BOT_LEFT_FAR:
				throw new RuntimeException("cof[5]"); 
				
			case TOP_LEFT_NEAR:
				cooficients[5] = (interpolatedFunction.computeValue(point5) + interpolatedFunction.computeValue(point6))/2.0; 
				break; 
			case TOP_RIGHT_NEAR:
				cooficients[5] = interpolatedFunction.computeValue(point6);  
				break; 
			case TOP_RIGHT_FAR:
				cooficients[5] = (interpolatedFunction.computeValue(point6) + interpolatedFunction.computeValue(point7))/2.0; 
				break; 
			case TOP_LEFT_FAR:
				cooficients[5] = (interpolatedFunction.computeValue(point8) + interpolatedFunction.computeValue(point5) + interpolatedFunction.computeValue(point7) + interpolatedFunction.computeValue(point6))/4.0;
				break; 
				
			}
		}	
		
		if(rightTopFarPoint)
			cooficients[6] = interpolatedFunction.computeValue(currentCoordinates[3], currentCoordinates[1], currentCoordinates[2]);
		else{
			switch(this.cubePosition){
			case BOT_LEFT_NEAR:
				throw new RuntimeException("cof[6]");
				
			case BOT_RIGHT_NEAR:
				cooficients[6] = (interpolatedFunction.computeValue(point2) + interpolatedFunction.computeValue(point3) + interpolatedFunction.computeValue(point6) + interpolatedFunction.computeValue(point7))/4.0;
				break; 
			case BOT_RIGHT_FAR:
				cooficients[6] = (interpolatedFunction.computeValue(point3) + interpolatedFunction.computeValue(point7))/2.0;
				break; 
			case BOT_LEFT_FAR:
				cooficients[6] = (interpolatedFunction.computeValue(point3) + interpolatedFunction.computeValue(point7) + interpolatedFunction.computeValue(point4) + interpolatedFunction.computeValue(point8))/4.0;
				break; 
			case TOP_LEFT_NEAR:
				cooficients[6] = (interpolatedFunction.computeValue(point6) + interpolatedFunction.computeValue(point7) + interpolatedFunction.computeValue(point5) + interpolatedFunction.computeValue(point8))/4.0;
				break; 
			case TOP_RIGHT_NEAR:
				cooficients[6] = (interpolatedFunction.computeValue(point6) + interpolatedFunction.computeValue(point7))/2.0; 
				break; 
			case TOP_RIGHT_FAR:
				cooficients[6] = interpolatedFunction.computeValue(point7); 
				break; 
			case TOP_LEFT_FAR:
				cooficients[6] = (interpolatedFunction.computeValue(point7) + interpolatedFunction.computeValue(point8))/2.0;
				break; 
				
			}
		}
			
		
		if(leftTopFarPoint)
			cooficients[7] = interpolatedFunction.computeValue(currentCoordinates[0], currentCoordinates[1], currentCoordinates[2]);
		else{
			switch(this.cubePosition){
			case BOT_LEFT_NEAR:
				cooficients[7] = (interpolatedFunction.computeValue(point1) + interpolatedFunction.computeValue(point4) + interpolatedFunction.computeValue(point5) + interpolatedFunction.computeValue(point8))/4.0;
				break; 
			case BOT_RIGHT_NEAR:
				throw new RuntimeException("cof[7]");
				
			case BOT_RIGHT_FAR:
				cooficients[7] = (interpolatedFunction.computeValue(point3) + interpolatedFunction.computeValue(point4) + interpolatedFunction.computeValue(point7) + interpolatedFunction.computeValue(point8))/4.0;
				break; 
			case BOT_LEFT_FAR:
				cooficients[7] = (interpolatedFunction.computeValue(point4) + interpolatedFunction.computeValue(point8))/2.0;
				break; 
			case TOP_LEFT_NEAR:
				cooficients[7] = (interpolatedFunction.computeValue(point5) + interpolatedFunction.computeValue(point8))/2.0;
				break; 
			case TOP_RIGHT_NEAR:
				cooficients[7] = (interpolatedFunction.computeValue(point5) + interpolatedFunction.computeValue(point6) + interpolatedFunction.computeValue(point7) + interpolatedFunction.computeValue(point8))/4.0;
				break; 
			case TOP_RIGHT_FAR:
				cooficients[7] = (interpolatedFunction.computeValue(point7) + interpolatedFunction.computeValue(point8))/2.0; 
				break; 
			case TOP_LEFT_FAR:
				cooficients[7] =  interpolatedFunction.computeValue(point8);
				break; 
				
			}
		}
	}
	
	private void computeEdgeCooficients(boolean secondComputation){
		BaseFunctionWrapper baseFunctionWrapper = new BaseFunctionWrapper(); 
		
		if(botNearEdge){
			
			if(secondComputation){
				baseFunctionWrapper.setLr(mapping);
				cooficients[8] += computeEdgeCooficientDx(baseFunctionWrapper,  xDif, pointDxCombination, EdgeBaseFunctions.EDGE_BOT_NEAR, yBotRightNear, zBotRightNear, xTopLeftFar, xBotRightNear);
			}
		}
		else if(!secondComputation){{

			switch(this.cubePosition){
			case BOT_LEFT_NEAR:
				if(nearFace && botFace){
					setPointEdgeCooficientsOnCrossElement(botNearAdjacentCube.getCooficients()[14], 8, 1);	 
				}
				else if(botFace){
					setPointEdgeCooficients(nearAdjacentCube.getCooficients()[10], 8, 1, 12, 5);
				}
				else if(nearFace){
					setPointEdgeCooficients(botAdjacentCube.getCooficients()[12], 8, 1, 10, 2);
				}
				else{
					setPointEdgeCooficientsOnTwoFaces(nearAdjacentCube.getCooficients()[10], 8, 1, 12, 5,botAdjacentCube.getCooficients()[12], 10, 2);
				}
				break; 
			case BOT_RIGHT_NEAR:
				if(nearFace && botFace){
					setPointEdgeCooficientsOnCrossElement(botNearAdjacentCube.getCooficients()[14], 8, 0);	 
				}
				else if(botFace){
					setPointEdgeCooficients(nearAdjacentCube.getCooficients()[10], 8, 0, 12, 4);
				}
				else if(nearFace){
					setPointEdgeCooficients(botAdjacentCube.getCooficients()[12], 8, 0, 10, 3);
				}
				else{
					setPointEdgeCooficientsOnTwoFaces(nearAdjacentCube.getCooficients()[10], 8, 0, 12, 4, botAdjacentCube.getCooficients()[12], 10, 3);
				}
				break;	
			case BOT_RIGHT_FAR:
				 if(!botFace){
					 setPointEdgeCooficients(botAdjacentCube.getCooficients()[12], 8, 0);
				 }
				 else{
					 throw new RuntimeException("cof[8]");
				 } 
				break; 
			case BOT_LEFT_FAR:
				if(!botFace){
					 setPointEdgeCooficients(botAdjacentCube.getCooficients()[12], 8, 1);
				 }
				 else{
					 throw new RuntimeException("cof[8]");
				 }
				break; 
			case TOP_LEFT_NEAR:
				if(!nearFace){
					setPointEdgeCooficients(nearAdjacentCube.getCooficients()[10], 8, 1);
				}
				else{
					throw new RuntimeException("cof[8]");
				}
				break; 
			case TOP_RIGHT_NEAR:
				if(!nearFace){
					setPointEdgeCooficients(nearAdjacentCube.getCooficients()[10], 8, 0);
				}
				else{
					throw new RuntimeException("cof[8]");
				}
				break;
			case TOP_RIGHT_FAR:
				throw new RuntimeException("cof[8]");
			case TOP_LEFT_FAR:
				throw new RuntimeException("cof[8]"); 	
			}
		}}
		
		if(botFarEdge){
			if(secondComputation){
				baseFunctionWrapper.setLr(mapping);
				cooficients[10] += computeEdgeCooficientDx(baseFunctionWrapper,  xDif, pointDxCombination, EdgeBaseFunctions.EDGE_BOT_FAR, yTopLeftFar, zBotRightNear, xTopLeftFar, xBotRightNear);
			}
		}
		else if(!secondComputation){{

			switch(this.cubePosition){
			case BOT_LEFT_NEAR:
				if(!botFace){
					setPointEdgeCooficients(botAdjacentCube.getCooficients()[14], 10, 2);
				}
				else{
					throw new RuntimeException("cof[10]");
				}
				break; 
			case BOT_RIGHT_NEAR:
				if(!botFace){
					
					setPointEdgeCooficients(botAdjacentCube.getCooficients()[14], 10, 3);
				}
				else{
					throw new RuntimeException("cof[10]");
				}
				break;	
			case BOT_RIGHT_FAR:
				 if(botFace && farFace){
					 setPointEdgeCooficientsOnCrossElement(botFarAdjacentCube.getCooficients()[12], 10, 3);
				 }
				 else if(botFace){
					 setPointEdgeCooficients(farAdjacentCube.getCooficients()[8], 10, 3, 14, 7);
				 }
				 else if(farFace){
					 setPointEdgeCooficients(botAdjacentCube.getCooficients()[14], 10, 3, 8, 0);
				 }
				 else{
					 setPointEdgeCooficientsOnTwoFaces(farAdjacentCube.getCooficients()[8], 10, 3, 14, 7, botAdjacentCube.getCooficients()[14], 8, 0);
				 }
				break; 
			case BOT_LEFT_FAR:
				if(botFace && farFace){
					setPointEdgeCooficientsOnCrossElement(botFarAdjacentCube.getCooficients()[12], 10, 2);
				 }
				 else if(botFace){
					 setPointEdgeCooficients(farAdjacentCube.getCooficients()[8], 10, 2, 14, 6);
				 }
				 else if(farFace){
					 setPointEdgeCooficients(botAdjacentCube.getCooficients()[14], 10, 2, 8, 1);
				 }
				 else{
					 setPointEdgeCooficientsOnTwoFaces(farAdjacentCube.getCooficients()[8], 10, 2, 14, 6, botAdjacentCube.getCooficients()[14], 8, 1);
				 }
				break; 
			case TOP_LEFT_NEAR:
				throw new RuntimeException("cof[10]"); 
			case TOP_RIGHT_NEAR:
				throw new RuntimeException("cof[10]");
			case TOP_RIGHT_FAR:
				if(!farFace){
					setPointEdgeCooficients(farAdjacentCube.getCooficients()[8], 10, 3);
				}
				else{
					throw new RuntimeException("cof[10]");
				}
				break; 
			case TOP_LEFT_FAR:
				if(!farFace){
					setPointEdgeCooficients(farAdjacentCube.getCooficients()[8], 10, 2);
				}
				else{
					throw new RuntimeException("cof[10]");
				} 	
				break; 
			}
			
		}}
		
		if(topNearEdge){
			if(secondComputation){
				baseFunctionWrapper.setLr(mapping);
				cooficients[12] += computeEdgeCooficientDx(baseFunctionWrapper,  xDif, pointDxCombination, EdgeBaseFunctions.EDGE_TOP_NEAR, yBotRightNear, zTopLeftFar, xTopLeftFar, xBotRightNear);
			}
			
		}
		
		else if(!secondComputation){{
			
			switch(this.cubePosition){
			case BOT_LEFT_NEAR:
				if(!nearFace){
					setPointEdgeCooficients(nearAdjacentCube.getCooficients()[14], 12, 5);
				}
				else{
					throw new RuntimeException("cof[12]");
				}
				break; 
			case BOT_RIGHT_NEAR:
				if(!nearFace){
					
					setPointEdgeCooficients(nearAdjacentCube.getCooficients()[14], 12, 4);
				}
				else{
					throw new RuntimeException("cof[12]");
				}
				break;	
			case BOT_RIGHT_FAR:
				 throw new RuntimeException("cof[12]");				  
			case BOT_LEFT_FAR:
				 throw new RuntimeException("cof[12]");
			case TOP_LEFT_NEAR:
				if(nearFace && topFace){
					setPointEdgeCooficientsOnCrossElement(topNearAdjacentCube.getCooficients()[10], 12, 5);
				}
				else if(topFace){
					setPointEdgeCooficients(nearAdjacentCube.getCooficients()[14], 12, 5, 8, 1);
				}
				else if(nearFace){
					setPointEdgeCooficients(topAdjacentCube.getCooficients()[8], 12, 5, 14, 6);
				}
				else{
					setPointEdgeCooficientsOnTwoFaces(nearAdjacentCube.getCooficients()[14], 12, 5, 8, 1, topAdjacentCube.getCooficients()[8], 14, 6);
				}
				break;  
			case TOP_RIGHT_NEAR:
				if(nearFace && topFace){
					setPointEdgeCooficientsOnCrossElement(topNearAdjacentCube.getCooficients()[10], 12, 4);
				}
				else if(topFace){
					setPointEdgeCooficients(nearAdjacentCube.getCooficients()[14], 12, 4, 8, 0);
				}
				else if(nearFace){
					setPointEdgeCooficients(topAdjacentCube.getCooficients()[8], 12, 4, 14, 7);
				}
				else{
					setPointEdgeCooficientsOnTwoFaces(nearAdjacentCube.getCooficients()[14], 12, 4, 8, 0, topAdjacentCube.getCooficients()[8], 14, 7);
				}
				break; 
			case TOP_RIGHT_FAR:
				if(!topFace){
					 setPointEdgeCooficients(topAdjacentCube.getCooficients()[8], 12, 4);
				 }
				 else{
					 throw new RuntimeException("cof[12]");
				 }
				break;
				
			case TOP_LEFT_FAR:
				if(!topFace){
					 setPointEdgeCooficients(topAdjacentCube.getCooficients()[8], 12, 5);
				 }
				 else{
					 throw new RuntimeException("cof[12]");
				 }
				break; 	
			}
			
		}}
		
		if(topFarEdge){
		
			if(secondComputation){
				baseFunctionWrapper.setLr(mapping);
				cooficients[14] += computeEdgeCooficientDx(baseFunctionWrapper,  xDif, pointDxCombination, EdgeBaseFunctions.EDGE_TOP_FAR, yTopLeftFar, zTopLeftFar, xTopLeftFar, xBotRightNear);
			}
		}
		
		else if(!secondComputation){{
		
			switch(this.cubePosition){
			case BOT_LEFT_NEAR:
				throw new RuntimeException("cof[14]");
			case BOT_RIGHT_NEAR:
				throw new RuntimeException("cof[14]");
			case BOT_RIGHT_FAR:
				 if(!farFace){
					 setPointEdgeCooficients(farAdjacentCube.getCooficients()[12], 14, 7);
				 }
				 else{
					 throw new RuntimeException("cof[14]");
				 }
				break; 
			case BOT_LEFT_FAR:
				 if(!farFace){
					 setPointEdgeCooficients(farAdjacentCube.getCooficients()[12], 14, 6);
				 }
				 else{
					 throw new RuntimeException("cof[14]");
				 }
				break;
			case TOP_LEFT_NEAR:
				if(!topFace){
					setPointEdgeCooficients(topAdjacentCube.getCooficients()[10], 14, 6);
				}
				else{
					throw new RuntimeException("cof[14]");
				}
				break;   
			case TOP_RIGHT_NEAR:
				if(!topFace){
					setPointEdgeCooficients(topAdjacentCube.getCooficients()[10], 14, 7);
				}
				else{
					throw new RuntimeException("cof[14]");
				}
				break; 
			case TOP_RIGHT_FAR:
				if(topFace && farFace){
					setPointEdgeCooficientsOnCrossElement(topFarAdjacentCube.getCooficients()[8], 14, 7);
				}
				else if(topFace){
					setPointEdgeCooficients(farAdjacentCube.getCooficients()[12], 14, 7, 10, 3);
				}
				else if(farFace){
					setPointEdgeCooficients(topAdjacentCube.getCooficients()[10], 14, 7, 12, 4);
				}
				else{
					setPointEdgeCooficientsOnTwoFaces(farAdjacentCube.getCooficients()[12], 14, 7, 10, 3, topAdjacentCube.getCooficients()[10], 12, 4);
				}
				break;
			case TOP_LEFT_FAR:
				if(topFace && farFace){
					setPointEdgeCooficientsOnCrossElement(topFarAdjacentCube.getCooficients()[8], 14, 6);
				}
				else if(topFace){
					setPointEdgeCooficients(farAdjacentCube.getCooficients()[12], 14, 6, 10, 2);
				}
				else if(farFace){
					setPointEdgeCooficients(topAdjacentCube.getCooficients()[10], 14, 6, 12, 5);
				}
				else{
					setPointEdgeCooficientsOnTwoFaces(farAdjacentCube.getCooficients()[12], 14, 6, 10, 2, topAdjacentCube.getCooficients()[10], 12, 5);
				}
				break; 	
			}
			
		}}
		
		if(leftBotEdge){
			if(secondComputation){
				baseFunctionWrapper.setLr(mapping);
				cooficients[11] += computeEdgeCooficientDy(baseFunctionWrapper,  yDif, pointDyCombination, EdgeBaseFunctions.EDGE_LEFT_BOT, xTopLeftFar, zBotRightNear, yBotRightNear, yTopLeftFar);
			}
			
		}
		else if(!secondComputation){{
			
			switch(this.cubePosition){
			case BOT_LEFT_NEAR:
				//tutaj
				if(leftFace && botFace){
					setPointEdgeCooficientsOnCrossElement(botLeftAdjacentCube.getCooficients()[13], 11, 3);
				}
				else if(leftFace){
					setPointEdgeCooficients(botAdjacentCube.getCooficients()[15], 11, 3, 9, 2);
				}
				else if(botFace){
					setPointEdgeCooficients(leftAdjacentCube.getCooficients()[9], 11, 3, 15, 7);
				}
				else{
					setPointEdgeCooficientsOnTwoFaces(botAdjacentCube.getCooficients()[15], 11, 3, 9, 2, leftAdjacentCube.getCooficients()[9], 15, 7); 
				}
				break; 
				//tutaj
			case BOT_LEFT_FAR:
				if(leftFace && botFace){
					setPointEdgeCooficientsOnCrossElement(botLeftAdjacentCube.getCooficients()[13], 11, 0);
				}
				else if(leftFace){
					setPointEdgeCooficients(botAdjacentCube.getCooficients()[15], 11, 0, 9, 1);
				}
				else if(botFace){
					setPointEdgeCooficients(leftAdjacentCube.getCooficients()[9], 11, 0, 15, 4);
				}
				else{
					setPointEdgeCooficientsOnTwoFaces(botAdjacentCube.getCooficients()[15], 11, 0, 9, 1, leftAdjacentCube.getCooficients()[9], 15, 4); 
				}
				break;
			case BOT_RIGHT_NEAR:
				if(!botFace){
					setPointEdgeCooficients(botAdjacentCube.getCooficients()[15], 11, 3);
				}
				else{
					throw new RuntimeException("cof[11]");
				}
				break; 
			case BOT_RIGHT_FAR:
				if(!botFace){
					setPointEdgeCooficients(botAdjacentCube.getCooficients()[15], 11, 0);
				}
				else{
					throw new RuntimeException("cof[11]");
				}
				break;
			case TOP_LEFT_NEAR:
				if(!leftFace){
					setPointEdgeCooficients(leftAdjacentCube.getCooficients()[9], 11, 3);
				}
				else{
					throw new RuntimeException("cof[11]");
				}
				break;   
			case TOP_LEFT_FAR:
				if(!leftFace){
					setPointEdgeCooficients(leftAdjacentCube.getCooficients()[9], 11, 0);
				}
				else{
					throw new RuntimeException("cof[11]");
				}
				break; 
			case TOP_RIGHT_NEAR:
				throw new RuntimeException("cof[11]");
				
			case TOP_RIGHT_FAR:
				throw new RuntimeException("cof[11]");
			}
			
		}}
		
		if(rightBotEdge){
			if(secondComputation){
				baseFunctionWrapper.setLr(mapping);
				cooficients[9] += computeEdgeCooficientDy(baseFunctionWrapper,  yDif, pointDyCombination, EdgeBaseFunctions.EDGE_RIGHT_BOT, xBotRightNear, zBotRightNear, yBotRightNear, yTopLeftFar);
			}
			
		}
		else if(!secondComputation){{
			
			switch(this.cubePosition){
			case BOT_LEFT_NEAR:
				if(!botFace){
					setPointEdgeCooficients(botAdjacentCube.getCooficients()[13], 9, 2);
				}
				else{
					throw new RuntimeException("cof[9]");
				}
				break; 
			case BOT_LEFT_FAR:
				if(!botFace){
					setPointEdgeCooficients(botAdjacentCube.getCooficients()[13], 9, 1);
				}
				else{
					throw new RuntimeException("cof[9]");
				}
				break;
			case BOT_RIGHT_NEAR:
				if(botFace && rightFace){
					setPointEdgeCooficientsOnCrossElement(botRightAdjacentCube.getCooficients()[15], 9, 2);
				}
				else if(rightFace){
					setPointEdgeCooficients(botAdjacentCube.getCooficients()[13], 9, 2, 11, 3);
				}
				else if(botFace){
					setPointEdgeCooficients(rightAdjacentCube.getCooficients()[11], 9, 2, 13, 6);
				}
				else{
					setPointEdgeCooficientsOnTwoFaces(botAdjacentCube.getCooficients()[13], 9, 2, 11, 3, rightAdjacentCube.getCooficients()[11], 13, 6);
				}
				break; 
			case BOT_RIGHT_FAR:
				if(botFace && rightFace){
					setPointEdgeCooficientsOnCrossElement(botRightAdjacentCube.getCooficients()[15], 9, 1);
				}
				else if(rightFace){
					setPointEdgeCooficients(botAdjacentCube.getCooficients()[13], 9, 1, 11, 0);
				}
				else if(botFace){
					setPointEdgeCooficients(rightAdjacentCube.getCooficients()[11], 9, 1, 13, 5);
				}
				else{
					setPointEdgeCooficientsOnTwoFaces(botAdjacentCube.getCooficients()[13], 9, 1, 11, 0, rightAdjacentCube.getCooficients()[11], 13, 5);
				}
				break;
			case TOP_LEFT_NEAR:
				throw new RuntimeException("cof[9]");
			case TOP_LEFT_FAR:
				throw new RuntimeException("cof[9]"); 
			case TOP_RIGHT_NEAR:
				if(!rightFace){
					setPointEdgeCooficients(rightAdjacentCube.getCooficients()[11], 9, 2);
				}
				else{
					throw new RuntimeException("cof[9]");
				}
				break; 
			case TOP_RIGHT_FAR:
				if(!rightFace){
					setPointEdgeCooficients(rightAdjacentCube.getCooficients()[11], 9, 1);
				}
				else{
					throw new RuntimeException("cof[9]");
				}
				break;
			}
			
		}}
		
		if(rightTopEdge){
			if(secondComputation){
				baseFunctionWrapper.setLr(mapping);
				cooficients[13] += computeEdgeCooficientDy(baseFunctionWrapper,  yDif, pointDyCombination, EdgeBaseFunctions.EDGE_RIGHT_TOP, xBotRightNear, zTopLeftFar, yBotRightNear, yTopLeftFar);
			}
			
		}
		else if(!secondComputation){{
			
			switch(this.cubePosition){
			case BOT_LEFT_NEAR:
				throw new RuntimeException("cof[13]");
			case BOT_LEFT_FAR:
				throw new RuntimeException("cof[13]");
			case BOT_RIGHT_NEAR:
				if(!rightFace){
					setPointEdgeCooficients(rightAdjacentCube.getCooficients()[15], 13, 6);
				}
				else{
					throw new RuntimeException("cof[13]");
				}
				break; 
			case BOT_RIGHT_FAR:
				if(!rightFace){
					setPointEdgeCooficients(rightAdjacentCube.getCooficients()[15], 13, 5);
				}
				else{
					throw new RuntimeException("cof[13]");
				}
				break;
			case TOP_LEFT_NEAR:
				if(!topFace){
					setPointEdgeCooficients(topAdjacentCube.getCooficients()[9], 13, 6);
				}
				else{
					throw new RuntimeException("cof[13]");
				}
				break;
			case TOP_LEFT_FAR:
				if(!topFace) {
					setPointEdgeCooficients(topAdjacentCube.getCooficients()[9], 13, 5);
				}
				else{
					throw new RuntimeException("cof[13]");
				}
				break; 
			case TOP_RIGHT_NEAR:
				if(rightFace && topFace){
					setPointEdgeCooficientsOnCrossElement(topRightAdjacentCube.getCooficients()[11], 13, 6);
				}
				else if(rightFace){
					setPointEdgeCooficients(topAdjacentCube.getCooficients()[9], 13, 6, 15, 7);
				}
				else if(topFace){
					setPointEdgeCooficients(rightAdjacentCube.getCooficients()[15], 13, 6, 9, 2);
				}
				else{
					setPointEdgeCooficientsOnTwoFaces(topAdjacentCube.getCooficients()[9], 13, 6, 15, 7, rightAdjacentCube.getCooficients()[15], 9, 2);
				}
				break; 
			case TOP_RIGHT_FAR:
				if(rightFace && topFace){
					setPointEdgeCooficientsOnCrossElement(topRightAdjacentCube.getCooficients()[11], 13, 5);
				}
				else if(rightFace){
					setPointEdgeCooficients(topAdjacentCube.getCooficients()[9], 13, 5, 15, 4);
				}
				else if(topFace){
					setPointEdgeCooficients(rightAdjacentCube.getCooficients()[15], 13, 5, 9, 1);
				}
				else{
					setPointEdgeCooficientsOnTwoFaces(topAdjacentCube.getCooficients()[9], 13, 5, 15, 4, rightAdjacentCube.getCooficients()[15], 9, 1);
				}
				break;
			}
			
		}}
		
		if(leftTopEdge){
			if(secondComputation){
				baseFunctionWrapper.setLr(mapping);
				cooficients[15] += computeEdgeCooficientDy(baseFunctionWrapper,  yDif, pointDyCombination, EdgeBaseFunctions.EDGE_LEFT_TOP, xTopLeftFar, zTopLeftFar, yBotRightNear, yTopLeftFar);
			}
			
		}
		else if(!secondComputation){{
			
			switch(this.cubePosition){
			case BOT_LEFT_NEAR:
				if(!leftFace){
					setPointEdgeCooficients(leftAdjacentCube.getCooficients()[13], 15, 7);
				}
				else{
					throw new RuntimeException("cof[15]");
				}
				break; 
			case BOT_LEFT_FAR:
				if(!leftFace){
					setPointEdgeCooficients(leftAdjacentCube.getCooficients()[13], 15, 4);
				}
				else{
					throw new RuntimeException("cof[15]");
				}
				break; 
			case BOT_RIGHT_NEAR:
				throw new RuntimeException("cof[15]");
			case BOT_RIGHT_FAR:
				throw new RuntimeException("cof[15]");
			case TOP_LEFT_NEAR:
				if(leftFace && topFace){
					setPointEdgeCooficientsOnCrossElement(topLeftAdjacentCube.getCooficients()[9], 15, 7);
				}
				else if(leftFace){
					setPointEdgeCooficients(topAdjacentCube.getCooficients()[11], 15, 7, 13, 6);
				}
				else if(topFace){
					setPointEdgeCooficients(leftAdjacentCube.getCooficients()[13], 15, 7, 11, 3);
				}
				else{
					setPointEdgeCooficientsOnTwoFaces(topAdjacentCube.getCooficients()[11], 15, 7, 13, 6, leftAdjacentCube.getCooficients()[13], 11, 3);
				}
				break;
			case TOP_LEFT_FAR:
				if(leftFace && topFace){
					setPointEdgeCooficientsOnCrossElement(topLeftAdjacentCube.getCooficients()[9], 15, 4);
				}
				else if(leftFace){
					setPointEdgeCooficients(topAdjacentCube.getCooficients()[11], 15, 4, 13, 5);
				}
				else if(topFace){
					setPointEdgeCooficients(leftAdjacentCube.getCooficients()[13], 15, 4, 11, 0);
				}
				else{
					setPointEdgeCooficientsOnTwoFaces(topAdjacentCube.getCooficients()[11], 15, 4, 13, 5, leftAdjacentCube.getCooficients()[13], 11, 0);
				}
				break;
			case TOP_RIGHT_NEAR:
				if(!topFace){
					setPointEdgeCooficients(topAdjacentCube.getCooficients()[11], 15, 7);
				}
				else{
					throw new RuntimeException("cof[15]");
				}
				break; 
			case TOP_RIGHT_FAR:
				if(!topFace){
					setPointEdgeCooficients(topAdjacentCube.getCooficients()[11], 15, 4);
				}
				else{
					throw new RuntimeException("cof[15]");
				}
				break;
			}
			
		}}
		
		if(leftNearEdge){
			if(secondComputation){
				baseFunctionWrapper.setLr(mapping);
				cooficients[16] += computeEdgeCooficientDz(baseFunctionWrapper,  zDif, pointDzCombination, EdgeBaseFunctions.EDGE_LEFT_NEAR, xTopLeftFar, yBotRightNear, zBotRightNear, zTopLeftFar);
			}
			
		}
		else if(!secondComputation){{
			
			switch(this.cubePosition){
			case BOT_LEFT_NEAR:
				if(leftFace && nearFace){
					setPointEdgeCooficientsOnCrossElement(leftNearAdjacentCube.getCooficients()[18], 16, 4);
				}
				else if(leftFace){
					setPointEdgeCooficients(nearAdjacentCube.getCooficients()[19], 16, 4, 17, 5);
				}
				else if(nearFace){
					setPointEdgeCooficients(leftAdjacentCube.getCooficients()[17], 16, 4, 19, 7);
				}
				else{
					setPointEdgeCooficientsOnTwoFaces(nearAdjacentCube.getCooficients()[19], 16, 4, 17, 5, leftAdjacentCube.getCooficients()[17], 19, 7);
				}
				break;
			case TOP_LEFT_NEAR:
				if(leftFace && nearFace){
					setPointEdgeCooficientsOnCrossElement(leftNearAdjacentCube.getCooficients()[18], 16, 0);
				}
				else if(leftFace){
					setPointEdgeCooficients(nearAdjacentCube.getCooficients()[19], 16, 0, 17, 1);
				}
				else if(nearFace){
					setPointEdgeCooficients(leftAdjacentCube.getCooficients()[17], 16, 0, 19, 3);
				}
				else{
					setPointEdgeCooficientsOnTwoFaces(nearAdjacentCube.getCooficients()[19], 16, 0, 17, 1, leftAdjacentCube.getCooficients()[17], 19, 3);
				}
				break;
			case BOT_RIGHT_NEAR:
				if(!nearFace){
					setPointEdgeCooficients(nearAdjacentCube.getCooficients()[19], 16, 4);
				}
				else{
					throw new RuntimeException("cof[16]"); 
				}
				break;
			case TOP_RIGHT_NEAR:
				if(!nearFace){
					setPointEdgeCooficients(nearAdjacentCube.getCooficients()[19], 16, 0);
				}
				else{
					throw new RuntimeException("cof[16]"); 
				}
				break;
			case BOT_LEFT_FAR:
				if(!leftFace){
					setPointEdgeCooficients(leftAdjacentCube.getCooficients()[17], 16, 4);
				}
				else throw new RuntimeException("cof[16]");
				break;
			case TOP_LEFT_FAR:
				if(!leftFace){
					setPointEdgeCooficients(leftAdjacentCube.getCooficients()[17], 16, 0);
				}
				else throw new RuntimeException("cof[16]");
				break;
			case BOT_RIGHT_FAR:
				throw new RuntimeException("cof[16]");
			case TOP_RIGHT_FAR:
				throw new RuntimeException("cof[16]");
				}
			
		}}
		
		if(rightNearEdge){
			if(secondComputation){
				baseFunctionWrapper.setLr(mapping);
				cooficients[17] += computeEdgeCooficientDz(baseFunctionWrapper,  zDif, pointDzCombination, EdgeBaseFunctions.EDGE_RIGHT_NEAR, xBotRightNear, yBotRightNear, zBotRightNear, zTopLeftFar);
			}
			
		}
		else if(!secondComputation){{
			
			switch(this.cubePosition){
			case BOT_LEFT_NEAR:
				if(!nearFace){
					setPointEdgeCooficients(nearAdjacentCube.getCooficients()[18], 17, 5);
				}
				else{
					throw new RuntimeException("cof[17]");
				}
				break;
			case TOP_LEFT_NEAR:
				if(!nearFace){
					setPointEdgeCooficients(nearAdjacentCube.getCooficients()[18], 17, 1);
				}
				else{
					throw new RuntimeException("cof[17]");
				}
				break;
			case BOT_RIGHT_NEAR:
				if(nearFace && rightFace){
					setPointEdgeCooficientsOnCrossElement(rightNearAdjacentCube.getCooficients()[19], 17, 5);
				}
				else if(rightFace){
					setPointEdgeCooficients(nearAdjacentCube.getCooficients()[18], 17, 5, 16, 4);
				}
				else if(nearFace){
					setPointEdgeCooficients(rightAdjacentCube.getCooficients()[16], 17, 5, 	18, 6);
				}
				else{
					
					setPointEdgeCooficientsOnTwoFaces(nearAdjacentCube.getCooficients()[18], 17, 5, 16, 4, rightAdjacentCube.getCooficients()[16], 18, 6);
					
				}
				break;
			case TOP_RIGHT_NEAR:
				if(nearFace && rightFace){
					setPointEdgeCooficientsOnCrossElement(rightNearAdjacentCube.getCooficients()[19], 17, 1);
				}
				else if(rightFace){
					setPointEdgeCooficients(nearAdjacentCube.getCooficients()[18], 17, 1, 16, 0);
				}
				else if(nearFace){
					setPointEdgeCooficients(rightAdjacentCube.getCooficients()[16], 17, 1, 	18, 2);
				}
				else{
					setPointEdgeCooficientsOnTwoFaces(nearAdjacentCube.getCooficients()[18], 17, 1, 16, 0, rightAdjacentCube.getCooficients()[16], 18, 2);
				}
				break;
			case BOT_LEFT_FAR:
				throw new RuntimeException("cof[17]");
			case TOP_LEFT_FAR:
				throw new RuntimeException("cof[17]");
			case BOT_RIGHT_FAR:
				setPointEdgeCooficients(rightAdjacentCube.getCooficients()[16], 17, 5);
				break; 
			case TOP_RIGHT_FAR:
				setPointEdgeCooficients(rightAdjacentCube.getCooficients()[16], 17, 1);
				break; 
				}
			
		}}
		
		if(rightFarEdge){
			if(secondComputation){
				baseFunctionWrapper.setLr(mapping);
				cooficients[18] += computeEdgeCooficientDz(baseFunctionWrapper,  zDif, pointDzCombination, EdgeBaseFunctions.EDGE_RIGHT_FAR, xBotRightNear, yTopLeftFar, zBotRightNear, zTopLeftFar);
			}
			
		}
		else if(!secondComputation){{
			
			switch(this.cubePosition){
			case BOT_LEFT_NEAR:
				throw new RuntimeException("cof[18]");
			case TOP_LEFT_NEAR:
				throw new RuntimeException("cof[18]");
			case BOT_RIGHT_NEAR:
				if(!rightFace){
					setPointEdgeCooficients(rightAdjacentCube.getCooficients()[19], 18, 6);
				}
				else{
					throw new RuntimeException("cof[18]");
				}
				break; 
			case TOP_RIGHT_NEAR:
				if(!rightFace){
					setPointEdgeCooficients(rightAdjacentCube.getCooficients()[19], 18, 2);
				}
				else{
					throw new RuntimeException("cof[18]");
				}
				break;
			case BOT_LEFT_FAR:
				if(!farFace){
					setPointEdgeCooficients(farAdjacentCube.getCooficients()[17], 18, 6);
				}
				else{
					throw new RuntimeException("cof[18]");
				}
				break; 
			case TOP_LEFT_FAR:
				if(!farFace){
					setPointEdgeCooficients(farAdjacentCube.getCooficients()[17], 18, 2);
				}
				else{
					throw new RuntimeException("cof[18]");
				}
				break;
			case BOT_RIGHT_FAR:
				if(rightFace && farFace){
					setPointEdgeCooficientsOnCrossElement(rightFarAdjacentCube.getCooficients()[16], 18, 6);
				}
				else if(rightFace){
					setPointEdgeCooficients(farAdjacentCube.getCooficients()[17], 18, 6, 19, 7);
				}
				else if(farFace){
					setPointEdgeCooficients(rightAdjacentCube.getCooficients()[19], 18, 6, 17, 5);
				}
				else{
					setPointEdgeCooficientsOnTwoFaces(farAdjacentCube.getCooficients()[17], 18, 6, 19, 7, rightAdjacentCube.getCooficients()[19], 17, 5);
				}
				break; 
			case TOP_RIGHT_FAR:
				if(rightFace && farFace){
					setPointEdgeCooficientsOnCrossElement(rightFarAdjacentCube.getCooficients()[16], 18, 2);
				}
				else if(rightFace){
					setPointEdgeCooficients(farAdjacentCube.getCooficients()[17], 18, 2, 19, 3);
				}
				else if(farFace){
					setPointEdgeCooficients(rightAdjacentCube.getCooficients()[19], 18, 2, 17, 1);
				}
				else{
					setPointEdgeCooficientsOnTwoFaces(farAdjacentCube.getCooficients()[17], 18, 2, 19, 3, rightAdjacentCube.getCooficients()[19], 17, 1);
				}
				break; 
				}
		}}
		
		if(leftFarEdge){
			
			if(secondComputation){
				baseFunctionWrapper.setLr(mapping);
				cooficients[19] += computeEdgeCooficientDz(baseFunctionWrapper,  zDif, pointDzCombination, EdgeBaseFunctions.EDGE_LEFT_FAR, xTopLeftFar, yTopLeftFar, zBotRightNear, zTopLeftFar);
			}
			
		}
		else if(!secondComputation){{
			
			 
			switch(this.cubePosition){
			case BOT_LEFT_NEAR:
				if(!leftFace){
					setPointEdgeCooficients(leftAdjacentCube.getCooficients()[18], 19, 7);
				}
				else{
					throw new RuntimeException("cof[19]");
				}
				break; 
			case TOP_LEFT_NEAR:
				if(!leftFace){
					setPointEdgeCooficients(leftAdjacentCube.getCooficients()[18], 19, 3);
				}
				else{
					throw new RuntimeException("cof[19]");
				}
				break;
			case BOT_RIGHT_NEAR:
				throw new RuntimeException("cof[19]");
			case TOP_RIGHT_NEAR:
				throw new RuntimeException("cof[19]");
			case BOT_LEFT_FAR:
				if(farFace && leftFace){
					setPointEdgeCooficientsOnCrossElement(leftFarAdjacentCube.getCooficients()[17], 19, 7);
				}
				else if(leftFace){
					setPointEdgeCooficients(farAdjacentCube.getCooficients()[16], 19, 7, 18, 6);
				}
				else if(farFace){
					setPointEdgeCooficients(leftAdjacentCube.getCooficients()[18], 19, 7, 16, 4);
				}
				else{
					setPointEdgeCooficientsOnTwoFaces(farAdjacentCube.getCooficients()[16], 19, 7, 18, 6, leftAdjacentCube.getCooficients()[18], 16, 4);
				}
				break; 
			case TOP_LEFT_FAR:
				if(farFace && leftFace){
					setPointEdgeCooficientsOnCrossElement(leftFarAdjacentCube.getCooficients()[17], 19, 3);
				}
				else if(leftFace){
					setPointEdgeCooficients(farAdjacentCube.getCooficients()[16], 19, 3, 18, 2);
				}
				else if(farFace){
					setPointEdgeCooficients(leftAdjacentCube.getCooficients()[18], 19, 3, 16, 0);
				}
				else{
					setPointEdgeCooficientsOnTwoFaces(farAdjacentCube.getCooficients()[16], 19, 3, 18, 2, leftAdjacentCube.getCooficients()[18], 16, 0);
				}
				break;
			case BOT_RIGHT_FAR:
				if(!farFace){
					setPointEdgeCooficients(farAdjacentCube.getCooficients()[16], 19, 7);
				}
				else{
					throw new RuntimeException("cof[19]");
				}
				break; 
			case TOP_RIGHT_FAR:
				if(!farFace){
					setPointEdgeCooficients(farAdjacentCube.getCooficients()[16], 19, 3);
				}
				else{
					throw new RuntimeException("cof[19]");
				}
				break;
				}
			 
		}}
	}
	
	private void computeFaceCooficients(boolean secondComputation){
		BaseFunctionWrapper baseFunctionWrapper = new BaseFunctionWrapper(); 
		
		if(nearFace){
			if(secondComputation){
				baseFunctionWrapper.setLr(mapping); 
				cooficients[20] = computeFaceCooficientDxDz(baseFunctionWrapper, xDif, zDif, 
						edgeDxCombination, edgeDzCombination, pointDxCombination, pointDzCombination,
						FaceBaseFunctions.FACE_NEAR, yBotRightNear, xTopLeftFar, xBotRightNear, zBotRightNear, zTopLeftFar);
			}
			 
		}
		
		else if(!secondComputation){{
			
			switch(this.cubePosition){
			case BOT_LEFT_NEAR:
				setPointEdgeFaceCooficients(nearAdjacentCube.getCooficients()[22], 12, 17, 20, 5);
				break; 
			case BOT_RIGHT_NEAR:
				setPointEdgeFaceCooficients(nearAdjacentCube.getCooficients()[22], 12, 16, 20, 4);
				break; 	
			case BOT_RIGHT_FAR:
				 throw new RuntimeException("cof[20]");
			case BOT_LEFT_FAR:
				 throw new RuntimeException("cof[20]");
			case TOP_LEFT_NEAR:
				setPointEdgeFaceCooficients(nearAdjacentCube.getCooficients()[22], 8, 17, 20, 1);
				break;  
			case TOP_RIGHT_NEAR:
				setPointEdgeFaceCooficients(nearAdjacentCube.getCooficients()[22], 8, 16, 20, 0);
				break;  
			case TOP_RIGHT_FAR:
				throw new RuntimeException("cof[20]");
			case TOP_LEFT_FAR:
				throw new RuntimeException("cof[20]"); 	
			}
			
		}}
		
		if(rightFace){
			if(secondComputation){
				baseFunctionWrapper.setLr(mapping); 
				cooficients[21] = computeFaceCooficientDyDz(baseFunctionWrapper, yDif, zDif, 
						edgeDyCombination, edgeDzCombination, pointDyCombination, pointDzCombination,
						FaceBaseFunctions.FACE_RIGHT, xBotRightNear, yBotRightNear, yTopLeftFar, zBotRightNear, zTopLeftFar);
			}
			
		}
		
		else if(!secondComputation){{
			
			switch(this.cubePosition){
			case BOT_LEFT_NEAR:
				throw new RuntimeException("cof[21]");
			case BOT_LEFT_FAR:
				throw new RuntimeException("cof[21]");
			case BOT_RIGHT_NEAR:
				setPointEdgeFaceCooficients(rightAdjacentCube.getCooficients()[23], 13, 18, 21, 6);
				break; 
			case BOT_RIGHT_FAR:
				setPointEdgeFaceCooficients(rightAdjacentCube.getCooficients()[23], 13, 17, 21, 5);
				break; 
			case TOP_LEFT_NEAR:
				throw new RuntimeException("cof[21]");   
			case TOP_LEFT_FAR:
				throw new RuntimeException("cof[21]"); 
			case TOP_RIGHT_NEAR:
				setPointEdgeFaceCooficients(rightAdjacentCube.getCooficients()[23], 9, 18, 21, 2);
				break; 
			case TOP_RIGHT_FAR:
				setPointEdgeFaceCooficients(rightAdjacentCube.getCooficients()[23], 9, 17, 21, 1);

				break; 
			}
			
		}}
		
		if(farFace){
			if(secondComputation){
				baseFunctionWrapper.setLr(mapping); 
				cooficients[22] = computeFaceCooficientDxDz(baseFunctionWrapper, xDif, zDif, 
						edgeDxCombination, edgeDzCombination, pointDxCombination, pointDzCombination,
						FaceBaseFunctions.FACE_FAR, yTopLeftFar, xTopLeftFar, xBotRightNear, zBotRightNear, zTopLeftFar);
			}
		}
		
		else if(!secondComputation){{
			
			switch(this.cubePosition){
			case BOT_LEFT_NEAR:
				throw new RuntimeException("cof[22]");
			case BOT_RIGHT_NEAR:
				throw new RuntimeException("cof[22]");
			case BOT_RIGHT_FAR:
				 setPointEdgeFaceCooficients(farAdjacentCube.getCooficients()[20], 19, 14, 22, 7);
				 break; 
			case BOT_LEFT_FAR:
				 setPointEdgeFaceCooficients(farAdjacentCube.getCooficients()[20], 18, 14, 22, 6);
				 break; 
			case TOP_LEFT_NEAR:
				throw new RuntimeException("cof[22]");
			case TOP_RIGHT_NEAR:
				throw new RuntimeException("cof[22]");
			case TOP_RIGHT_FAR:
				 setPointEdgeFaceCooficients(farAdjacentCube.getCooficients()[20], 19, 10, 22, 3);
				 break; 
			case TOP_LEFT_FAR:
				setPointEdgeFaceCooficients(farAdjacentCube.getCooficients()[20], 18, 10, 22, 2);
				 break;  	
			}
			
		}}
		
		if(leftFace){
			if(secondComputation){
				baseFunctionWrapper.setLr(mapping); 
				cooficients[23] = computeFaceCooficientDyDz(baseFunctionWrapper, yDif, zDif, 
						edgeDyCombination, edgeDzCombination, pointDyCombination, pointDzCombination,
						FaceBaseFunctions.FACE_LEFT, xTopLeftFar, yBotRightNear, yTopLeftFar, zBotRightNear, zTopLeftFar);
			}
			
		}
		
		else if(!secondComputation){{
			
			switch(this.cubePosition){
			case BOT_LEFT_NEAR:
				setPointEdgeFaceCooficients(leftAdjacentCube.getCooficients()[21], 15, 19, 23, 7);
				break; 
			case BOT_LEFT_FAR:
				setPointEdgeFaceCooficients(leftAdjacentCube.getCooficients()[21], 15, 16, 23, 4);
				break; 
			case BOT_RIGHT_NEAR:
				throw new RuntimeException("cof[23]");   
			case BOT_RIGHT_FAR:
				throw new RuntimeException("cof[23]");
			case TOP_LEFT_NEAR:
				setPointEdgeFaceCooficients(leftAdjacentCube.getCooficients()[21], 11, 19, 23, 3);
				break; 
			case TOP_LEFT_FAR:
				setPointEdgeFaceCooficients(leftAdjacentCube.getCooficients()[21], 11, 16, 23, 0);
				break; 
			case TOP_RIGHT_NEAR:
				throw new RuntimeException("cof[23]");
			case TOP_RIGHT_FAR:
				throw new RuntimeException("cof[23]");
			}
			
		}}
		
		if(topFace){
			if(secondComputation){
				baseFunctionWrapper.setLr(mapping);
				cooficients[24] = computeFaceCooficientDxDy(baseFunctionWrapper, xDif, yDif,
						edgeDxCombination, edgeDyCombination, pointDxCombination, pointDyCombination,
						FaceBaseFunctions.FACE_TOP, zTopLeftFar, xTopLeftFar, xBotRightNear, yBotRightNear, yTopLeftFar);
			}
			
		}
		else if(!secondComputation){{
			
			switch(this.cubePosition){
			case BOT_LEFT_NEAR:
				throw new RuntimeException("cof[24]");
			case BOT_RIGHT_NEAR:
				throw new RuntimeException("cof[24]");
			case BOT_RIGHT_FAR:
				throw new RuntimeException("cof[24]");
			case BOT_LEFT_FAR:
				throw new RuntimeException("cof[24]");
			case TOP_LEFT_NEAR:
				setPointEdgeFaceCooficients(topAdjacentCube.getCooficients()[25], 13, 14, 24, 6);
				break;  
			case TOP_RIGHT_NEAR:
				setPointEdgeFaceCooficients(topAdjacentCube.getCooficients()[25], 15, 14, 24, 7);
				break;  
			case TOP_RIGHT_FAR:
				setPointEdgeFaceCooficients(topAdjacentCube.getCooficients()[25], 15, 12, 24, 4);
				break;  
			case TOP_LEFT_FAR:
				setPointEdgeFaceCooficients(topAdjacentCube.getCooficients()[25], 13, 12, 24, 5);
				break;   	
			}
			
		}}
		
		if(botFace){
			if(secondComputation){
				baseFunctionWrapper.setLr(mapping);
				cooficients[25] = computeFaceCooficientDxDy(baseFunctionWrapper, xDif, yDif,
						edgeDxCombination, edgeDyCombination, pointDxCombination, pointDyCombination,
						FaceBaseFunctions.FACE_BOT, zBotRightNear, xTopLeftFar, xBotRightNear, yBotRightNear, yTopLeftFar);
			}
			
		}
		else if(!secondComputation){{
			
			switch(this.cubePosition){
			case BOT_LEFT_NEAR:
				
				setPointEdgeFaceCooficients(botAdjacentCube.getCooficients()[24], 10, 9, 25, 2);
				
				break;
			case BOT_RIGHT_NEAR:
				
				setPointEdgeFaceCooficients(botAdjacentCube.getCooficients()[24], 10, 11, 25, 3);
				
				break; 
			case BOT_RIGHT_FAR:
				
				setPointEdgeFaceCooficients(botAdjacentCube.getCooficients()[24], 8, 11, 25, 0);
				
				break; 
			case BOT_LEFT_FAR:
				
				setPointEdgeFaceCooficients(botAdjacentCube.getCooficients()[24], 8, 9, 25, 1);
				
				break; 
			case TOP_LEFT_NEAR:
				throw new RuntimeException("cof[25]");
			case TOP_RIGHT_NEAR:
				throw new RuntimeException("cof[25]");
			case TOP_RIGHT_FAR:
				throw new RuntimeException("cof[25]");
			case TOP_LEFT_FAR:
				throw new RuntimeException("cof[25]");
			}
			
		}}
	}

	public void printDiffrences(){

		java.util.Random random = new java.util.Random();
		random.setSeed(System.currentTimeMillis());

		double xDif = xBotRightNear - xTopLeftFar;
		double yDif = yTopLeftFar - yBotRightNear; 
		double zDif = zTopLeftFar - zBotRightNear; 
		
		
		
		this.printCube();
		for(int i = 0 ; i<4; i++){
			double x = random.nextDouble()*xDif + xTopLeftFar;

			double y = random.nextDouble()*yDif + yBotRightNear;

			double z = random.nextDouble()*zDif + zBotRightNear; 
			System.out.println("funkcja " + interpolatedFunction.computeValue(x, y, z) + 
					" interpolacja " + computeValue(x, y, z) + " roznica " + (interpolatedFunction.computeValue(x, y, z) - computeValue(x, y, z)) + " w punkcie " + "(" + x + "," + y + "," + z + ")");
			
		}
		
	}
	
	public void checkDifrences(){
		
		java.util.Random random = new java.util.Random();
		random.setSeed(System.currentTimeMillis());

		double xDif = xBotRightNear - xTopLeftFar;
		double yDif = yTopLeftFar - yBotRightNear; 
		double zDif = zTopLeftFar - zBotRightNear; 

		for(int i = 0 ; i<4; i++){
			double x = random.nextDouble()*xDif + xTopLeftFar;

			double y = random.nextDouble()*yDif + yBotRightNear;

			double z = random.nextDouble()*zDif + zBotRightNear; 
			
			if(Math.abs((interpolatedFunction.computeValue(x, y, z) - computeValue(x, y, z))) > 0.000001){
				System.out.println("BLAD!");
				printValues(); 
				System.exit(-1);
			}
			
		}
		
		
	}
	
	public double getRandomPointValue(){
		
		double xDif = xBotRightNear - xTopLeftFar;
		double yDif = yTopLeftFar - yBotRightNear; 
		double zDif = zTopLeftFar - zBotRightNear; 

		double x = random.nextDouble()*xDif + xTopLeftFar;

		double y = random.nextDouble()*yDif + yBotRightNear;

		double z = random.nextDouble()*zDif + zBotRightNear; 
			
		return interpolatedFunction.computeValue(x, y, z);	
	
	}
	
	public void printValues(){
		interpolatedFunction.setNormal();
		System.out.println("1 " + computeValue(xTopLeftFar, yBotRightNear, zBotRightNear) + " " + interpolatedFunction.computeValue(xTopLeftFar, yBotRightNear, zBotRightNear));
		System.out.println("2 " + computeValue(xBotRightNear, yBotRightNear, zBotRightNear) + " " + interpolatedFunction.computeValue(xBotRightNear, yBotRightNear, zBotRightNear));
		System.out.println("3 " + computeValue(xTopLeftFar, yTopLeftFar, zBotRightNear) + " " + interpolatedFunction.computeValue(xTopLeftFar, yTopLeftFar, zBotRightNear));
		System.out.println("4 " + computeValue(xBotRightNear, yTopLeftFar, zBotRightNear) + " " + interpolatedFunction.computeValue(xBotRightNear, yTopLeftFar, zBotRightNear));
		System.out.println("5 " + computeValue(xTopLeftFar, yBotRightNear, zTopLeftFar) + " " + interpolatedFunction.computeValue(xTopLeftFar, yBotRightNear, zTopLeftFar));
		System.out.println("6 " + computeValue(xBotRightNear, yBotRightNear, zTopLeftFar) + " " + interpolatedFunction.computeValue(xBotRightNear, yBotRightNear, zTopLeftFar));
		System.out.println("7 " + computeValue(xTopLeftFar, yTopLeftFar, zTopLeftFar) + " " + interpolatedFunction.computeValue(xTopLeftFar, yTopLeftFar, zTopLeftFar));
		System.out.println("8 " + computeValue(xBotRightNear, yTopLeftFar, zTopLeftFar) + " " + interpolatedFunction.computeValue(xBotRightNear, yTopLeftFar, zTopLeftFar));

		System.out.println("near " + nearFace);
		System.out.println("right " + rightFace);
		System.out.println("far " + farFace);
		System.out.println("left " + leftFace);
		System.out.println("top " + botFace);
		System.out.println("bot " + botFace);
	}
	
	public void printCooficients(){
		for(int i = 0 ; i < 27 ; i++){
			System.out.println(new Integer(i+1).toString() + " " + cooficients[i]);
		}
		
	}
	
	public double isErrorLowEnough(double desiredErrorRate, int maxLevelOfDivision){

		
		
		double quadInt1 = quadrature.definiteTripleIntegral(xTopLeftFar, xBotRightNear, yBotRightNear, 
				yTopLeftFar, zBotRightNear, zTopLeftFar, new SobolevSpaceDerivativeCombinationCombinationWrapper(Dimension.dx,this));
		
		double quadInt2 = quadrature.definiteTripleIntegral(xTopLeftFar, xBotRightNear, yBotRightNear, 
				yTopLeftFar, zBotRightNear, zTopLeftFar, new SobolevSpaceDerivativeCombinationCombinationWrapper(Dimension.dy,this));
		
		double quadInt3 = quadrature.definiteTripleIntegral(xTopLeftFar, xBotRightNear, yBotRightNear, 
				yTopLeftFar, zBotRightNear, zTopLeftFar, new SobolevSpaceDerivativeCombinationCombinationWrapper(Dimension.dz,this));
		
		double quadInt4 = quadrature.definiteTripleIntegral(xTopLeftFar, xBotRightNear, yBotRightNear, 
				yTopLeftFar, zBotRightNear, zTopLeftFar, new SobolevSpaceDerivativeCombinationCombinationWrapper(Dimension.normal,this));
		
		//System.out.println("normal directiron " + quadInt4);
		//System.out.println("x " + quadInt1);
		errK = quadInt1 + quadInt2 + quadInt3 + quadInt4;
		errK = quadInt4;
		
		interpolatedFunction.setNormal(); 
		if(maxLevelOfDivision <= this.levelOfDivision){
			errKLowEnough = true; 
			return errK;
		}
		errKLowEnough =  errK <=  desiredErrorRate;
		//System.out.println(errK);
		return errK; 
	}
	
	public boolean isErrKLowEnough(){
		return errKLowEnough; 
	}
	
	
	
	private class SobolevSpaceDerivativeCombinationCombinationWrapper implements TripArgFunction{
		
		
		private Dimension d; 
		private TripArgFunction cube; 
		public SobolevSpaceDerivativeCombinationCombinationWrapper(Dimension d, TripArgFunction cube){
			this.d = d; 
			this.cube = cube; 
		}

		@Override
		public double computeValue(double x, double y, double z) {

			switch(d){
			case dx:
				interpolatedFunction.setDx(); 
				break; 
			case dy:
				interpolatedFunction.setDy(); 
				break; 
			case dz:
				interpolatedFunction.setDz();
				break; 
			default:
				interpolatedFunction.setNormal();
				break; 
			}
			BaseFunctionWrapper baseFunctionWrapper = new BaseFunctionWrapper(); 
			baseFunctionWrapper.setWrappedFunction(InteriorBaseFunction.INTERIOR.getBaseFunctionDerivative(d));
			baseFunctionWrapper.setLr(mapping);
			
			double functionAndInterpolationDiffrence = 
			interpolatedFunction.computeValue(x, y, z); 
			//System.out.println(d + " " +interpolatedFunction.computeValue(x,y,z));
			switch(d){
			case dx:
				
				functionAndInterpolationDiffrence -= ((dxCombinationCombination.computeValue(x, y, z) +
				cooficients[26]*xDif*baseFunctionWrapper.computeValue(x, y, z)));
				
			break; 
			case dy:
				functionAndInterpolationDiffrence -= ((dyCombinationCombination.computeValue(x, y, z) +
				cooficients[26]*yDif*baseFunctionWrapper.computeValue(x, y, z)));
			break; 
			case dz:
				functionAndInterpolationDiffrence -= ((dzCombinationCombination.computeValue(x, y, z) +
				cooficients[26]*zDif*baseFunctionWrapper.computeValue(x, y, z)));
				
			break; 
			default:
				//System.out.println("x " + functionAndInterpolationDiffrence);
				functionAndInterpolationDiffrence -= cube.computeValue(x, y, z);
				//System.out.println("t " + cube.computeValue(x, y, z));
				//System.out.println("kwadrat " + functionAndInterpolationDiffrence*functionAndInterpolationDiffrence);
				break; 
				
			}

			return functionAndInterpolationDiffrence*functionAndInterpolationDiffrence; 
			
		}
		
		
	}
	public void printErrK(){
		System.out.println("poziom " + this.levelOfDivision + " norma " +  errK);
	}

}
