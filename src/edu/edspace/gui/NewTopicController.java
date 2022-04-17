/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.edspace.gui;

import edu.edspace.entities.ThreadType;
import edu.edspace.services.TopicService;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author 21656
 */
public class NewTopicController implements Initializable {

    @FXML
    private TextField topicField;
    @FXML
    private Button addBtn;
    @FXML
    private Hyperlink homeLink;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void addTopic(ActionEvent event) {
        TopicService topicService = new TopicService();
        ThreadType t = new ThreadType();
        
        t.setContent(topicField.getText());
        t.setDisplay(false);
        
        topicService.addTopic(t);
        
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Topic");
        alert.setContentText("Topic inserted successfuly!");
        alert.showAndWait();
    }
    
}
