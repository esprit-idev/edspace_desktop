/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.edspace.tests;

import java.text.SimpleDateFormat;
import java.util.Date;

import edu.edspace.entities.CategoryEmploi;
import edu.edspace.entities.CategoryNews;
import edu.edspace.entities.Club;
import edu.edspace.entities.ClubCategory;
import edu.edspace.entities.ClubPub;
import edu.edspace.services.ClubCategService;
import edu.edspace.services.ClubPubService;
import edu.edspace.services.ClubService;
import edu.edspace.entities.Classe;
import edu.edspace.entities.Document;
import edu.edspace.entities.DocumentFavoris;
import edu.edspace.entities.Emploi;
import edu.edspace.entities.Matiere;
import edu.edspace.entities.News;
import edu.edspace.entities.Message;
import edu.edspace.entities.Reponse;
import edu.edspace.services.DocumentFavorisService;
import edu.edspace.entities.Niveau;
import edu.edspace.services.ClasseService;
import edu.edspace.services.DocumentService;
import edu.edspace.services.EmploiCategoryService;
import edu.edspace.services.EmploiService;
import edu.edspace.services.MatiereService;
import edu.edspace.services.NewsCategoryService;
import edu.edspace.services.NewsService;
import edu.edspace.services.MessageService;
import edu.edspace.services.ThreadService;
import edu.edspace.utils.MyConnection;
import edu.edspace.utils.Statics;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import edu.edspace.entities.Thread;
import edu.edspace.entities.ThreadType;
import edu.edspace.entities.User;
import edu.edspace.services.ReponseService;
import edu.edspace.services.TopicService;
import edu.edspace.services.statics;

/**
 *
 * @author MeriamBI
 */
public class MainClass {
    
