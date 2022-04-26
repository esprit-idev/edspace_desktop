/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.edspace.services;

import edu.edspace.entities.ClubPub;
import edu.edspace.utils.MyConnection;
import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author anash
 */
public class ClubPubService {

    public void ajouterPubClub(ClubPub clubPub) {
        try {
            String req = "insert into club_pub (club_id,pub_date,pub_description,pub_file,pub_file_name,type_fichier,club_img,is_posted) values"
                    + "(?,?,?,?,?,?,?,0)"; //requete d'inertion avec parametres

            /*PreparedStatement used with dynamic requests+faster and more secure than Statement (used in the method above)
                can't drop or alter with PreparedStatement*/
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req); //instance of myconnection pour etablir la cnx
            pst.setInt(1, clubPub.getClub()); //parameter1=index in request(req) and parameter2=data to pass (nom de la personne)
            pst.setString(2, new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
            pst.setString(3, clubPub.getPubDesc());
            pst.setString(4, null);
            pst.setString(5, clubPub.getPubFile());
            pst.setString(6, null);
            pst.setString(7, clubPub.getPubImage());

            pst.executeUpdate(); //pour exécuter la requete
            System.out.println("ClubPub added");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void updateClubPub(String desc, int currentId) {
        String req = "update club_pub set pub_date=?,pub_description=?,is_posted=0 WHERE id=?";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req);
            pst.setString(1, new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
            pst.setString(2, desc);
            pst.setInt(3, currentId);
            pst.executeUpdate();
            System.out.println("ClubPub updated");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void deletePubClub(int pubid) {
        String req = "delete from club_pub where id = ?";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req);
            pst.setInt(1, pubid);
            pst.executeUpdate();
            System.out.println("ClubPub deleted");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List<ClubPub> displayPostedClubPubsFiltredByDate(int clubId, String minDate, String maxDate) {
        List<ClubPub> clubPubList = new ArrayList<>();
        try {
            String req = "SELECT * FROM `club_pub` WHERE club_id =" + clubId + " and date(pub_date) >='" + minDate + "' and date(pub_date) <='" + maxDate + "' and is_posted=1 ORDER BY pub_date deSC;"; //requete select from db
            Statement st = MyConnection.getInstance().getCnx().createStatement(); //instance of myConnection pour etablir la cnx
            ResultSet rs = st.executeQuery(req); //resultat de la requete

            //tant que rs has next get matiere and add it to the list
            while (rs.next()) {
                ClubPub clubPub = new ClubPub();
                clubPub.setId(rs.getString(1)); //set id from req result
                clubPub.setClub(rs.getInt(2));
                clubPub.setPubDate(rs.getString(3));
                clubPub.setPubDesc(rs.getString(4));
                clubPub.setPubFile(rs.getString(6));
                clubPub.setPubImage(rs.getString(8));
                clubPubList.add(clubPub); //ajout de la matiere a la liste
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return clubPubList;
    }

    public List<ClubPub> displayHangingClubPubs(int clubId) {
        List<ClubPub> clubPubList = new ArrayList<>();
        try {
            String req = "SELECT * FROM `club_pub` WHERE club_id =" + clubId + " and is_posted=0 ORDER BY pub_date deSC;"; //requete select from db
            Statement st = MyConnection.getInstance().getCnx().createStatement(); //instance of myConnection pour etablir la cnx
            ResultSet rs = st.executeQuery(req); //resultat de la requete

            //tant que rs has next get matiere and add it to the list
            while (rs.next()) {
                ClubPub clubPub = new ClubPub();
                clubPub.setId(rs.getString(1)); //set id from req result
                clubPub.setClub(rs.getInt(2));
                clubPub.setPubDate(rs.getString(3));
                clubPub.setPubDesc(rs.getString(4));
                clubPub.setPubFile(rs.getString(6));
                clubPub.setPubImage(rs.getString(8));
                clubPubList.add(clubPub); //ajout de la matiere a la liste
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return clubPubList;
    }

    public List<ClubPub> displayPostedClubPubs(int clubId) {
        List<ClubPub> clubPubList = new ArrayList<>();
        try {
            String req = "SELECT * FROM `club_pub` WHERE club_id =" + clubId + " and is_posted=1 ORDER BY pub_date deSC;"; //requete select from db
            Statement st = MyConnection.getInstance().getCnx().createStatement(); //instance of myConnection pour etablir la cnx
            ResultSet rs = st.executeQuery(req); //resultat de la requete

            //tant que rs has next get matiere and add it to the list
            while (rs.next()) {
                ClubPub clubPub = new ClubPub();
                clubPub.setId(rs.getString(1)); //set id from req result
                clubPub.setClub(rs.getInt(2));
                clubPub.setPubDate(rs.getString(3));
                clubPub.setPubDesc(rs.getString(4));
                clubPub.setPubFile(rs.getString(6));
                clubPub.setPubImage(rs.getString(8));
                clubPubList.add(clubPub); //ajout de la matiere a la liste
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return clubPubList;
    }

    public List<ClubPub> displayRefusedClubPubs(int clubId) {
        List<ClubPub> clubPubList = new ArrayList<>();
        try {
            String req = "SELECT * FROM `club_pub` WHERE club_id =" + clubId + " and is_posted=-1 ORDER BY pub_date deSC;"; //requete select from db
            Statement st = MyConnection.getInstance().getCnx().createStatement(); //instance of myConnection pour etablir la cnx
            ResultSet rs = st.executeQuery(req); //resultat de la requete

            //tant que rs has next get matiere and add it to the list
            while (rs.next()) {
                ClubPub clubPub = new ClubPub();
                clubPub.setId(rs.getString(1)); //set id from req result
                clubPub.setClub(rs.getInt(2));
                clubPub.setPubDate(rs.getString(3));
                clubPub.setPubDesc(rs.getString(4));
                clubPub.setPubFile(rs.getString(6));
                clubPub.setPubImage(rs.getString(8));
                clubPubList.add(clubPub); //ajout de la matiere a la liste
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return clubPubList;
    }

    public void acceptClubPub(int currentId) {
        String req = "update club_pub set is_posted=1 WHERE id=?";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req);
            pst.setInt(1, currentId);
            pst.executeUpdate();
            System.out.println("ClubPub accepted");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void refuseClubPub(int currentId) {
        String req = "update club_pub set is_posted=-1 WHERE id=?";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req);
            pst.setInt(1, currentId);
            pst.executeUpdate();
            System.out.println("ClubPub refused");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void notifMessage() {
        String req = "SELECT club_id FROM `club_pub` ORDER BY id DESC LIMIT 1;";
        int clubid = 0;
        try {

            Statement st = MyConnection.getInstance().getCnx().createStatement(); //instance of myConnection pour etablir la cnx
            ResultSet rs = st.executeQuery(req); //resultat de la requete

            //tant que rs has next get matiere and add it to the list
            while (rs.next()) {
                clubid = rs.getInt(1); //set id from req result
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        ClubService cb=new ClubService();
        
        //Obtain only one instance of the SystemTray object
        SystemTray tray = SystemTray.getSystemTray();

        //If the icon is a file
        Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
        //Alternative (if the icon is on the classpath):
        //Image image = Toolkit.getDefaultToolkit().createImage(getClass().getResource("icon.png"));

        TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");
        //Let the system resize the image if needed
        trayIcon.setImageAutoSize(true);
        //Set tooltip text for the tray icon
        trayIcon.setToolTip("System tray icon demo");
        try {
            tray.add(trayIcon);
        } catch (AWTException ex) {
        }

        trayIcon.displayMessage("EdSpace", "Le club "+cb.getClubName(clubid)+" a une publication en attente.", MessageType.INFO);
    }

    public void sharefb(String pubId, String clubId) {
        try {
            try {
                Desktop.getDesktop().browse(new URI("https://www.facebook.com/sharer/sharer.php?u=http%3A%2F%2F127.0.0.1%3A8000%2FdisplayPub%2F" + pubId + "%2F" + clubId + "&amp;src=sdkpreparse"));
            } catch (IOException ex) {
            }
        } catch (URISyntaxException ex) {
        }
    }

    public void likePub(int currentUser, int pubId) {
        String req = "insert into club_pub_likes (user_Id,pub_id) values"
                + "(?,?)";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req);
            pst.setInt(1, currentUser);
            pst.setInt(2, pubId);
            pst.executeUpdate();
            System.out.println("ClubPub liked");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }
    }

    public void dislikePub(int currentUser, int pubId) {
        String req = "delete from club_pub_likes where pub_id=? and user_id= ?";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req);
            pst.setInt(1, pubId);
            pst.setInt(2, currentUser);

            pst.executeUpdate();
            System.out.println("ClubPub disliked");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public int countLikesPerPub(int pubId) {
        int likes = 0;
        String req = "select COUNT(*) from club_pub_likes where pub_id=?";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req);
            pst.setInt(1, pubId);
            ResultSet rs = pst.executeQuery();
            rs.next();
            likes = rs.getInt("COUNT(*)");
            System.out.println("ClubPub numbs ");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return likes;
    }

    public boolean pubIsLiked(int userId, int pubId) {
        boolean isLiked = false;
        String req = "select COUNT(*) from club_pub_likes where pub_id=? and user_id=?";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req);
            pst.setInt(1, pubId);
            pst.setInt(2, userId);
            ResultSet rs = pst.executeQuery();
            rs.next();
            if (rs.getInt("COUNT(*)") != 0) {
                isLiked = true;
            }
            System.out.println("ClubPub isLiked or not ");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return isLiked;
    }

    public String getpubDesc(int pubId) {
        String desc = "";
        String req = "select pub_description from club_pub where id=?";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req);
            pst.setInt(1, pubId);
            ResultSet rs = pst.executeQuery();
            rs.next();
            desc = rs.getString(1);
            System.out.println("ClubPub numbs ");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return desc;
    }

    public int getPubsNB() {
        int nb = 0;
        String req = "select COUNT(*) from club_pub";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            rs.next();
            nb = rs.getInt("COUNT(*)");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return nb;
    }
}
