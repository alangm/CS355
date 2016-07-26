package cs355.controller.handler;

import java.awt.Point;
import java.awt.geom.Point2D;

import cs355.controller.Controller;
import cs355.model.drawing.Square;

public class SquareHandler implements Handler {

	Controller controller;
	Square s;
	Point2D start;
	
	public SquareHandler(Controller controller) {
		this.controller = controller;
		this.s = new Square(controller.getSelectedColor(),
							new Point2D.Double(0, 0),
							0);
	}
	
	@Override
	public void start(Point2D.Double start) {
		s.setColor(controller.getSelectedColor());
		this.start = start;
		
		this.s = new Square(controller.getSelectedColor(),
							start,
							0);
		
		controller.getModel().addShape(s);
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

        s.setCenter(new Point2D.Double(x, y));
        s.setSize(size);
	}

	@Override
	public void end() {
		//controller.getModel().addShape(s);
		//s = new Square(controller.getSelectedColor(), s.getCenter(), s.getSize());
	}

}
