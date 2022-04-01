/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.edspace.entities;

/**
 *
 * @author Eslem Nabitt
 */
public class CategoryEmploi {
    private int id;
    private String catgeoryName;

    public CategoryEmploi() {
    }

    public CategoryEmploi(int id, String catgeoryName) {
        this.id = id;
        this.catgeoryName = catgeoryName;
    }

    public CategoryEmploi(String catgeoryName) {
        this.catgeoryName = catgeoryName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCatgeoryName() {
        return catgeoryName;
    }

    public void setCatgeoryName(String catgeoryName) {
        this.catgeoryName = catgeoryName;
    }
    
}
