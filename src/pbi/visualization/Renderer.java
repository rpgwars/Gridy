package pbi.visualization;
/*
import java.awt.*;
import java.awt.event.*;
import java.util.List;

import javax.swing.*;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAnimatorControl;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.GLU;

import org.junit.internal.ExactComparisonCriteria;

import pbi.computations.data.ExampleFunctionElementSpace3;
import pbi.computations.part.ComputationCube;
import pbi.computations.part.DrawableCube;
import pbi.computations.part.ReferenceCube;
import pbi.computations.partitions.ReferencePartitioner;

import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.gl2.GLUT;

import static javax.media.opengl.GL.*; // GL constants
import static javax.media.opengl.GL2.*; // GL2 constants
//http://stackoverflow.com/questions/1732117/jogl-glu-gluunproject-always-returning-0-0 


@SuppressWarnings("serial")
public class Renderer extends GLCanvas implements GLEventListener, KeyListener,
		MouseListener, MouseMotionListener {

	// Setup OpenGL Graphics Renderer
	private DrawableCubeUtils drawableCubeUtils;
	private List<DrawableCube> allDataCubes;
	private List<DrawableCube> visibleDataCubes;
	private double[] quadVertices;

	boolean colourRising = true;
	private GLU glu; // for the GL Utility
	private GL2 gl;
	private final GLUT glut;
	private float rotateX = 0.0f;
	private float rotateY = 0.0f;
	private float rotateZ = 0.0f;
	private boolean zRotation = false;

	private float scale = 1.0f;

	private float mousePressedPositionX;
	private float mousePressedPositionY;
	private boolean meshView;
	private boolean interpolationView;
	private boolean interpolationQualityView;
	private boolean axisAndScaleView;

	private double upperValueToColorScaleBoundary;
	private double lowerValueToColorScaleBoundary;
	private double originalScale;

	private double[] projectionMatrix = new double[16];
	private double[] modelViewMatrix = new double[16];
	private int[] viewPort = new int[4];

	
	public Renderer(DrawableCubeUtils drawableCubeUtils) {

		this.addGLEventListener(this);
		this.addKeyListener(this);
		this.setFocusable(true);
		this.requestFocus(true);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.meshView = true;
		this.interpolationQualityView = false;
		this.interpolationView = false;
		this.axisAndScaleView = false;
		glut = new GLUT();

		this.drawableCubeUtils = drawableCubeUtils;
		this.allDataCubes = drawableCubeUtils.getAllCubes();
		quadVertices = new double[30 * allDataCubes.size()];

		int offset = 0;

		for (DrawableCube dc : allDataCubes) {
			dc.saveCubeQuadStripCoordinatesCoordinates(quadVertices, offset);
			offset += 30;
		}

		visibleDataCubes = drawableCubeUtils.getVisibleCubes(false);

		upperValueToColorScaleBoundary = drawableCubeUtils
				.getFunctionHighValue() * 1.15;
		lowerValueToColorScaleBoundary = drawableCubeUtils
				.getFunctionLowValue() * 0.85;
		this.originalScale = upperValueToColorScaleBoundary
				- lowerValueToColorScaleBoundary;
	}

	private void resetVisibleCubesCoordinates() {
		if (interpolationQualityView == false && interpolationView == false) {
			quadVertices = new double[30 * allDataCubes.size()];

			int offset = 0;

			for (DrawableCube dc : allDataCubes) {
				dc.saveCubeQuadStripCoordinatesCoordinates(quadVertices, offset);
				offset += 30;
			}
		} else {
			quadVertices = new double[30 * visibleDataCubes.size()];

			int offset = 0;

			for (DrawableCube dc : visibleDataCubes) {
				dc.saveCubeQuadStripCoordinatesCoordinates(quadVertices, offset);
				offset += 30;
			}

		}

	}

	// ------ Implement methods declared in GLEventListener ------

	
	@Override
	public void init(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2(); // get the OpenGL graphics context
		glu = new GLU(); // get GL Utilities
		gl.glClearColor(1.0f, 1.0f, 1.0f, 0.0f); // set background (clear) color
		gl.glClearDepth(1.0f); // set clear depth value to farthest
		gl.glEnable(GL_DEPTH_TEST); // enables depth testing
		gl.glDepthFunc(GL_LEQUAL); // the type of depth test to do
		gl.glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST); // best
																// perspective
																// correction
		gl.glShadeModel(GL_SMOOTH); // blends colors nicely, and smoothes out
									// lighting
		this.gl = gl;
	}


	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width,
			int height) {
		GL2 gl = drawable.getGL().getGL2(); // get the OpenGL 2 graphics context

		if (height == 0)
			height = 1; // prevent divide by zero
		float aspect = (float) width / height;

		// Set the view port (display area) to cover the entire window
		gl.glViewport(0, 0, width, height);
		gl.glGetIntegerv(GL2.GL_VIEWPORT, viewPort, 0);
		// Setup perspective projection, with aspect ratio matches viewport
		gl.glMatrixMode(GL_PROJECTION); // choose projection matrix
		gl.glLoadIdentity(); // reset projection matrix
		glu.gluPerspective(45.0, aspect, 0.1, 100.0); // fovy, aspect, zNear,
														// zFar

		// gl.glFrustum(-5*aspect, 5*aspect, -5*aspect, 5*aspect, 2*aspect,
		// 10*aspect);
		gl.glGetDoublev(GL2.GL_PROJECTION_MATRIX, projectionMatrix, 0);

		// Enable the model-view transform
		gl.glMatrixMode(GL_MODELVIEW);
		gl.glLoadIdentity(); // reset

	}


	@Override
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2(); // get the OpenGL 2 graphics context
		gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear color
																// and depth
																// buffers
		gl.glMatrixMode(GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glTranslatef(0.0f, 0.0f, -6.0f);
		gl.glScalef(scale, scale, scale);
		gl.glRotatef(rotateX, 1.0f, 0.0f, 0.0f);
		gl.glRotatef(rotateY, 0.0f, 1.0f, 0.0f);
		gl.glRotatef(rotateZ, 0.0f, 0.0f, 1.0f);
		gl.glGetDoublev(GL2.GL_MODELVIEW_MATRIX, modelViewMatrix, 0);

		if (meshView)
			displayFullMesh(gl);
		if (interpolationView)
			displayFunctionValues(gl, false);
		if (interpolationQualityView)
			displayFunctionValues(gl, true);

	}

	private void displayFullMesh(GL2 gl) {

		gl.glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
		int offset = 0;
		gl.glLineWidth(3.0f);

		for (int k = 0; k < quadVertices.length / 30; k++) {

			gl.glBegin(GL_QUAD_STRIP);
			for (int i = 0; i < 10; i++) {

				gl.glColor3f(0.125f, 0.150f, 0.2f);
				gl.glVertex3d(quadVertices[offset + 3 * i], quadVertices[offset
						+ 3 * i + 1], quadVertices[offset + 3 * i + 2]);
			}
			gl.glEnd();

			offset += 30;
		}

	}

	private void displayFunctionValues(GL2 gl, boolean approximationQuality) {

		gl.glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
		ValueToColorConverter simpleColorConverter;
		if (!approximationQuality) {
			simpleColorConverter = new DefaultConverter(
					lowerValueToColorScaleBoundary,
					upperValueToColorScaleBoundary);
		} else {
			simpleColorConverter = new DefaultConverter(0.0, 0.5);
		}
		for (DrawableCube dc : visibleDataCubes) {
			gl.glBegin(GL_QUADS);
			double[] buffer = dc
					.getFaceVertexAndColourData(approximationQuality);
			for (int i = 0; i < buffer.length; i = i + 4) {
				gl.glColor3dv(simpleColorConverter.convert(buffer[i + 3]), 0);
				gl.glVertex3d(buffer[i], buffer[i + 1], buffer[i + 2]);

			}
			gl.glEnd();
		}

		if (axisAndScaleView) {

			double[] domainBoundaries = drawableCubeUtils.getDomainBoundaries();

			gl.glColor3d(0.6, 0.3, 0.8);
			gl.glRasterPos3d(-1.0, -1.0, -1.0);
			glut.glutBitmapString(GLUT.BITMAP_TIMES_ROMAN_24,
					domainBoundaries[0] + ", " + domainBoundaries[2] + ", "
							+ domainBoundaries[4]);
			gl.glRasterPos3d(-1.0, -1.0, 1.0);
			glut.glutBitmapString(GLUT.BITMAP_TIMES_ROMAN_24,
					domainBoundaries[0] + ", " + domainBoundaries[2] + ", "
							+ domainBoundaries[5]);
			gl.glRasterPos3d(-1.0, 1.0, -1.0);
			glut.glutBitmapString(GLUT.BITMAP_TIMES_ROMAN_24,
					domainBoundaries[0] + ", " + domainBoundaries[3] + ", "
							+ domainBoundaries[4]);
			gl.glRasterPos3d(1.0, -1.0, -1.0);
			glut.glutBitmapString(GLUT.BITMAP_TIMES_ROMAN_24,
					domainBoundaries[1] + ", " + domainBoundaries[2] + ", "
							+ domainBoundaries[4]);
			gl.glRasterPos3d(1.0, 1.0, 1.0);
			glut.glutBitmapString(GLUT.BITMAP_TIMES_ROMAN_24,
					domainBoundaries[1] + ", " + domainBoundaries[3] + ", "
							+ domainBoundaries[5]);
			gl.glRasterPos3d(1.0, 1.0, -1.0);
			glut.glutBitmapString(GLUT.BITMAP_TIMES_ROMAN_24,
					domainBoundaries[1] + ", " + domainBoundaries[3] + ", "
							+ domainBoundaries[4]);
			gl.glRasterPos3d(1.0, -1.0, 1.0);
			glut.glutBitmapString(GLUT.BITMAP_TIMES_ROMAN_24,
					domainBoundaries[1] + ", " + domainBoundaries[2] + ", "
							+ domainBoundaries[5]);
			gl.glRasterPos3d(-1.0, 1.0, 1.0);
			glut.glutBitmapString(GLUT.BITMAP_TIMES_ROMAN_24,
					domainBoundaries[0] + ", " + domainBoundaries[3] + ", "
							+ domainBoundaries[5]);

			gl.glLoadIdentity();
			gl.glTranslatef(-2.5f, 4.5f, -6.0f);

			double[] info = simpleColorConverter.getScaleInformation();
			gl.glDisable(GL_DEPTH_TEST);
			gl.glColor3dv(simpleColorConverter.convert(info[0]), 0);
			gl.glRasterPos3d(-2.0, -2.0, -1.0);
			glut.glutBitmapString(GLUT.BITMAP_HELVETICA_12,
					Double.toString(info[0]));
			gl.glBegin(GL_TRIANGLES);
			gl.glVertex3d(-1.1, -2.0, -1.0);
			gl.glVertex3d(-1.0, -2.0, -1.0);
			gl.glVertex3d(-1.1, -1.9, -1.0);
			gl.glEnd();
			gl.glColor3dv(simpleColorConverter.convert(info[1]), 0);
			gl.glRasterPos3d(-2.0, -2.2, -1.0);
			glut.glutBitmapString(GLUT.BITMAP_HELVETICA_12,
					Double.toString(info[1]));
			gl.glBegin(GL_TRIANGLES);
			gl.glVertex3d(-1.1, -2.2, -1.0);
			gl.glVertex3d(-1.0, -2.2, -1.0);
			gl.glVertex3d(-1.1, -2.1, -1.0);
			gl.glEnd();
			gl.glColor3dv(simpleColorConverter.convert(info[2]), 0);
			gl.glRasterPos3d(-2.0, -2.4, -1.0);
			glut.glutBitmapString(GLUT.BITMAP_HELVETICA_12,
					Double.toString(info[2]));
			gl.glBegin(GL_TRIANGLES);
			gl.glVertex3d(-1.1, -2.4, -1.0);
			gl.glVertex3d(-1.0, -2.4, -1.0);
			gl.glVertex3d(-1.1, -2.3, -1.0);
			gl.glEnd();
			gl.glColor3dv(simpleColorConverter.convert(info[3]), 0);
			gl.glRasterPos3d(-2.0, -2.6, -1.0);
			glut.glutBitmapString(GLUT.BITMAP_HELVETICA_12,
					Double.toString(info[3]));
			gl.glBegin(GL_TRIANGLES);
			gl.glVertex3d(-1.1, -2.6, -1.0);
			gl.glVertex3d(-1.0, -2.6, -1.0);
			gl.glVertex3d(-1.1, -2.5, -1.0);
			gl.glEnd();
			gl.glColor3dv(simpleColorConverter.convert(info[4]), 0);
			gl.glRasterPos3d(-2.0, -2.8, -1.0);
			glut.glutBitmapString(GLUT.BITMAP_HELVETICA_12,
					Double.toString(info[4]));
			gl.glBegin(GL_TRIANGLES);
			gl.glVertex3d(-1.1, -2.8, -1.0);
			gl.glVertex3d(-1.0, -2.8, -1.0);
			gl.glVertex3d(-1.1, -2.7, -1.0);
			gl.glEnd();
			gl.glColor3dv(simpleColorConverter.convert(info[5]), 0);
			gl.glRasterPos3d(-2.0, -3.0, -1.0);
			glut.glutBitmapString(GLUT.BITMAP_HELVETICA_12,
					Double.toString(info[5]));
			gl.glBegin(GL_TRIANGLES);
			gl.glVertex3d(-1.1, -3.0, -1.0);
			gl.glVertex3d(-1.0, -3.0, -1.0);
			gl.glVertex3d(-1.1, -2.9, -1.0);
			gl.glEnd();
			gl.glEnable(GL_DEPTH_TEST);

		}

	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();

		switch (keyCode) {

		case KeyEvent.VK_ESCAPE:

			new Thread() {
				public void run() {
					GLAnimatorControl animator = getAnimator();
					if (animator.isStarted())
						animator.stop();
					System.exit(0);

				}
			}.start();
			break;
		case KeyEvent.VK_Q:
			drawableCubeUtils.addTier(true, allDataCubes, visibleDataCubes);
			resetVisibleCubesCoordinates();

			break;
		case KeyEvent.VK_W:
			drawableCubeUtils.removeTier(true, allDataCubes, visibleDataCubes);
			resetVisibleCubesCoordinates();

			break;
		case KeyEvent.VK_E:
			drawableCubeUtils.removeTier(false, allDataCubes, visibleDataCubes);
			resetVisibleCubesCoordinates();

			break;
		case KeyEvent.VK_R:
			drawableCubeUtils.addTier(false, allDataCubes, visibleDataCubes);
			resetVisibleCubesCoordinates();

			break;
		case 107:
			scale += 0.05f;
			break;
		case 45:
			scale -= 0.05f;
			break;
		case 49:
			meshView = !meshView;
			break;
		case 50:
			interpolationView = !interpolationView;
			interpolationQualityView = false;
			break;
		case 51:
			interpolationQualityView = !interpolationQualityView;
			interpolationView = false;
			break;
		case 52:
			axisAndScaleView = !axisAndScaleView;
			break;
		case KeyEvent.VK_Z:
			upperValueToColorScaleBoundary += originalScale * 0.05;
			break;
		case KeyEvent.VK_X:
			upperValueToColorScaleBoundary -= originalScale * 0.05;
			break;
		case KeyEvent.VK_C:
			lowerValueToColorScaleBoundary -= originalScale * 0.05;
			break;
		case KeyEvent.VK_V:
			lowerValueToColorScaleBoundary += originalScale * 0.05;
			break;
		case KeyEvent.VK_P:
			zRotation = !zRotation;
			break;

		}

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void mouseClicked(MouseEvent e) {

		// glu.gluUnProject(e.getX(),this.getSize().height - e.getY(), 0.0,
		// modelViewMatrix, 0, projectionMatrix, 0, viewPort, 0, obj, 0);

	}

	@Override
	public void mousePressed(MouseEvent e) {
		mousePressedPositionX = e.getX();
		mousePressedPositionY = e.getY();
	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {

		rotateY += (e.getX() - mousePressedPositionX) / 15.0f;
		if (zRotation)
			rotateZ += (e.getY() - mousePressedPositionY) / 15.0f;
		else
			rotateX += (e.getY() - mousePressedPositionY) / 15.0f;

		mousePressedPositionX = e.getX();
		mousePressedPositionY = e.getY();

	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}
}*/