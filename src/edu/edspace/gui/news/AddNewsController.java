package edu.edspace.gui.news;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.File;
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import edu.edspace.entities.CategoryNews;
import edu.edspace.entities.News;
import edu.edspace.services.NewsCategoryService;
import edu.edspace.services.NewsService;
import edu.edspace.utils.MyConnection;
import edu.edspace.utils.Statics;

import java.sql.Connection;


public class AddNewsController implements Initializable{
    @FXML
	private TextField titleField;
	@FXML
	private TextField descriptionField;
	@FXML
	private DatePicker dateField;
	@FXML
	private TextField authorField;
	@FXML
	private ComboBox<CategoryNews> categoryNameField;
    @FXML
    private ImageView imageField;
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
    private Button btnNews;
    @FXML
    private Button btnCatNews;
    @FXML 
    private Button save;
    @FXML 
    private Button cancel;
    @FXML
    private AnchorPane rootPane;
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
    private ImageView logo_iv;
    @FXML
    private Button chooseFileBtn;
    Connection connection = null;
    NewsService newsService = null;
    private List<CategoryNews> categories = new ArrayList();

    @FXML
    private void chooseImage(){
        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        FileChooser.ExtensionFilter extFilterjpg
                = new FileChooser.ExtensionFilter("jpg files (*.jpg)", "*.jpg");
        FileChooser.ExtensionFilter extFilterpng
                = new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
        fileChooser.getExtensionFilters()
                .addAll(extFilterjpg,extFilterpng);
        File file = fileChooser.showOpenDialog(null);
        if(file != null){
                chooseFileBtn.setText(file.getAbsolutePath());
        }
    }
    @FXML
    private void save(MouseEvent event){
        connection = MyConnection.getInstance().getCnx();
       
		String title = titleField.getText();
		String description = descriptionField.getText();
        String author = authorField.getText();
		String datePub = String.valueOf(dateField.getValue());
        int ext =  chooseFileBtn.getText().lastIndexOf(File.separator);
        String image = chooseFileBtn.getText().substring(ext+1);
        System.out.println("image");
        System.out.println(image);
        String file = chooseFileBtn.getText();
        System.out.println("file");
        System.out.println(file);
         try {
           Files.copy(Paths.get(file), Paths.get(Statics.myPubImages + image));
        }catch (IOException ex) {
             Logger.getLogger(AddNewsController.class.getName()).log(Level.SEVERE, null, ex);
         }
         CategoryNews categoryField = categoryNameField.getSelectionModel().getSelectedItem();
         Integer categoryName;
         if(categoryField !=null){
            categoryName = categoryField.getId();
            if (title.isEmpty() || description.isEmpty() || datePub.isEmpty() || author.isEmpty() || categoryName == null || image.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Please fill the fields");
                alert.showAndWait();
            } 
            else 
                {
                    News p = new News(title, author, description,categoryName.toString(),datePub,image);
                   // System.out.println(p);
                    NewsService newsService = new NewsService();
                    newsService.addNews(p);
                    getNewsView(event);
                    
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please select a category");
            alert.showAndWait();
         }
            
		
    }
    @FXML
    public void cancel(MouseEvent event){
        getNewsView(event);
    }
    private ObservableList<CategoryNews> fillComboBox(){
        ObservableList<CategoryNews> allcat = FXCollections.observableArrayList();
        NewsCategoryService ns = new NewsCategoryService();
        categories= ns.AllCats();
        for (int i = 0; i < categories.size(); i++) {
            allcat.addAll(categories.get(i));
        }
        return allcat;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        connection = MyConnection.getInstance().getCnx();
        categoryNameField.setItems(FXCollections.observableArrayList(fillComboBox()));
        initImages();
        
    }
//sidebar
@FXML
private void getNewsView(MouseEvent event) {
    try {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/news/allNews.fxml"));
        rootPane.getChildren().setAll(pane);
    } catch (IOException ex) {
        Logger.getLogger(AddNewsController.class.getName()).log(Level.SEVERE, null, ex);
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
private void getAllMatieresView(MouseEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/matiere/MatieresList.fxml"));
        Parent root = loader.load();
        rootPane.getScene().setRoot(root);
    } catch (IOException ex) {
        Logger.getLogger(AllNewsController.class.getName()).log(Level.SEVERE, null, ex);
    }
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
        // home_iv.setImage(homeI);
        // tabaff_iv.setImage(homeI);
        // users_iv.setImage(homeI);
        // niveaux_iv.setImage(homeI);
        // matieres_iv.setImage(homeI);
        // classe_iv.setImage(homeI);
        // club_iv.setImage(outI);
        // offre_iv.setImage(outI);
        // forum_iv.setImage(outI);
        // centre_iv.setImage(outI);
        // search_iv.setImage(searchI);
        // signOut_iv.setImage(outI);
    }
    
}
