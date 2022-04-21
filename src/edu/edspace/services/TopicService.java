/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.edspace.services;


import edu.edspace.entities.ThreadType;
import edu.edspace.utils.MyConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;
import edu.edspace.entities.Thread;
/**
 *
 * @author 21656
 */
public class TopicService {
    public boolean addTopic(ThreadType tt){
         
        try {
            String query = "Insert into thread_type values"+"(?,?,?)";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query);
            pst.setString(1, null);
            pst.setString(2, tt.getContent());
            pst.setBoolean(3, tt.getDisplay());
            pst.executeUpdate();
            System.out.println("Topic ajoutée");
            
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    return true;
    }
    public List<ThreadType> listTopics() {
        List<ThreadType> tts = new ArrayList<>();
        try {
            String query = "select * from thread_type where display = 0"; 
            Statement st = MyConnection.getInstance().getCnx().createStatement(); 
            ResultSet rs = st.executeQuery(query); 
            while (rs.next()) {
                ThreadType tt = new ThreadType();
                tt.setId(rs.getString("id"));
                tt.setContent(rs.getString("content"));
                tt.setDisplay(rs.getBoolean("display"));
               
                
                tts.add(tt); 
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return tts;
    }
    
    
    public void deleteTopic(ThreadType t) {
        String req = "update thread_type set display=1 WHERE id=?";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req);
            pst.setString(1,t.getId());
            pst.executeUpdate();
            System.out.println("Topic deleted");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public List<Thread> getThreadsByTopic(ThreadType topic){
        List<Thread> threads = new ArrayList<>();
        try {
            String query = "select * from thread where thread_type_id = "+topic.getId(); //requete select from db
            Statement st = MyConnection.getInstance().getCnx().createStatement(); //instance of myConnection pour etablir la cnx
            ResultSet rs = st.executeQuery(query); //resultat de la requete

            //tant que rs has next get matiere and add it to the list
            while (rs.next()) {
                edu.edspace.entities.Thread t = new edu.edspace.entities.Thread();
                t.setId(rs.getInt("id"));
                t.setThreadType(rs.getInt("thread_type_id"));
                t.setUser(rs.getInt("user_id"));
                t.setQuestion(rs.getString("question"));
                t.setPostDate(rs.getString("post_date"));
                t.setDisplay(rs.getBoolean("display"));
                t.setVerified(rs.getString("verified"));
                
                threads.add(t); 
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return threads;
    }
    public ThreadType getTopic(int id){
        ThreadType tt = new ThreadType();
        try {
            String req = "select * from thread_type where id ="+id;
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(req); 
            while (rs.next()) {
                tt.setId(rs.getString("id"));
                tt.setContent(rs.getString("content"));
                tt.setDisplay(rs.getBoolean("display"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return tt;
    }

}
