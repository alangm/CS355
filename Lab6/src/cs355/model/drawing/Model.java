package cs355.model.drawing;

import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import cs355.model.image.Image;

public class Model extends CS355Drawing {
	
	private List<Shape> shapes;
	private Shape selectedShape;
	private double scale;
	
	public Image image;

	public Model() {
		shapes = new ArrayList<Shape>();
		selectedShape = null;
		scale = 1;
	}

	@Override
	public Shape getShape(int index) {
		return shapes.get(index);
	}

	@Override
	public int addShape(Shape s) {
		shapes.add(0, s);
		setChanged();
		notifyObservers(this);
		
		return shapes.indexOf(s);	
	}

	@Override
	public void deleteShape(int index) {
		if(index >= 0) {
			shapes.remove(index);
		}
		
		setChanged();
		notifyObservers(this);
	}

	@Override
	public void moveToFront(int index) {
		if(index >= 0) {
			Shape s = shapes.get(index);
			shapes.remove(s);
			shapes.add(0, s);
		}
		
		setChanged();
		notifyObservers(this);
	}

	@Override
	public void movetoBack(int index) {
		if(index >= 0) {
			Shape s = shapes.get(index);
			shapes.remove(s);
			shapes.add(s);
		}
		
		setChanged();
		notifyObservers(this);
	}

	@Override
	public void moveForward(int index) {
		if(index > 0) {
			Shape s = shapes.get(index);
			shapes.remove(s);
			shapes.add(index-1, s);
		}
		
		setChanged();
		notifyObservers(this);
	}

	@Override
	public void moveBackward(int index) {
		if(index < shapes.size()-1) {
			Shape s = shapes.get(index);
			shapes.remove(s);
			shapes.add(index+1, s);
		}
		
		setChanged();
		notifyObservers(this);
	}

	@Override
	public List<Shape> getShapes() {
		return shapes;
	}

	@Override
	public List<Shape> getShapesReversed() {
		List<Shape> shapesTemp = shapes;
		
		for(int i=0, j=shapesTemp.size()-1; i<j; i++) {
			shapesTemp.add(i, shapesTemp.remove(j));
		}
		
		return shapesTemp;
	}

	@Override
	public void setShapes(List<Shape> shapes) {
		this.shapes = shapes;
		setChanged();
		notifyObservers(this);
	}
	
	public void setSelectedShape(Shape s) {
		this.selectedShape = s;
		update();
	}
	
	public Shape getSelectedShape() {
		return selectedShape;
	}
	
	public void setScale(double s) {
		scale = s;
	}
	
	public double getScale() {
		return scale;
	}
	
	public void scaleUp() {
		scale *= 1.25;
		update();
	}
	
	public void scaleDown() {
		scale /= 1.25;
		update();
	}
	
	public void update() {
		setChanged();
		notifyObservers(this);
	}

    public Shape checkShapeHit(Point2D p) {
        for (Shape shape : shapes) {
        	
            AffineTransform worldToObj = shape.fromWorldTransform();
            Point2D.Double objCoord = new Point2D.Double();
            worldToObj.transform(p, objCoord);

            if (shape.pointInShape(objCoord, 4)) {
                return shape;
            }
            
        }

        return null;
    }

}
