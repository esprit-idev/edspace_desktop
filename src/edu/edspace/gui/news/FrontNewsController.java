package edu.edspace.gui.news;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.edspace.entities.CategoryNews;
import edu.edspace.entities.News;
import edu.edspace.gui.document.ListDocFrontController;
import edu.edspace.services.NewsCategoryService;
import edu.edspace.services.NewsService;
import edu.edspace.services.statics;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;

public class FrontNewsController implements Initializable {
    @FXML
    private AnchorPane rootPane;
    @FXML
    private ImageView home_iv;
    @FXML
    private TilePane tilePane;
    @FXML
    private ComboBox<CategoryNews> category_cb;
    private List<CategoryNews> categories = new ArrayList<>();
    private List<News> newsList = new ArrayList<>();

    public void initData(){
        NewsService newsService = new NewsService();
        newsList = newsService.AllNews();
        NewsCategoryService cnews = new NewsCategoryService();
        categories = cnews.AllCatsNames();
        //System.out.println(catList);
       // check if list is empty
        if(newsList == null && newsList.isEmpty()){
            Label label = new Label();
            label.setText("Pas des nouvelles :( ");
            HBox hBox = new HBox();
            hBox.getChildren().addAll(label);
            tilePane.getChildren().addAll(hBox);
        }else{
            category_cb.setItems(FXCollections.observableArrayList(fillComboBox()));
            category_cb.valueProperty().addListener(new ChangeListener<>() {
                @Override
                public void changed(ObservableValue<? extends CategoryNews> cat, CategoryNews arg1,
                        CategoryNews arg2) {
                        newsList = newsService.filterByCat(category_cb.getValue().getId());
                        System.out.println(newsList);
                        System.out.println(category_cb.getValue().getId());
                        tilePane.getChildren().clear();
                        if(newsList.isEmpty()){
                            Label label = new Label();
                            label.setText("Pas des nouvelles :( ");
                        }else
                        initPane(newsList, categories);
                }
            });
            initPane(newsList,categories);
        }
    }
    private void initPane(List<News> news,List<CategoryNews>cc) {
        statics statics = new statics();
        Iterator<News> it = news.iterator();
        while (it.hasNext()) {
            News nw = it.next();
            try{
                FXMLLoader fXMLLoader = new FXMLLoader();
                fXMLLoader.setLocation(getClass().getResource("/edu/edspace/gui/news/cardFront.fxml"));
                AnchorPane pane = fXMLLoader.load();
                CardFrontController cd = fXMLLoader.getController();
                cd.setData(nw);
                pane.setOnMouseClicked(new EventHandler<Event>() {
                    @Override
                    public void handle(Event arg0) {
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/news/frontNewsDetail.fxml"));
                            AnchorPane panel = loader.load();
                            FrontNewsDetail fdetail = loader.getController();
                            fdetail.setI(nw.getId());
                            fdetail.settitle(nw.getTitle());
                            fdetail.setContent(nw.getContent());
                            fdetail.setIm(nw.getImage());
                            fdetail.setLikes(statics.numberOfLikes(nw.getId()));
                            rootPane.getChildren().setAll(panel);
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } 
                    }
                    
                });
                tilePane.getChildren().addAll(pane);
                
            } catch (IOException ex) {
                Logger.getLogger(AllNewsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }    
    private ObservableList<CategoryNews> fillComboBox(){
        ObservableList<CategoryNews> allcat = FXCollections.observableArrayList();
        NewsCategoryService ns = new NewsCategoryService();
        categories= ns.AllCats();
        for (int i = 0; i < categories.size(); i++) {
            allcat.addAll(categories.get(i));
        }
        return allcat;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        initImages();
        initData();
    }
    
    public void initImages() {
        File fileHome = new File("images/home_grey.png");
        Image homeI = new Image(fileHome.toURI().toString());
        home_iv.setImage(homeI);          
    }

    @FXML
    private void getHome(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/FrontHome.fxml"));
            Parent root = loader.load();
            rootPane.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(ListDocFrontController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }   
}
