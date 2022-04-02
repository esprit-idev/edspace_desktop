/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.edspace.tests;

import edu.edspace.entities.Document;
import edu.edspace.entities.Matiere;
import edu.edspace.services.DocumentService;
import edu.edspace.services.MatiereService;
import edu.edspace.services.ThreadService;
import edu.edspace.utils.MyConnection;
import edu.edspace.utils.Statics;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import edu.edspace.entities.Thread;

/**
 *
 * @author MeriamBI
 */
public class MainClass {

    public static void main(String[] args) {
        MyConnection.getInstance().getCnx();
        gestionThread();
        //gestionMatiere();
    }
    
    public static void gestionThread(){
        ThreadService  ThreadService = new  ThreadService();
        System.out.println("Test Thread add");
        Thread t = new Thread("How to FXJ",1,1);
        System.out.println("=>Threads:\n" + ThreadService.listThreads());
        System.out.println("///////////////////////////////////////////////////////////////");
        String oldId = t.getQuestion();
        t.setQuestion("Updated");
        ThreadService.modifierThread(t,oldId);
        ThreadService.deleteThread(t);
        //ThreadService.addThread(t);
    }
    public static void gestionMatiere() {
        System.out.println("******************TEST CRUD MATIERE******************");
        MatiereService ms = new MatiereService(); //instanciation du service MatiereService
        //TEST AFFICHAGE LISTE DES MATIERES
        System.out.println("=> La liste des matières:\n" + ms.listeMatieres());

        //TEST AJOUT MATIERE
        Matiere m = new Matiere("testfx", "3A");
        ms.ajouterMatiere(m);
        System.out.println("=> La liste des matières après ajout:\n" + ms.listeMatieres());

        //TEST MODIFIER MATIERE
        String oldId = m.getId();
        m.setId("testfxupdate");
        m.setNiveau("3B");
        ms.modifierMatiere(m, oldId);
        System.out.println("=> La liste des matières après modification:\n" + ms.listeMatieres());

        //TEST SUPPRIMER MATIERE
        ms.supprimerMatiere(m);
        System.out.println("=> La liste des matières après suppression:\n" + ms.listeMatieres());
    }

    public static void gestionDocument() {
        System.out.println("\n******************TEST CRUD DOCUMENT******************");
        DocumentService ds = new DocumentService(); //instanciation du service DocumentService
        //TEST AFFICHAGE LISTE DES DOCUMENTS
        System.out.println("=> La liste des documents:\n" + ds.listeDocuments());

        //TEST AJOUT DOCUMENT
        String owner = "Anas Houissa"; //to_change
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime currentDate = LocalDateTime.now();
        File fichier = new File(Statics.myDocs + "logo.png"); //to_change
        Document doc = new Document(0, "logo.png", formatter.format(currentDate), owner, null, "", "3A", "matiereA", "", fichier);
        ds.ajouterDocument(doc);
        System.out.println("=> La liste des documents après ajout:\n" + ds.listeDocuments());

        //TEST MODIFIER MATIERE
        doc.setMatiere("matiereB");
        doc.setNiveau("3B");
        ds.modifierDocument(doc);
        System.out.println("=> La liste des documents après modification:\n" + ds.listeDocuments());
        //TEST SUPPRIMER DOCUMENT
        ds.supprimerDocument(doc);
        System.out.println("=> La liste des documents après suppression:\n" + ds.listeDocuments());
    }
}
