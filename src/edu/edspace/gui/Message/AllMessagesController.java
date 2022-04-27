/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.edspace.gui.Message;

import edu.edspace.entities.Message;
import edu.edspace.services.ClasseService;
import edu.edspace.services.MessageService;
import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * FXML Controller class
 *
 * @author aa
 */
public class AllMessagesController implements Initializable {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private ImageView out_iv;
    @FXML
    private ImageView home_iv;
    @FXML
    private ImageView logo_iv;
    @FXML
    private TextField url_tf;
    @FXML
    private ImageView back_iv;
    @FXML
    private Pane pane1;
    @FXML
    private TextField text;
    @FXML
    private Button send;
    @FXML
    private VBox vbox;
    MessageService ms=new MessageService();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        refresh();
        // TODO
    }    
    
    
    public void refresh(){
       setmes();
        setOthermes(); 
    }
    
    public void setmes(){
          for (Message temp : ms.listeMyMessageClasse(1)) {
              HBox hBox=new HBox();
              hBox.setAlignment(Pos.CENTER_LEFT);
              hBox.setPadding(new Insets(5,5,5,10));
                         System.out.println(temp);
                           Text text=new Text(temp.getContent());
                           TextFlow textFlow=new TextFlow(text);
                           textFlow.setStyle("-fx-background-color: rgb(233,233,235)"+ " -fx-background-radius: 20px");
                           textFlow.setPadding(new Insets(5,0,5,100));
                           hBox.getChildren().add(textFlow);
                           vbox.getChildren().add(hBox);
                           
                        
           // items.add(temp.getUsername()+" "+temp.getPrenom());
        }
        
    }
    
    
     public void addmes(){
       
              HBox hBox=new HBox();
              hBox.setAlignment(Pos.CENTER_LEFT);
              hBox.setPadding(new Insets(5,5,5,10));
                         
                           Text text1=new Text(text.getText());
                           TextFlow textFlow=new TextFlow(text1);
                           textFlow.setStyle("-fx-background-color: rgb(233,233,235)"+ " -fx-background-radius: 20px");
                           textFlow.setPadding(new Insets(5,0,5,100));
                           hBox.getChildren().add(textFlow);
                           vbox.getChildren().add(hBox);
                           
                        
           // items.add(temp.getUsername()+" "+temp.getPrenom());
        }
        
    
    
    
     public void setOthermes(){
          for (Message temp : ms.listeOtherMessageClasse(1)) {
              HBox hBox=new HBox();
              hBox.setAlignment(Pos.CENTER_LEFT);
              hBox.setPadding(new Insets(5,5,5,10));
                         System.out.println(temp);
                           Text text=new Text(temp.getContent());
                           TextFlow textFlow=new TextFlow(text);
                           textFlow.setStyle("-fx-background-color: rgb(233,233,235)"+ " -fx-background-radius: 20px");
                           textFlow.setPadding(new Insets(5,100,5,0));
                           hBox.getChildren().add(textFlow);
                           vbox.getChildren().add(hBox);
                           
                        
           // items.add(temp.getUsername()+" "+temp.getPrenom());
        }
        
    }
    
    

    @FXML
    private void getHome(MouseEvent event) {
    }

    @FXML
    private void getCentre(MouseEvent event) {
    }

    @FXML
    private void sendMessage(ActionEvent event) {
        ClasseService cs=new ClasseService();
        
        
        Message m = new Message();
        
        m.setClasse(cs.getOneById(21));
        m.setUser(ms.getuser(1));
        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        m.setPostDate(date);
        m.setContent(text.getText());
        ms.ajouterMessage(m);
        text.setText("");
        addmes();
        
    }
    
}
