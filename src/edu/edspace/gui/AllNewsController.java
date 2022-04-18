package edu.edspace.gui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.edspace.entities.News;
import edu.edspace.services.NewsService;
import edu.edspace.utils.MyConnection;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
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
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;


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
    private AnchorPane rootPane;
    @FXML 
    private ImageView logoImageView;
    @FXML
    private Button btnAddNews;
    @FXML
    private TilePane tilePaneId;
    @FXML
    private VBox vboxId;
    @FXML
    private Pane paneId;
    @FXML
    private ImageView pubImageId;
    @FXML
    private Button btnEmploi;
    @FXML
    private Button btnNews;
    @FXML
    private Button btnCatNews;
    @FXML
	private TextField titleField;
	@FXML
	private TextField descriptionField;
	@FXML
	private DatePicker dateField;
	@FXML
	private TextField authorField;
	@FXML
	private ComboBox<String> categoryNameField;
    @FXML
    private ImageView imageField;
    Connection connection = null;
    private List<News> newsList = null;

    @FXML
    private void save(MouseEvent event){
        connection = MyConnection.getInstance().getCnx();
       
		String title = titleField.getText();
		String description = descriptionField.getText();
        String author = authorField.getText();
		String datePub = String.valueOf(dateField.getValue());
        String image = "yo.png";
        String categoryName = categoryNameField.getSelectionModel().getSelectedItem();
            
		if (title.isEmpty() || description.isEmpty() || datePub.isEmpty() || author.isEmpty() || categoryName.isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Please fill the fields");
			alert.showAndWait();
		} 
		else 
			{
                News p = new News("yyyy", "T", "Tt", "1","2021/04/08", "yo.png");
                NewsService newsService = new NewsService();
                newsService.addNews(p);
        }
    }
    private List<String> fillComboBox(){
        List<String> allcats = new ArrayList<>();
        try {
			// String query all publications 
			String query = "SELECT category_name FROM `categorie_news` " ;
            connection = MyConnection.getInstance().getCnx();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
                allcats.add(resultSet.getString("category_name"));
			}
            statement.close();
            resultSet.close();
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
        return allcats;
    }
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
    private void getEmploiView(MouseEvent event){
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/allEmploi.fxml"));
			rootPane.getChildren().setAll(pane);
		} catch (IOException ex) {
			Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
		}
    }
    @FXML
    private void addNews(MouseEvent event){
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/addNews.fxml"));
			rootPane.getChildren().setAll(pane);
		} catch (IOException ex) {
			Logger.getLogger(AllNewsController.class.getName()).log(Level.SEVERE, null, ex);
		}

    }
    @FXML
    private void getCatNewsView(MouseEvent event){
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/allCategoryNews.fxml"));
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
        categoryNameField.setItems(FXCollections.observableArrayList(fillComboBox()));
        NewsService newsService = new NewsService();
        newsList = newsService.AllNews();
       // check if list is empty
        if(newsList == null && newsList.isEmpty()){
            Label label = new Label();
            label.setText("La liste des publications est vide");
            HBox hBox = new HBox();
            hBox.getChildren().addAll(label);
            tilePaneId.getChildren().addAll(hBox);
        }else{
            //newsList = newsService.AllNews();
            Iterator<News> it = newsList.iterator();
            while (it.hasNext()) {
                News nw = it.next();
                Label titleLabel = new Label();
                Label ownerLabel = new Label();
                Button modifButton = new Button();
                modifButton.setText("Modify");
                Button deleteButton = new Button("Delete");
                deleteButton.setText("Delete");
                modifButton.setStyle("-fx-border-color: #ff0000;");
                deleteButton.setStyle("-fx-border-color: #ffff00;");
                deleteButton.setOnMouseClicked((MouseEvent event)->{
                    newsService.deleteNews(nw.getId());
                });
                modifButton.setOnMouseClicked((MouseEvent event)->{
                    try {
                        AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/updateNews.fxml"));
                        rootPane.getChildren().setAll(pane);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                HBox hBoxBtns = new HBox();
                hBoxBtns.getChildren().addAll(modifButton,deleteButton);
                titleLabel.setText(nw.getTitle());
                ownerLabel.setText(nw.getOwner());
                VBox v = new VBox(10);
                v.setPadding(new Insets(10, 20, 10, 20));
                v.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(0),new BorderWidths(2))));
                v.getChildren().addAll(titleLabel,ownerLabel,hBoxBtns);
                tilePaneId.getChildren().addAll(v);
        }
        
        }
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
