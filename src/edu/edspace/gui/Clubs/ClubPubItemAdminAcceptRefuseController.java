/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.edspace.gui.Clubs;

import edu.edspace.entities.Club;
import edu.edspace.entities.ClubPub;
import edu.edspace.services.ClubCategService;
import edu.edspace.services.ClubPubService;
import edu.edspace.utils.Statics;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.StageStyle;
import org.jsoup.Jsoup;

/**
 * FXML Controller class
 *
 * @author anash
 */
public class ClubPubItemAdminAcceptRefuseController implements Initializable {

    @FXML
    private AnchorPane main;
    @FXML
    private Circle clubimg;
    @FXML
    private Label clubName_l;
    @FXML
    private Label pubDesc;
    @FXML
    private ImageView pubImg;
    @FXML
    private Label pubDate;
    @FXML
    private ImageView refuseBtn;
    @FXML
    private ImageView acceptBtn;
    private int pubid;
        private ClubPub clubPub;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Lighting lighting = new Lighting(new Light.Distant(45, 90, Color.rgb(250, 90, 90)));
        ColorAdjust bright = new ColorAdjust(0, 1, 1, 1);
        lighting.setContentInput(bright);
        lighting.setSurfaceScale(0.0);
        refuseBtn.setEffect(lighting);

        Lighting lighting2 = new Lighting(new Light.Distant(45, 90, Color.rgb(20, 100, 120)));
        ColorAdjust bright2 = new ColorAdjust(0, 1, 1, 1);
        lighting2.setContentInput(bright2);
        lighting2.setSurfaceScale(0.0);
        acceptBtn.setEffect(lighting2);
    }

    public void setData(ClubPub clubPub, String clubName, String clubPicture) {
        this.clubPub = clubPub;
        clubName_l.setText(clubName.toUpperCase());
        pubDate.setText("Publie le " + clubPub.getPubDate());
        pubDesc.setText(Jsoup.parse(clubPub.getPubDesc()).text());
        File file = new File(Statics.ClubPic + clubPicture);
        Image imageClub = new Image(file.toURI().toString());
        clubimg.setFill(new ImagePattern(imageClub));
        setPubid(Integer.parseInt(this.clubPub.getId()));
        File file2 = new File(Statics.ClubPubsPic + clubPub.getPubImage());
        Image imagePub = new Image(file2.toURI().toString());
        pubImg.setImage(imagePub);

        //  centerImage();
    }

    public int getPubid() {
        return pubid;
    }

    public void setPubid(int pubid) {
        this.pubid = pubid;
    }

    @FXML
    private void refusePub(MouseEvent event) {
        ClubPubService cps = new ClubPubService();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("Refuser la publication");
        alert.setHeaderText(null);
        alert.setContentText("Etes vous sur de vouloir refuser cette publication ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) // alert is exited, no button has been pressed.
        {
            cps.refuseClubPub(getPubid());
            main.getChildren().clear();
            main.setPrefSize(0, 0);

        }
    }

    @FXML
    private void acceptPub(MouseEvent event) {
        ClubPubService cps = new ClubPubService();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("Accepter la publication");
        alert.setHeaderText(null);
        alert.setContentText("Etes vous sur de vouloir accepter cette publication ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) // alert is exited, no button has been pressed.
        {
            cps.acceptClubPub(getPubid());
            main.getChildren().clear();
            main.setPrefSize(0, 0);

        }
    }

}
