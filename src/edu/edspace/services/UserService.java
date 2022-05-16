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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
        String req = "SELECT * FROM user WHERE email=? ";
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
            
          String myPwd="$2a"+rs.getString("password").substring(3);
           System.out.println(myPwd);
            if (BCrypt.checkpw(Pwd, myPwd)) {
                Session.setId(rs.getInt("id"));
                Session.setUsername(rs.getString("username"));
                Session.setPrenom(rs.getString("prenom"));
                Session.setEmail(rs.getString("email"));
                Session.setRoles(rs.getString("roles"));
                Session.setClasse_id(rs.getString("classe_id"));

                System.out.println("login ");
                return true;
            }
           // return true;
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

    
   public User getUser(String email){
        User stu = new User();
        try {
             String req = "select * from user where email ='"+email+"'";
            Statement st = MyConnection.getInstance().getCnx().createStatement(); //instance of myConnection pour etablir la cnx
            ResultSet rs = st.executeQuery(req); //resultat de la requete
            
            //tant que rs has next get matiere and add it to the list
            while (rs.next()) {
                
                stu.setId(rs.getInt("id")); //set id from req result
                stu.setUsername(rs.getString("username")); 
                stu.setPrenom(rs.getString("prenom")); 
                stu.setEmail(rs.getString("email")); 
                stu.setPassword(rs.getString("password")); 
                
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            
        }
        return stu ;
    }
    
    public User find(int id){
        User ad = new User();
        try {
            String req = "select * from user where id ='"+id+"'";
            Statement st = MyConnection.getInstance().getCnx().createStatement(); //instance of myConnection pour etablir la cnx
            ResultSet rs = st.executeQuery(req); //resultat de la requete
            
            //tant que rs has next get matiere and add it to the list
            while (rs.next()) {
                
                ad.setId(rs.getInt("id")); //set id from req result
                ad.setUsername(rs.getString("username")); 
                ad.setPrenom(rs.getString("prenom")); 
                ad.setEmail(rs.getString("email")); 
                ad.setPassword(rs.getString("password")); 
                
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            
        }
        return ad ;
    }

   public  int existence (String email){
        int f=0;
       try{
            
            ObservableList<User> blogsList = FXCollections.observableArrayList();
            // Connection conn = getConnection();
            String query = "select * from user where email =  '"+email+"';";
            Statement st = MyConnection.getInstance().getCnx().createStatement(); //instance of myConnection pour etablir la cnx
            ResultSet rs = st.executeQuery(query);
            if(rs.next() == false){
                f= 1;
            }
            else f= 0;
            /*try{
                
                User user;
                while(rs.next()){
                    user = new User(rs.getString("id"), rs.getString("username"), rs.getString("prenom"),rs.getString("email"));
                    //blogsList.add(user);
                    
                    if(user.getEmail().equals(email)){                    
                        f=1;   
                    }
                }
                
            }catch(Exception ex){
                ex.printStackTrace();
            }*/
            // System.out.println(f);
           
        }catch(SQLException ex){
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return f;
    
   }

   
   public void updatemdp(User stu, String email) {
        String req = "update user set password=?  WHERE email=?";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req);
           
            pst.setString(1, BCrypt.hashpw(stu.getPassword(), BCrypt.gensalt(13)));
            
            pst.setString(2, email);
            pst.executeUpdate();
            System.out.println("Password updated");
                        System.out.println(stu.getPassword());

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
   
  public Boolean logout()  {
      Session cur = new Session();
       String req = "SELECT * FROM user where id="+ cur.getId();
      try {
            
            PreparedStatement pre = cnx.prepareStatement(req);
            // pre.setString(1, userName);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
             //   if () {
                    Session.setId(0);
                    Session.setUsername(null);
                    Session.setPrenom(null);
                    Session.setEmail(null);
                   // Session.setRole(rs.getString("roles"));
                    return true;
               // }
            }
           
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
      System.out.println(Session.getId()+Session.getEmail()+Session.getUsername());
       return false;
  }
  

}
