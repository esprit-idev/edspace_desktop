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
import edu.edspace.utils.Statics;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
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
public class DocAddController implements Initializable {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private ComboBox<String> niveau_cb;
    @FXML
    private ComboBox<String> matiere_cb;
    @FXML
    private RadioButton attachFile_rb;
    @FXML
    private RadioButton insertUrl_rb;
    @FXML
    private Button chooseFile_btn;
    @FXML
    private TextField url_tf;
    @FXML
    private Button add_btn;
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
    private ImageView emploi_v;
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
    private ImageView profil_iv;
    @FXML
    private Button btnSignout;
    @FXML
    private ImageView signOut_iv;
    @FXML
    private ToggleGroup group;

    private List<Matiere> mats = new ArrayList();
    private List<Niveau> niveaux = new ArrayList();
    @FXML
    private TextField nom_tf;
    @FXML
    private AnchorPane childPane;

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
            } catch (IOException ex) {
                Logger.getLogger(DocAddController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            url = url_tf.getText();
        }
        String owner = "Anas Houissa"; //to_change
        String insert_date = new SimpleDateFormat("dd/MM/yy").format(new Date());
        if (insertUrl_rb.isSelected() && isValid(url) == false) {
            String title = "Erreur survenue lors de l'ajout";
            String header = "URL invalide";
            String content = "Veuillez saisir une URL valide exemple:'https://www.google.com/'";
            showAlert(Alert.AlertType.ERROR, title, header, content);
        } else if (docName != null && docName.length() != 0 && niveau != null && niveau.length() != 0 && matiere != null && matiere.length() != 0) {
            System.out.println(type);
            Document doc = new Document(signal, docName, insert_date, owner, url, niveau, matiere, type);
            
            System.out.println(doc.toString());
            DocumentService ds = new DocumentService();
            ds.ajouterDocument(doc);
        } else {
            Document doc = new Document(signal, docName, insert_date, owner, url, niveau, matiere, type);
            System.out.println(doc.toString());
            String title = "Erreur survenue lors de l'ajout";
            String header = "Veuillez remplir tous les champs";
            String content = "Aucun champs ne doit être vide";
            showAlert(Alert.AlertType.ERROR, title, header, content);
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
            Logger.getLogger(DocAddController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
