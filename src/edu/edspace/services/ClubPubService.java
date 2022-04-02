/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.edspace.services;

import edu.edspace.entities.ClubPub;
import edu.edspace.utils.MyConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author anash
 */
public class ClubPubService {

    public void ajouterPubClub(ClubPub clubPub) {
        try {
            String req = "insert into club_pub (club_id,pub_date,pub_description,pub_file,pub_file_name,type_fichier,club_img,is_posted) values"
                    + "(?,?,?,?,?,?,?,?)"; //requete d'inertion avec parametres

            /*PreparedStatement used with dynamic requests+faster and more secure than Statement (used in the method above)
                can't drop or alter with PreparedStatement*/
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req); //instance of myconnection pour etablir la cnx
            pst.setInt(1, clubPub.getClub()); //parameter1=index in request(req) and parameter2=data to pass (nom de la personne)
            pst.setString(2, clubPub.getPubDate());
            pst.setString(3, clubPub.getPubDesc());
            pst.setString(4, clubPub.getPubFile());
            pst.setString(5, null);
            pst.setString(6, null);
            pst.setString(7, clubPub.getPubImage());
            pst.setInt(8, clubPub.getIs_posted());

            pst.executeUpdate(); //pour exécuter la requete
            System.out.println("ClubPub added");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List<ClubPub> displayClubPubs(int clubId) {
        List<ClubPub> clubPubList = new ArrayList<>();
        try {
            String req = "select * from club_pub where club_id=" + clubId; //requete select from db
            Statement st = MyConnection.getInstance().getCnx().createStatement(); //instance of myConnection pour etablir la cnx
            ResultSet rs = st.executeQuery(req); //resultat de la requete

            //tant que rs has next get matiere and add it to the list
            while (rs.next()) {
                ClubPub clubPub = new ClubPub();
                clubPub.setId(rs.getString(1)); //set id from req result
                clubPub.setClub(rs.getInt(2));
                clubPub.setPubDate(rs.getString(3));
                clubPub.setPubDesc(rs.getString(4));
                clubPub.setPubFile(rs.getString(5));
                clubPub.setPubImage(rs.getString(8));
                clubPubList.add(clubPub); //ajout de la matiere a la liste
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return clubPubList;
    }

    public void updateClubPub(ClubPub clubPub, int currentId) {
        String req = "update club_pub set pub_date=?,pub_description=?,club_img=?,is_posted=? WHERE id=?";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req);
            pst.setString(1, clubPub.getPubDate());
            pst.setString(2, clubPub.getPubDesc());
            pst.setString(3, clubPub.getPubImage());
            pst.setInt(4, clubPub.getIs_posted());
            pst.setInt(5, currentId);
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
}
