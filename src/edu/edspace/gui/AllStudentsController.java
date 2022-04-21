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
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
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

    
    Connection connection = null;
    @FXML
    private TableView<User> tableStudent;
    @FXML
    private Label gett;
    @FXML
    private ImageView icondelete;
    @FXML
    private Button btndel;
    @FXML
    private Button btnAjout;
    @FXML
    private TextField nomStudent;
    @FXML
    private TextField prenomStudent;
    @FXML
    private TextField emailStudent;
    @FXML
    private TextField mdpStudent;
    @FXML
    private ImageView logo;
    @FXML
    private Text msgnom;
    @FXML
    private Text msgpre;
    @FXML
    private Text msgemvid;
    @FXML
    private Text msgmdpvid;
    @FXML
    private Text invalidmsg;
    @FXML
    private Text msgmin;
    @FXML
    private ImageView update;
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
        
         File filed = new File("images/delete.png");
        Image del = new Image(filed.toURI().toString());
        icondelete.setImage(del);
         File fileLogo = new File("images/logo1.png");
        Image logoI = new Image(fileLogo.toURI().toString());
         logo.setImage(logoI);
          File fileU = new File("images/logo1.png");
        Image updat = new Image(fileU.toURI().toString());
         update.setImage(updat);
    }    

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
        
        String s = String.valueOf(blog.getId()) ;
       // gett.setText(blog.getUsername());
        gett.setText(s);
       nomStudent.setText(blog.getUsername());
       prenomStudent.setText(blog.getPrenom());
       emailStudent.setText(blog.getEmail());
       mdpStudent.setText(blog.getPassword());
        
       // gett.setText(blog.getUsername());
    }

    @FXML
    private void btnUpdate(ActionEvent event) {
        
         User stu = new User();
    // stu.setId(Integer.parseInt(SId.getText()));
     stu.setPrenom(nomStudent.getText());
     stu.setUsername(prenomStudent.getText());
     stu.setEmail(emailStudent.getText());
     stu.setPassword(mdpStudent.getText());
                StudentService SS= new StudentService();
                SS.updateStudent(stu, gett.getText());
                
                ObservableList<User> list = SS.listStudent();
        
        NStudent.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
        PStudent.setCellValueFactory(new PropertyValueFactory<User, String>("prenom"));
        EStudent.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
        tableStudent.setItems(list);
        
        
    }

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

    @FXML
    private void del(ActionEvent event) {
         StudentService SS= new StudentService();
            SS.supprimerPersonne(gett.getText());
           
        SS.listStudent();
         ObservableList<User> list = SS.listStudent();
        
        NStudent.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
        PStudent.setCellValueFactory(new PropertyValueFactory<User, String>("prenom"));
        EStudent.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
        tableStudent.setItems(list);
        
    }

    @FXML
    private void btnajout(ActionEvent event) {
         Boolean error = false;
        
          if(nomStudent.getText().equals("")) {
              msgnom.setVisible(true);
               error = true;
}
          
          
          if(prenomStudent.getText().equals("")) {
              msgpre.setVisible(true);
               error = true;
}
          if(emailStudent.getText().equals("")) {
              msgemvid.setVisible(true);
               error = true;
}
          
          
          if (mdpStudent.getText().length() < 8) {
            msgmin.setVisible(true);
            error = true;
        } 
          if (!validate(emailStudent.getText())) {
                mdpStudent.setVisible(true);
                error = true;
            }
          
   if (!error) {
    User stu = new User();
    // stu.setId(Integer.parseInt(SId.getText()));
     stu.setPrenom(prenomStudent.getText());
     stu.setUsername(nomStudent.getText());
     stu.setEmail(emailStudent.getText());
     stu.setPassword(mdpStudent.getText());
     StudentService SS = new StudentService();
   // SS.AjouterUser(stu);
    SS.ajouterStudent(stu);
    
    SS.listStudent();
         ObservableList<User> list = SS.listStudent();
        
        NStudent.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
        PStudent.setCellValueFactory(new PropertyValueFactory<User, String>("prenom"));
        EStudent.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
        tableStudent.setItems(list);
    }}
    
     public static boolean validate(String email) {
        final String EMAIL_VERIFICATION = "^([\\w-\\.]+)@([\\w\\.]+)\\.([a-z]){2,}$";
        Pattern pattern = Pattern.compile(EMAIL_VERIFICATION);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
        
    }
    
}