package models;

import java.util.Objects;

public class Specification {
	private int nbplace;
	private String marque, type, modele;

	public Specification(int nbplace, String marque, String modele, String type) {
		this.nbplace = nbplace;
		this.marque = marque;
		this.modele = modele;
		this.type = type;
	}

	public int getNbplace() {
		return nbplace;
	}

	public void setNbplace(int nbplace) {
		this.nbplace = nbplace;
	}

	public String getMarque() {
		return marque;
	}

	public void setMarque(String marque) {
		this.marque = marque;
	}

	public String getModele() {
		return modele;
	}

	public void setModele(String modele) {
		this.modele = modele;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Specification))
			return false;
		Specification sp = (Specification) o;
		return getNbplace() == sp.getNbplace() && getMarque().equals(sp.getMarque())
				&& getModele().equals(sp.getModele()) && getType().equals(sp.getType());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getNbplace(), getMarque(), getModele(), getType());
	}

}
