/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.edspace.gui.Clubs;

import edu.edspace.entities.ClubCategory;
import edu.edspace.services.ClubCategService;
import edu.edspace.services.ClubPubService;
import edu.edspace.services.UserService;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
import javafx.stage.StageStyle;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author anash
 */
public class ClubCategoriesListController implements Initializable {

    @FXML
    private HBox hboxa;
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
    private ScrollPane scrolla;
    @FXML
    private Label addlabel;
    @FXML
    private Button btnAnnulerModif;
    @FXML
    private TextField c_name_tf;
    @FXML
    private Button addbtn;
    @FXML
    private GridPane grida;
    @FXML
    private TableView<ClubCategory> tab;
    @FXML
    private TableColumn<ClubCategory, String> tab_cat;
    @FXML
    private TableColumn<ClubCategory, String> tab_action;
    ObservableList<ClubCategory> categoriesList = FXCollections.observableArrayList();
    private int pubNB;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showCategoties();
        initImages();
    }

    @FXML
    private void addCategorie(ActionEvent event) {
        ClubCategService cb = new ClubCategService();

        if (addbtn.getText().toUpperCase().equals("AJOUTER")) {
            if (c_name_tf.getText().isEmpty()) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initStyle(StageStyle.UTILITY);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez remplir tous le champ du formulaire");
                alert.showAndWait();
            } else {
                ClubCategory categorie = new ClubCategory();
                categorie.setCategorie(c_name_tf.getText());
                if (cb.ajouterClubCat(categorie)) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.initStyle(StageStyle.UTILITY);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("La catégorie " + c_name_tf.getText() + " a été ajoutée");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) // alert is exited, no button has been pressed.
                    {
                        showCategoties();

                        c_name_tf.setText("");
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.initStyle(StageStyle.UTILITY);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("La catégorie n'a pas été ajoutée");
                    alert.showAndWait();
                }

            }
        } else {
            ClubCategory selectedClubCat = tab.getSelectionModel().getSelectedItem();
            if (c_name_tf.getText().isEmpty()) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initStyle(StageStyle.UTILITY);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez remplir tous le champ du formulaire");
                alert.showAndWait();
            } else {

                if (cb.updateClubCategories(c_name_tf.getText(), selectedClubCat.getId())) {
                    System.out.println(selectedClubCat.getId() + "  " + selectedClubCat.getCategorie());
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.initStyle(StageStyle.UTILITY);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("La catégorie a été modifiée");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) // alert is exited, no button has been pressed.
                    {
                        showCategoties();

                        c_name_tf.setText("");
                        addbtn.setText("Ajouter");
                        addlabel.setText("Ajouter une catégorie");
                        btnAnnulerModif.setVisible(false);
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.initStyle(StageStyle.UTILITY);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("La catégorie n'a pas été modifiée");
                    alert.showAndWait();
                }

            }

        }
    }

    public void showCategoties() {
        ClubCategService cb = new ClubCategService();
        cb.getCategories(categoriesList, tab);
        tab_cat.setCellValueFactory(new PropertyValueFactory<ClubCategory, String>("categorie"));

        //add cell of button edit 
        Callback<TableColumn<ClubCategory, String>, TableCell<ClubCategory, String>> cellFactory = (TableColumn<ClubCategory, String> param) -> {
            // make cell containing buttons
            final TableCell<ClubCategory, String> cell = new TableCell<ClubCategory, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        ImageView deleteIcon = new ImageView(new Image(new File("src/images/del.png").toURI().toString()));
                        ImageView editIcon = new ImageView(new Image(new File("src/images/edit.png").toURI().toString()));
                        deleteIcon.setFitHeight(30);
                        deleteIcon.setFitWidth(30);

                        editIcon.setFitHeight(30);
                        editIcon.setFitWidth(30);

                        Lighting lighting = new Lighting(new Light.Distant(45, 90, Color.rgb(250, 90, 90)));
                        ColorAdjust bright = new ColorAdjust(0, 1, 1, 1);
                        lighting.setContentInput(bright);
                        lighting.setSurfaceScale(0.0);
                        deleteIcon.setEffect(lighting);

                        Lighting lighting2 = new Lighting(new Light.Distant(45, 90, Color.rgb(20, 100, 120)));
                        ColorAdjust bright2 = new ColorAdjust(0, 1, 1, 1);
                        lighting2.setContentInput(bright2);
                        lighting2.setSurfaceScale(0.0);
                        editIcon.setEffect(lighting2);
                        deleteIcon.setOnMouseClicked((MouseEvent event) -> {
                            ClubCategory selectedCategorie = tab.getSelectionModel().getSelectedItem();
                            //alert
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.initStyle(StageStyle.UTILITY);
                            alert.setTitle("Supprimer la catégorie");
                            alert.setHeaderText(null);
                            alert.setContentText("Etes vous sur de vouloir supprimer la catégorie " + selectedCategorie.getCategorie() + " ?");
                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.get() == ButtonType.OK) // alert is exited, no button has been pressed.
                            {
                                ClubCategService cb = new ClubCategService();
                                if (cb.deleteClubCategories(selectedCategorie.getId())) {
                                    Alert alerts = new Alert(Alert.AlertType.INFORMATION);
                                    alerts.initStyle(StageStyle.UTILITY);
                                    alerts.setTitle("Success");
                                    alerts.setHeaderText(null);
                                    alerts.setContentText("La catégorie " + selectedCategorie.getCategorie() + " a été supprimée");
                                    alerts.showAndWait();
                                    showCategoties();
                                } else {
                                    Alert alertz = new Alert(Alert.AlertType.ERROR);
                                    alertz.initStyle(StageStyle.UTILITY);
                                    alertz.setTitle("Error");
                                    alertz.setHeaderText(null);
                                    alertz.setContentText("La catégorie n'a pas été supprimée");
                                    alertz.showAndWait();
                                }

                            }
                        });
                        editIcon.setOnMouseClicked((MouseEvent event) -> {
                            ClubCategory selectedCategorie = tab.getSelectionModel().getSelectedItem();

                            //alert
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.initStyle(StageStyle.UTILITY);
                            alert.setTitle("Modifier la catégorie");
                            alert.setHeaderText(null);
                            alert.setContentText("Etes vous sur de vouloir modifier la catégorie " + selectedCategorie.getCategorie() + " ?");
                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.get() == ButtonType.OK) // alert is exited, no button has been pressed.
                            {
                                addlabel.setText("Modifier la catégorie " + selectedCategorie.getCategorie());
                                addbtn.setText("Modifier");
                                c_name_tf.setText(selectedCategorie.getCategorie());
                                btnAnnulerModif.setVisible(true);
                                btnAnnulerModif.setOnAction(new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent arg0) {
                                        addlabel.setText("Ajouter un club");
                                        addbtn.setText("Ajouter");
                                        c_name_tf.setText("");
                                        btnAnnulerModif.setVisible(false);

                                    }
                                });
                            }
                        }
                        );

                        HBox managebtn = new HBox(editIcon, deleteIcon);
                        //  managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                        HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));

                        setGraphic(managebtn);

                        setText(null);

                    }
                }

            };

            return cell;
        };
        tab_action.setCellFactory(cellFactory);
        tab.setItems(categoriesList);
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
    private void getNewsView(MouseEvent event) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/news/allNews.fxml"));
            club_iv.getScene().setRoot(pane);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void displayClubs(ActionEvent event) {
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

    @FXML
    private void getForum(ActionEvent event) {
        try {
            //instance mtaa el crud
            //redirection
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/ThreadList.fxml"));
            Parent root = loader.load();
            club_iv.getScene().setRoot(root);
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

FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/Niveau/AllNiveau.fxml"));

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
}
