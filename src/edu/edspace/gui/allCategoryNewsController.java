package edu.edspace.gui;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.edspace.entities.CategoryNews;
import edu.edspace.services.NewsCategoryService;
import edu.edspace.utils.MyConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class allCategoryNewsController implements Initializable{

    @FXML AnchorPane rootPane;
    @FXML 
    private ImageView logoImageView;
    @FXML
    private Button btnNews;
    @FXML
    private Button btnCatNews;
    @FXML
    private TableView<CategoryNews> tableViewId;
    @FXML
    private TableColumn<CategoryNews,String> categoryNameCol;
    @FXML

    Connection connection = null;
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
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        connection = MyConnection.getInstance().getCnx();
        CategoryNews cat = new CategoryNews();
        NewsCategoryService catService = new NewsCategoryService();
        cats.clear();
        //catService.AllCats();
        cats.addAll(catService.AllCats());
        tableViewId.setItems(cats);
       // categoryNameCol.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
    }
    
}
