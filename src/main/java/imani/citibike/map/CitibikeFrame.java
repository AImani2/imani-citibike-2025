package imani.citibike.map;

import org.jxmapviewer.viewer.Waypoint;
import org.jxmapviewer.viewer.WaypointPainter;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CitibikeFrame extends JFrame {

    private final CitibikeController controller;
    public CitibikeFrame() {
        RoutePainter routePainter = new RoutePainter();
        WaypointPainter<Waypoint> waypointPainter = new WaypointPainter<>();
        CitibikeComponent mapViewer = new CitibikeComponent();
        controller = new CitibikeController();

        setTitle("Citibike Map");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().add(mapViewer); //add(mapViewer, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();

        JButton mapButton = new JButton("Map");
        buttonPanel.add(mapButton);

        JButton clearButton = new JButton("Clear");
        buttonPanel.add(clearButton);

        add(buttonPanel, BorderLayout.SOUTH);

        JPanel coordPanel = new JPanel();
        coordPanel.setLayout(new GridLayout(0, 1));

        JLabel toLabel = new JLabel("To: ");
        toLabel.setBorder(new LineBorder(Color.BLACK));
        toLabel.setBackground(Color.WHITE);
        toLabel.setOpaque(true);
        coordPanel.add(toLabel);

        JLabel fromLabel = new JLabel("From: ");
        fromLabel.setBorder(new LineBorder(Color.BLACK));
        fromLabel.setBackground(Color.WHITE);
        fromLabel.setOpaque(true);
        coordPanel.add(fromLabel);
        add(coordPanel, BorderLayout.NORTH);

        // this gets the x and y which the user clicked on.
        // I need to store those points as to and from coordinates
        // these are stored in the controller?
        // how to decide if they are to and from?
        mapViewer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
//                Point2D.Double point = new Point2D.Double(x, y);
//                GeoPosition position = mapViewer.convertPointToGeoPosition(point);
                // call the controller here
            }
        });


        /*mapViewer.getMapViewer().zoomToBestFit(
                Set.of(from, startStation, endStation, to), //these r GeoPositions
                1.0
        );*/

        // and i have to add my action listeners to the buttons
        // when the user hits map that is when the controller calls findClosestStation
        // map also has to call draw routes which allows me to draw the routes for the user knowing the closest station info
        mapButton.addActionListener(e -> {
            //controller
            repaint();
        });

        // clear resets any stored variables
        clearButton.addActionListener(e -> {
            //controller
            repaint();
        });

    }


}
