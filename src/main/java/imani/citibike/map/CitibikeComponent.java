package imani.citibike.map;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.input.CenterMapListener;
import org.jxmapviewer.input.PanKeyListener;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCursor;
import org.jxmapviewer.painter.CompoundPainter;
import org.jxmapviewer.viewer.*;
import org.jxmapviewer.painter.Painter;
import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class CitibikeComponent extends JComponent {
    private final JXMapViewer mapViewer;
    public CitibikeComponent() {
        mapViewer = new JXMapViewer();
        TileFactoryInfo info = new OSMTileFactoryInfo();
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        mapViewer.setTileFactory(tileFactory);

        tileFactory.setThreadPoolSize(8);

        GeoPosition nyc = new GeoPosition(40, 73);

        mapViewer.setZoom(7);
        mapViewer.setAddressLocation(nyc);

        MouseInputListener mia = new PanMouseInputListener(mapViewer);
        mapViewer.addMouseListener(mia);
        mapViewer.addMouseMotionListener(mia);
        mapViewer.addMouseListener(new CenterMapListener(mapViewer));
        mapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCursor(mapViewer));
        mapViewer.addKeyListener(new PanKeyListener(mapViewer));

    }

    // what does this do?
    public void drawRoutes(RoutePainter routePainter, WaypointPainter<Waypoint> waypointPainter) {
        List<Painter<JXMapViewer>> painters = List.of(routePainter, waypointPainter);
        CompoundPainter<JXMapViewer> compoundPainter = new CompoundPainter<>(painters);
        mapViewer.setOverlayPainter(compoundPainter);
    }

    public JXMapViewer getMapViewer() {
        return mapViewer;
    }
}
