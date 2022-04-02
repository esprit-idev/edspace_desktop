/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.edspace.services;

import edu.edspace.entities.Matiere;
import edu.edspace.utils.MyConnection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MeriamBI
 */
public class MatiereService {

    public void ajouterMatiere(Matiere matiere) {
        try {
            String req = "insert into matiere (id,niveau_id) values"
                    + "(?,?)"; //requete d'inertion avec parametres

            /*PreparedStatement used with dynamic requests+faster and more secure than Statement (used in the method above)
                can't drop or alter with PreparedStatement*/
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req); //instance of myconnection pour etablir la cnx
            pst.setString(1, matiere.getId()); //parameter1=index in request(req) and parameter2=data to pass (nom de la personne)
            pst.setString(2, matiere.getNiveau()); //parameter1=index in request(req) and parameter2=data to pass (prenom de la personne)
            pst.executeUpdate(); //pour exécuter la requete
            System.out.println("Matière ajoutée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List<Matiere> listMatieres() {
        String req = "select * from matiere"; //requete select from db
        return getMatieresList(req);
    }

    public void modifierMatiere(Matiere matiere,String oldId) {
        String req = "update matiere set id=?, niveau_id=? WHERE id=?";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req);
            pst.setString(1, matiere.getId());
            pst.setString(2, matiere.getNiveau());
            pst.setString(3, oldId);
            pst.executeUpdate();
            System.out.println("Matière modifiée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void supprimerMatiere(Matiere matiere) {
        String req = "delete from matiere where id = ?";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req);
            pst.setString(1, matiere.getId());
            pst.executeUpdate();
            System.out.println("Matière supprimée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List<Matiere> filterByNiveau(String niveau) {
        String req = "select * from matiere where niveau_id='"+niveau+"'"; //requete select from db
        return getMatieresList(req);
    }
    
    public List<Matiere> getMatieresList(String req) {
        List<Matiere> myList = new ArrayList<>();
        try {
            Statement st = MyConnection.getInstance().getCnx().createStatement(); //instance of myConnection pour etablir la cnx
            ResultSet rs = st.executeQuery(req); //resultat de la requete

            //tant que rs has next get matiere and add it to the list
            while (rs.next()) {
                Matiere m = new Matiere();
                m.setId(rs.getString(1)); //set id from req result
                m.setId(rs.getString("id")); //set nom from req result
                m.setNiveau(rs.getString("niveau_id")); //matiere id from req result
                myList.add(m); //ajout de la matiere a la liste
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }
}
