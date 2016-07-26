package cs355.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import cs355.GUIFunctions;
import cs355.model.drawing.*;
import cs355.util.Transform;

public class Draw {
    
    private Graphics2D g2d;
    private View view;

    private AffineTransform objToWorld;
    
    private Color selectionColor;
    
    public Draw(View view) {
    	this.view = view;
    	
    	this.selectionColor = Color.yellow;
    }
	
	public void draw(Shape shape, boolean selected) {
        objToWorld = shape.toWorldTransform();

        Point2D center = new Point2D.Double(0, 0);

        AffineTransform t = new Transform();
        t.translate(shape.getCenter().getX(), shape.getCenter().getY());		// This is where the magic happens. :)
        t.rotate(shape.getRotation());

        g2d.setTransform(t);	// Set the magic into the graphics object

        if (shape instanceof Line) {
            draw((Line)shape, selected);
        } else if (shape instanceof Square) {
            draw((Square)shape, selected);
        } else if (shape instanceof Rectangle) {
            draw((Rectangle)shape, selected);
        } else if (shape instanceof Circle) {
            draw((Circle)shape, selected);
        } else if (shape instanceof Ellipse) {
            draw((Ellipse)shape, selected);
        } else if (shape instanceof Triangle) {
            draw((Triangle)shape, selected);
        }

        g2d.setTransform(new Transform());
	}

    public void draw(Line line, boolean selected) {
    	Point2D a = line.getCenter();
    	Point2D b = line.getEnd();
    	
    	Point2D x = new Point2D.Double(0, 0);
    	Point2D y = new Point2D.Double(b.getX()-a.getX(), b.getY()-a.getY());

        if (selected) {
            g2d.setColor(selectionColor);
            g2d.drawOval((int)x.getX() - 5,
            		     (int)x.getY() - 5, 10, 10);
            g2d.drawOval((int)y.getX() - 5,
            		     (int)y.getY() - 5, 10, 10);
        } else {
            g2d.setColor(line.getColor());
            g2d.drawLine((int)x.getX(), (int)x.getY(), (int)y.getX(), (int)y.getY());
        }
    }

	public void draw(Square s, boolean selected) {
	    int size = (int)s.getSize();
	    int x = (-size/2);
	    int y = (-size/2);
        
        if (selected) {
        	g2d.setColor(selectionColor);
        	g2d.drawRect(x, y, size, size);
        	
        	// Rotation handle
        	Point2D handlePoint = new Point2D.Double(0, y-10);
        	g2d.drawOval((int) handlePoint.getX()-5, (int) handlePoint.getY()-5, 10, 10);
        } else {
    	    g2d.setColor(s.getColor());
            g2d.fillRect(x, y, size, size);
        }
        
    }

	public void draw(Rectangle r, boolean selected) {
		int width = (int)r.getWidth();
		int height = (int)r.getHeight();
		int x = (-width/2);
		int y = (-height/2);

        if (selected) {
            g2d.setColor(selectionColor);
            g2d.drawRect(x, y, width, height);
        	
        	// Rotation handle
        	Point2D handlePoint = new Point2D.Double(0, y-10);
        	g2d.drawOval((int) handlePoint.getX()-5, (int) handlePoint.getY()-5, 10, 10);
        } else {
    		g2d.setColor(r.getColor());
    		g2d.fillRect(x, y, width, height);
        }
	}

	public void draw(Circle c, boolean selected) {
	    int r = (int)c.getRadius();
	    int x = -r;
	    int y = -r;
	    int size = r*2;
        
        if (selected) {
        	g2d.setColor(selectionColor);
        	g2d.drawRect(x, y, size, size);
        	
        	// Rotation handle
        	Point2D handlePoint = new Point2D.Double(0, y-10);
        	g2d.drawOval((int) handlePoint.getX()-5, (int) handlePoint.getY()-5, 10, 10);
        } else {
    	    g2d.setColor(c.getColor());
    	    g2d.fillOval(x, y, size, size);
        }
	}

	public void draw(Ellipse e, boolean selected) {
		int width = (int)e.getWidth();
		int height = (int)e.getHeight();
		int x = (-width/2);
		int y = (-height/2);
        
        if (selected) {
        	g2d.setColor(selectionColor);
        	g2d.drawRect(x, y, width, height);
        	
        	// Rotation handle
        	Point2D handlePoint = new Point2D.Double(0, y-10);
        	g2d.drawOval((int) handlePoint.getX()-5, (int) handlePoint.getY()-5, 10, 10);
        } else {
    		g2d.setColor(e.getColor());
    	    g2d.fillOval(x, y, width, height);
        }
	}
	
	public void draw(Triangle triangle, boolean selected) {
	    Point2D c1 = triangle.getA();
	    Point2D c2 = triangle.getB();
	    Point2D c3 = triangle.getC();
	    Point2D center = triangle.getCenter();
	    
	    Point2D a = new Point2D.Double(c1.getX()-center.getX(), c1.getY()-center.getY());
	    Point2D b = new Point2D.Double(c2.getX()-center.getX(), c2.getY()-center.getY());
	    Point2D c = new Point2D.Double(c3.getX()-center.getX(), c3.getY()-center.getY());
	
		int[] x = {
		    (int)(a.getX()),
		    (int)(b.getX()),
		    (int)(c.getX())
		};
		
		int[] y = {
		    (int)(a.getY()),
		    (int)(b.getY()),
		    (int)(c.getY())
		};
		
		Polygon p = new Polygon(x, y, 3);
        
        if (selected) {
        	g2d.setColor(selectionColor);
        	g2d.drawPolygon(x, y, 3);
        	
        	// Rotation handle
        	Point2D highestY = (a.getY() < b.getY() ? a : b);
        	highestY = (highestY.getY() < c.getY() ? highestY : c);
        	double height = highestY.getY();
        	Point2D handlePoint = new Point2D.Double(0, height-10);
        	g2d.drawOval((int) handlePoint.getX()-5, (int) handlePoint.getY()-5, 10, 10);
        } else {
    	    g2d.setColor(triangle.getColor());
            g2d.fillPolygon(p);
        }
	}
	
	public void setGraphics(Graphics2D g2d) {
	    this.g2d = g2d;
	}
}






