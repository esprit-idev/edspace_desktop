/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.edspace.entities;

/**
 *
 * @author anash
 */
public class ClubCategory {
    private String categorie,id;

    public ClubCategory() {
    }

    public ClubCategory(String categorie) {
        this.categorie = categorie;
       
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    @Override
    public String toString() {
        return "\nClubCategory{ " + "categorie=" + categorie + ", id=" + id + "}";
    }

    
    
    
}
