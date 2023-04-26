package viewConsole;

import controllers.*;
import models.Vehicule;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Scanner;

public class ConsoleMain {
	public static void main(String[] args) {
		// Default filePath:"src/controllers/Dataset.txt"
		Scanner sc = new Scanner(System.in);
		String filePath = "src/controllers/Dataset.txt";
		System.out.println("Bienvenue dans le système de location pour l'aéroport :");
		// Ajouter des données initiales
		Control.getInstance();
		ajouterDonneesInitiale(filePath);
		integrationMenu(sc);
		sauvegardeDonnees(filePath);
		sc.close();
	}

	public static void ajouterDonneesInitiale(String filePath) {
		Storage.readTxtFile(filePath);
	}

	public static void sauvegardeDonnees(String filePath) {
		Storage.writeTxtFile(filePath);
	}

	public static boolean runSys() {
		return Control.getStatus();
	}

	public static void consulterOffre() {
		if (!runSys()) {
			System.out.println("Le système est arrêté.");
			return;
		}
		Control.showSpecification();
	}

	public static void consulterDossier() {
		if (!runSys()) {
			System.out.println("Le système est arrêté.");
			return;
		}
		Control.showVeh("Disponible");
	}

	public static void enregistrerReservation(Scanner sc) {
		if (!runSys()) {
			System.out.println("Le système est arrêté.");
			return;
		}
		Control.miseAJour();
		try {
			System.out.println("Entrer id de Commande: ");
			String idCmd = sc.nextLine();
			if (Control.hasCmd(Integer.parseInt(idCmd))) {
				System.out.println("Commande dont l'ID existe déjà");
				return;
			}
			Control.showClients("Ordinaire");
			System.out.println("Entrer id de Client: ");
			String cliid = sc.nextLine();
			Control.showVeh("Disponible");
			System.out.println("Entrer id de Vehicule: ");
			String vehid = sc.nextLine();
			System.out.println("Entrer la date de debut: ");
			String date1 = sc.nextLine();
			System.out.println("Entrer la date de fin: ");
			String date2 = sc.nextLine();
			LocalDate debut = LocalDate.parse(date1);
			LocalDate fin = LocalDate.parse(date2);
			if ((Control.getCli(Integer.parseInt(cliid)).getStatus().equals("Ordinaire"))
					&& (Control.getVeh(Integer.parseInt(vehid)).getStatus().equals("Disponible"))
					&& (!debut.isBefore(LocalDate.now())) && (!fin.isBefore(debut))) {
				System.out.println(
						"Date de début ou de fin de la commande, numéro d'identification du véhicule ou numéro d'identification du client incorrects.");
			}
			if (!debut.isAfter(LocalDate.now())) {
				Factory.addCommande(idCmd, cliid, vehid, "Rendez-vous", date1, date2, "0", "200");
			} else {
				Factory.addCommande(idCmd, cliid, vehid, "En cours", date1, date2, "0", "200");
			}
			Control.getCli(Integer.parseInt(cliid)).setStatus("Loue");
			Control.getVeh(Integer.parseInt(vehid)).setStatus("Occupee");
			System.out.println("Commande créée avec succès.");
		} catch (Exception e) {
			System.out.println("Exception d'entrée: " + e.getMessage());
		}
	}

	public static void modifierReservation(Scanner sc) {
		if (!runSys()) {
			System.out.println("Le système est arrêté.");
			return;
		}
		Control.miseAJour();
		Control.showCmd();
		System.out.println("Veuillez saisir l'ID pour sélectionner la commande que vous souhaitez modifier :");
		try {
			int id = Integer.parseInt(sc.nextLine());
			if (!Control.hasCmd(id)) {
				System.out.println("Exception d'entrée");
				return;
			}
			Control.showCmd(id);
			System.out.println("Veuillez saisir un numéro pour sélectionner l'attribut que vous souhaitez modifier :");
			System.out
					.println("1)date de debut 2)date de fini");
			int choix = Integer.parseInt(sc.nextLine());
			System.out.println("Veuillez saisir de nouveau attribut :");
			switch (choix) {
				case 1:
					LocalDate debut = LocalDate.parse(sc.nextLine());
					if (debut.isBefore(LocalDate.now())) {
						System.out.println("Exception d'entrée");
						break;
					} else if (debut.isEqual(LocalDate.now())) {
						Control.getCmd(id)
								.setDateDebut(Date.from(debut.atStartOfDay(ZoneId.systemDefault()).toInstant()));
						Control.getCmd(id).setstatus("En cours");
					} else {
						Control.getCmd(id)
								.setDateDebut(Date.from(debut.atStartOfDay(ZoneId.systemDefault()).toInstant()));
						Control.getCmd(id).setstatus("Rendez-vous");
					}
					System.out.println("Commande modifiée avec succès.");
					Control.getCmd(id).setGarantie(Control.getCmd(id).getGarantie() - 10);
					break;
				case 2:
					LocalDate fin = LocalDate.parse(sc.nextLine());
					Control.getCmd(id).setDateFin(Date.from(fin.atStartOfDay(ZoneId.systemDefault()).toInstant()));
					System.out.println("Commande modifiée avec succès.");
					Control.getCmd(id).setGarantie(Control.getCmd(id).getGarantie() - 10);
					break;
				default:
					System.out.println("Exception d'entrée");
					break;
			}
		} catch (Exception e) {
			System.out.println("Exception d'entrée: " + e.getMessage());
		}
	}

