/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.edspace.gui.Clubs;

import edu.edspace.entities.ClubPub;
import edu.edspace.services.ClubPubService;
import edu.edspace.utils.MyConnection;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author anash
 */
public class ClubRubriqueStudentController implements Initializable {

    @FXML
    private ScrollPane scroll;
    @FXML
    private Label clubName_l;
    @FXML
    private ImageView clubPic;
    @FXML
    private Label clubDesc;
    @FXML
    private GridPane pubs;

    @FXML
    private Label clubId;
    private List<ClubPub> pubsList = new ArrayList<>();
    private int clubid;
    @FXML
    private ImageView ClubListIV;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        initImages();
    }    
    
    public void initData(int clubid) {
        //set icons color
        Lighting lighting = new Lighting(new Light.Distant(45, 90, Color.rgb(250, 250, 250)));
        ColorAdjust bright = new ColorAdjust(0, 1, 1, 1);
        lighting.setContentInput(bright);
        lighting.setSurfaceScale(0.0);
        MyConnection.getInstance().getCnx();
        ClubPubService cb = new ClubPubService();
        pubsList = cb.displayPostedClubPubs(clubid);
        int colu = 0;
        int row = 0;
        if (pubsList.isEmpty()) {
            Label l = new Label("Pas de publications.");
            pubs.add(l, colu++, row);
        } else {
            try {
                for (int i = 0; i < pubsList.size(); i++) {

                    FXMLLoader fxml = new FXMLLoader();
                    fxml.setLocation(getClass().getResource("/edu/edspace/gui/Clubs/ClubPubItem.fxml"));

                    AnchorPane anchorPane = fxml.load();//child
                    ClubPubItemController clubPubItemController = fxml.getController();
                    clubPubItemController.setData(pubsList.get(i), clubName_l.getText(),
                            clubPic.getImage().getUrl()
                    );
                    if (colu == 1) {
                        colu = 0;
                        row++;
                    }
                    pubs.add(anchorPane, colu++, row);
                    GridPane.setMargin(anchorPane, new Insets(10));

                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }
    
    public void setClubid(int clubid) {
        this.clubid = clubid;
    }

    public void setClubName_l(String clubName_l) {
        this.clubName_l.setText(clubName_l);
    }

    public void setClubPic(Image clubPic) {
        this.clubPic.setImage(clubPic);
    }

    public void setClubDesc(String clubDesc) {
        this.clubDesc.setText(clubDesc);
    }
    
    @FXML
    private void ClubList(MouseEvent event) {
        try {
            //instance mtaa el crud
            //redirection
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/Clubs/ClubListStudent.fxml"));
            Parent root = loader.load();
            clubDesc.getScene().setRoot(root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private void initImages() {
        File fileBack = new File("images/back.png");
        Image backI = new Image(fileBack.toURI().toString());
        
        ClubListIV.setImage(backI);
    }
    
    @FXML
    private void getHome(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/FrontHome.fxml"));
            Parent root = loader.load();
            clubName_l.getScene().setRoot(root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
}
