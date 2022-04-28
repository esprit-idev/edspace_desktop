/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.edspace.gui;

import edu.edspace.entities.CategoryEmploi;
import edu.edspace.entities.CategoryNews;
import edu.edspace.entities.News;
import edu.edspace.services.EmploiCategoryService;
import edu.edspace.services.NewsCategoryService;
import edu.edspace.services.NewsService;
import edu.edspace.services.statics;
import edu.edspace.utils.MyConnection;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author MeriamBI
 */
public class HomeBackController implements Initializable {

    Connection connection = null;
    @FXML
    private Button btnOverview;
    @FXML
    private Button btnOrders;
    @FXML
    private Button btnCustomers;
    @FXML
    private Button btnMenus;
    @FXML
    private Button btnMatiere;
    @FXML
    private Button btnEmploi;
    @FXML
    private Button btnSignout1;
    @FXML
    private Button btnCentrePartage;
    @FXML
    private Button btnSignout3;
    @FXML
    private ImageView logo_iv;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Button btnNews;
    @FXML
    private Pane pnlOverview;
    @FXML
    private ImageView home_iv;
    @FXML
    private ImageView tabaff_iv;
    @FXML
    private ImageView users_iv;
    @FXML
    private ImageView niveaux_iv;
    @FXML
    private ImageView classe_iv;
    @FXML
    private ImageView matieres_iv;
    @FXML
    private ImageView club_iv;
    @FXML
    private ImageView offre_iv;
    @FXML
    private ImageView forum_iv;
    @FXML
    private ImageView centre_iv;
    @FXML
    private ImageView signOut_iv;
    @FXML
    private ImageView search_iv;
    @FXML
    private ImageView niveauImg;
    @FXML
    private ImageView classImg;
    @FXML
    private ImageView matiereImg;
    @FXML
    private ImageView docImg;
    @FXML
    private ImageView newsarrowImg;
    @FXML
    private ImageView emploiarrowImg;
    @FXML
    private ImageView studentarrowImg;
    @FXML
    private ImageView clubarrowImg;
    @FXML
    private Label pubNum;
    @FXML
    private Label empNum;
    @FXML
    private Label studentNum;
    @FXML
    private Label clubNum;
    @FXML
    private Label niveauNum;
    @FXML
    private Label classeNum;
    @FXML
    private Label matiereNum;
    @FXML
    private Label documentNum;
    @FXML
    private PieChart pieChart;
    @FXML
    private BarChart<String,Integer> barChart;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private PieChart pieChartEmp;
    private List<CategoryNews> categories = new ArrayList<CategoryNews>();
    private List<CategoryEmploi> categoriesEmpl = new ArrayList<CategoryEmploi>();
    @FXML
    private Button btnClubs;
    @FXML
    private Button viewNewsBtn;
    @FXML
    private Button viewEmploiBtn;
    @FXML
    private Button viewEtudiantBtn;
    @FXML
    private Button viewClubsBtn;

