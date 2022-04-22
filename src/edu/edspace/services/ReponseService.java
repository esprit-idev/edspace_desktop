/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.edspace.services;

import edu.edspace.entities.Reponse;
import edu.edspace.utils.MyConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 21656
 */
public class ReponseService {
     public boolean addReponse(Reponse r){
         DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  
        LocalDateTime now = LocalDateTime.now(); 
        try {
            String query = "Insert into reponse values"+"(?,?,?,?,?,?)";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query);
            pst.setString(1, null);
            pst.setInt(2, r.getThread());
            pst.setInt(3, r.getUser());
            pst.setString(4,r.getReply());
            pst.setString(5, dtf.format(now));
            pst.setBoolean(6, false);
            pst.executeUpdate();
            System.out.println("reponse ajoutée à thread "+r.getThread());
            
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    return true;
    }
    public List<Reponse> listReponses() {
        List<Reponse> reps = new ArrayList<>();
        try {
            String query = "select * from reponse where display = 0"; //requete select from db
            Statement st = MyConnection.getInstance().getCnx().createStatement(); //instance of myConnection pour etablir la cnx
            ResultSet rs = st.executeQuery(query); //resultat de la requete

            //tant que rs has next get matiere and add it to the list
            while (rs.next()) {
                Reponse r = new Reponse();
                r.setId(rs.getString("id"));
                r.setReply(rs.getString("reply"));
                r.setUser(rs.getInt("user_id"));
                
                r.setReplyDate(rs.getString("reply_date"));
                r.setDisplay(rs.getBoolean("display"));
               
                
                reps.add(r); //ajout de la matiere a la liste
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return reps;
    }
    
    
    public void deleteReponse(Reponse r) {
        String req = "update reponse set display=1 WHERE id=?";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req);
            pst.setString(1,r.getId());
            pst.executeUpdate();
            System.out.println("Reponse deleted");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public List<Reponse> getReponsesByThread(int id){
        List<Reponse> reps = new ArrayList<>();
        try {
            String query = "select * from reponse where display = 0 and thread_id = "+id; //requete select from db
            Statement st = MyConnection.getInstance().getCnx().createStatement(); //instance of myConnection pour etablir la cnx
            ResultSet rs = st.executeQuery(query); //resultat de la requete

            //tant que rs has next get matiere and add it to the list
            while (rs.next()) {
                Reponse r = new Reponse();
                r.setId(rs.getString("id"));
                r.setReply(rs.getString("reply"));
                r.setUser(rs.getInt("user_id"));
                
                r.setReplyDate(rs.getString("reply_date"));
                r.setDisplay(rs.getBoolean("display"));
               
                
                reps.add(r); //ajout de la matiere a la liste
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return reps;
    }
    public Reponse getReponse(int id){
        Reponse r = new Reponse();
        try {
            String req = "select * from reponse where id ="+id;
            Statement st = MyConnection.getInstance().getCnx().createStatement(); //instance of myConnection pour etablir la cnx
            ResultSet rs = st.executeQuery(req); //resultat de la requete
            
            //tant que rs has next get matiere and add it to the list
            while (rs.next()) {
                
                r.setId(rs.getString("id"));
                r.setReply(rs.getString("reply"));
                r.setUser(rs.getInt("user_id"));
                
                r.setReplyDate(rs.getString("reply_date"));
                r.setDisplay(rs.getBoolean("display"));
                
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            
        }
        return r;
    }
}
