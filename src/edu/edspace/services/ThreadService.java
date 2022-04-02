/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.edspace.services;

import edu.edspace.entities.Thread;
import edu.edspace.utils.MyConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author 21656
 */
public class ThreadService {
    public boolean addThread(Thread T){
        try {
            String query = "Insert into thread values"+"(?,?,?,?,?,?,?,?)";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query);
            pst.setString(1, null);
            pst.setInt(2, T.getThreadType());
            pst.setInt(3, T.getUser());
            pst.setString(4,T.getQuestion());
            pst.setString(5, null);
            pst.setString(6, T.getPostDate());
            pst.setString(7, "0");
            pst.setString(8, "0");
            pst.executeUpdate(); //pour exécuter la requete
            System.out.println("Thread ajoutée");
            
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    return true;
    }
    public List<Thread> listThreads() {
        List<Thread> threads = new ArrayList<>();
        try {
            String query = "select * from thread"; //requete select from db
            Statement st = MyConnection.getInstance().getCnx().createStatement(); //instance of myConnection pour etablir la cnx
            ResultSet rs = st.executeQuery(query); //resultat de la requete

            //tant que rs has next get matiere and add it to the list
            while (rs.next()) {
                Thread t = new Thread();
                t.setId(rs.getString("id"));
                t.setThreadType(rs.getInt("thread_type_id"));
                t.setUser(rs.getInt("user_id"));
                t.setQuestion(rs.getString("question"));
                t.setPostDate(rs.getString("post_date"));
                t.setDisplay(rs.getBoolean("display"));
                t.setVerified(rs.getString("verified"));
                
                threads.add(t); //ajout de la matiere a la liste
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return threads;
    }
    
    public void modifierThread(Thread t,String Id) {
        String req = "update thread set question=? WHERE question=?";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req);
            pst.setString(1, t.getQuestion());
            
            pst.setString(2, Id);
            pst.executeUpdate();
            System.out.println("Thread Updated");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    public void deleteThread(Thread t) {
        String req = "delete from thread where question = ?";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req);
            pst.setString(1,t.getQuestion());
            pst.executeUpdate();
            System.out.println("Thread deleted");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
