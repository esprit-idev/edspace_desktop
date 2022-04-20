package edu.edspace.gui.emploi;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.sql.Connection;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import edu.edspace.entities.CategoryEmploi;
import edu.edspace.entities.Emploi;
import edu.edspace.gui.news.CardController;
import edu.edspace.services.EmploiCategoryService;
import edu.edspace.services.EmploiService;
import edu.edspace.entities.CategoryEmploi;
import edu.edspace.entities.Emploi;
import edu.edspace.gui.HomeController;
import edu.edspace.gui.news.AllNewsController;
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
import javafx.scene.control.Label;
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

    private List<Emploi> emploisList = new ArrayList<>();
    private List<CategoryEmploi> catList = new ArrayList<>();
    Connection connection = null;

    //fxml methods
    @FXML
    private void addEmploi(MouseEvent event){
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/emploi/addEmploi.fxml"));
			rootPane.getChildren().setAll(pane);
		} catch (IOException ex) {
			Logger.getLogger(AllNewsController.class.getName()).log(Level.SEVERE, null, ex);
		}
    }
    //init method
    //setting data 
    public void initData(){
        EmploiService emploiService = new EmploiService();
        emploisList = emploiService.AllEmplois();
        EmploiCategoryService cemps = new EmploiCategoryService();
        catList = cemps.AllCats();
       // check if list is empty
        if(emploisList == null && emploisList.isEmpty()){
            Label label = new Label();
            label.setText("La liste des publications est vide");
            HBox hBox = new HBox();
            hBox.getChildren().addAll(label);
            tilePaneId.getChildren().addAll(hBox);
        }else{
           initPane(emploisList,catList);
        }
    }
    private void initPane(List<Emploi> emps,List<CategoryEmploi>cc) {
        EmploiService emploiService = new EmploiService();
        Iterator<Emploi> it = emps.iterator();
        while (it.hasNext()) {
            Emploi nw = it.next();
            try{
            FXMLLoader fXMLLoader = new FXMLLoader();
                fXMLLoader.setLocation(getClass().getResource("/edu/edspace/gui/news/card.fxml"));
                AnchorPane pane = fXMLLoader.load();
                CardController cd = fXMLLoader.getController();
                cd.setEmpData(nw);
                Button delButton;
                Button modifButton;
                modifButton = cd.modifBtn;
                delButton = cd.getDeleteButton();
                delButton.setOnMouseClicked((MouseEvent event)->{
                    emploiService.deleteOffer(nw.getId());
                    getEmploiView(event);
                });
                modifButton.setOnMouseClicked((MouseEvent event)->{
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/emploi/updateEmploi.fxml"));
                        AnchorPane panel = loader.load();
                        UpdateEmploiController up = loader.getController();
                        up.setI(nw.getId());
                        up.settitle(nw.getTitle());
                        up.setContent(nw.getContent());
                        up.setDate(nw.getDate());
                        up.setIm(nw.getImage());
                        rootPane.getChildren().setAll(panel);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                tilePaneId.getChildren().addAll(pane);
                
            } catch (IOException ex) {
                Logger.getLogger(AddEmploiController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        initImages();
        connection = MyConnection.getInstance().getCnx();
        EmploiService empService = new EmploiService();
        initData();

    }
    //sidebar
    @FXML
    private void getEmploiView(MouseEvent event){
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/emploi/allEmploi.fxml"));
			rootPane.getChildren().setAll(pane);
		} catch (IOException ex) {
			Logger.getLogger(AllEmploiController.class.getName()).log(Level.SEVERE, null, ex);
		}
    }
    @FXML
    public void getNewsView(MouseEvent event){
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/news/allNews.fxml"));
            rootPane.getChildren().setAll(pane);
		} catch (IOException ex) {
			Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
		}
    }
    @FXML
    private void getCatEmploiView(MouseEvent event){
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/emploi/allcategoryEmploi.fxml"));
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
    @FXML
    private void getCatNewsView(MouseEvent event) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/news/allCategoryNews.fxml"));
            rootPane.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(AllEmploiController.class.getName()).log(Level.SEVERE, null, ex);
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
