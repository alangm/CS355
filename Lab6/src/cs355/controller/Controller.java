package cs355.controller;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.io.File;
import java.util.Iterator;

import cs355.GUIFunctions;
import cs355.controller.handler.*;
import cs355.model.drawing.*;
import cs355.model.image.*;
import cs355.util.Tool;
import cs355.view.View;

public class Controller implements CS355Controller {
	
	public static Controller singleton;
	
	private Model model;
	private View view;
	private Tool selectedTool;
	private Color selectedColor;
	//private Point initPoint;
	private Handler handler;
	//private Point2D dragOriginMouse;
	//private Point2D dragOriginCenter;
	//private Point2D dragOriginStart;
	//private Point2D dragOriginEnd;
	public boolean displayImage = false;
	public Image image;
	
	public static Controller Singleton(Model m, View v) {
		if(singleton == null) {
			singleton = new Controller(m, v);
		}
		return singleton;
	}

	public Controller(Model m, View view) {
		model = m;
		selectedTool = Tool.COLOR;
		selectedColor = Color.white;
		//initPoint = new Point();
		this.view = view;
		
		singleton = this;
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
        Point2D.Double p = new Point2D.Double(e.getX(), e.getY());
        
        if(handler != null) {
        	handler.start(p);
        	model.update();
        }
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(handler != null) {
			handler.end();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if(handler != null) {
			handler.drag(e.getPoint());
		}
		model.update();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void colorButtonHit(Color c) {
		selectedColor = c;
		GUIFunctions.changeSelectedColor(c);
		
		if(model.getSelectedShape() != null) {
			model.getSelectedShape().setColor(c);
		}
		
		handler = null;
		model.update();
	}

	@Override
	public void lineButtonHit() {
		selectedTool = Tool.LINE;
		this.handler = new LineHandler(this);
		model.setSelectedShape(null);
	}

	@Override
	public void squareButtonHit() {
		selectedTool = Tool.SQUARE;
		this.handler = new SquareHandler(this);
		model.setSelectedShape(null);
	}

	@Override
	public void rectangleButtonHit() {
		selectedTool = Tool.RECTANGLE;
		this.handler = new RectangleHandler(this);
		model.setSelectedShape(null);
	}

	@Override
	public void circleButtonHit() {
		selectedTool = Tool.CIRCLE;
		this.handler = new CircleHandler(this);
		model.setSelectedShape(null);
	}

	@Override
	public void ellipseButtonHit() {
		selectedTool = Tool.ELLIPSE;
		this.handler = new EllipseHandler(this);
		model.setSelectedShape(null);
	}

	@Override
	public void triangleButtonHit() {
		selectedTool = Tool.TRIANGLE;
		this.handler = new TriangleHandler(this);
		model.setSelectedShape(null);
	}

	@Override
	public void selectButtonHit() {
		selectedTool = Tool.SELECTION;
		this.handler = new SelectionHandler(this);
		model.setSelectedShape(null);
	}

    @Override
    public void zoomInButtonHit() {
        view.zoomIn();
		handler = null;
    }

    @Override
    public void zoomOutButtonHit() {
        view.zoomOut();
		handler = null;
    }

    @Override
    public void hScrollbarChanged(int value) {
        view.scrollHTo(value);
    }

    @Override
    public void vScrollbarChanged(int value) {
        view.scrollVTo(value);
    }

	@Override
	public void openScene(File file) {
		// TODO Auto-generated method stub

	}

    @Override
    public void toggle3DModelDisplay() {
        view.draw3DHouse = !view.draw3DHouse;
        if(view.draw3DHouse) {
        	selectedTool = Tool.HOME;
        } else {
        	selectedTool = null;
        }
        GUIFunctions.refresh();
    }

    @Override
    public void keyPressed(Iterator<Integer> iterator) {
    	if(selectedTool != Tool.HOME) {
    		return;
    	}
    	
        while (iterator.hasNext()) {
            char key = (char) ((int) iterator.next());

            if (key == 'W') {
                view.camera.z += 1 * Math.cos(Math.toRadians(view.camera.angle));
                view.camera.x += 1 * Math.sin(Math.toRadians(view.camera.angle));
            }

            if (key == 'S') {
                view.camera.z -= 1 * Math.cos(Math.toRadians(view.camera.angle));
                view.camera.x -= 1 * Math.sin(Math.toRadians(view.camera.angle));
            }

            if (key == 'A') {
                view.camera.z += 1 * Math.cos(Math.toRadians(view.camera.angle+90));
                view.camera.x += 1 * Math.sin(Math.toRadians(view.camera.angle+90));
            }

            if (key == 'D') {
                view.camera.z -= 1 * Math.cos(Math.toRadians(view.camera.angle+90));
                view.camera.x -= 1 * Math.sin(Math.toRadians(view.camera.angle+90));
            }

            if (key == 'Q') {
                view.camera.angle += 1;
                if (view.camera.angle > 360) {
                    view.camera.angle -= 360;
                }
            }

            if (key == 'E') {
                view.camera.angle -= 1;
                if (view.camera.angle < 0) {
                    view.camera.angle += 360;
                }
            }

            if (key == 'R') {
                view.camera.y += 1;
            }

            if (key == 'F') {
                view.camera.y -= 1;
            }
        }

        GUIFunctions.refresh();
    }

	@Override
	public void openImage(File file) {
		model.image = new Image();
		model.image.open(file);
		GUIFunctions.refresh();
	}

	@Override
	public void saveImage(File file) {
		if(model.image != null)
			model.image.save(file);
	}

	@Override
	public void toggleBackgroundDisplay() {
		displayImage = !displayImage;
		GUIFunctions.refresh();
	}

	@Override
	public void saveDrawing(File file) {
		model.save(file);
	}

	@Override
	public void openDrawing(File file) {
		model.open(file);
	}

	@Override
	public void doDeleteShape() {
		if(model.getSelectedShape() != null) {
			model.deleteShape(model.getShapes().indexOf(model.getSelectedShape()));
		}
		model.setSelectedShape(null);
	}

	@Override
	public void doEdgeDetection() {
		model.image.edgeDetection();
		GUIFunctions.refresh();
	}

	@Override
	public void doSharpen() {
		model.image.sharpen();
		GUIFunctions.refresh();
	}

	@Override
	public void doMedianBlur() {
		model.image.medianBlur();
		GUIFunctions.refresh();
	}

	@Override
	public void doUniformBlur() {
		model.image.uniformBlur();
		GUIFunctions.refresh();
	}

	@Override
	public void doGrayscale() {
		model.image.grayscale();
		GUIFunctions.refresh();
	}

	@Override
	public void doChangeContrast(int contrastAmountNum) {
        model.image.contrast(contrastAmountNum);
        GUIFunctions.refresh();
	}

	@Override
	public void doChangeBrightness(int brightnessAmountNum) {
        model.image.brightness(brightnessAmountNum);
        GUIFunctions.refresh();
	}

	@Override
	public void doMoveForward() {
		if(model.getSelectedShape() != null) {
			model.moveForward(model.getShapes().indexOf(model.getSelectedShape()));
		}
	}

	@Override
	public void doMoveBackward() {
		if(model.getSelectedShape() != null) {
			model.moveBackward(model.getShapes().indexOf(model.getSelectedShape()));
		}
	}

	@Override
	public void doSendToFront() {
		if(model.getSelectedShape() != null) {
			model.moveToFront(model.getShapes().indexOf(model.getSelectedShape()));
		}
	}

	@Override
	public void doSendtoBack() {
		if(model.getSelectedShape() != null) {
			model.movetoBack(model.getShapes().indexOf(model.getSelectedShape()));
		}
	}
	
	public Model getModel() {
		return model;
	}
	
	public Color getSelectedColor() {
		return selectedColor;
	}
	
	public void rotateShape(MouseEvent e, Shape s) {
		Point2D.Double c = s.getCenter();
		
		s.setRotation(.5);					// TODO -- just for testing...
	}
	
	public View getView() {
		return this.view;
	}

}
