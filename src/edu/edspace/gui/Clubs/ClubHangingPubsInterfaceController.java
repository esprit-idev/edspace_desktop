/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.edspace.gui.Clubs;

import edu.edspace.entities.ClubPub;
import edu.edspace.entities.Session;
import edu.edspace.services.ClubPubService;
import edu.edspace.services.ClubService;
import edu.edspace.utils.MyConnection;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author anash
 */
public class ClubHangingPubsInterfaceController implements Initializable {

    @FXML
    private GridPane pubsHangGrid;
    private List<ClubPub> pubsList = new ArrayList<>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void initData(int clubid) {
        ClubPubService cb = new ClubPubService();
        ClubService cp = new ClubService();
        String clubPic = cp.getClubImage(clubid);
        String clubName = cp.getClubName(clubid);

        pubsList = cb.displayHangingClubPubs(clubid);
        int colu = 0;
        int row = 0;
        if (pubsList.isEmpty()) {
            Label l = new Label("Pas de publications.");
            pubsHangGrid.add(l, colu++, row);
        } else {
            for (int i = 0; i < pubsList.size(); i++) {
                if (Session.getRoles().contains("RESPO")) {
                    try {
                        FXMLLoader fxml = new FXMLLoader(getClass().getResource("ClubPubHangRefItem.fxml"));

                        AnchorPane anchorPane = fxml.load();//child
                        ClubPubHangRefItemController clubPubHangRefItemController = fxml.getController();
                        clubPubHangRefItemController.setData(pubsList.get(i), clubName,
                                clubPic
                        );
                        
                        if (colu == 1) {
                            colu = 0;
                            row++;
                        }
                        pubsHangGrid.add(anchorPane, colu++, row);
                        GridPane.setMargin(anchorPane, new Insets(10));
                    } catch (IOException ex) {
                        Logger.getLogger(ClubHangingPubsInterfaceController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    try {
                        FXMLLoader fxml = new FXMLLoader(getClass().getResource("ClubPubItemAdminAcceptRefuse.fxml"));

                        AnchorPane anchorPane = fxml.load();//child
                        ClubPubItemAdminAcceptRefuseController clubPubItemAdminAcceptRefuseController = fxml.getController();
                        clubPubItemAdminAcceptRefuseController.setData(pubsList.get(i), clubName,
                                clubPic
                        );
                        if (colu == 1) {
                            colu = 0;
                            row++;
                        }
                        pubsHangGrid.add(anchorPane, colu++, row);
                        GridPane.setMargin(anchorPane, new Insets(10));
                    } catch (IOException ex) {
                        Logger.getLogger(ClubHangingPubsInterfaceController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
        }

    }

}
