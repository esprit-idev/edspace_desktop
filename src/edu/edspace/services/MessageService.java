package edu.edspace.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.sql.*;

import edu.edspace.entities.Classe;
import edu.edspace.entities.Message;
import edu.edspace.entities.User;
import edu.edspace.utils.MyConnection;

public class MessageService {
	
	public void ajouterMessage(Message message) {
		try {
			String req = "insert into message (classe_id,user_id,content,post_date) values"
                    + "(?,?,?,?)";
			PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
			pst.setInt(1, message.getClasse().getId());
			pst.setInt(2, message.getUser().getId());
			pst.setString(3, message.getContent());
			java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
			pst.setDate(4,date);
			pst.executeUpdate();
			 System.out.println("Message ajouté");
		}catch(SQLException ex) {
			 System.out.println(ex.getMessage());
		}
		
		
		
	}
	
	public List<Message> listeMessage(){
		List<Message> list=new ArrayList<>();
		
		try {
			
			String req = "select * from Message"; 
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(req);
            ClasseService ns=new ClasseService();
            MessageService us=new MessageService();
            
            while (rs.next()) {
            	
            	Message m=new Message();
            	m.setId(rs.getInt("id"));
            	m.setContent(rs.getString("content"));
            	Date d1 = new Date(rs.getDate("post_date").getTime());
            	m.setPostDate(d1);
            	m.setClasse(ns.getOneById(rs.getInt("classe_id")));
            	m.setUser(us.getuser(rs.getInt("classe_id")));
            	
            	list.add(m);
            }
		}catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
		
		return list;
	}

	
	
	
	public List<Message> listeMessageClasse(int c){
		List<Message> list=new ArrayList<>();
		
		try {
			String req = "select * from Message where classe_id=?"; 
			PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, c);
            ClasseService ns=new ClasseService();
            MessageService us=new MessageService();
          
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
            	
            	Message m=new Message();
            	m.setId(rs.getInt("id"));
            	m.setContent(rs.getString("content"));
            	Date d1 = new Date(rs.getDate("post_date").getTime());
            	m.setPostDate(d1);
            	m.setClasse(ns.getOneById(rs.getInt("classe_id")));
            	m.setUser(us.getuser(rs.getInt("user_id")));
            	list.add(switcher(m));
            }
		}catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
		
		return list;
	}
        
        
        public List<Message> listeMyMessageClasse(int c){
		List<Message> list=new ArrayList<>();
		
		try {
			String req = "select * from Message where user_id=?"; 
			PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, c);
            ClasseService ns=new ClasseService();
            MessageService us=new MessageService();
          
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
            	
            	Message m=new Message();
            	m.setId(rs.getInt("id"));
            	m.setContent(rs.getString("content"));
            	Date d1 = new Date(rs.getDate("post_date").getTime());
            	m.setPostDate(d1);
            	m.setClasse(ns.getOneById(rs.getInt("classe_id")));
            	m.setUser(us.getuser(rs.getInt("user_id")));
            	list.add(switcher(m));
            }
		}catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
		
		return list;
	}
        
        
        
        public List<Message> listeOtherMessageClasse(int c){
		List<Message> list=new ArrayList<>();
		
		try {
			String req = "select * from Message where user_id<>?"; 
			PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, c);
            ClasseService ns=new ClasseService();
            MessageService us=new MessageService();
          
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
            	
            	Message m=new Message();
            	m.setId(rs.getInt("id"));
            	m.setContent(rs.getString("content"));
            	Date d1 = new Date(rs.getDate("post_date").getTime());
            	m.setPostDate(d1);
            	m.setClasse(ns.getOneById(rs.getInt("classe_id")));
            	m.setUser(us.getuser(rs.getInt("classe_id")));
            	list.add(m);
            }
		}catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
		
		return list;
	}
	
	
	
	
	
	
	public User getuser(int id) {
		User u=new User();
		u.setId(-1);
		try {
		 String req = "select * from user where id= ?";
		 PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req);
		 pst.setInt(1,id);
	
           ResultSet rs = pst.executeQuery();
           while (rs.next()) {
           	u.setId(rs.getInt("id"));
        	u.setUsername(rs.getString("username"));
        	u.setPrenom(rs.getString("prenom"));
        	u.setEmail(rs.getString("email"));
           }
           return u;
	}catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
		return u;
	}
	
	
	
	
	
	public int MessageNumber(int id) {
		int i=0;
		List<Message> list=listeMessageClasse(id);
		for (Message temp : list) {
            i++;
        }
		return i;
	}
	
	///::::::::::::: bad words detector:::::::::::///
	
	public String[] mots(){
		String[] l = null;
		String req = "select * from message where id= ?";
		 PreparedStatement pst;
		try {
			
			pst = MyConnection.getInstance().getCnx().prepareStatement(req);
			 pst.setInt(1,124);
				String str;
	           ResultSet rs = pst.executeQuery();
	           while (rs.next()) {
	        	   str=rs.getString("content");
	        	   l=str.split(" ");
	        	  //System.out.println(l[1]+" "+l[2]);
	        	   
	           }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return l;
		
	}
	
	
	public boolean detector(String s1,String s2) {
		if(s1.equals(s2)) {
	
			return true;
		}
		
		return false;

	}
	
	public Message switcher(Message m) {
		String[] tokens=m.getContent().split(" ");
		String text = "";
		for(String s:tokens) {
			
			String[] mot=mots();
			String test=s;
			for(String ss:mot) {
			if(detector(s,ss)) {
				test="****";
			}
			}
			text=text+" "+test;
		}
		
		m.setContent(text);
		return m;
	}
	///////////////////////////////////////////
	
	
	
	//:::::::::::::::::::::::search message::::
	/*public boolean mots1(String s1,String s2){
		String[] l1 = null;
	
		
			 

	        	   l1=s1.split(" ");
	        	   String[] l2=s2.split("(?!^)");
	        	   for (int i =0; i<l1.length;i++) {
	        		   if(l1[i].toUpperCase().contains(s2.toUpperCase())) {
	        			   return true;
	        		   }
	        		   
	        	   }
	        	   return false;
	        	   
	        	  //System.out.println(l[1]+" "+l[2]);
	        	   
	 
		
	}*/
        
        public boolean mots1(String s1,String s2){
		String[] l1 = null;
	
		
			 

	        	   l1=s1.split(" ");
	        	   String[] l2=s2.split("(?!^)");
	        	   for (int i =0; i<l1.length;i++) {
	        		   if(l1[i].toUpperCase().contains(s2.toUpperCase())) {
	        			   return true;
	        		   }
	        		   
	        	   }
	        	   return false;
	        	   
	        	  //System.out.println(l[1]+" "+l[2]);
	        	   
	 
		
	}
	
	////////////////function which u give it a label it and make it hidden
	public boolean find(String s1,String s2) {
		return(s1.contains(s2));
		
	};
	
	
	
}
