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


import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.*;

public class VisualizationMain extends HttpServlet{

	private boolean computationsOn = false; 
	
	private synchronized boolean computationsOn(){
		if(!computationsOn) {
			computationsOn = true; 
			return false; 
		}
		return true; 
			
	}
	
	private synchronized void computationsOff(){ 
		computationsOn = false; 
	}

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
    	
    	if(computationsOn()){
    		resp.getWriter().print("Obliczenia w trakcie\n");
    		for(String str : log)
    			resp.getWriter().println(str);
    		
    	}
    	else{
    		resp.getWriter().print("rozpoczynam obliczenia\n");
    		startComputations("sixHalfBalls.dat", 0.00005);
    	}

    }
    
    List<String> log; 
    
    private void startComputations(final String fileName, final double error){
    	ReferenceCube initialCube = new ReferenceCube(0.0, 1.0, 1.0, 1.0, 0.0,
				0.0, 0, null);
		ReferencePartitioner p = new ReferencePartitioner(initialCube);

		final HAdaptationMultiThreadComputationReferencePartitioner x = new HAdaptationMultiThreadComputationReferencePartitioner(
				p, 4);

		log = x.getLog();
		System.out.println(new File(".").getAbsolutePath());
		File f = new File(fileName);
		if (!f.exists()) {
			System.out.println("Data file does not exist");
			System.exit(0);
		}
		
		initialCube = new ReferenceCube(0.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0,
				null);
		p = new ReferencePartitioner(initialCube);

		Thread worker = new Thread(){
			@Override
			public void run() {
				x.startAdaptiveMultiThreadComputation(new DataStructure(fileName),error, 4);
				computationsOff();
			}
		};
		worker.start();

    }

	public static void main(String[] args) throws Exception {
		
				
        Server server = new Server(Integer.valueOf(System.getenv("PORT")));
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);
        context.addServlet(new ServletHolder(new VisualizationMain()),"/*");
        server.start();
        server.join();

	

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
