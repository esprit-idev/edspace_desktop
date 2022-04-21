/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.edspace.gui;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import edu.edspace.entities.Club;
import edu.edspace.entities.ClubCategory;
import edu.edspace.entities.User;
import edu.edspace.services.ClubCategService;
import edu.edspace.services.ClubPubService;
import edu.edspace.services.ClubService;
import edu.edspace.services.StudentService;
import edu.edspace.utils.MyConnection;
import edu.edspace.utils.Statics;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javax.swing.text.AbstractDocument.Content;

/**
 * FXML Controller class
 *
 * @author anash
 */
public class ClubListAdminController implements Initializable {

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
    private TextField c_name_tf;
    @FXML
    private ComboBox<String> c_cat;
    @FXML
    private ComboBox<String> c_respo;
    @FXML
    private TextArea c_desc_tf;
    @FXML
    private Button addbtn;
    @FXML
    private GridPane grida;
    @FXML
    private TableView<Club> tab;
    @FXML
    private TableColumn<Club, String> tab_nom;
    @FXML
    private TableColumn<Club, String> tab_desc;
    @FXML
    private TableColumn<Club, String> tab_cat;
    @FXML
    private TableColumn<Club, String> tab_res;
    @FXML
    private TableColumn<Club, String> tab_action;

    ObservableList<Club> clubList = FXCollections.observableArrayList();
    ObservableList<String> students = FXCollections.observableArrayList();
    ObservableList<String> categories = FXCollections.observableArrayList();
    List<User> studentsList = new ArrayList<>();
    @FXML
    private Label addlabel;
    @FXML
    private Button btnAnnulerModif;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tab_desc.setCellFactory(WRAPPING_CELL_FACTORY);
        ClubCategService cb = new ClubCategService();
        categories.add("-- Club Categories --");

        c_cat.setItems(categories);
        c_cat.setValue("-- Club Categories --");

        MyConnection.getInstance().getCnx();
        List<ClubCategory> clubCat = new ArrayList<>();
        clubCat = cb.displayClubCategories();
        for (ClubCategory clubcat : clubCat) {
            categories.add(clubcat.getCategorie());
        }
        c_cat.setItems(categories);

        students.add("-- Club Responsable --");
        c_respo.setItems(students);
        c_respo.setValue("-- Club Responsable --");

