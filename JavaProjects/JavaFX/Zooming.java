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

public class Zooming {

	private static Point2D startPoint;

	/**
	 * Add zooming related listeners
	 * 
	 * @param region
	 */
	public static void addListeners(Region region) {
		region.layoutBoundsProperty().addListener(e -> {
			Zooming.clipRegion(region);
		});
		region.setOnScroll(e -> {
			zoomFeature(region, e, 1);
			e.consume();
		});
		region.setOnZoom(e -> {
			zoomFeature(region, e, 1);
			e.consume();
		});
		region.setOnMousePressed(e -> {
			startPoint = new Point2D(e.getSceneX(), e.getSceneY());
			e.consume();
		});
		region.setOnMouseDragged(e -> {
			moveChildren((Pane) region,
					startPoint.subtract(new Point2D(e.getSceneX(), e.getSceneY()))
							.multiply(1 / region.getScaleX()));
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
	private static void zoomFeature(Region node, GestureEvent e, double minS) {
		zoom(node, Math.pow(1.01, (e instanceof ZoomEvent) ? ((ZoomEvent) e).getZoomFactor()
				: ((ScrollEvent) e).getDeltaY()), minS);
		clipRegion(node);

		double scale = node.getScaleX();
		Bounds viewPort = node.getLayoutBounds();
		Point2D clickPoint = new Point2D(e.getSceneX(), e.getSceneY() - 30);
		Point2D viewCenter = new Point2D(viewPort.getWidth() / 2, viewPort.getHeight() / 2);
		moveChildren((Pane) node, clickPoint.subtract(viewCenter).multiply(1 / scale));
		System.out.println(scale);
		System.out.println(clickPoint);
		System.out.println(viewCenter);
		
	}

	/**
	 * Scale a given Node by a give Factor, will not shrink past a given minS.
	 * 
	 * @param node
	 * @param factor
	 * @param minS
	 */
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

	/**
	 * Clip a given region based on its viewport and center clipView on regions
	 * center.
	 * 
	 * @param region
	 */
	private static void clipRegion(Region region) {
		double scale = region.getScaleX();
		Point2D mapCenter = new Point2D(region.getWidth() / 2, region.getHeight() / 2);
		Point2D viewCenter = new Point2D((region.getLayoutBounds().getWidth() / 2) / scale,
				(region.getLayoutBounds().getHeight() / 2) / scale);
		final Rectangle outPutClip = new Rectangle();
		outPutClip.setWidth(region.getWidth());
		outPutClip.setHeight(region.getHeight());
		outPutClip.setLayoutX(mapCenter.getX() - viewCenter.getX());
		outPutClip.setLayoutY(mapCenter.getY() - viewCenter.getY());
		region.setClip(outPutClip);
	}

	private static void moveChildren(Pane pane, Point2D vector) {
		pane.getChildren().forEach(child -> {
			child.setTranslateX(child.getTranslateX() - vector.getX());
			child.setTranslateY(child.getTranslateY() - vector.getY());
		});
	}
}