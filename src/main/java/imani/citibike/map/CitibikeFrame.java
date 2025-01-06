package imani.citibike.map;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.Waypoint;
import org.jxmapviewer.viewer.WaypointPainter;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

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

        // now I have to add my mouse listener to the map
        // and i have to add my action listeners to the buttons
        // i call my controller from this class
    }
}
