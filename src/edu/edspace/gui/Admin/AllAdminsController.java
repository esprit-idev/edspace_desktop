/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.edspace.gui.Admin;

import edu.edspace.gui.Student.AllStudentsController;
import com.itextpdf.text.DocumentException;
import edu.edspace.entities.User;
import edu.edspace.gui.HomeBackController;
import static edu.edspace.gui.Student.AllStudentsController.validate;
import edu.edspace.services.AdminService;
import edu.edspace.services.ExcelFileService;
import edu.edspace.services.PDFService;
import edu.edspace.services.StudentService;
import edu.edspace.services.UserService;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author YOOSURF
 */
public class AllAdminsController implements Initializable {

    @FXML
    private TextField nomAdmin;
    @FXML
    private TextField prenomAdmin;
    @FXML
    private TextField emailAdmin;
    @FXML
    private TextField mdpAdmin;
    @FXML
    private Button btnaddAdmin;
    @FXML
    private TableColumn<User, String> nom;
    @FXML
    private TableColumn<User, String> prenom;
    @FXML
    private TableColumn<User, String> email;
    @FXML
    private TableView<User> tableAdmin;
    private ImageView logo;
    @FXML
    private Button btndelete;
    @FXML
    private Label gett;
    @FXML
    private ImageView delicon;
    @FXML
    private Button btnUpdate;
    @FXML
    private TableColumn<User, String> id;
    @FXML
    private ImageView update;
    @FXML
    private ImageView logo_iv;
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
    private Button btnOverview;
    @FXML
    private Button btnNews;
    @FXML
    private Button btnOrders;
    @FXML
    private Button btnCustomers;
    @FXML
    private Button btnMenus;
    @FXML
    private Button btnMatiere;
    @FXML
    private Button btnSettings;
    @FXML
    private Button btnEmploi;
    @FXML
    private Button btnSignout1;
    @FXML
    private Button btnCentrePartage;
    @FXML
    private Button btnSignout3;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Button getStudent;
    @FXML
    private ImageView stud;
    @FXML
    private Text msgemail;
    @FXML
    private Text msgmin;
    @FXML
    private Button excelbtn;
    @FXML
    private Button pdfbtn;
    @FXML
    private TextField search;
    @FXML
    private ComboBox<String> sortCB;

    /**
     * Initializes the controller class.
     */
    ObservableList<String> ss = FXCollections.observableArrayList();
    @FXML
    private ImageView excel_iv;
    @FXML
    private ImageView pdf_iv;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         ss.add("Par nom");
      ss.add("Par email");
      sortCB.setItems(ss);
      
        AdminService SS= new AdminService();
       SS.listAdmin() ;
         ObservableList<User> list = SS.listAdmin();
        
        nom.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
        prenom.setCellValueFactory(new PropertyValueFactory<User, String>("prenom"));
        email.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
        id.setCellValueFactory(new PropertyValueFactory<User, String>("id"));
        tableAdmin.setItems(list);
         
         FilteredList<User> filteredData= new FilteredList<>(list,b->true);
      search.textProperty().addListener((observable,oldValue,newValue)->{
     filteredData.setPredicate(blogs->{
     if(newValue.isEmpty()||  newValue==null){
     return true;
    }
         String searchkeyword=newValue.toLowerCase();
         if(blogs.getUsername().toLowerCase().indexOf(searchkeyword)> -1){
         return true;
               }else if (blogs.getPrenom().toLowerCase().indexOf(searchkeyword)> -1){
        return true;
        }else
        return false;
             });
          });
      SortedList<User>sortedData=new SortedList<>(filteredData);
       sortedData.comparatorProperty().bind(tableAdmin.comparatorProperty());
         tableAdmin.setItems(sortedData);
        
