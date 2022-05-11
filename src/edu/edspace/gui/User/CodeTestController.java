/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.edspace.gui.User;

import edu.edspace.gui.User.MdpController;
import edu.edspace.gui.User.ResetController;
import edu.edspace.entities.User;
import edu.edspace.services.MailService;
import edu.edspace.services.UserService;
import edu.edspace.utils.MyConnection;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author YOOSURF
 */
public class CodeTestController implements Initializable {

    @FXML
    private TextField code1;
    @FXML
    private Button send;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private ImageView log;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      // codereset(email);
    }    

    public void codereset(String email){
        // String c=code1.getText()+code2.getText()+code3.getText();
      
        // int n = Integer.parseInt(c);
        File fileLogo = new File("images/logo2.png");
        Image logoI = new Image(fileLogo.toURI().toString());
        log.setImage(logoI);
          int min = 100;  
       int max = 900;  
//Generate random int value from 200 to 400     
     int b = (int)(Math.random()*(max-min+1)+min); 
     String s=Integer.toString(b);
     System.out.println(b);  
      MailService MS = new MailService();
      UserService U = new UserService();
      User us = U.getUser(email);
      System.out.println(us.getPrenom()+us.getEmail());
        MS.send("Mr ou Madame"+us.getUsername()+us.getPrenom()+" voila ton code est "+b, email);
        send.setOnAction(e->{
            String c=code1.getText();
        if(c.equals(s)){
             FXMLLoader blog_parent = new FXMLLoader(getClass().getResource("/edu/edspace/gui/User/Reset.fxml"));
        try {
           
            Parent root1 = blog_parent.load();
                   ResetController RC = blog_parent.getController();
                   RC.update(email);
                   
                  send.getScene().setRoot(root1);
       // Scene blog_scene = new Scene(blog_parent);
       /// Stage app_stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        
       // app_stage.setScene(blog_scene);
       // app_stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MdpController.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        else{
        System.out.println("noo");
       // System.out.println(s);
        System.out.println(c);
        }
        });
       
    
    
    
        
        // TODO
    
    
}}
