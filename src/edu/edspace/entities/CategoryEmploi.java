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
    private String categoryName;

    public CategoryEmploi() {
    }

    public CategoryEmploi(int id, String catgeoryName) {
        this.id = id;
        this.categoryName = catgeoryName;
    }

    public CategoryEmploi(String catgeoryName) {
        this.categoryName = catgeoryName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String catgeoryName) {
        this.categoryName = catgeoryName;
    }
    @Override
    public String toString(){
        return  categoryName;
    }
}
