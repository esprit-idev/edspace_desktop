/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.edspace.gui;

import com.mysql.jdbc.Connection;
import edu.edspace.entities.User;
import edu.edspace.services.StudentService;
import edu.edspace.utils.MyConnection;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author YOOSURF
 */
public class AllStudentsController implements Initializable {

    @FXML
    private TableColumn<User, String> NStudent;
    @FXML
    private TableColumn<User, String> PStudent;
    @FXML
    private TableColumn<User,String> EStudent;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnAddS;

    
    Connection connection = null;
    @FXML
    private TableView<User> tableStudent;
    @FXML
    private Label gett;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     // MyConnection.getInstance().getCnx();
        //initImages();
        StudentService SS= new StudentService();
        SS.listStudent();
         ObservableList<User> list = SS.listStudent();
        
        NStudent.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
        PStudent.setCellValueFactory(new PropertyValueFactory<User, String>("prenom"));
        EStudent.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
        tableStudent.setItems(list);
    }    

    @FXML
    private void getAddView(ActionEvent event) {
        
        try {
            /* Parent parent = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/AddStudent.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show(); */
            
            Parent blog_parent = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/AddStudent.fxml"));
        Scene blog_scene = new Scene(blog_parent);
        Stage app_stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        
        app_stage.setScene(blog_scene);
        app_stage.show();
        } catch (IOException ex) {
            Logger.getLogger(AllStudentsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }

    @FXML
    private void handleMouseAction(MouseEvent event) {
        User blog = tableStudent.getSelectionModel().getSelectedItem();
        
       //System.out.println(blog.getEmail());
       
        //return blog.getUsername() ;
        
        gett.setText(blog.getUsername());
    }

    @FXML
    private void btnUpdate(ActionEvent event) {
        
        
    }

    @FXML
    private void btnDelete(ActionEvent event) {
        
            StudentService SS= new StudentService();
            SS.supprimerPersonne(gett.getText());
           
        SS.listStudent();
         ObservableList<User> list = SS.listStudent();
        
        NStudent.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
        PStudent.setCellValueFactory(new PropertyValueFactory<User, String>("prenom"));
        EStudent.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
        tableStudent.setItems(list);
           
      
    }
    
}
