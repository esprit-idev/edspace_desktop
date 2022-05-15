package edu.edspace.gui.emploi;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.edspace.entities.CategoryEmploi;
import edu.edspace.entities.Emploi;
import edu.edspace.gui.news.CardController;
import edu.edspace.services.EmploiCategoryService;
import edu.edspace.services.EmploiService;
import edu.edspace.services.UserService;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.stage.FileChooser;
import java.awt.*;
public class AddEmploiController implements Initializable {
    @FXML
    private TextField titleField;
    @FXML
	private TextField descriptionField;
	@FXML
	private DatePicker dateField;
	@FXML
	private ComboBox<CategoryEmploi> categoryNameFieldBox;
    @FXML
    private Button chooseFileBtn;
    @FXML 
    private Button save;
    @FXML 
    private Button cancel;
    @FXML
    private AnchorPane rootPane;
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
    private ImageView logo_iv;
    //sidebar btns
    @FXML 
    private Button btnNews;
    @FXML 
    private Button btnEmploi;
    private List<CategoryEmploi> categories = new ArrayList();
    //control saisie variables
    @FXML
    private Label titleError;
    @FXML
    private Label descriptionError;
    @FXML
    private Label fileError;
    @FXML
    private Label imageError;
    @FXML
    private Label categoryError;
    //choose image and set its path
    @FXML
    private void chooseImage(){
        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        FileChooser.ExtensionFilter extFilterjpg
                = new FileChooser.ExtensionFilter("jpg files (*.png)", "*.png");
        FileChooser.ExtensionFilter extFilterpng
                = new FileChooser.ExtensionFilter("png files (*.jpg)", "*.jpg");
        fileChooser.getExtensionFilters()
                .addAll(extFilterjpg,extFilterpng);
        File file = fileChooser.showOpenDialog(null);
        if(file != null){
            System.out.println(file.getAbsolutePath());
                chooseFileBtn.setText(file.getAbsolutePath());
        }
    }
    
    //add button method
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
        String datePub = new SimpleDateFormat("dd/MM/yy").format(new Date());
        int ext =  chooseFileBtn.getText().lastIndexOf(File.separator);
        String image = chooseFileBtn.getText().substring(ext+1);
        String file = chooseFileBtn.getText();

         CategoryEmploi categoryField = categoryNameFieldBox.getSelectionModel().getSelectedItem();
         Integer categoryName;
         if(categoryField !=null){
            categoryName = categoryField.getId();
            if (title.isEmpty() || description.isEmpty() || datePub.isEmpty() || categoryName.toString().isEmpty() || image.isEmpty()) {
                showError();
            } 
            else {
                try {
                        Files.copy(Paths.get(file), Paths.get(Statics.myPubImages + image),StandardCopyOption.REPLACE_EXISTING);
                    }catch (IOException ex) {
                        Logger.getLogger(AddEmploiController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                System.out.println(image);
                System.out.println(Paths.get(file));
                Emploi p = new Emploi(title, description,categoryName.toString(),datePub,image);
                EmploiService newsService = new EmploiService();
                newsService.addEmploi(p);

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
                 trayIcon.displayMessage("Ajout", "L'offre " + p.getTitle() + "  a ete bien ajouter", TrayIcon.MessageType.INFO);
                getEmploiView(event);
                    
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
        if(chooseFileBtn.getText().isEmpty()){
            fileError.setVisible(true);
        }
            
        if(categoryNameFieldBox.getSelectionModel().getSelectedItem()== null){
            categoryError.setVisible(true);
        }else{
            categoryError.setVisible(false);
        }
    }
    private ObservableList<CategoryEmploi> fillComboBox(){
        ObservableList<CategoryEmploi> allcat = FXCollections.observableArrayList();
        EmploiCategoryService ns = new EmploiCategoryService();
        categories= ns.AllCats();
        for (int i = 0; i < categories.size(); i++) {
            allcat.addAll(categories.get(i));
        }
        return allcat;
    }
    //cancel button method
    @FXML
    public void cancel(MouseEvent event){
        getEmploiView(event);
    }
    //init method
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        MyConnection.getInstance().getCnx();
        categoryNameFieldBox.setItems(FXCollections.observableArrayList(fillComboBox()));
        initImages();
    }
    
//sidebar
@FXML
private void getNewsView(MouseEvent event) {
    try {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/news/allNews.fxml"));
        rootPane.getChildren().setAll(pane);
    } catch (IOException ex) {
        Logger.getLogger(AddEmploiController.class.getName()).log(Level.SEVERE, null, ex);
    }
}
@FXML
private void getEmploiView(MouseEvent event){
    try {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/emploi/allEmploi.fxml"));
        rootPane.getChildren().setAll(pane);
    } catch (IOException ex) {
        Logger.getLogger(AddEmploiController.class.getName()).log(Level.SEVERE, null, ex);
    }
}
@FXML
private void getCatNewsView(MouseEvent event){
    try {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/news/allCategoryNews.fxml"));
        rootPane.getChildren().setAll(pane);
    } catch (IOException ex) {
        Logger.getLogger(AddEmploiController.class.getName()).log(Level.SEVERE, null, ex);
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
        Logger.getLogger(AddEmploiController.class.getName()).log(Level.SEVERE, null, ex);
    }
}
@FXML
private void getDashboardView(MouseEvent event){
    try {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/HomeBack.fxml"));
        rootPane.getChildren().setAll(pane);
    } catch (IOException ex) {
        Logger.getLogger(AddEmploiController.class.getName()).log(Level.SEVERE, null, ex);
    }
}
@FXML
private void getUsers(ActionEvent event) { 
    try {
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
private void getAllMatieresView(MouseEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/matiere/MatieresList.fxml"));
        Parent root = loader.load();
        rootPane.getScene().setRoot(root);
    } catch (IOException ex) {
        Logger.getLogger(AddEmploiController.class.getName()).log(Level.SEVERE, null, ex);
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
private void handleClicks(ActionEvent event) {
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
