/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.edspace.gui.Classe;

import com.mysql.jdbc.Connection;
import edu.edspace.entities.Classe;
import edu.edspace.entities.Niveau;
import edu.edspace.entities.User;
import edu.edspace.services.ClasseService;
import edu.edspace.services.ClubService;
import edu.edspace.services.NiveauService;
import edu.edspace.utils.MyConnection;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
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
    private TextField niveauA;
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
    
   String idet;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initImages();
        refresh();
row();
        btnaddET.setVisible(false);
          
          
        
        
    }    
    
    
    
    private ObservableList<String> items = FXCollections.observableArrayList();
     private ObservableList<String> items2 = FXCollections.observableArrayList();
    
    int cClasse;
    
    public void row(){
        pnlOverview.setOnMouseClicked(
        tv ->{
           pane1.setVisible(false);
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
                     listeET.setItems(items);
                     List<User> list=new ArrayList<>();
                     list.addAll(cs.listUserClasse(cClasse));
                     for (User temp : list) {
                         System.out.println(temp);
            items.add(temp.getUsername()+" "+temp.getPrenom());
        }
                     
                               listeet2.setItems(items2);
                     List<User> list2=new ArrayList<>();
                    
                      list2.addAll(cs.listUserClasse(cClasse));
                     for (User temp : list2) {
                         System.out.println(temp);
            items2.add(temp.getEmail());
        }
                     
                     
                      pane1.setVisible(true);
                    id=table.getItems().get(myIndex).getId();
                    
                  
                    
                }
            });
            return myRow;
        });
        // TODO
        
        
        
        
        
        
        listeet2.setCellFactory(tv ->{
           // TableRow<Classe> myRow=new TableRow<>();
            ListCell<String> myRow= new ListCell<>();
           
             
            
           
            myRow.setOnMouseClicked(event ->{
                if(event.getClickCount()==1 && (!myRow.isEmpty())){
                    myIndex2=listeet2.getSelectionModel().getSelectedIndex();
                    idet=listeet2.getItems().get(myIndex2);
                    System.out.println(idet);
                   // System.out.println(String.valueOf(niveauTable.getItems().get(myIndex).getId()));
                    valideret.setDisable(false);
           
                    
                  
                    
                }
            });
            return myRow;
        });
                
              //  .setRowFactory();
              
              
              
        
      
         
    
    }
    
    ObservableList<Classe> n = FXCollections.observableArrayList();
    
    public void refresh(){
        panel2.setVisible(false);
        paneAU.setVisible(false);
        update.setDisable(true);
        btnaddET.setDisable(true);
        pane1.setVisible(false);
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
    private void getNewsView(MouseEvent event) {
    }

    @FXML
    private void getAllMatieresView(MouseEvent event) {
    }

    @FXML
    private void getAllDocsView(MouseEvent event) {
    }

    @FXML
    private void AddClasse(ActionEvent event) {
        title.setText("AJOUTER UNE CLASSE");
        classeA.setText("");
        niveauA.setText("");
         paneAU.setVisible(true);
    }

    @FXML
    private void UpdateClasse(ActionEvent event) {
        title.setText("UPDATE CLASSE");
        Classe x=new Classe();
        x=cs.getOneById(cClasse);
        classeA.setText(x.getClasse());
        niveauA.setText(x.getNiveau().getId());
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
        
        if(!Testvide(classeA.getText(),niveauA.getText())){
            Classe x=new Classe();
            Niveau y=new Niveau();
            y.setId(niveauA.getText());
            x.setClasse(classeA.getText());
            x.setNiveau(y);
        
        if(title.getText().equals("UPDATE CLASSE")){
            
            
            cs.modifierClasse(x);
           
            
        }else{
            cs.ajouterClasse(x);
            
        }
         refresh();
        }
        
        
        
    }

    @FXML
    private void annulerr(ActionEvent event) {
        paneAU.setVisible(false);
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
    }

    @FXML
    private void showAddet(ActionEvent event) {
        panel2.setVisible(true);
    }
    


    
}
