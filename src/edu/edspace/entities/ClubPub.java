/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.edspace.entities;

import java.util.Date;

/**
 *
 * @author anash
 */
public class ClubPub {

    String id, pubDesc, pubImage, pubFile, pubDate;
    int Club, is_posted;

    public ClubPub() {
    }

    public ClubPub( String pubDesc, String pubImage, String pubFile, String pubDate, int Club, int is_posted) {
        
        this.pubDesc = pubDesc;
        this.pubImage = pubImage;
        this.pubFile = pubFile;
        this.pubDate = pubDate;
        this.Club = Club;
        this.is_posted = is_posted;

    }

    public String getId() {
        return id;
    }

    public int getIs_posted() {
        return is_posted;
    }

    public void setIs_posted(int is_posted) {
        this.is_posted = is_posted;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPubDesc() {
        return pubDesc;
    }

    public void setPubDesc(String pubDesc) {
        this.pubDesc = pubDesc;
    }

    public String getPubImage() {
        return pubImage;
    }

    public void setPubImage(String pubImage) {
        this.pubImage = pubImage;
    }

    public String getPubFile() {
        return pubFile;
    }

    public void setPubFile(String pubFile) {
        this.pubFile = pubFile;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public int getClub() {
        return Club;
    }

    public void setClub(int Club) {
        this.Club = Club;
    }

    @Override
    public String toString() {
        return "ClubPub{" + "id=" + id + ", pubDesc=" + pubDesc + ", pubImage=" + pubImage + ", pubFile=" + pubFile + ", pubDate=" + pubDate + ", Club=" + Club + "}\n";
    }

}
