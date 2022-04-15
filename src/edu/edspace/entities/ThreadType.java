/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.edspace.entities;
import edu.edspace.entities.Thread;
/**
 *
 * @author 21656
 */
public class ThreadType {

    @Override
    public String toString() {
        return "ThreadType{" + "id=" + id + ", content=" + content + ", display=" + display  + "; \n";
    }
    String id,content;
    boolean display;
    

    public ThreadType() {
       
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean getDisplay() {
        return display;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }

    public ThreadType(String content, boolean display) {
        this.content = content;
        this.display = display;
        
    }

    
}
