/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.edspace.gui.document;

import edu.edspace.entities.Document;
import edu.edspace.entities.Matiere;
import edu.edspace.entities.Niveau;
import edu.edspace.services.DocumentService;
import edu.edspace.services.MatiereService;
import edu.edspace.services.NiveauService;
import edu.edspace.utils.MyConnection;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author MeriamBI
 */
public class DocRController implements Initializable {

    @FXML
    private Label name_label;
    @FXML
    private Label matniv_label;
    @FXML
    private Label owner_label;
    @FXML
    private Label date_label;
    
    @FXML
    private ComboBox<String> more_cb;
    @FXML
    private ImageView fave_iv;

    @FXML
    private AnchorPane rootPane;
    @FXML
    private VBox vbox;

    private List<Matiere> mats = new ArrayList();
    private List<Niveau> niveaux = new ArrayList();
    private Document doc;
    
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
    private void pin_unpin(MouseEvent event) {
    }

    public void setData(Document doc) {
        String currentUser = "Anas Houissa"; //to_change
        this.doc = doc;
        date_label.setText(doc.getDate_insert());
        matniv_label.setText(doc.getNiveau() + " | " + doc.getMatiere());
        name_label.setText(doc.getNom());
        owner_label.setText(doc.getProp());
        more_cb.setItems(optionsList(currentUser));
        more_cb.setOnAction(e->{
            doAction(doc);
            
                });
        /*more_cb.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            doAction(doc);
        });*/
    }
    //actions to be triggered when choosing selecting option from more_cb

    private void doAction(Document doc) {
        String selected=more_cb.getValue();
        if(selected!=null){
            if (selected.equals("Modifier")) {
            updateDoc(doc);
        } else if (selected.equals("Supprimer")) {
            deleteDoc(doc);
        } else if (selected.equals("Télécharger")) {
            downloadDoc(doc);
        } else if (selected.equals("Partager")) {
            shareDoc(doc);
        } else if (selected.equals("Signaler")) {
            reportDoc(doc);
        }
        }
        more_cb.setValue(null);
    }

    private void updateDoc(Document doc) {
        // Create the custom dialog
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Mise à jour Box");
        dialog.setHeaderText("Mise à jour du document " + doc.getNom());

        // Set the icon
        //dialog.setGraphic(new ImageView(this.getClass().getResource("login.png").toString()));
        // Set the button types
        ButtonType saveButtonType = new ButtonType("Enregistrer", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType);

        // Create the nom and niveau labels and fields
        //grid setting
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        //nom tf init
        TextField tf = new TextField();
        tf.setText(doc.getNom());
        tf.setDisable(true);

        //niveau combobox init
        ComboBox<String> niveau_cb = new ComboBox<>();
        niveau_cb.setItems(niveauxList());
        niveau_cb.setValue(doc.getNiveau());

        //niveau combobox init
        ComboBox<String> matiere_cb = new ComboBox<>();
        matiere_cb.setValue(doc.getMatiere());
        matiere_cb.setItems(matieresListFiltered(niveau_cb.getValue()));
        niveau_cb.valueProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue ov, String oldVal, String newVal) {
                matiere_cb.setItems(matieresListFiltered(niveau_cb.getValue()));
            }
        });

        //add tf and cb to the grid +lables
        grid.add(new Label("Nom du document:"), 0, 0);
        grid.add(tf, 1, 0);
        grid.add(new Label("Niveau concerné:"), 0, 1);
        grid.add(niveau_cb, 1, 1);
        grid.add(new Label("Matière concernée:"), 0, 2);
        grid.add(matiere_cb, 1, 2);
        dialog.getDialogPane().setContent(grid);
        Optional<ButtonType> result = dialog.showAndWait();
        String newNiv = niveau_cb.getValue();
        System.out.println(newNiv);
        String newMat = matiere_cb.getValue();
        System.out.println(newMat);
        DocumentService ds = new DocumentService();
        doc.setNiveau(newNiv);
        doc.setMatiere(newMat);
        System.out.println(doc.toString());
        ds.modifierDocument(doc);
        refreshView();
        //refreshView();
    }

    private void deleteDoc(Document doc) {
        System.out.println("Supprimer");
        String title = "Confirmation de la suppression";
        String header = "Ce document sera supprimé définitivement";
        String content = "Êtes-vous sur de bien vouloir supprimer ce document?";
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
        System.out.println("Télécharger");
    }

    private void shareDoc(Document doc) {
        System.out.println("Partager");
    }

    private void reportDoc(Document doc) {
        System.out.println("Signaler");
    }

    protected void refreshView() {
        setData(doc);
        
    }
    
    //list of niveaux in ObservableList
    private ObservableList<String> optionsList(String currentUser) {
        ObservableList<String> oblist = FXCollections.observableArrayList();
        if (currentUser.equals(doc.getProp())) {
            oblist.add("Modifier");
            oblist.add("Supprimer");
        }
        oblist.add("Télécharger");
        oblist.add("Partager");
        oblist.add("Signaler");
        return oblist;
    }

    //list of niveaux in ObservableList
    private ObservableList<String> niveauxList() {
        ObservableList<String> oblistN = FXCollections.observableArrayList();
        NiveauService ns = new NiveauService();
        niveaux = ns.listeNiveaux();
        for (int i = 0; i < niveaux.size(); i++) {
            oblistN.add(niveaux.get(i).getId());
        }
        return oblistN;
    }

    //list of matieres filtered by niveau in ObservableList
    private ObservableList<String> matieresListFiltered(String niveau) {
        ObservableList<String> oblistM = FXCollections.observableArrayList();
        MatiereService ns = new MatiereService();
        mats = ns.filterByNiveau(niveau);
        for (int i = 0; i < mats.size(); i++) {
            oblistM.add(mats.get(i).getId());
        }
        return oblistM;
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

    public void initImages() {
        File pinFile = new File("images/heart.png");
        Image pinI = new Image(pinFile.toURI().toString());
        
        File unpinFile = new File("images/heart-crossed.png");
        Image unpinI = new Image(unpinFile.toURI().toString());
        
        fave_iv.setImage(pinI);
    }

}
