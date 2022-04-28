/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.edspace.services;

import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;
import java.sql.PreparedStatement;
import edu.edspace.entities.News;
import edu.edspace.utils.MyConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 *
 * @author eslem
 */
public class NewsService {

    Connection connection = MyConnection.getInstance().getCnx();
    ResultSet resultSet;
    PreparedStatement preparedStatement;
    String query = null;
    // list all publicatio news
    public List<News> AllNews(){
        List<News> listNews = new ArrayList<>();
        try {
			// String query all publications 
			query = "SELECT publication_news.*, categorie_news.category_name as catName FROM publication_news JOIN categorie_news on publication_news.category_name_id = categorie_news.id" ;
            resultSet = connection.createStatement().executeQuery(query);
			while (resultSet.next()) {
				News pub = new News();
				pub.setId(resultSet.getInt(1));
                pub.setCategoryName(resultSet.getString("catName"));
                pub.setDate(resultSet.getString(3));
                pub.setTitle(resultSet.getString(4));
                pub.setOwner(resultSet.getString(5));
                pub.setImage(resultSet.getString(6));
                pub.setContent(resultSet.getString(10));
				listNews.add(pub);
			}
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
        return listNews;
    }
// add a publication 
    public void addNews(News pub){
           try{ 
                query = "INSERT INTO publication_news (title, owner, content, category_name_id, date, image) VALUES (?,?,?,?,?,?)";
                preparedStatement = connection.prepareStatement(query);

                preparedStatement.setString(1, pub.getTitle());
                preparedStatement.setString(2, pub.getOwner());
                preparedStatement.setString(3, pub.getContent());
                preparedStatement.setString(4, pub.getCategoryName());
                preparedStatement.setString(5, pub.getDate());
                preparedStatement.setString(6, pub.getImage());
                preparedStatement.executeUpdate();

        }catch(SQLException ex){
            System.out.println(ex.getMessage());;
        }
    }
//update news 
    public void updateNews(News pub, int id){
        try {
            query = "UPDATE publication_news SET " +
                    "title=?," + 
		            "content=?," + 
		            "image=?," + 
                    "date= ?,"+ 
                    "owner=?," +
                    "category_name_id=?" +
                    "WHERE id =?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, pub.getTitle());
            preparedStatement.setString(2, pub.getContent());
            preparedStatement.setString(3, pub.getImage());
            preparedStatement.setString(4, pub.getDate());
            preparedStatement.setString(5, pub.getOwner());
            preparedStatement.setString(6, pub.getCategoryName());

            preparedStatement.setInt(7, id);
            preparedStatement.executeUpdate();

            System.out.println("updated");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
//delete news
    public void deleteNews(int id){
        try {
            query = "DELETE FROM publication_news WHERE id= ? ";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            System.out.println("deleted");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<News> AllNewsforFilter(String req){
        List<News> listNews = new ArrayList<>();
        listNews.clear();
        try {
			// String query all publications 
			query = req ;
            resultSet = connection.createStatement().executeQuery(query);
			while (resultSet.next()) {
				News pub = new News();
				pub.setId(resultSet.getInt(1));
                pub.setCategoryName(resultSet.getString(2));
                pub.setDate(resultSet.getString(3));
                pub.setTitle(resultSet.getString(4));
                pub.setOwner(resultSet.getString(5));
                pub.setImage(resultSet.getString(6));
                pub.setContent(resultSet.getString(10));
				listNews.add(pub);
			}
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
        return listNews;
    }
public List<News> filterByCat(int cat) {
    String req = "SELECT * from publication_news where category_name_id='"+cat+"'";
    return AllNewsforFilter(req);
}
public List<News> SearchPbulications(){
    List<News> listNews = new ArrayList<>();
    //listNews.clear();
    try {
        // String query all publications 
        query = "select title from publication_news";
        resultSet = connection.createStatement().executeQuery(query);
        while (resultSet.next()) {
            News pub = new News();
            pub.setId(resultSet.getInt(1));
            pub.setCategoryName(resultSet.getString(2));
            pub.setDate(resultSet.getString(3));
            pub.setTitle(resultSet.getString(4));
            pub.setOwner(resultSet.getString(5));
            pub.setImage(resultSet.getString(6));
            pub.setContent(resultSet.getString(10));
            listNews.add(pub);
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return listNews;
}
public List<News> SearchPbulications(String title){
    List<News> listNews = new ArrayList<>();
    //listNews.clear();
    try {
        // String query all publications 
        query = "select * from publication_news where title ='"+title+"'";
        resultSet = connection.createStatement().executeQuery(query);
        while (resultSet.next()) {
            News pub = new News();
            pub.setId(resultSet.getInt(1));
            pub.setCategoryName(resultSet.getString(2));
            pub.setDate(resultSet.getString(3));
            pub.setTitle(resultSet.getString(4));
            pub.setOwner(resultSet.getString(5));
            pub.setImage(resultSet.getString(6));
            pub.setContent(resultSet.getString(10));
            listNews.add(pub);
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return listNews;
}
public News findById(int id){
    News news = new News();
    //listNews.clear();
    try {
        // String query all publications 
        query = "select * from publication_news where id ='"+id+"'";
        resultSet = connection.createStatement().executeQuery(query);
        while (resultSet.next()) {
            News pub = new News();
            pub.setId(resultSet.getInt(1));
            pub.setCategoryName(resultSet.getString(2));
            pub.setDate(resultSet.getString(3));
            pub.setTitle(resultSet.getString(4));
            pub.setOwner(resultSet.getString(5));
            pub.setImage(resultSet.getString(6));
            pub.setContent(resultSet.getString(10));
            news = pub;
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return news;
}
public void updateLikes(int likes, int id){
    try {
        query = "update publication_news set likes=? WHERE id=?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, likes);
        preparedStatement.setInt(2, id);
        preparedStatement.executeUpdate();
        System.out.println("updated likes");
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}
public List<News> LimitAllNews(){
    List<News> listNews = new ArrayList<>();
    try {
        // String query all publications 
        query = "SELECT publication_news.*, categorie_news.category_name as catName FROM publication_news JOIN categorie_news on publication_news.category_name_id = categorie_news.id limit 3" ;
        resultSet = connection.createStatement().executeQuery(query);
        while (resultSet.next()) {
            News pub = new News();
            pub.setId(resultSet.getInt(1));
            pub.setCategoryName(resultSet.getString("catName"));
            pub.setDate(resultSet.getString(3));
            pub.setTitle(resultSet.getString(4));
            pub.setOwner(resultSet.getString(5));
            pub.setImage(resultSet.getString(6));
            pub.setContent(resultSet.getString(10));
            listNews.add(pub);
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return listNews;
}
}
