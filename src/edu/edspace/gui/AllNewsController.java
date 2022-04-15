package edu.edspace.gui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.edspace.entities.News;
import edu.edspace.services.NewsService;
import edu.edspace.utils.MyConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    private AnchorPane rootPane;
    @FXML 
    private ImageView logoImageView;
    @FXML
    private Button btnAddNews;
    @FXML
    private TilePane hboxId;
    @FXML
    private VBox vboxId;
    @FXML
    private Pane paneId;
    @FXML
    private ImageView pubImageId;

    NewsService newsService = null;
    
    @FXML
    private void addNews(MouseEvent event){
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/addNews.fxml"));
			rootPane.getChildren().setAll(pane);
		} catch (IOException ex) {
			Logger.getLogger(AllNewsController.class.getName()).log(Level.SEVERE, null, ex);
		}

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        MyConnection.getInstance().getCnx();
        newsService = new NewsService();
        newsService.AllNews();
        Iterator<News> it = newsService.AllNews().iterator();
        
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
            hboxId.getChildren().addAll(v);
        }
        File file = new File("images/logo1.png");
        Image logo = new Image(file.toURI().toString());
        logoImageView.setImage(logo);
    }
    
}
