package cs355.controller.handler;

import java.awt.geom.Point2D;

public interface Handler {
	public void start(Point2D.Double start);
	public void drag(Point2D end);
	public void end();
}