package cs355.controller.handler;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

import cs355.controller.Controller;
import cs355.model.drawing.Line;

public class LineHandler implements Handler {
	
	private Controller controller;
	private Line l;
	private Point2D.Double start;
	
	public LineHandler(Controller controller) {
		this.controller = controller;
		this.l = new Line(controller.getSelectedColor(),
						  new Point2D.Double(0,  0),
						  new Point2D.Double(0,  0));
	}

	@Override
	public void start(Double start) {
		this.start = start;
		this.l = new Line(controller.getSelectedColor(),
						  start,
						  start);
		
		controller.getModel().addShape(l);
	}

	@Override
	public void drag(Point2D end) {
		l.setEnd(new Point2D.Double(end.getX(), end.getY()));
	}

	@Override
	public void end() {
		l.resetHits();
	}

}
