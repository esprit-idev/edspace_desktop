package edu.edspace.gui.emploi;

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

import edu.edspace.entities.CategoryEmploi;
import edu.edspace.entities.Emploi;
import edu.edspace.services.EmploiCategoryService;
import edu.edspace.services.EmploiService;
import edu.edspace.utils.MyConnection;
import edu.edspace.utils.Statics;
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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

public class UpdateEmploiController implements Initializable {
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
	private ComboBox<CategoryEmploi> categoryNameFieldBox;
    @FXML
    private Button chooseFileBtn;
    @FXML 
    private Button update;
    @FXML 
    private Button cancel;
    @FXML
    private AnchorPane rootPane;
    //sidebar btns
    @FXML 
    private Button btnNews;
    @FXML 
    private Button btnEmploi;
    private List<CategoryEmploi> categories = new ArrayList();
    //id of the offer to update
    int id; 
   
    //upload image
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
                chooseFileBtn.setText(file.getAbsolutePath());
        }
    }
//update method 
    @FXML
    private void updateEmploi(MouseEvent event){
        MyConnection.getInstance().getCnx();
        
        String title = titleField.getText();
		String description = descriptionField.getText();
		String datePub = String.valueOf(dateField.getValue());
        int ext =  chooseFileBtn.getText().lastIndexOf(File.separator);
        String image = chooseFileBtn.getText().substring(ext+1);
        String file = chooseFileBtn.getText();


         CategoryEmploi categoryField = categoryNameFieldBox.getSelectionModel().getSelectedItem();
         Integer categoryName;
         if(categoryField !=null){
            categoryName = categoryField.getId();
            if (title.isEmpty() || description.isEmpty() || datePub.isEmpty() || categoryName.toString().isEmpty()|| image.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Please fill the fields");
                alert.showAndWait();
            } 
            else 
                {
                    try {
                        Files.copy(Paths.get(file), Paths.get(Statics.myPubImages + image));
                     }catch (IOException ex) {
                          Logger.getLogger(AddEmploiController.class.getName()).log(Level.SEVERE, null, ex);
                      }
                    Emploi p = new Emploi(title, description,categoryName.toString(),datePub,image);
                    EmploiService newsService = new EmploiService();
                    newsService.updateEmploi(p,id);
                    getEmploiView(event);
                    
            }
         }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please select a category");
            alert.showAndWait();
         }
             

    }
    @FXML
    private void cancelEmploi(MouseEvent event){
        getEmploiView(event);
    }
    //fill the combobox
    private ObservableList<CategoryEmploi> fillComboBox(){
        ObservableList<CategoryEmploi> allcat = FXCollections.observableArrayList();
        EmploiCategoryService ns = new EmploiCategoryService();
        categories= ns.AllCats();
        for (int i = 0; i < categories.size(); i++) {
            allcat.addAll(categories.get(i));
        }
        return allcat;
    }
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        MyConnection.getInstance().getCnx();
        categoryNameFieldBox.setItems(FXCollections.observableArrayList(fillComboBox()));
        initImages();
        
    }

    //setters to set from allemploi controller
    public void setI(int i){
        id = i;
    }
    public void settitle(String title){
        titleField.setText(title);
    }
    public void setContent(String title){
        descriptionField.setText(title);
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
          AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/allNews.fxml"));
          rootPane.getChildren().setAll(pane);
      } catch (IOException ex) {
          Logger.getLogger(AddEmploiController.class.getName()).log(Level.SEVERE, null, ex);
      }
  }
  @FXML
  private void getCatEmploiView(MouseEvent event){
      try {
          AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/news/allCategoryNews.fxml"));
          rootPane.getChildren().setAll(pane);
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
    private void getAllDocsView(MouseEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/document/DocsList.fxml"));
            Parent root = loader.load();
            rootPane.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(UpdateEmploiController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
    @FXML
    private void getAllMatieresView(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/matiere/MatieresList.fxml"));
            Parent root = loader.load();
            rootPane.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(UpdateEmploiController.class.getName()).log(Level.SEVERE, null, ex);
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
