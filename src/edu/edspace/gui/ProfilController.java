/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.edspace.gui;

import edu.edspace.entities.Session;
import edu.edspace.services.UserService;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author YOOSURF
 */
public class ProfilController implements Initializable {

    @FXML
    private TextField monNom;
    @FXML
    private TextField monPrenom;
    @FXML
    private Button btnsend;
    @FXML
    private TextField pwd;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void send(ActionEvent event) {
        Session stu = new Session();
    // stu.setId(Integer.parseInt(SId.getText()));
    
    monPrenom.setText(stu.getUsername());
       monNom.setText(stu.getPrenom());
       pwd.setText(stu.getPassword());
     stu.setPrenom(monNom.getText());
     stu.setUsername(monPrenom.getText());
     stu.setPassword(pwd.getText());
                UserService SS= new UserService();
                String s=stu.getId_Lo();
                SS.updateProfil(stu,s);
    }
    
}
