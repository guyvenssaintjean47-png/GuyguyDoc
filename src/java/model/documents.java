package model;

import java.sql.Timestamp;

public class documents {

    private int id;                    
    private int userId;                 
    private String nomFichier;           
    private String typeAvant;            
    private String typeApres;           
    private Timestamp dateConversion;    
    private String nomFichierConverti;   

    // Constructeur vide
    public documents() {}

    // Constructeur complet (avec id et dateConversion)
    public documents(int id, int userId, String nomFichier, String typeAvant, String typeApres, Timestamp dateConversion, String nomFichierConverti) {
        this.id = id;
        this.userId = userId;
        this.nomFichier = nomFichier;
        this.typeAvant = typeAvant;
        this.typeApres = typeApres;
        this.dateConversion = dateConversion;
        this.nomFichierConverti = nomFichierConverti;
    }

    // Constructeur utilisé pour l'ajout d'une conversion (sans id et date)
    public documents(int userId, String nomFichier, String typeAvant, String typeApres, String nomFichierConverti) {
        this.userId = userId;
        this.nomFichier = nomFichier;
        this.typeAvant = typeAvant;
        this.typeApres = typeApres;
        this.nomFichierConverti = nomFichierConverti;
    }

    // Constructeur optionnel sans nomFichierConverti (ancienne version)
    public documents(int userId, String nomFichier, String typeAvant, String typeApres) {
        this.userId = userId;
        this.nomFichier = nomFichier;
        this.typeAvant = typeAvant;
        this.typeApres = typeApres;
    }

    // ----- Getters et Setters -----
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getNomFichier() { return nomFichier; }
    public void setNomFichier(String nomFichier) { this.nomFichier = nomFichier; }

    public String getTypeAvant() { return typeAvant; }
    public void setTypeAvant(String typeAvant) { this.typeAvant = typeAvant; }

    public String getTypeApres() { return typeApres; }
    public void setTypeApres(String typeApres) { this.typeApres = typeApres; }

    public Timestamp getDateConversion() { return dateConversion; }
    public void setDateConversion(Timestamp dateConversion) { this.dateConversion = dateConversion; }

    public String getNomFichierConverti() { return nomFichierConverti; }
    public void setNomFichierConverti(String nomFichierConverti) { this.nomFichierConverti = nomFichierConverti; }
}
