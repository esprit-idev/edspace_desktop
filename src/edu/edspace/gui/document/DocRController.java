package edu.edspace.gui.document;

import edu.edspace.entities.Document;
import edu.edspace.entities.DocumentFavoris;
import edu.edspace.entities.Matiere;
import edu.edspace.entities.Niveau;
import edu.edspace.entities.Session;
import edu.edspace.services.DocumentFavorisService;
import edu.edspace.services.DocumentService;
import edu.edspace.services.MatiereService;
import edu.edspace.services.NiveauService;
import edu.edspace.utils.MyConnection;
import edu.edspace.utils.Statics;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javax.mail.MessagingException;

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

    @FXML
    private HBox hbox;

    private List<Matiere> mats = new ArrayList();
    private List<Niveau> niveaux = new ArrayList();
    private Document doc;
    private String role = Session.getRoles();
    
    private String currentUser = Session.getUsername()+" "+Session.getPrenom();
    private int currentUserId=Session.getId();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        MyConnection.getInstance().getCnx();
    }

    public void setData(Document doc) {
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
        fave_iv.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            pin_unpin(doc);
        });
        if (!Session.getRoles().contains("ADMIN")) {
            setFaveIv(doc);
        } else {
            hbox.getChildren().remove(fave_iv);
        }

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
            } else if (selected.equals("Modifier")) {
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
        //String currentUser = "Anas Houissa"; //to_change
        more_cb.getSelectionModel().clearSelection();
        more_cb.setItems(optionsList(currentUser));

    }

    private void pin_unpin(Document doc) {
        //int currentUserId = 5; //to_change
        DocumentFavoris fave = new DocumentFavoris(currentUserId, doc.getId());
        DocumentFavorisService dfs = new DocumentFavorisService();
        if (isPinned(doc)) {
            dfs.unpinDocument(fave);
        } else {
            dfs.pinDocument(fave);
        }
        setFaveIv(doc);
    }

    private void setFaveIv(Document doc) {
        File pinFile = new File("images/heart.png");
        Image pinI = new Image(pinFile.toURI().toString());

        File unpinFile = new File("images/heart-crossed.png");
        Image unpinI = new Image(unpinFile.toURI().toString());
        if (isPinned(doc)) {
            fave_iv.setImage(unpinI);
        } else {
            fave_iv.setImage(pinI);
        }
    }

    private void apercuDoc(Document doc) {
        DocumentService ds = new DocumentService();
        ds.apercuDocument(doc);
    }

    private void updateDoc(Document doc) {
        // Create the custom dialog
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Mise à jour Box");
        dialog.setHeaderText("Mise à jour du document " + doc.getNom());

        // Set the icon
        File fileRefresh = new File("images/refresh_green.png");
        Image refresh = new Image(fileRefresh.toURI().toString());
        dialog.setGraphic(new ImageView(refresh));
        // Set the button types
        ButtonType saveButtonType = new ButtonType("Enregistrer", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, cancelButtonType);

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
        if (result.get() == saveButtonType) {
            String newNiv = niveau_cb.getValue();
            String newMat = matiere_cb.getValue();
            DocumentService ds = new DocumentService();
            doc.setNiveau(newNiv);
            doc.setMatiere(newMat);
            ds.modifierDocument(doc);
            setData(doc);
        }
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
        } catch (FileAlreadyExistsException ex) {
            System.out.println(ex.getMessage());
            String title = "Erreur survenue lors de l'ajout";
            String header = "Un document avec le même nom existe déjà";
            String content = "Veuillez choisir un autre nom";
            showAlert(Alert.AlertType.ERROR, title, header, content);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void shareDoc(Document doc) {
        // Create the custom dialog
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Partager via gmail");
        dialog.setHeaderText("Partage de " + doc.getNom());

        // Set the icon
        File fileRefresh = new File("images/share_green.png");
        Image refresh = new Image(fileRefresh.toURI().toString());
        dialog.setGraphic(new ImageView(refresh));
        ButtonType sendButtonType = new ButtonType("Envoyer", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(sendButtonType, cancelButtonType);
        // Create the nom and niveau labels and fields
        //grid setting
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        //from tf init
        TextField from_tf = new TextField();

        //pwd tf init
        PasswordField pwd_tf = new PasswordField();

        //to tf init
        TextField to_tf = new TextField();

        //objet de l'email tf init
        TextField object_tf = new TextField();

        //contenu de l'email tf init
        TextField body_tf = new TextField();

        //add tf and cb to the grid +lables
        grid.add(new Label("Votre adresse gmail"), 0, 0);
        grid.add(from_tf, 1, 0);
        grid.add(new Label("Votre mot de passe gmail"), 0, 1);
        grid.add(pwd_tf, 1, 1);
        grid.add(new Label("Adresse de destination"), 0, 2);
        grid.add(to_tf, 1, 2);
        grid.add(new Label("Objet de l'email"), 0, 3);
        grid.add(object_tf, 1, 3);
        grid.add(new Label("Contenu de l'email"), 0, 4);
        grid.add(body_tf, 1, 4);
        dialog.getDialogPane().setContent(grid);
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.get() == sendButtonType) {
            DocumentService ds = new DocumentService();
            String from = from_tf.getText();
            String pwd = pwd_tf.getText();
            String to = to_tf.getText();
            String object = object_tf.getText();
            String body = body_tf.getText();
            try {
                if (from != null && from.length() != 0 && pwd != null && pwd.length() != 0 && to != null && to.length() != 0
                        && object != null && object.length() != 0 && body != null && body.length() != 0) {
                    ds.sendDocViaEmail(doc, from, pwd, to, object, body);
                } else {
                    String title = "Erreur survenue lors de l'envoi";
                    String header = "Veuillez remplir tous les champs";
                    String content = "Aucun champs ne doit être vide";
                    showAlert(Alert.AlertType.WARNING, title, header, content);
                }

            } catch (MessagingException ex) {
                System.out.println(ex.getMessage());
                String title = "Erreur survenue lors de l'envoi";
                String header = "L'envoi de l'email a échoué";
                String content = "Vérifier votre adresse mail et/ou votre mot de passe";
                showAlert(Alert.AlertType.ERROR, title, header, content);
            }
        }
    }

    private void reportDoc(Document doc) {
        String title = "Confirmation du signal";
        String header = "Êtes-vous sur de bien vouloir signaler ce document?";
        String content = "Ce document sera supprimé après examen s'il enfreigne les Standards de la plateforme";
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
            ds.signalerDocument(doc);
        } else {
            alert.close();
        }

    }

    //list of options in ObservableList
    private ObservableList<String> optionsList(String currentUser) {
        ObservableList<String> oblist = FXCollections.observableArrayList();
        oblist.add("Ouvrir");

        if (!Session.getRoles().contains("ADMIN")) {
            if (currentUser.equals(doc.getProp())) {
                oblist.add("Modifier");
                oblist.add("Supprimer");
            }
            oblist.add("Télécharger");
            oblist.add("Partager");
            oblist.add("Signaler");
        } else {
            oblist.add("Supprimer");
            oblist.add("Télécharger");
        }
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

    private boolean isPinned(Document doc) {
        //int currentUserId = 5; //to_change
        DocumentFavoris document = new DocumentFavoris(currentUserId, doc.getId());
        DocumentFavorisService dfs = new DocumentFavorisService();
        List<DocumentFavoris> lf = dfs.listFaves(currentUserId);
        if (lf.contains(document)) {
            return true;
        }
        return false;
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

}