package cs355.model.drawing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

import cs355.GUIFunctions;
import cs355.Vector;

/**
 * Add your triangle code here. You can add fields, but you cannot
 * change the ones that already exist. This includes the names!
 */
public class Triangle extends Shape {

	// The three points of the triangle.
	private Point2D.Double a;
	private Point2D.Double b;
	private Point2D.Double c;

	/**
	 * Basic constructor that sets all fields.
	 * @param color the color for the new shape.
	 * @param center the center of the new shape.
	 * @param a the first point, relative to the center.
	 * @param b the second point, relative to the center.
	 * @param c the third point, relative to the center.
	 */
	public Triangle(Color color, Point2D.Double center, Point2D.Double a,
					Point2D.Double b, Point2D.Double c)
	{

		// Initialize the superclass.
		super(color, center);

		// Set fields.
		this.a = a;
		this.b = b;
		this.c = c;
	}

	/**
	 * Getter for the first point.
	 * @return the first point as a Java point.
	 */
	public Point2D.Double getA() {
		return a;
	}

	/**
	 * Setter for the first point.
	 * @param a the new first point.
	 */
	public void setA(Point2D.Double a) {
		this.a = a;
	}

	/**
	 * Getter for the second point.
	 * @return the second point as a Java point.
	 */
	public Point2D.Double getB() {
		return b;
	}

	/**
	 * Setter for the second point.
	 * @param b the new second point.
	 */
	public void setB(Point2D.Double b) {
		this.b = b;
	}

	/**
	 * Getter for the third point.
	 * @return the third point as a Java point.
	 */
	public Point2D.Double getC() {
		return c;
	}

	/**
	 * Setter for the third point.
	 * @param c the new third point.
	 */
	public void setC(Point2D.Double c) {
		this.c = c;
	}

	/**
	 * Add your code to do an intersection test
	 * here. You shouldn't need the tolerance.
	 * @param pt = the point to test against.
	 * @param tolerance = the allowable tolerance.
	 * @return true if pt is in the shape,
	 *		   false otherwise.
	 */
	@Override
	public boolean pointInShape(Point2D.Double pt, double tolerance) {
        Vector v1 = new Vector(a);
        Vector v2 = new Vector(b);
        Vector v3 = new Vector(c);

        Vector _v1 = v1;
        v1 = v1.minus(v2);
        v2 = v2.minus(v3);
        v3 = v3.minus(_v1);

        boolean b1 = sign(pt, v1, v2) < 0;
        boolean b2 = sign(pt, v2, v3) < 0;
        boolean b3 = sign(pt, v3, v1) < 0;

        return ((b1 == b2) && (b2 == b3));
	}

    private static double sign(Point2D p1, Point2D p2, Point2D p3) {
        return (p1.getX() - p3.getX()) * (p2.getY() - p3.getY()) - (p2.getX() - p3.getX()) * (p1.getY() - p3.getY());
    }

	@Override
	public boolean handleHit(Point2D pt) {
		boolean hit = false;
    	
        AffineTransform worldToObj = fromWorldTransform();
        Point2D.Double objCoord = new Point2D.Double();
        worldToObj.transform(pt, objCoord);
        
    	Point2D highestY = (a.getY() < b.getY() ? a : b);
    	highestY = (highestY.getY() < c.getY() ? highestY : c);
    	double height = highestY.getY();
    	Point2D handlePoint = new Point2D.Double(0, height-10);
		
		double x = objCoord.getX();
		double y = objCoord.getY();
		
		hit = (x >= handlePoint.getX()) &&
          	  (x <= handlePoint.getX()+10) &&
          	  (y >= handlePoint.getY()) &&
          	  (y <= handlePoint.getY()+10);
		
		System.out.println(hit);
		
		return hit;
	}

}
