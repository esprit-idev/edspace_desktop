/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.edspace.gui;

import edu.edspace.entities.ThreadType;
import edu.edspace.services.TopicService;
import edu.edspace.utils.MyConnection;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;

import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author 21656
 */
public class ListTopicsController implements Initializable {

    @FXML
    private Text test;
    @FXML
    private TableView<ThreadType> table;
    @FXML
    private TableColumn<ThreadType, String> content;

    ObservableList<ThreadType> Topics = FXCollections.observableArrayList();
    Connection connection = null;
    @FXML
    private Hyperlink previous;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       connection = MyConnection.getInstance().getCnx();
       content.setCellValueFactory(new PropertyValueFactory<ThreadType,String>("content"));
       TopicService t = new TopicService();
       Topics.clear();
       Topics.addAll(t.listTopics());
       System.out.println(Topics);
       table.setItems(Topics);
       
    }    

    @FXML
    private void redirectAddTopic(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("newTopic.fxml"));
        try {
            
            Parent root = loader.load();
            table.getScene().setRoot(root);
            
        } catch (IOException ex) {
            Logger.getLogger(ListTopicsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
