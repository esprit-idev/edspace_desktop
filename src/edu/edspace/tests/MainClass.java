/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.edspace.tests;

import java.util.logging.Level;
import java.util.logging.Logger;

import edu.edspace.entities.Session;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

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
           // Parent parent = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/Login.fxml"));
        // Parent parent = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/Classe/AllClasses.fxml"));
           Parent parent = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/Niveau/AllNiveau.fxml"));
            //Parent parent = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/Message/AllMessages.fxml"));
//Parent parent = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/Message/AllMessages.fxml"));          
//Parent parent = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/Niveau/AllNiveau.fxml"));
          // Parent parent = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/FrontHome.fxml")); //new front

           //Parent parent = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/HomeFront.fxml")); //old front
            Scene scene = new Scene(parent);
            primaryStage.setScene(scene);
            primaryStage.setTitle("EdSpace");
            //change this to your own path or comment it to not cause problems
            //primaryStage.getIcons().add(new Image("E:\\Webprojects\\PIDEV\\edspace_desktop\\images\\graduate.png"));
            primaryStage.show();
        
        } catch (IOException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}