package edu.edspace.gui.news;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import edu.edspace.entities.News;
import edu.edspace.utils.Statics;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class FrontNewsDetail implements Initializable {

    @FXML
    private Label title;
    @FXML
    private Text content;
    @FXML
    private ImageView image;
    @FXML
    private ImageView backgroundImage;
    @FXML
    private ImageView home_iv;
    @FXML
    private AnchorPane rootPane;
    private News nw;
    int id;
    String imagef;
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        File file = new File("images/bg.PNG");
        Image background = new Image(file.toURI().toString());
        backgroundImage.setImage(background);
        File filehome = new File("images/home_grey.png");
        Image home = new Image(filehome.toURI().toString());
        home_iv.setImage(home);
        
    }
    public void setData(News news){
        this.nw= news;
        title.setText(news.getTitle());
        content.setText(news.getContent());
        File f = new File(Statics.myPubImages + nw.getImage());
        Image i = new Image(f.toURI().toString());
        image.setImage(i);
        image.setPreserveRatio(true);
        image.fitWidthProperty().bind(rootPane.widthProperty());
        image.fitHeightProperty().bind(rootPane.heightProperty());
    }
    public void setI(int i){
        id= i;
    }
    public void settitle(String t){
        title.setText(t);
    }
    public void setContent(String title){
        content.setText(title);
    }
    // public void setowner(String title){
    //     authorField.setText(title);
    // }
    // public void setDate(String date){
    //     dateField.setValue(LocalDate.parse(date));
    // }
    public void setIm(String imag){
        Path f = Paths.get(Statics.myPubImages + imag);
        Image imagev = new Image(f.toUri().toString());
        System.out.println(imagev);
        image.setImage(imagev);
        System.out.println(image);
    }
    @FXML
    private void getHome(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/FrontHome.fxml"));
            Parent root = loader.load();
            rootPane.getScene().setRoot(root);
        } catch (IOException ex) {
            ex.getMessage();
        }
    }    
}
