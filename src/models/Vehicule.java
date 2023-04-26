package models;

//(type, moteur, accessoires, nombre de places, disponibilité, etc.)
//simple, prestige ou utilitaire
public class Vehicule {
	private int idVehicule, kilometrage;
	private String status;
	private Specification specification;

	public Vehicule(int idVehicule, int kilometrage, String status, Specification specification) {
		this.idVehicule = idVehicule;
		this.kilometrage = kilometrage;
		this.status = status;
		this.specification = specification;
	}

	public int getIdVehicule() {
		return idVehicule;
	}

	public void setIdVehicule(int idVehicule) {
		this.idVehicule = idVehicule;
	}

	public int getKilometage() {
		return kilometrage;
	}

	public void setKilometage(int kilometrage) {
		this.kilometrage = kilometrage;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Specification getSpe() {
		return specification;
	}

	public String getType() {
		return specification.getType();
	}

	public String getModele() {
		return specification.getModele();
	}

	public String getMarque() {
		return specification.getMarque();
	}

	public int getNbplace() {
		return specification.getNbplace();
	}
}
