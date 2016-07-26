package cs355.controller.handler;

import java.awt.Point;
import java.awt.geom.Point2D;

import cs355.controller.Controller;
import cs355.model.drawing.Circle;
import cs355.model.drawing.Square;

public class CircleHandler implements Handler {

	Controller controller;
	Circle c;
	Point2D start;
	
	public CircleHandler(Controller controller) {
		this.controller = controller;
		this.c = new Circle(controller.getSelectedColor(),
							new Point2D.Double(0, 0),
							0);
	}
	
	@Override
	public void start(Point2D.Double start) {
		c.setColor(controller.getSelectedColor());
		this.start = start;
		
		this.c = new Circle(controller.getSelectedColor(),
							start,
							0);
		
		controller.getModel().addShape(c);
	}

	@Override
	public void drag(Point2D end) {
        double x, y;
		
		double xDiff = end.getX() - start.getX();
		double yDiff = end.getY() - start.getY();
        double width = Math.abs(xDiff);
        double height = Math.abs(yDiff);
        
        double size = (width < height ? width : height);

        if (xDiff >= 0) {
            x = start.getX() + size/2;
        } else {
            x = start.getX() - size/2;
        }

        if (yDiff >= 0) {
            y = start.getY() + size/2;
        } else {
            y = start.getY() - size/2;
        }

        c.setCenter(new Point2D.Double(x, y));
        c.setRadius(size/2);
	}

	@Override
	public void end() {}

}
