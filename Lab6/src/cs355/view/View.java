package cs355.view;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Observable;

import cs355.GUIFunctions;
import cs355.model.scene.HouseModel;
import cs355.model.scene.WireFrame;
import cs355.controller.Camera;
import cs355.controller.Controller;
import cs355.model.drawing.*;
import cs355.model.image.Image;
import cs355.model.scene.Point3D;
import cs355.util.Transform;

public class View implements ViewRefresher {

	Controller c = Controller.singleton;
	
    private Draw draw;
	private Model model;
	private double zoom;
	private double canvasWidth;
	private double canvasHeight;
	private double scrollX;
	private double scrollY;

    public AffineTransform worldToView = new Transform();
    public AffineTransform viewToWorld = new Transform();
    public boolean drawImage = true;

    public boolean draw3DHouse = false;
    public Camera camera = new Camera(0, 5, -20);
    
    private WireFrame house = new HouseModel();
	
	public View() {
		draw = new Draw(this);
		model = new Model();
		zoom = .25;
		canvasWidth = 2048;
		canvasHeight = 2048;
		scrollX = 0;
		scrollY = 0;
		
		updateWorldToView();
	}

	@Override
	public void update(Observable o, Object arg) {
		try {
			if(arg instanceof Model) {
				model = (Model)arg;
			} else {
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
		draw.setGraphics(g2d);
		
		c = Controller.singleton;
		
		if(c != null && c.displayImage) {
			this.renderImage(g2d);
		}
		
		if(model != null) {
			shapes = model.getShapes();
			
			// Draw shapes in the correct order...
			for(int i=shapes.size()-1; i>=0; i--) {
				Shape s = shapes.get(i);
				
				draw.draw(s, false);	// tell it to draw the outline if it's selected
			}
			
			if(model.getSelectedShape() != null) {
				draw.draw(model.getSelectedShape(), true);
			}
		}

        if (draw3DHouse) {
            draw.drawWireFrame(house);
        }
	}
	
	private void renderImage(Graphics2D g2d) {
        if (model.image != null && drawImage) {
            g2d.setTransform(worldToView);
            BufferedImage bi = model.image.getImage();
            g2d.drawImage(bi, null, (2048-bi.getWidth())/2, (2048-bi.getHeight())/2);
        }
	}

    public void zoomIn() {
        if (zoom >= 4) {
        	return;
        }

        Point2D center = new Point2D.Double(canvasWidth/2, canvasHeight/2);
        viewToWorld.transform(center, center);

        zoom *= 2;
        updateWorldToView();
        updateScrollBars();

        GUIFunctions.setHScrollBarPosit((int)center.getX());
        GUIFunctions.setVScrollBarPosit((int)center.getY());
        GUIFunctions.refresh();
    }

    public void zoomOut() {
        if (zoom <= 0.25) {
        	return;
        }

        Point2D center = new Point2D.Double(canvasWidth/2, canvasHeight/2);
        viewToWorld.transform(center, center);

        zoom *= 0.5;
        updateScrollBars();
        updateWorldToView();

        GUIFunctions.setHScrollBarPosit((int) center.getX());
        GUIFunctions.setVScrollBarPosit((int) center.getY());
        GUIFunctions.refresh();
    }

    public void updateScrollBars() {
        GUIFunctions.setHScrollBarMax((int) canvasWidth);
        GUIFunctions.setHScrollBarKnob((int) (canvasWidth/zoom));
        GUIFunctions.setVScrollBarMax((int) canvasHeight);
        GUIFunctions.setVScrollBarKnob((int) (canvasHeight/zoom));
    }

    public void updateWorldToView() {
        worldToView.setToIdentity();
        worldToView.translate(-scrollX, -scrollY);
        worldToView.scale(zoom, zoom);
        viewToWorld.setToIdentity();
        viewToWorld.scale(1/zoom, 1/zoom);
        viewToWorld.translate(scrollX, scrollY);
    }

    public void scrollHTo(int value) {
        scrollX = value;
        updateWorldToView();
        GUIFunctions.refresh();
    }

    public void scrollVTo(int value) {
        scrollY = value;
        updateWorldToView();
        GUIFunctions.refresh();
    }

}

