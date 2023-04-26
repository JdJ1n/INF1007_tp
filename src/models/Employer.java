package models;

public class Employer {
    private int id;
    private String nom;
    private String prenom;
    private String motdepasse;

    public Employer(int id, String nom, String prenom, String motdepasse) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.motdepasse = motdepasse;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMotdepasse() {
        return motdepasse;
    }

    public void setMotdepasse(String motdepasse) {
        this.motdepasse = motdepasse;
   }
}
