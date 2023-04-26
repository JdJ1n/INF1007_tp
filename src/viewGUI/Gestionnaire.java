package viewGUI;

import javax.swing.*;

import controllers.Control;
import controllers.Storage;
import models.Specification;
import models.Vehicule;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class Gestionnaire {
    public static JFrame mainFrame;

    private static boolean isVehicleIdExists(int id) {
        for (Vehicule veh : Control.listVeh) {
            if (veh.getIdVehicule() == id) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        new Gestionnaire();
        SwingUtilities.invokeLater(() -> Gestionnaire.createGestionnairePage());

    }

    public static void createGestionnairePage() {
        mainFrame = new JFrame("Vehicle Submission");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(700, 350);

        // Create a JPanel with GridBagLayout
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Add labels and text fields for vehicle information
        String[] labels = { "Vehicle ID:", "Kilometage:", "Status:", "Nombre de place:", "Marque:", "Modele:",
                "Type:" };
        JTextField[] textFields = new JTextField[labels.length];
        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i;
            panel.add(new JLabel(labels[i]), gbc);

            gbc.gridx = 1;
            textFields[i] = new JTextField(20);
            panel.add(textFields[i], gbc);
            int index = i;
            textFields[i].addFocusListener(new FocusAdapter() {
                @Override
                public void focusLost(FocusEvent e) {
                    if (!checkInput(index, textFields[index].getText())) {
                        JOptionPane.showMessageDialog(mainFrame, "Invalid input for " + labels[index], "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
        }

        JButton retourButton = new JButton("Retourner au menu supérieur");
        retourButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.dispose();
                GUIMainLoginPage.createLoginPage();
            }

        });
        gbc.gridx = 0;
        gbc.gridy = labels.length;
        gbc.gridwidth = 1;
        panel.add(retourButton, gbc);

        // Add a button to submit vehicle information
        JButton submitButton = new JButton("Enregistrer");
        submitButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(textFields[0].getText());
                    int mileage = Integer.parseInt(textFields[1].getText());
                    String status = textFields[2].getText();
                    int seats = Integer.parseInt(textFields[3].getText());
                    String brand = textFields[4].getText();
                    String model = textFields[5].getText();
                    String type = textFields[6].getText();

                    Vehicule veh = new Vehicule(id, mileage, status,
                            new Specification(seats, brand, model, type));
                    Control.addVeh(veh);
                    Storage.writeTxtFile("src/controllers/Dataset.txt");
                    JOptionPane.showMessageDialog(null, "Ajouté avec succès!");
                }

                catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "SVP vérrifier!");
                }
            }

        });
        gbc.gridx = 1;
        gbc.gridy = labels.length;
        gbc.gridwidth = 1;
        panel.add(submitButton, gbc);

        // Add a button to get vehicle report
        JButton reportButton = new JButton("Affricher un rapport");
        gbc.gridx = 2;
        panel.add(reportButton, gbc);

        // Add an ActionListener to the report button
        reportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ShowModeleListePage.showVehPage();
                ShowModeleListePage.showCliPage("Rapport de Client", false);
                ShowModeleListePage.showComPage("Rapport de Commande", false);

            }
        });

        mainFrame.add(panel);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    private static boolean checkInput(int index, String input) {
        // You can customize the check conditions based on the index (0-6) for each text
        // field.
        if (input.isEmpty()) {
            return true;
        }
        switch (index) {
            case 0: // Vehicle ID
                // Check if the input is an integer

                if (isVehicleIdExists(Integer.parseInt(input))) {
                    JOptionPane.showMessageDialog(null, "Véhicules dont l'ID existe déjà");
                }
                break;
            case 1: // Mileage
                // Check if the input is a int
                try {
                    Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    return false;
                }
            case 2: // Vehicle Status
                // Check if the input is either "Available" or "Unavailable"
                if (input.equals("EnTretien") || input.equals("Retraite") || input.equals("Disponible")) {
                    return true;
                }
            case 3:// Seats
                // Check if the input is an integer
                try {
                    Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    return false;
                }

            case 4: // Brand
                break;
            case 5: // Model
                break;
            case 6: // Type
                if (!input.equals("Simple") && !input.equals("Prestige") && !input.equals("Utilitaire")) {
                    return false;
                }
                break;
            default:
                break;
        }
        return true;
    }

}
