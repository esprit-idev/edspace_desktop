/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.edspace.gui;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import edu.edspace.utils.MyConnection;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author eslem
 */
public class Home implements Initializable{

    @FXML 
    public ImageView logoImageView;
    @FXML
    private Button newsNavBtn;
    @FXML
    private Button btn_Club;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MyConnection.getInstance().getCnx();
        File file = new File("images/logo1.png");
        Image logo = new Image(file.toURI().toString());
        logoImageView.setImage(logo);
    }

    @FXML
    private void displayClubs(ActionEvent event) {
        try {
            //instance mtaa el crud
            //redirection
            FXMLLoader loader=new FXMLLoader(getClass().getResource("ClubListStudent.fxml"));
            Parent root=loader.load();
            ClubListStudentController pc=loader.getController();
            btn_Club.getScene().setRoot(root);
            //JOptionPane.showConfirmDialog(co, "fef");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());        }
    }
    
}
