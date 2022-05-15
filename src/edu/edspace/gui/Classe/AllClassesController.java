/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.edspace.gui.Classe;

import com.mysql.jdbc.Connection;
import edu.edspace.entities.Classe;
import edu.edspace.entities.Niveau;
import edu.edspace.entities.User;
import edu.edspace.gui.news.AllNewsController;
import edu.edspace.gui.news.allCategoryNewsController;
import edu.edspace.services.ClasseService;
import edu.edspace.services.ClubService;
import edu.edspace.services.NiveauService;
import edu.edspace.services.UserService;
import edu.edspace.utils.MyConnection;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author aa
 */
public class AllClassesController implements Initializable {

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
    private Button btnSettings;
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
    private Pane pnlCustomer;
    @FXML
    private Pane pnlOrders;
    @FXML
    private Pane pnlMenus;
    @FXML
    private Pane pnlOverview;
    @FXML
    private Label nbn;
    @FXML
    private Label nbc;
    @FXML
    private Label nbclub;
    @FXML
    private TableView<Classe> table;
    @FXML
    private TableColumn<Classe, String> niveau;
    @FXML
    private TableColumn<Classe, String> classe;
    @FXML
    private TableColumn<Classe, String> nbetudaint;
    @FXML
    private ImageView search_iv;

    Connection connection=null;
    NiveauService ns=new NiveauService();
    ClasseService cs=new ClasseService();
    @FXML
    private Button ajouter;
    @FXML
    private Button update;
    @FXML
    private Button supprimer;
      
    int myIndex;
    int myIndex2;
    int id;
    @FXML
    private TableColumn<Classe, String> idclasse;
    @FXML
    private Pane pane1;
    @FXML
    private ListView<String> listeET;
    @FXML
    private Pane paneAU;
    @FXML
    private Text title;
    @FXML
    private ImageView logo_iv1;
    @FXML
    private TextField classeA;
    @FXML
    private Button validerr;
    @FXML
    private Button annuler;
    @FXML
    private ListView<String> listeet2;
    @FXML
    private Button valideret;
    @FXML
    private Button annuler2;
    @FXML
    private Pane panel2;
    @FXML
    private Button btnaddET;
    @FXML
    private TextField emailET;
    
        @FXML
    private Label texterreur;
            @FXML
    private TextField search1;
            
    @FXML
    private ComboBox<String> combo;
    @FXML
    private ComboBox<String> combo2;
    
