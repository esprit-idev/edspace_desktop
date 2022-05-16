package edu.edspace.gui.news;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import edu.edspace.entities.Emploi;
import edu.edspace.entities.News;
import edu.edspace.services.NewsService;
import edu.edspace.utils.Statics;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class CardFrontController implements Initializable{
    private News nw;
    private Emploi emp;
    @FXML
    public Label title;
    @FXML
    private Label category;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private ImageView image;

    File file = null;
    NewsService newsService = new NewsService();
    AllNewsController AL = new AllNewsController();

    public void setData(News news) {
        this.nw= news;
        title.setText(news.getTitle());
        category.setText(news.getCategoryName());
        File f = new File(Statics.myPubImages + nw.getImage());
        Image i = new Image(f.toURI().toString());
        System.out.println(f.toURI().toString());
        image.setImage(i);
        image.setPreserveRatio(true);
        image.fitWidthProperty().bind(rootPane.widthProperty());
        image.fitHeightProperty().bind(rootPane.heightProperty());
    }
    public void setDataEmpl(Emploi emps) {
        this.emp= emps;
        title.setText(emps.getTitle());
        category.setText(emps.getCategoryName());
        File f = new File(Statics.myPubImages + emp.getImage());
        Image i = new Image(f.toURI().toString());
        System.out.println(f.toURI().toString());
        image.setImage(i);
        image.setPreserveRatio(true);
        image.fitWidthProperty().bind(rootPane.widthProperty());
        image.fitHeightProperty().bind(rootPane.heightProperty());
    } 
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        
    }
    
}
