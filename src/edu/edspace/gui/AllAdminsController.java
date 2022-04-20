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
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author YOOSURF
 */
public class AllAdminsController implements Initializable {

    @FXML
    private TextField nomAdmin;
    @FXML
    private TextField prenomAdmin;
    @FXML
    private TextField emailAdmin;
    @FXML
    private TextField mdpAdmin;
    @FXML
    private Button btnaddAdmin;
    @FXML
    private TableColumn<User, String> nom;
    @FXML
    private TableColumn<User, String> prenom;
    @FXML
    private TableColumn<User, String> email;
    @FXML
    private TableView<User> tableAdmin;
    @FXML
    private ImageView logo;
    @FXML
    private Button btndelete;
    @FXML
    private Label gett;
    @FXML
    private ImageView delicon;
    @FXML
    private Button btnUpdate;
    @FXML
    private TableColumn<User, String> id;
    @FXML
    private ImageView update;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        AdminService SS= new AdminService();
       SS.listAdmin() ;
         ObservableList<User> list = SS.listAdmin();
        
        nom.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
        prenom.setCellValueFactory(new PropertyValueFactory<User, String>("prenom"));
        email.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
        id.setCellValueFactory(new PropertyValueFactory<User, String>("id"));
        tableAdmin.setItems(list);
        
         File fileLogo = new File("images/logo1.png");
        Image logoI = new Image(fileLogo.toURI().toString());
        logo.setImage(logoI);
        File filedel = new File("images/delete.png");
        Image del = new Image(filedel.toURI().toString());
        delicon.setImage(del);
         File fileU = new File("images/update.png");
        Image updat = new Image(fileU.toURI().toString());
         update.setImage(updat);
    }    

    @FXML
    private void Add(ActionEvent event) {
       Boolean error = false; 
        if(nomAdmin.getText().equals("")) {
          Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Fill All DATA");
            alert.showAndWait();
        error = true;
        }
        
        if (!error) {
    User stu = new User();
    // stu.setId(Integer.parseInt(SId.getText()));
     stu.setPrenom(nomAdmin.getText());
     stu.setUsername(prenomAdmin.getText());
     stu.setEmail(emailAdmin.getText());
     stu.setPassword(mdpAdmin.getText());
    AdminService SS= new AdminService();
       
   // SS.AjouterUser(stu);
    SS.ajouterAdmin(stu);
    
    SS.listAdmin() ;
         ObservableList<User> list = SS.listAdmin();
        
        nom.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
        prenom.setCellValueFactory(new PropertyValueFactory<User, String>("prenom"));
        email.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
        tableAdmin.setItems(list);
    }}
    
     public static boolean validate(String email) {
        final String EMAIL_VERIFICATION = "^([\\w-\\.]+)@([\\w\\.]+)\\.([a-z]){2,}$";
        Pattern pattern = Pattern.compile(EMAIL_VERIFICATION);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @FXML
    private void delete(ActionEvent event) {
        AdminService SS= new AdminService();
            SS.supprimerAdmin(gett.getText());
            
            SS.listAdmin() ;
         ObservableList<User> list = SS.listAdmin();
        
        nom.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
        prenom.setCellValueFactory(new PropertyValueFactory<User, String>("prenom"));
        email.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
        tableAdmin.setItems(list); 
    }

    @FXML
    private void handleMouseAction(MouseEvent event) {
        
        User blog = tableAdmin.getSelectionModel().getSelectedItem();
      String s = String.valueOf(blog.getId()) ;
       // gett.setText(blog.getUsername());
        gett.setText(s);
       nomAdmin.setText(blog.getUsername());
       prenomAdmin.setText(blog.getPrenom());
       emailAdmin.setText(blog.getEmail());
       mdpAdmin.setText(blog.getPassword());
    }

    @FXML
    private void Update(ActionEvent event) {
        User stu = new User();
    // stu.setId(Integer.parseInt(SId.getText()));
     stu.setPrenom(nomAdmin.getText());
     stu.setUsername(prenomAdmin.getText());
     stu.setEmail(emailAdmin.getText());
     stu.setPassword(mdpAdmin.getText());
                AdminService SS= new AdminService();
                SS.updateAdmin(stu, gett.getText());
                
                 ObservableList<User> list = SS.listAdmin();
        
        nom.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
        prenom.setCellValueFactory(new PropertyValueFactory<User, String>("prenom"));
        email.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
        tableAdmin.setItems(list); 

    }
    
}
