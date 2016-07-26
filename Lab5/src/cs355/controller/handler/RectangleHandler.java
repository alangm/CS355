package cs355.controller.handler;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

import cs355.controller.Controller;
import cs355.model.drawing.Rectangle;

public class RectangleHandler implements Handler {
	
	private Controller controller;
	private Rectangle r;
	private Point2D.Double start;
	
	public RectangleHandler(Controller controller) {
		this.controller = controller;
		this.r = new Rectangle(controller.getSelectedColor(),
							   new Point2D.Double(0, 0),
							   0,
							   0);
	}

	@Override
	public void start(Point2D.Double start) {
		r.setColor(controller.getSelectedColor());
		this.start = start;
		
		this.r = new Rectangle(controller.getSelectedColor(),
							   start,
							   0,
							   0);
		
		controller.getModel().addShape(r);
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

        r.setCenter(new Point2D.Double(x, y));
        r.setWidth(width);
        r.setHeight(height);
	}

	@Override
	public void end() {}

}
