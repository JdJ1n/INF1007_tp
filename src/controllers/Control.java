package controllers;

import models.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class Control {
	private static boolean runstatus = true;
	private static ArrayList<Specification> listSpe = new ArrayList<Specification>();
	public static ArrayList<Vehicule> listVeh = new ArrayList<Vehicule>();
	public static ArrayList<Client> listCli = new ArrayList<Client>();
	private static ArrayList<Commande> listCmd = new ArrayList<Commande>();
	private static ArrayList<Employer> listEmp = new ArrayList<Employer>();
	private static Control instance = new Control();

	private Control() {
	}

	public static Control getInstance() {
		return instance;
	}

	public static boolean getStatus() {
		return runstatus;
	}

	public static void changeStatus() {
		runstatus = !runstatus;
	}

	public static ArrayList<Specification> getSpe() {
		return listSpe;
	}

	public static boolean hasSpe(Specification spe) {
		return listSpe.contains(spe);
	}

	public static int countSpe(Specification spe) {
		int count = 0;
		for (Vehicule veh : listVeh) {
			if (veh.getSpe().equals(spe)) {
				count++;
			}
		}
		return count;
	}

	public static ArrayList<Vehicule> getVeh() {
		return listVeh;
	}

	public static Vehicule getVeh(int id) {
		for (Vehicule veh : listVeh) {
			if (veh.getIdVehicule() == id) {
				return veh;
			}
		}
		return null;
	}

	public static boolean hasVeh(int id) {
		for (Vehicule veh : listVeh) {
			if (id == veh.getIdVehicule()) {
				return true;
			}
		}
		return false;
	}

	public static void addVeh(Vehicule veh) {
		if (!listSpe.contains(veh.getSpe())) {
			listSpe.add(veh.getSpe());
		}
		listVeh.add(veh);
	}

	public static void removeVeh(Vehicule veh) {
		if (listVeh.contains(veh)) {
			listVeh.remove(veh);
			for (int i = 0; i < listSpe.size(); i++) {
				if (countSpe(listSpe.get(i)) == 0) {
					listSpe.remove(listSpe.get(i));
				}
			}
		}
	}

	public static void removeVeh(int id) {
		for (int i = 0; i < listVeh.size(); i++) {
			if (listVeh.get(i).getIdVehicule() == id) {
				removeVeh(listVeh.get(i));
			}
		}
	}

	public static ArrayList<Client> getCli() {
		return listCli;
	}

	public static Client getCli(int id) {
		for (Client cli : listCli) {
			if (cli.getId() == id) {
				return cli;
			}
		}
		return null;
	}

	public static Boolean hasCli(int id) {
		for (Client cli : listCli) {
			if (cli.getId() == id) {
				return true;
			}
		}
		return false;
	}

	public static void addCli(Client cli) {
		listCli.add(cli);
	}

	public static void removeCli(Client cli) {
		if (listCli.contains(cli)) {
			listCli.remove(cli);
		}
	}

	public static void removeCli(int id) {
		for (Client cli : listCli) {
			if (cli.getId() == id) {
				removeCli(cli);
			}
		}
	}

	public static ArrayList<Commande> getCmd() {
		return listCmd;
	}

	public static boolean hasCmd(int id) {
		for (Commande cmd : listCmd) {
			if (cmd.getId() == id) {
				return true;
			}
		}
		return false;
	}

	public static Commande getCmd(int id) {
		for (Commande cmd : listCmd) {
			if (cmd.getId() == id) {
				return cmd;
			}
		}
		return null;
	}

	public static void addCmd(Commande cmd) {
		listCmd.add(cmd);
	}

	public static void removeCmd(Commande cmd) {
		if (listCmd.contains(cmd)) {
			listCmd.remove(cmd);
		}
	}

	public static void removeCmd(int id) {
		for (Commande cmd : listCmd) {
			if (cmd.getId() == id) {
				removeCmd(cmd);
			}
		}
	}

	public static ArrayList<Employer> getEmp() {
		return listEmp;
	}

	public static void addEmp(Employer emp) {
		listEmp.add(emp);
	}

	public static Employer getEmp(int id) {
		for (Employer emp : listEmp) {
			if (emp.getId() == id) {
				return emp;
			}
		}
		return null;
	}

	public static void showSpecification() {
		System.out.printf("%-15s%-15s%-15s%-15s\n", "Type", "Marque", "Modele", "Nombre de place");
		for (Specification spe : listSpe) {
			System.out.printf("%-15s%-15s%-15s%-15s\n", spe.getType(), spe.getMarque(), spe.getModele(),
					spe.getNbplace());
		}
	}

	public static void showVeh() {
		System.out.printf("%-5s%-15s%-15s%-15s%-15s%-15s%-15s\n", "ID", "Kilometrage", "Etat", "Type", "Marque",
				"Modele", "Nombre de place");
		for (Vehicule veh : listVeh) {
			System.out.printf("%-5s%-15s%-15s%-15s%-15s%-15s%-15s\n", veh.getIdVehicule(), veh.getKilometage(),
					veh.getStatus(), veh.getSpe().getType(), veh.getSpe().getMarque(), veh.getSpe().getModele(),
					veh.getSpe().getNbplace());
		}
	}

	public static void showVeh(String status) {
		System.out.printf("%-5s%-15s%-15s%-15s%-15s%-15s%-15s\n", "ID", "Kilometrage", "Etat", "Type", "Marque",
				"Modele", "Nombre de place");
		for (Vehicule veh : listVeh) {
			if (veh.getStatus().equals(status)) {
				System.out.printf("%-5s%-15s%-15s%-15s%-15s%-15s%-15s\n", veh.getIdVehicule(), veh.getKilometage(),
						veh.getStatus(), veh.getSpe().getType(), veh.getSpe().getMarque(), veh.getSpe().getModele(),
						veh.getSpe().getNbplace());
			}
		}
	}

	public static void showCmd() {
		// (int id, Client cli, Vehicule veh, String status, Date datedebut, Date
		// datefin,int kilometrage,int garantie
		System.out.printf("%-5s%-15s%-15s%-15s%-15s%-15s%-15s%-15s\n", "ID", "Client ID", "Vehicule ID", "Etat",
				"Debut",
				"Fin", "Kilometrage", "Garantie");
		for (Commande cmd : listCmd) {
			System.out.printf("%-5s%-15s%-15s%-15s%-15s%-15s%-15s%-15s\n", cmd.getId(), cmd.getCli().getId(),
					cmd.getVeh().getIdVehicule(), cmd.getstatus(), Factory.dateToString(cmd.getDateDebut()),
					Factory.dateToString(cmd.getDateFin()),
					cmd.getKilometrage(), cmd.getGarantie());
		}
	}

	public static void showCmd(int id) {
		// (int id, Client cli, Vehicule veh, String status, Date datedebut, Date
		// datefin,int kilometrage,int garantie
		System.out.printf("%-5s%-15s%-15s%-15s%-15s%-15s%-15s%-15s\n", "ID", "Client ID", "Vehicule ID", "Etat",
				"Debut",
				"Fin", "Kilometrage", "Garantie");
		for (Commande cmd : listCmd) {
			if (cmd.getId() == id) {
				System.out.printf("%-5s%-15s%-15s%-15s%-15s%-15s%-15s%-15s\n", cmd.getId(), cmd.getCli().getId(),
						cmd.getVeh().getIdVehicule(), cmd.getstatus(), Factory.dateToString(cmd.getDateDebut()),
						Factory.dateToString(cmd.getDateFin()),
						cmd.getKilometrage(), cmd.getGarantie());
			}
		}
	}

	public static void showVehDetail(int id) {
		boolean find = false;
		for (Vehicule veh : listVeh) {
			if (veh.getIdVehicule() == id) {
				System.out.printf("%-5s%-15s%-15s%-15s%-15s%-15s%-15s\n", "ID", "Kilometrage", "Etat", "Type",
						"Marque", "Modele", "Nombre de place");
				System.out.printf("%-5s%-15s%-15s%-15s%-15s%-15s%-15s\n", veh.getIdVehicule(), veh.getKilometage(),
						veh.getStatus(), veh.getSpe().getType(), veh.getSpe().getMarque(), veh.getSpe().getModele(),
						veh.getSpe().getNbplace());
				find = true;
			}
		}
		if (find == false) {
			System.out.println("Le véhicule avec l'ID " + id + " n'existe pas dans le système.");
		}
	}

	public static void showClients() {
		System.out.printf("%-5s%-15s%-15s%-15s%-20s%-20s%-20s%-10s%-10s%-10s\n", "ID", "Nom", "Prenom", "Adresse",
				"Tele", "Permis",
				"Cartes", "Etat", "Info", "Dette");
		for (Client cli : listCli) {
			System.out.printf("%-5s%-15s%-15s%-15s%-20s%-20s%-20s%-10s%-10s%-10s\n", cli.getId(), cli.getNom(),
					cli.getPrenom(), cli.getAdresse(), cli.getNumtele(), cli.getNumpermis(), cli.getNumcartes(),
					cli.getStatus(), cli.getInfo(), cli.getDette());
		}
	}

	public static void showClients(String status) {
		System.out.printf("%-5s%-15s%-15s%-15s%-20s%-20s%-20s%-10s%-10s%-10s\n", "ID", "Nom", "Prenom", "Adresse",
				"Tele", "Permis",
				"Cartes", "Etat", "Info", "Dette");
		for (Client cli : listCli) {
			if (cli.getStatus().equals(status)) {
				System.out.printf("%-5s%-15s%-15s%-15s%-20s%-20s%-20s%-10s%-10s%-10s\n", cli.getId(), cli.getNom(),
						cli.getPrenom(), cli.getAdresse(), cli.getNumtele(), cli.getNumpermis(), cli.getNumcartes(),
						cli.getStatus(), cli.getInfo(), cli.getDette());
			}
		}
	}

	public static void showClientDetail(int id) {
		boolean find = false;
		for (Client cli : listCli) {
			if (cli.getId() == id) {
				System.out.printf("%-5s%-15s%-15s%-15s%-20s%-20s%-20s%-10s%-10s%-10s\n", "ID", "Nom", "Prenom",
						"Adresse", "Tele", "Permis", "Cartes", "Etat", "Info", "Dette");
				System.out.printf("%-5s%-15s%-15s%-15s%-20s%-20s%-20s%-10s%-10s%-10s\n", cli.getId(), cli.getNom(),
						cli.getPrenom(), cli.getAdresse(), cli.getNumtele(), cli.getNumpermis(), cli.getNumcartes(),
						cli.getStatus(), cli.getInfo(), cli.getDette());
				find = true;
			}
		}
		if (find == false) {
			System.out.println("Le véhicule avec l'ID " + id + " n'existe pas dans le système.");
		}
	}

	public static void miseAJour() {
		LocalDate ajd = LocalDate.now();
		Date date = Date.from(ajd.atStartOfDay(ZoneId.systemDefault()).toInstant());
		for (Commande cmd : listCmd) {
			if (cmd.getDateFin().before(date) && cmd.getstatus().equals("En cours")) {
				cmd.setstatus("Retard");
				cmd.getCli().setStatus("Retard");
			}
			if (cmd.getDateDebut().before(date) && cmd.getstatus().equals("Rendez-vous")) {
				cmd.setstatus("En cours");
			}
		}
	}

}
