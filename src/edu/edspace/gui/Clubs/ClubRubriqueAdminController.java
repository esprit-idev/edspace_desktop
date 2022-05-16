/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.edspace.gui.Clubs;

import edu.edspace.entities.ClubPub;
import edu.edspace.gui.news.allCategoryNewsController;
import edu.edspace.services.ClubPubService;
import edu.edspace.services.ClubService;
import edu.edspace.services.UserService;
import edu.edspace.utils.MyConnection;
import edu.edspace.utils.Statics;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author anash
 */
public class ClubRubriqueAdminController implements Initializable {

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
    @FXML
    private ScrollPane scroll;
    @FXML
    private Label clubName_l;
    @FXML
    private Label clubId;
    @FXML
    private Label clubDesc;
    @FXML
    private GridPane pubs;
    private List<ClubPub> pubsList = new ArrayList<>();
    private int clubid;
    @FXML
    private Button btndisplayPubenAttente;
    @FXML
    private Button badgeEnAttente;
    @FXML
    private ImageView enattenteIcon;
    @FXML
    private AnchorPane main;
    @FXML
    private ImageView clubPic;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initImages();

    }

    public void initData(int clubid) {
        MyConnection.getInstance().getCnx();
        ClubPubService cb = new ClubPubService();
        ClubService cp = new ClubService();
        //setbadge
        if (cb.displayHangingClubPubs(clubid).isEmpty()) {
            badgeEnAttente.setVisible(false);
        } else {
            badgeEnAttente.setText(String.valueOf(cb.displayHangingClubPubs(clubid).size()));
        }
        //set icon color
        Lighting lighting = new Lighting(new Light.Distant(45, 90, Color.rgb(250, 250, 250)));
        ColorAdjust bright = new ColorAdjust(0, 1, 1, 1);
        lighting.setContentInput(bright);
        lighting.setSurfaceScale(0.0);
        enattenteIcon.setEffect(lighting);

        //display hanging pubs
        enattenteIcon.setOnMouseClicked((MouseEvent event) -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/Clubs/ClubHangingPubsInterface.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root, 625, 500);
                Stage stage = new Stage();
                stage.setScene(scene);
                ClubHangingPubsInterfaceController clubHangingPubsInterfaceController = loader.getController();
                clubHangingPubsInterfaceController.initData(clubid);
                stage.initStyle(StageStyle.UTILITY);
                stage.show();
                stage.setOnCloseRequest(events -> {
                    pubs.getChildren().clear();
                    initData(clubid);
                });
            } catch (IOException ex) {
                Logger.getLogger(ClubRubriqueAdminController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        //setting actual pubs
        String clubPicS = cp.getClubImage(clubid);

        File clubimage = new File(Statics.ClubPic + clubPicS);
        Image clubimg = new Image(clubimage.toURI().toString());
        clubPic.setImage(clubimg);

        pubsList = cb.displayPostedClubPubs(clubid);
        int colu = 0;
        int row = 0;
        if (pubsList.isEmpty()) {
            HBox h = new HBox(300);
            h.setPadding(new Insets(50, 0, 0, 0));
            Label l1 = new Label("");

            Label l = new Label("Pas de publications.");
            h.getChildren().addAll(l1, l);
            pubs.add(h, colu++, row);
        } else {
            try {
                for (int i = 0; i < pubsList.size(); i++) {
                    FXMLLoader fxml = new FXMLLoader();
                    fxml.setLocation(getClass().getResource("/edu/edspace/gui/Clubs/ClubPubAdminItem.fxml"));

                    AnchorPane anchorPane = fxml.load();//child
                    ClubPubAdminItemController clubPubAdminItemController = fxml.getController();
                    clubPubAdminItemController.setData(pubsList.get(i), clubName_l.getText(),
                            clubPicS
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

    public void setClubDesc(String clubDesc) {
        this.clubDesc.setText(clubDesc);
    }

    public void initImages() {
        File fileLogo = new File("images/logo1.png");
        Image logoI = new Image(fileLogo.toURI().toString());

        File fileHome = new File("images/stats_grey.png");
        Image homeI = new Image(fileHome.toURI().toString());

        File fileTab = new File("images/announcement_grey.png");
        Image tabI = new Image(fileTab.toURI().toString());

        File fileLevel = new File("images/level_grey.png");
        Image levelI = new Image(fileLevel.toURI().toString());

        File fileClass = new File("images/class-management_grey.png");
        Image classI = new Image(fileClass.toURI().toString());

        File fileBook = new File("images/book_grey.png");
        Image bookI = new Image(fileBook.toURI().toString());

        File fileForum = new File("images/forum2_grey.png");
        Image forumI = new Image(fileForum.toURI().toString());

        File fileOffre = new File("images/briefcase_grey.png");
        Image offreI = new Image(fileOffre.toURI().toString());

        File fileDocs = new File("images/file_grey.png");
        Image docsI = new Image(fileDocs.toURI().toString());

        File fileUsers = new File("images/users_grey.png");
        Image usersI = new Image(fileUsers.toURI().toString());

        File fileClub = new File("images/org_grey.png");
        Image clubI = new Image(fileClub.toURI().toString());

        File fileOut = new File("images/logout_grey.png");
        Image outI = new Image(fileOut.toURI().toString());

        logo_iv.setImage(logoI);
        home_iv.setImage(homeI);
        tabaff_iv.setImage(tabI);
        users_iv.setImage(usersI);
        niveaux_iv.setImage(levelI);
        classe_iv.setImage(classI);
        matieres_iv.setImage(bookI);
        club_iv.setImage(clubI);
        offre_iv.setImage(offreI);
        forum_iv.setImage(forumI);
        centre_iv.setImage(docsI);
        signOut_iv.setImage(outI);
    }

    @FXML
    private void displayPubenAttente(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ClubHangingPubsInterface.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            ClubHangingPubsInterfaceController clubHangingPubsInterfaceController = loader.getController();
            clubHangingPubsInterfaceController.initData(clubid);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ClubRubriqueAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void dashboard(ActionEvent event) {
        try {
            //instance mtaa el crud
            //redirection
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/HomeBack.fxml"));
            Parent root = loader.load();
            club_iv.getScene().setRoot(root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void getNewsView(ActionEvent event) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/news/allNews.fxml"));
            club_iv.getScene().setRoot(pane);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void getUsers(ActionEvent event) {
        try {
            //instance mtaa el crud
            //redirection
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/AllAdmins.fxml"));
            Parent root = loader.load();
            club_iv.getScene().setRoot(root);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void getMatieres(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/matiere/MatieresList.fxml"));
            Parent root = loader.load();
            btnCustomers.getScene().setRoot(root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void getOffre(ActionEvent event) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/emploi/allEmploi.fxml"));
            club_iv.getScene().setRoot(pane);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void getdocs(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/document/DocsList.fxml"));
            Parent root = loader.load();
            club_iv.getScene().setRoot(root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void getNewsView(MouseEvent event) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/news/allNews.fxml"));
            club_iv.getScene().setRoot(pane);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void getForum(ActionEvent event) {
        try {
            //instance mtaa el crud
            //redirection
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/ThreadList.fxml"));
            Parent root = loader.load();
            clubDesc.getScene().setRoot(root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void getNiveau(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/Niveau/AllNiveau.fxml"));

            Parent root = loader.load();

            club_iv.getScene().setRoot(root);

        } catch (IOException ex) {

            ex.printStackTrace();

        }
    }

    @FXML
    private void getClasses(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/Classe/AllClasses.fxml"));

            Parent root = loader.load();

            club_iv.getScene().setRoot(root);

        } catch (IOException ex) {

            ex.printStackTrace();

        }
    }

    @FXML
    private void logout(ActionEvent event) {
        UserService US = new UserService();

        US.logout();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/User/Login.fxml"));

        try {

            Parent root = loader.load();

            club_iv.getScene().setRoot(root);

        } catch (IOException ex) {

            ex.printStackTrace();

        }
    }

    @FXML
    private void reload(MouseEvent event) {
    }

    @FXML
    private void displaylistClubs(ActionEvent event) {
        try {
            //instance mtaa el crud
            //redirection
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/Clubs/ClubListAdmin.fxml"));
            Parent root = loader.load();
            club_iv.getScene().setRoot(root);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
