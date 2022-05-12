/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.edspace.gui;

import edu.edspace.entities.Session;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import edu.edspace.entities.Thread;
import edu.edspace.gui.User.LoginController;
import edu.edspace.gui.news.AllNewsController;
import edu.edspace.gui.news.allCategoryNewsController;
import edu.edspace.services.ThreadService;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author 21656
 */
public class ThreadListController implements Initializable {

    
    ObservableList<Thread> Threads = FXCollections.observableArrayList();
    @FXML
    private AnchorPane ap;
    @FXML
    private VBox vmain;
    
    final List<Thread> th = new ArrayList();
    @FXML
    private AnchorPane rootPane;
    @FXML
    private ImageView logo_iv;
    @FXML
    private Button btnOverview;
    @FXML
    private ImageView home_iv;
    @FXML
    private Button btnNews;
    @FXML
    private ImageView tabaff_iv;
    @FXML
    private Button btnOrders;
    @FXML
    private ImageView users_iv;
    @FXML
    private Button btnCustomers;
    @FXML
    private ImageView niveaux_iv;
    @FXML
    private Button btnMenus;
    @FXML
    private ImageView classe_iv;
    @FXML
    private Button btnMatiere;
    @FXML
    private ImageView matieres_iv;
    @FXML
    private Button btnClubs;
    @FXML
    private ImageView club_iv;
    @FXML
    private Button btnEmploi;
    @FXML
    private ImageView offre_iv;
    @FXML
    private Button btnSignout1;
    @FXML
    private ImageView forum_iv;
    @FXML
    private Button btnCentrePartage;
    @FXML
    private ImageView centre_iv;
    @FXML
    private Button btnSignout3;
    @FXML
    private ImageView signOut_iv;
    @FXML
    private Pane pnlOverview;
    @FXML
    private Button topics;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initImages();
       ThreadService threadService = new ThreadService();
       List<Thread>ts = threadService.listThreads();
        for (int i = 0;i<ts.size();i++)
       {
           th.add(ts.get(i));
       }
       
       topics.setOnAction(e->{
                try {
            //instance mtaa el crud
            //redirection
            FXMLLoader loader = new FXMLLoader(getClass().getResource("listTopics.fxml"));
            Parent root = loader.load();
            club_iv.getScene().setRoot(root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
           });
        
       Threads.clear();
       Threads.addAll(threadService.listThreads());
       vmain.setSpacing(10);
       if(ts.size()==0){
           vmain.getChildren().add(new Text("Empty"));
       }
       for (int i = 0;i<ts.size();i++)
       {
           int q = i;
           Thread t = new Thread();
          
           t = ts.get(i);
           System.out.println(ts.get(i).getUser());
           VBox v = new VBox();
           HBox hb = new HBox();
           Hyperlink h = new Hyperlink();
           Label l = new Label();
           Button del = new Button("Delete");
           del.setStyle("-fx-background-color: red; -fx-text-fill: white;");
           Button up = new Button("Update");
           up.setStyle("-fx-background-color: gray; -fx-text-fill: white;");
           l.setText(t.getPostDate());
           h.setText(t.getQuestion());
           h.setStyle("-fx-font-size: 21;");
           v.getChildren().add(h);
           up.setOnAction(e->{
               FXMLLoader loader = new FXMLLoader(getClass().getResource("newThread.fxml"));
               try {
                   Parent root1 = loader.load();
                   NewThreadController TC = loader.getController();
                   TC.update(th.get(q));
                   vmain.getScene().setRoot(root1);
               } catch (IOException ex) {
                   Logger.getLogger(ThreadListController.class.getName()).log(Level.SEVERE, null, ex);
               }
           });
           HBox ha= new HBox();
           
         
          //System.out.println(s);
                       
           if(t.getUser()==Session.getId()){
               ha.getChildren().add(up);
           }
           Text tx= new Text();
           tx.setText("Added by "+t.getUser());
           
           if(Session.getId()== t.getUser() || Session.getRoles().equals("[\"ROLE_ADMIN\"]")){
           ha.getChildren().add(del);
            del.setOnAction(e->{
               Alert a = new Alert(Alert.AlertType.CONFIRMATION);
               a.setTitle("Confirmation");
               a.setHeaderText(null);
               a.setContentText("Are you sure you want to delete" + th.get(q).getQuestion());
              
               Optional<ButtonType> action = a.showAndWait();
               if(action.get() == ButtonType.OK){
                   threadService.deleteThread(th.get(q));
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("ThreadList.fxml"));
        try {
            Parent root = loader.load();
            del.getScene().setRoot(root);
            
        } catch (IOException ex) {
            Logger.getLogger(ListTopicsController.class.getName()).log(Level.SEVERE, null, ex);
        }
                    }
          
           });
          }
           
           
           ha.setSpacing(5);
           ha.setStyle("-fx-padding: 0 10 0;");
           v.getChildren().addAll(ha);
          
           h.setOnAction(
              new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Thread.fxml"));
               try {
                   Parent root1 = loader.load();
                   ThreadController TC = loader.getController();
                   TC.setThread(th.get(q).getQuestion(),th.get(q).getId());
                   vmain.getScene().setRoot(root1);
               } catch (IOException ex) {
                   Logger.getLogger(ThreadListController.class.getName()).log(Level.SEVERE, null, ex);
               }
            
            
        }
    });
           l.setStyle("-fx-padding: 0 120 0;");
           l.setTextFill(Color.GREEN);
           
