package cs355.model.drawing;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import cs355.controller.Transformations;

/**
 * Add your circle code here. You can add fields, but you cannot
 * change the ones that already exist. This includes the names!
 */
public class Circle extends Shape {

	// The radius.
	private double radius;
	private Point2D objCoord;

	/**
	 * Basic constructor that sets all fields.
	 * @param color the color for the new shape.
	 * @param center the center of the new shape.
	 * @param radius the radius of the new shape.
	 */
	public Circle(Color color, Point2D.Double center, double radius) {

		// Initialize the superclass.
		super(color, center);

		// Set the field.
		this.radius = radius;
	}

	/**
	 * Getter for this Circle's radius.
	 * @return the radius of this Circle as a double.
	 */
	public double getRadius() {
		return radius;
	}

	/**
	 * Setter for this Circle's radius.
	 * @param radius the new radius of this Circle.
	 */
	public void setRadius(double radius) {
		this.radius = radius;
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
		
		System.out.println("distance: ");
		System.out.println(objCoord.toString());
		System.out.println(center.toString());
		
		// This is a super ugly fix...but here we go.
		if(Math.abs(objCoord.distance(new Point2D.Double(-radius/2, -radius/2))) <= radius/2) {
			return true;
		}
		
		return false;
	}

}
