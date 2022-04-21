/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.edspace.gui.document;

import edu.edspace.entities.Document;
import edu.edspace.entities.Matiere;
import edu.edspace.entities.Niveau;
import edu.edspace.entities.Session;
import edu.edspace.services.DocumentService;
import edu.edspace.utils.MyConnection;
import edu.edspace.utils.Statics;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author MeriamBI
 */
public class DocRReportedController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Label date_label;

    @FXML
    private ImageView fave_iv;

    @FXML
    private HBox hbox;

    @FXML
    private Label matniv_label;

    @FXML
    private ComboBox<String> more_cb;

    @FXML
    private Label name_label;

    @FXML
    private Label owner_label;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private VBox vbox;

    private List<Matiere> mats = new ArrayList();
    private List<Niveau> niveaux = new ArrayList();
    private Document doc;
    
    String currentUser = Session.getUsername()+" "+Session.getPrenom();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        MyConnection.getInstance().getCnx();
    }

    public void setData(Document doc) {
        String role = Session.getRoles(); //to_change
        //String currentUser = "Anas Houissa"; //to_change
        this.doc = doc;
        date_label.setText(doc.getDate_insert());
        matniv_label.setText(doc.getNiveau() + " | " + doc.getMatiere());
        name_label.setText(doc.getNom());
        owner_label.setText(doc.getProp());
        more_cb.setItems(optionsList(currentUser));
        more_cb.setOnAction(e -> {
            doAction(doc);
            //more_cb.getSelectionModel().clearSelection();
        });
        hbox.getChildren().remove(fave_iv);

        /*more_cb.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            doAction(doc);
        });*/
    }
    
//actions to be triggered when choosing selecting option from more_cb
    private void doAction(Document doc) {
        String selected = more_cb.getValue();
        if (selected != null) {
            if (selected.equals("Ouvrir")) {
                apercuDoc(doc);
            } else if (selected.equals("Supprimer")) {
                deleteDoc(doc);
            } else if (selected.equals("Télécharger")) {
                downloadDoc(doc);
            } else if (selected.equals("Ignorer")) {
                ignoreReport(doc);
            }
        }
        //String currentUser = "Anas Houissa"; //to_change
        more_cb.getSelectionModel().clearSelection();
        more_cb.setItems(optionsList(currentUser));

    }
    
    private void apercuDoc(Document doc) {
        DocumentService ds = new DocumentService();
        ds.apercuDocument(doc);
    }
    
    private void deleteDoc(Document doc) {
        String title = "Confirmation de la suppression";
        String header = "Êtes-vous sur de bien vouloir supprimer ce document?";
        String content = "Ce document sera supprimé définitivement";
        final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.setResizable(true);
        //ButtonType confirmButtonType = new ButtonType("Oui", ButtonData.OK_DONE);
        //ButtonType denyButtonType = new ButtonType("Non", ButtonData.NO);
        //alert.getDialogPane().getButtonTypes().addAll(confirmButtonType, denyButtonType);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            DocumentService ds = new DocumentService();
            ds.supprimerDocument(doc);
            rootPane.getChildren().remove(vbox);

        } else {
            alert.close();
        }
    }
    
    private void downloadDoc(Document doc) {
        String chosenDir = Statics.initDir;
        Stage stage = (Stage) rootPane.getScene().getWindow();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File(Statics.initDir));
        File selectedDirectory = directoryChooser.showDialog(stage);
        chosenDir = selectedDirectory.getAbsolutePath();
        DocumentService ds = new DocumentService();
        try {
            ds.downloadDocument(doc, chosenDir);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            String title = "Erreur survenue lors du téléchargement";
            String header = "Ce document existe déjà dans " + chosenDir;
            String content = "Veuillez choisir un autre emplacement";
            showAlert(Alert.AlertType.ERROR, title, header, content);
        }
    }
    
    private void ignoreReport(Document doc){
        String title = "";
        String header = "Êtes-vous sur de bien vouloir ignorer le signal de ce document?";
        String content = "Ce document figurera dans le centre de partage";
        final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.setResizable(true);
        //ButtonType confirmButtonType = new ButtonType("Oui", ButtonData.OK_DONE);
        //ButtonType denyButtonType = new ButtonType("Non", ButtonData.NO);
        //alert.getDialogPane().getButtonTypes().addAll(confirmButtonType, denyButtonType);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            DocumentService ds = new DocumentService();
            ds.ignorerSignalDocument(doc);
            rootPane.getChildren().remove(vbox);
        } else {
            alert.close();
        }
    }
    
     //alert dialog sample
    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        final Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.setResizable(true);
        alert.showAndWait();
    }
    
    //list of niveaux in ObservableList
    private ObservableList<String> optionsList(String currentUser) {
        ObservableList<String> oblist = FXCollections.observableArrayList();
        oblist.add("Ouvrir");
        oblist.add("Télécharger");
        oblist.add("Supprimer");
        oblist.add("Ignorer");
        return oblist;
    }

}
