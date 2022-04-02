/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.edspace.services;

import java.sql.Blob;
import edu.edspace.entities.Document;
import edu.edspace.utils.MyConnection;
import edu.edspace.utils.Statics;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author MeriamBI
 */
public class DocumentService {
//int signalements, String nom, String date_insert, String prop, String url, String base64, String niveau, String matiere, String type, FileOutputStream fichier

    public void ajouterDocument(Document document) {
        try {
            String req = "insert into document (signalements,nom,date_insert,proprietaire,url,base64,niveau_id,matiere_id,type,fichier) values"
                    + "(?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, document.getSignalements());
            pst.setString(2, document.getNom());
            pst.setString(3, document.getDate_insert());
            pst.setString(4, document.getProp());
            pst.setString(5, document.getUrl());
            pst.setString(6, convertFileToBase64(Statics.myDocs + document.getNom()));
            pst.setString(7, document.getNiveau());
            pst.setString(8, document.getMatiere());
            pst.setString(9, URLConnection.guessContentTypeFromName(document.getNom()));
            File fichier = document.getFichier();
            FileInputStream inputStream = null;
            try {
                inputStream = new FileInputStream(fichier);
            } catch (FileNotFoundException ex) {
                System.out.println(ex.getMessage());
            }
            pst.setBlob(10, inputStream);
            pst.executeUpdate();
            System.out.println("Document ajouté");
            ResultSet rs = pst.getGeneratedKeys();
            while (rs.next()) {
                document.setId(rs.getInt(1));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List<Document> listeDocuments() {
        List<Document> myList = new ArrayList<>();
        try {
            String req = "select * from document"; //requete select from db
            Statement st = MyConnection.getInstance().getCnx().createStatement(); //instance of myConnection pour etablir la cnx
            ResultSet rs = st.executeQuery(req); //resultat de la requete

            while (rs.next()) {
                Document d = new Document();
                d.setId(rs.getInt("id"));
                d.setMatiere(rs.getString("matiere_id"));
                d.setNiveau(rs.getString("niveau_id"));
                d.setNom(rs.getString("nom"));
                d.setDate_insert(rs.getString("date_insert"));
                d.setProp(rs.getString("proprietaire"));
                //d.setFichier(rs.getBlob("fichier"));
                d.setType(rs.getString("type"));
                d.setSignalements(rs.getInt("signalements"));
                d.setUrl(rs.getString("url"));
                d.setBase64(rs.getString("base64"));

                File fichier = convertBlobToFile(rs.getBlob("fichier"), d);
                d.setFichier(fichier);

                myList.add(d);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }

    public void modifierDocument(Document document) {
        String req = "update document set niveau_id=?, matiere_id=? WHERE id=?";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req);
            pst.setString(1, document.getNiveau());
            pst.setString(2, document.getMatiere());
            pst.setInt(3, document.getId());
            pst.executeUpdate();
            System.out.println("document modifié");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void supprimerDocument(Document document) {
        String req = "delete from document where id = ?";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req);
            pst.setInt(1, document.getId());
            pst.executeUpdate();
            System.out.println("Document supprimé");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public File convertBlobToFile(Blob blob, Document d) {
        InputStream blobStream = null;
        try {
            blobStream = blob.getBinaryStream();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        //saving blob to a file using fileoutput stream (converting blob from db to file
        FileOutputStream fos = null;
        File fichier = null;
        try {
            fichier = new File(Statics.myDocs + d.getNom());
            fos = new FileOutputStream(fichier);
            if (!fichier.exists()) {
                try {
                    fichier.createNewFile();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        byte[] buffer = new byte[1024];
        int n = 0;
        try {
            while ((n = blobStream.read(buffer)) != -1) {
                fos.write(buffer, 0, n);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        try {
            fos.flush();
            fos.close();
            blobStream.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return fichier;
    }

    public String convertFileToBase64(String filepath) {
        byte[] fileContent = null;
        try {
            fileContent = FileUtils.readFileToByteArray(new File(filepath));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        String myBase64 = java.util.Base64.getEncoder().encodeToString(fileContent);
        return myBase64;
    }
}
