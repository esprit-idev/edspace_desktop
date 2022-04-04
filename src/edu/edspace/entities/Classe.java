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
    private int id;
    private Niveau niveau;
    private String classe;

    public Classe(int id, Niveau niveau, String classe) {
        this.id = id;
        this.niveau = niveau;
        this.classe = classe;
    }

    
    
    
    public Classe() {
		
	}




	public int getId() {
        return id;
    }

    public Niveau getNiveau() {
        return niveau;
    }

    public String getClasse() {
        return classe;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNiveau(Niveau niveau) {
        this.niveau = niveau;
    }

    @Override
	public String toString() {
		return "Classe [id=" + id + ", niveau=" + niveau + ", classe=" + classe + "]";
	}




	public void setClasse(String classe) {
        this.classe = classe;
    }
    
    
    
    
}
