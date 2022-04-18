package edu.edspace.gui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.edspace.entities.Emploi;
import edu.edspace.services.EmploiService;
import edu.edspace.utils.MyConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class AllEmploiController implements Initializable{

    //fxml attributes
    @FXML
    private Button btnOverview;
    @FXML
    private Button btnOrders;
    @FXML
    private Button btnCustomers;
    @FXML
    private Button btnMenus;
    @FXML
    private Button btnPackages;
    @FXML
    private Button btnSettings;
    @FXML
    private Button btnEmploi;
    @FXML
    private Button btnSignout1;
    @FXML
    private Button btnSignout2;
    @FXML
    private Button btnSignout3;
    @FXML
    private Pane pnlCustomer;
    @FXML
    private Pane pnlOrders;
    @FXML
    private Pane pnlMenus;
    @FXML
    private Pane pnlOverview;
    @FXML
    private VBox pnItems;
    @FXML
    private ImageView logo_iv,home_iv,tabaff_iv,users_iv,niveaux_iv,matieres_iv,classe_iv,club_iv,offre_iv,forum_iv,centre_iv,search_iv,signOut_iv;
    @FXML
    private Button addBtn;
    @FXML
    private Button catBtn;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TilePane tilePaneId;

    Connection connection = null;
    private List<Emploi> emploiList = null;
    //fxml methods
    @FXML
    private void addEmploi(MouseEvent event){
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/addNews.fxml"));
			rootPane.getChildren().setAll(pane);
		} catch (IOException ex) {
			Logger.getLogger(AllNewsController.class.getName()).log(Level.SEVERE, null, ex);
		}
    }
    @FXML
    public void getNewsView(MouseEvent event){
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/allNews.fxml"));
            rootPane.getChildren().setAll(pane);
		} catch (IOException ex) {
			Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
		}
    }
    @FXML
    private void getCatEmploiView(MouseEvent event){
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/allCategoryNews.fxml"));
			rootPane.getChildren().setAll(pane);
		} catch (IOException ex) {
			Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
		}
    }
    @FXML
    private void getDashboardView(MouseEvent event){
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/HomeBack.fxml"));
			rootPane.getChildren().setAll(pane);
		} catch (IOException ex) {
			Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
		}
    }
    //init method
    @FXML
    private void handleClicks(ActionEvent event) {
    }
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        initImages();
        connection = MyConnection.getInstance().getCnx();
        EmploiService empService = new EmploiService();
        emploiList = empService.AllEmplois();
        if(emploiList == null){
            Label label = new Label();
            label.setText("La liste des offres est vide");
            HBox hBox = new HBox();
            hBox.getChildren().addAll(label);
            tilePaneId.getChildren().addAll(hBox);
        }else{
            Iterator<Emploi> it = empService.AllEmplois().iterator();
            while(it.hasNext()){
                Emploi em = new Emploi();
                Label titleLabel = new Label();
                Label ownerLabel = new Label();
                Button modifButton = new Button();
                modifButton.setText("Modify");
                Button deleteButton = new Button("Delete");
                deleteButton.setText("Delete");
                modifButton.setStyle("-fx-border-color: #ff0000;");
                deleteButton.setStyle("-fx-border-color: #ffff00;");
                deleteButton.setOnMouseClicked((MouseEvent event)->{
                    empService.deleteOffer(em.getId());
                });
                modifButton.setOnMouseClicked((MouseEvent event)->{
                    try {
                        AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/updateNews.fxml"));
                        rootPane.getChildren().setAll(pane);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                HBox hBoxBtns = new HBox();
                hBoxBtns.getChildren().addAll(modifButton,deleteButton);
                titleLabel.setText(em.getTitle());
                ownerLabel.setText(em.getCategoryName());
                VBox v = new VBox(10);
                v.setPadding(new Insets(10, 20, 10, 20));
                v.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(0),new BorderWidths(1))));
                v.getChildren().addAll(titleLabel,ownerLabel,hBoxBtns);
                tilePaneId.getChildren().addAll(v);           
        }
        }
    }
    public void initImages(){
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
        users_iv.setImage(usersI);
        niveaux_iv.setImage(homeI);
        matieres_iv.setImage(homeI);
        classe_iv.setImage(homeI);
        club_iv.setImage(outI);
        offre_iv.setImage(outI);
        forum_iv.setImage(outI);
        centre_iv.setImage(outI);
        search_iv.setImage(searchI);
        signOut_iv.setImage(outI);
    }
}
