/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.edspace.services;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import edu.edspace.entities.Emploi;
import edu.edspace.utils.MyConnection;

/**
 *
 * @author eslem
 */
public class EmploiService {

    Connection connection = MyConnection.getInstance().getCnx();
    ResultSet resultSet;
    PreparedStatement preparedStatement;
    String query = null;
    
    //display all offers 

    public List<Emploi> AllEmplois(){
        List<Emploi> listEmplois = new ArrayList<>();
        try {
            query = "SELECT * FROM `emploi`";
            resultSet = connection.createStatement().executeQuery(query);
            while(resultSet.next()){
                Emploi em = new Emploi();
                em.setId(resultSet.getInt(1));
                em.setCategoryName(resultSet.getString(2));
                em.setTitle(resultSet.getString(3));
                em.setContent(resultSet.getString(4));
                em.setDate(resultSet.getString(5));
                em.setImage(resultSet.getString(6));
                listEmplois.add(em);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return listEmplois;
    }

    //add an offer 

    public void addEmploi(Emploi em){
        try {
            query = "INSERT INTO emploi (category_name_id, title, content, date, image) VALUES (?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, em.getCategoryName());
            preparedStatement.setString(2, em.getTitle());
            preparedStatement.setString(3, em.getContent());
            preparedStatement.setString(4, em.getDate());
            preparedStatement.setString(5, em.getImage());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //update an offer 
    public void updateEmploi(Emploi em){
        try {
            query = "UPDATE emploi SET " +
            "title=?," +
            "content=?," +
            "category_name_id=?," +
            "date=?," +
            "image=?" +
            "WHERE id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, em.getTitle());
            preparedStatement.setString(2, em.getContent());
            preparedStatement.setString(3, em.getCategoryName());
            preparedStatement.setString(4, em.getDate());
            preparedStatement.setString(5, em.getImage());
            preparedStatement.setInt(6, em.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //delete an offer 

    public void deleteOffer(int id){
        try {
            query = "DELETE FROM emploi WHERE id= ? ";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            System.out.println("deleted");
        } catch (SQLException e) {
            e.notify();
        }
    }
}
