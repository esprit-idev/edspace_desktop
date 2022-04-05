/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.edspace.entities;

/**
 *
 * @author 21656
 */
public class Reponse {
    String id;
    String reply;
    String replyDate;
    int thread_id;
    boolean display;
    int user_id;

    public int getUser() {
        return user_id;
    }

    public void setUser(int user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "Reponse{" + "id=" + id + ", reply=" + reply + ", replyDate=" + replyDate + ", thread_id=" + thread_id + ", display=" + display + ", user_id=" + user_id + '}';
    }

    
    

    public boolean isDisplay() {
        return display;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }
    
    

    public Reponse() {
        
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getReplyDate() {
        return replyDate;
    }

    public void setReplyDate(String replyDate) {
        this.replyDate = replyDate;
    }

    public int getThread() {
        return thread_id;
    }

    public void setThread(int thread) {
        this.thread_id = thread;
    }

    public Reponse(String reply,int thread,int user_id) {
        
        this.reply = reply;
        
        this.thread_id = thread;
        this.user_id = user_id;
    }
   
}