    public static void main(String[] args) {
        MyConnection.getInstance().getCnx();
       // gestionClasse();
        statics();
    }
    
    
    public static void ClubPub() {
        ClubPubService clubPubService = new ClubPubService();
        System.out.println(clubPubService.displayClubPubs(5));
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
    public static void gestionTopics(){
        TopicService TopicService = new TopicService();
        ThreadType t = new ThreadType("test FX",false);
        TopicService.addTopic(t);
        System.out.println("////////////////////////////////");
        System.out.println(TopicService.listTopics());
        ThreadType t1 = TopicService.getTopic(2);
        TopicService.deleteTopic(t1);
        System.out.println(TopicService.getThreadsByTopic(t1));
    }
    
    public static void gestionReponses(){
        ReponseService ReponseService = new ReponseService();
        Reponse r = new Reponse("Testing reponses",4,1);
        ReponseService.addReponse(r);
        System.out.println("////////////////////////////////");
        System.out.println(ReponseService.listReponses());
        System.out.println(ReponseService.getReponsesByThread(3));
        Reponse r1 = ReponseService.getReponse(19);
        ReponseService.deleteReponse(r1);
    }
    
    
    public static void gestionThread() {
        ThreadService ThreadService = new ThreadService();
        //System.out.println("Test Thread add");
        Thread t = new Thread("How to FXJ", 1, 1);
        //System.out.println("=>Threads:\n" + ThreadService.listThreads());
        //System.out.println("///////////////////////////////////////////////////////////////");
        //String oldId = t.getQuestion();
        //t.setQuestion("Updated");
        //ThreadService.modifierThread(t, oldId);
        //ThreadService.deleteThread(t);
        //System.out.println(ThreadService.getThread(1));
        //ThreadService.addThread(t);
        Thread t2 = ThreadService.getThread(2);
        ThreadService.setVerified(t2);
       System.out.println(ThreadService.getThread(2));

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
        File fichier = new File(Statics.myDocs + "logo.png"); //to_change
        Document doc = new Document(0, "logo.png", formatter.format(currentDate), owner, null, "", "3A", "matiereA", "", fichier);
        Document url = new Document(0, "urltest", formatter.format(currentDate), owner, "https://github.com/KnpLabs/snappy", null, "3B", "matiereB", "url", null);
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

        //TEST LIST FAVE DOCS
        System.out.println("=> La liste des documents favoris:\n" + dfs.listFaves(user_id));
        
        //TEST UNPIN DOCUMENT
        dfs.unpinDocument(fave);

        

        //TEST URL TO PDF
        try {
            ds.convertUrlToPdf("testt");
        } catch (InterruptedException | IOException ex) {
            System.out.println(ex.getMessage());
        }

        //TEST SEND DOC VIA EMAIL
        ds.sendDocViaEmail(url);
        ds.sendDocViaEmail(doc);

        //TEST LIST REPORTED DOCS
        //TEST LIST FAVE DOCS
        System.out.println("=> La liste des documents signalés:\n" + ds.listReportedDocs());
    }*/

    public static void news(){
        NewsService pubs = new NewsService();
        System.out.println(pubs.AllNews());
        News p = new News("TestDelete2", "Test", "Test", "1","2021/03/1", "yo.png");
        pubs.addNews(p);
        System.out.print("added successfully");
        // pubs.updateNews(p);
        // System.out.println(pubs.AllNews());
        // System.out.println(p.getId());
        // pubs.deleteNews(26);
        // System.out.println(pubs.AllNews());

    }
    public static void emplois(){
        EmploiService emps = new EmploiService();
        System.out.println(emps.AllEmplois());
    //    Emploi e = new Emploi("T", "Test","1", "2021/03/1", "yooo.png");
    //     emps.addEmploi(e);
    //     System.out.println("update an offer");
    //   emps.updateEmploi(e);
    //   System.out.println(emps.AllEmplois());
   // emps.deleteOffer(54);
    //System.out.println("delete offer");
    //System.out.println(emps.AllEmplois());
    }
    public static void newsCategory(){
        // NewsCategoryService nCat = new NewsCategoryService();
        // System.out.println(nCat.AllCats());
        // CategoryNews c = new CategoryNews("Important");
        // nCat.addCat(c);
        // System.out.println(nCat.AllCats());
        // nCat.updateCat(c);
        // nCat.deleteCat(2);
        // System.out.println(nCat.AllCats());
    }
    public static void emploisCategory(){
        // EmploiCategoryService nCat = new EmploiCategoryService();
        // System.out.println(nCat.AllCats());
        // CategoryEmploi c = new CategoryEmploi();
        // nCat.addCat(c);
        // System.out.println(nCat.AllCats());
        // nCat.updateCat(c);
        // nCat.deleteCat(2);
        // System.out.println(nCat.AllCats());
    }

    public static void gestionClasse() {
        System.out.println("\n******************TEST CRUD CLASSE******************");
       ClasseService cs =new ClasseService();
       Niveau n=new Niveau("3A");
       Classe cc=new Classe();
       cc.setId(14);
       cc.setClasse("22");
       cc.setNiveau(n);
       cs.supprimerClasse(cc);
       System.out.println(cs.getOneById(14));
       
    }
    public static void gestionMessage() {
        System.out.println("\n******************TEST CRUD MESSAGE******************");
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
        Date date = new Date();
        System.out.println(formatter.format(date)); 
        User u = new User();
        u.setId(1);
        Classe c =new Classe();
        c.setId(14);
        Message m = new Message();
        m.setUser(u);
        m.setContent("esdfs");
        m.setClasse(c);
        //java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        //m.setPostDate(sqlDate); 
        
        MessageService cs =new MessageService();
        cs.ajouterMessage(m);

    }

    public static void statics(){
        //"[\"ROLE_STUDENT\",\"ROLE_RESPONSABLEC\"]"
        statics sc = new statics();
        //  System.out.println(sc.numberOfPubsByCategory(5));
        //role : "[\"ROLE_ADMIN\"]"
      //  System.out.println(sc.numberOfStudents("[\"ROLE_STUDENT\",\"ROLE_RESPONSABLEC\"]"));
        // System.out.println(sc.numberOfOffreEmploi());
      //  System.out.println(sc.numberOfLikes(27));
    }
    
    
}
