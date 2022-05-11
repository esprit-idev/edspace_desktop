/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.edspace.gui;

import edu.edspace.entities.Session;
import edu.edspace.entities.ThreadType;
import edu.edspace.services.ThreadService;
import edu.edspace.services.TopicService;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import edu.edspace.entities.Thread;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author 21656
 */
public class NewThreadController implements Initializable {

    @FXML
    private ChoiceBox<String> topic;
    @FXML
    private TextField tfQuestion;
    @FXML
    private Button save;
    int user = 1;
   

    /**
     * Initializes the controller class.
     */
    
    ObservableList<String> Topics = FXCollections.observableArrayList();
    @FXML
    private Label ftopic;
    @FXML
    private Hyperlink previous;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private ImageView out_iv;
    @FXML
    private ImageView logo_iv;
    @FXML
    private ImageView profile_iv;
    @FXML
    private Text error1;
    @FXML
    private Text error2;
    @FXML
    private Text bad;
    String[] tab = {"Shit","Zah"};
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
    public void update(Thread t){
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
        System.out.println(t.getId());
        tfQuestion.setText(t.getQuestion());
        save.setText("Update");
        save.setOnAction(e->{
            if(tfQuestion.getText().length()==0){
                error1.setVisible(true);
            }
                else{
                 if(checkForBadWords(tfQuestion.getText())==true){
                 bad.setVisible(true);
                       tfQuestion.setText(null);
                       tfQuestion.setBorder(Border.stroke(Color.RED));
                       tfQuestion.setPromptText("Replace the question");
            }
                else{
            t.setQuestion(tfQuestion.getText());
            ThreadService threadService = new ThreadService();
            threadService.modifierThread(t, t.getId());
            if(Session.getId()== t.getUser() || Session.getRoles().equals("[\"ROLE_ADMIN\"]")){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ThreadList.fxml"));
               try {
                   Parent root = loader.load();
                   
                   tfQuestion.getScene().setRoot(root);
               } catch (IOException ex) {
                   Logger.getLogger(ThreadListController.class.getName()).log(Level.SEVERE, null, ex);
               }
            }
            else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FrontThread.fxml"));
               try {
                   Parent root = loader.load();
                   
                   tfQuestion.getScene().setRoot(root);
               } catch (IOException ex) {
                   Logger.getLogger(ThreadListController.class.getName()).log(Level.SEVERE, null, ex);
               }
            }
            
         Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thread");
        alert.setContentText("Thread updated successfuly!");
        alert.showAndWait();
            }}});
        
        topic.setVisible(false);
        ftopic.setVisible(false);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
        TopicService topics = new TopicService();
        List<ThreadType> ts = topics.listTopics();
        for(int i = 0; i<ts.size();i++){
            ThreadType t = new ThreadType();
            t = ts.get(i);
           Topics.add(t.getId()+"- "+t.getContent());
        }
       topic.setItems(Topics);
    }    

    @FXML
    private void addThread(ActionEvent event) {
        if(tfQuestion.getText().length()== 0 && topic.getSelectionModel().getSelectedItem() == null){
            error1.setVisible(true);
            error2.setVisible(true);
        }else if(tfQuestion.getText().length()== 0 && topic.getSelectionModel().getSelectedItem() != null){
            error1.setVisible(true);
            error2.setVisible(false);
        }
        else if(topic.getSelectionModel().getSelectedItem() == null && (tfQuestion.getText().length()!=0)){
            error1.setVisible(false);
            error2.setVisible(true);
        }
       
        else {
            error1.setVisible(false);
            error2.setVisible(false);
            if(checkForBadWords(tfQuestion.getText())==true){
                 bad.setVisible(true);
                       tfQuestion.setText(null);
                       tfQuestion.setBorder(Border.stroke(Color.RED));
                       tfQuestion.setPromptText("Replace the question");
            }
            else{
        ThreadService threadService = new ThreadService();
        Thread t = new Thread();
        t.setQuestion(tfQuestion.getText());
        t.setUser(this.user);
        int content =Integer.parseInt((topic.getSelectionModel().getSelectedItem()).substring(0, topic.getSelectionModel().getSelectedItem().indexOf("-")));
        t.setThreadType(content);
        threadService.addThread(t);
         Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thread");
        alert.setContentText("Thread inserted successfuly!");
        alert.showAndWait();
        if(Session.getId()== t.getUser() || Session.getRoles().equals("[\"ROLE_ADMIN\"]")){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ThreadList.fxml"));
        try {
            
            Parent root = loader.load();
            topic.getScene().setRoot(root);
            
        } catch (IOException ex) {
            Logger.getLogger(ListTopicsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        else{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FrontThread.fxml"));
        try {
            
            Parent root = loader.load();
            topic.getScene().setRoot(root);
            
        } catch (IOException ex) {
            Logger.getLogger(ListTopicsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
            }
    }}

    @FXML
    private void logout(MouseEvent event) {
    }

    @FXML
    private void getProfile(MouseEvent event) {
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
        
        home.setImage(homeI);
    }
}
