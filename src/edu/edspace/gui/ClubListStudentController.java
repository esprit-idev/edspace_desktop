/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.edspace.gui;

import edu.edspace.entities.Club;
import edu.edspace.services.ClubService;
import edu.edspace.utils.MyConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author anash
 */
public class ClubListStudentController implements Initializable {

    @FXML
    private Text text;
    @FXML
    private AnchorPane main;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        List<Club> clubs;
        MyConnection.getInstance().getCnx();
        ClubService cb = new ClubService();
        clubs = cb.displayClub();
        main.getChildren().add(new Text("dzd"));

        for (Club c : clubs) {
            main.getChildren().add(new Text(c.getClubName()));
            text.setText(c.getClubName());

        }

    }

}
