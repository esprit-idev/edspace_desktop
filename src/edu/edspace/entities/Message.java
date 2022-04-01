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
public class Message {
      
    private int id;
    private String content;
    private String postDate;

    private int classe;
    private int user;
    
 
    public Message() {
        
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public void setClasse(int classe) {
        this.classe = classe;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getPostDate() {
        return postDate;
    }

    public int getClasse() {
        return classe;
    }

    public int getUser() {
        return user;
    }

    
}
