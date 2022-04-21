/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.edspace.services;

import edu.edspace.entities.ClubCategory;
import edu.edspace.utils.MyConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

/**
 *
 * @author anash
 */
public class ClubCategService {

    public boolean ajouterClubCat(ClubCategory clubCategory) {
                boolean res = false;

        try {
            String req = "insert into categorie_club (categorie_nom) values"
                    + "(?)"; //requete d'inertion avec parametres

            /*PreparedStatement used with dynamic requests+faster and more secure than Statement (used in the method above)
                can't drop or alter with PreparedStatement*/
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req); //instance of myconnection pour etablir la cnx
            pst.setString(1, clubCategory.getCategorie()); //parameter1=index in request(req) and parameter2=data to pass (nom de la personne)
            pst.executeUpdate(); //pour exécuter la requete
            System.out.println("ClubCategory added");
            res=true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return res;
    }

    public List<ClubCategory> displayClubCategories() {
        List<ClubCategory> clubCategList = new ArrayList<>();
        try {
            String req = "select * from categorie_club"; //requete select from db
            Statement st = MyConnection.getInstance().getCnx().createStatement(); //instance of myConnection pour etablir la cnx
            ResultSet rs = st.executeQuery(req); //resultat de la requete

            //tant que rs has next get matiere and add it to the list
            while (rs.next()) {
                ClubCategory clubCategory = new ClubCategory();
                clubCategory.setId(rs.getString(1)); //set id from req result
                clubCategory.setCategorie(rs.getString(2)); //set categorie from req result
                clubCategList.add(clubCategory); //ajout de la matiere a la liste
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return clubCategList;
    }

    public boolean updateClubCategories(String catName, String currentId) {
        boolean res = false;
        String req = "update categorie_club set categorie_nom=? WHERE id=?";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req);
            pst.setString(1, catName);
            pst.setString(2, currentId);
            pst.executeUpdate();
            System.out.println("ClubCategory updated");
            res=true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return res;
    }

    public boolean deleteClubCategories(String catgId) {
        boolean res = false;
        String req = "delete from categorie_club where id = ?";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req);
            pst.setString(1, catgId);
            pst.executeUpdate();
            System.out.println("ClubCategory deleted");
            res = true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return res;
    }

    public void getCategories(ObservableList<ClubCategory> categoriesList, TableView<ClubCategory> tab) {
        categoriesList.clear();

        try {
            String req = "select * from categorie_club"; //requete select from db
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req); //instance of myConnection pour etablir la cnx
            ResultSet rs = pst.executeQuery(req); //resultat de la requete

            //tant que rs has next get matiere and add it to the list
            while (rs.next()) {
                ClubCategory category = new ClubCategory();
                category.setId(rs.getString("id")); //set id from req result
                category.setCategorie(rs.getString("categorie_nom"));

                categoriesList.add(category); //ajout de la matiere a la liste
                tab.setItems(categoriesList);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

}
