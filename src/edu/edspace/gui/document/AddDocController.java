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
import edu.edspace.services.MatiereService;
import edu.edspace.services.NiveauService;
import edu.edspace.utils.MyConnection;
import edu.edspace.utils.Statics;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author MeriamBI
 */
public class AddDocController implements Initializable {

    @FXML
    private TextField nom_tf;
    @FXML
    private ComboBox<String> niveau_cb;
    @FXML
    private ComboBox<String> matiere_cb;
    @FXML
    private Button chooseFile_btn;
    @FXML
    private Button add_btn;
    @FXML
    private RadioButton attachFile_rb;
    @FXML
    private ToggleGroup group;
    @FXML
    private RadioButton insertUrl_rb;
    @FXML
    private TextField url_tf;
    @FXML
    private ImageView out_iv;
    @FXML
    private ImageView home_iv;
    @FXML
    private ImageView logo_iv;

    private List<Matiere> mats = new ArrayList();
    private List<Niveau> niveaux = new ArrayList();
    @FXML
    private AnchorPane rootPane;
    @FXML
    private ImageView back_iv;
    
    private String owner = Session.getUsername()+" "+Session.getPrenom();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        MyConnection.getInstance().getCnx();
        initImages();
        initDisplay();
    }

    private void initDisplay() {
        niveau_cb.setItems(niveauxList());
        niveau_cb.valueProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue ov, String oldVal, String newVal) {
                matiere_cb.setItems(matieresListFiltered(niveau_cb.getValue()));
            }
        });
    }

    @FXML
    private void handleClicks(ActionEvent event) {
    }

    @FXML
    private void chooseFile(MouseEvent event) {
        List<String> listExt = new ArrayList<>();
        listExt.add("*.jpg");
        listExt.add("*.jpeg");
        listExt.add("*.png");
        listExt.add("*.pdf");
        listExt.add("*.zip");
        listExt.add("*.JPG");
        listExt.add("*.JPEG");
        listExt.add("*.PNG");
        listExt.add("*.PDF");
        listExt.add("*.ZIP");
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Files", listExt));
        File file = fc.showOpenDialog(null);
        if (file != null) {
            chooseFile_btn.setText(file.getAbsolutePath());
        }
    }

    @FXML
    private void addDoc(MouseEvent event) {
        String docName = nom_tf.getText();
        String niveau = niveau_cb.getValue();
        String matiere = matiere_cb.getValue();
        String file = null;
        String url = null;
        String type = "url";
        int signal = 0;
        if (attachFile_rb.isSelected()) {
            int index = chooseFile_btn.getText().lastIndexOf('.');
            if (index > 0) {
                docName += "." + chooseFile_btn.getText().substring(index + 1);
            }
            file = chooseFile_btn.getText();
            type = URLConnection.guessContentTypeFromName(file);
            try {
                Files.copy(Paths.get(file), Paths.get(Statics.myDocs + docName));
            } catch (FileAlreadyExistsException ex) {
                System.out.println(ex.getMessage());
                String title = "Erreur survenue lors de l'ajout";
                String header = "Un document avec le même nom existe déjà";
                String content = "Veuillez choisir un autre nom";
                showAlert(Alert.AlertType.ERROR, title, header, content);
            } catch (IOException ex) {
                Logger.getLogger(AddDocController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            url = url_tf.getText();
        }
        //String owner = "Anas Houissa"; //to_change 
        String insert_date = new SimpleDateFormat("dd/MM/yy").format(new Date());
        if (insertUrl_rb.isSelected() && isValid(url) == false) {
            String title = "Erreur survenue lors de l'ajout";
            String header = "URL invalide";
            String content = "Veuillez saisir une URL valide exemple:'https://www.google.com/'";
            showAlert(Alert.AlertType.ERROR, title, header, content);
        } else if (docName != null && docName.length() != 0 && niveau != null && niveau.length() != 0 && matiere != null && matiere.length() != 0) {
            Document doc = new Document(signal, docName, insert_date, owner, url, niveau, matiere, type);
            DocumentService ds = new DocumentService();
            try {
                ds.ajouterDocument(doc);
            } catch (FileAlreadyExistsException ex) {
                System.out.println(ex.getMessage());
                String title = "Erreur survenue lors de l'ajout";
                String header = "Un document avec le même nom existe déjà";
                String content = "Veuillez choisir un autre nom";
                showAlert(Alert.AlertType.ERROR, title, header, content);
            }
        } else {
            Document doc = new Document(signal, docName, insert_date, owner, url, niveau, matiere, type);
            System.out.println(doc.toString());
            String title = "Erreur survenue lors de l'ajout";
            String header = "Veuillez remplir tous les champs";
            String content = "Aucun champs ne doit être vide";
            showAlert(Alert.AlertType.WARNING, title, header, content);
        }
    }

    @FXML
    private void checkRadio() {
        if (attachFile_rb.isSelected()) {
            chooseFile_btn.setVisible(true);
            url_tf.setVisible(false);

        }
        if (insertUrl_rb.isSelected()) {
            chooseFile_btn.setVisible(false);
            url_tf.setVisible(true);
        }
    }

    //url validator
    public static boolean isValid(String url) {
        /* Try creating a valid URL */
        try {
            new URL(url).toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
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
        File fileLogo = new File("images/logo1.png");
        Image logoI = new Image(fileLogo.toURI().toString());

        File fileHome = new File("images/home_grey.png");
        Image homeI = new Image(fileHome.toURI().toString());

        File fileOut = new File("images/logout_grey.png");
        Image outI = new Image(fileOut.toURI().toString());

        File fileBack = new File("images/back_grey.png");
        Image backI = new Image(fileBack.toURI().toString());

        logo_iv.setImage(logoI);
        home_iv.setImage(homeI);
        out_iv.setImage(outI);
        back_iv.setImage(backI);
    }

    @FXML
    private void getHome(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/FrontHome.fxml"));
            Parent root = loader.load();
            rootPane.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(AddDocController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void getCentre(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/document/ListDocFront.fxml"));
            Parent root = loader.load();
            rootPane.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(AddDocController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
