/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.edspace.tests;

import java.util.logging.Level;
import java.util.logging.Logger;

import edu.edspace.services.ClubCategService;
import edu.edspace.services.ClubPubService;
import edu.edspace.services.ClubService;
import edu.edspace.entities.Message;
import edu.edspace.entities.Niveau;

import edu.edspace.services.MessageService;

import edu.edspace.services.NiveauService;
import edu.edspace.services.ThreadService;
import edu.edspace.utils.MyConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

import edu.edspace.entities.Thread;
import edu.edspace.entities.User;

import edu.edspace.services.AdminService;

import edu.edspace.services.MailService;
import edu.edspace.services.ClasseService;



import edu.edspace.services.StudentService;
import edu.edspace.services.UserService;
import edu.edspace.services.statics;


/**
 *
 * @author MeriamBI
 */
public class MainClass extends Application{

    public static void main(String[] args){
       launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/Login.fxml"));
           //Parent parent = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/HomeBack.fxml"));
           //Parent parent = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/FrontHome.fxml")); //new front
           //Parent parent = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/HomeFront.fxml")); //old front
            Scene scene = new Scene(parent);
            primaryStage.setScene(scene);
            primaryStage.setTitle("EdSpace");
            //change this to your own path or comment it to not cause problems
            primaryStage.getIcons().add(new Image("C:\\Users\\eslem\\Desktop\\edspace_desktop\\images\\graduate.png"));
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
