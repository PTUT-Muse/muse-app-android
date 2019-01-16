package com.example.lpiem.muse_app_android.data.model;

public class Capture {
    private int id;
    private int etat;
    private String titre;
    private String temps;
    private String description;
    private String date;

    public Capture(int id, int etat, String titre, String description, String date, String temps) {
        this.etat = etat;
        this.titre = titre;
        this.description = description;
        this.date = date;
        this.temps = temps;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTemps() {
        return temps;
    }

    public void setTemps(String temps) {
        this.temps = temps;
    }

    public String getDescription() {
        return description;
    }
}
