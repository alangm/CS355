package cs355.controller.handler;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

import cs355.GUIFunctions;
import cs355.controller.CS355Controller;
import cs355.controller.Controller;
import cs355.model.drawing.Line;
import cs355.model.drawing.Shape;

public class SelectionHandler implements Handler {

	Controller controller;
	Point2D start;
	boolean rotate;
	Shape selectedShape;
	
	public SelectionHandler(Controller controller) {
		this.controller = controller;
		start = new Point2D.Double(0, 0);
		rotate = false;
		selectedShape = null;
	}
	
	@Override
	public void start(Point2D.Double start) {
		this.start = start;
		selectedShape = controller.getModel().getSelectedShape();
		
		if (selectedShape != null) {
			if (selectedShape.handleHit(start)) {
				rotate = true;
				return;
			}
		}
		
		selectedShape = controller.getModel().checkShapeHit(start);
		if(selectedShape != null) {
			controller.getModel().setSelectedShape(selectedShape);
			GUIFunctions.changeSelectedColor(selectedShape.getColor());
		} else {
			controller.getModel().setSelectedShape(null);
		}
	}

	@Override
	public void drag(Point2D end) {
		Shape s = controller.getModel().getSelectedShape();
		
		if (s != null) {
			if(rotate) {
				if(s instanceof Line) {
					Line l = (Line)s;
					
					if(l.getCenterHit()) {
						l.setCenter(new Point2D.Double(end.getX(), end.getY()));
					} else if(l.getEndHit()) {
						l.setEnd(new Point2D.Double(end.getX(), end.getY()));
					}
				} else {
					Point2D center = s.getCenter();
					double dx = end.getX() - center.getX();
					double dy = end.getY() - center.getY();
					double angle = Math.atan2(dx, dy) + Math.PI;
					s.setRotation(-angle);
				}
			} else {
				Point2D.Double currCenter = controller.getModel().getSelectedShape().getCenter();
				double xDiff = end.getX() - start.getX();
				double yDiff = end.getY() - start.getY();
				currCenter.setLocation(currCenter.getX()+xDiff, currCenter.getY()+yDiff);
				s.setCenter(currCenter);
				
				if (s instanceof Line) {
					Point2D.Double currEnd = ((Line) s).getEnd();
					currEnd.setLocation(currEnd.getX()+xDiff, currEnd.getY()+yDiff);
					((Line) s).setEnd(currEnd);
				}
			}
			
			controller.getModel().update();
		}

		start = end;

	}

	@Override
	public void end() {
		rotate = false;
	}
	
}
