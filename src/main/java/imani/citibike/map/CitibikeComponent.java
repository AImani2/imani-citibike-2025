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
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CitibikeComponent extends JComponent {

    private final JXMapViewer mapViewer;
    private Set<Waypoint> waypoints;
    private RoutePainter routePainter = new RoutePainter();
    private WaypointPainter<Waypoint> waypointPainter = new WaypointPainter<>();
    private final List<GeoPosition> wayPointLocations = new ArrayList<>();

    public CitibikeComponent() {
        mapViewer = new JXMapViewer();
        TileFactoryInfo info = new OSMTileFactoryInfo();
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        mapViewer.setTileFactory(tileFactory);

        tileFactory.setThreadPoolSize(8);
        mapViewer.setZoom(7);
        GeoPosition nyc = new GeoPosition(40.77228687788679, -73.9842939376831);
        mapViewer.setAddressLocation(nyc);

        MouseInputListener mia = new PanMouseInputListener(mapViewer);
        mapViewer.addMouseListener(mia);
        mapViewer.addMouseMotionListener(mia);
        mapViewer.addMouseListener(new CenterMapListener(mapViewer));
        mapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCursor(mapViewer));
        mapViewer.addKeyListener(new PanKeyListener(mapViewer));

        setLayout(new BorderLayout());
        add(mapViewer, BorderLayout.CENTER);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        mapViewer.paint(g);
    }

    public void combinePainters(RoutePainter routePainter, WaypointPainter<Waypoint> waypointPainter) {
        List<Painter<JXMapViewer>> painters = List.of(routePainter, waypointPainter);
        CompoundPainter<JXMapViewer> compoundPainter = new CompoundPainter<>(painters);
        mapViewer.setOverlayPainter(compoundPainter);
    }

    public void drawRoutes(GeoPosition start, GeoPosition startStation, GeoPosition endStation, GeoPosition end) {

        waypoints = generateWaypoints(
                start,
                startStation,
                endStation,
                end
        );

        wayPointLocations.clear();
        wayPointLocations.add(start);
        wayPointLocations.add(startStation);
        wayPointLocations.add(endStation);
        wayPointLocations.add(end);

        routePainter.setTrack(wayPointLocations);
        waypointPainter.setWaypoints(waypoints);

        combinePainters(routePainter, waypointPainter);
    }

    private Set<Waypoint> generateWaypoints(
        GeoPosition start, GeoPosition startStation, GeoPosition endStation, GeoPosition end) {
        Set<Waypoint> waypoints = new HashSet<>();

        if (start != null) {
            waypoints.add(new DefaultWaypoint(start));
        }
        if (startStation != null) {
            waypoints.add(new DefaultWaypoint(startStation));
        }
        if (endStation != null) {
            waypoints.add(new DefaultWaypoint(endStation));
        }
        if (end != null) {
            waypoints.add(new DefaultWaypoint(end));
        }

        return waypoints;
    }

    public void updateWayPoints(GeoPosition start, GeoPosition startStation, GeoPosition endStation, GeoPosition end) {

        waypointPainter.setWaypoints(generateWaypoints(start, startStation, endStation, end));
        combinePainters(routePainter, waypointPainter);
    }

    public JXMapViewer getMapViewer() {
        return mapViewer;
    }


}
