package cs355.model.drawing;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

import cs355.GUIFunctions;
import cs355.Vector;

/**
 * Add your line code here. You can add fields, but you cannot
 * change the ones that already exist. This includes the names!
 */
public class Line extends Shape {

	// The ending point of the line.
	private Point2D.Double end;
	private boolean centerHit;
	private boolean endHit;

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
		this.end = end;
		
		centerHit = false;
		endHit = false;
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
        Vector c = new Vector(new Point2D.Double(0, 0));
        Vector e = new Vector(new Point2D.Double(end.getX()-center.getX(), end.getY()-center.getY()));
        Vector p = new Vector(pt);

        Vector ce = e.minus(c);
        Vector cp = p.minus(c);

        double l = ce.magnitude();
        double l2 = l * l;

        double t = cp.dot(ce)/(l2);
        if (t < 0) return p.distanceTo(c) <= tolerance;
        if (t > 1) return p.distanceTo(e) <= tolerance;

        Vector proj = c.plus(ce.times(t));
        
        return p.distanceTo(proj) <= tolerance;
	}

	@Override
	public boolean handleHit(Point2D pt) {
		centerHit = false;
		endHit = false;
    	
        AffineTransform worldToObj = fromWorldTransform();
        Point2D.Double objCoord = new Point2D.Double();
        Point2D.Double objCoordEnd = new Point2D.Double();
        worldToObj.transform(pt, objCoord);
        worldToObj.transform(end, objCoordEnd);
		
		double x = objCoord.getX();
		double y = objCoord.getY();
		
		centerHit = (x >= -5) &&
	          	    (x <= 5) &&
	          	    (y >= -5) &&
	          	    (y <= 5);
		
		endHit = (x >= (objCoordEnd.x-5)) &&
          	     (x <= (objCoordEnd.x+5)) &&
          	     (y >= (objCoordEnd.y-5)) &&
          	     (y <= (objCoordEnd.y+5));
			
		return centerHit || endHit;
	}
	
	public boolean getCenterHit() {
		return centerHit;
	}
	
	public boolean getEndHit() {
		return endHit;
	}
	
	public void resetHits() {
		centerHit = false;
		endHit = false;
	}

}
