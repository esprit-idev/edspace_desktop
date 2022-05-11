package edu.edspace.gui.news;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.edspace.entities.CategoryNews;
import edu.edspace.entities.News;
import edu.edspace.services.NewsCategoryService;
import edu.edspace.services.NewsService;
import edu.edspace.utils.MyConnection;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;


public class AllNewsController implements Initializable{
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
    private ImageView matieres_iv;
    @FXML
    private ImageView classe_iv;
    @FXML
    private ImageView club_iv;
    @FXML
    private ImageView offre_iv;
    @FXML
    private ImageView forum_iv; 
    @FXML
    private ImageView centre_iv; 
    @FXML
    private ImageView search_iv; 
    @FXML
    private ImageView signOut_iv;
    @FXML
    private ImageView emptyIcon; 
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Button btnAddNews;
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
    private TilePane tilePaneId;
    @FXML
    private Pane paneId;
    @FXML
    private Button btnNews;
    @FXML
    private Button btnCatNews;
    @FXML
    private TextField searchField;
    @FXML
    private Button btnSearch;
    Connection connection = null;
    private NewsService newsService = new NewsService();
    private List<News> newsList = newsService.AllNews();   
    private List<CategoryNews> catList = new ArrayList<>();
    List<News> filteredList = new ArrayList<>();

    @FXML
    public void search(MouseEvent event){
        NewsCategoryService cnews = new NewsCategoryService();
        catList = cnews.AllCatsNames();
        String searchWord = searchField.getText().toLowerCase();
        if(searchWord.isEmpty()){
            filteredList.clear();   
            System.out.println(newsList);
            tilePaneId.getChildren().clear();
            initPane(newsList, catList);
        }else{
            filteredList = newsService.SearchPbulications(searchWord);
            if(filteredList == null && filteredList.isEmpty()){
                File file = new File("images/empty-folder.png");
                Image notFound = new Image(file.toURI().toString());
                emptyIcon.setImage(notFound);
                emptyIcon.setVisible(true);
                System.out.println(notFound);
                newsList = newsService.AllNews();
            }else{
                System.out.println(filteredList);
                newsList.clear();
                tilePaneId.getChildren().clear();
                //newsList = filteredList;
                initPane(filteredList, catList);
                newsList = newsService.AllNews();
            }
            
        }
        
        //initData();
    }
    public void initData(){
        NewsCategoryService cnews = new NewsCategoryService();
        catList = cnews.AllCatsNames();
       // check if list is empty
        if(newsList == null && newsList.isEmpty()){
            Label label = new Label();
            label.setText("La liste des publications est vide");
            HBox hBox = new HBox();
            hBox.getChildren().addAll(label);
            tilePaneId.getChildren().addAll(hBox);
        }else{
           initPane(newsList,catList);
        }
    }
    private void initPane(List<News> news,List<CategoryNews>cc) {
        NewsService newsService = new NewsService();
        Iterator<News> it = news.iterator();
        while (it.hasNext()) {
            News nw = it.next();
            try{
            FXMLLoader fXMLLoader = new FXMLLoader();
                fXMLLoader.setLocation(getClass().getResource("/edu/edspace/gui/news/card.fxml"));
                AnchorPane pane = fXMLLoader.load();
                CardController cd = fXMLLoader.getController();
                cd.setData(nw);
                Button delButton;
                Button modifButton;
                modifButton = cd.modifBtn;
                delButton = cd.getDeleteButton();
                delButton.setOnMouseClicked((MouseEvent event)->{
                    ButtonType saveButtonType = new ButtonType("Supprimer", ButtonData.OK_DONE);
                    Alert alert = new Alert(AlertType.WARNING,"",saveButtonType);
                    alert.setContentText("Vous voulez vraiment supprime cette article?");
                    newsService.deleteNews(nw.getId());
                    getNewsView(event);
                });
                modifButton.setOnMouseClicked((MouseEvent event)->{
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/news/updateNews.fxml"));
                        AnchorPane panel = loader.load();
                        updateNewsController up = loader.getController();
                        up.setI(nw.getId());
                        up.settitle(nw.getTitle());
                        up.setContent(nw.getContent());
                        up.setowner(nw.getOwner());
                        up.setIm(nw.getImage());
                        rootPane.getChildren().setAll(panel);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                tilePaneId.getChildren().addAll(pane);
                
            } catch (IOException ex) {
                Logger.getLogger(AllNewsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void addNews(MouseEvent event){
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/news/addNews.fxml"));
			rootPane.getChildren().setAll(pane);
		} catch (IOException ex) {
			Logger.getLogger(AllNewsController.class.getName()).log(Level.SEVERE, null, ex);
		}

    }

    public ObservableList<CategoryNews> fillComboBox(){
        ObservableList<CategoryNews> allcat = FXCollections.observableArrayList();
        NewsCategoryService ns = new NewsCategoryService();
        catList= ns.AllCats();
        for (int i = 0; i < catList.size(); i++) {
            allcat.addAll(catList.get(i));
        }
        return allcat;
    }
    //sidebar
    @FXML
    private void getNewsView(MouseEvent event){
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/news/allNews.fxml"));
			rootPane.getChildren().setAll(pane);
		} catch (IOException ex) {
			Logger.getLogger(AllNewsController.class.getName()).log(Level.SEVERE, null, ex);
		}
    }    
    @FXML
    private void getEmploiView(MouseEvent event){
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/emploi/allEmploi.fxml"));
			rootPane.getChildren().setAll(pane);
		} catch (IOException ex) {
			Logger.getLogger(AllNewsController.class.getName()).log(Level.SEVERE, null, ex);
		}
    }
    @FXML
    private void getCatNewsView(MouseEvent event){
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/news/allCategoryNews.fxml"));
			rootPane.getChildren().setAll(pane);
		} catch (IOException ex) {
			Logger.getLogger(AllNewsController.class.getName()).log(Level.SEVERE, null, ex);
		}
    }
    @FXML
    private void getAllDocsView(MouseEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/document/DocsList.fxml"));
            Parent root = loader.load();
            rootPane.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(allCategoryNewsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    private void getDashboardView(MouseEvent event){
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/HomeBack.fxml"));
			rootPane.getChildren().setAll(pane);
		} catch (IOException ex) {
			Logger.getLogger(allCategoryNewsController.class.getName()).log(Level.SEVERE, null, ex);
		}
    }
    @FXML
    private void getNiveauView(MouseEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/Niveau/AllNiveau.fxml")); 
            Parent root = loader.load(); 
            rootPane.getScene().setRoot(root); 
		} catch (IOException ex) {
			ex.printStackTrace(); 
		}
    }
    @FXML
    private void getClassesView(MouseEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/Classe/AllClasses.fxml")); 
            Parent root = loader.load(); 
            rootPane.getScene().setRoot(root); 
		} catch (IOException ex) {
			ex.printStackTrace(); 
		}
    }           
    @FXML
    private void getAllMatieresView(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/matiere/MatieresList.fxml"));
            Parent root = loader.load();
            rootPane.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(AllNewsController.class.getName()).log(Level.SEVERE, null, ex);
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
    private void getForum(MouseEvent event) {
        try {
            //instance mtaa el crud
            //redirection
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/ThreadList.fxml"));
            Parent root = loader.load();
            forum_iv.getScene().setRoot(root);
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
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/User/Login.fxml"));
			rootPane.getChildren().setAll(pane);
		} catch (IOException ex) {
			
		}
    }        
    @FXML
    private void handleClicks(ActionEvent event) {
    }
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        connection = MyConnection.getInstance().getCnx();
        initImages();
        initData();
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
