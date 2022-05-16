/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.edspace.gui.Clubs;

import edu.edspace.entities.Club;
import edu.edspace.entities.Session;
import edu.edspace.services.ClubPubService;
import edu.edspace.services.ClubService;
import edu.edspace.utils.MyConnection;
import edu.edspace.utils.Statics;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author anash
 */
public class ClubItemController implements Initializable {

    @FXML
    private Label club_name_label;
    @FXML
    private ImageView club_img;
    @FXML
    private Label club_cat_label;
    @FXML
    private Label club_desc_label;
    @FXML
    private Label club_respo_label;
    @FXML
    private Button btn_consult;
    private Club club;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label clubId;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setData(Club club) {
        this.club = club;
        club_cat_label.setText(club.getClubCategorie());
        club_desc_label.setText(club.getClubDesc());
        club_name_label.setText(club.getClubName());
        //File file = new File(Statics.ClubPic + club.getClubPic());
      //  Image image = new Image(file.toURI().toString());
       // club_img.setImage(image);
        club_respo_label.setText(club.getClubRespo());
        clubId.setText(club.getClubId());
    }

    @FXML
    private void consultClub(ActionEvent event) {
        try {
            //instance mtaa el crud
            //redirection
            ClubService cb=new ClubService();
            String currentResponsableClubId=String.valueOf(cb.getUserClubID(Session.getId())); //to_change
            if (currentResponsableClubId.equals(club.getClubId())) {
                System.out.println("oui");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/Clubs/ClubRubriqueResponsable.fxml"));
                Parent root = loader.load();
                ClubRubriqueResponsableController pc = loader.getController();
                pc.setClubName_l("Rubrique club " + club_name_label.getText());
                pc.setClubid(Integer.parseInt(club.getClubId()));
          //      File file = new File(Statics.ClubPic + club.getClubPic());
            //    Image image = new Image(file.toURI().toString());
             //   pc.setClubPic(image);
                pc.setClubDesc(club.getClubDesc());
                pc.initData(Integer.parseInt(club.getClubId()));
                club_desc_label.getScene().setRoot(root);
            }else{
                System.out.println("non");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/Clubs/ClubRubriqueStudent.fxml"));
                Parent root = loader.load();
                ClubRubriqueStudentController pc = loader.getController();
                pc.setClubName_l("Rubrique club " + club_name_label.getText());
                pc.setClubid(Integer.parseInt(club.getClubId()));
             //   File file = new File(Statics.ClubPic + club.getClubPic());
               // Image image = new Image(file.toURI().toString());
              //  pc.setClubPic(image);
                pc.setClubDesc(club.getClubDesc());
                pc.initData(Integer.parseInt(club.getClubId()));
                club_desc_label.getScene().setRoot(root);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
