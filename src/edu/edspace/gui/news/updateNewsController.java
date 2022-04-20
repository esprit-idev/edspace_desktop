package edu.edspace.gui.news;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.edspace.entities.CategoryNews;
import edu.edspace.entities.News;
import edu.edspace.services.NewsCategoryService;
import edu.edspace.services.NewsService;
import edu.edspace.utils.MyConnection;
import edu.edspace.utils.Statics;
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

public class updateNewsController implements Initializable {
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
    private Button update;
    @FXML
    private Button cancel;
    @FXML
    private Button chooseFileBtn;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Button btnews;

    CardController cd = new CardController();
    private List<CategoryNews> categories = new ArrayList();
    CardController cdd = new CardController();
    AllNewsController cccc = new AllNewsController();
    public int id;
    String image;

    public void display(String t,String content){
        titleField.setText(t);
        descriptionField.setText(content);
    }
    @FXML
    private void chooseImage(){
        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        FileChooser.ExtensionFilter extFilterjpg
                = new FileChooser.ExtensionFilter("jpg files (*.jpg)", "*.png");
        FileChooser.ExtensionFilter extFilterpng
                = new FileChooser.ExtensionFilter("png files (*.png)", "*.jpg");
        fileChooser.getExtensionFilters()
                .addAll(extFilterjpg,extFilterpng);
        File file = fileChooser.showOpenDialog(null);
        if(file != null){
                chooseFileBtn.setText(file.getAbsolutePath());
        }
    }
    @FXML
    public void save(MouseEvent event){
        MyConnection.getInstance().getCnx();
		String title = titleField.getText();
		String description = descriptionField.getText();
        String author = authorField.getText();
		String datePub = String.valueOf(dateField.getValue());
        int ext =  chooseFileBtn.getText().lastIndexOf(File.separator);
        image = chooseFileBtn.getText().substring(ext+1);
        String file = chooseFileBtn.getText();
        try {
           Files.copy(Paths.get(file), Paths.get(Statics.myPubImages + image));
        }catch (IOException ex) {
             Logger.getLogger(AddNewsController.class.getName()).log(Level.SEVERE, null, ex);
         }
        CategoryNews catField = categoryNameField.getSelectionModel().getSelectedItem();
        Integer categoryName;
         if(catField != null){
             categoryName = catField.getId();
             if (title.isEmpty() || description.isEmpty() || datePub.isEmpty() || author.isEmpty() || categoryName == null || image.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Please fill the fields");
                alert.showAndWait();
            } 
            else 
                {
                    News p = new News(title, author, description,categoryName.toString(),datePub,image);
                    NewsService newsService = new NewsService();
                    newsService.updateNews(p,id);
                    System.out.println(id);
                    getNewsView(event);  
                }    
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Please fill the fields");
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
    @FXML
    private void getNewsView(MouseEvent event){
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/news/allNews.fxml"));
			rootPane.getChildren().setAll(pane);
		} catch (IOException ex) {
			Logger.getLogger(updateNewsController.class.getName()).log(Level.SEVERE, null, ex);
		}
    }
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        MyConnection.getInstance().getCnx();
        News nw = new News();
        categoryNameField.setItems(FXCollections.observableArrayList(fillComboBox()));
        display(nw.getTitle(), nw.getContent());
        initImages();
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
    }
    public void setI(int i){
        id= i;
    }
    public void settitle(String title){
        titleField.setText(title);
    }
    public void setContent(String title){
        descriptionField.setText(title);
    }
    public void setowner(String title){
        authorField.setText(title);
    }
    public void setDate(String date){
        dateField.setValue(LocalDate.parse(date));
    }
    public void setIm(String imag){
        Path f = Paths.get(Statics.myPubImages + imag);
        chooseFileBtn.setText(f.toString());
    }
//sidebar
@FXML
    private void getCatNewsView(MouseEvent event) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/allCategoryNews.fxml"));
            rootPane.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(updateNewsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    private void getAllDocsView(MouseEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/document/DocsList.fxml"));
            Parent root = loader.load();
            rootPane.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(updateNewsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    private void getEmploiView(MouseEvent event){
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/allEmploi.fxml"));
			rootPane.getChildren().setAll(pane);
		} catch (IOException ex) {
			Logger.getLogger(updateNewsController.class.getName()).log(Level.SEVERE, null, ex);
		}
    }
    @FXML
    private void getDashboardView(MouseEvent event){
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/HomeBack.fxml"));
			rootPane.getChildren().setAll(pane);
		} catch (IOException ex) {
			Logger.getLogger(updateNewsController.class.getName()).log(Level.SEVERE, null, ex);
		}
    }
    
    @FXML
    private void getAllMatieresView(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/matiere/MatieresList.fxml"));
            Parent root = loader.load();
            rootPane.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(updateNewsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
