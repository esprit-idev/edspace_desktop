/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.edspace.tests;

import java.util.logging.Level;
import java.util.logging.Logger;

import edu.edspace.services.ClubCategService;
import edu.edspace.services.ClubPubService;
import edu.edspace.services.ClubService;
import edu.edspace.entities.Message;
import edu.edspace.entities.Niveau;
import edu.edspace.entities.Session;

import edu.edspace.services.MessageService;

import edu.edspace.services.NiveauService;
import edu.edspace.services.ThreadService;
import edu.edspace.utils.MyConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

import edu.edspace.entities.Thread;
import edu.edspace.entities.User;

import edu.edspace.services.AdminService;

import edu.edspace.services.MailService;
import edu.edspace.services.ClasseService;



import edu.edspace.services.StudentService;
import edu.edspace.services.UserService;
import edu.edspace.services.statics;


/**
 *
 * @author MeriamBI
 */
public class MainClass extends Application{

    public static void main(String[] args){
        MyConnection.getInstance().getCnx();


        
       // Student();
      // User();
      // news();
       // emplois();
        // newsCategory();
       // gestionClasse();
       // statics();
       // c();
       launch(args);

        

      // launch(args);

    }

    public static void ClubPub() {
        ClubPubService clubPubService = new ClubPubService();
        System.out.println(clubPubService.displayPostedClubPubs(5));
        //add
        /*  ClubPub cpa = new ClubPub("test", "test", null, new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()), 5, 0);
        clubPubService.ajouterPubClub(cpa);
        System.out.println(clubPubService.displayClubPubs(5));*/
        //update
        /*   ClubPub cp=new ClubPub("test", "test", null, new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()), 5, 0);
        clubPubService.updateClubPub(cp,85);
                System.out.println(clubPubService.displayClubPubs(5));
         */
        //delete
        /*clubPubService.deletePubClub(0);
        System.out.println(clubPubService.displayClubPubs(5));*/
    }

    public static void ClubCateg() {
        ClubCategService categService = new ClubCategService();
        //diplay clubsCat
        System.out.println(categService.displayClubCategories());

        //add clubsCat
        /*  ClubCategory clubCategory1 = new ClubCategory("info");
        categService.ajouterClubCat(clubCategory1);
        System.out.println(categService.displayClubCategories());*/
        //delete clubsCat
        /*  categService.deleteClubCategories(Integer.toString(14));
        System.out.println(categService.displayClubCategories());*/
        //udpate clubsCat
        /*  categService.updateClubCategories("goku",Integer.toString(13));
        System.out.println(categService.displayClubCategories());*/
    }

    public static void Clubs() {
        ClubService clubService = new ClubService();
        //diplay clubs
        System.out.println(clubService.displayClub());
        /* 
        //add club
        Club c1 = new Club("ClubTest", "ClubTest", "ClubTest", 14, 1);
        clubService.ajouterClub(c1);
        System.out.println(clubService.displayClub());

        //delete clubs
        clubService.deleteClub(19);
        System.out.println(clubService.displayClub());

        //udpate clubs
      
        Club c2 = new Club("ClubTest", "ClubTest", "ClubTest", 2, 1);
         clubService.updateClub(c2,6);
        System.out.println(clubService.displayClub());
         */

    }

    public static void gestionThread() {
        ThreadService ThreadService = new ThreadService();
        System.out.println("Test Thread add");
        Thread t = new Thread("How to FXJ", 1, 1);
        System.out.println("=>Threads:\n" + ThreadService.listThreads());
        System.out.println("///////////////////////////////////////////////////////////////");
        String oldId = t.getQuestion();
        t.setQuestion("Updated");
        ThreadService.modifierThread(t, oldId);
        ThreadService.deleteThread(t);
        //ThreadService.addThread(t);

    }

