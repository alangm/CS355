package cs355.solution;

import java.awt.Color;

import cs355.GUIFunctions;
import cs355.controller.*;
import cs355.model.drawing.Model;
import cs355.view.*;

/**
 * This is the main class. The program starts here.
 * Make you add code below to initialize your model,
 * view, and controller and give them to the app.
 */
public class CS355 {

	/**
	 * This is where it starts.
	 * @param args = the command line arguments
	 */
	public static void main(String[] args) {
		
		Model model = new Model();
		Controller controller = new Controller(model);
		Viewer viewer = new Viewer();
		
		model.addObserver(viewer);

		// Fill in the parameters below with your controller and view.
		GUIFunctions.createCS355Frame(controller, viewer);
		GUIFunctions.changeSelectedColor(Color.white);

		GUIFunctions.refresh();
	}
}
