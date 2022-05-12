/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.edspace.services;

import edu.edspace.entities.Document;
import edu.edspace.entities.DocumentFavoris;
import edu.edspace.utils.MyConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MeriamBI
 */
public class DocumentFavorisService {
    
    public void pinDocument(DocumentFavoris fave) {
        try {
            String req = "insert into document_favoris (user_id,document_id) values"
                    + "(?,?)";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, fave.getUser_id());
            pst.setInt(2, fave.getDocument_id());
            pst.executeUpdate(); //pour exécuter la requete
            System.out.println("Dcoument épinglé");
            ResultSet rs = pst.getGeneratedKeys();
            while (rs.next()) {
                fave.setId(rs.getInt(1));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void unpinDocument(DocumentFavoris fave) {
        //String req = "delete from document_favoris where id = ?";
        String req = "delete from document_favoris where user_id = ? and document_id=?";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req);
            pst.setInt(1, fave.getUser_id());
            pst.setInt(2, fave.getDocument_id());
            pst.executeUpdate();
            System.out.println("Document détaché");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public List<Document> listDocsFavesJoined(int userId) {
        String req ="select * from document inner join document_favoris on (document.id=document_favoris.document_id) where document_favoris.user_id="+userId;
        List<Document> myList = new ArrayList<>();
        try {
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(req); //resultat de la requete
            while (rs.next()) {
                Document d = new Document();
                d.setId(rs.getInt("id"));
                d.setMatiere(rs.getString("matiere_id"));
                d.setNiveau(rs.getString("niveau_id"));
                d.setNom(rs.getString("nom"));
                d.setDate_insert(rs.getString("date_insert"));
                d.setProp(rs.getString("proprietaire"));
                d.setType(rs.getString("type"));
                d.setSignalements(rs.getInt("signalements"));
                d.setUrl(rs.getString("url"));
                myList.add(d);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }
    
    
    
    public List<DocumentFavoris> listFaves(int user_id) {
        String req = "select * from document_favoris where user_id="+user_id; //requete select from db
        return getFaveList(req);
    }
    
    //common method called when getting a list of fave docs
    public List<DocumentFavoris> getFaveList(String req){
        List<DocumentFavoris> myList = new ArrayList<>();
        try {
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(req); //resultat de la requete
            while (rs.next()) {
                DocumentFavoris d = new DocumentFavoris();
                d.setId(rs.getInt("id"));
                d.setDocument_id(rs.getInt("document_id"));
                d.setUser_id(rs.getInt("user_id"));
                myList.add(d);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }
}