    /*
    public static void gestionMatiere() {
        System.out.println("******************TEST CRUD MATIERE******************");
        MatiereService ms = new MatiereService(); //instanciation du service MatiereService
        //TEST AFFICHAGE LISTE DES MATIERES
        System.out.println("=> La liste des matières:\n" + ms.listMatieres());

        //TEST AJOUT MATIERE
        Matiere m = new Matiere("testfx", "3A");
        ms.ajouterMatiere(m);
        System.out.println("=> La liste des matières après ajout:\n" + ms.listMatieres());

        //TEST MODIFIER MATIERE
        String oldId = m.getId();
        m.setId("testfxupdate");
        m.setNiveau("3B");
        ms.modifierMatiere(m, oldId);
        System.out.println("=> La liste des matières après modification:\n" + ms.listMatieres());

        //TEST SUPPRIMER MATIERE
        ms.supprimerMatiere(m);
        System.out.println("=> La liste des matières après suppression:\n" + ms.listMatieres());

        //TEST LIST MATIERES FILTERED BY NIVEAU
        System.out.println("=> La liste des matières filtrée par niveau:\n" + ms.filterByNiveau("3A"));
    }

    public static void gestionDocument() {
        System.out.println("\n******************TEST CRUD DOCUMENT******************");
        DocumentService ds = new DocumentService(); //instanciation du service DocumentService
        DocumentFavorisService dfs = new DocumentFavorisService(); //instanciation du service DocumentFavorisService
        //TEST AFFICHAGE LISTE DES DOCUMENTS
        System.out.println("=> La liste des documents:\n" + ds.listDocs());

        //TEST AJOUT DOCUMENT
        String owner = "Anas Houissa"; //to_change
        int user_id = 5; //to_change
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime currentDate = LocalDateTime.now();
        Document doc = new Document(0, "logo.png", formatter.format(currentDate), owner, null, "3A", "matiereA", "");
        Document url = new Document(0, "urltest", formatter.format(currentDate), owner, "https://github.com/KnpLabs/snappy", "3B", "matiereB", "url");
        ds.ajouterDocument(doc);
        ds.ajouterDocument(url);
        System.out.println("=> La liste des documents après ajout:\n" + ds.listDocs());

        //TEST MODIFIER MATIERE
        doc.setMatiere("matiereB");
        doc.setNiveau("3B");
        ds.modifierDocument(doc);
        System.out.println("=> La liste des documents après modification:\n" + ds.listDocs());
        //TEST SUPPRIMER DOCUMENT
        ds.supprimerDocument(doc);
        System.out.println("=> La liste des documents après suppression:\n" + ds.listDocs());

        //TEST LIST DOCUMENTS FILTERED BY OWNER
        System.out.println("=> La liste des documents de l'utilisateur courant:\n" + ds.filterByOwner(owner));

        //TEST LIST DOCUMENTS FILTERED BY NIVEAU AND MATIERE
        System.out.println("=> La liste des documents filtrée par niveau et matière:\n" + ds.filterByNiveauMatiere("3A", "PiDev"));

        //TEST SIGNALER DOCUMENT
        ds.signalerDocument(url);

        //TEST PIN DOCUMENT
        DocumentFavoris fave = new DocumentFavoris(user_id, url.getId());
        dfs.pinDocument(fave);

        //TEST UNPIN DOCUMENT
        dfs.unpinDocument(fave);

        //TEST LIST FAVE DOCS
        System.out.println("=> La liste des documents favoris:\n" + dfs.listFaves(user_id));
        
        //TEST URL TO PDF
        try {
            ds.convertUrlToPdf(url.getNom());
        } catch (InterruptedException | IOException ex) {
            System.out.println(ex.getMessage());
        }

        //TEST SEND DOC VIA EMAIL
        ds.sendDocViaEmail(url);
        ds.sendDocViaEmail(doc);

        //TEST LIST REPORTED DOCS
        System.out.println("=> La liste des documents signalés:\n" + ds.listReportedDocs());
        
        //TEST APERCU DOCUMENT
        ds.apercuDocument(doc);
        ds.apercuDocument(url);
    }
    }*/
    
   
        
   
    public static void Admin(){
    User admin = new User();
    }
    public static void c(){
        Niveau x=new Niveau();
        x.setId("3A");
        NiveauService cs=new NiveauService();
        MessageService y=new MessageService();
        Message m=new Message();
        m.setContent("aaa aa bbb cc tt xx");
        
       y.switcher(m);
          
       }

    public static void statics(){
                //"[\"ROLE_STUDENT\",\"ROLE_RESPONSABLEC\"]"
                statics sc = new statics();
                 System.out.println(sc.numberOfPubsByCategory(1));
                //role : "[\"ROLE_ADMIN\"]"
             //  System.out.println(sc.numberOfStudents("[\"ROLE_STUDENT\",\"ROLE_RESPONSABLEC\"]"));
               //  System.out.println(sc.numberOfOffreEmploi());
              //System.out.println(sc.numberOfLikes(27));
            }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            if(Session.getUsername()==null){
            Parent parent = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/Login.fxml"));

           // Parent parent = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/HomeBack.fxml"));

            Scene scene = new Scene(parent);
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        else{
        Parent parent = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/AllAdmins.fxml"));

           // Parent parent = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/HomeBack.fxml"));

            Scene scene = new Scene(parent);
            primaryStage.setScene(scene);
            primaryStage.show();
        }
          /*  Parent parent = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/AllAdmins.fxml"));

           // Parent parent = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/HomeBack.fxml"));

            Scene scene = new Scene(parent);
            primaryStage.setScene(scene);
            primaryStage.show();*/
        } catch (IOException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
