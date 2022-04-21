/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.edspace.gui;

import edu.edspace.entities.Session;
import edu.edspace.services.UserService;
import java.io.File;
import java.sql.Connection;
import edu.edspace.utils.MyConnection;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author YOOSURF
 */
public class LoginController implements Initializable {

    @FXML
    private TextField emailLogin;
    @FXML
    private PasswordField pwdLogin;
    @FXML
    private Button btnLogin;
    @FXML
    private Text nullErr;
    @FXML
    private ImageView logo;
    @FXML
    private Button mdpOublie;
    @FXML
    private Text nullmdp;
    @FXML
    private Label curr;
    @FXML
    private AnchorPane rootPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //  Connection = MyConnection.getInstance().getCnx();
        //  initImages();
        File fileLogo = new File("images/logo2.png");
        Image logoI = new Image(fileLogo.toURI().toString());
        logo.setImage(logoI);

         /*Session now =new Session();
          String s = String.valueOf(now.getUsername()) ;
       // gett.setText(blog.getUsername());
        curr.setText(s);*/
    }    


    


    @FXML
    private void login(ActionEvent event) {

        Boolean error = false;

        System.out.println("login: " + emailLogin.getText());
        System.out.println("password_field: " + pwdLogin.getText());

        if (emailLogin.getText().equals("")) {
            nullErr.setVisible(true);
            error = true;
            // System.out.println("here");
            //   AlertUtils.makeInformation("Choisir une date pour createdAt");

        } if(pwdLogin.getText().equals("")) {
              nullmdp.setVisible(true);
               error = true;
}
       
        
        else {

        }
        if (pwdLogin.getText().equals("")) {
            nullmdp.setVisible(true);
            error = true;
        } /*
         if ( pwdLogin.getText().equals("")) {
            nullmdp.setVisible(true);
            error = true;
          //   AlertUtils.makeInformation("Choisir une date pour createdAt");
        } */ else {

            nullErr.setVisible(false);
        }

        if (!error) {
            UserService US = new UserService();
            if (US.login(emailLogin.getText(), pwdLogin.getText())) {

              
                    
            try {
                 Session now =new Session();
          String s = String.valueOf(now.getRoles()) ;
          //System.out.println(s);
                       if(s.equals("[\"ROLE_ADMIN\"]")){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/HomeBack.fxml"));
            Parent root = loader.load();
            rootPane.getScene().setRoot(root);}
                       else{FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/FrontHome.fxml"));
            Parent root = loader.load();
            rootPane.getScene().setRoot(root);}
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
       /* } catch (IOException ex) {
            Logger.getLogger(AllStudentsController.class.getName()).log(Level.SEVERE, null, ex);
        }*/
                

/*
                //FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/AddStudent.fxml"));
                try {
                    if (Session.getRoles().contains("ADMIN")) {
                        Parent parent = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/HomeBack.fxml")); //backoffice
                        Scene scene = new Scene(parent);
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.initStyle(StageStyle.UTILITY);
                        stage.show();
                    } else {
                        Parent parent = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/FrontHome.fxml"));
                        Scene scene = new Scene(parent);
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.initStyle(StageStyle.UTILITY);
                        stage.show();
                    }

                } catch (IOException ex) {
                    Logger.getLogger(AllStudentsController.class.getName()).log(Level.SEVERE, null, ex);
                }*/


            } else {
                //  msgerreur.setVisible(true);
                // JOptionPane.showMessageDialog(null, "verifier");
                JOptionPane.showMessageDialog(null, "votre Email ou mot de passe que vous avez saisi(e) n'est pas associé(e) à un compte ",
                        "Alerte", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    @FXML
    private void mdp(ActionEvent event) {
        try {
            Parent blog_parent = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/Mdp.fxml"));
            Scene blog_scene = new Scene(blog_parent);
            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            app_stage.setScene(blog_scene);
            app_stage.show();
        } catch (IOException ex) {
            Logger.getLogger(AllStudentsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}