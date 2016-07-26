package cs355.model.drawing;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import cs355.controller.Transformations;

/**
 * Add your line code here. You can add fields, but you cannot
 * change the ones that already exist. This includes the names!
 */
public class Line extends Shape {

	// The starting point of the line.
	private Point2D.Double start;

	// The ending point of the line.
	private Point2D.Double end;
	private Point2D objCoord;
	private double xDist;
	private double yDist;

	/**
	 * Basic constructor that sets all fields.
	 * @param color the color for the new shape.
	 * @param start the starting point.
	 * @param end the ending point.
	 */
	public Line(Color color, Point2D.Double start, Point2D.Double end) {

		// Initialize the superclass.
		super(color, start);

		// Set the field.
		this.start = start;
		this.end = end;
		this.xDist = end.x - start.x;
		this.yDist = end.y - start.y;
	}

	/**
	 * Getter for this Line's starting point.
	 * @return the starting point as a Java point.
	 */
	public Point2D.Double getStart() {
		return start;
	}

	/**
	 * Setter for this Line's starting point.
	 * @param start the new starting point for the Line.
	 */
	public void setStart(Point2D.Double start) {
		this.start = start;
		this.xDist = end.x - start.x;
		this.yDist = end.y - start.y;
		this.end = new Point2D.Double(start.getX()+xDist, start.getY()+yDist);
	}

	/**
	 * Getter for this Line's ending point.
	 * @return the ending point as a Java point.
	 */
	public Point2D.Double getEnd() {
		return end;
	}

	/**
	 * Setter for this Line's ending point.
	 * @param end the new ending point for the Line.
	 */
	public void setEnd(Point2D.Double end) {
		this.end = end;
		this.xDist = end.x - start.x;
		this.yDist = end.y - start.y;
		this.start = new Point2D.Double(end.getX()-xDist, end.getY()-yDist);
	}
	
	public double getXDist() {
		return xDist;
	}
	
	public double getYDist() {
		return yDist;
	}

	/**
	 * Add your code to do an intersection test
	 * here. You <i>will</i> need the tolerance.
	 * @param pt = the point to test against.
	 * @param tolerance = the allowable tolerance.
	 * @return true if pt is in the shape,
	 *		   false otherwise.
	 */
	@Override
	public boolean pointInShape(Point2D.Double pt, double tolerance) {
		AffineTransform viewToObj = Transformations.viewToObj(this);
		//worldToObj.rotate(rotation);
		//worldToObj.translate(-start.getX(),-start.getY());
		objCoord = viewToObj.transform(pt, objCoord);
		
		double norm = Math.sqrt((end.x - start.x) * (end.x - start.x) + (end.y - start.y) * (end.y - start.y));
		double dist = Math.abs((pt.getX() - start.x) * (end.y - start.y) - (pt.getY() - start.y) * (end.x - start.x)) / norm;
		
		System.out.println("dist to line: " + dist);
		
		if (dist <= tolerance) {
			return true;
		}
		
		
		return false;
	}

}
