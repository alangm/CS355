package cs355.model.scene;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Brennan Smith
 */
public class WireFrame implements Iterable<Line3D>
{

	public WireFrame(ArrayList<Line3D> lines) {
		// TODO Auto-generated constructor stub
	}

	public WireFrame() {
		// TODO Auto-generated constructor stub
	}

	public Iterator<Line3D> getLines()
    {
        return new ArrayList<Line3D>().iterator();
    }

    @Override
    public Iterator iterator() {
        return getLines();
    }
}
