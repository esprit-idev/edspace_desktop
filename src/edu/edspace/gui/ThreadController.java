/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.edspace.gui;


import edu.edspace.entities.Reponse;
import edu.edspace.entities.Session;
import edu.edspace.entities.User;
import edu.edspace.services.ReponseService;
import edu.edspace.services.ThreadService;
import edu.edspace.services.UserService;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.VPos;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import edu.edspace.entities.Thread;
import edu.edspace.services.MailService;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author 21656
 */
public class ThreadController implements Initializable {

    @FXML
    private Text tquestion;
    @FXML
    private VBox vbox;
    
    private String thread;
    public int id;
    @FXML
    private ScrollPane sp;
    @FXML
    private TextField tfReponse;
    @FXML
    private Button addBtn;
    @FXML
    private Hyperlink previous;
    private int admin =0 ;
    final List<Reponse> re = new ArrayList();
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Text error;
    @FXML
    private ImageView logo_iv;
    @FXML
    private ImageView pdf;
    @FXML
    private ImageView google;
    String[] tab = {"Shit","Zah","fk"};
    @FXML
    private Text bad;
    @FXML
    private ImageView home;
    public boolean checkForBadWords(String t){
        boolean valid = false;
        
        for(int i = 0; i<tab.length;i++){
            if(t.toUpperCase().indexOf(tab[i].toUpperCase())>=0){
                valid = true;
                
                
                 break;
            }
            else{
                valid = false;
            }
        }
       return valid;
    }
    
    public void setThread(String te, int id){
        google.setOnMouseClicked(e->{
            ThreadService threadService= new ThreadService();
            Thread the = new Thread();
            the.setQuestion(tquestion.getText());
            threadService.search(the);
        });
        pdf.setOnMouseClicked(e->{
            ThreadService threadService= new ThreadService();
            Thread the = new Thread();
            the.setQuestion(tquestion.getText());
            threadService.searchPDF(the);
        });
        initImages();
        home.setOnMouseClicked(e->{
             if(Session.getRoles().equals("[\"ROLE_ADMIN\"]")){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ThreadList.fxml"));
        try {
            
            Parent root = loader.load();
            previous.getScene().setRoot(root);
            
        } catch (IOException ex) {
            Logger.getLogger(ListTopicsController.class.getName()).log(Level.SEVERE, null, ex);
        }}
                else{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FrontHome.fxml"));
               try {
                   Parent root1 = loader.load();
                   home.getScene().setRoot(root1);
               } catch (IOException ex) {
                   Logger.getLogger(ThreadListController.class.getName()).log(Level.SEVERE, null, ex);
               }
        }});

        previous.setOnAction(e->{
            if(Session.getRoles().equals("[\"ROLE_ADMIN\"]")){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ThreadList.fxml"));
        try {
            
            Parent root = loader.load();
            previous.getScene().setRoot(root);
            
        } catch (IOException ex) {
            Logger.getLogger(ListTopicsController.class.getName()).log(Level.SEVERE, null, ex);
        }}
                else{
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("FrontThread.fxml"));
        try {
            
            Parent root = loader.load();
            previous.getScene().setRoot(root);
            
        } catch (IOException ex) {
            Logger.getLogger(ListTopicsController.class.getName()).log(Level.SEVERE, null, ex);
        }
                        }
        });
        this.thread = te;
        this.id=id;
        
        tquestion.setText(thread);
        System.out.println(this.id);
       ReponseService reponseService = new ReponseService();
       ThreadService threadService = new ThreadService();
       UserService userService = new UserService();
       List<Reponse> reps = reponseService.getReponsesByThread(id);
      for(int p = 0; p<reps.size();p++){
          re.add(reps.get(p));
      }
       
