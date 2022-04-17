/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.edspace.gui.document;

import edu.edspace.entities.Document;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
    
    private Document doc;
    @FXML
    private ImageView opt_iv;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        initImages();
    }    
    
    public void setData(Document doc){
        this.doc=doc;
        date_label.setText(doc.getDate_insert());
        matniv_label.setText(doc.getNiveau()+" | "+doc.getMatiere());
        name_label.setText(doc.getNom());
        owner_label.setText(doc.getProp());
    }
    
    public void initImages() {

        File fileBox = new File("images/icons8_Xbox_Menu_32px.png");
        Image boxI = new Image(fileBox.toURI().toString());

        opt_iv.setImage(boxI);
    }
    
}