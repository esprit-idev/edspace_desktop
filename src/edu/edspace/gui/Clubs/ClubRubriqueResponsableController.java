/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.edspace.gui.Clubs;

import edu.edspace.entities.ClubPub;
import edu.edspace.services.ClubPubService;
import edu.edspace.services.ClubService;
import edu.edspace.utils.MyConnection;
import edu.edspace.utils.Statics;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.StageStyle;
import javafx.util.Pair;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author anash
 */
public class ClubRubriqueResponsableController implements Initializable {

    @FXML
    private ScrollPane scroll;
    @FXML
    private Label clubName_l;
    @FXML
    private ImageView clubPic;
    @FXML
    private Label clubDesc;
    @FXML
    private TextArea pubdesc_et;
    @FXML
    private GridPane pubs;

    @FXML
    private Label clubId;
    private List<ClubPub> pubsList = new ArrayList<>();
    private int clubid;
    @FXML
    private Button btndisplayPubenAttente;
    @FXML
    private Button badgeEnAttente;
    @FXML
    private Button btndisplayPubRefused;
    @FXML
    private Button badgeRefused;
    @FXML
    private ImageView refusedIcon;
    @FXML
    private ImageView enattenteIcon;
    @FXML
    private Button publier_btn;
    @FXML
    private Button btnPubImg;
    @FXML
    private Button btnPubFile;
    @FXML
    private TextField pubimgSelected;
    @FXML
    private TextField pubFileSelected;
    @FXML
    private Button camerabtn;
    @FXML
    private ImageView addpicIV;
    @FXML
    private Button clubdescbtn;
    @FXML
    private ImageView addClubdesc;
    @FXML
    private ImageView ClubListIV;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {


    }