    @FXML
    private void getNewsView(MouseEvent event) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/news/allNews.fxml"));
            rootPane.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(HomeBackController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    private void getCatNewsView(MouseEvent event) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/news/allCategoryNews.fxml"));
            rootPane.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(HomeBackController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void getAllDocsView(MouseEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/document/DocsList.fxml"));
            Parent root = loader.load();
            rootPane.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(HomeBackController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void getEmploiView(MouseEvent event) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/emploi/allEmploi.fxml"));
            rootPane.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(HomeBackController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    private void getDashboardView(MouseEvent event) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/HomeBack.fxml"));
            rootPane.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(HomeBackController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    private void getAllMatieresView(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/matiere/MatieresList.fxml"));
            Parent root = loader.load();
            rootPane.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(HomeBackController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        connection = MyConnection.getInstance().getCnx();
        initImages();
        initChart();

    }

    //here are statics of our dashboard
    public void initChart() {
        statics sc = new statics();
        pubNum.setText(sc.numberOfPublications());
        empNum.setText(sc.numberOfOffreEmploi());
        studentNum.setText(sc.numberOfStudents("[\"ROLE_STUDENT\",\"ROLE_RESPONSABLEC\"]"));
        clubNum.setText(sc.numberOfClubs());
        niveauNum.setText(sc.numberOfNiveau());
        classeNum.setText(sc.numberOfClasses());
        matiereNum.setText(sc.numberOfMatiere());
        documentNum.setText(sc.numberOfDocuments());
        //setting the two different pie charts
        ObservableList<PieChart.Data> pieDataNews = FXCollections.observableArrayList(pieDataNews());
        ObservableList<PieChart.Data> pieDataEmpl = FXCollections.observableArrayList(pieDataEmploi());
        pieChart.setData(pieDataNews);
        pieChart.setTitle("N Publications Par Categorie");
        pieChartEmp.setData(pieDataEmpl);
        pieChartEmp.setTitle("N Offres Par Categorie");
        barChart.setTitle("Nombre des Intéractions Par Publication");
        pieChart.setAnimated(true);
        XYChart.Series<String,Integer> seriesData = barChartNews();
        xAxis.setLabel("Titres");
        yAxis.setLabel("J aimes");
        barChart.getData().addAll(seriesData);
        barChart.setAnimated(true);
       // barChart.setbar
    }

    //observable list of pichart data, filled with category names and their corresponding publications number
    private ObservableList<PieChart.Data> pieDataNews() {
        statics sc = new statics();
        ObservableList<PieChart.Data> allcat = FXCollections.observableArrayList();
        NewsCategoryService ns = new NewsCategoryService();
        categories = ns.AllCats();
        int num = 0;
        for (int i = 0; i < categories.size(); i++) {
            num = sc.numberOfPubsByCategory(categories.get(i).getId());
            allcat.add(new PieChart.Data(categories.get(i).getCategoryName(), num));
        }
        return allcat;
    }

    //observable list of pichart data, filled with category names and their corresponding emploi number
    private ObservableList<PieChart.Data> pieDataEmploi() {
        statics sc = new statics();
        ObservableList<PieChart.Data> allcat = FXCollections.observableArrayList();
        EmploiCategoryService ns = new EmploiCategoryService();
        categoriesEmpl = ns.AllCats();
        int num = 0;
        for (int i = 0; i < categoriesEmpl.size(); i++) {
            num = sc.numberOfEmploisByCategory(categoriesEmpl.get(i).getId());
            allcat.add(new PieChart.Data(categoriesEmpl.get(i).getCategoryName(), num));
        }
        return allcat;
    }
    private XYChart.Series<String,Integer> barChartNews() {
        statics sc = new statics();
        NewsService newsService = new NewsService();
        List<News> newsList = newsService.AllNews();
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        for(int i=0; i < newsList.size(); i++){
            String titles = newsList.get(i).getTitle();
            int likes = sc.numberOfLikes(newsList.get(i).getId());
            System.out.println(likes);
            series.getData().add(new XYChart.Data<>(titles,likes));
        }
        return series;
    }
    @FXML
    private void handleClicks(ActionEvent event) {

    }

    public void initImages() {
        File fileLogo = new File("images/logo1.png");
        Image logoI = new Image(fileLogo.toURI().toString());

        File fileHome = new File("images/stats_grey.png");
        Image homeI = new Image(fileHome.toURI().toString());

        File fileTab = new File("images/announcement_grey.png");
        Image tabI = new Image(fileTab.toURI().toString());

        File fileLevel = new File("images/level_grey.png");
        Image levelI = new Image(fileLevel.toURI().toString());

        File fileClass = new File("images/class-management_grey.png");
        Image classI = new Image(fileClass.toURI().toString());

        File fileBook = new File("images/book_grey.png");
        Image bookI = new Image(fileBook.toURI().toString());

        File fileForum = new File("images/forum2_grey.png");
        Image forumI = new Image(fileForum.toURI().toString());

        File fileOffre = new File("images/briefcase_grey.png");
        Image offreI = new Image(fileOffre.toURI().toString());

        File fileDocs = new File("images/file_grey.png");
        Image docsI = new Image(fileDocs.toURI().toString());

        File fileUsers = new File("images/users_grey.png");
        Image usersI = new Image(fileUsers.toURI().toString());

        File fileClub = new File("images/org_grey.png");
        Image clubI = new Image(fileClub.toURI().toString());

        File fileOut = new File("images/logout_grey.png");
        Image outI = new Image(fileOut.toURI().toString());

        logo_iv.setImage(logoI);
        home_iv.setImage(homeI);
        tabaff_iv.setImage(tabI);
        users_iv.setImage(usersI);
        niveaux_iv.setImage(levelI);
        classe_iv.setImage(classI);
        matieres_iv.setImage(bookI);
        club_iv.setImage(clubI);
        offre_iv.setImage(offreI);
        forum_iv.setImage(forumI);
        centre_iv.setImage(docsI);
        signOut_iv.setImage(outI);

        //statics images don't copy them please
        File fileniv = new File("images/level_grey_big.png");
        Image nivI = new Image(fileniv.toURI().toString());
        niveauImg.setImage(nivI);

        File filemat = new File("images/book_grey_big.png");
        Image matI = new Image(filemat.toURI().toString());
        matiereImg.setImage(matI);

        File fileclass= new File("images/briefcase_grey_big.png");
        Image classsI = new Image(fileclass.toURI().toString());
        classImg.setImage(classsI);

        File filedoc= new File("images/file_grey_doc.png");
        Image docssI = new Image(filedoc.toURI().toString());
        docImg.setImage(docssI);

        File filearrow= new File("images/arrow.png");
        Image arrowI = new Image(filearrow.toURI().toString());
        newsarrowImg.setImage(arrowI);
        emploiarrowImg.setImage(arrowI);
        studentarrowImg.setImage(arrowI);
        clubarrowImg.setImage(arrowI);
        
    }
    @FXML
    private void displayClubs(ActionEvent event) {
        try {
            //instance mtaa el crud
            //redirection
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/Clubs/ClubListAdmin.fxml"));
            Parent root = loader.load();
            rootPane.getScene().setRoot(root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    @FXML
    private void getForum(MouseEvent event) {
        try {
            //instance mtaa el crud
            //redirection
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/ThreadList.fxml"));
            Parent root = loader.load();
            rootPane.getScene().setRoot(root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }
    @FXML
    private void getUsers(ActionEvent event) {
        try {
            //instance mtaa el crud
            //redirection
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/AllAdmins.fxml"));
            Parent root = loader.load();
            rootPane.getScene().setRoot(root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

}