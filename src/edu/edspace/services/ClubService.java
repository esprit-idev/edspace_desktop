/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.edspace.services;

import edu.edspace.entities.Club;
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
public class ClubService {

    public void ajouterClub(Club club) {
        try {
            String req = "insert into club (club_categorie_id,club_responsable_id,club_nom,club_description,club_pic) values"
                    + "(?,?,?,?,?)"; //requete d'inertion avec parametres

            /*PreparedStatement used with dynamic requests+faster and more secure than Statement (used in the method above)
                can't drop or alter with PreparedStatement*/
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req); //instance of myconnection pour etablir la cnx
            pst.setInt(1, club.getClubCategorie()); //parameter1=index in request(req) and parameter2=data to pass (nom de la personne)
            pst.setInt(2, club.getClubRespo());
            pst.setString(3, club.getClubName());
            pst.setString(4, club.getClubDesc());
            pst.setString(5, club.getClubPic());
            pst.executeUpdate(); //pour exécuter la requete
            System.out.println("Club added");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List<Club> displayClub() {
        List<Club> clubList = new ArrayList<>();
        try {
            String req = "select * from club"; //requete select from db
            Statement st = MyConnection.getInstance().getCnx().createStatement(); //instance of myConnection pour etablir la cnx
            ResultSet rs = st.executeQuery(req); //resultat de la requete

            //tant que rs has next get matiere and add it to the list
            while (rs.next()) {
                Club club = new Club();
                club.setClubId(rs.getString(1)); //set id from req result
                club.setClubCategorie(rs.getInt(2));
                club.setClubRespo(rs.getInt(3));
                club.setClubName(rs.getString(4));
                club.setClubDesc(rs.getString(5));
                club.setClubPic(rs.getString(6));
                clubList.add(club); //ajout de la matiere a la liste
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return clubList;
    }

    public void updateClub(Club club, int currentId) {
        String req = "update club set club_categorie_id=?,club_responsable_id=?,club_nom=?,club_description=?,club_pic=? WHERE id=?";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req);
            pst.setInt(1, club.getClubCategorie());
            pst.setInt(2, club.getClubRespo());
            pst.setString(3, club.getClubName());
            pst.setString(4, club.getClubDesc());
            pst.setString(5, club.getClubPic());
            pst.setInt(6, currentId);
            pst.executeUpdate();
            System.out.println("Club updated");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void deleteClub(int catgId) {
        String req = "delete from club where id = ?";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req);
            pst.setInt(1, catgId);
            pst.executeUpdate();
            System.out.println("Club deleted");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
