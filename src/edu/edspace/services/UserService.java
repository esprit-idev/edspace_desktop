/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.edspace.services;

import edu.edspace.entities.Session;
import edu.edspace.entities.User;
import edu.edspace.utils.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author YOOSURF
 */
public class UserService {
    
    public Connection cnx = MyConnection.getInstance().getCnx();
    public Statement st;

    public UserService() {
        try {
            st = cnx.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    
    public Boolean login(String email, String Pwd)  {
        try {
        String req = "SELECT * FROM user WHERE email=?";
        PreparedStatement pre = cnx.prepareStatement(req);
        pre.setString(1, email);
        ResultSet rs = pre.executeQuery();
        while (rs.next()) {
            
           /* String myPwd="$2y"+rs.getString("password").substring(3);
            if (BCrypt.checkpw(Pwd, myPwd)) {
                Session.setId(rs.getInt("id"));
                Session.setUsername(rs.getString("username"));
                Session.setEmail(rs.getString("email"));
                Session.setRoles(rs.getString("roles"));
                System.out.println("login ");
            }*/
            
            if (BCrypt.checkpw(Pwd, rs.getString("password"))) {
                Session.setId(rs.getInt("id"));
                Session.setUsername(rs.getString("username"));
                Session.setEmail(rs.getString("email"));
                Session.setRoles(rs.getString("roles"));
                System.out.println("login ");
            }
            return true;
            }
        
        
       
    
         } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
}
    
    public void Exist(String username, String id){
        try {
        String req = "SELECT * FROM user WHERE username=? and id=?";
        PreparedStatement pre = cnx.prepareStatement(req);
        pre.setString(1, username);
       // pre.setBoolean(2, true);
        pre.setString(2, id);
        ResultSet rs = pre.executeQuery();
        while (rs.next()) {
            System.out.println("Exist" + rs.getString("id"));
            System.out.println("deconnecter");

        }
        System.out.println("erreur");
    
}catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    
}
    
    
    public void updateProfil(Session stu, String id) {
        String req = "update user set username=?, prenom=?, password=?  WHERE id=?";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req);
            pst.setString(1, stu.getUsername());
            pst.setString(2 ,stu.getPrenom());
            pst.setString(3, stu.getPassword());
            pst.setString(4, id);
            pst.executeUpdate();
            System.out.println("Profil updated");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    
    public void export(){
    try{
    String query = "select * from user" ;
     PreparedStatement pre = cnx.prepareStatement(query);
     ResultSet rs = pre.executeQuery();
    }catch (SQLException ex){
    System.out.println("hello");
    }
    }

   


    }