    public void initData(int clubid) {
        //set icons color
        Lighting lighting = new Lighting(new Light.Distant(45, 90, Color.rgb(250, 250, 250)));
        ColorAdjust bright = new ColorAdjust(0, 1, 1, 1);
        lighting.setContentInput(bright);
        lighting.setSurfaceScale(0.0);
        enattenteIcon.setEffect(lighting);
        refusedIcon.setEffect(lighting);
        addpicIV.setEffect(lighting);
        addClubdesc.setEffect(lighting);
        MyConnection.getInstance().getCnx();
        ClubPubService cb = new ClubPubService();
        pubsList = cb.displayPostedClubPubs(clubid);
        int colu = 0;
        int row = 0;
        if (pubsList.isEmpty()) {
            Label l = new Label("Pas de publications.");
            pubs.add(l, colu++, row);
        } else {
            try {
                for (int i = 0; i < pubsList.size(); i++) {

                    FXMLLoader fxml = new FXMLLoader();
                    fxml.setLocation(getClass().getResource("/edu/edspace/gui/Clubs/ClubPubItem.fxml"));

                    AnchorPane anchorPane = fxml.load();//child
                    ClubPubItemController clubPubItemController = fxml.getController();
                    clubPubItemController.setData(pubsList.get(i), clubName_l.getText(),
                            clubPic.getImage().getUrl()
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


    @FXML
    private void addPub(ActionEvent event) {

        if (pubdesc_et.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Le champ description de la publication ne peut pas étre vide");
            alert.showAndWait();
        } else {
            //add file
            String fileName = uniqueFilename();
            String fileSelceted = "";
            String typefile = "";
            int indexs = pubFileSelected.getText().lastIndexOf('.');
            if (indexs > 0) {
                fileName += "." + pubFileSelected.getText().substring(indexs + 1);
            }
            fileSelceted = pubFileSelected.getText();
            typefile = URLConnection.guessContentTypeFromName(fileSelceted);
            try {
                Files.copy(Paths.get(fileSelceted), Paths.get(Statics.ClubPubsFile + fileName));
            } catch (IOException ex) {
                Logger.getLogger(ClubAddController.class.getName()).log(Level.SEVERE, null, ex);
            }
            //add img
            String imgName = uniqueFilename();
            String imgSelceted = "";
            String typeImg = "";
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("La publication est envoyée, attendez l'approbation de l'administrateur");
            alert.showAndWait();
            ClubPubService cb = new ClubPubService();
            int index = pubimgSelected.getText().lastIndexOf('.');
            if (index > 0) {
                imgName += "." + pubimgSelected.getText().substring(index + 1);
            }
            imgSelceted = pubimgSelected.getText();
            typeImg = URLConnection.guessContentTypeFromName(imgSelceted);
            try {
                Files.copy(Paths.get(imgSelceted), Paths.get(Statics.ClubPubsPic + imgName));
            } catch (IOException ex) {
                Logger.getLogger(ClubAddController.class.getName()).log(Level.SEVERE, null, ex);
            }
            //add pub
            ClubPub pub = new ClubPub(pubdesc_et.getText(), imgName, fileName, null, clubid, clubid);
            cb.ajouterPubClub(pub);
            pubimgSelected.setText("");
            pubFileSelected.setText("");
            initData(clubid);
            pubdesc_et.setText("");

        }

    }

    public void setClubid(int clubid) {
        this.clubid = clubid;
    }

    public void setClubName_l(String clubName_l) {
        this.clubName_l.setText(clubName_l);
    }

    public void setClubPic(Image clubPic) {
        this.clubPic.setImage(clubPic);
    }

    public void setClubDesc(String clubDesc) {
        this.clubDesc.setText(clubDesc);
    }


    @FXML
    private void displayPubenAttente(ActionEvent event) {
    }

    @FXML
    private void displayPubRefused(ActionEvent event) {
    }

    @FXML
    private void SelectPubImg(ActionEvent event) {

        List<String> listExt = new ArrayList<>();
        listExt.add("*.jpg");
        listExt.add("*.jpeg");
        listExt.add("*.png");
        listExt.add("*.JPG");
        listExt.add("*.JPEG");
        listExt.add("*.PNG");
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Files", listExt));
        File file = fc.showOpenDialog(null);
        if (file != null) {
            pubimgSelected.setText(file.getAbsolutePath());
        }
    }

    @FXML
    private void SelectPubFile(ActionEvent event) {
        List<String> listExt = new ArrayList<>();
        listExt.add("*.xlsx");
        listExt.add("*.docx");
        listExt.add("*.potx");
        listExt.add("*.pptx");
        listExt.add("*.pps");
        listExt.add("*.ppsx");
        listExt.add("*.pdf");
        listExt.add("*.dotx");
        listExt.add("*.XLSX");
        listExt.add("*.DOCX");
        listExt.add("*.POTX");
        listExt.add("*.PPTX");
        listExt.add("*.PPS");
        listExt.add("*.PPSX");
        listExt.add("*.PDF");
        listExt.add("*.DOTX");
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Files", listExt));
        File file = fc.showOpenDialog(null);
        if (file != null) {
            pubFileSelected.setText(file.getAbsolutePath());
        }
    }

    public String uniqueFilename() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 40;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }

    @FXML
    private void changeClubPic(ActionEvent event) {
        List<String> listExt = new ArrayList<>();
        listExt.add("*.jpg");
        listExt.add("*.jpeg");
        listExt.add("*.png");
        listExt.add("*.JPG");
        listExt.add("*.JPEG");
        listExt.add("*.PNG");
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Files", listExt));
        File file = fc.showOpenDialog(null);
        if (file != null) {
            camerabtn.setText(file.getAbsolutePath());
            clubPic.setImage(new Image(file.getAbsolutePath()));
            String imgName = uniqueFilename();
            String imgSelceted = "";
            String typeImg = "";
            ClubService cb = new ClubService();
            int index = camerabtn.getText().lastIndexOf('.');
            if (index > 0) {
                imgName += "." + camerabtn.getText().substring(index + 1);
            }
            imgSelceted = camerabtn.getText();
            typeImg = URLConnection.guessContentTypeFromName(imgSelceted);
            try {
                cb.updateClubPic(imgName, clubid);
                Files.copy(Paths.get(imgSelceted), Paths.get(Statics.ClubPic + imgName));
            } catch (IOException ex) {
                Logger.getLogger(ClubAddController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void editclubImg(MouseEvent event) {
        List<String> listExt = new ArrayList<>();
        listExt.add("*.jpg");
        listExt.add("*.jpeg");
        listExt.add("*.png");
        listExt.add("*.JPG");
        listExt.add("*.JPEG");
        listExt.add("*.PNG");
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Files", listExt));
        File file = fc.showOpenDialog(null);
        if (file != null) {
            camerabtn.setText(file.getAbsolutePath());
            clubPic.setImage(new Image(file.getAbsolutePath()));
            String imgName = uniqueFilename();
            String imgSelceted = "";
            String typeImg = "";
            ClubService cb = new ClubService();
            int index = camerabtn.getText().lastIndexOf('.');
            if (index > 0) {
                imgName += "." + camerabtn.getText().substring(index + 1);
            }
            imgSelceted = camerabtn.getText();
            typeImg = URLConnection.guessContentTypeFromName(imgSelceted);
            try {
                cb.updateClubPic(imgName, clubid);
                Files.copy(Paths.get(imgSelceted), Paths.get(Statics.ClubPic + imgName));
                initData(clubid);
            } catch (IOException ex) {
                Logger.getLogger(ClubAddController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @FXML
    private void changeClubDesc(ActionEvent event) {
        ClubService cs = new ClubService();

        // Create the custom dialog
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Modification de la description du club " + clubName_l.getText());

        // Set the button types
        ButtonType saveButtonType = new ButtonType("Modifier", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, cancelButtonType);

        // Create the nom and niveau labels and fields
        //grid setting
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        //nom tf init
        TextArea tf = new TextArea();
        tf.setText(clubDesc.getText());

        //add tf and cb to the grid +lables
        grid.add(new Label("Description:"), 0, 0);
        grid.add(tf, 1, 0);

        dialog.getDialogPane().setContent(grid);
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.get() == saveButtonType) {
            cs.updateClubDesc(tf.getText(), clubid);
            clubDesc.setText(tf.getText());

        }
    }

    @FXML
    private void editclubDesc(MouseEvent event) {
        ClubService cs = new ClubService();

        // Create the custom dialog
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Modification de la description du club " + clubName_l.getText());

        // Set the button types
        ButtonType saveButtonType = new ButtonType("Modifier", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, cancelButtonType);

        // Create the nom and niveau labels and fields
        //grid setting
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        //nom tf init
        TextArea tf = new TextArea();
        tf.setText(clubDesc.getText());

        //add tf and cb to the grid +lables
        grid.add(new Label("Description:"), 0, 0);
        grid.add(tf, 1, 0);

        dialog.getDialogPane().setContent(grid);
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.get() == saveButtonType) {
            cs.updateClubDesc(tf.getText(), clubid);
            clubDesc.setText(tf.getText());
        }
    }

    @FXML
    private void ClubList(MouseEvent event) {
          try {
            //instance mtaa el crud
            //redirection
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/Clubs/ClubListStudent.fxml"));
            Parent root = loader.load();
            clubDesc.getScene().setRoot(root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
