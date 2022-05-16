/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.edspace.gui.document;

import edu.edspace.entities.Document;
import edu.edspace.entities.Matiere;
import edu.edspace.entities.Message;
import edu.edspace.entities.Niveau;
import edu.edspace.entities.Session;
import edu.edspace.gui.Message.AllMessagesController;
import edu.edspace.gui.document.Client;
import edu.edspace.gui.Message.JsonReader;
import edu.edspace.services.ClasseService;
import edu.edspace.services.DocumentService;
import edu.edspace.services.MatiereService;
import edu.edspace.services.MessageService;
import edu.edspace.services.NiveauService;
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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * FXML Controller class
 *
 * @author MeriamBI
 */
public class ListDocFrontController implements Initializable {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private ComboBox<String> niveau_cb;
    @FXML
    private ComboBox<String> matiere_cb;
    @FXML
    private ImageView add_iv;
    @FXML
    private Label reinitialiser_label;
    @FXML
    private ImageView home_iv;
    @FXML
    private ScrollPane scroll;
    @FXML
    private TilePane grid;
    @FXML
    private Button fave_btn;
    @FXML
    private ImageView fave_iv;
    @FXML
    private Label nodocs_l;

    private List<Matiere> mats = new ArrayList();
    private List<Niveau> niveaux = new ArrayList();
    private List<Document> docs = new ArrayList();
    private ImageView chat_iv;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        MyConnection.getInstance().getCnx();
        initImages();
        initDisplay();
        refreshchat();
    }

    @FXML
    private void addDoc(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/document/AddDoc.fxml"));
            Parent root = loader.load();
            rootPane.getScene().setRoot(root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void reinitialiserFiltre(MouseEvent event) {
        niveau_cb.setValue(null);
        matiere_cb.setValue(null);
        initDisplay();
    }

    private void initDisplay() {
        DocumentService ds = new DocumentService();
        docs = ds.listDocs();
        if (docs.isEmpty()) {
            scroll.setVisible(false);
            nodocs_l.setVisible(true);
        } else {
            scroll.setVisible(true);
            nodocs_l.setVisible(false);
            initGrid(docs);
        }
        niveau_cb.setItems(niveauxList());
        niveau_cb.setPromptText("Tous les niveaux");

        niveau_cb.valueProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue ov, String oldVal, String newVal) {
                matiere_cb.setItems(matieresListFiltered(niveau_cb.getValue()));
                matiere_cb.setPromptText("Toutes les matières");
            }
        });

        matiere_cb.valueProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue ov, String oldVal, String newVal) {
                docs = ds.filterByNiveauMatiere(niveau_cb.getValue(), newVal);
                grid.getChildren().clear();
                if (docs.isEmpty()) {
                    scroll.setVisible(false);
                    nodocs_l.setVisible(true);
                } else {
                    scroll.setVisible(true);
                    nodocs_l.setVisible(false);
                    initGrid(docs);
                }

            }
        });
    }

    private void initGrid(List<Document> docs) {
        int column = 0;
        int row = 0;
        for (int i = 0; i < docs.size(); i++) {
            try {
                FXMLLoader fXMLLoader = new FXMLLoader();
                fXMLLoader.setLocation(getClass().getResource("/edu/edspace/gui/document/DocR.fxml"));
                AnchorPane anchorPane = fXMLLoader.load();
                DocRController docRController = fXMLLoader.getController();
                docRController.setData(docs.get(i));

                /*if (column == 4) {
                    column = 0;
                    row++;
                }
                grid.add(anchorPane, column++, row);*/
                //grid.getRowConstraints().add(new RowConstraints(30));
                grid.getChildren().addAll(anchorPane);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    //list of niveaux in ObservableList
    private ObservableList<String> niveauxList() {
        ObservableList<String> oblistN = FXCollections.observableArrayList();
        NiveauService ns = new NiveauService();
        niveaux = ns.listeNiveaux();
        for (int i = 0; i < niveaux.size(); i++) {
            oblistN.add(niveaux.get(i).getId());
        }
        return oblistN;
    }

    //list of matieres filtered by niveau in ObservableList
    private ObservableList<String> matieresListFiltered(String niveau) {
        ObservableList<String> oblistM = FXCollections.observableArrayList();
        MatiereService ns = new MatiereService();
        mats = ns.filterByNiveau(niveau);
        for (int i = 0; i < mats.size(); i++) {
            oblistM.add(mats.get(i).getId());
        }
        return oblistM;
    }

    public void initImages() {

        File fileHome = new File("images/home_grey.png");
        Image homeI = new Image(fileHome.toURI().toString());

        File fileAdd = new File("images/add-new_grey.png");
        Image addI = new Image(fileAdd.toURI().toString());

        File fileHeart = new File("images/heart.png");
        Image heartI = new Image(fileHeart.toURI().toString());

        File filechat = new File("images/chat1.png");
        Image chat = new Image(filechat.toURI().toString());

        logo_chat.setImage(chat);
        home_iv.setImage(homeI);
        add_iv.setImage(addI);
        fave_iv.setImage(heartI);
    }

    @FXML
    private void getFave(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/document/FaveDocs.fxml"));
            Parent root = loader.load();
            rootPane.getScene().setRoot(root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void getHome(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/edspace/gui/FrontHome.fxml"));
            Parent root = loader.load();
            rootPane.getScene().setRoot(root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void refreshchat() {
        port = 2018;

        pane1.setVisible(false);
        ClasseService cs = new ClasseService();
        String niveau = cs.getOneById(cs.getStudent(uid).getClasse_id()).getNiveau().getId();
        String classe = cs.getOneById(cs.getStudent(uid).getClasse_id()).getClasse();
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

        setmes();
        //  setOthermes(); 
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
}
