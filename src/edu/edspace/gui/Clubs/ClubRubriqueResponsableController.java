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
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import javafx.scene.control.Slider;
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
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
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
    @FXML
    private ImageView home_iv;
    @FXML
    private Slider slider;
    @FXML
    private Label timer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initImages();

    }

    public void initData(int clubid) {
        if (String.valueOf(slider.getValue()).contains(".")) {
            timer.setText(String.valueOf(slider.getValue()).split(".0")[0] + " S restantes");

        } else {
            timer.setText(String.valueOf(slider.getValue()) + " S restantes");

        }
        slider.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(
                    ObservableValue<? extends Number> observableValue,
                    Number oldValue,
                    Number newValue) {
                timer.setText(String.valueOf(newValue.intValue()) + " S restantes");

            }
        });

        ClubPubService cb = new ClubPubService();
        // badges
        if (cb.displayHangingClubPubs(clubid).isEmpty()) {
            badgeEnAttente.setVisible(false);
        } else {
            badgeEnAttente.setText(String.valueOf(cb.displayHangingClubPubs(clubid).size()));
        }
        if (cb.displayRefusedClubPubs(clubid).isEmpty()) {
            badgeRefused.setVisible(false);
        } else {
            badgeRefused.setText(String.valueOf(cb.displayRefusedClubPubs(clubid).size()));
        }
        //display hanging pubs
        refusedIcon.setOnMouseClicked((MouseEvent event) -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/Clubs/ClubRefusedPubsInterface.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root, 625, 500);
                Stage stage = new Stage();
                stage.setScene(scene);
                ClubRefusedPubsInterfaceController clubRefusedPubsInterfaceController = loader.getController();
                clubRefusedPubsInterfaceController.initData(clubid);
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
        //set icons color
        Lighting lighting = new Lighting(new Light.Distant(45, 90, Color.rgb(28, 36, 36)));
        ColorAdjust bright = new ColorAdjust(0, 1, 1, 1);
        lighting.setContentInput(bright);
        lighting.setSurfaceScale(0.0);
        enattenteIcon.setEffect(lighting);
        refusedIcon.setEffect(lighting);
        addpicIV.setEffect(lighting);
        addClubdesc.setEffect(lighting);

        pubsList = cb.displayPostedClubPubs(clubid);
        int colu = 0;
        int row = 0;
        if (pubsList.isEmpty()) {
            Label l = new Label("Pas de publications.");
            l.setTextFill(Color.WHITE);

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
            String fileName = null;
            String fileSelceted = "";
            String typefile = "";
            int indexs = pubFileSelected.getText().lastIndexOf('.');
            if (indexs > 0) {
                fileName = uniqueFilename() + "." + pubFileSelected.getText().substring(indexs + 1);
                fileSelceted = pubFileSelected.getText();
                typefile = URLConnection.guessContentTypeFromName(fileSelceted);
                try {
                    Files.copy(Paths.get(fileSelceted), Paths.get(Statics.ClubPubsFile + fileName));
                } catch (IOException ex) {
                    Logger.getLogger(ClubAddController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            //add img
            String imgName = null;
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

                imgName = uniqueFilename() + "." + pubimgSelected.getText().substring(index + 1);
                imgSelceted = pubimgSelected.getText();
                typeImg = URLConnection.guessContentTypeFromName(imgSelceted);
                try {
                    Files.copy(Paths.get(imgSelceted), Paths.get(Statics.ClubPubsPic + imgName));
                } catch (IOException ex) {
                    Logger.getLogger(ClubAddController.class.getName()).log(Level.SEVERE, null, ex);
                }
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
    private void displayPubRefused(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ClubRefusedPubsInterface.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            ClubRefusedPubsInterfaceController clubRefusedPubsInterfaceController = loader.getController();
            clubRefusedPubsInterfaceController.initData(clubid);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ClubRubriqueAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    private void initImages() {
        File fileRefuse = new File("src/images/refused.png");
        Image refusedI = new Image(fileRefuse.toURI().toString());

        File fileAtt = new File("src/images/enattente.png");
        Image attI = new Image(fileAtt.toURI().toString());

        File fileBack = new File("images/back_grey.png");
        Image backI = new Image(fileBack.toURI().toString());

        File fileEdit = new File("images/edit.png");
        Image editI = new Image(fileEdit.toURI().toString());

        File fileImage = new File("images/add-photo.png");
        Image imageI = new Image(fileImage.toURI().toString());

        File fileHome = new File("images/home_grey.png");
        Image h = new Image(fileHome.toURI().toString());

        home_iv.setImage(h);
        refusedIcon.setImage(refusedI);
        enattenteIcon.setImage(attI);
        ClubListIV.setImage(backI);
        addClubdesc.setImage(editI);
        addpicIV.setImage(imageI);

    }

    @FXML
    private void getHome(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/FrontHome.fxml"));
            Parent root = loader.load();
            clubName_l.getScene().setRoot(root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private String getmicTExt(String duree) {
        String s = "";
        try {
            String command = "py C:\\Users\\anash\\Desktop\\speechScript.py " + duree;
            Process p = Runtime.getRuntime().exec(command);
            try {
                // Start a new java process 

                // Read and print the standard output stream of the process 
                try ( BufferedReader input
                        = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                    String line;
                    while ((line = input.readLine()) != null) {
                        s = line;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
        }
        return s;
    }

    @FXML
    private void rec(ActionEvent event) {
        new Thread(new Runnable() {
            public void run() {
                if (pubdesc_et.getText().length() != 0) {
                    pubdesc_et.setText(pubdesc_et.getText() + " " + getmicTExt(timer.getText()));

                } else {
                    pubdesc_et.setText(getmicTExt(timer.getText()));
                }
            }
        }).start();

        Timer timers = new Timer();

        timers.scheduleAtFixedRate(new TimerTask() {
            int i = Integer.parseInt(timer.getText().split(" S")[0]);

            public void run() {
                new Thread(new Runnable() {
                    public void run() {
                        Platform.runLater(() -> {
                            timer.setText(i + " S restantes");
                            i--;

                            if (i < 0) {
                                timers.cancel();
                                timer.setText(String.valueOf(Math.round(slider.getValue())) + " S restantes");
                            }
                        });
                    }
                }).start();

            }

        }, 0, 1000);

    }
}
