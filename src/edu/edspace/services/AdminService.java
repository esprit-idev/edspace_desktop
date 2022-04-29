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
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author YOOSURF
 */
public class AdminService {
   public void ajouterAdmin(User ad) {
        
           try {
            String req = "Insert into user values"
                    + "(?,?,?,?,?,?,?,?,?,?,?,?)"; //requete d'inertion avec parametres

            /*PreparedStatement used with dynamic requests+faster and more secure than Statement (used in the method above)
                can't drop or alter with PreparedStatement*/
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req); //instance of myconnection pour etablir la cnx
            pst.setString(1, null);
            pst.setString(2,null);
            pst.setString(3, null);
            pst.setString(4, ad.getUsername()); 
            pst.setString(5, ad.getPrenom()); 
            pst.setString(6, ad.getEmail());
           /*  String myPwd = "$2y"+BCrypt.hashpw(ad.getPassword(), BCrypt.gensalt(13)).substring(3);
            pst.setString(7, myPwd);*/
            pst.setString(7,BCrypt.hashpw(ad.getPassword(), BCrypt.gensalt(13)));
            pst.setBoolean(8 , false);
            pst.setString(9,null);
            pst.setString(10,"[\"ROLE_ADMIN\"]");
             pst.setString(11,null);
             pst.setString(12,null);
            pst.executeUpdate(); //pour exécuter la requete
            System.out.println("Admin ajoutée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    
    

    public ObservableList<User> listAdmin() {
        ObservableList<User> listAdmin = FXCollections.observableArrayList();
        try {
            String req = "select * from user where roles='[\"ROLE_ADMIN\"]'"  ; //requete select from db
            Statement st = MyConnection.getInstance().getCnx().createStatement(); //instance of myConnection pour etablir la cnx
            ResultSet rs=st.executeQuery(req); //resultat de la requete
            
            //tant que rs has next get personne and add it to the list
            while(rs.next()){
                User ad =new User();
                ad.setId(rs.getInt("id")); //set id from req result
                ad.setUsername(rs.getString("username")); 
                ad.setPrenom(rs.getString("prenom")); 
                ad.setEmail(rs.getString("email")); 
               // stu.setIsBanned(rs.getBoolean("IsBanned"));
                listAdmin.add(ad); //ajout de la personne a la liste
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return listAdmin;
    }
    
    public void updateAdmin(User ad, String id) {
        String req = "update user set username=?, prenom=?, email=?, password=? , is_banned=? WHERE id=?";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req);
            pst.setString(1, ad.getUsername());
            pst.setString(2 ,ad.getPrenom());
            pst.setString(3, ad.getEmail());
            pst.setString(4, BCrypt.hashpw(ad.getPassword(), BCrypt.gensalt(13)));
            //pst.setString(4, ad.getPassword());
            pst.setBoolean(5, ad.getIsBanned());
            pst.setString(6, id);
            pst.executeUpdate();
            System.out.println("Admin updated");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
      public void supprimerAdmin(String id) {
        String req = "delete from user where id = ?";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req);
            pst.setString(1, id);
            pst.executeUpdate();
            System.out.println("Admin supprimée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
      public User getAdmin(int id){
        User ad = new User();
        try {
            String req = "select * from user where id ="+id;
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
      
       public List<User> sortByNom(){
         List<User> users=listAdmin();
         List<User> resultat=users.stream().sorted(Comparator.comparing(User::getUsername)).collect(Collectors.toList());
         return resultat;
     }
     
     
 public List<User> sortByEmail(){
         List<User> users=listAdmin();
         List<User> resultat=users.stream().sorted(Comparator.comparing(User::getEmail)).collect(Collectors.toList());
         return resultat;
     }
    
    
}
