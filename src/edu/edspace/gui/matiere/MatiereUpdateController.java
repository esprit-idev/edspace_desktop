/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.edspace.gui.matiere;

import edu.edspace.entities.Matiere;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author MeriamBI
 */
public class MatiereUpdateController implements Initializable {

    @FXML
    private DialogPane dialog;
    @FXML
    private TextField nom_tf;
    @FXML
    private Button save_btn;
    
    private Matiere matiere;
    @FXML
    private ComboBox<String> niveau_cb;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    protected void setData(Matiere matiere){
        this.nom_tf.setText(matiere.getId());
        this.niveau_cb.setValue(matiere.getNiveau());
    }
    
    @FXML
    private void saveUpdate(MouseEvent event) {
    }

    @FXML
    private void handleClicks(ActionEvent event) {
    }
    
}
