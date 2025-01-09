package imani.citibike.map;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.painter.Painter;
import org.jxmapviewer.viewer.GeoPosition;

import java.util.ArrayList;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.util.List;

/**
 * @author Martin Steiger
 */

public class RoutePainter implements Painter<JXMapViewer> {

    private Color color = Color.RED;
    private boolean antiAlias = true;
    private List<GeoPosition> track;

    public RoutePainter() {
        this.track = new ArrayList<>();
    }

    public void setTrack(List<GeoPosition> track) {
        this.track = new ArrayList<>(track);
    }

    @Override
    public void paint(Graphics2D g, JXMapViewer map, int width, int height) {
        g = (Graphics2D) g.create();

        Rectangle rect = map.getViewportBounds();
        g.translate(-rect.x, -rect.y);

        if (antiAlias) {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }

        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(4));
        drawRoute(g, map);

        g.setColor(color);
        g.setStroke(new BasicStroke(2));
        drawRoute(g, map);

        g.dispose();
    }

    private void drawRoute(Graphics2D g, JXMapViewer map) {
        int lastX = 0;
        int lastY = 0;

        boolean first = true;

        for (GeoPosition gp : track) {
            Point2D pt = map.getTileFactory().geoToPixel(gp, map.getZoom());

            if (first) {
                first = false;
            } else {
                g.drawLine(lastX, lastY, (int) pt.getX(), (int) pt.getY());
            }

            lastX = (int) pt.getX();
            lastY = (int) pt.getY();
        }
    }
}