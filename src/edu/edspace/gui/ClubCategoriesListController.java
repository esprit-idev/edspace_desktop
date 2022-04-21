/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.edspace.gui;

import edu.edspace.entities.ClubCategory;
import edu.edspace.services.ClubCategService;
import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showCategoties();
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
                    System.out.println(selectedClubCat.getId()+"  "+selectedClubCat.getCategorie());
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
}
