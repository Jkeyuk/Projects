package controllers.systemMap;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.GestureEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;

public class GUItools {

	public static class Panning {

		private static Point2D startPoint;

		/**
		 * Translate the children of a given pane by amount of mouse drag.
		 * 
		 * @param pane
		 */
		public static void addPanning(Pane pane) {
			pane.setOnMousePressed(e -> {
				startPoint = new Point2D(e.getSceneX(), e.getSceneY());
				e.consume();
			});
			pane.setOnMouseDragged(e -> {
				pane.getChildren().forEach(child -> {
					Point2D vector = new Point2D(e.getSceneX(), e.getSceneY()).subtract(startPoint)
							.multiply(1 / child.getParent().getScaleX());
					child.setTranslateX(child.getTranslateX() + vector.getX());
					child.setTranslateY(child.getTranslateY() + vector.getY());
				});
				startPoint = new Point2D(e.getSceneX(), e.getSceneY());
				e.consume();
			});
			;
		}
	}

	public static class Zooming {

		/**
		 * Add zooming to region based on gesutre amount.
		 * 
		 * @param node
		 * @param e
		 * @param minS
		 */
		public static void zoomFeature(Region node, GestureEvent e, double minS) {
			if (e instanceof ZoomEvent) {
				ZoomEvent z = (ZoomEvent) e;
				zoom(node, Math.pow(1.01, z.getZoomFactor()), minS);
			} else if (e instanceof ScrollEvent) {
				ScrollEvent s = (ScrollEvent) e;
				zoom(node, Math.pow(1.01, s.getDeltaY()), minS);
			}
		}

		/**
		 * Scale a given Node by a give Factor, will not shirnk past a given
		 * minS.
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
		 * Clip a given region based on its viewport and center clipView on
		 * regions center.
		 * 
		 * @param region
		 */
		public static void clipRegion(Region region) {
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
	}
}
