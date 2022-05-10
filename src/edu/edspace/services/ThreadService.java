/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.edspace.services;

import javax.mail.internet.InternetAddress;
import edu.edspace.entities.Reponse;
import edu.edspace.entities.Thread;
import edu.edspace.utils.MyConnection;
import edu.edspace.utils.Statics;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
/**
 *
 * @author 21656
 */
public class ThreadService {
    public boolean addThread(Thread T){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  
        LocalDateTime now = LocalDateTime.now(); 
        try {
            String query = "Insert into thread values"+"(?,?,?,?,?,?,?,?)";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query);
            pst.setString(1, null);
            pst.setInt(2, T.getThreadType());
            pst.setInt(3, T.getUser());
            pst.setString(4,T.getQuestion());
            pst.setString(5, null);
            pst.setString(6, dtf.format(now));
            pst.setString(7, "0");
            pst.setString(8, "0");
            pst.executeUpdate();
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
            String query = "select * from thread where display= 0"; //requete select from db
            Statement st = MyConnection.getInstance().getCnx().createStatement(); //instance of myConnection pour etablir la cnx
            ResultSet rs = st.executeQuery(query); //resultat de la requete

            //tant que rs has next get matiere and add it to the list
            while (rs.next()) {
                Thread t = new Thread();
                t.setId(rs.getInt("id"));
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
    
    public void modifierThread(Thread t,int Id) {
        String req = "update thread set question=? WHERE id=?";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req);
            pst.setString(1, t.getQuestion());
            
            pst.setInt(2, Id);
            pst.executeUpdate();
            System.out.println("Thread Updated");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    public void deleteThread(Thread t) {
        String req = "update thread set display=1 WHERE question=?";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req);
            pst.setString(1,t.getQuestion());
            pst.executeUpdate();
            System.out.println("Thread deleted");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    public Thread getThread(int id){
        Thread t = new Thread();
        try {
            String req = "select * from thread where id ="+id;
            Statement st = MyConnection.getInstance().getCnx().createStatement(); //instance of myConnection pour etablir la cnx
            ResultSet rs = st.executeQuery(req); //resultat de la requete
            
            //tant que rs has next get matiere and add it to the list
            while (rs.next()) {
                
                t.setId(rs.getInt("id"));
                t.setThreadType(rs.getInt("thread_type_id"));
                t.setUser(rs.getInt("user_id"));
                t.setQuestion(rs.getString("question"));
                t.setPostDate(rs.getString("post_date"));
                t.setDisplay(rs.getBoolean("display"));
                t.setVerified(rs.getString("verified"));
                
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            
        }
        return t;
    }
    public void setVerified(Thread t){
        String req = "update thread set verified=1 WHERE id = "+t.getId();
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req);
            
            pst.executeUpdate();
            System.out.println("Thread verified");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    public void search(Thread t){
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                if (t.getQuestion().contains(" ")){
                    
                        String my_new_str = t.getQuestion().replace(" ", "+");
                Desktop.getDesktop().browse(new URI("https://www.google.com/search?q="+ my_new_str));
                }
                else {
                    Desktop.getDesktop().browse(new URI("https://www.google.com/search?q="+t.getQuestion()));
                }
            } catch (URISyntaxException ex) {
                Logger.getLogger(ThreadService.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ThreadService.class.getName()).log(Level.SEVERE, null, ex);
            }
}
    }
    public void searchPDF(Thread t){
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                if (t.getQuestion().contains(" ")){
                    
                        String my_new_str = t.getQuestion().replace(" ", "+");
                    
               
                Desktop.getDesktop().browse(new URI("https://www.google.com/search?q=filetype%3Apdf+"+my_new_str));
                 }
                else 
                {
                    Desktop.getDesktop().browse(new URI("https://www.google.com/search?q=filetype%3Apdf+"+t.getQuestion()));
                }
            } catch (URISyntaxException ex) {
                Logger.getLogger(ThreadService.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ThreadService.class.getName()).log(Level.SEVERE, null, ex);
            }
}
    }
    public void VerifyByReponses(Thread t){
        ReponseService ReponseService = new ReponseService();
        List<Reponse> reponses = ReponseService.getReponsesByThread(t.getId());
        if (t.getVerified().equals("1")){
            System.out.println("Thread is already Verified");
        }
        else {
            
        
        if(reponses.size()>=5){
            String req = "update thread set verified=1 WHERE id = "+t.getId();
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req);
            
            pst.executeUpdate();
            System.out.println("Thread verified");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
        else{
               System.out.println("Thread is still not verified you need " + (5-reponses.size()) + " more");
                }
        }
    }
    
}
