/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.edspace.entities;

public class Document {
    private int id,signalements;
    private String nom,date_insert,prop,url,base64;
    private Niveau niveau;
    private Matiere matiere;

    public Document() {
    }

    public Document(int signalements, String nom, String date_insert, String prop, String url, String base64, Niveau niveau, Matiere matiere) {
        this.signalements = signalements;
        this.nom = nom;
        this.date_insert = date_insert;
        this.prop = prop;
        this.url = url;
        this.base64 = base64;
        this.niveau = niveau;
        this.matiere = matiere;
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

    public String getDate_insert() {
        return date_insert;
    }

    public void setDate_insert(String date_insert) {
        this.date_insert = date_insert;
    }

    public String getProp() {
        return prop;
    }

    public void setProp(String prop) {
        this.prop = prop;
    }

    public Matiere getMatiere() {
        return matiere;
    }

    public void setMatiere(Matiere matiere) {
        this.matiere = matiere;
    }

    public Niveau getNiveau() {
        return niveau;
    }

    public void setNiveau(Niveau niveau) {
        this.niveau = niveau;
    }

    
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public int getSignalements() {
        return signalements;
    }

    public void setSignalements(int signalements) {
        this.signalements = signalements;
    }

    @Override
    public String toString() {
        return "Document{" + "id=" + id + ", signalements=" + signalements + ", nom=" + nom + ", date_insert=" + date_insert + ", prop=" + prop + ", url=" + url + ", base64=" + base64 + ", niveau=" + niveau + ", matiere=" + matiere + '}';
    }
    
}
