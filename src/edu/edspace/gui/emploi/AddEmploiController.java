package edu.edspace.gui.emploi;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
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
import edu.edspace.utils.MyConnection;
import edu.edspace.utils.Statics;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.stage.FileChooser;

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
    //sidebar btns
    @FXML 
    private Button btnNews;
    @FXML 
    private Button btnEmploi;
    private List<CategoryEmploi> categories = new ArrayList();

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
                chooseFileBtn.setText(file.getAbsolutePath());
        }
    }
    
    //add button method
    @FXML
    public void save(MouseEvent event){
        MyConnection.getInstance().getCnx();
       
		String title = titleField.getText();
		String description = descriptionField.getText();
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
             Logger.getLogger(AddEmploiController.class.getName()).log(Level.SEVERE, null, ex);
         }
         CategoryEmploi categoryField = categoryNameFieldBox.getSelectionModel().getSelectedItem();
         Integer categoryName;
         if(categoryField !=null){
            categoryName = categoryField.getId();
            if (title.isEmpty() || description.isEmpty() || datePub.isEmpty() || categoryName.toString().isEmpty() || image.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Please fill the fields");
                alert.showAndWait();
            } 
            else 
                {
                    Emploi p = new Emploi(title, description,categoryName.toString(),datePub,image);
                    EmploiService newsService = new EmploiService();
                    newsService.addEmploi(p);
                    getEmploiView(event);
                    
            }
         }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please select a category");
            alert.showAndWait();
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
        getNewsView(event);
    }
    //init method
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        MyConnection.getInstance().getCnx();
        categoryNameFieldBox.setItems(FXCollections.observableArrayList(fillComboBox()));

        
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
  private void getCatNewsView(MouseEvent event) {
      try {
          AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/allCategoryNews.fxml"));
          rootPane.getChildren().setAll(pane);
      } catch (IOException ex) {
          Logger.getLogger(AllEmploiController.class.getName()).log(Level.SEVERE, null, ex);
      }
  }
 
    
}
