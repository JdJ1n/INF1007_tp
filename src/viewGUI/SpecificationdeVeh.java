package viewGUI;

import javax.swing.*;
import controllers.Control;
import models.Specification;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SpecificationdeVeh {

    public static void main(String[] args) {
        new SpecificationdeVeh();
        SwingUtilities.invokeLater(() -> SpecificationdeVeh.createMainPage());
    }

    public static void createMainPage() {
        JFrame frame = new JFrame("Spécification de Véhicule");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);

        // String filePath = "src/controllers/Dataset.txt";
        // Control.getInstance();
        // Main.ajouterDonneesInitiale(filePath);
        // Main.sauvegardeDonnees(filePath);

        ArrayList<Specification> datalist = Control.getSpe();

        // Create table model
        SpeDataTableModel tableModel = new SpeDataTableModel(datalist);

        // Create table
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Add button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Add buttons
        JButton btnRet = new JButton("Retourner au menu supérieur");
        btnRet.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                GUIMainLoginPage.createLoginPage();
            }

        });
        buttonPanel.add(btnRet);

        frame.setLocationRelativeTo(null); // Center the window on the screen
        frame.setVisible(true);
    }
}
