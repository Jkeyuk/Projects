package controllers.systemMap.guiTools;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.GestureEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;

public class GUIzoom {

	private static Point2D startPoint;

	/**
	 * Add zooming related listeners
	 * 
	 * @param region
	 */
	public static void addListeners(Region region, double minS, double maxS) {
		region.layoutBoundsProperty().addListener(e -> {
			GUIzoom.clipRegion(region);
		});
		region.setOnScroll(e -> {
			zoomFeature(region, e, minS, maxS);
			e.consume();
		});
		region.setOnZoom(e -> {
			zoomFeature(region, e, minS, maxS);
			e.consume();
		});
		region.setOnMousePressed(e -> {
			startPoint = new Point2D(e.getSceneX(), e.getSceneY());
			e.consume();
		});
		region.setOnMouseDragged(e -> {
			moveChildren(
				(Pane) region,
				startPoint.subtract(new Point2D(e.getSceneX(), e.getSceneY())).multiply(
					1 / region.getScaleX()));
			startPoint = new Point2D(e.getSceneX(), e.getSceneY());
			e.consume();
		});
	}

	/**
	 * zoom region based on gesture amount.
	 * 
	 * @param node
	 * @param e
	 * @param minS
	 */
	private static void zoomFeature(Region node, GestureEvent e, double minS, double maxS) {
		double scale = (e instanceof ZoomEvent) ? ((ZoomEvent) e).getZoomFactor()
				: ((ScrollEvent) e).getDeltaY();
		scaleNode(node, Math.pow(1.01, scale), minS, maxS);
		clipRegion(node);
		if (scale > 0) {
			Bounds layBound = node.getLayoutBounds();
			moveChildren(
				(Pane) node,
				node.sceneToLocal(e.getSceneX(), e.getSceneY()).subtract(
					new Point2D(layBound.getWidth(), layBound.getHeight()).multiply(1 / 2.0)));
		}
	}

	/**
	 * Scale a given Node by a give Factor, will not shrink past a given minS.
	 * 
	 * @param node
	 * @param factor
	 * @param minS
	 */
	private static void scaleNode(Node node, double factor, double minS, double maxS) {
		double oldScale = node.getScaleX();
		double scale = oldScale * factor;
		if (scale < minS)
			scale = minS;
		if (scale > maxS)
			scale = maxS;
		node.setScaleX(scale);
		node.setScaleY(scale);
	}

	/**
	 * Clip a given region based on its viewport and center clipView on regions
	 * center.
	 * 
	 * @param region
	 */
	private static void clipRegion(Region region) {
		Bounds rBounds = region.getLayoutBounds();
		Point2D center = new Point2D(rBounds.getMaxX(), rBounds.getMaxY()).multiply(1 / 2.0);
		Point2D finalPos = center.subtract(center.multiply(1 / region.getScaleX()));
		region.setClip(
			new Rectangle(
				finalPos.getX(),
				finalPos.getY(),
				rBounds.getWidth(),
				rBounds.getHeight()));
	}

	/**
	 * Moves chicldren of a given pane by a given vector.
	 * 
	 * @param pane
	 * @param vector
	 */
	private static void moveChildren(Pane pane, Point2D vector) {
		pane.getChildren().forEach(child -> {
			child.setTranslateX(child.getTranslateX() - vector.getX());
			child.setTranslateY(child.getTranslateY() - vector.getY());
		});
	}
}