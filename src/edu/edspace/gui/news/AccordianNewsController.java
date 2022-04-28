package edu.edspace.gui.news;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import edu.edspace.entities.News;
import edu.edspace.utils.Statics;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class AccordianNewsController implements Initializable{
    @FXML
    private HBox hbox;
    @FXML
    private ImageView imageView;
    @FXML
    private Label title;
    @FXML
    private Label content;
    @FXML
    private Label date;
    News nw;
    public void setData(News news){
        this.nw = news;
        title.setText(news.getTitle());
        content.setText(news.getContent());
        date.setText(news.getDate());
        File f = new File(Statics.myPubImages + nw.getImage());
        Image i = new Image(f.toURI().toString());
        imageView.setImage(i);
        
        imageView.setPreserveRatio(true);
        imageView.fitWidthProperty().bind(hbox.widthProperty());
        imageView.fitHeightProperty().bind(hbox.heightProperty());
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub
        
    }
    
}
