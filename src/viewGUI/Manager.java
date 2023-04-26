package viewGUI;

import javax.swing.*;

import controllers.Control;
import controllers.Storage;
import models.Vehicule;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Manager {

    public static void main(String[] args) {
        new Manager();
        SwingUtilities.invokeLater(() -> Manager.createManagerPage());
    }

    public static void createManagerPage() {
        JFrame frame = new JFrame("System Status");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // Create a panel for the system status label and buttons
        JPanel statusPanel = new JPanel(new BorderLayout());
        JLabel statusLabel = new JLabel("System status: Closed");

        statusPanel.add(statusLabel, BorderLayout.NORTH);

        // Create start/stop buttons
        JButton startButton = new JButton("Start System");
        JButton stopButton = new JButton("Stop System");
        stopButton.setEnabled(false);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statusLabel.setText("System status: Running");
                startButton.setEnabled(false);
                stopButton.setEnabled(true);
                GUIMainLoginPage.status = true;
            }
        });

        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statusLabel.setText("System status: Closed");
                startButton.setEnabled(true);
                stopButton.setEnabled(false);
                GUIMainLoginPage.status = false;
            }
        });
        if (GUIMainLoginPage.status == true) {
            statusLabel.setText("System status: Running");
            startButton.setEnabled(false);
            stopButton.setEnabled(true);
        } else {
            statusLabel.setText("System status: Closed");
            startButton.setEnabled(true);
            stopButton.setEnabled(false);
        }
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);
        statusPanel.add(buttonPanel, BorderLayout.CENTER);

        // Create a panel for the return and evacuate buttons
        JPanel actionPanel = new JPanel();
        JButton returnButton = new JButton("Retourner au menu supérieur");
        returnButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                GUIMainLoginPage.createLoginPage();
            }

        });
        JButton retraiterButton = new JButton("Retraiter véhicule");
        retraiterButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                JDialog retraiterFrame = new JDialog(frame, "Retraiter un Véhicule", true);
                ArrayList<Vehicule> datalist = Control.getVeh();
                VeDataTableModel tableModel = new VeDataTableModel(datalist);
                JTable table = new JTable(tableModel);
                JScrollPane scrollPane = new JScrollPane(table);
                retraiterFrame.add(scrollPane, BorderLayout.CENTER);
                retraiterFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                retraiterFrame.setSize(800, 400);
                retraiterFrame.setLocationRelativeTo(frame);

                // Add button panel
                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                retraiterFrame.add(buttonPanel, BorderLayout.SOUTH);

                // Add buttons
                JButton btnRet = new JButton("Retraiter véhicule");
                btnRet.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int selectedRow = table.getSelectedRow();
                        if (selectedRow != -1) {
                            Vehicule vehicule = datalist.get(selectedRow);
                            vehicule.setStatus("Retraite");
                            tableModel.setValueAt(vehicule.getStatus(), selectedRow, 2);
                            Storage.writeTxtFile("src/controllers/Dataset.txt");
                        }
                        JOptionPane.showMessageDialog(frame, "Modifié avec succès!");
                    }

                });
                buttonPanel.add(btnRet);
                retraiterFrame.setVisible(true);
            }

        });
        actionPanel.add(returnButton);
        actionPanel.add(retraiterButton);

        // Add the panels to the frame
        frame.add(statusPanel, BorderLayout.NORTH);
        frame.add(actionPanel, BorderLayout.SOUTH);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
