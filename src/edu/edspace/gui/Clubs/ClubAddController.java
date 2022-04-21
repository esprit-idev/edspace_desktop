/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.edspace.gui.Clubs;

import java.util.logging.Level;
import java.util.logging.Logger;
import edu.edspace.entities.ClubCategory;
import edu.edspace.entities.User;
import edu.edspace.services.ClubCategService;
import edu.edspace.services.StudentService;
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
import java.util.Random;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author anash
 */
public class ClubAddController implements Initializable {

    @FXML
    private Button btn_addClub;
    @FXML
    private TextField c_name_tf;
    @FXML
    private TextArea c_desc_tf;
    @FXML
    private ComboBox<String> c_cat;
    @FXML
    private ComboBox<String> c_respo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ClubCategService cb = new ClubCategService();
        ObservableList<String> categories = FXCollections.observableArrayList();
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

        StudentService cs = new StudentService();
        ObservableList<String> students = FXCollections.observableArrayList();
        students.add("-- Club Responsable --");
        c_respo.setItems(students);
        c_respo.setValue("-- Club Responsable --");

        List<User> studentsList = new ArrayList<>();
        studentsList = cs.listStudent();
        for (User user : studentsList) {
            students.add(user.getEmail());
        }
        c_respo.setItems(students);
    }

    @FXML
    private void addClub(ActionEvent event) {

        String filename = uniqueFilename();
        String files = "";
        String type = "";

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
            c_name_tf.setText(file.getAbsolutePath());
        }
        int index = c_name_tf.getText().lastIndexOf('.');
        if (index > 0) {
            filename += "." + c_name_tf.getText().substring(index + 1);
        }
        files = c_name_tf.getText();
        type = URLConnection.guessContentTypeFromName(files);
        try {
            Files.copy(Paths.get(files), Paths.get(Statics.ClubPubsPic + filename));
        } catch (IOException ex) {
            Logger.getLogger(ClubAddController.class.getName()).log(Level.SEVERE, null, ex);
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
}
