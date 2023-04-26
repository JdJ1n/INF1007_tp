package controllers;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

//import java.util.Arrays;

import models.*;

public class Factory {

	private static Factory instance = new Factory();

	private Factory() {
	}

	public static Factory getInstance() {
		return instance;
	}

	public static Vehicule getVehicule(int idVehicule, int kilometrage, String status, int nbplace, String marque,
			String modele, String type) {
		return new Vehicule(idVehicule, kilometrage, status, new Specification(nbplace, marque, modele, type));
	}

	public static Commande getCommande(int idCmd, Client cli, Vehicule veh, String status, String date1, String date2) {
		return new Commande(idCmd, cli, veh, status, stringToDate(date1), stringToDate(date2), 0, 200);
	}

	public static Client getClient(int id, String nom, String prenom, String adresse, String numtele,
			String numpermis, String numcartes, String status, String info, double dette) {
		return new Client(id, nom, prenom, adresse, numtele, numpermis, numcartes, status, info,
				dette);
	}

	public static void addVehicule(String idVehicule, String kilometrage, String status, String nbplace,
			String marque, String modele, String type) {
		Vehicule veh = new Vehicule(Integer.parseInt(idVehicule), Integer.parseInt(kilometrage), status,
				new Specification(Integer.parseInt(nbplace), marque, modele, type));
		Control.addVeh(veh);
	}

	public static void addCommande(String idCmd, String cliid, String vehid, String status, String date1, String date2,
			String kilometrage, String garantie) {
		Vehicule veh = Control.getVeh(Integer.parseInt(vehid));
		Client cli = Control.getCli(Integer.parseInt(cliid));
		Commande cmd = new Commande(Integer.parseInt(idCmd), cli, veh, status, stringToDate(date1), stringToDate(date2),
				Integer.parseInt(kilometrage), Double.parseDouble(garantie));
		Control.addCmd(cmd);
	}

	public static void addClient(String id, String nom, String prenom, String adresse, String numtele,
			String numpermis, String numcartes, String status, String info, String dette) {
		Client cli = new Client(Integer.parseInt(id), nom, prenom, adresse, numtele, numpermis, numcartes, status, info,
				Double.parseDouble(dette));
		Control.addCli(cli);
	}

	public static Date stringToDate(String strDate) {
		// yyyy-MM-dd
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date = formatter.parse(strDate);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String dateToString(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = formatter.format(date);
		return strDate;
	}

	public static long dateBetween(Date date) {
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		long daysBetween = ChronoUnit.DAYS.between(localDate, LocalDate.now());
		return daysBetween;
	}

	public static long hourBetween(Date date) {
		LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		long hoursBetween = ChronoUnit.HOURS.between(localDateTime, LocalDateTime.now());
		return hoursBetween;
	}
}
