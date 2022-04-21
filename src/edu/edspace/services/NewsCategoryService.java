package edu.edspace.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.edspace.entities.CategoryNews;
import edu.edspace.utils.MyConnection;

public class NewsCategoryService {
    Connection connection = MyConnection.getInstance().getCnx();
    ResultSet resultSet;
    PreparedStatement preparedStatement;
    String query = null;

    // list all cat news
    public List<CategoryNews> AllCats(){
        List<CategoryNews> listCats = new ArrayList<>();
        try {
			// String query all publications 
			query = "SELECT * FROM `categorie_news` " ;
            resultSet = connection.createStatement().executeQuery(query);
			while (resultSet.next()) {
				CategoryNews pub = new CategoryNews();
				pub.setId(resultSet.getInt(1));
                pub.setCategoryName(resultSet.getString(2));
				listCats.add(pub);
			}
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
        return listCats;
    }

    // add a category
    public void addCat(CategoryNews pub){
        try{ 
             query = "INSERT INTO categorie_news ( category_name) VALUES (?)";
             preparedStatement = connection.prepareStatement(query);
             preparedStatement.setString(1, pub.getCategoryName());
             preparedStatement.execute();

     }catch(SQLException ex){
         ex.getStackTrace();
     }
    }

    //update news 
    public void updateCat(int id, String categoryName){
        try {
            query = "UPDATE `categorie_news` SET" +
            "`category_name` = ?" +
            " WHERE `id` = ? ";
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, categoryName);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();

            System.out.println("updated");
        } catch (SQLException ex) {
            ex.getStackTrace();
        }
    }

    //delete news

    public void deleteCat(int id){
        try {
            query = "DELETE FROM `categorie_news` WHERE id= ? ";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            System.out.println("deleted");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
        // list all cat news
        public List<CategoryNews> AllCatsNames(){
            List<CategoryNews> listCats = new ArrayList<>();
            try {
                // String query all publications 
                query = "SELECT category_name FROM `categorie_news` " ;
                resultSet = connection.createStatement().executeQuery(query);
                while (resultSet.next()) {
                    CategoryNews pub = new CategoryNews();
                    pub.setCategoryName(resultSet.getString(1));
                    listCats.add(pub);
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            return listCats;
        }
    
}
