/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.edspace.entities;

/**
 *
 * @author aa
 */
public class Classe {
    private String id;
    private String niveau;
    private String classe;

    public Classe(String id, String niveau, String classe) {
        this.id = id;
        this.niveau = niveau;
        this.classe = classe;
    }

    
    
    
    public String getId() {
        return id;
    }

    public String getNiveau() {
        return niveau;
    }

    public String getClasse() {
        return classe;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }
    
    
    
    
}
