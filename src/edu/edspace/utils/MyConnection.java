/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.edspace.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author MeriamBI
 */
public class MyConnection {
    private String url="jdbc:mysql://localhost:3306/edspace";
    private String login="root";
    private String pwd="";

    Connection cnx;
    public static MyConnection instance; //singleton
    
    //private constructor, use of static var instance instead
    private MyConnection() {
        try {
            cnx=DriverManager.getConnection(url,login,pwd);
            System.out.println("cnx établie");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    //getter of the instance to use instead of the constructor since it is private (raisons de sécurité)
    public static MyConnection getInstance() {
        if(instance==null){
            instance=new MyConnection();
        }
        return instance;
    }

    
    public Connection getCnx() {
        return cnx;
    }
    
    
}
