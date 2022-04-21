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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
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
import javax.swing.JOptionPane;

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
    @FXML
    private ImageView logo_iv;
    @FXML
    private ImageView home_iv;
    @FXML
    private ImageView tabaff_iv;
    @FXML
    private ImageView users_iv;
    @FXML
    private ImageView niveaux_iv;
    @FXML
    private ImageView classe_iv;
    @FXML
    private ImageView matieres_iv;
    @FXML
    private ImageView club_iv;
    @FXML
    private ImageView offre_iv;
    @FXML
    private ImageView forum_iv;
    @FXML
    private ImageView centre_iv;
    @FXML
    private ImageView signOut_iv;
    @FXML
    private Button btnOverview;
    @FXML
    private Button btnNews;
    @FXML
    private Button btnOrders;
    @FXML
    private Button btnCustomers;
    @FXML
    private Button btnMenus;
    @FXML
    private Button btnMatiere;
    @FXML
    private Button btnSettings;
    @FXML
    private Button btnEmploi;
    @FXML
    private Button btnSignout1;
    @FXML
    private Button btnCentrePartage;
    @FXML
    private Button btnSignout3;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Button getStudent;
    @FXML
    private ImageView stud;

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
        
        /* File fileLogo = new File("images/logo1.png");
        Image logoI = new Image(fileLogo.toURI().toString());
        logo.setImage(logoI);*/
        File filedel = new File("images/delete.png");
        Image del = new Image(filedel.toURI().toString());
        delicon.setImage(del);
         File fileU = new File("images/update.png");
        Image updat = new Image(fileU.toURI().toString());
         update.setImage(updat);
         initImages();
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
        int n = JOptionPane.showConfirmDialog(null,
"DO YOU CONFIRM THE DELETION?","Confirm Dialog",JOptionPane.YES_NO_OPTION);
if(n == JOptionPane.YES_OPTION)
{
//****

        AdminService SS= new AdminService();
            SS.supprimerAdmin(gett.getText());
            
            SS.listAdmin() ;
         ObservableList<User> list = SS.listAdmin();
        
        nom.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
        prenom.setCellValueFactory(new PropertyValueFactory<User, String>("prenom"));
        email.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
        tableAdmin.setItems(list); 
    }}

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
        
        File fileStu = new File("images/student.png");
        Image Stu = new Image(fileStu.toURI().toString());
        
        
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
        stud.setImage(Stu);
    }

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

    @FXML
    private void users(ActionEvent event) {
        try {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/AllAdmins.fxml"));
            Parent root = loader.load();
            rootPane.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(HomeBackController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void getStudents(ActionEvent event) {
        try {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/AllStudents.fxml"));
            Parent root = loader.load();
            rootPane.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(HomeBackController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
}



