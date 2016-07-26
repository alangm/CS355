package cs355.controller;

import cs355.GUIFunctions;
import cs355.model.drawing.Shape;
import cs355.solution.CS355;

import java.awt.geom.*;

import static cs355.GUIFunctions.*;
import static cs355.solution.CS355.*;
import static java.lang.Math.*;

public class Transformations {
	
	public static Point2D.Double transformPoint(AffineTransform t, Point2D p) {
		Point2D pObj = new Point2D.Double(p.getX(), p.getY());
		Point2D pWorld = new Point2D.Double();

		t.transform(pObj, pWorld);

		return new Point2D.Double(pWorld.getX(), pWorld.getY());
	}

	public static AffineTransform viewToObj(Shape s) {
		AffineTransform transform = new AffineTransform();
		transform.concatenate(worldToObj(s));
		transform.concatenate(viewToWorld());

		return transform;
	}

	public static AffineTransform objToView(Shape s){
		AffineTransform transform = new AffineTransform();
		transform.concatenate(worldToView());
		transform.concatenate(objToWorld(s));

		return transform;
	}

	public static AffineTransform worldToObj(Shape s) {
		Point2D p = s.getCenter();
		double x = p.getX();
		double y = p.getY();

		double r = s.getRotation();
		double cos = cos(r);
		double sin = sin(r);

		return new AffineTransform(cos, -sin, sin, cos, (-cos*x) - (sin*y), (sin*x) - (cos*y));
	}

	public static AffineTransform objToWorld(Shape s) {
		Point2D location = s.getCenter();
		double x = location.getX();
		double y = location.getY();

		double r = s.getRotation();
		double cos = cos(r);
		double sin = sin(r);

		return new AffineTransform(cos, sin, -sin, cos, x, y);
	}

	public static AffineTransform viewToWorld() {
		AffineTransform transform = new AffineTransform();

		// translate, then scale
		transform.concatenate(new AffineTransform(1.0, 0.0, 0.0, 1.0,
				                                 (double)horizontalViewPosition,
				                                 (double)verticalViewPosition));
		transform.concatenate(new AffineTransform(1 / zoomFactor, 0.0, 0.0, 1 / zoomFactor, 0.0, 0.0));

		return transform;
	}

	public static  AffineTransform worldToView() {
		AffineTransform transform = new AffineTransform();

		// scale, then translate
		transform.concatenate(new AffineTransform(zoomFactor, 0.0, 0.0, zoomFactor, 0.0,   0.0));
		transform.concatenate(new AffineTransform(1.0, 0.0, 0.0, 1.0,
				                                 (double) -horizontalViewPosition,
				                                 (double) -verticalViewPosition));

		return transform;
	}


	
	
	private static double zoomFactor = .25;

	public static double getZoomFactor() {
		return zoomFactor;
	}

	public static void setZoomFactor(double factor) {
		int width = (int) (512 / factor);		// 512 is width of the view
		GUIFunctions.setHScrollBarKnob(width);
		GUIFunctions.setVScrollBarKnob(width);

		int viewCenterWidth = 512 / 2;

		double scaledWidth = viewCenterWidth / zoomFactor;
		double center_x = horizontalViewPosition + scaledWidth;
		double center_y = verticalViewPosition + scaledWidth;

		horizontalViewPosition = max(min((int) (center_x - viewCenterWidth / factor), 2048 - width), 0);
		verticalViewPosition   = max(min((int) (center_y - viewCenterWidth / factor), 2048 - width), 0);

		zoomFactor = factor;

		GUIFunctions.setHScrollBarPosit(horizontalViewPosition);
		GUIFunctions.setVScrollBarPosit(verticalViewPosition);
	}


	
	private static int horizontalViewPosition = 0;
	private static int verticalViewPosition = 0;

	public static void setHorizontalViewPosition(int horizontalViewPosition) {
		Transformations.horizontalViewPosition = horizontalViewPosition;
	}

	public static void setVerticalViewPosition(int verticalViewPosition) {
		Transformations.verticalViewPosition = verticalViewPosition;
	}

}
