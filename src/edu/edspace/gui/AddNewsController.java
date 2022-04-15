package edu.edspace.gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import java.io.File;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import edu.edspace.entities.News;
import edu.edspace.services.NewsService;
import edu.edspace.utils.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
	private ComboBox<String> categoryNameField;

    @FXML
    private ImageView imageField;
    
    @FXML 
    private ImageView logoImageView;

    @FXML 
    private Button save;

    Connection connection = null;
    NewsService newsService = null;
    @FXML
    private void save(MouseEvent event) throws SQLException{
        connection = MyConnection.getInstance().getCnx();
       
		String title = titleField.getText();
		String description = descriptionField.getText();
        String author = authorField.getText();
		String datePub = String.valueOf(dateField.getValue());
        String image = "yo.png";
        String categoryName = categoryNameField.getValue();
            
		if (title.isEmpty() || description.isEmpty() || datePub.isEmpty() || author.isEmpty() || categoryName.isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Please fill the fields");
			alert.showAndWait();

		} 
		else 
			{
                newsService = new NewsService();
                News p = new News(title, author, description, categoryName, datePub, image);
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
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        connection = MyConnection.getInstance().getCnx();
        categoryNameField.setItems(FXCollections.observableArrayList(fillComboBox()));
        File file = new File("images/logo1.png");
        Image logo = new Image(file.toURI().toString());
        logoImageView.setImage(logo);
        
    }
    
}
