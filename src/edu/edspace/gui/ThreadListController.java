/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.edspace.gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import edu.edspace.entities.Thread;
import edu.edspace.services.ThreadService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author 21656
 */
public class ThreadListController implements Initializable {

    
    ObservableList<Thread> Threads = FXCollections.observableArrayList();
    @FXML
    private AnchorPane ap;
    @FXML
    private VBox vmain;
    int user = 1;
    int admin = 0;
    final List<Thread> th = new ArrayList();
    @FXML
    private Hyperlink previous;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
       ThreadService threadService = new ThreadService();
       List<Thread>ts = threadService.listThreads();
        for (int i = 0;i<ts.size();i++)
       {
           th.add(ts.get(i));
       }
       
        
       Threads.clear();
       Threads.addAll(threadService.listThreads());
       vmain.setSpacing(10);
       if(ts.size()==0){
           vmain.getChildren().add(new Text("Empty"));
       }
       for (int i = 0;i<ts.size();i++)
       {
           int q = i;
           Thread t = new Thread();
          
           t = ts.get(i);
           System.out.println(ts.get(i).getUser());
           VBox v = new VBox();
           HBox hb = new HBox();
           Hyperlink h = new Hyperlink();
           Label l = new Label();
           Button del = new Button("Delete");
           del.setStyle("-fx-background-color: red; -fx-text-fill: white;");
           Button up = new Button("Update");
           up.setStyle("-fx-background-color: gray; -fx-text-fill: white;");
           l.setText(t.getPostDate());
           h.setText(t.getQuestion());
           h.setStyle("-fx-font-size: 21;");
           v.getChildren().add(h);
           up.setOnAction(e->{
               FXMLLoader loader = new FXMLLoader(getClass().getResource("newThread.fxml"));
               try {
                   Parent root1 = loader.load();
                   NewThreadController TC = loader.getController();
                   TC.update(th.get(q));
                   vmain.getScene().setRoot(root1);
               } catch (IOException ex) {
                   Logger.getLogger(ThreadListController.class.getName()).log(Level.SEVERE, null, ex);
               }
           });
           HBox ha= new HBox();
           if(t.getUser()==this.user){
               ha.getChildren().add(up);
           }
           Text tx= new Text();
           tx.setText("Added by "+t.getUser());
           if(this.user == t.getUser() || this.admin == 1){
           ha.getChildren().add(del);
            del.setOnAction(e->{
               Alert a = new Alert(Alert.AlertType.CONFIRMATION);
               a.setTitle("Confirmation");
               a.setHeaderText(null);
               a.setContentText("Are you sure you want to delete" + th.get(q).getQuestion());
              
               Optional<ButtonType> action = a.showAndWait();
               if(action.get() == ButtonType.OK){
                   threadService.deleteThread(th.get(q));
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("ThreadList.fxml"));
        try {
            Parent root = loader.load();
            del.getScene().setRoot(root);
            
        } catch (IOException ex) {
            Logger.getLogger(ListTopicsController.class.getName()).log(Level.SEVERE, null, ex);
        }
                    }
          
           });
          }
           
           
           ha.setSpacing(5);
           ha.setStyle("-fx-padding: 0 10 0;");
           v.getChildren().addAll(ha);
          
           h.setOnAction(
              new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Thread.fxml"));
               try {
                   Parent root1 = loader.load();
                   ThreadController TC = loader.getController();
                   TC.setThread(th.get(q).getQuestion(),th.get(q).getId());
                   vmain.getScene().setRoot(root1);
               } catch (IOException ex) {
                   Logger.getLogger(ThreadListController.class.getName()).log(Level.SEVERE, null, ex);
               }
            
            
        }
    });
           l.setStyle("-fx-padding: 0 120 0;");
           l.setTextFill(Color.GREEN);
           
           v.getChildren().add(l);
           v.setBorder(new Border(new BorderStroke(Color.GREEN, 
            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
           vmain.getChildren().add(v);
       }
       
           }    

    @FXML
    private void addNewThread(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("newThread.fxml"));
            Parent root = loader.load();
            vmain.getScene().setRoot(root);
            
        } catch (IOException ex) {
            Logger.getLogger(ThreadListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public  void setThread(Thread t){
        
    }
}
