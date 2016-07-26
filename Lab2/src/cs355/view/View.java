package cs355.view;

import java.awt.Graphics2D;
import java.util.List;
import java.util.Observable;

import cs355.GUIFunctions;
import cs355.model.drawing.*;

public class View implements ViewRefresher {
	
    private Draw draw;
	private Model model;
	
	public View() {
		draw = new Draw(this);
		model = new Model();
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
	}

}

