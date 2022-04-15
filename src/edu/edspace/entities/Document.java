/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.edspace.entities;

import java.io.File;

public class Document {
    private int id,signalements;
    private String nom,date_insert,prop,url,niveau,matiere,type;
    
    public Document() {
    }

    public Document(int signalements, String nom, String date_insert, String prop, String url, String niveau, String matiere, String type) {
        this.signalements = signalements;
        this.nom = nom;
        this.date_insert = date_insert;
        this.prop = prop;
        this.url = url;
        this.niveau = niveau;
        this.matiere = matiere;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getMatiere() {
        return matiere;
    }

    public void setMatiere(String matiere) {
        this.matiere = matiere;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getSignalements() {
        return signalements;
    }

    public void setSignalements(int signalements) {
        this.signalements = signalements;
    }

    @Override
    public String toString() {
        return "Document{" + "id=" + id + ", signalements=" + signalements + ", nom=" + nom + ", date_insert=" + date_insert + ", prop=" + prop + ", url=" + url + ", niveau=" + niveau + ", matiere=" + matiere + ", type=" + type + '}';
    }
    
    
}
