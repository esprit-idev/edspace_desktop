/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.edspace.gui.Clubs;

import edu.edspace.entities.ClubPub;
import edu.edspace.entities.Session;
import edu.edspace.services.ClubPubService;
import edu.edspace.services.ClubService;
import edu.edspace.utils.Statics;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import org.jsoup.Jsoup;

/**
 * FXML Controller class
 *
 * @author anash
 */
public class ClubPubStudentItemController implements Initializable {

    private ClubPub clubPub;
    private int pubid;
    private String clubnom;
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
    private ImageView shareBtn;
    @FXML
    private ImageView likeBtn;
    @FXML
    private Label likes_bn;
    @FXML
    private Hyperlink pubfile;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void setData(ClubPub clubPub, String clubName, String clubPicture) {
        this.clubPub = clubPub;
        ClubPubService cps = new ClubPubService();
        setClubnom(clubName);
        clubName_l.setText(clubName.toUpperCase().split("RUBRIQUE CLUB")[1].toUpperCase());
        pubDate.setText("Publie le " + clubPub.getPubDate());
        pubDesc.setText(Jsoup.parse(clubPub.getPubDesc()).text());
        if (clubPicture.contains("file:/")) {
            File file = new File(clubPicture.split("file:/")[1]);
            Image imageClub = new Image(file.toURI().toString());
            clubimg.setFill(new ImagePattern(imageClub));
        } else {
            File file = new File(clubPicture);
            Image imageClub = new Image(file.toURI().toString());
            clubimg.setFill(new ImagePattern(imageClub));
        }
        pubfile.setText(clubPub.getPubFile());
        pubfile.setVisible(true);
        setPubid(Integer.parseInt(clubPub.getId()));
        File file2 = new File(Statics.ClubPubsPic + clubPub.getPubImage());
        Image imagePub = new Image(file2.toURI().toString());
        pubImg.setImage(imagePub);
        likes_bn.setText(String.valueOf(cps.countLikesPerPub(getPubid())) + " Likes");
        if (cps.pubIsLiked(Session.getId(), getPubid())) {
            File file = new File("src/images/heart.png");
            Image logoI = new Image(file.toURI().toString());
            likeBtn.setImage(logoI);
        }

    }

    public int getPubid() {
        return pubid;
    }

    public void setPubid(int pubid) {
        this.pubid = pubid;
    }

    @FXML
    private void sharepub(MouseEvent event) {
        ClubPubService cps = new ClubPubService();
        ClubService cs = new ClubService();
        System.out.println();
        System.out.println();
        cps.sharefb(String.valueOf(getPubid()), String.valueOf(cs.getClubIdByClubName(getClubnom().split("Rubrique club ")[1])));
    }

    @FXML
    private void likePub(MouseEvent event) {
        ClubPubService cps = new ClubPubService();
        if (likeBtn.getImage().getUrl().contains("dislike")) {
            File file = new File("src/images/heart.png");
            Image logoI = new Image(file.toURI().toString());
            likeBtn.setImage(logoI);
            cps.likePub(Session.getId(), getPubid());
            likes_bn.setText(String.valueOf(cps.countLikesPerPub(getPubid()))+" Likes");

        } else {
            File file = new File("src/images/dislike.png");
            Image logoI = new Image(file.toURI().toString());
            likeBtn.setImage(logoI);
            cps.dislikePub(Session.getId(), getPubid());
            likes_bn.setText(String.valueOf(cps.countLikesPerPub(getPubid()))+" Likes");

        }
    }

    @FXML
    private void openPubFile(ActionEvent event) {
        File fic = new File(Statics.ClubPubsFile + pubfile.getText());
        try {
            Desktop.getDesktop().open(fic);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public String getClubnom() {
        return clubnom;
    }

    public void setClubnom(String clubnom) {
        this.clubnom = clubnom;
    }

}
