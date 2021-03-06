package cs355.model.drawing;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

/**
 * Add your square code here. You can add fields, but you cannot
 * change the ones that already exist. This includes the names!
 */
public class Square extends Shape {

	// The size of this Square.
	private double size;

	/**
	 * Basic constructor that sets all fields.
	 * @param color the color for the new shape.
	 * @param center the center of the new shape.
	 * @param size the size of the new shape.
	 */
	public Square(Color color, Point2D.Double center, double size) {

		// Initialize the superclass.
		super(color, center);

		// Set the field.
		this.size = size;
	}

	/**
	 * Getter for this Square's size.
	 * @return the size as a double.
	 */
	public double getSize() {
		return size;
	}

	/**
	 * Setter for this Square's size.
	 * @param size the new size.
	 */
	public void setSize(double size) {
		this.size = size;
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
        double x = pt.getX();
        double y = pt.getY();
        
        boolean hit = (x >= (-size / 2)) &&
                	  (x <= (size / 2)) &&
                	  (y >= (-size / 2)) &&
                	  (y <= (size / 2));
        
        return hit;
	}

	@Override
	public boolean handleHit(Point2D pt) {
		boolean hit = false;
    	
        AffineTransform worldToObj = fromWorldTransform();
        Point2D.Double objCoord = new Point2D.Double();
        worldToObj.transform(pt, objCoord);
		
		double x = objCoord.getX();
		double y = objCoord.getY();
		
		hit = (x >= -5) &&
          	  (x <= 5) &&
          	  (y >= ((-size / 2) - 15)) &&
          	  (y <= ((-size / 2)) - 5);
		
		return hit;
	}

}
