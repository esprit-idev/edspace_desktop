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
public class Emploi {
    private int id;
    private String title,content,categoryName,date, image;

    public Emploi() {
    }

    public Emploi(int id, String title, String content, String categoryName, String date, String image) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.categoryName = categoryName;
        this.date = date;
        this.image = image;
    }

    public Emploi(String title, String content, String categoryName, String date, String image) {
        this.title = title;
        this.content = content;
        this.categoryName = categoryName;
        this.date = date;
        this.image = image;
    }
    public Emploi(String title, String content,String date, String image) {
        this.title = title;
        this.content = content;
        this.date = date;
        this.image = image;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    @Override
    public String toString() {
        return "Emploi{" + "id=" + id + ", Title=" + title + ", date=" + date+ ", content=" + content +"\n" + "categoryName=" + categoryName + ", image=" + image + '}' + "\n";
    }
}
