/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.edspace.gui;

import edu.edspace.entities.Club;
import edu.edspace.services.ClubService;
import edu.edspace.utils.MyConnection;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

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
    private HBox h_box;
    @FXML
    private ImageView logo_iv;
    @FXML
    private Button btnOverview;
    @FXML
    private ImageView home_iv;
    @FXML
    private Button btnNews;
    @FXML
    private ImageView tabaff_iv;
    @FXML
    private Button btnOrders;
    @FXML
    private ImageView users_iv;
    @FXML
    private Button btnCustomers;
    @FXML
    private ImageView niveaux_iv;
    @FXML
    private Button btnMenus;
    @FXML
    private ImageView classe_iv;
    @FXML
    private Button btnPackages;
    @FXML
    private ImageView matieres_iv;
    @FXML
    private Button btn_Club;
    @FXML
    private ImageView club_iv;
    @FXML
    private Button btnEmploi;
    @FXML
    private ImageView offre_iv;
    @FXML
    private Button btnSignout1;
    @FXML
    private ImageView forum_iv;
    @FXML
    private Button btnSignout2;
    @FXML
    private ImageView centre_iv;
    @FXML
    private Button btnSignout3;
    @FXML
    private ImageView signOut_iv;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        MyConnection.getInstance().getCnx();
        ClubService cb = new ClubService();
        clubs = cb.displayClub();
        int colu = 0;
        int row = 0;
        try {
            for (int i = 0; i < clubs.size(); i++) {

                FXMLLoader fxml = new FXMLLoader();
                fxml.setLocation(getClass().getResource("ClubItem.fxml"));

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

      
 /*    Button newClub = new Button("Add Club");
        newClub.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                try {
                    //instance mtaa el crud
                    //redirection
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("ClubAdd.fxml"));
                    Parent root = loader.load();
                    ClubAddController pc = loader.getController();
                    grid.getScene().setRoot(root);

                } catch (IOException ex) {
                    System.out.println(ex.toString());
                }
            }
        });
        grid.getChildren().add(newClub);*/
 
 initImages();
    }
        public void initImages(){
        File fileLogo = new File("images/logo1.png");
        Image logoI = new Image(fileLogo.toURI().toString());
        
        File fileHome = new File("images/icons8_Home_32px.png");
        Image homeI = new Image(fileHome.toURI().toString());
        
        File fileUsers = new File("images/icons8_Person_32px.png");
        Image usersI = new Image(fileUsers.toURI().toString());
        
        File fileOut = new File("images/icons8_Sign_Out_32px.png");
        Image outI = new Image(fileOut.toURI().toString());
        
        File fileSearch = new File("images/icons8_Search_52px.png");
        Image searchI = new Image(fileSearch.toURI().toString());
        
        logo_iv.setImage(logoI);
        home_iv.setImage(homeI);
        tabaff_iv.setImage(homeI);
        users_iv.setImage(homeI);
        niveaux_iv.setImage(homeI);
        matieres_iv.setImage(homeI);
        classe_iv.setImage(homeI);
        club_iv.setImage(outI);
        offre_iv.setImage(outI);
        forum_iv.setImage(outI);
        centre_iv.setImage(outI);
        signOut_iv.setImage(outI);
    }

    @FXML
    private void handleClicks(ActionEvent event) {
    }

    @FXML
    private void getNewsView(MouseEvent event) {
    }

    @FXML
    private void displayClubs(ActionEvent event) {
    }



}
