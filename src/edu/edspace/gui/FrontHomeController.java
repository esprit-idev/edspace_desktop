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

/**
 * FXML Controller class
 *
 * @author MeriamBI
 */
public class FrontHomeController implements Initializable {

    @FXML
    private ImageView tabAff_iv;
    @FXML
    private ImageView forum_iv;
    @FXML
    private ImageView offre_iv;
    @FXML
    private ImageView centre_iv;
    @FXML
    private ImageView users_iv;
    @FXML
    private ImageView club_iv;
    @FXML
    private ImageView out_iv;
    @FXML
    private ImageView profile_iv;
    @FXML
    private ImageView logo_iv;
    @FXML
    private Button tabAff_btn;
    @FXML
    private Button forum_btn;
    @FXML
    private Button offre_btn;
    @FXML
    private Button centre_btn;
    @FXML
    private Button users_btn;
    @FXML
    private Button clubs_btn;
    @FXML
    private AnchorPane rootPane;
    private int admin = 0;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        MyConnection.getInstance().getCnx();
        initImages();
    }    
    
    public void initImages() {
        File fileLogo = new File("images/logo1.png");
        Image logoI = new Image(fileLogo.toURI().toString());
        
        File fileAnnouncement = new File("images/announcement_grey_bigger.png");
        Image announcementI = new Image(fileAnnouncement.toURI().toString());
        
        File fileForum = new File("images/forum2_grey_bigger.png");
        Image forumI = new Image(fileForum.toURI().toString());
        
        File fileOffre = new File("images/briefcase_grey_bigger.png");
        Image offreI = new Image(fileOffre.toURI().toString());
        
        File fileDocs = new File("images/file_grey_bigger.png");
        Image docsI = new Image(fileDocs.toURI().toString());

        File fileUsers = new File("images/users_grey_bigger.png");
        Image usersI = new Image(fileUsers.toURI().toString());
        
        File fileAccount = new File("images/profile2.png");
        Image accountI = new Image(fileAccount.toURI().toString());

        File fileOut = new File("images/logout_grey.png");
        Image outI = new Image(fileOut.toURI().toString());
        
        File fileClub = new File("images/org_grey_bigger.png");
        Image clubI = new Image(fileClub.toURI().toString());

        logo_iv.setImage(logoI);
        tabAff_iv.setImage(announcementI);
        forum_iv.setImage(forumI);
        club_iv.setImage(clubI);
        offre_iv.setImage(offreI);
        centre_iv.setImage(docsI);
        users_iv.setImage(usersI);
        profile_iv.setImage(accountI);
        out_iv.setImage(outI);
        
    }

    @FXML
    private void getTabAff(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/news/frontNews.fxml"));
            Parent root = loader.load();
            rootPane.getScene().setRoot(root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void handleClicks(ActionEvent event) {
    }

    @FXML
    private void getForum(MouseEvent event) {
        if (this.admin == 0){
                try {
            
            //instance mtaa el crud
            //redirection
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FrontThread.fxml"));
            Parent root = loader.load();
            forum_iv.getScene().setRoot(root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
            }
        else if(this.admin ==1) {
             try {
            
            //instance mtaa el crud
            //redirection
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ThreadList.fxml"));
            Parent root = loader.load();
            forum_iv.getScene().setRoot(root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        }
        
    }

    @FXML
    private void getOffre(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/emploi/frontEmploi.fxml"));
            Parent root = loader.load();
            rootPane.getScene().setRoot(root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void getCentre(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/document/ListDocFront.fxml"));
            Parent root = loader.load();
            rootPane.getScene().setRoot(root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void getClubs(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/Clubs/ClubListStudent.fxml"));
            Parent root = loader.load();
            rootPane.getScene().setRoot(root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void logout(MouseEvent event) {
    }

    @FXML
    private void getProfile(MouseEvent event) {
    }

    @FXML
    private void getListeStudent(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/StudentClasse.fxml"));
            Parent root = loader.load();
            rootPane.getScene().setRoot(root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
}