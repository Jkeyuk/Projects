/*Zoom into a Node wrapped in a pane, wrapped in a group, wrapped in a scrollpane*/

import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.GestureEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.ZoomEvent;

public class GUItools {

	public static void zoomFeature(ScrollPane scroll, Node node, GestureEvent e, double minS) {
		if (e instanceof ZoomEvent) {
			ZoomEvent z = (ZoomEvent) e;
			zoom(node, Math.pow(1.01, z.getZoomFactor()), minS);
			adjustScrollBar(scroll, e.getX(), e.getY(), node);
		} else if (e instanceof ScrollEvent) {
			ScrollEvent s = (ScrollEvent) e;
			zoom(node, Math.pow(1.01, s.getDeltaY()), minS);
			adjustScrollBar(scroll, e.getX(), e.getY(), node);
		}
	}

	private static void zoom(Node node, double factor, double minS) {
		double oldScale = node.getScaleX();
		double scale = oldScale * factor;
		if (scale < minS)
			scale = minS;
		if (scale > 50)
			scale = 50;
		node.setScaleX(scale);
		node.setScaleY(scale);
	}

	private static void adjustScrollBar(ScrollPane p, double x, double y, Node n) {
		double hrel = p.getHmax() - p.getHmin();
		double vrel = p.getVmax() - p.getVmin();
		Bounds layoutSize = n.getBoundsInLocal();
		double finalH = hrel * (x / layoutSize.getWidth());
		double finalV = vrel * (y / layoutSize.getHeight());
		p.setHvalue(finalH);
		p.setVvalue(finalV);
	}
}
