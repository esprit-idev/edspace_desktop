/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.edspace.gui.User;

import edu.edspace.entities.User;
import edu.edspace.services.UserService;
import java.io.File;
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
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author YOOSURF
 */
public class ResetController implements Initializable {

    @FXML
    private PasswordField mdp;
    @FXML
    private Button btnreset;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private ImageView logo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        File fileLogo = new File("images/logo2.png");
        Image logoI = new Image(fileLogo.toURI().toString());
        logo.setImage(logoI);
        // TODO
    }    

    @FXML
    private void reset(ActionEvent event) {
    }
    
    public void update(String email){
       User u = new User();
            UserService US = new UserService();
             btnreset.setOnAction(e->{
             u.setPassword(mdp.getText());
             US.updatemdp(u, email);
        try {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/User/Login.fxml"));
            Parent root = loader.load();
            rootPane.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(ResetController.class.getName()).log(Level.SEVERE, null, ex);
        }

    });
    }
    
}
