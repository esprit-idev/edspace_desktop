/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.edspace.gui;

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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author YOOSURF
 */
public class MdpController implements Initializable {

    @FXML
    private ImageView log;
    @FXML
    private Button mdpOublier;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         File fileLogo = new File("images/logo2.png");
        Image logoI = new Image(fileLogo.toURI().toString());
        log.setImage(logoI);
        // TODO
    }    

    @FXML
    private void mdpOublier(ActionEvent event) {
        
        try {
            Parent blog_parent = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/Login.fxml"));
        Scene blog_scene = new Scene(blog_parent);
        Stage app_stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        
        app_stage.setScene(blog_scene);
        app_stage.show();
        } catch (IOException ex) {
            Logger.getLogger(AllStudentsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}