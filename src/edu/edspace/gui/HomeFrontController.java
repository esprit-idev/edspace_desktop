/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.edspace.gui;

import edu.edspace.utils.MyConnection;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author MeriamBI
 */
public class HomeFrontController implements Initializable {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private ImageView logo_iv;
    @FXML
    private Button btnNews;
    @FXML
    private ImageView tabaff_iv;
    @FXML
    private Button btnForum;
    @FXML
    private ImageView forum_iv;
    @FXML
    private Button btnClubs;
    @FXML
    private ImageView club_iv;
    @FXML
    private Button btnEmploi;
    @FXML
    private Button btnCentrePartage;
    @FXML
    private ImageView centre_iv;
    @FXML
    private Button btnStudents;
    @FXML
    private ImageView users_iv;
    @FXML
    private Button btnProfil;
    @FXML
    private Button btnSignout;
    @FXML
    private ImageView signOut_iv;
    @FXML
    private Pane pnlCustomer;
    @FXML
    private Pane pnlOrders;
    @FXML
    private Pane pnlMenus;
    @FXML
    private Pane pnlOverview;
    @FXML
    private ImageView emploi_v;
    @FXML
    private ImageView profil_iv;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        MyConnection.getInstance().getCnx();
        initImages();
    }

    @FXML
    private void handleClicks(ActionEvent event) {

    }
    
    @FXML
    private void getNewsView(MouseEvent event) {

    }

    @FXML
    private void getAllDocsView(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/document/DocListFront.fxml"));
            Parent root = loader.load();
            rootPane.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(HomeFrontController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void initImages() {
        File fileLogo = new File("images/logo1.png");
        Image logoI = new Image(fileLogo.toURI().toString());

        File fileHome = new File("images/announcement_grey.png");
        Image homeI = new Image(fileHome.toURI().toString());
        
        File fileForum = new File("images/forum2_grey.png");
        Image forumI = new Image(fileForum.toURI().toString());
        
        File fileOffre = new File("images/briefcase_grey.png");
        Image offreI = new Image(fileOffre.toURI().toString());
        
        File fileDocs = new File("images/file_grey.png");
        Image docsI = new Image(fileDocs.toURI().toString());

        File fileUsers = new File("images/users_grey.png");
        Image usersI = new Image(fileUsers.toURI().toString());
        
        File fileAccount = new File("images/account_grey.png");
        Image accountI = new Image(fileAccount.toURI().toString());

        File fileOut = new File("images/logout_grey.png");
        Image outI = new Image(fileOut.toURI().toString());
        
        File fileClub = new File("images/org_grey.png");
        Image clubI = new Image(fileClub.toURI().toString());

        logo_iv.setImage(logoI);
        tabaff_iv.setImage(homeI);
        forum_iv.setImage(forumI);
        club_iv.setImage(clubI);
        emploi_v.setImage(offreI);
        centre_iv.setImage(docsI);
        users_iv.setImage(usersI);
        profil_iv.setImage(accountI);
        signOut_iv.setImage(outI);
    }
}