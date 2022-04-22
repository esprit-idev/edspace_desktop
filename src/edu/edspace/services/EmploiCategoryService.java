package edu.edspace.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.edspace.entities.CategoryEmploi;
import edu.edspace.utils.MyConnection;
public class EmploiCategoryService {
    
    Connection connection = MyConnection.getInstance().getCnx();
    ResultSet resultSet;
    PreparedStatement preparedStatement;
    String query = null;

    // list all cat
    public List<CategoryEmploi> AllCats(){
        List<CategoryEmploi> listCats = new ArrayList<>();
        try {
			// String query all publications 
			query = "SELECT * FROM `categorie_emploi` " ;
            resultSet = connection.createStatement().executeQuery(query);
			while (resultSet.next()) {
				CategoryEmploi pub = new CategoryEmploi();
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
    public void addCat(CategoryEmploi pub){
        try{ 
             query = "INSERT INTO categorie_emploi ( category_name) VALUES (?)";
             preparedStatement = connection.prepareStatement(query);
             preparedStatement.setString(1, pub.getCategoryName());
             preparedStatement.execute();

     }catch(SQLException ex){
         ex.getStackTrace();
     }
    }

    //update 
    public void updateCat(String cat, int id){
        try {
            query = "UPDATE `categorie_emploi` SET" +
            "`category_name` = ?" +
            " WHERE `id` = ? ";
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, cat);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();

            System.out.println("updated");
        } catch (SQLException ex) {
            ex.getStackTrace();
        }
    }

    //delete


    public void deleteCat(int id){
        try {
            query = "DELETE FROM `categorie_emploi` WHERE id= ? ";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            System.out.println("deleted");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
