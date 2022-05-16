/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.edspace.gui;

import edu.edspace.entities.Message;
import edu.edspace.entities.Session;
import edu.edspace.gui.Message.AllMessagesController;
import edu.edspace.gui.Message.JsonReader;
import edu.edspace.services.ClasseService;
import edu.edspace.services.MessageService;
import edu.edspace.services.ThreadService;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * FXML Controller class
 *
 * @author 21656
 */
public class FrontThreadController implements Initializable {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private ImageView out_iv;
    @FXML
    private ImageView logo_iv;
    @FXML
    private ImageView profile_iv;
    @FXML
    private ImageView previous;
    @FXML
    private VBox vmain;
       @FXML
    private Pane pane1;
    @FXML
    private TextField text;
    @FXML
    private Button location;
    @FXML
    private Button send;
    @FXML
    private ScrollPane sp_main;
    @FXML
    private AnchorPane anchor1;
    @FXML
    public VBox vbox;
    @FXML
    private Button translate;
    @FXML
    private ImageView logo_chat;
    @FXML
    private Button chatgroupe;

    public    MessageService ms=new MessageService();
    public int uid=Session.getId();
   public Socket socket;
   public OutputStream out;
   public PrintWriter ostream;
  public   BufferedReader in;
     public String serverAddr;
    public int port;
public ClientThread client;
public Thread clientThread;

  ClasseService cs=new ClasseService();

    /**
     * Initializes the controller class.
     */
    ObservableList<edu.edspace.entities.Thread> Threads = FXCollections.observableArrayList();
    int user = 1;
    int admin = 1;
    final List<edu.edspace.entities.Thread> th = new ArrayList();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initImages();
        refreshchat();
        previous.setOnMouseClicked(e->{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FrontHome.fxml"));
               try {
                   Parent root1 = loader.load();
                   vmain.getScene().setRoot(root1);
               } catch (IOException ex) {
                   Logger.getLogger(ThreadListController.class.getName()).log(Level.SEVERE, null, ex);
               }
        });
              ThreadService threadService = new ThreadService();
       List<edu.edspace.entities.Thread>ts = threadService.listThreads();
        for (int i = 0;i<ts.size();i++)
       {
           th.add(ts.get(i));
       }
       
       
        
