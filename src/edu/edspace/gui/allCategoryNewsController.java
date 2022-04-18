package edu.edspace.gui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.edspace.entities.CategoryNews;
import edu.edspace.services.NewsCategoryService;
import edu.edspace.utils.MyConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

public class allCategoryNewsController implements Initializable{

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
    private Button btnNews;
    @FXML
    private Button btnCatNews;
    @FXML
    private TableView<CategoryNews> tableViewId;
    @FXML
    private TableColumn<CategoryNews,String> categoryNameCol;
    @FXML
    private TableColumn<CategoryNews,String> updateCol;
    @FXML
    private TableColumn<CategoryNews,String> deleteCol;
    
    Connection connection = null;
    CategoryNews c = null;
    ObservableList<CategoryNews> cats = FXCollections.observableArrayList();
    @FXML
    private void getNewsView(MouseEvent event){
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/allNews.fxml"));
			rootPane.getChildren().setAll(pane);
		} catch (IOException ex) {
			Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
		}
    }
    @FXML
    private void handleClicks(ActionEvent event) {
    }
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        connection = MyConnection.getInstance().getCnx();
        initImages();
        NewsCategoryService catService = new NewsCategoryService();
        cats.clear();
        cats.addAll(catService.AllCats());
        categoryNameCol.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
        updateCell();
        deleteCell();
        }
public void updateCell(){
             //add cell of button edit 
         Callback<TableColumn<CategoryNews, String>, TableCell<CategoryNews, String>> cellFoctory = (TableColumn<CategoryNews, String> param) -> {
            // make cell containing buttons
            final TableCell<CategoryNews, String> cell = new TableCell<CategoryNews, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {
                        Button modifButton = new Button();
                        modifButton.setText("Update");
                        modifButton.setStyle("-fx-border-color: #ff0000;");
                        modifButton.setOnMouseClicked((MouseEvent event)->{
                            try {
                                AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/updateNews.fxml"));
                                rootPane.getChildren().setAll(pane);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });

                        HBox managebtn = new HBox(modifButton);
                        managebtn.setStyle("-fx-alignment:center");
                        setGraphic(managebtn);
                        setText(null);

                    }
                }

            };

            return cell;
        };
         updateCol.setCellFactory(cellFoctory);
         tableViewId.setItems(cats);
}
public void deleteCell(){
    NewsCategoryService catService = new NewsCategoryService();
    Callback<TableColumn<CategoryNews, String>, TableCell<CategoryNews, String>> cellFoctory = (TableColumn<CategoryNews, String> param) -> {
        final TableCell<CategoryNews, String> cell = new TableCell<CategoryNews, String>() {
            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                //that cell created only on non-empty rows
                if (empty) {
                    setGraphic(null);
                    setText(null);

                } else {
                    Button deleteButton = new Button("Delete");
                    deleteButton.setText("Delete");
                    deleteButton.setStyle("-fx-border-color: #ffff00;");
                    deleteButton.setOnMouseClicked((MouseEvent event)->{
                        c = tableViewId.getSelectionModel().getSelectedItem();
                        catService.deleteCat(c.getId());
                    });
                    HBox managebtn = new HBox(deleteButton);
                    managebtn.setStyle("-fx-alignment:center");
                    setGraphic(managebtn);
                    setText(null);
                }
            }
        };
        return cell;
    };
     deleteCol.setCellFactory(cellFoctory);
     tableViewId.setItems(cats);
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
        users_iv.setImage(homeI);
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
