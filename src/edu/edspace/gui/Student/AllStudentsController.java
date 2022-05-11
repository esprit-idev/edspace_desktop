/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.edspace.gui.Student;

import com.itextpdf.text.DocumentException;
import com.mysql.jdbc.Connection;
import edu.edspace.entities.Session;
import edu.edspace.entities.User;
import edu.edspace.gui.HomeBackController;
import edu.edspace.gui.news.AllNewsController;
import edu.edspace.gui.news.allCategoryNewsController;
import edu.edspace.services.ExcelFileService;
import edu.edspace.services.PDFService;
import edu.edspace.services.StudentService;
import edu.edspace.services.UserService;
import edu.edspace.utils.MyConnection;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;
import java.util.Collections;
import javafx.collections.FXCollections;

/**
 * FXML Controller class
 *
 * @author YOOSURF
 */
public class AllStudentsController implements Initializable {
 public static String compareVar ;
    @FXML
    private TableColumn<User, String> NStudent;
    @FXML
    private TableColumn<User, String> PStudent;
    @FXML
    private TableColumn<User,String> EStudent;
    @FXML
    private Button btnUpdate;

    
    Connection connection = null;
    @FXML
    private TableView<User> tableStudent;
    @FXML
    private Label gett;
    @FXML
    private ImageView icondelete;
    @FXML
    private Button btndel;
    @FXML
    private Button btnAjout;
    @FXML
    private TextField nomStudent;
    @FXML
    private TextField prenomStudent;
    @FXML
    private TextField emailStudent;
    @FXML
    private TextField mdpStudent;
    private ImageView logo;
    @FXML
    private Text msgnom;
    @FXML
    private Text msgpre;
    @FXML
    private Text msgemvid;
    @FXML
    private Text msgmdpvid;
    @FXML
    private Text invalidmsg;
    @FXML
    private Text msgmin;
    @FXML
    private ImageView update;
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
    private Button btnExport;
    @FXML
    private TextField search;
    @FXML
    private Button pdf;
    @FXML
    private ComboBox<String> sortCB;
    @FXML
    private Text testmsg;
    
    //List<User> listStudent ;
    /**
     * Initializes the controller class.
     */
    ObservableList<String> ss = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     // MyConnection.getInstance().getCnx();
        //initImages();
      ss.add("Par nom");
      ss.add("Par email");
      sortCB.setItems(ss);
        
        displayData();
        
