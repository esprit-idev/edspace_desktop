/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.edspace.entities;

import java.util.ArrayList;

/**
 *
 * @author 21656
 */
public class Thread {
    String id;
    String question;
    int nb_likes;
    String postDate;
    boolean display;
    String threadType;
    int user;
    String verified;

    public String getVerified() {
        return verified;
    }

    public void setVerified(String verified) {
        this.verified = verified;
    }

    public int getUser() {
        return 1;
    }

    public void setUser(int user) {
        this.user = user;
    }
    ArrayList<Reponse> reponses;

    public ArrayList<Reponse> getReponses() {
        return reponses;
    }

    public void setReponses(ArrayList<Reponse> reponses) {
        this.reponses = reponses;
    }
    public Thread(){
        
    }
    
    public Thread(String id,String question, int nb_likes, String postDate, String threadType) {
        this.id = id;
        this.question = question;
        this.nb_likes = nb_likes;
        this.postDate = postDate;
        this.threadType = threadType;
        
    }
    

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getNb_likes() {
        return nb_likes;
    }

    public void setNb_likes(int nb_likes) {
        this.nb_likes = nb_likes;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getThreadType() {
        return threadType;
    }

    public void setThreadType(String threadType) {
        this.threadType = threadType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    

    public boolean isDisplay() {
        return display;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }
}
