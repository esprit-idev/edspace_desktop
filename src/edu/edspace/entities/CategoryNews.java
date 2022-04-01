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
public class CategoryNews {
    private int id;
    private String categoryName;

    public CategoryNews() {
    }

    public CategoryNews(int id, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
    }

    public CategoryNews(String categoryName) {
        this.categoryName = categoryName;
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

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    
}
