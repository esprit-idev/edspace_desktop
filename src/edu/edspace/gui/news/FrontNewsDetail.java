package edu.edspace.gui.news;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import edu.edspace.entities.News;
import edu.edspace.services.NewsService;
import edu.edspace.services.statics;
import edu.edspace.utils.MyConnection;
import edu.edspace.utils.Statics;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
//import twitter4j.Twitter;

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
    private ImageView arrow_iv;
    @FXML
    private ImageView shareImage;
    @FXML
    private ImageView likeImage;
    @FXML
    private Label numberOfLikes;
    @FXML
    private Button shareButton;
    @FXML
    private Button likeButton;
    @FXML
    private AnchorPane rootPane;
    private News nw;
    Integer id;
    String imagef;
    NewsService newsService = new NewsService();
    News newss = null;
    Integer likes;
    /* */ 
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        MyConnection.getInstance().getCnx();
        initImages();
    }
    @FXML
    private void clickLike(MouseEvent event){
        System.out.println(id);
        statics statics = new statics();
        likes = statics.numberOfLikes(id);
        System.out.println(likes);
        System.out.println(newss);
        likes = likes +1;
        System.out.print(likes);
        newsService.updateLikes(likes, newsService.findById(id).getId());
        numberOfLikes.setText(likes.toString());
    }
    @FXML
    private void openBrowser(MouseEvent event) throws URISyntaxException{
        URI u = new URI("https://twitter.com/compose/tweet");
        try {
            java.awt.Desktop.getDesktop().browse(u);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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
        Integer likes = newss.getLikes();
        newss = news;
        numberOfLikes.setText(likes.toString());
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
    public void setLikes(Integer l){
        numberOfLikes.setText(l.toString());
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
        image.setImage(imagev);
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
    @FXML
    private void getTabAff(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/news/frontNews.fxml"));
            Parent root = loader.load();
            rootPane.getScene().setRoot(root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    private void initImages(){
        File file = new File("images/bg.PNG");
        Image background = new Image(file.toURI().toString());
        backgroundImage.setImage(background);
        File filehome = new File("images/home_grey.png");
        Image home = new Image(filehome.toURI().toString());
        home_iv.setImage(home);
        File fileshare = new File("images/share_green.png");
        Image share = new Image(fileshare.toURI().toString());
        shareImage.setImage(share);
        File filelike = new File("images/heart.png");
        Image like = new Image(filelike.toURI().toString());
        likeImage.setImage(like);
        File filearrow = new File("images/back_grey.png");
        Image arrowI = new Image(filearrow.toURI().toString());
        arrow_iv.setImage(arrowI);
    }    
}
