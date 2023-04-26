package controllers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import models.Client;
import models.Commande;
import models.Vehicule;

public class Storage {
    // Default filePath:"src/controllers/Dataset.txt

    public static String getAbsolutePath(String path) {
        File file = new File(path);
        if (!file.isAbsolute()) {
            file = new File(file.getAbsolutePath());
        }
        return file.getAbsolutePath();
    }

    public static void readTxtFile(String filePath) {
        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] words = line.split(",");
                lineToData(words);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static void writeTxtFile(String filePath) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            fw.write("");
            fw.close();
            BufferedWriter bw = new BufferedWriter(new FileWriter(file.getAbsoluteFile(), true));
            for (Vehicule veh : Control.getVeh()) {
                bw.write("Vehicule" + "," + veh.getIdVehicule() + "," + veh.getKilometage() + "," + veh.getStatus()
                        + "," + veh.getSpe().getNbplace() + "," + veh.getSpe().getMarque() + ","
                        + veh.getSpe().getModele() + "," + veh.getSpe().getType());
                bw.newLine();
            }
            for (Client cli : Control.getCli()) {
                bw.write("Client" + "," + cli.getId() + "," + cli.getNom() + "," + cli.getPrenom()
                        + "," + cli.getAdresse() + "," + cli.getNumtele() + ","
                        + cli.getNumpermis() + "," + cli.getNumcartes() + "," + cli.getStatus() + "," + cli.getInfo()
                        + "," + cli.getDette());
                bw.newLine();
            }
            for (Commande cmd : Control.getCmd()) {
                bw.write("Commande" + "," + cmd.getId() + "," + cmd.getCli().getId() + ","
                        + cmd.getVeh().getIdVehicule() + "," + cmd.getstatus() + ","
                        + Factory.dateToString(cmd.getDateDebut()) + "," + Factory.dateToString(cmd.getDateFin()) + ","
                        + cmd.getKilometrage() + "," + cmd.getGarantie());
                bw.newLine();
            }
            //
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // La chaîne de caractères est ajoutée à la classe Control par le biais de
    // différentes méthodes de la classe Factory en tant que paramètres différents
    // en fonction du premier tableau de chaînes de caractères.
    public static void lineToData(String[] words) {
        switch (words[0]) {
            case "Vehicule":
                Factory.addVehicule(words[1], words[2], words[3], words[4], words[5], words[6], words[7]);
                break;
            case "Client":
                Factory.addClient(words[1], words[2], words[3], words[4], words[5], words[6], words[7], words[8],
                        words[9], words[10]);
                break;
            case "Commande":
                Factory.addCommande(words[1], words[2], words[3], words[4], words[5], words[6], words[7], words[8]);
                break;
            default:
                break;
        }

    }

}
