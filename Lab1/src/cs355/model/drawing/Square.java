package cs355.model.drawing;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import cs355.controller.Transformations;

/**
 * Add your square code here. You can add fields, but you cannot
 * change the ones that already exist. This includes the names!
 */
public class Square extends Shape {

	// The upper left corner of this shape.
	private Point2D.Double upperLeft;
	private Point2D objCoord;

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
	 * Getter for this Rectangle's upper left corner.
	 * @return the upper left corner as a Java point.
	 */
	public Point2D.Double getUpperLeft() {
		return upperLeft;
	}

	/**
	 * Setter for this Rectangle's upper left corner.
	 * @param upperLeft the new upper left corner.
	 */
	public void setUpperLeft(Point2D.Double upperLeft) {
		this.upperLeft = upperLeft;
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
	 * Getter for this Square's center point.
	 * @return the center as a Point2D.Double.
	 */
	public Point2D.Double getCenter() {
		return center;
	}

	/**
	 * Setter for this Square's center point.
	 * @param center the new center.
	 */
	public void setCenter(Point2D.Double center) {
		this.center = center;
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
		AffineTransform viewToObj = Transformations.viewToObj(this);
		//worldToObj.rotate(rotation);
		//worldToObj.translate(-center.getX(),-center.getY());
		objCoord = viewToObj.transform(pt, objCoord);
		
		System.out.println("pt: " + pt.toString());
		System.out.println("objCoord: " + objCoord.toString());
		
		if(Math.abs(objCoord.getX()) <= size/2) {
			if(Math.abs(objCoord.getY()) <= size/2) {
				return true;
			}
		}
		
		// Check for handle hit
		
		return false;
	}

}
