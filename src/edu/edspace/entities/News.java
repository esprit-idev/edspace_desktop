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
public class News {
    private int id, views, likes;
    private String title, owner, content, comments, categoryName, date, image;
    public News() {
    }

    public News(int id, int views, int likes, String title, String owner, String content, String comments, String categoryName, String date,String image) {
        this.id = id;
        this.views = views;
        this.likes = likes;
        this.title = title;
        this.owner = owner;
        this.content = content;
        this.comments = comments;
        this.categoryName = categoryName;
        this.date = date;
        this.image = image;
    }

    public News(int views, int likes, String title, String owner, String content, String comments, String categoryName, String date) {
        this.views = views;
        this.likes = likes;
        this.title = title;
        this.owner = owner;
        this.content = content;
        this.comments = comments;
        this.categoryName = categoryName;
        this.date = date;
    }

    public News(String title, String owner, String content, String categoryName, String date, String image) {
        this.title = title;
        this.owner = owner;
        this.content = content;
        this.categoryName = categoryName;
        this.date = date;
        this.image = image;
    }
    public News(String title, String owner, String content,String date, String image) {
        this.title = title;
        this.owner = owner;
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

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
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
        return "Publication News{" + "id=" + id + ", Title=" + title + ", owner=" + owner + ", date=" + date+ ", content=" + content +"\n" + "categoryName=" + categoryName + ", image=" + image + '}' + "\n";
    }
    public int incrementLikes(){
        return likes = likes+1;
     }
}
