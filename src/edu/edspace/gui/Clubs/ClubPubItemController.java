/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.edspace.gui.Clubs;

import edu.edspace.entities.ClubPub;
import edu.edspace.services.ClubPubService;
import edu.edspace.utils.Statics;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
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
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.StageStyle;
import org.jsoup.Jsoup;

/**
 * FXML Controller class
 *
 * @author anash
 */
public class ClubPubItemController implements Initializable {

    @FXML
    private Label clubName_l;
    @FXML
    private Circle clubimg;
    @FXML
    private Label pubDate;
    @FXML
    private Label pubDesc;
    @FXML
    private ImageView pubImg;
    private ClubPub clubPub;
    @FXML
    private ImageView deleteBtn;
    @FXML
    private ImageView editBtn;
    private int pubid;
    @FXML
    private AnchorPane main;
    @FXML
    private Hyperlink pubfile;
    @FXML
    private Label l_likes;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        File editf = new File("src/images/edit.png");
        Image editim = new Image(editf.toURI().toString());
        File delf = new File("src/images/del.png");
        Image delim = new Image(delf.toURI().toString());
        deleteBtn.setImage(delim);
        editBtn.setImage(editim);
        Lighting lighting = new Lighting(new Light.Distant(45, 90, Color.rgb(250, 90, 90)));
        ColorAdjust bright = new ColorAdjust(0, 1, 1, 1);
        lighting.setContentInput(bright);
        lighting.setSurfaceScale(0.0);
        deleteBtn.setEffect(lighting);

        Lighting lighting2 = new Lighting(new Light.Distant(45, 90, Color.rgb(20, 100, 120)));
        ColorAdjust bright2 = new ColorAdjust(0, 1, 1, 1);
        lighting2.setContentInput(bright2);
        lighting2.setSurfaceScale(0.0);
        editBtn.setEffect(lighting2);
    }

    public void setData(ClubPub clubPub, String clubName, String clubPicture) {
        this.clubPub = clubPub;
        ClubPubService cps = new ClubPubService();
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
        l_likes.setText(String.valueOf(cps.countLikesPerPub(getPubid())) + " Likes");
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

    @FXML
    private void editPub(MouseEvent event) {
        ClubPubService cps = new ClubPubService();

        // Create the custom dialog
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Modification de la publication");

        // Set the button types
        ButtonType saveButtonType = new ButtonType("Modifier", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, cancelButtonType);

        // Create the nom and niveau labels and fields
        //grid setting
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        //nom tf init
        TextArea tf = new TextArea();
        tf.setText(Jsoup.parse(cps.getpubDesc(getPubid())).text());

        //add tf and cb to the grid +lables
        grid.add(new Label("Description:"), 0, 0);
        grid.add(tf, 1, 0);

        dialog.getDialogPane().setContent(grid);
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.get() == saveButtonType) {
            cps.updateClubPub(tf.getText(), pubid);
            main.getChildren().clear();
            main.setPrefSize(0, 0);
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

}