           v.getChildren().add(l);
           v.setBorder(new Border(new BorderStroke(Color.GREEN, 
            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
           vmain.getChildren().add(v);
       }
       
           }    

    @FXML
    private void addNewThread(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("newThread.fxml"));
            Parent root = loader.load();
            vmain.getScene().setRoot(root);
            
        } catch (IOException ex) {
            Logger.getLogger(ThreadListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public  void setThread(Thread t){
        
    }
        @FXML
    private void getNewsView(MouseEvent event){
        
    }    
    @FXML
    private void getEmploiView(MouseEvent event){
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/emploi/allEmploi.fxml"));
			rootPane.getChildren().setAll(pane);
		} catch (IOException ex) {
			Logger.getLogger(AllNewsController.class.getName()).log(Level.SEVERE, null, ex);
		}
    }
    private void getCatNewsView(MouseEvent event){
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/news/allCategoryNews.fxml"));
			rootPane.getChildren().setAll(pane);
		} catch (IOException ex) {
			Logger.getLogger(AllNewsController.class.getName()).log(Level.SEVERE, null, ex);
		}
    }
    @FXML
    private void getAllDocsView(MouseEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/document/DocsList.fxml"));
            Parent root = loader.load();
            rootPane.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(allCategoryNewsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void getNiveauView(MouseEvent event){
       
    }
    private void getClassesView(MouseEvent event){
       
    }           
    @FXML
    private void getAllMatieresView(MouseEvent event) {
        
    }
    @FXML
    private void displayClubs(ActionEvent event) {
        try {
            //instance mtaa el crud
            //redirection
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/Clubs/ClubListAdmin.fxml"));
            Parent root = loader.load();
            club_iv.getScene().setRoot(root);
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
            forum_iv.getScene().setRoot(root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }
    @FXML
    private void getUsers(ActionEvent event) {
        
        try {
            //instance mtaa el crud
            //redirection
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/Admin/AllAdmins.fxml"));
            Parent root = loader.load();
            club_iv.getScene().setRoot(root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }
    private void logout(MouseEvent event){
        
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
    }

    @FXML
    private void getDashboardView(ActionEvent event) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/HomeBack.fxml"));
			rootPane.getChildren().setAll(pane);
		} catch (IOException ex) {
			Logger.getLogger(allCategoryNewsController.class.getName()).log(Level.SEVERE, null, ex);
		}
    }

    @FXML
    private void getNewsView(ActionEvent event) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/news/allNews.fxml"));
			rootPane.getChildren().setAll(pane);
		} catch (IOException ex) {
			Logger.getLogger(AllNewsController.class.getName()).log(Level.SEVERE, null, ex);
		}
    }

    @FXML
    private void getNiveauView(ActionEvent event) {
         try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/Niveau/AllNiveau.fxml")); 
            Parent root = loader.load(); 
            rootPane.getScene().setRoot(root); 
		} catch (IOException ex) {
			ex.printStackTrace(); 
		}
    }

    @FXML
    private void getClassesView(ActionEvent event) {
         try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/Classe/AllClasses.fxml")); 
            Parent root = loader.load(); 
            rootPane.getScene().setRoot(root); 
		} catch (IOException ex) {
			ex.printStackTrace(); 
		}
    }

    @FXML
    private void getAllMatieresView(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/matiere/MatieresList.fxml"));
            Parent root = loader.load();
            rootPane.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(AllNewsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void logout(ActionEvent event) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/User/Login.fxml"));
			rootPane.getChildren().setAll(pane);
		} catch (IOException ex) {
			
		}
    }

    

    
}
