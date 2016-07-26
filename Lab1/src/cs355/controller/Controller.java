package cs355.controller;
import static java.lang.Math.*;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cs355.GUIFunctions;
import cs355.controller.CS355Controller;
import cs355.model.drawing.*;

public class Controller implements CS355Controller, MouseListener, MouseMotionListener {
	
	private Model model;
	private Tool selectedTool;
	private Color selectedColor;
	private List<Point2D.Double> trianglePoints;
	private Point initPoint;
	private Point2D dragOriginCenter;
	private Point2D dragOriginStart;
	private Point2D dragOriginEnd;
	private Point2D dragOriginMouse;
	private Line draggingLine;
	private boolean hitHandle;
	private double scale;

	public Controller(Model m) {
		model = m;
		selectedTool = Tool.COLOR;
		selectedColor = Color.white;
		trianglePoints = new ArrayList<Point2D.Double>();
		initPoint = new Point();
		scale = 1;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(selectedTool == Tool.TRIANGLE) {
			drawTriangle(e);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(selectedTool == Tool.TRIANGLE) {
			// Do nothing.
		} else if(selectedTool == Tool.LINE) {
			model.addShape(new Line(selectedColor
								  , (new Point2D.Double(e.getPoint().getX(), e.getPoint().getY()))
								  , (new Point2D.Double(e.getPoint().getX(), e.getPoint().getY()))));
		} else if(selectedTool == Tool.SQUARE) {
			initPoint = e.getPoint();
			Point2D.Double p = new Point2D.Double(initPoint.getX(), initPoint.getY());
			Square s = new Square(selectedColor, p, 0);
			//s.setUpperLeft(p);
			model.addShape(s);
		} else if(selectedTool == Tool.RECTANGLE) {
			initPoint = e.getPoint();
			Point2D.Double p = new Point2D.Double(initPoint.getX(), initPoint.getY());
			Rectangle r = new Rectangle(selectedColor, p, 0, 0);
			//r.setUpperLeft(p);
			model.addShape(r);
		} else if(selectedTool == Tool.CIRCLE) {
			initPoint = e.getPoint();
			model.addShape(new Circle(selectedColor
									, new Point2D.Double(initPoint.getX(), initPoint.getY())
									, 0));
		} else if(selectedTool == Tool.ELLIPSE) {
			initPoint = e.getPoint();
			model.addShape(new Ellipse(selectedColor
									 , new Point2D.Double(initPoint.getX(), initPoint.getY())
									 , 0
									 , 0));
		} else if(selectedTool == Tool.SELECTION) {
			dragOriginMouse = new Point2D.Double(e.getX(), e.getY());
			Point2D.Double p = new Point2D.Double(e.getPoint().getX(), e.getPoint().getY());
			if(!clickTest(p, 4)) {
				model.setSelectedShape(null);
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		initPoint = null;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if(selectedTool == Tool.LINE) {
			((Line)model.getShape(0)).setEnd(new Point2D.Double(e.getPoint().getX(), e.getPoint().getY()));
			model.update();
		} else if(selectedTool == Tool.SQUARE) {
			redrawSquare(e);
			model.update();
		} else if(selectedTool == Tool.RECTANGLE) {
			redrawRectangle(e);
			model.update();
		} else if(selectedTool == Tool.CIRCLE) {
			redrawCircle(e);
			model.update();
		} else if(selectedTool == Tool.ELLIPSE) {
			redrawEllipse(e);
			model.update();
		} else if(selectedTool == Tool.SELECTION) {
			if(model.getSelectedShape() != null) {
				if(false) {
					//rotateShape();
					// Come in here if the handle was hit... set a flag...?
				} else {
					dragShape(e, model.getSelectedShape());
					model.update();
				}
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}

	@Override
	public void colorButtonHit(Color c) {
		selectedColor = c;
		GUIFunctions.changeSelectedColor(c);
		
		if(model.getSelectedShape() != null) {
			model.getSelectedShape().setColor(c);
		}
	}

	@Override
	public void lineButtonHit() {
		selectedTool = Tool.LINE;
		model.setSelectedShape(null);
		trianglePoints.clear();
	}

	@Override
	public void squareButtonHit() {
		selectedTool = Tool.SQUARE;
		model.setSelectedShape(null);
		trianglePoints.clear();
	}

	@Override
	public void rectangleButtonHit() {
		selectedTool = Tool.RECTANGLE;
		model.setSelectedShape(null);
		trianglePoints.clear();
	}

	@Override
	public void circleButtonHit() {
		selectedTool = Tool.CIRCLE;
		model.setSelectedShape(null);
		trianglePoints.clear();
	}

	@Override
	public void ellipseButtonHit() {
		selectedTool = Tool.ELLIPSE;
		model.setSelectedShape(null);
		trianglePoints.clear();
	}

	@Override
	public void triangleButtonHit() {
		selectedTool = Tool.TRIANGLE;
		model.setSelectedShape(null);
	}

	@Override
	public void selectButtonHit() {
		selectedTool = Tool.SELECTION;
		model.setSelectedShape(null);
		trianglePoints.clear();
	}

	@Override
	public void zoomInButtonHit() {
		double newFactor = min(Transformations.getZoomFactor() * 2, 4);
		Transformations.setZoomFactor(newFactor);
		model.update();
	}

	@Override
	public void zoomOutButtonHit() {
		double newFactor = max(Transformations.getZoomFactor() / 2, .25);
		Transformations.setZoomFactor(newFactor);
		model.update();
	}

	@Override
	public void hScrollbarChanged(int value) {
		Transformations.setHorizontalViewPosition(value);
		model.update();
	}

	@Override
	public void vScrollbarChanged(int value) {
		Transformations.setVerticalViewPosition(value);
		model.update();
	}

	@Override
	public void openScene(File file) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void toggle3DModelDisplay() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(Iterator<Integer> iterator) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void openImage(File file) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveImage(File file) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void toggleBackgroundDisplay() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveDrawing(File file) {
		model.save(file);
	}

	@Override
	public void openDrawing(File file) {
		model.open(file);
	}

	@Override
	public void doDeleteShape() {
		if(model.getSelectedShape() != null) {
			model.deleteShape(model.getShapes().indexOf(model.getSelectedShape()));
		}
	}

	@Override
	public void doEdgeDetection() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doSharpen() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doMedianBlur() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doUniformBlur() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doGrayscale() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doChangeContrast(int contrastAmountNum) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doChangeBrightness(int brightnessAmountNum) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doMoveForward() {
		if(model.getSelectedShape() != null) {
			model.moveForward(model.getShapes().indexOf(model.getSelectedShape()));
		}
	}

	@Override
	public void doMoveBackward() {
		if(model.getSelectedShape() != null) {
			model.moveBackward(model.getShapes().indexOf(model.getSelectedShape()));
		}
	}

	@Override
	public void doSendToFront() {
		if(model.getSelectedShape() != null) {
			model.moveToFront(model.getShapes().indexOf(model.getSelectedShape()));
		}
	}

	@Override
	public void doSendtoBack() {
		if(model.getSelectedShape() != null) {
			model.movetoBack(model.getShapes().indexOf(model.getSelectedShape()));
		}
	}
	
	public void drawTriangle(MouseEvent e) {
		trianglePoints.add(new Point2D.Double(e.getPoint().getX(), e.getPoint().getY()));
		if(trianglePoints.size() >= 3) {
			Point2D.Double a = trianglePoints.get(0);
			Point2D.Double b = trianglePoints.get(1);
			Point2D.Double c = trianglePoints.get(2);
			
			Point2D.Double center = Triangle.findCenter(a, b, c);
			
			model.addShape(new Triangle(selectedColor
									 , center
									 , new Point2D.Double(a.getX(), a.getY())
									 , new Point2D.Double(b.getX(), b.getY())
									 , new Point2D.Double(c.getX(), c.getY())));
			trianglePoints.clear();
		}
	}
	
	public void redrawSquare(MouseEvent e) {
		Square s = (Square)model.getShape(0);
		
		Point origin = initPoint;
		Point m = e.getPoint();
		
		double xDiff = m.getX() - origin.getX();
		double yDiff = m.getY() - origin.getY();
		
		boolean xIsPos = ((xDiff >= 0) ? true : false);
		boolean yIsPos = ((yDiff >= 0) ? true : false);
		
		double sizeX = 0, sizeY = 0, size = 0;
		
		if(xIsPos && !yIsPos) {
			sizeX = (double)(e.getX() - origin.getX());
			sizeY = (double)(origin.getY() - e.getY());
			size = ((sizeX > sizeY) ? sizeY : sizeX);
			s.setCenter(new Point2D.Double(origin.getX() + size/2, origin.getY() - size/2));
			//if(size <= origin.getY() - m.getY()) {
				//s.setUpperLeft(new Point2D.Double(origin.getX(), origin.getY() - size));
			//}
		} else if(!xIsPos && !yIsPos) {
			sizeX = (double)(origin.getX() - e.getX());
			sizeY = (double)(origin.getY() - e.getY());
			size = ((sizeX > sizeY) ? sizeY : sizeX);
			s.setCenter(new Point2D.Double(origin.getX() - size/2, origin.getY() - size/2));
			//if(size <= origin.getX() - m.getX() && size <= origin.getY() - m.getY()) {
				//s.setUpperLeft(new Point2D.Double(origin.getX() - size, origin.getY() - size));
			//}
		} else if(!xIsPos && yIsPos) {
			sizeX = (double)(origin.getX() - m.getX());
			sizeY = (double)(m.getY() - origin.getY());
			size = ((sizeX > sizeY) ? sizeY : sizeX);
			s.setCenter(new Point2D.Double(origin.getX() - size/2, origin.getY() + size/2));
			//if(size <= origin.getX() - m.getX()) {
				//s.setUpperLeft(new Point2D.Double(origin.getX() - size, origin.getY()));
			//}
		} else {
			sizeX = (double)(e.getX() - origin.getX());
			sizeY = (double)(e.getY() - origin.getY());
			size = ((sizeX > sizeY) ? sizeY : sizeX);
			s.setCenter(new Point2D.Double(origin.getX() + size/2, origin.getY() + size/2));
		}

		s.setSize(size);
	}
	
	public void redrawRectangle(MouseEvent e) {
		Rectangle r = (Rectangle)model.getShape(0);
		
		Point origin = initPoint;
		Point m = e.getPoint();
		
		double width = 0;
		double height = 0;
		
		double xDiff = m.getX() - origin.getX();
		double yDiff = m.getY() - origin.getY();
		
		boolean xIsPos = ((xDiff >= 0) ? true : false);
		boolean yIsPos = ((yDiff >= 0) ? true : false);
		
		if(xIsPos && !yIsPos) {
			//r.setUpperLeft(new Point2D.Double(origin.getX(), m.getY()));
			width =		(double)(e.getX() - origin.getX());
			height =	(double)(origin.getY() - e.getY());
			r.setCenter(new Point2D.Double(initPoint.getX() + width/2, initPoint.getY() - height/2));
		} else if(!xIsPos && !yIsPos) {
			//r.setUpperLeft(new Point2D.Double(m.getX(), m.getY()));
			width =		(double)(origin.getX() - e.getX());
			height =	(double)(origin.getY() - e.getY());
			r.setCenter(new Point2D.Double(initPoint.getX() - width/2, initPoint.getY() - height/2));
		} else if(!xIsPos && yIsPos) {
			//r.setUpperLeft(new Point2D.Double(m.getX(), origin.getY()));
			width =		(double)(origin.getX() - e.getX());
			height =	(double)(e.getY() - origin.getY());
			r.setCenter(new Point2D.Double(initPoint.getX() - width/2, initPoint.getY() + height/2));
		} else {
			//r.setUpperLeft(new Point2D.Double(origin.getX(), origin.getY()));
			width = 	m.getX() - origin.getX();
			height = 	m.getY() - origin.getY();
			r.setCenter(new Point2D.Double(initPoint.getX() + width/2, initPoint.getY() + height/2));
		}
		
		r.setWidth(Math.abs(width));
		r.setHeight(Math.abs(height));
	}
	
	public void redrawCircle(MouseEvent e) {
		Circle c = (Circle)model.getShape(0);
		
		Point origin = initPoint;
		Point m = e.getPoint();
		
		double xDiff = m.getX() - origin.getX();
		double yDiff = m.getY() - origin.getY();
		
		boolean xIsPos = ((xDiff >= 0) ? true : false);
		boolean yIsPos = ((yDiff >= 0) ? true : false);
		
		double radiusX = 0, radiusY = 0, radius = 0;
		
		if(xIsPos && !yIsPos) {
			radiusX = (double)(e.getX() - origin.getX());
			radiusY = (double)(origin.getY() - e.getY());
			radius = ((radiusX > radiusY) ? radiusY : radiusX);
			c.setCenter(new Point2D.Double(origin.getX() + radius
										 , origin.getY()));
		} else if(!xIsPos && !yIsPos) {
			radiusX = (double)(origin.getX() - e.getX());
			radiusY = (double)(origin.getY() - e.getY());
			radius = ((radiusX > radiusY) ? radiusY : radiusX);
			c.setCenter(new Point2D.Double(origin.getX()
						 				 , origin.getY()));
		} else if(!xIsPos && yIsPos) {
			radiusX = (double)(origin.getX() - m.getX());
			radiusY = (double)(m.getY() - origin.getY());
			radius = ((radiusX > radiusY) ? radiusY : radiusX);
			c.setCenter(new Point2D.Double((origin.getX())
						 				 , (origin.getY()) + radius));
		} else {
			radiusX = (double)(m.getX() - origin.getX());
			radiusY = (double)(m.getY() - origin.getY());
			radius = ((radiusX > radiusY) ? radiusY : radiusX);
			c.setCenter(new Point2D.Double(origin.getX() + radius
										 , origin.getY() + radius));
		}

		c.setRadius(radius);
		
		System.out.println("origin: " + origin.getX() + ", " + origin.getY());
		System.out.println("center: " + c.getCenter().getX() + ", " + c.getCenter().getY());
		System.out.println("mouse: " + e.getX() + ", " + e.getY());
	}
	
	public void redrawEllipse(MouseEvent e) {
		Ellipse el = (Ellipse)model.getShape(0);
		
		Point origin = initPoint;
		Point m = e.getPoint();
		
		double width = 0;
		double height = 0;
		
		double xDiff = m.getX() - origin.getX();
		double yDiff = m.getY() - origin.getY();
		
		boolean xIsPos = ((xDiff >= 0) ? true : false);
		boolean yIsPos = ((yDiff >= 0) ? true : false);
		
		if(xIsPos && !yIsPos) {
			width =		(double)(e.getX() - origin.getX());
			height =	(double)(origin.getY() - e.getY());
			el.setCenter(new Point2D.Double(origin.getX() + (width / 2)
					  					  , origin.getY() - (height / 2)));
		} else if(!xIsPos && !yIsPos) {
			width =		(double)(origin.getX() - e.getX());
			height =	(double)(origin.getY() - e.getY());
			el.setCenter(new Point2D.Double(origin.getX() - (width / 2)
					  					  , origin.getY() - (height / 2)));
		} else if(!xIsPos && yIsPos) {
			width =		(double)(origin.getX() - e.getX());
			height =	(double)(e.getY() - origin.getY());
			el.setCenter(new Point2D.Double(origin.getX() - (width / 2)
					  					  , origin.getY() + (height / 2)));
		} else {
			width = 	m.getX() - origin.getX();
			height = 	m.getY() - origin.getY();
			el.setCenter(new Point2D.Double(origin.getX() + (width / 2)
										  , origin.getY() + (height / 2)));
		}
		
		el.setWidth(Math.abs(width));
		el.setHeight(Math.abs(height));
	}
	
	public boolean clickTest(Point2D.Double wClick, double lineTolerance) {
		for(int i=0; i<model.getShapes().size(); i++) {
			Shape s = model.getShapes().get(i);
			if(s.pointInShape(wClick, lineTolerance)) {
				model.setSelectedShape(s);
				GUIFunctions.changeSelectedColor(s.getColor());
				if(s instanceof Line) {
					dragOriginStart = new Point2D.Double(((Line) s).getStart().getX()
														, ((Line) s).getStart().getY());
					dragOriginEnd = new Point2D.Double(((Line) s).getEnd().getX()
														, ((Line) s).getEnd().getY());
				}
				
				dragOriginCenter = new Point2D.Double(s.getCenter().getX()
													, s.getCenter().getY());
				return true;
			}
		}
		
		return false;
	}
	
	public void dragShape(MouseEvent e, Shape s) {
		Point2D.Double c = s.getCenter();
		
		double xDist = dragOriginMouse.getX() - dragOriginCenter.getX();
		double yDist = dragOriginMouse.getY() - dragOriginCenter.getY();
		
		if(s instanceof Line) {
			double xDistStart = dragOriginMouse.getX() - dragOriginStart.getX();
			double yDistStart = dragOriginMouse.getY() - dragOriginStart.getY();
			
			double xDistEnd = dragOriginMouse.getX() - dragOriginEnd.getX();
			double yDistEnd = dragOriginMouse.getY() - dragOriginEnd.getY();
			
			((Line) s).setStart(new Point2D.Double(e.getX() - xDistStart
											   	 , e.getY() - yDistStart));
			((Line) s).setEnd(new Point2D.Double(e.getX() - xDistEnd
				   	 						   , e.getY() - yDistEnd));
		} else {
			s.setCenter(new Point2D.Double(e.getX() - xDist
					 					 , e.getY() - yDist));
		}
	}
	
	public void rotateShape(MouseEvent e, Shape s) {
		Point2D.Double c = s.getCenter();
		
		s.setRotation(.5);					// TODO -- just for testing...
	}

}
