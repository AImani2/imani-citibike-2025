package imani.citibike.map;

import imani.citibike.json.Station;
import org.jxmapviewer.viewer.GeoPosition;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.Set;

public class CitibikeFrame extends JFrame {

    private final CitibikeController controller;
    private boolean isToPoint = true;
    private GeoPosition toPosition;
    private GeoPosition fromPosition;
    public CitibikeFrame() {

        CitibikeComponent mapViewer = new CitibikeComponent();
        controller = new CitibikeController(mapViewer);

        setTitle("Citibike Map");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().add(mapViewer, BorderLayout.CENTER);

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

        mapViewer.getMapViewer().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                Point2D.Double point = new Point2D.Double(x, y);
                GeoPosition position = mapViewer.getMapViewer().convertPointToGeoPosition(point);
                if (isToPoint) {
                    toPosition = position;
                    toLabel.setText("To: " + position);
                } else {
                    fromPosition = position;
                    fromLabel.setText("From: " + position);
                }

                isToPoint = !isToPoint;
                controller.setPoints(toPosition, fromPosition);
                mapViewer.updateWayPoints(
                        toPosition,
                        fromPosition,
                        controller.getStartStation(),
                        controller.getEndStation());
                mapViewer.getMapViewer().repaint();
            }
        });

        mapButton.addActionListener(e -> {
            if (toPosition != null && fromPosition != null) {
                controller.findClosestStations();

                mapViewer.drawRoutes(
                        toPosition,
                        fromPosition,
                        controller.getStartStation(),
                        controller.getEndStation());

                mapViewer.getMapViewer().zoomToBestFit(
                        Set.of(fromPosition,
                                controller.getStartStation(),
                                controller.getEndStation(),
                                toPosition),
                        1.0
                );

                mapViewer.getMapViewer().repaint();
            } else {
                JOptionPane.showMessageDialog(null, "Please select both 'To' and 'From' points.");
            }
        });

        // clear resets any stored variables
        clearButton.addActionListener(e -> {
            toPosition = null;
            fromPosition = null;
            toLabel.setText("To: ");
            fromLabel.setText("From: ");
            controller.clearPoints();
            mapViewer.updateWayPoints(
                    toPosition,
                    fromPosition,
                    controller.getStartStation(),
                    controller.getEndStation());
            mapViewer.getMapViewer().setOverlayPainter(null);
            mapViewer.getMapViewer().repaint();
        });

    }


}
