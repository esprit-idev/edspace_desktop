/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.edspace.gui;

import edu.edspace.entities.User;
import edu.edspace.services.AdminService;
import edu.edspace.services.StudentService;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author YOOSURF
 */
public class StudentClasseController implements Initializable {

    @FXML
    private TableColumn<User, String> nom;
    @FXML
    private TableColumn<User, String> prenom;
    @FXML
    private TableColumn<User, String> email;
    @FXML
    private TableView<User> tablestu;
    @FXML
    private Button btn;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private ImageView home;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         StudentService SS= new StudentService();
       SS.listStudentByClasse() ;
         ObservableList<User> list = SS.listStudentByClasse();
        
        nom.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
        prenom.setCellValueFactory(new PropertyValueFactory<User, String>("prenom"));
        email.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
       // id.setCellValueFactory(new PropertyValueFactory<User, String>("id"));
        tablestu.setItems(list);
        
         File fileLogo = new File("images/home_grey.png");
        Image logoI = new Image(fileLogo.toURI().toString());
        home.setImage(logoI);
        // TODO
    }    

    @FXML
    private void home(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/FrontHome.fxml"));
            Parent root = loader.load();
            rootPane.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(StudentClasseController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