        /* File fileLogo = new File("images/logo1.png");
        Image logoI = new Image(fileLogo.toURI().toString());
        logo.setImage(logoI);*/
        File filedel = new File("images/delete.png");
        Image del = new Image(filedel.toURI().toString());
        delicon.setImage(del);
         File fileU = new File("images/update.png");
        Image updat = new Image(fileU.toURI().toString());
         update.setImage(updat);
         initImages();
    }    

    @FXML
    private void Add(ActionEvent event) {
       Boolean error = false;   
        if(nomAdmin.getText().equals("")|| prenomAdmin.getText().equals("")|| emailAdmin.getText().equals("")) {
          Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Fill All DATA");
            alert.showAndWait();
        error = true;
        }
       
        if (mdpAdmin.getText().length() < 8) {
            msgmin.setVisible(true);
            error = true;
        } 
        if (!validate(emailAdmin.getText())) {
              //msgemvid.setVisible(false);
                msgemail.setVisible(true);
                error = true;
            }
        
        if (!error) {
    User stu = new User();
    // stu.setId(Integer.parseInt(SId.getText()));
     stu.setPrenom(nomAdmin.getText());
     stu.setUsername(prenomAdmin.getText());
     stu.setEmail(emailAdmin.getText());
     stu.setPassword(mdpAdmin.getText());
    AdminService SS= new AdminService();
       
   // SS.AjouterUser(stu);
    SS.ajouterAdmin(stu);
    
    SS.listAdmin() ;
         ObservableList<User> list = SS.listAdmin();
        
        nom.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
        prenom.setCellValueFactory(new PropertyValueFactory<User, String>("prenom"));
        email.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
        tableAdmin.setItems(list);
    }}
    
     public static boolean validate(String email) {
        final String EMAIL_VERIFICATION = "^([\\w-\\.]+)@([\\w\\.]+)\\.([a-z]){2,}$";
        Pattern pattern = Pattern.compile(EMAIL_VERIFICATION);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @FXML
    private void delete(ActionEvent event) {
        int n = JOptionPane.showConfirmDialog(null,"Êtes-vous sûr de vouloir supprimer?","Confirm Dialog",JOptionPane.YES_NO_OPTION);
if(n == JOptionPane.YES_OPTION)
{
//****

        AdminService SS= new AdminService();
            SS.supprimerAdmin(gett.getText());
            
            SS.listAdmin() ;
         ObservableList<User> list = SS.listAdmin();
        
        nom.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
        prenom.setCellValueFactory(new PropertyValueFactory<User, String>("prenom"));
        email.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
        tableAdmin.setItems(list); 
    }}

    @FXML
    private void handleMouseAction(MouseEvent event) {
        
        User blog = tableAdmin.getSelectionModel().getSelectedItem();
      String s = String.valueOf(blog.getId()) ;
       // gett.setText(blog.getUsername());
        gett.setText(s);
       nomAdmin.setText(blog.getUsername());
       prenomAdmin.setText(blog.getPrenom());
       emailAdmin.setText(blog.getEmail());
       mdpAdmin.setText(blog.getPassword());
    }

    @FXML
    private void Update(ActionEvent event) {
         Boolean error = false;   
        if(nomAdmin.getText().equals("")|| prenomAdmin.getText().equals("")|| emailAdmin.getText().equals("")) {
          Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Fill All DATA");
            alert.showAndWait();
        error = true;
        }
        if (!error) {
         int n = JOptionPane.showConfirmDialog(null,"Êtes-vous sûr de vouloir modifier","Confirm Dialog",JOptionPane.YES_NO_OPTION);
if(n == JOptionPane.YES_OPTION)
{
        User stu = new User();
    // stu.setId(Integer.parseInt(SId.getText()));
     stu.setPrenom(nomAdmin.getText());
     stu.setUsername(prenomAdmin.getText());
     stu.setEmail(emailAdmin.getText());
     stu.setPassword(mdpAdmin.getText());
                AdminService SS= new AdminService();
                SS.updateAdmin(stu, gett.getText());
                
                 ObservableList<User> list = SS.listAdmin();
        
        nom.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
        prenom.setCellValueFactory(new PropertyValueFactory<User, String>("prenom"));
        email.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
        tableAdmin.setItems(list); 

    }}}
   /* static int existe(User stu){
  
   if(emailAdmin.getText().equals(stu.getEmail())){
       //System.out.println("La valeur recherchée n'existe pas");
       return 0;}
   else {
       //retourner la position courante
       return 1;}
 }*/
   

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
        
        File fileStu = new File("images/student.png");
        Image Stu = new Image(fileStu.toURI().toString());
        
        File fileex = new File("images/excel.png");
        Image iex = new Image(fileex.toURI().toString());
        
        File filepdf = new File("images/pdf1.png");
        Image ipdf = new Image(filepdf.toURI().toString());
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
        stud.setImage(Stu);
        excel_iv.setImage(iex);
        pdf_iv.setImage(ipdf);
    }


    

    @FXML
    private void users(ActionEvent event) {
        try {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/AllAdmins.fxml"));
            Parent root = loader.load();
            rootPane.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(HomeBackController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void getStudents(ActionEvent event) {
        try {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/Student/AllStudents.fxml"));
            Parent root = loader.load();
            rootPane.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(HomeBackController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void exportExcel(ActionEvent event) {
         ExcelFileService EX = new ExcelFileService();
  // Stage primaryStage = null;
  //  EX.start(primaryStage);
    EX.generateExcelAdmin();
    }

    @FXML
    private void exportPdf(ActionEvent event) {
        
        try {
            PDFService PS = new PDFService();
            try {
                PS.liste_admins();
            } catch (IOException ex) {
                Logger.getLogger(AllStudentsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (DocumentException ex) {
            Logger.getLogger(AllStudentsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void trier(ActionEvent event) {
         AdminService SS = new AdminService();
         if(sortCB.getValue().equals("Par nom")){
            ObservableList<User> tri1=FXCollections.observableArrayList();
            tri1=FXCollections.observableArrayList(SS.sortByNom());
            tableAdmin.setItems(tri1);
            
        }
        else if(sortCB.getValue().equals("Par email")){
            ObservableList<User> tri2=FXCollections.observableArrayList();
            tri2=FXCollections.observableArrayList(SS.sortByEmail());
            tableAdmin.setItems(tri2);
        }
        
    }

    private void getDashboardView(MouseEvent event) { 

        try { 

            AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/HomeBack.fxml")); 

            rootPane.getChildren().setAll(pane); 

        } catch (IOException ex) { 

            ex.printStackTrace(); 

        } 

    } 

 

//TABLEAU D’AFFICHAGE 

    private void getNewsView(MouseEvent event) { 

        try { 

            AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/news/allNews.fxml")); 

            rootPane.getChildren().setAll(pane); 

        } catch (IOException ex) { 

            ex.printStackTrace(); 

        } 

    } 


 

//NIVEAU D’ETUDES 

private void getNiveaux(MouseEvent event) { 

try { 

FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/Niveau/AllNiveau.fxml")); 

Parent root = loader.load(); 

rootPane.getScene().setRoot(root); 

} catch (IOException ex) { 

ex.printStackTrace(); 

} 

} 

 

 //CLASSES 

private void getClasses(MouseEvent event) { 

try { 

FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/Classe/AllClasses.fxml")); 

Parent root = loader.load(); 

rootPane.getScene().setRoot(root); 

} catch (IOException ex) { 

ex.printStackTrace(); 

} 

} 

 

//MATIERES 

  private void getAllMatieresView(MouseEvent event) { 

        try { 

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/matiere/MatieresList.fxml")); 

            Parent root = loader.load(); 

            rootPane.getScene().setRoot(root); 

        } catch (IOException ex) { 

            ex.printStackTrace(); 

        } 

    } 

 

//CLUBS UNIVERSITAIRES 

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

  

//OFFRES D’EMPLOI 

    @FXML 
 private void getEmploiView(MouseEvent event) { 

        try { 

            AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/emploi/allEmploi.fxml")); 

            rootPane.getChildren().setAll(pane); 

        } catch (IOException ex) { 

            ex.printStackTrace(); 

        } 

    } 

 

  //FORUM 

private void getForum(MouseEvent event) { 

        try { 

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/ThreadList.fxml")); 

            Parent root = loader.load(); 

            rootPane.getScene().setRoot(root); 

        } catch (IOException ex) { 

            ex.printStackTrace(); 

        }        

    } 

     

//CENTRE DE PARTAGE 

    @FXML 
private void getAllDocsView(MouseEvent event) { 

        try { 

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/document/DocsList.fxml")); 

            Parent root = loader.load(); 

            rootPane.getScene().setRoot(root); 

        } catch (IOException ex) { 

          ex.printStackTrace(); 

        } 

    } 

 

//LOGOUT 

@FXML 
 private void Dec(ActionEvent event) { 

         UserService US = new UserService(); 

         US.logout(); 

         FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/User/Login.fxml")); 

         try { 

         Parent root = loader.load(); 

         rootPane.getScene().setRoot(root); 

     } catch (IOException ex) { 

        ex.printStackTrace(); 

     } 

    } 

    @FXML
    private void getDashboardView(ActionEvent event) {
         try { 

            AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/HomeBack.fxml")); 

            rootPane.getChildren().setAll(pane); 

        } catch (IOException ex) { 

            ex.printStackTrace(); 

        } 
    }

    @FXML
    private void getNewsView(ActionEvent event) {
        try { 

            AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/news/allNews.fxml")); 

            rootPane.getChildren().setAll(pane); 

        } catch (IOException ex) { 

            ex.printStackTrace(); 

        } 
    }

    @FXML
    private void getNiveaux(ActionEvent event) {
        try { 

FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/Niveau/AllNiveau.fxml")); 

Parent root = loader.load(); 

rootPane.getScene().setRoot(root); 

} catch (IOException ex) { 

ex.printStackTrace(); 

} 
    }

    @FXML
    private void getClasses(ActionEvent event) {
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

            ex.printStackTrace(); 

        } 
    }

    @FXML
    private void getEmploiView(ActionEvent event) {
        try { 

            AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/emploi/allEmploi.fxml")); 

            rootPane.getChildren().setAll(pane); 

        } catch (IOException ex) { 

            ex.printStackTrace(); 

        } 
    }

    @FXML
    private void getForum(ActionEvent event) {
        try { 

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/ThreadList.fxml")); 

            Parent root = loader.load(); 

            rootPane.getScene().setRoot(root); 

        } catch (IOException ex) { 

            ex.printStackTrace(); 

        }        
    }

    @FXML
    private void getAllDocsView(ActionEvent event) {
        try { 

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/document/DocsList.fxml")); 

            Parent root = loader.load(); 

            rootPane.getScene().setRoot(root); 

        } catch (IOException ex) { 

          ex.printStackTrace(); 

        } 
    }

    
}



