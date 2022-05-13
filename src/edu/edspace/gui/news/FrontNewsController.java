package edu.edspace.gui.news;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.edspace.entities.CategoryNews;
import edu.edspace.entities.Message;
import edu.edspace.entities.News;
import edu.edspace.entities.Session;
import edu.edspace.gui.Message.JsonReader;
import edu.edspace.gui.document.ListDocFrontController;
import edu.edspace.services.ClasseService;
import edu.edspace.services.MessageService;
import edu.edspace.services.NewsCategoryService;
import edu.edspace.services.NewsService;
import edu.edspace.services.statics;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
public class FrontNewsController implements Initializable {
        /* messages*/ 
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
        public  BufferedReader in;
        public String serverAddr;
        public int port;
        public ClientNews client;
        public Thread clientThread;
    
      ClasseService cs=new ClasseService();
        /* messages*/
    @FXML
    private AnchorPane rootPane;
    @FXML
    private ImageView home_iv;
    @FXML
    private TilePane tilePane;
    @FXML
    private Button resetBtn;
    @FXML
    private ComboBox<CategoryNews> category_cb;
    private List<CategoryNews> categories = new ArrayList<>();
    private List<News> newsList = new ArrayList<>();

    public void initData(){
        NewsService newsService = new NewsService();
        newsList = newsService.AllNews();
        NewsCategoryService cnews = new NewsCategoryService();
        categories = cnews.AllCats();
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
                        //System.out.println(newsList);
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
    @FXML
    private void reset(MouseEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/news/frontNews.fxml"));
            Parent root = loader.load();
            rootPane.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(FrontNewsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }   
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        initImages();
        initData();
        refreshchat();
    }
    
    public void initImages() {
        File fileHome = new File("images/home_grey.png");
        Image homeI = new Image(fileHome.toURI().toString());
        home_iv.setImage(homeI);   
        File filechat = new File("images/chat1.png");
        Image chat = new Image(filechat.toURI().toString());
        logo_chat.setImage(chat);       
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
      /* Messaging */  

  public void refreshchat(){
    port=2018;
    
     pane1.setVisible(false);
    ClasseService cs =new ClasseService();
    String niveau=cs.getOneById(cs.getStudent(uid).getClasse_id()).getNiveau().getId();
    String classe=cs.getOneById(cs.getStudent(uid).getClasse_id()).getClasse();
    chatgroupe.setText("CLASSE : "+niveau+" "+classe);
    initImages();
    setmes();
       try{

        client = new ClientNews(this);
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
                 System.out.println(temp);
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
                 System.out.println(temp);
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
                 System.out.println(temp);
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
    Logger.getLogger(AllNewsController.class.getName()).log(Level.SEVERE, null, ex);
}


}  
}
