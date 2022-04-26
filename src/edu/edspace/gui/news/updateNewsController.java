package edu.edspace.gui.news;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
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
     //control saisie variables
     @FXML
     private Label titleError;
     @FXML
     private Label descriptionError;
     @FXML
     private Label authorError;
     @FXML
     private Label fileError;
     @FXML
     private Label imageError;
     @FXML
     private Label categoryError;
     
    CardController cd = new CardController();
    private List<CategoryNews> categories = new ArrayList<CategoryNews>();
    CardController cdd = new CardController();
    AllNewsController cccc = new AllNewsController();
    public int id;
    String image;

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
        titleField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> arg0, String newValue, String oldValue) {
                if(oldValue.isEmpty()){
                    titleError.setVisible(true);
                }else{
                    titleError.setVisible(false);
                } 
            }});
        descriptionField.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> arg0, String newValue, String oldValue) {
                    if(oldValue.isEmpty()){
                        descriptionError.setVisible(true);
                    }else{
                        descriptionError.setVisible(false);
                    } 
                }});
        authorField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> arg0, String newValue, String oldValue) {
                if(oldValue.isEmpty()){
                            authorError.setVisible(true);
                }else{
                            authorError.setVisible(false);
                } 
            }});
        chooseFileBtn.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> arg0, String newValue, String oldValue) {
                if(oldValue.isEmpty()){
                        fileError.setVisible(true);
                }else{
                        fileError.setVisible(false);
                } 
            }});
        MyConnection.getInstance().getCnx();
		String title = titleField.getText();
		String description = descriptionField.getText();
        String author = authorField.getText();
        String datePub = new SimpleDateFormat("dd/MM/yy").format(new Date());
        int ext =  chooseFileBtn.getText().lastIndexOf(File.separator);
        image = chooseFileBtn.getText().substring(ext+1);
        String file = chooseFileBtn.getText();

        CategoryNews catField = categoryNameField.getSelectionModel().getSelectedItem();
        Integer categoryName;
         if(catField != null){
             categoryName = catField.getId();
             if (title.isEmpty() || description.isEmpty() || datePub.isEmpty() || author.isEmpty() || categoryName == null || image.isEmpty()) {
                showError();
            } 
            else 
                {
                    try {
                        Files.copy(Paths.get(file), Paths.get(Statics.myPubImages + image),StandardCopyOption.REPLACE_EXISTING);
                     }catch (IOException ex) {
                          Logger.getLogger(AddNewsController.class.getName()).log(Level.SEVERE, null, ex);
                      }
                    News p = new News(title, author, description,categoryName.toString(),datePub,image);
                    NewsService newsService = new NewsService();
                    newsService.updateNews(p,id);
                    System.out.println(id);
                    getNewsView(event);  
                }    
            }else{
                showError();
            }     
		
    }
    private void showError(){
        if(titleField.getText().isEmpty()){
            titleError.setVisible(true);

        }
        if(descriptionField.getText().isEmpty()){
            descriptionError.setVisible(true);

        }
        if(authorField.getText().isEmpty()){
            authorError.setVisible(true);
        }
        if(chooseFileBtn.getText().isEmpty()){
            fileError.setVisible(true);
        }
            
        if(categoryNameField.getSelectionModel().getSelectedItem()== null){
            categoryError.setVisible(true);
        }else{
            categoryError.setVisible(false);
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
        MyConnection.getInstance().getCnx();
        categoryNameField.setItems(FXCollections.observableArrayList(fillComboBox()));
        initImages();
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
    public void setIm(String imag){
        Path f = Paths.get(Statics.myPubImages + imag);
        chooseFileBtn.setText(f.toString());
    }
//sidebar
@FXML
private void getNewsView(MouseEvent event){
    try {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/news/allNews.fxml"));
        rootPane.getChildren().setAll(pane);
    } catch (IOException ex) {
        Logger.getLogger(updateNewsController.class.getName()).log(Level.SEVERE, null, ex);
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/AllAdmins.fxml"));
        Parent root = loader.load();
        club_iv.getScene().setRoot(root);
    } catch (IOException ex) {
        ex.printStackTrace();
    }
    
}
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