       //tquestion.setText(thread.getQuestion());
       if(reps.size() == 0){
           sp.setContent(new Text("Empty"));
           
       }
       addBtn.setOnAction(e->{
               if(tfReponse.getText().length()==0)
               {
                   
                   error.setVisible(true);
               }
               else{
                    if(checkForBadWords(tfReponse.getText())==true){
                       bad.setVisible(true);
                       tfReponse.setText(null);
                       tfReponse.setBorder(Border.stroke(Color.RED));
                       tfReponse.setPromptText("Replace the answer");
            }else{
              reponseService.addReponse(new Reponse(tfReponse.getText(),this.id,1));
              FXMLLoader loader = new FXMLLoader(getClass().getResource("Thread.fxml"));
               try {
                   Parent root1 = loader.load();
                   ThreadController TC = loader.getController();
                   TC.setThread(this.thread,this.id);
                  addBtn.getScene().setRoot(root1);
               } catch (IOException ex) {
                   Logger.getLogger(ThreadListController.class.getName()).log(Level.SEVERE, null, ex);
               }
           }}});
       for(int i = 0; i<reps.size();i++){
           
           int q = i;
           Text u = new Text();
           TextArea t = new TextArea();
           Text td = new Text();
          
           Circle c = new Circle();
           c.setCenterX(100);
           c.setCenterY(100);
           c.setRadius(15);
           c.setStyle("-fx-stroke: red; -fx-fill: green;");
           Reponse r = new Reponse();
           r = reps.get(i);
           u.setText(String.valueOf("user "+r.getUser()));
           t.setText(r.getReply());
           t.setEditable(false);
           t.setStyle("-fx-text-fill: black; -fx-background-color: #D2D2D2;-fx-border-radius: 10; -fx-background-radius: 10;");
           
           
           td.setText(r.getReplyDate());
           td.setTextAlignment(TextAlignment.RIGHT);
           td.setStyle("-fx-padding: 50px;");
           vbox.getChildren().addAll(u,c,t,td);
           if(this.admin == 1){
               Button b = new Button("delete");
               b.setOnAction(e->{
                   
                   Alert a = new Alert(Alert.AlertType.CONFIRMATION);
               a.setTitle("Confirmation");
               a.setHeaderText(null);
               a.setContentText("Are you sure you want to delete" + re.get(q).getReply());
              
               Optional<ButtonType> action = a.showAndWait();
               if(action.get() == ButtonType.OK){
                   reponseService.deleteReponse(re.get(q));
                   if(this.admin==1){
                   FXMLLoader loader = new FXMLLoader(getClass().getResource("Thread.fxml"));
               try {
                   Parent root1 = loader.load();
                   ThreadController TC = loader.getController();
                   TC.setThread(this.thread,this.id);
                  addBtn.getScene().setRoot(root1);
               } catch (IOException ex) {
                   Logger.getLogger(ThreadListController.class.getName()).log(Level.SEVERE, null, ex);
               }
                    
               }
               }
               else {
                   FXMLLoader loader = new FXMLLoader(getClass().getResource("FrontThread.fxml"));
               try {
                   Parent root1 = loader.load();
                   ThreadController TC = loader.getController();
                   TC.setThread(this.thread,this.id);
                  addBtn.getScene().setRoot(root1);
               } catch (IOException ex) {
                   Logger.getLogger(ThreadListController.class.getName()).log(Level.SEVERE, null, ex);
               }
               }
               });
            vbox.getChildren().add(b);
           sp.setContent(vbox);
           
           addBtn.setOnMouseClicked(e->{
               if(checkForBadWords(tfReponse.getText())==true){
                       bad.setVisible(true);
                       tfReponse.setText(null);
                       tfReponse.setBorder(Border.stroke(Color.RED));
                       tfReponse.setPromptText("Replace the answer");
            }else{
              
              reponseService.addReponse(new Reponse(tfReponse.getText(),this.id,6));
              
              Thread thread = threadService.getThread(id);
              UserService us = new UserService();
              User user = us.find(thread.getUser());
              
              FXMLLoader loader = new FXMLLoader(getClass().getResource("Thread.fxml"));
               try {
                   
                   Parent root1 = loader.load();
                   ThreadController TC = loader.getController();
                   TC.setThread(this.thread,this.id);
                  addBtn.getScene().setRoot(root1);
               } catch (IOException ex) {
                   Logger.getLogger(ThreadListController.class.getName()).log(Level.SEVERE, null, ex);
               }
           }
           });
           
           }
    }
   
    }
   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initImages();
        previous.setOnAction(e->{
            if(this.admin==1){
           FXMLLoader loader = new FXMLLoader(getClass().getResource("ThreadList.fxml"));
        try {
            
            Parent root = loader.load();
            previous.getScene().setRoot(root);
            
        } catch (IOException ex) {
            Logger.getLogger(ListTopicsController.class.getName()).log(Level.SEVERE, null, ex);
        }
            }
            else{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FrontThread.fxml"));
        try {
            
            Parent root = loader.load();
            previous.getScene().setRoot(root);
            
        } catch (IOException ex) {
            Logger.getLogger(ListTopicsController.class.getName()).log(Level.SEVERE, null, ex);
        }
            }
       });
       }
    public void initImages() {
        File fileLogo = new File("images/logo1.png");
        Image logoI = new Image(fileLogo.toURI().toString());
        
        File fileHome = new File("images/home_grey.png");
        Image homeI = new Image(fileHome.toURI().toString());
        
        File fileTab = new File("images/announcement_grey.png");
        Image tabI = new Image(fileTab.toURI().toString());
        
        File fileLevel = new File("images/level_grey.png");
        Image levelI = new Image(fileLevel.toURI().toString());
        
        File fileClass = new File("images/class-management_grey.png");
        Image classI = new Image(fileClass.toURI().toString());
        
        File fileBook = new File("images/book_grey.png");
        Image bookI = new Image(fileBook.toURI().toString());
        
        File fileForum = new File("images/forum2_grey.png");
        Image forumI = new Image(fileForum.toURI().toString());
        
        File fileOffre = new File("images/briefcase_grey.png");
        Image offreI = new Image(fileOffre.toURI().toString());
        
        File fileDocs = new File("images/file_grey.png");
        Image docsI = new Image(fileDocs.toURI().toString());

        File fileUsers = new File("images/users_grey.png");
        Image usersI = new Image(fileUsers.toURI().toString());
        
        File fileClub = new File("images/org_grey.png");
        Image clubI = new Image(fileClub.toURI().toString());

        File fileOut = new File("images/logout_grey.png");
        Image outI = new Image(fileOut.toURI().toString());
        
        File fileReport = new File("images/report_red.png");
        Image reportI = new Image(fileReport.toURI().toString());
        
        File filePdf = new File("images/pdf.png");
        Image pdfl = new Image(filePdf.toURI().toString());
        
        File fileg = new File("images/google.png");
        Image gl = new Image(fileg.toURI().toString());
        
        logo_iv.setImage(logoI);
        pdf.setImage(pdfl);
        google.setImage(gl);
        home.setImage(homeI);
    }

    }    
    

