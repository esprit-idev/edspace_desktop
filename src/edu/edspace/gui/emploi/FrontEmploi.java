package edu.edspace.gui.emploi;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import edu.edspace.entities.CategoryEmploi;
import edu.edspace.entities.Emploi;
import edu.edspace.gui.news.CardFrontController;
import edu.edspace.services.EmploiCategoryService;
import edu.edspace.services.EmploiService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import java.util.ArrayList;
import java.util.Iterator;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
public class FrontEmploi implements Initializable {
    @FXML
    private AnchorPane rootPane;
    @FXML
    private ImageView home_iv;
    @FXML
    private TilePane tilePane;
    @FXML
    private ComboBox<CategoryEmploi> category_cb;
    private List<CategoryEmploi> categories = new ArrayList<>();
    private List<Emploi> empsList = new ArrayList<>();

    public void initData(){
        EmploiService eService = new EmploiService();
        empsList = eService.AllEmplois();
        EmploiCategoryService cemps = new EmploiCategoryService();
        categories = cemps.AllCats();
        //System.out.println(catList);
       // check if list is empty
        if(empsList == null && empsList.isEmpty()){
            Label label = new Label();
            label.setText("Pas des nouvelles :( ");
            HBox hBox = new HBox();
            hBox.getChildren().addAll(label);
            tilePane.getChildren().addAll(hBox);
        }else{
            category_cb.setItems(FXCollections.observableArrayList(fillComboBox()));
            // category_cb.valueProperty().addListener(new ChangeListener<>() {
            //     @Override
            //     public void changed(ObservableValue<? extends CategoryNews> cat, CategoryNews arg1,
            //             CategoryNews arg2) {
            //             newsList = newsService.filterByCat(category_cb.getValue().getId());
            //             System.out.println(newsList);
            //             System.out.println(category_cb.getValue().getId());
            //             tilePane.getChildren().clear();
            //             if(newsList.isEmpty()){
            //                 Label label = new Label();
            //                 label.setText("Pas des nouvelles :( ");
            //             }else
            //             initPane(newsList, categories);
            //     }
            // });
            initPane(empsList,categories);
        }
    }
    private void initPane(List<Emploi> emps,List<CategoryEmploi>cc) {
        Iterator<Emploi> it = emps.iterator();
        while (it.hasNext()) {
            Emploi nw = it.next();
            try{
            FXMLLoader fXMLLoader = new FXMLLoader();
                fXMLLoader.setLocation(getClass().getResource("/edu/edspace/gui/news/cardFront.fxml"));
                AnchorPane pane = fXMLLoader.load();
                CardFrontController cd = fXMLLoader.getController();
                cd.setDataEmpl(nw);
                tilePane.getChildren().addAll(pane);
                
            } catch (IOException ex) {
                Logger.getLogger(FrontEmploi.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }    
    private ObservableList<CategoryEmploi> fillComboBox(){
        ObservableList<CategoryEmploi> allcat = FXCollections.observableArrayList();
        EmploiCategoryService ns = new EmploiCategoryService();
        categories= ns.AllCats();
        for (int i = 0; i < categories.size(); i++) {
            allcat.addAll(categories.get(i));
        }
        return allcat;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        initData();
        initImages();
        
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
            Logger.getLogger(FrontEmploi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

