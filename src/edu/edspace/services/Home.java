/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.edspace.services;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import edu.edspace.utils.MyConnection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author eslem
 */
public class Home implements Initializable{

    @FXML 
    private ImageView logoImageView;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       // MyConnection.getInstance().getCnx();
        File file = new File("images/logo1.png");
        Image logo = new Image(file.toURI().toString());
        System.out.println(logo);
        logoImageView.setImage(logo);
    }
    
}
