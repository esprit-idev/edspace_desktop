/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.edspace.gui.Clubs;

import edu.edspace.entities.Club;
import edu.edspace.entities.ClubCategory;
import edu.edspace.entities.Message;
import edu.edspace.entities.Session;
import edu.edspace.gui.Message.AllMessagesController;
import edu.edspace.gui.Clubs.Client;
import edu.edspace.gui.Message.JsonReader;
import edu.edspace.gui.document.ListDocFrontController;
import edu.edspace.services.ClasseService;
import edu.edspace.services.ClubCategService;
import edu.edspace.services.ClubService;
import edu.edspace.services.MessageService;
import edu.edspace.utils.MyConnection;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
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
 * @author anash
 */
public class ClubListStudentController implements Initializable {

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

    public MessageService ms = new MessageService();
    public int uid = Session.getId();
    public Socket socket;
    public OutputStream out;
    public PrintWriter ostream;
    public BufferedReader in;
    public String serverAddr;
    public int port;
    public Client client;
    public Thread clientThread;
    ClasseService cs = new ClasseService();

    @FXML
    private ScrollPane scroll;
    @FXML
    private GridPane grid;
    private List<Club> clubs = new ArrayList<>();
    @FXML
    private ImageView home_iv1;
    @FXML
    private ComboBox<String> filter_cb;

    private List<ClubCategory> categories = new ArrayList();
    private ObservableList<String> categoriesList;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        refreshchat();

