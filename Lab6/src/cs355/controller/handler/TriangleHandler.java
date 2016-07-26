package cs355.controller.handler;

import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;
import java.util.List;

import cs355.controller.Controller;
import cs355.model.drawing.Triangle;

public class TriangleHandler implements Handler {
	
	private Controller controller;
	Triangle t;
	Point2D.Double start;
	private List<Point2D.Double> trianglePoints;
	
	public TriangleHandler(Controller controller) {
		this.controller = controller;
		this.trianglePoints = new ArrayList<Point2D.Double>();
	}

	@Override
	public void start(Double start) {
		trianglePoints.add(new Point2D.Double(start.getX(), start.getY()));
		end();
	}

	@Override
	public void drag(Point2D end) {}

	@Override
	public void end() {
		if(trianglePoints.size() >= 3) {
			Point2D.Double a = trianglePoints.get(0);
			Point2D.Double b = trianglePoints.get(1);
			Point2D.Double c = trianglePoints.get(2);
			
			// set the center of the triangle
	        double x = (a.getX() + b.getX() + c.getX())/3;
	        double y = (a.getY() + b.getY() + c.getY())/3;
			Point2D.Double center = new Point2D.Double();
			center.setLocation(x, y);
			
			controller.getModel().addShape(new Triangle(controller.getSelectedColor(),
														center, a, b, c));
			
			trianglePoints.clear();
		}
	}

}
