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
import edu.edspace.entities.Session;

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

import java.io.File;
import java.io.IOException;

import edu.edspace.entities.Thread;
import edu.edspace.entities.User;

import edu.edspace.services.AdminService;

import edu.edspace.services.MailService;
import edu.edspace.services.ClasseService;



import edu.edspace.services.StudentService;
import edu.edspace.services.UserService;
import edu.edspace.services.statics;
import java.io.File;

/**
 *
 * @author MeriamBI
 */
public class MainClass extends Application {



    public static void main(String[] args){
       launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {


            if(Session.getUsername()==null){

           // Parent parent = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/Login.fxml"));
           Parent parent = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/HomeBack.fxml"));
          // Parent parent = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/FrontHome.fxml")); //new front
           //Parent parent = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/HomeFront.fxml")); //old front
            Scene scene = new Scene(parent);
            primaryStage.setScene(scene);
            primaryStage.setTitle("EdSpace");
            File fileLogo = new File("images/graduate.png");
            Image logoI = new Image(fileLogo.toURI().toString());
            primaryStage.getIcons().add(logoI);
            primaryStage.show();
        }
        else{
       // Parent parent = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/AllAdmins.fxml"));

            Parent parent = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/HomeBack.fxml"));

            Scene scene = new Scene(parent);
            primaryStage.setScene(scene);
            primaryStage.show();
        }
           // Parent parent = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/AllAdmins.fxml"));

           // Parent parent = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/HomeBack.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}