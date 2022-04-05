/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.edspace.entities;

import java.util.Date;

/**
 *
 * @author aa
 */
public class Message {
      
    private int id;
    private String content;
    private Date postDate;

    private Classe classe;
    private User user;
    
 
    public Message() {
        
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setPostDate(Date string) {
        this.postDate = string;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setClasse(Classe classe) {
        this.classe = classe;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public Date getPostDate() {
        return postDate;
    }

    public Classe getClasse() {
        return classe;
    }

	public Message(int id, String content, Date postDate, Classe classe, User user) {
		super();
		this.id = id;
		this.content = content;
		this.postDate = postDate;
		this.classe = classe;
		this.user = user;
	}

	@Override
	public String toString() {
		return "Message [id=" + id + ", content=" + content + ", postDate=" + postDate + ", classe=" + classe
				+ ", user=" + user + "]";
	}

	public User getUser() {
        return user;
    }

    
}