         File filed = new File("images/delete.png");
        Image del = new Image(filed.toURI().toString());
        icondelete.setImage(del);
        /* File fileLogo = new File("images/logo1.png");
        Image logoI = new Image(fileLogo.toURI().toString());
         logo.setImage(logoI); */
          File fileU = new File("images/update.png");
        Image updat = new Image(fileU.toURI().toString());
         update.setImage(updat);
         initImages();
          
    }    

    private void getAddView(ActionEvent event) {
        
        try {
          
            Parent blog_parent = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/AddStudent.fxml"));
        Scene blog_scene = new Scene(blog_parent);
        Stage app_stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        
        app_stage.setScene(blog_scene);
        app_stage.show();
        } catch (IOException ex) {
            Logger.getLogger(AllStudentsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }

    @FXML
    private void handleMouseAction(MouseEvent event) {
        User blog = tableStudent.getSelectionModel().getSelectedItem();
        
        String s = String.valueOf(blog.getId()) ;
       // gett.setText(blog.getUsername());
        gett.setText(s);
       nomStudent.setText(blog.getUsername());
       prenomStudent.setText(blog.getPrenom());
       emailStudent.setText(blog.getEmail());
       mdpStudent.setText(blog.getPassword());
        
       // gett.setText(blog.getUsername());
    }

    @FXML
    private void btnUpdate(ActionEvent event) {
         Boolean error = false;
        if(nomStudent.getText().equals("") || prenomStudent.getText().equals("")||emailStudent.getText().equals("") || mdpStudent.getText().equals("") ) {
              
              Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("veuillez remplir tous les champs");
            alert.showAndWait();
        
             // msgnom.setVisible(true);
               error = true;
         }
         
          /* if (!validate(emailStudent.getText())) {
              //msgemvid.setVisible(false);
                mdpStudent.setVisible(true);
                error = true;
            }*/
         /* if (mdpStudent.getText().length() < 8) {
            msgmin.setVisible(true);
            error = true;
        } */
        if (!error) {
        int n = JOptionPane.showConfirmDialog(null,"Êtes-vous sûr de vouloir modifier?","Confirm Dialog",JOptionPane.YES_NO_OPTION);
        if(n == JOptionPane.YES_OPTION)
{
         User stu = new User();
    // stu.setId(Integer.parseInt(SId.getText()));
     stu.setPrenom(nomStudent.getText());
     stu.setUsername(prenomStudent.getText());
     stu.setEmail(emailStudent.getText());
     stu.setPassword(mdpStudent.getText());
                StudentService SS= new StudentService();
                SS.updateStudent(stu, gett.getText());
                
                ObservableList<User> list = SS.listStudent();
        
        NStudent.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
        PStudent.setCellValueFactory(new PropertyValueFactory<User, String>("prenom"));
        EStudent.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
        tableStudent.setItems(list);
        
        
    }}}

    private void btnDelete(ActionEvent event) {
        
            StudentService SS= new StudentService();
            SS.supprimerPersonne(gett.getText());
           
        SS.listStudent();
         ObservableList<User> list = SS.listStudent();
        
        NStudent.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
        PStudent.setCellValueFactory(new PropertyValueFactory<User, String>("prenom"));
        EStudent.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
        tableStudent.setItems(list);
           
      
    }

    @FXML
    private void del(ActionEvent event) {
         if(gett.getText().isEmpty()){
              Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Veuillez selectioner un etudiant");
            alert.showAndWait();
        //error = true;
         }
         else{
        int n = JOptionPane.showConfirmDialog(null,"Êtes-vous sûr de vouloir supprimer l'etudiant ?","Confirm Dialog",JOptionPane.YES_NO_OPTION);
        
        if(n == JOptionPane.YES_OPTION)
{
         StudentService SS= new StudentService();
        
         
            SS.supprimerPersonne(gett.getText());
           
        SS.listStudent();
         ObservableList<User> list = SS.listStudent();
        
        NStudent.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
        PStudent.setCellValueFactory(new PropertyValueFactory<User, String>("prenom"));
        EStudent.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
        tableStudent.setItems(list);
        
    }}}

    @FXML
    private void btnajout(ActionEvent event) {
         Boolean error = false;
       UserService US= new UserService();
          if(nomStudent.getText().equals("") || prenomStudent.getText().equals("")|| emailStudent.getText().equals("")) {
              
          Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs");
            alert.showAndWait();
        error = true;
        }
           final int MAX=8;
                         
// Specifying the number of uppercase letters in password
             final int MIN_Uppercase=2;
             // Specifying the minimum lowercase letters in password
             final int MIN_Lowercase=2;
             // Specifying the number of digits in a password
             final int NUM_Digits=2;
             // Specify the minimum number of special case letters
             final int Special=2;
             // Count number of uppercase letters in a password
             int uppercaseCounter=0;
             // Counter lowercase letters in a password
             int lowercaseCounter=0;
             // Count digits in a password
             int digitCounter=0;
             // count special case letters in a password
             int specialCounter=0;
             
             // Take input of password from the user
            
 System.out.println("Enter the password\n");
            
 Scanner input = new Scanner(System.in);

             // Take password input from the user and save it in the string password
            
 //String password = input.nextLine();
         String password =mdpStudent.getText() ;
             
             for (int i=0; i < password.length(); i++ ) {
                    char c = password.charAt(i);
                    if(Character.isUpperCase(c)) 
                          uppercaseCounter++;
                    else if(Character.isLowerCase(c)) 
                          lowercaseCounter++;
                    else if(Character.isDigit(c)) 
                          digitCounter++;     
                     if(c>=33&&c<=46||c==64){
                      specialCounter++;
                  }
                    
             }
             
             if (password.length() >= MAX && uppercaseCounter >= MIN_Uppercase 
&& lowercaseCounter >= MIN_Lowercase && digitCounter >= NUM_Digits && specialCounter >= Special) { 
                    System.out.println("Valid Password");
             }
             else {
   System.out.println("Your password does not contain the following:");
                    if(password.length() < MAX)
                          System.out.println(" atleast 8 characters");
                    if (lowercaseCounter < MIN_Lowercase) 
                          System.out.println("Minimum lowercase letters");
                    if (uppercaseCounter < MIN_Uppercase) 
                          System.out.println("Minimum uppercase letters");
                    if(digitCounter < NUM_Digits) 
                          System.out.println("Minimum number of numeric digits");
                    if(specialCounter < Special)
System.out.println("Password should contain at lest 3 special characters");
                    
             }
         
           if (!validate(emailStudent.getText())) {
              //msgemvid.setVisible(false);
                mdpStudent.setVisible(true);
                error = true;
            }
          
          
          if (mdpStudent.getText().length() < 8) {
            msgmin.setVisible(true);
            error = true;
        } 
         if(US.existence(emailStudent.getText())==1) {
             System.out.println("tapez un autre email");
         }
         
         /* User s = new User();
          if (emailStudent.getText().equals(s.getEmail())){
          Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Email existe deja");
            alert.showAndWait();
            System.out.println("existe");
        error = true;
          }
          */
          
   if (!error) {
    User stu = new User();
    // stu.setId(Integer.parseInt(SId.getText()));
     stu.setPrenom(prenomStudent.getText());
     stu.setUsername(nomStudent.getText());
     stu.setEmail(emailStudent.getText());
     stu.setPassword(mdpStudent.getText());
     StudentService SS = new StudentService();
   // SS.AjouterUser(stu);
    SS.ajouterStudent(stu);
    
    SS.listStudent();
         ObservableList<User> list = SS.listStudent();
        
        NStudent.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
        PStudent.setCellValueFactory(new PropertyValueFactory<User, String>("prenom"));
        EStudent.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
        tableStudent.setItems(list);
    }}
    
     public static boolean validate(String email) {
        final String EMAIL_VERIFICATION = "^([\\w-\\.]+)@([\\w\\.]+)\\.([a-z]){2,}$";
        Pattern pattern = Pattern.compile(EMAIL_VERIFICATION);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
        
    }

    private void profil(ActionEvent event) {
         try {
            /* Parent parent = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/AddStudent.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show(); */
            
            Parent blog_parent = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/Profil.fxml"));
        Scene blog_scene = new Scene(blog_parent);
        Stage app_stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        
        app_stage.setScene(blog_scene);
        app_stage.show();
        } catch (IOException ex) {
            Logger.getLogger(AllStudentsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        final Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.setResizable(true);
        alert.showAndWait();}

    
    
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
    private void getUsers(ActionEvent event) {
         try {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/Admin/AllAdmins.fxml"));
            Parent root = loader.load();
            rootPane.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(HomeBackController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void ExportExcel(ActionEvent event) {
   // ExportService EX = new ExportService();
      ExcelFileService EX = new ExcelFileService();
  // Stage primaryStage = null;
  //  EX.start(primaryStage);
    EX.generateExcelStudent();
    
    }
    
    @FXML
      public  void test(){
         
             // Specify the maximum number of letters in a password
             final int MAX=8;
                         
// Specifying the number of uppercase letters in password
             final int MIN_Uppercase=2;
             // Specifying the minimum lowercase letters in password
             final int MIN_Lowercase=2;
             // Specifying the number of digits in a password
             final int NUM_Digits=2;
             // Specify the minimum number of special case letters
             final int Special=2;
             // Count number of uppercase letters in a password
             int uppercaseCounter=0;
             // Counter lowercase letters in a password
             int lowercaseCounter=0;
             // Count digits in a password
             int digitCounter=0;
             // count special case letters in a password
             int specialCounter=0;
             
             // Take input of password from the user
            
 System.out.println("Enter the password\n");
            
 Scanner input = new Scanner(System.in);

             // Take password input from the user and save it in the string password
            
 //String password = input.nextLine();
         String password =mdpStudent.getText() ;
             
             for (int i=0; i < password.length(); i++ ) {
                    char c = password.charAt(i);
                    if(Character.isUpperCase(c)) 
                          uppercaseCounter++;
                    else if(Character.isLowerCase(c)) 
                          lowercaseCounter++;
                    else if(Character.isDigit(c)) 
                          digitCounter++;     
                     if(c>=33&&c<=46||c==64){
                      specialCounter++;
                  }
                    
             }
             
             if (password.length() >= MAX && uppercaseCounter >= MIN_Uppercase 
&& lowercaseCounter >= MIN_Lowercase && digitCounter >= NUM_Digits && specialCounter >= Special) { 
                    System.out.println("Valid Password");
             }
             else {
   System.out.println("Your password does not contain the following:");
                    if(password.length() < MAX)
                          System.out.println(" atleast 8 characters");
                    if (lowercaseCounter < MIN_Lowercase) 
                          System.out.println("Minimum lowercase letters");
                    if (uppercaseCounter < MIN_Uppercase) 
                          System.out.println("Minimum uppercase letters");
                    if(digitCounter < NUM_Digits) 
                          System.out.println("Minimum number of numeric digits");
                    if(specialCounter < Special)
System.out.println("Password should contain at lest 3 special characters");
                    
             }
             
        
	}

    @FXML
    private void ExportPdf(ActionEvent event) {
        try {
            PDFService PS = new PDFService();
            try {
                PS.liste_Students();
            } catch (IOException ex) {
                Logger.getLogger(AllStudentsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (DocumentException ex) {
            Logger.getLogger(AllStudentsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    void displayData(){
      StudentService SS= new StudentService();
        SS.listStudent();
         ObservableList<User> list = SS.listStudent();
        
        NStudent.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
        PStudent.setCellValueFactory(new PropertyValueFactory<User, String>("prenom"));
        EStudent.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
        tableStudent.setItems(list);
        
        
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
sortedData.comparatorProperty().bind(tableStudent.comparatorProperty());
tableStudent.setItems(sortedData);
    }

    @FXML
    private void sort(ActionEvent event) {
        StudentService SS = new StudentService();
         if(sortCB.getValue().equals("Par nom")){
            ObservableList<User> tri1=FXCollections.observableArrayList();
            tri1=FXCollections.observableArrayList(SS.sortByNom());
            tableStudent.setItems(tri1);
            
        }
        else if(sortCB.getValue().equals("Par email")){
            ObservableList<User> tri2=FXCollections.observableArrayList();
            tri2=FXCollections.observableArrayList(SS.sortByEmail());
            tableStudent.setItems(tri2);
        }
    }

    @FXML
    private void Dec(ActionEvent event) {
     
         UserService US = new UserService();
         US.logout();
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/User/Login.fxml"));
         try {
         Parent root = loader.load();
         rootPane.getScene().setRoot(root);
     } catch (IOException ex) {
         Logger.getLogger(AllStudentsController.class.getName()).log(Level.SEVERE, null, ex);
     }
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

    private void getNewsView(ActionEvent event) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/news/allNews.fxml"));
			rootPane.getChildren().setAll(pane);
		} catch (IOException ex) {
			Logger.getLogger(AllNewsController.class.getName()).log(Level.SEVERE, null, ex);
		}
    }

    @FXML
    private void getClasseView(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/Classe/AllClasses.fxml")); 
            Parent root = loader.load(); 
            rootPane.getScene().setRoot(root); 
		} catch (IOException ex) {
			ex.printStackTrace(); 
		}
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
    private void getForum(ActionEvent event) {
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
    private void handleClicks(ActionEvent event) {
    }

    @FXML
    private void getNewsView(MouseEvent event) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/news/allNews.fxml"));
			rootPane.getChildren().setAll(pane);
		} catch (IOException ex) {
			Logger.getLogger(AllNewsController.class.getName()).log(Level.SEVERE, null, ex);
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
    private void getEmploiView(MouseEvent event) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/edu/edspace/gui/emploi/allEmploi.fxml"));
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
    private void getNiveauView(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/Niveau/AllNiveau.fxml")); 
            Parent root = loader.load(); 
            rootPane.getScene().setRoot(root); 
		} catch (IOException ex) {
			ex.printStackTrace(); 
		}
    }
    
     

    
    
}