        // TODO
        MyConnection.getInstance().getCnx();
        initImages();
        categoriesList = catsList();
        categoriesList.add("Toutes les catégories");
        filter_cb.setItems(categoriesList);
        filter_cb.setValue("Toutes les catégories");
        showClubs();
        filter_cb.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                if (filter_cb.getValue().equals("Toutes les catégories")) {
                    showClubs();
                } else {
                    showFiltredClubs(filter_cb.getValue());
                }
            }
        });
    }

    public void refreshchat() {
        port = 2018;

        pane1.setVisible(false);
        ClasseService css = new ClasseService();
        String niveau = css.getOneById(css.getStudent(uid).getClasse_id()).getNiveau().getId();
        String classe = css.getOneById(css.getStudent(uid).getClasse_id()).getClasse();
        chatgroupe.setText("CLASSE : " + niveau + " " + classe);
        initImages();
        setmes();

        try {

            client = new Client(this);
            clientThread = new Thread(client);
            clientThread.start();

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void setmes() {
        for (Message temp : ms.listeMessageClasse(cs.getOneById(cs.getStudent(uid).getClasse_id()).getId())) {

            if (temp.getUser().getId() == uid) {
                HBox hBox = new HBox();
                hBox.setAlignment(Pos.CENTER_LEFT);
                hBox.setPadding(new Insets(5, 5, 5, 10));
                System.out.println(temp);
                Text text = new Text(temp.getContent());
                text.setFill(Color.LIGHTGREEN);
                text.setFont(Font.font("SANS_SERIF", 13));
                TextFlow textFlow = new TextFlow(text);
                textFlow.setStyle("-fx-background-color: rgb(233,233,235)" + " -fx-background-radius: 20px");
                textFlow.setPadding(new Insets(5, 0, 5, 100));
                hBox.getChildren().add(textFlow);
                vbox.getChildren().add(hBox);

                // items.add(temp.getUsername()+" "+temp.getPrenom());
            } else {
                HBox hBox = new HBox();
                hBox.setAlignment(Pos.CENTER_LEFT);
                hBox.setPadding(new Insets(5, 5, 5, 0));
                System.out.println(temp);
                Text text = new Text(temp.getContent());
                text.setFont(Font.font("SANS_SERIF", 13));
                text.setFill(Color.VIOLET);
                TextFlow textFlow = new TextFlow(text);
                textFlow.setStyle("-fx-background-color: rgb(233,233,235)" + " -fx-background-radius: 20px");
                textFlow.setPadding(new Insets(5, 0, 5, 0));
                hBox.getChildren().add(textFlow);
                vbox.getChildren().add(hBox);

            }
        }

    }

    public void addmes(String tt) {

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5, 5, 5, 10));

        Text text1 = new Text(tt);
        TextFlow textFlow = new TextFlow(text1);
        textFlow.setStyle("-fx-background-color: rgb(233,233,235)" + " -fx-background-radius: 20px");
        textFlow.setPadding(new Insets(5, 0, 5, 100));
        hBox.getChildren().add(textFlow);
        vbox.getChildren().add(hBox);

        // items.add(temp.getUsername()+" "+temp.getPrenom());
    }

    public void setOthermes() {
        for (Message temp : ms.listeOtherMessageClasse(uid)) {
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER_LEFT);
            hBox.setPadding(new Insets(5, 5, 5, 10));
            System.out.println(temp);
            Text text = new Text(temp.getContent());
            TextFlow textFlow = new TextFlow(text);
            textFlow.setStyle("-fx-background-color: rgb(233,233,235)" + " -fx-background-radius: 20px");
            textFlow.setPadding(new Insets(5, 100, 5, 0));
            hBox.getChildren().add(textFlow);
            vbox.getChildren().add(hBox);

            // items.add(temp.getUsername()+" "+temp.getPrenom());
        }

    }

    @FXML
    private void sendMessage(ActionEvent event) {

        if (!text.getText().isEmpty()) {

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
        String mylocation = JsonReader.show("102.156.219.112");

        ClasseService cs = new ClasseService();

        Message m = new Message();

        m.setClasse(cs.getOneById(cs.getOneById(cs.getStudent(uid).getClasse_id()).getId()));
        m.setUser(ms.getuser(1));
        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        m.setPostDate(date);
        m.setContent("MY LOCATION RIGHT NOW IS AT AT : " + mylocation.toUpperCase() + " weather description :" + JsonReader.weather(mylocation));

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
            text.setText(JsonReader.translate("en", "fr", text.getText()));
        } catch (IOException ex) {
            Logger.getLogger(AllMessagesController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void getHome(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/FrontHome.fxml"));
            Parent root = loader.load();
            grid.getScene().setRoot(root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void showClubs() {
        ClubService cb = new ClubService();
        clubs = cb.displayClub();
        int colu = 0;
        int row = 0;
        try {
            for (int i = 0; i < clubs.size(); i++) {

                FXMLLoader fxml = new FXMLLoader();
                fxml.setLocation(getClass().getResource("/edu/edspace/gui/Clubs/ClubItem.fxml"));

                AnchorPane anchorPane = fxml.load();//child

                ClubItemController clubItemController = fxml.getController();
                clubItemController.setData(clubs.get(i));

                if (colu == 2) {
                    colu = 0;
                    row++;
                }

                grid.add(anchorPane, colu++, row);
                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void showFiltredClubs(String clubName) {
        grid.getChildren().clear();
        ClubService cb = new ClubService();
        clubs = cb.displayClubFiltredByName(clubName);
        int colu = 0;
        int row = 0;
        try {
            for (int i = 0; i < clubs.size(); i++) {

                FXMLLoader fxml = new FXMLLoader();
                fxml.setLocation(getClass().getResource("/edu/edspace/gui/Clubs/ClubItem.fxml"));

                AnchorPane anchorPane = fxml.load();//child

                ClubItemController clubItemController = fxml.getController();
                clubItemController.setData(clubs.get(i));

                if (colu == 2) {
                    colu = 0;
                    row++;
                }

                grid.add(anchorPane, colu++, row);
                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void initImages() {
        File filechat = new File("images/chat1.png");
        Image chat = new Image(filechat.toURI().toString());

        logo_chat.setImage(chat);
        File fileHome = new File("images/home_grey.png");
        Image homeI = new Image(fileHome.toURI().toString());

        home_iv1.setImage(homeI);

    }

    private ObservableList<String> catsList() {
        ObservableList<String> oblistN = FXCollections.observableArrayList();
        ClubCategService ccs = new ClubCategService();
        categories = ccs.displayClubCategories();
        for (int i = 0; i < categories.size(); i++) {
            oblistN.add(categories.get(i).getCategorie());
        }
        return oblistN;
    }

}
