/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.edspace.gui;

import edu.edspace.services.UserService;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author YOOSURF
 */
public class LoginController implements Initializable {

    @FXML
    private TextField emailLogin;
    @FXML
    private TextField pwdLogin;
    @FXML
    private Button btnLogin;
    @FXML
    private Text msgerreur;
    @FXML
    private Text nullErr;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    


    @FXML
    private void login(ActionEvent event ) {
        
         Boolean error = false;

        System.out.println("login: " + emailLogin.getText());
        System.out.println("password_field: " + pwdLogin.getText());

        if (emailLogin.getText().equals("") || pwdLogin.getText().equals("")) {
            nullErr.setVisible(true);
            error = true;
          //   AlertUtils.makeInformation("Choisir une date pour createdAt");
        } else {
            nullErr.setVisible(false);
        }

        if (!error) {
            UserService US = new UserService();
            if (US.login(emailLogin.getText(), pwdLogin.getText())) {
               
                
                    //FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/AddStudent.fxml"));
                   try {
                    Parent parent = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/AllStudents.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(AllStudentsController.class.getName()).log(Level.SEVERE, null, ex);
        }
                
            } else {
                msgerreur.setVisible(true);
            }
        }
    }

}
