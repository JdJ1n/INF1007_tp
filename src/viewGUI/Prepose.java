package viewGUI;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import controllers.Control;
import controllers.Factory;
import controllers.Storage;
import models.Client;
import models.Commande;
import models.Vehicule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Prepose {
    private static String cliId;
    private static String vehid;
    private static JFrame mainFrame;
    private static int id;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }

    static void createAndShowGUI() {
        mainFrame = new JFrame("Menu");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(400, 300);

        JPanel panel = new JPanel();
        mainFrame.add(panel);

        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        String[] buttonLabels = {
                "Retourner au menu supérieur",
                "Enregistrer les réservations",
                "Modifier les réservations",
                "Retourner un véhicule",
                "Enregistrer un client",
                "Modifier un client",
                "Accéder la liste de retard"
        };

        for (int i = 0; i < buttonLabels.length; i++) {
            JButton button = new JButton(buttonLabels[i]);
            gbc.gridx = 0;
            gbc.gridy = i;
            panel.add(button, gbc);

            final int index = i;
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    handleButtonClick(index);
                }
            });
        }

        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    private static void handleButtonClick(int index) {
        switch (index) {
            case 0:
                mainFrame.setVisible(false);
                GUIMainLoginPage.createLoginPage();
                break;
            case 1:
                mainFrame.setVisible(true);
                Control.miseAJour();
                showClient("Ordinaire");
                break;
            case 2:
                mainFrame.setVisible(true);
                Control.miseAJour();
                showCommande();
                break;
            case 3:
                mainFrame.setVisible(true);
                retournerVehicule();
                break;
            case 4:
                mainFrame.setVisible(true);
                enregistrerClient();
                break;
            case 5:
                mainFrame.setVisible(true);
                ShowModeleListePage.showCliPage("Modifier Client", true);
                break;
            case 6:
                JDialog fram = new JDialog(Gestionnaire.mainFrame, "Liste Retard");
                ArrayList<Client> datalist = Control.getCli();
                List<Client> filteredClients = ClientFilter.filterByStatus(datalist, "Retard");
                ClientModel tableModel = new ClientModel(filteredClients, false);
                JTable table = new JTable(tableModel);
                JScrollPane scrollPane = new JScrollPane(table);
                fram.add(scrollPane, BorderLayout.CENTER);
                fram.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                fram.setSize(1600, 400);
                fram.setLocationRelativeTo(mainFrame);
                fram.setVisible(true);
                break;
            default:
                break;
        }
    }

    public static void showClient(String status) {

        JDialog cliFrame = new JDialog(Gestionnaire.mainFrame, "Client Liste");
        ArrayList<Client> datalist = Control.getCli();
        // String filterStatus = "Ordinaire";
        List<Client> filteredClients = ClientFilter.filterByStatus(datalist, status);
        ClientModel tableModel = new ClientModel(filteredClients, false);
        JTable table = new JTable(tableModel);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int row = table.getSelectedRow();
                    if (row >= 0 && row < datalist.size()) {
                        Client client = datalist.get(row);
                        // 在此处执行所需操作，例如更新客户端相关信息
                        cliId = Integer.toString(client.getId());
                    }
                }
            }
        });

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel comID = new JLabel("Entrer ID de la commande: ");
        JTextField comIdText = new JTextField(10);
        JButton confirmerButton = new JButton("Confirmer");
        confirmerButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (Control.hasCmd(Integer.parseInt(comIdText.getText(), 10))) {
                    JOptionPane.showMessageDialog(null, "Commande dont l'ID existe déjà");
                } else {
                    String idCmd = comIdText.getText();
                    JDialog vehFrame = new JDialog(Gestionnaire.mainFrame, "Véhicule Liste", false);
                    ArrayList<Vehicule> datalist = Control.getVeh();
                    String filterStatus = "Disponible";
                    List<Vehicule> filterVehicules = VehiculeFilter.filterByStatus(datalist, filterStatus);
                    VeDataTableModel tableModel = new VeDataTableModel(filterVehicules);
                    JTable table = new JTable(tableModel);
                    JScrollPane scrollPane = new JScrollPane(table);

                    table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

                        @Override
                        public void valueChanged(ListSelectionEvent e) {
                            int row = table.getSelectedRow();
                            Vehicule vehicule = datalist.get(row);
                            vehid = Integer.toString(vehicule.getIdVehicule());
                        }

                    });

                    vehFrame.add(scrollPane, BorderLayout.CENTER);
                    JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                    JLabel startDateLabel = new JLabel("Entrer la date de debut: ");
                    JTextField startDateField = new JTextField(10);
                    JLabel endDateLabel = new JLabel("Entrer la date de fin: ");
                    JTextField endDateField = new JTextField(10);
                    JButton submitButton = new JButton("Enregistrer");
                    submitButton.setEnabled(false);
                    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    startDateField.addFocusListener(new FocusAdapter() {
                        @Override
                        public void focusLost(FocusEvent e) {
                            try {
                                LocalDate startDate = LocalDate.parse(startDateField.getText(), dateFormatter);
                                LocalDate now = LocalDate.now();

                                if (startDate.isBefore(now)) {
                                    JOptionPane.showMessageDialog(vehFrame,
                                            "La date de début ne peut pas être inférieure à la date actuelle !",
                                            "Error", JOptionPane.ERROR_MESSAGE);
                                    startDateField.requestFocus();
                                }
                            } catch (DateTimeParseException ex) {
                                JOptionPane.showMessageDialog(vehFrame,
                                        "Veuillez saisir le format de date correct (aaaa-MM-jj)！", "Error",
                                        JOptionPane.ERROR_MESSAGE);
                                startDateField.requestFocus();
                            }
                        }
                    });
                    endDateField.addFocusListener(new FocusAdapter() {
                        @Override
                        public void focusLost(FocusEvent e) {
                            try {
                                LocalDate startDate = LocalDate.parse(startDateField.getText(), dateFormatter);
                                LocalDate endDate = LocalDate.parse(endDateField.getText(), dateFormatter);

                                if (endDate.isBefore(startDate)) {
                                    JOptionPane.showMessageDialog(vehFrame,
                                            "La date de fin ne peut pas être antérieure à la date de début !", "Error",
                                            JOptionPane.ERROR_MESSAGE);
                                    endDateField.requestFocus();
                                }
                            } catch (DateTimeParseException ex) {
                                JOptionPane.showMessageDialog(vehFrame,
                                        "Veuillez saisir le format de date correct (aaaa-MM-jj)！", "Error",
                                        JOptionPane.ERROR_MESSAGE);
                                endDateField.requestFocus();
                            }
                        }
                    });
                    DocumentListener documentListener = new DocumentListener() {
                        @Override
                        public void insertUpdate(DocumentEvent e) {
                            validateInput();
                        }

                        @Override
                        public void removeUpdate(DocumentEvent e) {
                            validateInput();
                        }

                        @Override
                        public void changedUpdate(DocumentEvent e) {
                            validateInput();
                        }

                        private void validateInput() {
                            try {
                                LocalDate startDate = LocalDate.parse(startDateField.getText(), dateFormatter);
                                LocalDate endDate = LocalDate.parse(endDateField.getText(), dateFormatter);
                                LocalDate now = LocalDate.now();

                                if (startDate.isBefore(now) || endDate.isBefore(startDate)) {
                                    submitButton.setEnabled(false);
                                } else {
                                    submitButton.setEnabled(true);
                                }
                            } catch (DateTimeParseException e) {
                                submitButton.setEnabled(false);
                            }
                        }
                    };

                    startDateField.getDocument().addDocumentListener(documentListener);
                    endDateField.getDocument().addDocumentListener(documentListener);
                    submitButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            LocalDate debut = LocalDate.parse(startDateField.getText());
                            //LocalDate fin = LocalDate.parse(endDateField.getText());
                            if (!debut.isAfter(LocalDate.now())) {
                                int selectedRow = table.getSelectedRow();
                                if (selectedRow != -1) {
                                    Factory.addCommande(idCmd, cliId, vehid, "Rendez-vous", startDateField.getText(),
                                            endDateField.getText(), "0", "200");
                                } else {
                                    Factory.addCommande(idCmd, cliId, vehid, "En cours", startDateField.getText(),
                                            endDateField.getText(), "0", "200");
                                }
                                Control.getCli(Integer.parseInt(cliId)).setStatus("Loue");
                                Control.getVeh(Integer.parseInt(vehid)).setStatus("Occupee");
                            }

                            Storage.writeTxtFile("src/controllers/Dataset.txt");
                            JOptionPane.showMessageDialog(null, "Ajouté avec succès!");
                            vehFrame.setVisible(false);
                            mainFrame.setVisible(true);
                        }

                    });
                    // Ajouter des composants au panneau inférieur
                    bottomPanel.add(startDateLabel);
                    bottomPanel.add(startDateField);
                    bottomPanel.add(endDateLabel);
                    bottomPanel.add(endDateField);
                    bottomPanel.add(submitButton);

                    // Ajouter le panneau inférieur au vehFrame
                    vehFrame.add(bottomPanel, BorderLayout.SOUTH);

                    vehFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    vehFrame.setSize(1600, 400);
                    vehFrame.setLocationRelativeTo(mainFrame);
                    cliFrame.dispose();
                    vehFrame.setVisible(true);
                }

            }

        });
        JScrollPane scrollPane = new JScrollPane(table);
        bottomPanel.add(comID);
        bottomPanel.add(comIdText);
        bottomPanel.add(confirmerButton);
        cliFrame.add(scrollPane, BorderLayout.CENTER);
        cliFrame.add(bottomPanel, BorderLayout.SOUTH);
        cliFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        cliFrame.setSize(1600, 400);
        cliFrame.setLocationRelativeTo(mainFrame);
        cliFrame.setVisible(true);

    }

    public static void showCommande() {
        JDialog cmdFrame = new JDialog(Gestionnaire.mainFrame, "Commande Liste");
        ArrayList<Commande> datalist = Control.getCmd();
        CommandeModel model = new CommandeModel(datalist, true);

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        cmdFrame.add(scrollPane, BorderLayout.CENTER);
        cmdFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        cmdFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int result = JOptionPane.showConfirmDialog(cmdFrame, "Dois-je enregistrer les modifications ?",
                        "Enregistrer les modifications", JOptionPane.YES_NO_CANCEL_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    cmdFrame.setVisible(false);
                    mainFrame.setVisible(true);
                } else if (result == JOptionPane.NO_OPTION) {
                    cmdFrame.setVisible(false);
                    mainFrame.setVisible(true);
                }
            }
        });
        cmdFrame.setSize(1600, 400);
        cmdFrame.setLocationRelativeTo(mainFrame);
        cmdFrame.setVisible(true);

    }

    private static void enregistrerClient() {
        JFrame frame = new JFrame("Enregister Client");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("ID:"), getConstraints(0, 0));
        JTextField idField = new JTextField(15);
        idField.getDocument().addDocumentListener(new DocumentListener() {
            private void validateId() {
                String id = idField.getText();
                if (Control.hasCli(Integer.parseInt(id))) {
                    JOptionPane.showMessageDialog(frame, "Client dont l'ID existe déjà", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    idField.requestFocus();
                }
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                validateId();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                validateId();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                validateId();
            }
        });
        inputPanel.add(idField, getConstraints(1, 0));

        // Nom label and text field
        inputPanel.add(new JLabel("Nom:"), getConstraints(0, 1));
        JTextField nomField = new JTextField(15);
        inputPanel.add(nomField, getConstraints(1, 1));

        // Prenom label and text field
        inputPanel.add(new JLabel("Prenom:"), getConstraints(0, 2));
        JTextField prenomField = new JTextField(15);
        inputPanel.add(prenomField, getConstraints(1, 2));

        // Adresse label and text field
        inputPanel.add(new JLabel("Adresse:"), getConstraints(0, 3));
        JTextField adresseField = new JTextField(15);
        inputPanel.add(adresseField, getConstraints(1, 3));

        // Numtele label and text field
        inputPanel.add(new JLabel("Numtele:"), getConstraints(0, 4));
        JTextField numteleField = new JTextField(15);
        inputPanel.add(numteleField, getConstraints(1, 4));

        // Numpermis label and text field
        inputPanel.add(new JLabel("Numpermis:"), getConstraints(0, 5));
        JTextField numpermisField = new JTextField(15);
        inputPanel.add(numpermisField, getConstraints(1, 5));

        // Numcartes label and text field
        inputPanel.add(new JLabel("Numcartes:"), getConstraints(0, 6));
        JTextField numcartesField = new JTextField(15);
        inputPanel.add(numcartesField, getConstraints(1, 6));

        // Infomation label and text field
        inputPanel.add(new JLabel("Infomation:"), getConstraints(0, 7));
        JTextField infomationField = new JTextField(15);
        inputPanel.add(infomationField, getConstraints(1, 7));

        frame.add(inputPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton submiterButton = new JButton("Submiter");
        submiterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Factory.addClient(idField.getText(), nomField.getText(), prenomField.getText(), adresseField.getText(),
                        numteleField.getText(), numpermisField.getText(), numcartesField.getText(), "Oridaire",
                        infomationField.getText(), "0");
                Storage.writeTxtFile("src/controllers/Dataset.txt");
                JOptionPane.showMessageDialog(frame, "Client ajouté avec succès!");
                idField.setText("");
                nomField.setText("");
                prenomField.setText("");
                adresseField.setText("");
                numteleField.setText("");
                numcartesField.setText("");
                numpermisField.setText("");
                numcartesField.setText("");
                infomationField.setText("");
            }
        });
        buttonPanel.add(submiterButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        List<JTextField> fields = Arrays.asList(idField, nomField, prenomField, adresseField, numteleField,
                numpermisField, numcartesField, infomationField);

        DocumentListener enableButtonListener = new DocumentListener() {
            private void checkAllFields() {
                boolean allFieldsFilled = fields.stream().allMatch(field -> !field.getText().trim().isEmpty());
                submiterButton.setEnabled(allFieldsFilled);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                checkAllFields();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                checkAllFields();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                checkAllFields();
            }
        };

        // Add the listener to all text fields
        for (JTextField field : fields) {
            field.getDocument().addDocumentListener(enableButtonListener);
        }

        // Initially disable the button
        submiterButton.setEnabled(false);

        // ... (rest of the method)

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    private static GridBagConstraints getConstraints(int x, int y) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.anchor = x == 0 ? GridBagConstraints.LINE_END : GridBagConstraints.LINE_START;
        constraints.insets = new Insets(5, 5, 5, 5);
        return constraints;
    }

    private static void retournerVehicule() {
        Control.miseAJour();
        JDialog comFrame = new JDialog(mainFrame, "Liste de Commande");
        ArrayList<Commande> datalist = Control.getCmd();
        CommandeModel tableModel = new CommandeModel(datalist, false);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel buttonPanel = new JPanel();
        JLabel label = new JLabel("le nombre de kilomètres dépassant l'allocation gratuite:");
        JTextField textField = new JTextField();
        JButton button = new JButton("Submiter");
        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.add(label, BorderLayout.WEST);
        buttonPanel.add(textField, BorderLayout.CENTER);
        buttonPanel.add(button, BorderLayout.EAST);

        comFrame.setLayout(new BorderLayout());
        comFrame.add(buttonPanel, BorderLayout.SOUTH);
        comFrame.add(scrollPane, BorderLayout.CENTER);

        ListSelectionModel selectionModel = table.getSelectionModel();

        selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {

                    int rowIndex = table.getSelectedRow();
                    id = datalist.get(rowIndex).getId();

                }
            }
        });
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

                boolean hasSelection = table.getSelectedRowCount() > 0;

                boolean isNumeric = textField.getText().matches("\\d+");

                button.setEnabled(hasSelection && isNumeric);
            }
        });

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Control.getCmd(id).setKilometrage(Integer.parseInt(textField.getText()));
                Control.getCmd(id)
                        .setGarantie(Control.getCmd(id).getGarantie() - 0.25 * Control.getCmd(id).getKilometrage());
                if (Control.getCmd(id).getstatus().equals("Retard")) {
                    long late = Factory.dateBetween(Control.getCmd(id).getDateFin());
                    if (late > 1) {
                        Control.getCmd(id).setGarantie(Control.getCmd(id).getGarantie() - 48 - (late - 1) * 75);
                    } else {
                        long latehours = Factory.hourBetween(Control.getCmd(id).getDateFin());
                        Control.getCmd(id).setGarantie(Control.getCmd(id).getGarantie() - (latehours) * 2);
                    }
                }
                if (Control.getCmd(id).getGarantie() >= 0) {
                    JOptionPane.showMessageDialog(comFrame,
                            "Cher client,Nous vous informons que votre dépôt, après avoir été utilisé pour couvrir les frais supplémentaires, présente un solde de"
                                    + Control.getCmd(id).getGarantie() + " dollars. Ce montant vous sera remboursé. ");
                    Control.getCmd(id).getCli().setStatus("Ordinaire");
                } else {
                    JOptionPane.showMessageDialog(comFrame,
                            "Nous vous informons que votre dépôt a été utilisé pour couvrir les frais supplémentaires et qu'il ne reste plus de solde. Vous avez actuellement une dette de "
                                    + (-Control.getCmd(id).getGarantie())
                                    + " dollars. Si cette somme n'est pas réglée, votre compte sera bloqué.");
                    Control.getCmd(id).getCli().setStatus("Bloque");
                    Control.getCmd(id).getCli().setDette((-Control.getCmd(id).getGarantie()));
                }
                Control.getCmd(id).getVeh().setStatus("Disponible");
                Control.getCmd(id).setstatus("Fini");
                Storage.writeTxtFile("src/controllers/Dataset.txt");
            }
        });

        comFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        comFrame.setSize(1600, 400);
        comFrame.setLocationByPlatform(true);
        comFrame.setVisible(true);
    }
}
