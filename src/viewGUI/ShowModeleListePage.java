package viewGUI;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import controllers.Control;
import models.Client;
import models.Commande;
import models.Vehicule;

public class ShowModeleListePage {
    public static void showVehPage() {
        JDialog retraiterFrame = new JDialog(Gestionnaire.mainFrame, "Rapport de Véhicule");
        ArrayList<Vehicule> datalist = Control.getVeh();
        VeDataTableModel tableModel = new VeDataTableModel(datalist);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        retraiterFrame.add(scrollPane, BorderLayout.CENTER);
        retraiterFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        retraiterFrame.setSize(800, 400);
        retraiterFrame.setLocationByPlatform(true);
        retraiterFrame.setVisible(true);
    }

    public static void showCliPage(String title, Boolean isEditable) {
        JDialog cliFrame = new JDialog(Gestionnaire.mainFrame, title);
        ArrayList<Client> datalist = Control.getCli();
        ClientModel tableModel = new ClientModel(datalist, isEditable);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        cliFrame.add(scrollPane, BorderLayout.CENTER);
        cliFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        cliFrame.setSize(1600, 400);
        cliFrame.setLocationByPlatform(true);
        cliFrame.setVisible(true);
    }

    public static void showComPage(String title, Boolean isEditable) {
        JDialog comFrame = new JDialog(Gestionnaire.mainFrame, title);
        ArrayList<Commande> datalist = Control.getCmd();
        CommandeModel tableModel = new CommandeModel(datalist, isEditable);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        comFrame.add(scrollPane, BorderLayout.CENTER);
        comFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        comFrame.setSize(1600, 400);
        comFrame.setLocationByPlatform(true);
        comFrame.setVisible(true);
    }
}
