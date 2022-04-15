/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.edspace.gui;

import edu.edspace.utils.MyConnection;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
/**
 * FXML Controller class
 *
 * @author MeriamBI
 */
public class HomeBackController implements Initializable {


    @FXML
    private Button btnOverview;
    @FXML
    private Button btnOrders1;
    @FXML
    private Button btnOrders;
    @FXML
    private Button btnCustomers;
    @FXML
    private Button btnMenus;
    @FXML
    private Button btnPackages;
    @FXML
    private Button btnSettings;
    @FXML
    private Button btnSignout;
    @FXML
    private Button btnSignout1;
    @FXML
    private Button btnSignout2;
    @FXML
    private Button btnSignout3;
    @FXML
    private Pane pnlCustomer;
    @FXML
    private Pane pnlOrders;
    @FXML
    private Pane pnlMenus;
    @FXML
    private Pane pnlOverview;
    @FXML
    private VBox pnItems;
    @FXML
    private ImageView logo_iv,home_iv,tabaff_iv,users_iv,niveaux_iv,matieres_iv,classe_iv,club_iv,offre_iv,forum_iv,centre_iv,search_iv,signOut_iv;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        MyConnection.getInstance().getCnx();
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
        search_iv.setImage(outI);
        signOut_iv.setImage(outI);
    }    
    
    @FXML
    private void handleClicks(ActionEvent event) {
    }

}
