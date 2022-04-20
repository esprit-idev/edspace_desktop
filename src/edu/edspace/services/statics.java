package edu.edspace.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.edspace.utils.MyConnection;

public class statics {

    Connection connection = MyConnection.getInstance().getCnx();
    ResultSet resultSet;
    PreparedStatement preparedStatement;
    String query = null;
    Integer total = 0;

    //number of news by category
    public int numberOfPubsByCategory(int id){
        total = 0;
        try {
            query = "SELECT p.category_name_id from publication_news p INNER JOIN categorie_news c ON p.category_name_id = c.id where c.id =" + id;
            resultSet = connection.createStatement().executeQuery(query);
            while(resultSet.next()){
                total++;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }
    //number of emploi by category 
    public int numberOfEmploisByCategory(int id){
        total = 0;
        try {
            query = "SELECT p.category_name_id from emploi p INNER JOIN categorie_emploi c ON p.category_name_id = c.id where c.id =" + id;
            resultSet = connection.createStatement().executeQuery(query);
            while(resultSet.next()){
                total++;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }
    //number of news
    public String numberOfPublications(){
        total = 0;
        try {
            query = "Select count(*) from publication_news";
            resultSet = connection.createStatement().executeQuery(query);
            while(resultSet.next()){
                total = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total.toString();
        
    }
    //number of emploi
    public String numberOfOffreEmploi(){
        total = 0;
        try {
            query = "Select count(*) from emploi";
            resultSet = connection.createStatement().executeQuery(query);
            while(resultSet.next()){
                total = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total.toString();
        
    }
    //number of clubs
    public String numberOfClubs(){
        try {
            query = "Select count(*) from club";
            resultSet = connection.createStatement().executeQuery(query);
            while(resultSet.next()){
                total = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total.toString(); 
    }
    //number of classes 
    public int numberOfClasses(){
        try {
            query = "Select count(*) from classe";
            resultSet = connection.createStatement().executeQuery(query);
            while(resultSet.next()){
                total = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total; 
    }
    //number of niveau
    public String numberOfNiveau(){
        try {
            query = "Select count(*) from niveau";
            resultSet = connection.createStatement().executeQuery(query);
            while(resultSet.next()){
                total = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total.toString(); 
    }
    // number of matiere 
    public int numberOfMatiere(){
        try {
            query = "Select count(*) from matiere";
            resultSet = connection.createStatement().executeQuery(query);
            while(resultSet.next()){
                total = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total; 
    }
    //number of documents
    public int numberOfDocuments(){
        try {
            query = "Select count(*) from documents";
            resultSet = connection.createStatement().executeQuery(query);
            while(resultSet.next()){
                total = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total; 
    }
    //number of Threads 
    public int numberOfThreads(){
        try {
            query = "Select count(*) from thread";
            resultSet = connection.createStatement().executeQuery(query);
            while(resultSet.next()){
                total = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total; 
    }
    //number of replies in threads 
    public int numberOfReplies(){
        try {
            query = "Select count(*) from reponse";
            resultSet = connection.createStatement().executeQuery(query);
            while(resultSet.next()){
                total = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total; 
    }
    // number of Students 
    public String numberOfStudents(String r){
        try {
            query = "Select count(*) from user where roles like '" + r +"'";
            resultSet = connection.createStatement().executeQuery(query);
            while(resultSet.next()){
                total = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total.toString(); 
    }
    // number of categories in news
    public int numberOfCategoryNews(){
        total = 0;
        try {
            query = "Select count(*) from categorie_news";
            resultSet = connection.createStatement().executeQuery(query);
            while(resultSet.next()){
                total = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
        
    }
    // number of categories in emplois
    public int numberOfCategoryNewsemploi(){
        try {
            query = "Select count(*) from categorie_emploi";
            resultSet = connection.createStatement().executeQuery(query);
            while(resultSet.next()){
                total = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total; 
    }
    // number of categories in clubs
    public int numberOfCategoryClub(){
        try {
            query = "Select count(*) from categorie_club";
            resultSet = connection.createStatement().executeQuery(query);
            while(resultSet.next()){
                total = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total; 
    }
    // numbers of likes of each news publication 
    public int numberOfLikes(int id){
        try {
            query = "SELECT likes from publication_news where id=" + id;
            resultSet = connection.createStatement().executeQuery(query);
            while(resultSet.next()){
                total = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }
    // numbers of likes of each views publication 
    public int numberOfViews(int id){
        try {
            query = "SELECT vues from publication_news where id=" + id;
            resultSet = connection.createStatement().executeQuery(query);
            while(resultSet.next()){
                total = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }
}
