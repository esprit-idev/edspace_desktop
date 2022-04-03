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
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author MeriamBI
 */
public class DocumentService {

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
            pst.setString(7, document.getNiveau());
            pst.setString(8, document.getMatiere());

            File fichier = document.getFichier();
            FileInputStream inputStream = null;
            String mimeType = "url";
            String base64 = null;
            if (fichier != null) {
                base64 = convertFileToBase64(Statics.myDocs + document.getNom());
                mimeType = URLConnection.guessContentTypeFromName(document.getNom());
                try {
                    inputStream = new FileInputStream(fichier);
                } catch (FileNotFoundException ex) {
                    System.out.println(ex.getMessage());
                }
            }
            pst.setString(6, base64);
            pst.setString(9, mimeType);
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

    public List<Document> listDocs() {
        String req = "select * from document"; //requete select from db
        return getDocumentsList(req);
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

    public List<Document> filterByOwner(String owner) {
        String req = "select * from document where proprietaire='" + owner + "'"; //requete select from db
        return getDocumentsList(req);
    }

    public List<Document> filterByNiveauMatiere(String niveau, String matiere) {
        String req = "select * from document where niveau_id='" + niveau + "' and matiere_id='" + matiere + "'"; //requete select from db
        return getDocumentsList(req);
    }
    
    public List<Document> listReportedDocs() {
        String req = "select * from document where signalements>0"; //requete select from db
        return getDocumentsList(req);
    }

    public void signalerDocument(Document document) {
        String req = "update document set signalements=? WHERE id=?";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req);
            pst.setInt(1, document.getSignalements() + 1);
            pst.setInt(2, document.getId());
            pst.executeUpdate();
            System.out.println("document signalé");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void ignorerSignalDocument(Document document) {
        String req = "update document set signalements=? WHERE id=?";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req);
            pst.setInt(1, 0);
            pst.setInt(2, document.getId());
            pst.executeUpdate();
            System.out.println("document signalé");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    //common method called when getting a list of docs
    public List<Document> getDocumentsList(String req) {
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
                //d.setFichier(rs.getBlob("fichier"));
                d.setType(rs.getString("type"));
                d.setSignalements(rs.getInt("signalements"));
                d.setUrl(rs.getString("url"));
                d.setBase64(rs.getString("base64"));
                if (rs.getString("url") == null) {
                    File fichier = convertBlobToFile(rs.getBlob("fichier"), d);
                    d.setFichier(fichier);
                } else {
                    d.setFichier(null);
                }

                myList.add(d);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }

    public void sendDocViaEmail(Document doc) {
        // Get a Properties object
        Properties props = System.getProperties();
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.ssl.required", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        String to = "meriamesprittest@gmail.com"; //to_change with destination email
        String from = "meriamesprittest@gmail.com"; //to_change with current user email
        String password = "meriamesprittest221199*#"; //to_change with current user email pwd
        try {
            Session session = Session.getDefaultInstance(props,
                    new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(from, password);
                }
            });

            //compose message     
       
            File file = doc.getFichier();
            Multipart multipart = new MimeMultipart();
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("this is the subject"); //to_change with email subject
            //create MimeBodyPart object and set your message text     
            BodyPart messageBodyPart1 = new MimeBodyPart();
            if (file != null) {
                messageBodyPart1.setText("This is the body\nCe document est envoyé depuis la plateforme EDSPACE par " + "Anas Houissa"/*change with current username*/); //to_change with email body
            } else {
                messageBodyPart1.setText("This is the body\n" + doc.getUrl() + "\nCe document est envoyé depuis la plateforme EDSPACE par " + "Anas Houissa"/*change with current username*/); //to_change with email body
            }

            //create new MimeBodyPart object and set DataHandler object to this object      
            MimeBodyPart messageBodyPart2 = new MimeBodyPart();

            if (file != null) {
                String filepath = doc.getFichier().getPath();//change accordingly  
                DataSource source = new FileDataSource(filepath);
                messageBodyPart2.setDataHandler(new DataHandler(source));
                messageBodyPart2.setFileName(filepath);
                //create Multipart object and add MimeBodyPart objects to this object      

                if (file != null) {
                    multipart.addBodyPart(messageBodyPart2);
                }
            }
            multipart.addBodyPart(messageBodyPart1);

            //set the multiplart object to the message object  
            message.setContent(multipart);

            //7) send message  
            Transport.send(message);

            System.out.println("email sent!");
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
    }

    public void convertUrlToPdf(String filename) throws InterruptedException, IOException {
        Process wkhtml; // Create uninitialized process
        String command = "wkhtmltopdf https://github.com/KnpLabs/snappy C:/Users/MeriamBI/Desktop/testpdfhtml/" + filename + ".pdf"; // Desired command
        //to_change
        wkhtml = Runtime.getRuntime().exec(command); // Start process
        IOUtils.copy(wkhtml.getErrorStream(), System.err); // Print output to console

        wkhtml.waitFor(); // Allow process to run

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
