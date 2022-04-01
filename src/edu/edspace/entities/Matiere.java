/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.edspace.entities;

public class Matiere {
    private String id,niveau;

    public Matiere() {
    }

    public Matiere(String id, String niveau) {
        this.id = id;
        this.niveau = niveau;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }
    
    

    @Override
    public String toString() {
        return "Matiere{" + "id=" + id + ", niveau=" + niveau + '}';
    }
    
    
}
