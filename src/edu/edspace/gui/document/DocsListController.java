/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.edspace.gui.document;

import edu.edspace.entities.Document;
import edu.edspace.services.DocumentService;
import edu.edspace.utils.MyConnection;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author MeriamBI
 */
public class DocsListController implements Initializable {

    @FXML
    private Button btnOverview;
    @FXML
    private Button btnOrders;
    @FXML
    private Button btnCustomers;
    @FXML
    private Button btnMenus;
    @FXML
    private Button btnMatiere;
    @FXML
    private Button btnSettings;
    @FXML
    private Button btnEmploi;
    @FXML
    private Button btnSignout1;
    @FXML
    private Button btnCentrePartage;
    @FXML
    private Button btnSignout3;
    @FXML
    private ImageView logo_iv;
    @FXML
    private ImageView home_iv;
    @FXML
    private ImageView tabaff_iv;
    @FXML
    private ImageView users_iv;
    @FXML
    private ImageView niveaux_iv;
    @FXML
    private ImageView classe_iv;
    @FXML
    private ImageView matieres_iv;
    @FXML
    private ImageView club_iv;
    @FXML
    private ImageView offre_iv;
    @FXML
    private ImageView forum_iv;
    @FXML
    private ImageView centre_iv;
    @FXML
    private ImageView signOut_iv;
    @FXML
    private ImageView search_iv;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Button btnNews;
    @FXML
    private ScrollPane scroll;
    @FXML
    private GridPane grid;
    
    private List<Document> docs=new ArrayList();
    @FXML
    private ComboBox<String> niveau_cb;
    @FXML
    private ComboBox<String> matiere_cb;
    @FXML
    private ImageView reported_iv;
    @FXML
    private Label reinitialiser_label;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        MyConnection.getInstance().getCnx();
        initImages();
        int column=0;
        int row=0;
        DocumentService ds = new DocumentService();
        docs=ds.listDocs();
        for (int i=0;i<docs.size();i++){
            try {
                FXMLLoader fXMLLoader=new FXMLLoader();
                fXMLLoader.setLocation(getClass().getResource("/edu/edspace/gui/document/DocR.fxml"));
                AnchorPane anchorPane=fXMLLoader.load();
                
                DocRController docRController= fXMLLoader.getController();
                docRController.setData(docs.get(i));
                
                if(column==3){
                    column=0;
                    row++;
                }
                grid.add(anchorPane, column++, row);
                
                GridPane.setMargin(anchorPane, new Insets(10));
            } catch (IOException ex) {
                Logger.getLogger(DocsListController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }   
    
    @FXML
    private void getNewsView(MouseEvent event) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/allNews.fxml"));
            rootPane.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(DocsListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void getCatNewsView(MouseEvent event) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/allCategoryNews.fxml"));
            rootPane.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(DocsListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void getAllDocsView(MouseEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/document/DocsList.fxml"));
            Parent root = loader.load();
            rootPane.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(DocsListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void getAllMatieresView(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/matiere/MatieresList.fxml"));
            Parent root = loader.load();
            rootPane.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(DocsListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    @FXML
    private void getReportedDocs(MouseEvent event) {
    }

    @FXML
    private void reinitialiserFiltre(MouseEvent event) {
    }

    @FXML
    private void handleClicks(ActionEvent event) {

    }

    public void initImages() {
        File fileLogo = new File("images/logo1.png");
        Image logoI = new Image(fileLogo.toURI().toString());

        File fileHome = new File("images/icons8_Home_32px.png");
        Image homeI = new Image(fileHome.toURI().toString());

        File fileUsers = new File("images/icons8_Person_32px.png");
        Image usersI = new Image(fileUsers.toURI().toString());

        File fileOut = new File("images/icons8_Sign_Out_32px.png");
        Image outI = new Image(fileOut.toURI().toString());

        File fileSearch = new File("images/icons8_Search_52px.png");
        Image searchI = new Image(fileSearch.toURI().toString());

        logo_iv.setImage(logoI);
        home_iv.setImage(homeI);
        tabaff_iv.setImage(homeI);
        users_iv.setImage(homeI);
        niveaux_iv.setImage(homeI);
        matieres_iv.setImage(homeI);
        classe_iv.setImage(homeI);
        club_iv.setImage(outI);
        offre_iv.setImage(outI);
        forum_iv.setImage(outI);
        centre_iv.setImage(outI);
        signOut_iv.setImage(outI);
        reported_iv.setImage(outI);
    }
    
}
