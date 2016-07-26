package cs355.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.Observable;

import cs355.GUIFunctions;
import cs355.controller.Transformations;
import cs355.model.drawing.*;

public class Viewer implements ViewRefresher {
	
	private Model model;

	public Viewer() {
		model = new Model();
	}

	@Override
	public void update(Observable o, Object arg) {
		try {
			if(arg instanceof Model) {
				model = (Model)arg;
			} else {
				//model = null;
				throw new Exception("Model was passed as null");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		GUIFunctions.refresh();
	}

	@Override
	public void refreshView(Graphics2D g2d) {
		List<Shape> shapes;
		
		if(model != null) {
			shapes = model.getShapes();
		
			for (int i=shapes.size()-1; i>=0; i--) {
				// check the type, draw the shape
				Shape s = shapes.get(i);

				//AffineTransform objToWorld = new AffineTransform();
				//AffineTransform worldToView = new AffineTransform();
				AffineTransform objToWorld = Transformations.objToWorld(s);
				AffineTransform worldToView = Transformations.worldToView();
				AffineTransform objToView = Transformations.objToView(s);
				
				//if(s instanceof Line) {
				//	objToWorld.translate(((Line) s).getStart().getX(), ((Line) s).getStart().getY());
				//} else {
				//	objToWorld.translate(s.getCenter().getX(), s.getCenter().getY());
				//}
				//objToWorld.rotate(s.getRotation());
				
				//objToWorld.concatenate(worldToView);
				g2d.setTransform(objToView);
				
				if (s instanceof Triangle) {
					Triangle t = (Triangle)s;
					Polygon p = new Polygon(new int[] {(int)t.getA().getX(), (int)t.getB().getX(), (int)t.getC().getX()}
										  , new int[] {(int)t.getA().getY(), (int)t.getB().getY(), (int)t.getC().getY()}
										  , 3);
					
					g2d.setColor(t.getColor());
					g2d.fillPolygon(p);
					
					if(t == model.getSelectedShape()) {
						g2d.setColor(Color.red);
						g2d.drawPolygon(p);
					}
				} else if (s instanceof Line) {
					Line l = (Line)s;
					g2d.setColor(l.getColor());
					g2d.drawLine(0
							   , 0
							   , (int)l.getXDist()
							   , (int)l.getYDist());
					if(l == model.getSelectedShape()) {
						g2d.setColor(Color.red);
						g2d.drawOval(-4, -4, 8, 8);
						g2d.drawOval((int)l.getXDist() - 4
								   , (int)l.getYDist() - 4
								   , 8
								   , 8);
					}
				} else if (s instanceof Square) {
					Square sq = (Square)s;
					
					g2d.setColor(sq.getColor());
					g2d.fillRect((int)-sq.getSize()/2
							   , (int)-sq.getSize()/2
							   , (int)sq.getSize()
							   , (int)sq.getSize());
					
					if(sq == model.getSelectedShape()) {
						g2d.setColor(Color.red);
						g2d.drawRect((int)-sq.getSize()/2
								   , (int)-sq.getSize()/2
								   , (int)sq.getSize()
								   , (int)sq.getSize());
						
						g2d.drawOval(-4
								   , (int)-sq.getSize()/2 - 12
								   , 8
								   , 8);
					}
				} else if (s instanceof Rectangle) {
					Rectangle r = (Rectangle)s;
					
					g2d.setColor(r.getColor());
					g2d.fillRect((int)-r.getWidth()/2
							   , (int)-r.getHeight()/2
							   , (int)r.getWidth()
							   , (int)r.getHeight());
					
					if(r == model.getSelectedShape()) {
						g2d.setColor(Color.red);
						g2d.drawRect((int)-r.getWidth()/2
								   , (int)-r.getHeight()/2
								   , (int)r.getWidth()
								   , (int)r.getHeight());
						
						g2d.drawOval(-4
								   , (int)-r.getHeight()/2 - 12
								   , 8
								   , 8);
					}
				} else if (s instanceof Circle) {
					Circle c = (Circle)s;
					
					g2d.setColor(c.getColor());
					g2d.fillOval((int)(-c.getRadius())
							   , (int)(-c.getRadius())
							   , (int)(c.getRadius())
							   , (int)(c.getRadius()));

					if(c == model.getSelectedShape()) {
						g2d.setColor(Color.red);
						g2d.drawRect((int)(-c.getRadius())
								   , (int)(-c.getRadius())
								   , (int)(c.getRadius())
								   , (int)(c.getRadius()));
						
						g2d.drawOval((int)(-4 - c.getRadius()/2)
								   , (int)-c.getRadius() - 12
								   , 8
								   , 8);
					}
				} else if (s instanceof Ellipse) {
					Ellipse e = (Ellipse)s;
					
					g2d.setColor(e.getColor());
					g2d.fillOval((int)(-e.getWidth()/2)
							   , (int)(-e.getHeight()/2)
							   , (int)(e.getWidth())
							   , (int)(e.getHeight()));
					
					if(e == model.getSelectedShape()) {
						g2d.setColor(Color.red);
						g2d.drawRect((int)(-e.getWidth()/2)
								   , (int)(-e.getHeight()/2)
								   , (int)(e.getWidth())
								   , (int)(e.getHeight()));
						
						g2d.drawOval(-4
								   , (int)-e.getHeight()/2 - 12
								   , 8
								   , 8);
					}
				}
			}
		} else {
			try {
				throw new Exception("Model is null!");
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

}
