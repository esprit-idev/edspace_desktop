/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.edspace.services;

import edu.edspace.entities.User;
import edu.edspace.utils.MyConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author YOOSURF
 */
public class StudentService {
    
    public void ajouterStudent(User stu) {
        
           try {
            String req = "Insert into user values"
                    + "(?,?,?,?,?,?,?,?,?,?,?,?)"; //requete d'inertion avec parametres

            /*PreparedStatement used with dynamic requests+faster and more secure than Statement (used in the method above)
                can't drop or alter with PreparedStatement*/
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req); //instance of myconnection pour etablir la cnx
            pst.setString(1, null);
            pst.setString(2,null);
            pst.setString(3, null);
            pst.setString(4, stu.getUsername()); 
            pst.setString(5, stu.getPrenom()); 
            pst.setString(6, stu.getEmail());
            pst.setString(7,BCrypt.hashpw(stu.getPassword(), BCrypt.gensalt(13)));
            pst.setBoolean(8 , false);
            pst.setString(9,null);
            pst.setString(10,"[\"ROLE_STUDENT\"]");
            pst.setString(11,null);
            pst.setString(12,null);
            pst.executeUpdate(); //pour exécuter la requete
            System.out.println("Etudiant ajoutée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
   public void AjouterUser(User u)  {
        try {
        String req = "insert into user(username, prenom ,email ,password,roles) values(?,?,?,?,?)";
        PreparedStatement pre = MyConnection.getInstance().getCnx().prepareStatement(req);
        pre.setString(1, u.getUsername());
       // pre.setString(2, u.getUsername().toLowerCase());
        pre.setString(2, u.getPrenom());
        pre.setString(3, u.getEmail());

       // pre.setString(4, u.getEmail().toLowerCase());
        pre.setString(4, BCrypt.hashpw(u.getPassword(), BCrypt.gensalt(13)));
         pre.setString(5,"[\"ROLE_STUDENT\"]");
       // pre.setString(7, "a:0:{}");
        pre.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    

    public ObservableList<User> listStudent() {
        ObservableList<User> listStudent = FXCollections.observableArrayList();
        try {
            String req = "select * from user where roles='[\"ROLE_STUDENT\"]'"  ; //requete select from db
            Statement st = MyConnection.getInstance().getCnx().createStatement(); //instance of myConnection pour etablir la cnx
            ResultSet rs=st.executeQuery(req); //resultat de la requete
            
            //tant que rs has next get personne and add it to the list
            while(rs.next()){
                User stu =new User();
                stu.setId(rs.getInt("id")); //set id from req result
                stu.setUsername(rs.getString("username")); 
                stu.setPrenom(rs.getString("prenom")); 
                stu.setEmail(rs.getString("email")); 
               // stu.setIsBanned(rs.getBoolean("IsBanned"));
                listStudent.add(stu); //ajout de la personne a la liste
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return listStudent;
    }
    
    public void updateStudent(User stu, String id) {
        String req = "update user set username=?, prenom=?, email=?, password=? , is_banned=? WHERE id=?";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req);
            pst.setString(1, stu.getUsername());
            pst.setString(2 ,stu.getPrenom());
            pst.setString(3, stu.getEmail());
            pst.setString(4, stu.getPassword());
            pst.setBoolean(5, stu.getIsBanned());
            pst.setString(6, id);
            pst.executeUpdate();
            System.out.println("Student updated");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
      public void supprimerPersonne(String username) {
        String req = "delete from user where username = ?";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req);
            pst.setString(1, username);
            pst.executeUpdate();
            System.out.println("Etudiant supprimée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
      public User getStudent(int id){
        User stu = new User();
        try {
            String req = "select * from user where id ="+id;
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
      
}
