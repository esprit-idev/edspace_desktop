/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.edspace.gui.Niveau;

import edu.edspace.entities.Niveau;
import edu.edspace.gui.news.AllNewsController;
import edu.edspace.gui.news.allCategoryNewsController;
import edu.edspace.services.ClasseService;
import edu.edspace.services.ClubService;
import edu.edspace.services.MessageService;
import edu.edspace.services.NiveauService;
import edu.edspace.services.UserService;
import edu.edspace.utils.MyConnection;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
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
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author aa
 */
public class AllNiveauController implements Initializable {

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
    private VBox pnItems;
    @FXML
    private ImageView search_iv;
    @FXML
    private TableColumn<Niveau, String> niveau;
    @FXML
    private TableView<Niveau> niveauTable;

    
    Connection connection = null;
    ObservableList<Niveau> n = FXCollections.observableArrayList();
    @FXML
    private TableColumn<Niveau, Integer> nbclasse;
    int myIndex;
    @FXML
    private Label nbNiveau;
    @FXML
    private Label nbc;
    @FXML
    private Label nbclub;
    @FXML
    private Button supp;
    
    private String id;
    @FXML
    private Button btnupdate;
    @FXML
    private TextField Nniveau;
    @FXML
    private Button valider;
    @FXML
    private Pane updatepane;
    @FXML
    private ImageView logo_iv11;
    @FXML
    private Button btnajout;
    @FXML
    private Label clabel;
    @FXML
    private Pane autopane;
    @FXML
    private TextField nbc1;
    @FXML
    private Button btnauto;
    @FXML
    private Label clabel1;
    @FXML
    private ImageView logo_iv111;
    @FXML
    private CheckBox un;
    @FXML
    private CheckBox deux;
    @FXML
    private CheckBox trois;
    @FXML
    private CheckBox quatre;
    @FXML
    private CheckBox cinq;
    @FXML
    private CheckBox six;
    @FXML
    private TextField quatreN;
    @FXML
    private TextField cinqN;
    @FXML
    private TextField sixN;
    @FXML
    private TextField nbc2;
    @FXML
    private TextField nbc3;
    @FXML
    private TextField nbc4;
    @FXML
    private TextField nbc5;
    @FXML
    private TextField nbc6;
    @FXML
    private Button btnauto1;
    @FXML
    private TextField search;
    @FXML
    private Button annuler;
    @FXML
    private Button btnautovalider;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initImages();
     refresh();
        niveauTable.setRowFactory(tv ->{
            TableRow<Niveau> myRow=new TableRow<>();
            myRow.setOnMouseClicked(event ->{
                if(event.getClickCount()==1 && (!myRow.isEmpty())){
                    myIndex=niveauTable.getSelectionModel().getSelectedIndex();
                   // System.out.println(String.valueOf(niveauTable.getItems().get(myIndex).getId()));
                    supp.setDisable(false);
                     btnupdate.setDisable(false);
                    id=String.valueOf(niveauTable.getItems().get(myIndex).getId());
                }
            });
            return myRow;
        });
        
        
        // TODO
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

    public void setnbNiveau(){
        NiveauService ns=new NiveauService();
        ClasseService cs=new ClasseService();
        ClubService cb=new ClubService();
        nbNiveau.setText(""+ns.listeNiveaux().size());
        nbc.setText(""+cs.listeClasses().size());
        nbclub.setText(""+cb.displayClub().size());
    }

    @FXML
    private void suppN(ActionEvent event) {
        
                 Alert alert = new Alert(AlertType.CONFIRMATION);
      alert.setTitle("Supprimer Niveau");
      alert.setHeaderText("Voulez-vous vraiment supprimer ce niveau ?");

               Optional<ButtonType> option = alert.showAndWait();

                   if (option.get() == null) {
     
      } else if (option.get() == ButtonType.OK) {
          NiveauService ns=new NiveauService();
                   Niveau d=new Niveau();
                   
                  d=ns.getOneById(String.valueOf(niveauTable.getItems().get(myIndex).getId()));
                   ns.supprimerNiveau(d);
                   
                   refresh();
      } else if (option.get() == ButtonType.CANCEL) {
         refresh();
      }
                
          
        
    }

    
    public void refresh(){
        updatepane.setVisible(false);
         setnbNiveau();
        connection = MyConnection.getInstance().getCnx();
        NiveauService ns=new NiveauService();
        n.clear();
        n.addAll(ns.listeNiveaux());
        niveau.setCellValueFactory(new PropertyValueFactory<Niveau,String>("id"));
        nbclasse.setCellValueFactory(new PropertyValueFactory<Niveau,Integer>("nbClasse"));
        niveauTable.setItems(n);
             supp.setDisable(true);
     btnupdate.setDisable(true);
       autopane.setVisible(false);
    }

