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
import edu.edspace.services.UserService;
import edu.edspace.gui.news.AllNewsController;
import edu.edspace.utils.MyConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import java.awt.*;
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
    @FXML
    private TextField searchField;
    @FXML
    private Button btnSearch;
    private List<Emploi> emploisList = new ArrayList<>();
    private List<CategoryEmploi> catList = new ArrayList<>();
    List<Emploi> filteredList = new ArrayList<>();
    Connection connection = null;
    EmploiService emploiService = new EmploiService();

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
    @FXML
    public void search(MouseEvent event){
        EmploiCategoryService cnews = new EmploiCategoryService();
        catList = cnews.AllCats();
        String searchWord = searchField.getText().toLowerCase();
        if(searchWord.isEmpty()){
            filteredList.clear();   
            System.out.println(emploisList);
            tilePaneId.getChildren().clear();
            initPane(emploisList, catList);
        }else{
            filteredList = emploiService.SearchPublications(searchWord);
            if(filteredList == null || filteredList.isEmpty()){
                emploisList = emploiService.AllEmplois();
            }else{
                System.out.println(filteredList);
                emploisList.clear();
                tilePaneId.getChildren().clear();
                //newsList = filteredList;
                initPane(filteredList, catList);
                emploisList = emploiService.AllEmplois();
            }
            
        }
        
        //initData();
    }
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

                        /* notification */ 
                 SystemTray tray = SystemTray.getSystemTray();
                 //If the icon is a file
                 java.awt.Image imagess = Toolkit.getDefaultToolkit().createImage("icon.png");
                 TrayIcon trayIcon = new TrayIcon(imagess, "ADD");
                 //Let the system resize the image if needed
                 trayIcon.setImageAutoSize(true);
                 //Set tooltip text for the tray icon
                 trayIcon.setToolTip("System tray icon demo");
                 try {
                     tray.add(trayIcon);
                 } catch (AWTException e) {
                     System.out.println(e.getMessage());
                 }
                 trayIcon.displayMessage("Supprimer", "L'offre " + nw.getTitle() + "  a ete bien supprimer", TrayIcon.MessageType.INFO);
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
        initData();

    }
//sidebar
@FXML
private void getNewsView(MouseEvent event){
    try {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/news/allNews.fxml"));
        rootPane.getChildren().setAll(pane);
    } catch (IOException ex) {
        Logger.getLogger(AllEmploiController.class.getName()).log(Level.SEVERE, null, ex);
    }
}
@FXML
private void displayClubs(ActionEvent event) {
    try {
        //instance mtaa el crud
        //redirection
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/Clubs/ClubListAdmin.fxml"));
        Parent root = loader.load();
        club_iv.getScene().setRoot(root);
    } catch (IOException ex) {
        ex.printStackTrace();
    }
}

@FXML
private void getUsers(ActionEvent event) {
    
    try {
        //instance mtaa el crud
        //redirection
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/Admin/AllAdmins.fxml"));
        Parent root = loader.load();
        club_iv.getScene().setRoot(root);
    } catch (IOException ex) {
        ex.printStackTrace();
    }
    
}
@FXML
private void logout(MouseEvent event){
    UserService US = new UserService();
    US.logout();
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/User/Login.fxml"));
    try {
    Parent root = loader.load();
    rootPane.getScene().setRoot(root); 
    } catch (IOException ex) {		
    }
}  
@FXML
private void getForum(MouseEvent event) {
    try {
        //instance mtaa el crud
        //redirection
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/ThreadList.fxml"));
        Parent root = loader.load();
        club_iv.getScene().setRoot(root);
    } catch (IOException ex) {
        ex.printStackTrace();
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
    @FXML
    private void getNiveaux(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/Niveau/AllNiveau.fxml"));
            Parent root = loader.load();
            rootPane.getScene().setRoot(root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    @FXML
    private void getClasses(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/Classe/AllClasses.fxml"));
            Parent root = loader.load();
            rootPane.getScene().setRoot(root);
        } catch (IOException ex) {
            ex.printStackTrace();
    }   
    }
    @FXML
    private void getAllDocsView(MouseEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/document/DocsList.fxml"));
            Parent root = loader.load();
            rootPane.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(AllEmploiController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
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
    private void getDashboardView(MouseEvent event){
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/HomeBack.fxml"));
			rootPane.getChildren().setAll(pane);
		} catch (IOException ex) {
			Logger.getLogger(AllEmploiController.class.getName()).log(Level.SEVERE, null, ex);
		}
    }   
    @FXML
    private void getAllMatieresView(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/matiere/MatieresList.fxml"));
            Parent root = loader.load();
            rootPane.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(AllEmploiController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    private void getCatEmploiView(MouseEvent event){
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/emploi/allcategoryEmploi.fxml"));
			rootPane.getChildren().setAll(pane);
		} catch (IOException ex) {
			
		}
    }
    public void initImages() {
        File fileLogo = new File("images/logo1.png");
        Image logoI = new Image(fileLogo.toURI().toString());
        
        File fileHome = new File("images/stats_grey.png");
        Image homeI = new Image(fileHome.toURI().toString());
        
        File fileTab = new File("images/announcement_grey.png");
        Image tabI = new Image(fileTab.toURI().toString());
        
        File fileLevel = new File("images/level_grey.png");
        Image levelI = new Image(fileLevel.toURI().toString());
        
        File fileClass = new File("images/class-management_grey.png");
        Image classI = new Image(fileClass.toURI().toString());
        
        File fileBook = new File("images/book_grey.png");
        Image bookI = new Image(fileBook.toURI().toString());
        
        File fileForum = new File("images/forum2_grey.png");
        Image forumI = new Image(fileForum.toURI().toString());
        
        File fileOffre = new File("images/briefcase_grey.png");
        Image offreI = new Image(fileOffre.toURI().toString());
        
        File fileDocs = new File("images/file_grey.png");
        Image docsI = new Image(fileDocs.toURI().toString());
    
        File fileUsers = new File("images/users_grey.png");
        Image usersI = new Image(fileUsers.toURI().toString());
        
        File fileClub = new File("images/org_grey.png");
        Image clubI = new Image(fileClub.toURI().toString());
    
        File fileOut = new File("images/logout_grey.png");
        Image outI = new Image(fileOut.toURI().toString());
        
        logo_iv.setImage(logoI);
        home_iv.setImage(homeI);
        tabaff_iv.setImage(tabI);
        users_iv.setImage(usersI);
        niveaux_iv.setImage(levelI);
        classe_iv.setImage(classI);
        matieres_iv.setImage(bookI);
        club_iv.setImage(clubI);
        offre_iv.setImage(offreI);
        forum_iv.setImage(forumI);
        centre_iv.setImage(docsI);
        signOut_iv.setImage(outI);
    }
}