        ClubService c = new ClubService();
        studentsList = c.listStudentNotResponsable();
        for (User user : studentsList) {
            students.add(user.getEmail());
        }
        c_respo.setItems(students);
        categories.add("Ajouter des categories");
        c_cat.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                if (c_cat.getValue().equals("Ajouter des categories")) {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("ClubCategoriesList.fxml"));
                        Parent root = loader.load();
                        btn_Club.getScene().setRoot(root);
                    } catch (IOException ex) {
                        Logger.getLogger(ClubListAdminController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        showClubs();
    }

    public void showClubs() {
        ClubService cb = new ClubService();
        cb.getClubs(clubList, tab);
        tab_nom.setCellValueFactory(new PropertyValueFactory<Club, String>("clubName"));
        tab_cat.setCellValueFactory(new PropertyValueFactory<Club, String>("clubCategorie"));
        tab_desc.setCellValueFactory(new PropertyValueFactory<Club, String>("clubDesc"));
        tab_res.setCellValueFactory(new PropertyValueFactory<Club, String>("clubRespo"));
        //add cell of button edit 
        Callback<TableColumn<Club, String>, TableCell<Club, String>> cellFactory = (TableColumn<Club, String> param) -> {
            // make cell containing buttons
            final TableCell<Club, String> cell = new TableCell<Club, String>() {
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
                        ImageView consultIcon = new ImageView(new Image(new File("src/images/consult.png").toURI().toString()));

                        deleteIcon.setFitHeight(30);
                        deleteIcon.setFitWidth(30);

                        editIcon.setFitHeight(30);
                        editIcon.setFitWidth(30);

                        consultIcon.setFitHeight(30);
                        consultIcon.setFitWidth(30);

                        Lighting lighting1 = new Lighting(new Light.Distant(45, 90, Color.rgb(55, 180, 98)));
                        ColorAdjust bright1 = new ColorAdjust(0, 1, 1, 1);
                        lighting1.setContentInput(bright1);
                        lighting1.setSurfaceScale(0.0);
                        consultIcon.setEffect(lighting1);

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

                        consultIcon.setOnMouseClicked((MouseEvent event) -> {
                            try {
                                Club selectedClub = tab.getSelectionModel().getSelectedItem();
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("ClubRubriqueAdmin.fxml"));
                                Parent root = loader.load();
                                ClubRubriqueAdminController clubRubriqueAdminController = loader.getController();
                                clubRubriqueAdminController.setClubName_l("Rubrique club " + selectedClub.getClubName());
                                clubRubriqueAdminController.setClubid(Integer.parseInt(selectedClub.getClubId()));
                                clubRubriqueAdminController.setClubDesc(selectedClub.getClubDesc());
                                clubRubriqueAdminController.initData(Integer.parseInt(selectedClub.getClubId()));
                                btn_Club.getScene().setRoot(root);
                            } catch (IOException ex) {
                                Logger.getLogger(ClubListAdminController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });
                        deleteIcon.setOnMouseClicked((MouseEvent event) -> {
                            Club selectedClub = tab.getSelectionModel().getSelectedItem();
                            //alert
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.initStyle(StageStyle.UTILITY);
                            alert.setTitle("Supprimer le club");
                            alert.setHeaderText(null);
                            alert.setContentText("Etes vous sur de vouloir supprimer le club " + selectedClub.getClubName() + " ?");
                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.get() == ButtonType.OK) // alert is exited, no button has been pressed.
                            {
                                ClubService cb = new ClubService();
                                if (cb.deleteClub(Integer.parseInt(selectedClub.getClubId()))) {
                                    Alert alerts = new Alert(Alert.AlertType.INFORMATION);
                                    alerts.initStyle(StageStyle.UTILITY);
                                    alerts.setTitle("Success");
                                    alerts.setHeaderText(null);
                                    alerts.setContentText("Le club " + selectedClub.getClubName() + " a été supprimé");
                                    alerts.showAndWait();
                                    c_respo.getItems().clear();
                                    c_respo.getItems().add("-- Club Responsable --");
                                    studentsList = cb.listStudentNotResponsable();
                                    for (User user : studentsList) {
                                        students.add(user.getEmail());
                                    }
                                    c_respo.setItems(students);
                                    showClubs();
                                    c_respo.setValue("-- Club Responsable --");
                                } else {
                                    Alert alertz = new Alert(Alert.AlertType.ERROR);
                                    alertz.initStyle(StageStyle.UTILITY);
                                    alertz.setTitle("Error");
                                    alertz.setHeaderText(null);
                                    alertz.setContentText("Le club n'a pas été supprimé");
                                    alertz.showAndWait();
                                }

                            }
                        });
                        editIcon.setOnMouseClicked((MouseEvent event) -> {
                            Club selectedClub = tab.getSelectionModel().getSelectedItem();

                            //alert
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.initStyle(StageStyle.UTILITY);
                            alert.setTitle("Modifier le club");
                            alert.setHeaderText(null);
                            alert.setContentText("Etes vous sur de vouloir modifier le club " + selectedClub.getClubName() + " ?");
                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.get() == ButtonType.OK) // alert is exited, no button has been pressed.
                            {
                                addlabel.setText("Modifier le club " + selectedClub.getClubName());
                                addbtn.setText("Modifier");
                                c_name_tf.setText(selectedClub.getClubName());
                                c_desc_tf.setText(selectedClub.getClubDesc());
                                c_cat.setValue(selectedClub.getClubCategorie());
                                c_respo.getItems().add(selectedClub.getClubRespo());
                                c_respo.setValue(selectedClub.getClubRespo());
                                btnAnnulerModif.setVisible(true);
                                btnAnnulerModif.setOnAction(new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent arg0) {
                                        addlabel.setText("Ajouter un club");
                                        addbtn.setText("Ajouter");
                                        c_name_tf.setText("");
                                        c_desc_tf.setText("");
                                        c_respo.getItems().remove(selectedClub.getClubRespo());
                                        c_cat.setValue("-- Club Categories --");
                                        c_respo.setValue("-- Club Responsable --");
                                        btnAnnulerModif.setVisible(false);

                                    }
                                });
                            }
                        }
                        );

                        HBox managebtn = new HBox(editIcon, deleteIcon, consultIcon);
                        //  managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                        HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));
                        HBox.setMargin(consultIcon, new Insets(2, 3, 0, 2));

                        setGraphic(managebtn);

                        setText(null);

                    }
                }

            };

            return cell;
        };
        tab_action.setCellFactory(cellFactory);
        tab.setItems(clubList);
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
    private void addClub(ActionEvent event) {
        ClubService cb = new ClubService();

        if (addbtn.getText().toUpperCase().equals("AJOUTER")) {
            if (c_respo.getValue().equals("-- Club Responsable --") || c_cat.getValue().equals("-- Club Categories --") || c_name_tf.getText().isEmpty() || c_desc_tf.getText().isEmpty()) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initStyle(StageStyle.UTILITY);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez remplir tous les champs du formulaire");
                alert.showAndWait();
            } else {
                Club club = new Club();
                club.setClubName(c_name_tf.getText());
                club.setClubDesc(c_desc_tf.getText());
                club.setClubRespo(String.valueOf(cb.getRespoid(c_respo.getValue())));
                club.setClubCategorie(String.valueOf(cb.getCatId(c_cat.getValue())));
                if (cb.ajouterClub(club)) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.initStyle(StageStyle.UTILITY);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("Le club " + c_name_tf.getText() + " a été ajouté");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) // alert is exited, no button has been pressed.
                    {
                        ClubService c = new ClubService();
                        c_respo.getItems().clear();
                        c_respo.getItems().add("-- Club Responsable --");
                        studentsList = c.listStudentNotResponsable();
                        for (User user : studentsList) {
                            students.add(user.getEmail());
                        }
                        c_respo.setItems(students);
                        showClubs();
                        c_respo.setValue("-- Club Responsable --");

                        c_name_tf.setText("");
                        c_cat.setValue("-- Club Categories --");
                        c_desc_tf.setText("");

                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.initStyle(StageStyle.UTILITY);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Le club n'a pas été ajouté");
                    alert.showAndWait();
                }

            }
        } else {
            Club selectedClub = tab.getSelectionModel().getSelectedItem();
            if (c_respo.getValue().equals("-- Club Responsable --") || c_cat.getValue().equals("-- Club Categories --") || c_name_tf.getText().isEmpty() || c_desc_tf.getText().isEmpty()) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initStyle(StageStyle.UTILITY);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez remplir tous les champs du formulaire");
                alert.showAndWait();
            } else {
                Club club = new Club();
                club.setClubName(c_name_tf.getText());
                club.setClubDesc(c_desc_tf.getText());
                club.setClubRespo(String.valueOf(cb.getRespoid(c_respo.getValue())));
                club.setClubCategorie(String.valueOf(cb.getCatId(c_cat.getValue())));
                if (cb.updateClub(club, Integer.parseInt(selectedClub.getClubId()))) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.initStyle(StageStyle.UTILITY);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("Le club " + c_name_tf.getText() + " a été modifié");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) // alert is exited, no button has been pressed.
                    {
                        ClubService c = new ClubService();
                        c_respo.getItems().clear();
                        c_respo.getItems().add("-- Club Responsable --");
                        studentsList = c.listStudentNotResponsable();
                        for (User user : studentsList) {
                            students.add(user.getEmail());
                        }
                        c_respo.setItems(students);
                        showClubs();
                        c_respo.setValue("-- Club Responsable --");

                        c_name_tf.setText("");
                        c_cat.setValue("-- Club Categories --");
                        c_desc_tf.setText("");
                        addbtn.setText("Ajouter");
                        addlabel.setText("Ajouter un club");
                        btnAnnulerModif.setVisible(false);
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.initStyle(StageStyle.UTILITY);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Le club n'a pas été modifié");
                    alert.showAndWait();
                }

            }

        }

    }
    public static final Callback<TableColumn<Club, String>, TableCell<Club, String>> WRAPPING_CELL_FACTORY
            = new Callback<TableColumn<Club, String>, TableCell<Club, String>>() {

        @Override
        public TableCell<Club, String> call(TableColumn<Club, String> param) {
            TableCell<Club, String> tableCell = new TableCell<Club, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    if (item == getItem()) {
                        return;
                    }

                    super.updateItem(item, empty);

                    if (item == null) {
                        super.setText(null);
                        super.setGraphic(null);
                    } else {
                        super.setText(null);
                        Label l = new Label(item);
                        l.setWrapText(true);
                        VBox box = new VBox(l);
                        l.heightProperty().addListener((observable, oldValue, newValue) -> {
                            box.setPrefHeight(newValue.doubleValue() + 7);
                            Platform.runLater(() -> this.getTableRow().requestLayout());
                        });
                        super.setGraphic(box);
                    }
                }
            };
            return tableCell;
        }
    };
}
