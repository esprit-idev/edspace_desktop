/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.edspace.gui;


import edu.edspace.entities.Reponse;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

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
    private int admin = 1;
    final List<Reponse> re = new ArrayList();
    @FXML
    private AnchorPane rootPane;
    
    public void setThread(String te, int id){
        
        previous.setOnAction(e->{
           FXMLLoader loader = new FXMLLoader(getClass().getResource("ThreadList.fxml"));
        try {
            
            Parent root = loader.load();
            previous.getScene().setRoot(root);
            
        } catch (IOException ex) {
            Logger.getLogger(ListTopicsController.class.getName()).log(Level.SEVERE, null, ex);
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
           addBtn.setOnAction(e->{
               
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
           });
       }
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
            vbox.getChildren().add(b);
           sp.setContent(vbox);
           
           addBtn.setOnAction(e->{
               
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
           });
           }
    }}
   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        previous.setOnAction(e->{
           FXMLLoader loader = new FXMLLoader(getClass().getResource("ThreadList.fxml"));
        try {
            
            Parent root = loader.load();
            previous.getScene().setRoot(root);
            
        } catch (IOException ex) {
            Logger.getLogger(ListTopicsController.class.getName()).log(Level.SEVERE, null, ex);
        }
       });
       }
    }    
    