       Threads.clear();
       Threads.addAll(threadService.listThreads());
       vmain.setSpacing(10);
       if(ts.size()==0){
           vmain.getChildren().add(new Text("Empty"));
       }
       for (int i = 0;i<ts.size();i++)
       {
           int q = i;
           edu.edspace.entities.Thread t = new edu.edspace.entities.Thread();
          
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
           if(t.getUser()==this.user){
               ha.getChildren().add(up);
           }
           Text tx= new Text();
           tx.setText("Added by "+t.getUser());
           if(this.user == t.getUser() || this.admin == 1){
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
    private void logout(MouseEvent event) {
    }

    @FXML
    private void getProfile(MouseEvent event) {
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
    public void initImages() {
        File fileLogo = new File("images/logo1.png");
        Image logoI = new Image(fileLogo.toURI().toString());
        
        File fileHome = new File("images/home_grey.png");
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
        
        File fileReport = new File("images/report_red.png");
        Image reportI = new Image(fileReport.toURI().toString());
                 File filechat = new File("images/chat1.png");
        Image chat = new Image(filechat.toURI().toString());

       
        logo_chat.setImage(chat);

        
        logo_iv.setImage(logoI);
        previous.setImage(homeI);
    }
      
    public void refreshchat(){
            port=2018;
            
             pane1.setVisible(false);
        ClasseService cs =new ClasseService();
        String niveau=cs.getOneById(cs.getStudent(uid).getClasse_id()).getNiveau().getId();
        String classe=cs.getOneById(cs.getStudent(uid).getClasse_id()).getClasse();
         chatgroupe.setText("CLASSE : "+niveau+" "+classe);
    
            
               try{
    
    
                client = new ClientThread(this);
                clientThread = new Thread(client);
                clientThread.start();
                
            }
            catch(Exception ex){
                System.out.println(ex);
            }
       

       
       setmes();
      //  setOthermes(); 
    }
    
    
  
    
    
    public void setmes(){
          for (Message temp : ms.listeMessageClasse(cs.getOneById(cs.getStudent(uid).getClasse_id()).getId())) {
              
              if(temp.getUser().getId()==uid){
              HBox hBox=new HBox();
              hBox.setAlignment(Pos.CENTER_LEFT);
              hBox.setPadding(new Insets(5,5,5,10));
           
                           Text text=new Text(temp.getContent());
                           text.setFill(Color.LIGHTGREEN);
                              text.setFont(Font.font("SANS_SERIF", 13));
                           TextFlow textFlow=new TextFlow(text);
                           textFlow.setStyle("-fx-background-color: rgb(233,233,235)"+ " -fx-background-radius: 20px");
                           textFlow.setPadding(new Insets(5,0,5,100));
                           hBox.getChildren().add(textFlow);
                           vbox.getChildren().add(hBox);
                           
                        
           // items.add(temp.getUsername()+" "+temp.getPrenom());
        }else{
                   HBox hBox=new HBox();
              hBox.setAlignment(Pos.CENTER_LEFT);
              hBox.setPadding(new Insets(5,5,5,0));
            
                           Text text=new Text(temp.getContent());
                              text.setFont(Font.font("SANS_SERIF", 13));
                           text.setFill(Color.VIOLET);
                           TextFlow textFlow=new TextFlow(text);
                           textFlow.setStyle("-fx-background-color: rgb(233,233,235)"+ " -fx-background-radius: 20px");
                           textFlow.setPadding(new Insets(5,0,5,0));
                           hBox.getChildren().add(textFlow);
                           vbox.getChildren().add(hBox);
                  
              }
          }
        
    }
    
    
     public void addmes(String tt){
       
              HBox hBox=new HBox();
              hBox.setAlignment(Pos.CENTER_LEFT);
              hBox.setPadding(new Insets(5,5,5,10));
                         
                           Text text1=new Text(tt);
                           TextFlow textFlow=new TextFlow(text1);
                           textFlow.setStyle("-fx-background-color: rgb(233,233,235)"+ " -fx-background-radius: 20px");
                           textFlow.setPadding(new Insets(5,0,5,100));
                           hBox.getChildren().add(textFlow);
                           vbox.getChildren().add(hBox);
                           
                        
           // items.add(temp.getUsername()+" "+temp.getPrenom());
        }
        
    
    
    
     public void setOthermes(){
          for (Message temp : ms.listeOtherMessageClasse(uid)) {
              HBox hBox=new HBox();
              hBox.setAlignment(Pos.CENTER_LEFT);
              hBox.setPadding(new Insets(5,5,5,10));
                   
                           Text text=new Text(temp.getContent());
                           TextFlow textFlow=new TextFlow(text);
                           textFlow.setStyle("-fx-background-color: rgb(233,233,235)"+ " -fx-background-radius: 20px");
                           textFlow.setPadding(new Insets(5,100,5,0));
                           hBox.getChildren().add(textFlow);
                           vbox.getChildren().add(hBox);
                           
                        
           // items.add(temp.getUsername()+" "+temp.getPrenom());
        }
        
    }
    
    
  
     

    @FXML
    private void sendMessage(ActionEvent event) {
           
        
        if(!text.getText().isEmpty()){
       
        
        
        
        
        
        
        
        
        Message m = new Message();
        
        m.setClasse(cs.getOneById(cs.getStudent(uid).getClasse_id()));
        m.setUser(ms.getuser(uid));
        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        m.setPostDate(date);
        m.setContent(text.getText());
         //addmes(ms.switcher(m).getContent());
        client.send(m);
        ms.ajouterMessage(m);
        
        
        
        }
        text.setText("");
    }
    
    
    

    @FXML
    private void location(ActionEvent event) {
             String mylocation=JsonReader.show("102.156.219.112");
              
              ClasseService cs=new ClasseService();
        
        
        
        Message m = new Message();
        
        m.setClasse(cs.getOneById(cs.getOneById(cs.getStudent(uid).getClasse_id()).getId()));
        m.setUser(ms.getuser(1));
        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        m.setPostDate(date);
        m.setContent("MY LOCATION RIGHT NOW IS AT AT : "+mylocation.toUpperCase() +" weather description :"+JsonReader.weather(mylocation));
        
        //addmes("I'M AT : "+mylocation);
         client.send(m);
        ms.ajouterMessage(m);
    }
    
      @FXML
    void open(MouseEvent event) {
        
        pane1.setVisible(true);

    }
    
        @FXML
    void closechat(ActionEvent event) {
 pane1.setVisible(false);
    }
    
       @FXML
    void translatem(ActionEvent event) {
        try {
            text.setText(JsonReader.translate("en", "fr",text.getText()));
        } catch (IOException ex) {
            Logger.getLogger(AllMessagesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        

    }
    
}