        private ObservableList<String> items = FXCollections.observableArrayList();
     private ObservableList<String> items2 = FXCollections.observableArrayList();
   String idet;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initImages();
        refresh();
row();
        btnaddET.setVisible(true);
          
          
        
        
    }    
    
    
    

    
    int cClasse;
    
    public void row(){
        pnlOverview.setOnMouseClicked(
        tv ->{
         //  pane1.setVisible(false);
        });
                 table.setRowFactory(tv ->{
            TableRow<Classe> myRow=new TableRow<>();
           
             
            
           
            myRow.setOnMouseClicked(event ->{
                if(event.getClickCount()==1 && (!myRow.isEmpty())){
                    myIndex=table.getSelectionModel().getSelectedIndex();
                    cClasse=table.getItems().get(myIndex).getId();
                   // System.out.println(String.valueOf(niveauTable.getItems().get(myIndex).getId()));
                    supprimer.setDisable(false);
                     update.setDisable(false);
                     btnaddET.setDisable(false);
                    // listeET.setItems(items);
                     
                     
                   //   pane1.setVisible(true);
                    id=table.getItems().get(myIndex).getId();
                    
                  
                    
                }
            });
            return myRow;
        });
        // TODO
        
        
        
        
        
        
                
              //  .setRowFactory();
              
              
              
        
      
         
    
    }
    
    ObservableList<Classe> n = FXCollections.observableArrayList();
    
    public void refresh(){
        validerr.setDisable(true);
        combo.valueProperty().set(null);
        combo2.valueProperty().set(null);
        email="";
        valideret.setDisable(true);
        panel2.setVisible(false);
        paneAU.setVisible(false);
        update.setDisable(true);
        combo.setItems(list);
        btnaddET.setDisable(true);
//        pane1.setVisible(false);
                supprimer.setDisable(true);
        MyConnection.getInstance().getCnx();
        ClubService club=new ClubService();
        
        nbn.setText(""+ns.listeNiveaux().size());
                nbc.setText(""+cs.listeClasses().size());
                nbclub.setText(""+club.displayClub().size());
                n.clear();
        n.addAll(cs.listeClasses());
        niveau.setCellValueFactory(new PropertyValueFactory<Classe,String>("idn"));
        classe.setCellValueFactory(new PropertyValueFactory<Classe,String>("classe"));
        nbetudaint.setCellValueFactory(new PropertyValueFactory<Classe,String>("nbet"));
        idclasse.setCellValueFactory(new PropertyValueFactory<Classe,String>("id"));
        
        table.setItems(n);
    }

    @FXML
    private void handleClicks(ActionEvent event) {
    }

 @FXML
    private void getNewsView(MouseEvent event){
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/news/allNews.fxml"));
			rootPane.getChildren().setAll(pane);
		} catch (IOException ex) {
			Logger.getLogger(AllNewsController.class.getName()).log(Level.SEVERE, null, ex);
		}
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
    @FXML
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
    @FXML
    private void getDashboardView(MouseEvent event){
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/HomeBack.fxml"));
			rootPane.getChildren().setAll(pane);
		} catch (IOException ex) {
			Logger.getLogger(allCategoryNewsController.class.getName()).log(Level.SEVERE, null, ex);
		}
    }
    @FXML
    private void getNiveauView(MouseEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/Niveau/AllNiveau.fxml")); 
            Parent root = loader.load(); 
            rootPane.getScene().setRoot(root); 
		} catch (IOException ex) {
			ex.printStackTrace(); 
		}
    }
    @FXML
    private void getClassesView(MouseEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/Classe/AllClasses.fxml")); 
            Parent root = loader.load(); 
            rootPane.getScene().setRoot(root); 
		} catch (IOException ex) {
			ex.printStackTrace(); 
		}
    }           
    @FXML
    private void getAllMatieresView(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/matiere/MatieresList.fxml"));
            Parent root = loader.load();
            rootPane.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(AllNewsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    private void displayClubs(ActionEvent event) {
        try {

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/Admin/AllAdmins.fxml"));
            Parent root = loader.load();
            club_iv.getScene().setRoot(root);
        } catch (IOException ex) {
            ex.printStackTrace();
        } 
    }
    @FXML
    private void logout(MouseEvent event){
        UserService US = new UserService();
        US.logout();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/User/Login.fxml"));
        try {
        Parent root = loader.load();
        rootPane.getScene().setRoot(root); 
		} catch (IOException ex) {		
		}
    }  
    
    @FXML
    private void AddClasse(ActionEvent event) {
        title.setText("AJOUTER UNE CLASSE");
        classeA.setText("");
        selected="";
         combo2.valueProperty().set(null);
        listniveau =FXCollections.observableArrayList(cs.niveauToString(ns.listeNiveaux()));
         combo2.setItems(listniveau);
       
         paneAU.setVisible(true);
    }

    @FXML
    private void UpdateClasse(ActionEvent event) {
        title.setText("UPDATE CLASSE");
        Classe x=new Classe();
        x=cs.getOneById(cClasse);
        classeA.setText(x.getClasse());
                listniveau =FXCollections.observableArrayList(cs.niveauToString(ns.listeNiveaux()));
                combo2.setItems(listniveau);
                int i = 0;
                

              combo2.getSelectionModel().select(x.getNiveau().getId());
            selected=combo2.getValue();

         paneAU.setVisible(true);
        
    }

    @FXML
    private void DeleteClasse(ActionEvent event) {
        
               Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
      alert.setTitle("Supprimer cette classe");
      alert.setHeaderText("Voulez-vous vraiment supprimer cette classe ?");

               Optional<ButtonType> option = alert.showAndWait();

                   if (option.get() == null) {
                   }
                   if (option.get() == ButtonType.OK) {
        cs.supprimerClasse(cClasse);
        refresh();
                           }
                   
                     if (option.get() == ButtonType.CANCEL) {
         //refresh();
      }
                   }
    
    
    
    public boolean Testvide(String id,String id1){
         if(id.trim().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);

alert.setTitle("Erreur Classe");
alert.setHeaderText("Classe ne peut pas être vide");

alert.showAndWait();
return true;
            
        }
         if(id1.trim().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);

alert.setTitle("Error niveau");
alert.setHeaderText("Niveau ne peut pas être vide");

alert.showAndWait();
return true;
            
        }
        return false;
        
    }

    @FXML
    private void validerua(ActionEvent event) {
        
        if(!Testvide(classeA.getText(),selected)){
            Classe x=new Classe();
            Niveau y=new Niveau();
            y.setId(selected);
            x.setClasse(classeA.getText());
            x.setId(id);
            
            x.setNiveau(y);
        
        if(title.getText().equals("UPDATE CLASSE")){
            
            
            
            if(!cs.exist(classeA.getText(), selected)){
                 cs.modifierClasse(x);
            }
            
             else{
             Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
      alert.setTitle("Erreur");
      alert.setHeaderText("Classe deja exist");

               Optional<ButtonType> option = alert.showAndWait();
                   if (option.get() == null) {
                   }
                   if (option.get() == ButtonType.OK) {
    
        refresh();
            }
            }
           
            
        }
        
        else{

            if (cs.checkexist(classeA.getText(),selected).getId()==-1){
                
                if(!ns.getOneById(selected).getId().equals("-1")){
            cs.ajouterClasse(x);
                }
                else{
                                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
      alert.setTitle("Erreur");
      alert.setHeaderText("Niveau n'exist pas");

               Optional<ButtonType> option = alert.showAndWait();
                   if (option.get() == null) {
                   }
                   if (option.get() == ButtonType.OK) {
    
    
            }
                    
                }
            
            }
            else{
                             Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
      alert.setTitle("Erreur");
      alert.setHeaderText("Classe deja exist");

               Optional<ButtonType> option = alert.showAndWait();
                   if (option.get() == null) {
                   }
                   if (option.get() == ButtonType.OK) {
    
    
            }
            }
        }
         refresh();
        }
        
        
        
    }

    @FXML
    private void annulerr(ActionEvent event) {
        paneAU.setVisible(false);
        btnaddET.setDisable(false);
        panel2.setVisible(false);
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
    private void validerEt(ActionEvent event) {
        
        
        
        User x=cs.emailStudent(email, cClasse);

        refresh();
        

        }
            
    

    @FXML
    private void showAddet(ActionEvent event) {
        combo.setCache(false);
        list =FXCollections.observableArrayList(cs.userToEmail(cs.listUserNoClasse(cClasse)));
        combo.setItems(list);
       // System.out.println(cs.userToEmail(cs.listUserNoClasse(cClasse))[0]);
        panel2.setVisible(true);
    }
    
public static boolean isValid(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                            "[a-zA-Z0-9_+&*-]+)*@" +
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                            "A-Z]{2,7}$";
                              
        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }



@FXML
    private void search(KeyEvent event) {
        String s = search1.getText();

        table.getItems().clear();
       // ChequierCrud cc = new ChequierCrud();
        List<Classe> c = cs.listeClasses();
        for (int i = 0; i < c.size(); i++) {
            if (mot(c.get(i).getClasse(), s)) {
                n.addAll(c.get(i));
            }
        }
   
        
                niveau.setCellValueFactory(new PropertyValueFactory<Classe,String>("idn"));
        classe.setCellValueFactory(new PropertyValueFactory<Classe,String>("classe"));
        nbetudaint.setCellValueFactory(new PropertyValueFactory<Classe,String>("nbet"));
        idclasse.setCellValueFactory(new PropertyValueFactory<Classe,String>("id"));
        
        table.setItems(n);
    }
    
    public boolean mot(String s1, String s2) {
        String[] l1 = null;

        l1 = s1.split(" ");
        String[] l2 = s2.split("(?!^)");
        for (int i = 0; i < l1.length; i++) {
            if (l1[i].toUpperCase().contains(s2.toUpperCase())) {
                return true;
            }
        }
        return false;
    }
    
    
    ObservableList<String> list =FXCollections.observableArrayList(cs.userToEmail(cs.listUserNoClasse(cClasse)));
    String email;
    
    @FXML
    void selectEmail(ActionEvent event) {
        email=combo.getValue();
          valideret.setDisable(false);
      

    }
    ObservableList<String> listniveau =FXCollections.observableArrayList(cs.niveauToString(ns.listeNiveaux()));
    String selected;
    @FXML
    void selectNiveau(ActionEvent event) {
                selected=combo2.getValue();
          validerr.setDisable(false);

    }
}
