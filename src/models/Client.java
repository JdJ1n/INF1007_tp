package models;

public class Client {
    private int id;
    private String nom;
    private String prenom;
    private String adresse;
    private String numtele;
    private String numpermis;
    private String numcartes;
    private String info;
    private String status;
    private double dette;

    public Client(int id, String nom, String prenom, String adresse, String numtele, String numpermis, String numcartes,
            String status, String info, double dette) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.numtele = numtele;
        this.numpermis = numpermis;
        this.numcartes = numcartes;
        this.info = info;
        this.status = status;
        this.dette = dette;
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

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getNumtele() {
        return numtele;
    }

    public void setNumtele(String numtele) {
        this.numtele = numtele;
    }

    public String getNumpermis() {
        return numpermis;
    }

    public void setNumpermis(String numpermis) {
        this.numpermis = numpermis;
    }

    public String getNumcartes() {
        return numcartes;
    }

    public void setNumcartes(String numcartes) {
        this.numcartes = numcartes;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getDette() {
        return dette;
    }

    public void setDette(double dette) {
        this.dette = dette;
    }

    public void addDette(int add) {
        this.dette += add;
    }
}
