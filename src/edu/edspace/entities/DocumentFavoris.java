/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.edspace.entities;

public class DocumentFavoris {
    private int id;
    private User user;
    private Document doc;

    public DocumentFavoris() {
    }

    public DocumentFavoris(User user, Document doc) {
        this.user = user;
        this.doc = doc;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
  

    public Document getDoc() {
        return doc;
    }

    public void setDoc(Document doc) {
        this.doc = doc;
    }

    @Override
    public String toString() {
        return "DocumentFavoris{" + "id=" + id + ", user=" + user + ", doc=" + doc + '}';
    }
    
}
