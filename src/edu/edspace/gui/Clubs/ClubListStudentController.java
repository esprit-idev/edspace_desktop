/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.edspace.gui.Clubs;

import edu.edspace.entities.Club;
import edu.edspace.entities.ClubCategory;
import edu.edspace.gui.document.ListDocFrontController;
import edu.edspace.services.ClubCategService;
import edu.edspace.services.ClubService;
import edu.edspace.utils.MyConnection;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
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
public class ClubListStudentController implements Initializable {

    @FXML
    private ScrollPane scroll;
    @FXML
    private GridPane grid;
    private List<Club> clubs = new ArrayList<>();
    @FXML
    private ImageView home_iv1;
    @FXML
    private ComboBox<String> filter_cb;
  
    private List<ClubCategory> categories = new ArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        MyConnection.getInstance().getCnx();
        initImages();
        filter_cb.setItems(catsList());
        filter_cb.setPromptText("Toutes les catégories");
        ClubService cb = new ClubService();
        clubs = cb.displayClub();
        int colu = 0;
        int row = 0;
        try {
            for (int i = 0; i < clubs.size(); i++) {

                FXMLLoader fxml = new FXMLLoader();
                fxml.setLocation(getClass().getResource("/edu/edspace/gui/Clubs/ClubItem.fxml"));

                AnchorPane anchorPane = fxml.load();//child

                ClubItemController clubItemController = fxml.getController();
                clubItemController.setData(clubs.get(i));

                if (colu == 2) {
                    colu = 0;
                    row++;
                }

                grid.add(anchorPane, colu++, row);
                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    @FXML
    private void getHome(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/FrontHome.fxml"));
            Parent root = loader.load();
            grid.getScene().setRoot(root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void initImages() {

        File fileHome = new File("images/home_grey.png");
        Image homeI = new Image(fileHome.toURI().toString());

        home_iv1.setImage(homeI);

    }

    private ObservableList<String> catsList() {
        ObservableList<String> oblistN = FXCollections.observableArrayList();
        ClubCategService ccs = new ClubCategService();
        categories = ccs.displayClubCategories();
        for (int i = 0; i < categories.size(); i++) {
            oblistN.add(categories.get(i).getCategorie());
        }
        return oblistN;
    }

}
