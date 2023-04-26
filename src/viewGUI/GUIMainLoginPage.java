package viewGUI;

import javax.swing.*;

import controllers.Control;
import viewConsole.ConsoleMain;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIMainLoginPage {
    public static boolean status = false;
    private static String username1 = "client";
    private static String password1 = "123";
    private static String username2 = "prepose";
    private static String password2 = "123";
    private static String username3 = "manager";
    private static String password3 = "123";
    private static String username4 = "gestionnaire";
    private static String password4 = "123";

    public static void main(String[] args) {
        new GUIMainLoginPage();
        SwingUtilities.invokeLater(() -> GUIMainLoginPage.createLoginPage());
        String filePath = "src/controllers/Dataset.txt";
        Control.getInstance();
        ConsoleMain.ajouterDonneesInitiale(filePath);
        ConsoleMain.sauvegardeDonnees(filePath);
    }

    public static void createLoginPage() {
        JFrame frame = new JFrame("Login Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 200);

        JPanel panel = new JPanel(new GridBagLayout());
        frame.add(panel);

        GridBagConstraints constraints = new GridBagConstraints();

        JLabel lblUsername = new JLabel("Username:");
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.LINE_END;
        panel.add(lblUsername, constraints);

        JTextField txtUsername = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.LINE_START;
        panel.add(txtUsername, constraints);

        JLabel lblPassword = new JLabel("Password:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.LINE_END;
        panel.add(lblPassword, constraints);

        JPasswordField pwdPassword = new JPasswordField(20);
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.LINE_START;
        panel.add(pwdPassword, constraints);

        JButton btnLogin = new JButton("Login");
        JButton btnVisitor = new JButton("Login as visiter");
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.LINE_START;
        panel.add(btnLogin, constraints);
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.LINE_END;
        panel.add(btnVisitor, constraints);

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = txtUsername.getText();
                String password = new String(pwdPassword.getPassword());

                if (username1.equals(username) && password1.equals(password) && status == true) {
                    frame.dispose();
                    SpecificationdeVeh.createMainPage();
                } else if (username2.equals(username) && password2.equals(password)) {
                    frame.dispose();
                    Prepose.createAndShowGUI();
                } else if (username3.equals(username) && password3.equals(password)) {
                    frame.dispose();
                    Manager.createManagerPage();
                }

                else if (username4.equals(username) && password4.equals(password) && status == true) {
                    frame.dispose();
                    Gestionnaire.createGestionnairePage();
                } else if (status == false) {
                    JOptionPane.showMessageDialog(frame,
                            "System is closed! Veuillez vous connecter au système en tant que Manager et activer le système");
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid username or password.");
                }

            }

        });

        btnVisitor.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (status == true) {
                    frame.dispose();
                    SpecificationdeVeh.createMainPage();
                } else {

                    JOptionPane.showMessageDialog(frame,
                            "System is closed! Veuillez vous connecter au système en tant que Manager et activer le système");
                }

            }

        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