    String v1="";
    @FXML
    private void updateN(ActionEvent event) {
     //   btnupdate.setVisible(false);
     clabel.setText("UPDATE niveau");
     valider.setText("Update");
     Nniveau.setText(String.valueOf(niveauTable.getItems().get(myIndex).getId()));
     v1=String.valueOf(niveauTable.getItems().get(myIndex).getId());
        updatepane.setVisible(true);
    }

    @FXML
    private void updated(ActionEvent event) {
         if(!testvide(Nniveau.getText())){
        	 Boolean flag = Character.isDigit(Nniveau.getText().charAt(0));
		         if(flag) {
                               NiveauService ns=new NiveauService();
            
             if(valider.getText().equals("Update")){
     String v2=Nniveau.getText();
     
    // ns.modifierNiveau(v1,v2);
    
    Niveau t=new Niveau("4T");
    ns.modifierNiveau(v1, v2);
     Nniveau.setText("");
             }
            if(valider.getText().equals("Ajouter")){
                Niveau temp=new Niveau(Nniveau.getText());
                ns.ajouterNiveau(temp);
                 Nniveau.setText("");
            }
             
                         refresh();
        updatepane.setVisible(false);
                         }
		        	
		          else {
                              Alert alert = new Alert(AlertType.ERROR);

alert.setTitle("Error niveau");
alert.setHeaderText("Niveau must start with a number ,'"+ Nniveau.getText().charAt(0)+"' is a letter");

alert.showAndWait();
		            System.out.println("'"+ Nniveau.getText().charAt(0)+"' is a letter");
		         }
    }
            Nniveau.setText("");
        
    }

    @FXML
    private void ajouterN(ActionEvent event) {
        clabel.setText("Ajouter un niveau");
        valider.setText("Ajouter");
        updatepane.setVisible(true);
    }
    
    public boolean testvide(String id ){
        if(id.trim().equals("")){
            Alert alert = new Alert(AlertType.ERROR);

alert.setTitle("Error niveau");
alert.setHeaderText("Niveau ne peut pas être vide");

alert.showAndWait();
return true;
            
        }
        return false;
    }

    @FXML
    private void autofill(ActionEvent event) {
        String[] ln = new String[7];
        int[] lc=new int[7];
        lc[0]=0;
         ln[0]="";
        if(un.isSelected()){
            ln[1]="1";
           lc[1]=Integer.parseInt(nbc1.getText());
        //System.out.println(un.getText());
        }else
            ln[1]="";
          if(deux.isSelected()){
               ln[2]="1";
               lc[2]=Integer.parseInt(nbc2.getText());
        }else
              ln[2]="";
          if(trois.isSelected()){
               ln[3]="1";
               lc[3]=Integer.parseInt(nbc3.getText());
        }else
              ln[3]="";
          if(quatre.isSelected()){
               ln[4]="4"+quatreN.getText();
               lc[4]=Integer.parseInt(nbc4.getText());
        }else
              ln[4]="";
          if(cinq.isSelected()){
              lc[5]=Integer.parseInt(nbc5.getText());
               ln[5]="5"+cinqN.getText();
        }else
              ln[5]="";
          if(six.isSelected()){
              lc[6]=Integer.parseInt(nbc6.getText());
               ln[6]="6"+sixN.getText();
        }else
              ln[6]="";
          
          
          NiveauService ns=new NiveauService();
         ns.autofillA(ln, lc);
          refresh();
     
         
        
    }

    @FXML
    private void activeAuto(ActionEvent event) {
        autopane.setVisible(true);
    }

    @FXML
    private void cancelAuto(ActionEvent event) {
        refresh();
        
    }

    
    
    @FXML
    private void find(KeyEvent event) {
       // System.out.println("test");
        String s=search.getText();
        MessageService ms=new MessageService();
        
        niveauTable.getItems().stream()
    .filter(item -> ms.mots1(item.getId(),s ))
    .findAny()
    .ifPresent(item -> {
        niveauTable.getSelectionModel().select(item);
        niveauTable.scrollTo(item);
    });
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
    
    
    
}
