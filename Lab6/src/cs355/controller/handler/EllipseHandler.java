package cs355.controller.handler;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

import cs355.controller.Controller;
import cs355.model.drawing.Circle;
import cs355.model.drawing.Ellipse;

public class EllipseHandler implements Handler {
	
	private Controller controller;
	private Ellipse e;
	private Point2D.Double start;
	
	public EllipseHandler(Controller controller) {
		this.controller = controller;
		this.e = new Ellipse(controller.getSelectedColor(),
							new Point2D.Double(0, 0),
							0,
							0);
	}

	@Override
	public void start(Point2D.Double start) {
		e.setColor(controller.getSelectedColor());
		this.start = start;
		
		this.e = new Ellipse(controller.getSelectedColor(),
							 start,
							 0,
							 0);
		
		controller.getModel().addShape(e);
	}

	@Override
	public void drag(Point2D end) {
        double x, y;
		
		double xDiff = end.getX() - start.getX();
		double yDiff = end.getY() - start.getY();
        double width = Math.abs(xDiff);
        double height = Math.abs(yDiff);
        
        x = (start.getX() + end.getX()) / 2;
        y = (start.getY() + end.getY()) / 2;

        e.setCenter(new Point2D.Double(x, y));
        e.setWidth(width);
        e.setHeight(height);
	}

	@Override
	public void end() {}

}
