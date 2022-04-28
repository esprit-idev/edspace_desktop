/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.edspace.gui.Clubs;

import edu.edspace.entities.ClubPub;
import edu.edspace.services.ClubPubService;
import edu.edspace.utils.Statics;
import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
public class ClubPubAdminItemController implements Initializable {

    @FXML
    private AnchorPane main;
    @FXML
    private Circle clubimg;
    @FXML
    private Label clubName_l;
    @FXML
    private ImageView deleteBtn;
    @FXML
    private Label pubDesc;
    @FXML
    private ImageView pubImg;
    @FXML
    private Label pubDate;
    private ClubPub clubPub;
    private int pubid;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        File delf = new File("src/images/del.png");
        Image delim = new Image(delf.toURI().toString());
        deleteBtn.setImage(delim);
        Lighting lighting = new Lighting(new Light.Distant(45, 90, Color.rgb(250, 90, 90)));
        ColorAdjust bright = new ColorAdjust(0, 1, 1, 1);
        lighting.setContentInput(bright);
        lighting.setSurfaceScale(0.0);
        deleteBtn.setEffect(lighting);
    }

    public void setData(ClubPub clubPub, String clubName, String clubPicture) {
        this.clubPub = clubPub;
        clubName_l.setText(clubName.toUpperCase().split("RUBRIQUE CLUB")[1].toUpperCase());
        pubDate.setText("Publie le " + clubPub.getPubDate());
        pubDesc.setText(Jsoup.parse(clubPub.getPubDesc()).text());
        File file = new File(Statics.ClubPic + clubPicture);
        Image imageClub = new Image(file.toURI().toString());
        clubimg.setFill(new ImagePattern(imageClub));
        setPubid(Integer.parseInt(clubPub.getId()));
        File file2 = new File(Statics.ClubPubsPic + clubPub.getPubImage());
        Image imagePub = new Image(file2.toURI().toString());
        pubImg.setImage(imagePub);

        //  centerImage();
    }

    @FXML
    private void deletepub(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("Supprimer la publication");
        alert.setHeaderText(null);
        alert.setContentText("Etes vous sur de vouloir supprimer la publciation?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) // alert is exited, no button has been pressed.
        {
            ClubPubService cb = new ClubPubService();
            cb.deletePubClub(getPubid());
            main.getChildren().clear();
            main.setPrefSize(0, 0);
        }
    }

    public void setPubid(int pubid) {
        this.pubid = pubid;
    }

    public int getPubid() {
        return pubid;
    }

}
