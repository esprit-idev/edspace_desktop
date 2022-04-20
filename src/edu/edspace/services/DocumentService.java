/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.edspace.services;

import edu.edspace.entities.Document;
import edu.edspace.entities.Matiere;
import edu.edspace.utils.MyConnection;
import edu.edspace.utils.Statics;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import javax.mail.Session;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.apache.commons.io.IOUtils;
;

/**
 *
 * @author MeriamBI
 */
public class DocumentService {

    public void ajouterDocument(Document document) throws FileAlreadyExistsException{
        try {
            String req = "insert into document (signalements,nom,date_insert,proprietaire,url,niveau_id,matiere_id,type) values"
                    + "(?,?,?,?,?,?,?,?)";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, document.getSignalements());
            pst.setString(2, document.getNom());
            pst.setString(3, document.getDate_insert());
            pst.setString(4, document.getProp());
            pst.setString(5, document.getUrl());
            pst.setString(6, document.getNiveau());
            pst.setString(7, document.getMatiere());

            if (!document.getType().equals("url")) {
                document.setType(URLConnection.guessContentTypeFromName(document.getNom()));
            }

            pst.setString(8, document.getType());
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
    
    public int countRelatedDocs(Matiere matiere){
        int relatedDocs=0;
        String req = "select count(*) from document where matiere_id=?"; //requete select from db
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req);
            pst.setString(1, matiere.getId());
            ResultSet rs = pst.executeQuery();
            rs.next();
            relatedDocs = rs.getInt("count(*)");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return relatedDocs;
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
        String reqF = "delete from document_favoris where document_id = ?";
        String req = "delete from document where id = ?";
        try {
            PreparedStatement pstF = MyConnection.getInstance().getCnx().prepareStatement(reqF);
            pstF.setInt(1, document.getId());
            pstF.executeUpdate();
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req);
            pst.setInt(1, document.getId());
            pst.executeUpdate();
            System.out.println("Document supprimé");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void apercuDocument(Document doc) {
        File fic = new File(Statics.myDocs + doc.getNom());
        if (doc.getUrl() == null) {
            try {
                Desktop.getDesktop().open(fic);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        } else {
            File ficConverti = new File("C:/Users/MeriamBI/Desktop/testpdfhtml/" + doc.getNom() + ".pdf");
            try {
                Desktop.getDesktop().open(ficConverti);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public void downloadDocument(Document doc,String chosenDir) throws IOException {
        if(doc.getType().equals("url")){
            Files.copy(Paths.get(Statics.convertedDir + doc.getNom()+".pdf"), Paths.get(chosenDir+"/"+ doc.getNom()+".pdf"));
        }else{
            Files.copy(Paths.get(Statics.myDocs + doc.getNom()), Paths.get(chosenDir+"/"+ doc.getNom()));
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
                myList.add(d);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }

    public void sendDocViaEmail(Document doc,String from,String password,String to,String object,String body) throws AddressException, MessagingException {
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
            Session session = Session.getDefaultInstance(props,
                    new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(from, password);
                }
            });

            //compose message 
            Multipart multipart = new MimeMultipart();
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(object);
            //create MimeBodyPart object and set your message text     
            BodyPart messageBodyPart1 = new MimeBodyPart();
            if (doc.getUrl() == null) {
                messageBodyPart1.setText(body+"\nCe document est envoyé depuis la plateforme EDSPACE par " + "Anas Houissa"/*to_change with current username*/);
            } else {
                messageBodyPart1.setText(body+"\n" + doc.getUrl() + "\nCe document est envoyé depuis la plateforme EDSPACE par " + "Anas Houissa"/*to_change with current username*/);
            }

            //create new MimeBodyPart object and set DataHandler object to this object      
            MimeBodyPart messageBodyPart2 = new MimeBodyPart();

            if (doc.getUrl() == null) {
                String filepath = Statics.myDocs + doc.getNom();
                DataSource source = new FileDataSource(filepath);
                messageBodyPart2.setDataHandler(new DataHandler(source));
                messageBodyPart2.setFileName(filepath);
                //create Multipart object and add MimeBodyPart objects to this object      

                if (doc.getUrl() == null) {
                    multipart.addBodyPart(messageBodyPart2);
                }
            }
            multipart.addBodyPart(messageBodyPart1);

            //set the multiplart object to the message object  
            message.setContent(multipart);

            //7) send message  
            Transport.send(message);

            System.out.println("email sent!");
    }

    public void convertUrlToPdf(String filename) throws InterruptedException, IOException {
        Process wkhtml; // Create uninitialized process
        String command = "wkhtmltopdf https://github.com/KnpLabs/snappy C:/Users/MeriamBI/Desktop/testpdfhtml/" + filename + ".pdf"; //to_change
        //to_change
        wkhtml = Runtime.getRuntime().exec(command); // Start process
        IOUtils.copy(wkhtml.getErrorStream(), System.err); // Print output to console
        wkhtml.waitFor(); // Allow process to run

    }

}