	public static void retournerVehicule(Scanner sc) {
		if (!runSys()) {
			System.out.println("Le système est arrêté.");
			return;
		}
		Control.miseAJour();
		Control.showCmd();
		System.out.println("Veuillez saisir l'ID pour sélectionner la commande que vous souhaitez finir :");
		try {
			int id = Integer.parseInt(sc.nextLine());
			if (!Control.hasCmd(id)) {
				System.out.println("Exception d'entrée");
				return;
			}
			Control.showCmd(id);
			System.out.println("Veuillez indiquer le nombre de kilomètres dépassant l'allocation gratuite: ");
			Control.getCmd(id).setKilometrage(Integer.parseInt(sc.nextLine()));
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
				System.out.println("Cher client, ");
				System.out.println(
						"Nous vous informons que votre dépôt, après avoir été utilisé pour couvrir les frais supplémentaires, présente un solde de "
								+ Control.getCmd(id).getGarantie() + " dollars. Ce montant vous sera remboursé. ");
				Control.getCmd(id).getCli().setStatus("Ordinaire");
			} else {
				System.out.println(
						"Nous vous informons que votre dépôt a été utilisé pour couvrir les frais supplémentaires et qu'il ne reste plus de solde. Vous avez actuellement une dette de "
								+ (-Control.getCmd(id).getGarantie())
								+ " dollars. Si cette somme n'est pas réglée, votre compte sera bloqué.");
				Control.getCmd(id).getCli().setStatus("Bloque");
				Control.getCmd(id).getCli().setDette((-Control.getCmd(id).getGarantie()));
			}
			Control.getCmd(id).getVeh().setStatus("Disponible");
			Control.getCmd(id).setstatus("Fini");
		} catch (Exception e) {
			System.out.println("Exception d'entrée: " + e.getMessage());
		}
	}

	public static void enregistrerClient(Scanner sc) {
		if (!runSys()) {
			System.out.println("Le système est arrêté.");
			return;
		}
		try {
			System.out.print("Entrer id: ");
			String id = sc.nextLine();
			if (Control.hasCli(Integer.parseInt(id))) {
				System.out.println("Client dont l'ID existe déjà");
				return;
			}
			System.out.print("Entrer nom: ");
			String nom = sc.nextLine();
			System.out.print("Entrer prenom: ");
			String prenom = sc.nextLine();
			System.out.print("Entrer adresse: ");
			String adresse = sc.nextLine();
			System.out.print("Entrer numtele: ");
			String numtele = sc.nextLine();
			System.out.print("Entrer numpermis: ");
			String numpermis = sc.nextLine();
			System.out.print("Entrer numcartes: ");
			String numcartes = sc.nextLine();
			System.out.print("Entrer infomation: ");
			String info = sc.nextLine();
			if (info.equals(""))
				info = " ";
			Factory.addClient(id, nom, prenom, adresse, numtele, numpermis, numcartes, "Ordinaire", info, "0");
		} catch (Exception e) {
			System.out.println("Exception d'entrée: " + e.getMessage());
		}
	}

	public static void modifierClient(Scanner sc) {
		if (!runSys()) {
			System.out.println("Le système est arrêté.");
			return;
		}
		Control.showClients();
		System.out.println("Veuillez saisir l'ID pour sélectionner le client que vous souhaitez modifier :");
		try {
			int id = Integer.parseInt(sc.nextLine());
			if (!Control.hasCli(id)) {
				System.out.println("Exception d'entrée");
				return;
			}
			Control.showClientDetail(id);
			System.out.println("Veuillez saisir un numéro pour sélectionner l'attribut que vous souhaitez modifier :");
			System.out
					.println("1)nom 2)prenom 3)adresse 4)numTele 5)numPermis 6)numCartes 7)Etat 8)Infomation 9)Dette");
			int choix = Integer.parseInt(sc.nextLine());
			System.out.println("Veuillez saisir de nouveau attribut :");
			switch (choix) {
				case 1:
					String nom = sc.nextLine();
					Control.getCli(id).setNom(nom);
					break;
				case 2:
					String prenom = sc.nextLine();
					Control.getCli(id).setPrenom(prenom);
					break;
				case 3:
					String adresse = sc.nextLine();
					Control.getCli(id).setAdresse(adresse);
					break;
				case 4:
					String numtele = sc.nextLine();
					Control.getCli(id).setNumtele(numtele);
					break;
				case 5:
					String permis = sc.nextLine();
					Control.getCli(id).setNumpermis(permis);
					break;
				case 6:
					String cartes = sc.nextLine();
					Control.getCli(id).setNumcartes(cartes);
					break;
				case 7:
					String status = sc.nextLine();
					if (status.equals("Ordinaire") || status.equals("Loue") || status.equals("Retard")
							|| status.equals("Bloque"))
						Control.getCli(id).setStatus(status);
					break;
				case 8:
					String info = sc.nextLine();
					Control.getCli(id).setInfo(info);
					break;
				case 9:
					int dette = Integer.parseInt(sc.nextLine());
					Control.getCli(id).setDette(dette);
					break;
				default:
					System.out.println("Exception d'entrée");
					break;
			}
		} catch (Exception e) {
			System.out.println("Exception d'entrée: " + e.getMessage());
		}
	}

	public static void ListeRetard() {
		if (!runSys()) {
			System.out.println("Le système est arrêté.");
			return;
		}
		Control.miseAJour();
		Control.showClients("Retard");
	}

	public static void demarrerSys() {
		if (runSys()) {
			System.out.println("Le système fonctionne.");
			return;
		}
		Control.changeStatus();
		System.out.println("Le système a démarré.");
	}

	public static void arreterSys() {
		if (!runSys()) {
			System.out.println("Le système est arrêté.");
			return;
		}
		Control.changeStatus();
		System.out.println("Le système est maintenant arrêté.");
	}

	public static void retraiterVehicule(Scanner sc) {
		Control.miseAJour();
		if (!runSys()) {
			System.out.println("Le système est arrêté.");
			return;
		}
		Control.showVeh();

		System.out.println("Veuillez saisir l'ID pour sélectionner le véhicule à retraiter :");
		try {
			int idToRemove = Integer.parseInt(sc.nextLine());
			System.out.println("Le véhicule que vous avez sélectionné est :");
			for (Vehicule veh : Control.getVeh()) {
				if (veh.getIdVehicule() == idToRemove) {
					Control.showVehDetail(idToRemove);
					idToRemove = Control.getVeh().indexOf(veh);
				}
			}
			System.out.println("Veuillez indiquer son état (Entretien / Retraite / Disponible) :");
			String statusRemove = sc.nextLine();
			if (statusRemove.equals("Entretien") || statusRemove.equals("Retraite")
					|| statusRemove.equals("Disponible")) {
				Control.getVeh().get(idToRemove).setStatus(statusRemove);
			} else {
				System.out.println("Entrée non valide.");
			}
		} catch (Exception e) {
			System.out.println("Entrée non valide.");

		}
	}

	public static void enregistrerVehicule(Scanner sc) {
		if (!runSys()) {
			System.out.println("Le système est arrêté.");
			return;
		}
		System.out.print("Entrer idVehicule: ");
		String idVehicule = sc.nextLine();
		if (Control.hasVeh(Integer.parseInt(idVehicule))) {
			System.out.println("Véhicules dont l'ID existe déjà");
			return;
		}
		System.out.print("Entrer kilometrage: ");
		String kilometrage = sc.nextLine();
		System.out.print("Entrer nbplace: ");
		String nbplace = sc.nextLine();
		System.out.print("Entrer marque: ");
		String marque = sc.nextLine();
		System.out.print("Entrer modele: ");
		String modele = sc.nextLine();
		System.out.print("Entrer type: ");
		String type = sc.nextLine();
		try {
			Factory.addVehicule(idVehicule, kilometrage, "Disponible", nbplace, marque, modele, type);
			System.out.println("Nouveau véhicule ajouté avec succès.");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public static void obtenirRapport() {
		if (!runSys()) {
			System.out.println("Le système est arrêté.");
			return;
		}
		System.out.println("Rapport des Clients: ");
		Control.showClients();
		System.out.println("Rapport des Vehucules: ");
		Control.showVeh();
		System.out.println("Rapport des Commandes: ");
		Control.showCmd();
	}

	public static void integrationMenu(Scanner sc) {
		Menus.Menu mainMenu = new Menus.Menu(null, "\nVous voulez utiliser ses fonctions comme :");
		Menus.Menu clinonMenu = new Menus.Menu(mainMenu, "\nSous-menu pour les clients non enregistrés :");
		Menus.Menu clientMenu = new Menus.Menu(mainMenu, "\nSous-menu pour les clients enregistrés :");
		Menus.Menu preposeMenu = new Menus.Menu(mainMenu, "\nSous-menu pour les préposés :");
		Menus.Menu managerMenu = new Menus.Menu(mainMenu, "\nSous-menu pour les manager :");
		Menus.Menu gestionMenu = new Menus.Menu(mainMenu, "\nSous-menu pour les gestionnaires :");
		preposeMenu.addOptions(new Menus.MenuOption("Enregistrer les réservations", "", new Menus.MenuAction() {
			@Override
			public void doAction() {
				enregistrerReservation(sc);
			}
		}), new Menus.MenuOption("Modifier les réservations", "", new Menus.MenuAction() {
			@Override
			public void doAction() {
				modifierReservation(sc);
			}
		}), new Menus.MenuOption("Retourner un véhicule", "", new Menus.MenuAction() {
			@Override
			public void doAction() {
				retournerVehicule(sc);
			}
		}), new Menus.MenuOption("Enregistrer un client", "", new Menus.MenuAction() {
			@Override
			public void doAction() {
				enregistrerClient(sc);
			}
		}), new Menus.MenuOption("Modifier un client", "", new Menus.MenuAction() {
			@Override
			public void doAction() {
				modifierClient(sc);
			}
		}), new Menus.MenuOption("Accéder la liste de retard", "", new Menus.MenuAction() {
			@Override
			public void doAction() {
				ListeRetard();
			}
		}));
		managerMenu.addOptions(new Menus.MenuOption("Démarrer le système", "", new Menus.MenuAction() {
			@Override
			public void doAction() {
				demarrerSys();
			}
		}), new Menus.MenuOption("Arrêter le système", "", new Menus.MenuAction() {
			@Override
			public void doAction() {
				arreterSys();
			}
		}), new Menus.MenuOption("Retraiter de véhicule", "", new Menus.MenuAction() {
			@Override
			public void doAction() {
				retraiterVehicule(sc);
			}
		}));
		gestionMenu.addOptions(new Menus.MenuOption("Enregistrer un véhicule", "", new Menus.MenuAction() {
			@Override
			public void doAction() {
				enregistrerVehicule(sc);
			}
		}), new Menus.MenuOption("Obtenir un rapport", "", new Menus.MenuAction() {
			@Override
			public void doAction() {
				obtenirRapport();
			}
		}));
		mainMenu.addOptions(new Menus.MenuOption("Un client non enregistré", "Vous pouvez consulter les offres",
				new Menus.MenuAction() {
					@Override
					public void doAction() {
						System.out.println("Vous pouvez consulter les offres :");
						consulterOffre();
						clinonMenu.show();
					}
				}), new Menus.MenuOption("Un client enregistré", "Vous pouvez consulter les dossiers.",
						new Menus.MenuAction() {
							@Override
							public void doAction() {
								System.out.println("Vous pouvez consulter les dossiers :");
								consulterDossier();
								clientMenu.show();
							}
						}),
				new Menus.MenuOption("Un préposé", "Vous pouvez effectuer les opérations.", new Menus.MenuAction() {
					@Override
					public void doAction() {
						preposeMenu.show();
					}
				}),
				new Menus.MenuOption("Un manager",
						"Vous pouvez démarrer / arrêter le système et retraiter des véhicules.",
						new Menus.MenuAction() {
							@Override
							public void doAction() {
								managerMenu.show();
							}
						}),
				new Menus.MenuOption("Un gestionnaire", "Vous pouvez enregistrer des véhicules et obtenir un rapport.",
						new Menus.MenuAction() {
							@Override
							public void doAction() {
								gestionMenu.show();
							}
						}));
		mainMenu.show();
	}

}
