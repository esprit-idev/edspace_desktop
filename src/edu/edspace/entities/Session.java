/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.edspace.entities;

/**
 *
 * @author YOOSURF
 */
public class Session {
    private static int id = 0;
    private static String username;
    private static String prenom;
    private static String email;
    private static String roles;
    private static String password;

    private static String id_Lo;

    public static String getId_Lo() {
        return id_Lo;
    }

    public static void setId_Lo(String id_Lo) {
        Session.id_Lo = id_Lo;
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        Session.id = id;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        Session.username = username;
    }
 public static String getPrenom() {
        return prenom;
    }

    public static void setPrenom(String prenom) {
        Session.prenom = prenom;
    }
    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        Session.email = email;
    }

    public static String getRoles() {
        return roles;
    }

    public static void setRoles(String roles) {
        Session.roles = roles;
    }
     public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        Session.password = password;
    }
}
