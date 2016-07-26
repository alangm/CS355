package cs355.model.drawing;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import cs355.controller.Transformations;

/**
 * Add your rectangle code here. You can add fields, but you cannot
 * change the ones that already exist. This includes the names!
 */
public class Rectangle extends Shape {

	// The upper left corner of this shape.
	private Point2D.Double upperLeft;

	// The width of this shape.
	private double width;

	// The height of this shape.
	private double height;
	private Point2D objCoord;

	/**
	 * Basic constructor that sets all fields.
	 * @param color the color for the new shape.
	 * @param center the center of the new shape.
	 * @param width the width of the new shape.
	 * @param height the height of the new shape.
	 */
	public Rectangle(Color color, Point2D.Double center, double width, double height) {

		// Initialize the superclass.
		super(color, center);

		// Set fields.
		this.width = width;
		this.height = height;
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
	 * Getter for this shape's width.
	 * @return this shape's width as a double.
	 */
	public double getWidth() {
		return width;
	}

	/**
	 * Setter for this shape's width.
	 * @param width the new width.
	 */
	public void setWidth(double width) {
		this.width = width;
	}

	/**
	 * Getter for this shape's height.
	 * @return this shape's height as a double.
	 */
	public double getHeight() {
		return height;
	}

	/**
	 * Setter for this shape's height.
	 * @param height the new height.
	 */
	public void setHeight(double height) {
		this.height = height;
	}
	
	public Point2D.Double getCenter() {
		return center;
	}
	
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
		
		if(Math.abs(objCoord.getX()) <= width/2) {
			if(Math.abs(objCoord.getY()) <= height/2) {
				return true;
			}
		}
		
		return false;
	}

}
