package integrals;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import pbi.computations.function.DoubArgFunction;
import pbi.computations.function.TripArgFunction;
import pbi.computations.integrals.GaussianQuadrature;




public class GaussianQuadratureTest {

	
	GaussianQuadrature integrator; 
	
	@Before
	public void setIntegrator(){
		integrator  = new GaussianQuadrature();
	}
	
	
	
	
	@Test
	public void testDoubleIntegral(){
		
		DoubArgFunction function = new DoubArgFunction(){
			
			public double computeValue(double v1, double v2){
				
				return v1*v2 + 3*v1; 
			}
			
		};
		
		assertEquals(integrator.definiteDoubleIntegral(1, 2, 3, 4, function),(double)39/4,0.0005); 
		
	}
	
	@Test
	public void testTripleIntegral(){
		
		TripArgFunction function = new TripArgFunction(){
			
			public double computeValue(double v1, double v2, double v3){
				
				return v1*v2*v3 + 3*v1 + v2*v2; 
			}
			
		};
		
		assertEquals(integrator.definiteTripleIntegral(1, 2, 3, 4, 5, 6, function),(double)1097/24,0.005);  
		
	}
	
	
}
