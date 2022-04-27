/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.edspace.entities;

public class Niveau {
    private String id;
      private int nbClasse;

    public int getNbClasse() {
        return nbClasse;
    }

    /*public void setNbClasse(int nbClasse) {
        this.nbClasse = nbClasse;
    }*/
    
    public void setNbClasse(int nbClasse) {
        this.nbClasse = nbClasse;
    }
    

    public Niveau(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Niveau{" + "id=" + id + ", nbClasse=" + nbClasse + '}';
    }

	
	public Niveau() {
	}
}
