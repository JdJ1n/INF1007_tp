package models;

import java.util.Date;

public class Commande {
	private int id;
	private Client cli;
	private Vehicule veh;
	private String status;
	private Date datedebut;
	private Date datefin;
	private int kilometrage;
	private double garantie;

	public Commande(int id, Client cli, Vehicule veh, String status, Date datedebut, Date datefin, int kilometrage,
			double garantie) {
		this.id = id;
		this.cli = cli;
		this.veh = veh;
		this.status = status;
		this.datedebut = datedebut;
		this.datefin = datefin;
		this.kilometrage = kilometrage;
		this.garantie = garantie;
	}

	public void updateStatus() {
		// DateDebut et DateFin définissent l'état approprié
		if (datedebut != null && datefin != null) {
			if (datedebut.before(new Date()) && datefin.after(new Date())) {
				setstatus("En cours");
			} else if (datedebut.after(new Date())) {
				setstatus("Rendez-vous");
			} else if (datefin.before(new Date())) {
				setstatus("Fini");
			}
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Client getCli() {
		return cli;
	}

	public void setCli(Client cli) {
		this.cli = cli;
	}

	public Vehicule getVeh() {
		return veh;
	}

	public void setVeh(Vehicule veh) {
		this.veh = veh;
	}

	public String getstatus() {
		return status;
	}

	public void setstatus(String status) {
		this.status = status;
	}

	public Date getDateDebut() {
		return datedebut;
	}

	public void setDateDebut(Date date) {
		this.datedebut = date;
	}

	public Date getDateFin() {
		return datefin;
	}

	public void setDateFin(Date date) {
		this.datefin = date;
	}

	public int getKilometrage() {
		return kilometrage;
	}

	public void setKilometrage(int kilometrage) {
		this.kilometrage = kilometrage;
	}

	public double getGarantie() {
		return garantie;
	}

	public void setGarantie(double garantie) {
		this.garantie = garantie;
	}

	public void setGarantie(String aValue) {
	}

	public void setVeh(String aValue) {
	}

	public void setCli(String aValue) {
	}
}
