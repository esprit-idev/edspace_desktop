/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.edspace.gui;

import edu.edspace.entities.User;
import edu.edspace.services.StudentService;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author YOOSURF
 */
public class AddStudentController implements Initializable {

    @FXML
    private TextField nomStudent;
    @FXML
    private TextField prenomStudent;
    @FXML
    private TextField emailStudent;
    @FXML
    private TextField passwordStudent;
    @FXML
    private Button btnAddStudent;
    @FXML
    private Text errmsgN;
    @FXML
    private Text msgnovalidemail;
    @FXML
    private Text msgpass;
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
        // TODO
    }    

    @FXML
    private void addStudent(ActionEvent event) {
         Boolean error = false;
        
          if(nomStudent.getText().equals("")) {
              errmsgN.setVisible(true);
               error = true;
}
          
          if (passwordStudent.getText().length() < 8) {
            msgpass.setVisible(true);
            error = true;
        }
          if (!validate(emailStudent.getText())) {
                msgnovalidemail.setVisible(true);
                error = true;
            }
          
   if (!error) {
    User stu = new User();
    // stu.setId(Integer.parseInt(SId.getText()));
     stu.setPrenom(prenomStudent.getText());
     stu.setUsername(nomStudent.getText());
     stu.setEmail(emailStudent.getText());
     stu.setPassword(passwordStudent.getText());
     StudentService SS = new StudentService();
   // SS.AjouterUser(stu);
    SS.ajouterStudent(stu);
    }}
    
     public static boolean validate(String email) {
        final String EMAIL_VERIFICATION = "^([\\w-\\.]+)@([\\w\\.]+)\\.([a-z]){2,}$";
        Pattern pattern = Pattern.compile(EMAIL_VERIFICATION);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    

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
}



