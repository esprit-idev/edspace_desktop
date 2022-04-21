/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.edspace.gui;

import com.mysql.jdbc.Connection;
import edu.edspace.entities.Session;
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
import javafx.scene.layout.AnchorPane;
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
    @FXML
    private AnchorPane rootPane;
    @FXML
    private ImageView logo_iv;
    @FXML
    private Button btnOverview;
    @FXML
    private ImageView home_iv;
    @FXML
    private Button btnNews;
    @FXML
    private ImageView tabaff_iv;
    @FXML
    private Button btnOrders;
    @FXML
    private ImageView users_iv;
    @FXML
    private Button btnCustomers;
    @FXML
    private ImageView niveaux_iv;
    @FXML
    private Button btnMenus;
    @FXML
    private ImageView classe_iv;
    @FXML
    private Button btnMatiere;
    @FXML
    private ImageView matieres_iv;
    @FXML
    private Button btnSettings;
    @FXML
    private ImageView club_iv;
    @FXML
    private Button btnEmploi;
    @FXML
    private ImageView offre_iv;
    @FXML
    private Button btnSignout1;
    @FXML
    private ImageView forum_iv;
    @FXML
    private Button btnCentrePartage;
    @FXML
    private ImageView centre_iv;
    @FXML
    private Button btnSignout3;
    @FXML
    private ImageView signOut_iv;
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
        /* File fileLogo = new File("images/logo1.png");
        Image logoI = new Image(fileLogo.toURI().toString());
         logo.setImage(logoI); */
          File fileU = new File("images/update.png");
        Image updat = new Image(fileU.toURI().toString());
         update.setImage(updat);
         initImages();
         
         /*Session now =new Session();
          String s = String.valueOf(now.getUsername()) ;
       // gett.setText(blog.getUsername());
        gett.setText(s);*/
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
        
         
         if (!validate(emailStudent.getText())) {
              //msgemvid.setVisible(false);
                mdpStudent.setVisible(true);
                error = true;
            }
         
          if(nomStudent.getText().equals("") || prenomStudent.getText().equals("")) {
              
               String title = "Erreur survenue lors de l'ajout";
            String header = "Veuillez remplir tous les champs";
            String content = "Aucun champs ne doit être vide";
            showAlert(Alert.AlertType.WARNING, title, header, content);
             // msgnom.setVisible(true);
               error = true;
         }
          
          
         /* if(prenomStudent.getText().equals("")) {
              msgpre.setVisible(true);
               error = true;
      }
          if(emailStudent.getText().equals("")) {
              msgemvid.setVisible(true);
               error = true;
      }*/
        
          
          
          if (mdpStudent.getText().length() < 8) {
            msgmin.setVisible(true);
            error = true;
        } 
          
          //if (US.login(emailLogin.getText(), pwdLogin.getText())) {}
          
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

    private void profil(ActionEvent event) {
         try {
            /* Parent parent = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/AddStudent.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show(); */
            
            Parent blog_parent = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/Profil.fxml"));
        Scene blog_scene = new Scene(blog_parent);
        Stage app_stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        
        app_stage.setScene(blog_scene);
        app_stage.show();
        } catch (IOException ex) {
            Logger.getLogger(AllStudentsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        final Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.setResizable(true);
        alert.showAndWait();}

    @FXML
    private void handleClicks(ActionEvent event) {
    }

    @FXML
    private void getNewsView(MouseEvent event) {
    }

    @FXML
    private void getAllMatieresView(MouseEvent event) {
    }

    @FXML
    private void getEmploiView(MouseEvent event) {
    }

    @FXML
    private void getAllDocsView(MouseEvent event) {
    }
    
     public void initImages() {
        File fileLogo = new File("images/logo1.png");
        Image logoI = new Image(fileLogo.toURI().toString());
        
        File fileHome = new File("images/stats_grey.png");
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
        
        logo_iv.setImage(logoI);
        home_iv.setImage(homeI);
        tabaff_iv.setImage(tabI);
        users_iv.setImage(usersI);
        niveaux_iv.setImage(levelI);
        classe_iv.setImage(classI);
        matieres_iv.setImage(bookI);
        club_iv.setImage(clubI);
        offre_iv.setImage(offreI);
        forum_iv.setImage(forumI);
        centre_iv.setImage(docsI);
        signOut_iv.setImage(outI);
    }

    @FXML
    private void getUsers(ActionEvent event) {
         try {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/AllAdmins.fxml"));
            Parent root = loader.load();
            rootPane.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(HomeBackController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}