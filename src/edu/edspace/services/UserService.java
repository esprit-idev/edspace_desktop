/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.edspace.services;

import edu.edspace.entities.Session;
import edu.edspace.utils.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

    
    
    public void login(String userName, String Pwd)  {
        try {
        String req = "SELECT * FROM user WHERE username=?";
        PreparedStatement pre = cnx.prepareStatement(req);
        pre.setString(1, userName);
        ResultSet rs = pre.executeQuery();
        while (rs.next()) {
            if (BCrypt.checkpw(Pwd, rs.getString("password"))) {
                Session.setId(rs.getInt("id"));
                Session.setUsername(rs.getString("username"));
                Session.setEmail(rs.getString("email"));
                Session.setRoles(rs.getString("roles"));
                System.out.println("login");
            }
            else{ 
                System.out.println("Verifier votre mot de passe et votre adresse mail");
            }
        }
       
    
         } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    
}
}
