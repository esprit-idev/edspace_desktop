package edu.edspace.gui.emploi;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.edspace.entities.CategoryEmploi;
import edu.edspace.services.EmploiCategoryService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class AllCategoryEmploiController implements Initializable{
    
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
    private AnchorPane rootPane;
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
    private Button btnNews;
    @FXML
    private TableView<CategoryEmploi> tableViewId;
    @FXML
    private TableColumn<CategoryEmploi,String> categoryNameCol;
    @FXML 
    private Button modify;
    @FXML
    private Button add_btn;
    @FXML
    private TextField nomTextField;
    private List<CategoryEmploi> cats = new ArrayList<CategoryEmploi>();

    //fill the category list with categories
    private ObservableList<CategoryEmploi> catsList() {
        ObservableList<CategoryEmploi> oblistC = FXCollections.observableArrayList();
        EmploiCategoryService ns = new EmploiCategoryService();
        cats = ns.AllCats();
        for (int i = 0; i < cats.size(); i++) {
             oblistC.add(cats.get(i));
        }
        return oblistC;
        
    }
    @FXML
    private void addCat(MouseEvent event) {
        String nomCat = nomTextField.getText();
        if (nomCat != null && nomCat.length() != 0) {
            CategoryEmploi c = new CategoryEmploi(nomCat);
            EmploiCategoryService cs = new EmploiCategoryService();
            cs.addCat(c);
            refreshTable();
        } else {
            showAlert();
        }
    }
    //delete cat
    @FXML
    private void deleteCategory(){
        CategoryEmploi category= tableViewId.getSelectionModel().getSelectedItem();
        if(category!=null){
            EmploiCategoryService nc = new EmploiCategoryService();
            nc.deleteCat(category.getId());
            refreshTable();
        }else{
            showAlert();
        }
    }
    //update
    @FXML
    private void updateCategory(MouseEvent event) {
        CategoryEmploi category= tableViewId.getSelectionModel().getSelectedItem();
        if (category != null) {

            Dialog dialog = new Dialog<>();
            dialog.setTitle("Mise à jour Box");
            dialog.setHeaderText("Mise à jour de la matière ");

            ButtonType saveButtonType = new ButtonType("Modifier", ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(saveButtonType);
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));
            //nom tf init
            TextField tf = new TextField();
            tf.setPromptText("Nom");
            tf.setText(category.getCategoryName());
            //add tf and cb to the grid +lables
            grid.add(new Label("Nom de la matière:"), 0, 0);
            grid.add(tf, 1, 0);
            dialog.getDialogPane().setContent(grid);
            Optional<ButtonType> result = dialog.showAndWait();
                
            if (tf.getText() != null && tf.getText().length() != 0) {
                EmploiCategoryService es = new EmploiCategoryService();
                        es.updateCat(tf.getText(),category.getId());;
                        refreshTable();
            } else {
                showAlert();
             }

        } else {
            showAlert();
        }
    }      
    //initialize table
    public void initTable(){
        categoryNameCol.setCellValueFactory(new PropertyValueFactory<CategoryEmploi,String>("categoryName"));
        tableViewId.setItems(catsList());
    }
    //refresh table after every event
    public void refreshTable(){
        cats.clear();
        initTable();
    }
    //alert function that gets called each time there's an error
    private void showAlert(){
        Alert al = new Alert(Alert.AlertType.ERROR);
        al.setContentText("selectionner une categorie svp");
        al.showAndWait();
    }
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        initImages();
        initTable(); 
    }
    //sidebar views
    @FXML
    private void getNewsView(MouseEvent event){
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/news/allNews.fxml"));
            rootPane.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(AllCategoryEmploiController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    private void getCatNewsView(MouseEvent event) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/news/allCategoryNews.fxml"));
            rootPane.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(AllCategoryEmploiController.class.getName()).log(Level.SEVERE, null, ex);
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
    private void getAllDocsView(MouseEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/document/DocsList.fxml"));
            Parent root = loader.load();
            rootPane.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(AllCategoryEmploiController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    private void getEmploiView(MouseEvent event){
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/emploi/allEmploi.fxml"));
			rootPane.getChildren().setAll(pane);
		} catch (IOException ex) {
			Logger.getLogger(AllCategoryEmploiController.class.getName()).log(Level.SEVERE, null, ex);
		}
    }
    @FXML
    private void getDashboardView(MouseEvent event){
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/HomeBack.fxml"));
			rootPane.getChildren().setAll(pane);
		} catch (IOException ex) {
			Logger.getLogger(AllCategoryEmploiController.class.getName()).log(Level.SEVERE, null, ex);
		}
    }
    
    @FXML
    private void getAllMatieresView(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/matiere/MatieresList.fxml"));
            Parent root = loader.load();
            rootPane.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(AllCategoryEmploiController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
 //images init
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
