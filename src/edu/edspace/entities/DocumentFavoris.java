/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.edspace.entities;

public class DocumentFavoris {
    private int id,user_id,document_id;

    public DocumentFavoris() {
    }

    public DocumentFavoris(int user_id, int document_id) {
        this.user_id = user_id;
        this.document_id = document_id;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDocument_id() {
        return document_id;
    }

    public void setDocument_id(int document_id) {
        this.document_id = document_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DocumentFavoris other = (DocumentFavoris) obj;
        if (this.user_id != other.user_id) {
            return false;
        }
        return this.document_id == other.document_id;
    }

    @Override
    public String toString() {
        return "DocumentFavoris{" + "id=" + id + ", user_id=" + user_id + ", document_id=" + document_id + '}';
    }

    
}
