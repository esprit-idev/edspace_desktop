package edu.edspace.services;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import edu.edspace.entities.Classe;
import edu.edspace.entities.Document;
import edu.edspace.entities.Niveau;
import edu.edspace.utils.MyConnection;

public class NiveauService {
	
	public void ajouterNiveau(Niveau niveau) {
		try {
			String req = "insert into niveau (id) values"
                    + "(?)";
			PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, niveau.getId());
			pst.executeUpdate();
			 System.out.println("Niveau ajouté");
			
		}catch(SQLException ex) {
			 System.out.println(ex.getMessage());
		}
	}
	
	
	
	public List<Niveau> listeNiveaux(){
		List<Niveau> list=new ArrayList<>();
		
		try {
			String req = "select * from niveau"; 
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
            	Niveau n=new Niveau();
            	n.setId(rs.getString("id"));
            	list.add(n);
            }
		}catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
		
		return list;
	}
	
	public void modifierNiveau(Niveau niveau) {
		 String req = "update niveau set id=? WHERE id=?";
		try {
			PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req);
			pst.setString(1, niveau.getId());
	         pst.setString(2, niveau.getId());
	         pst.executeUpdate();
	            System.out.println("Niveau modifié");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         
	}
	
	
	 public void supprimerNiveau(Niveau niveau) {
	        String req = "delete from niveau where id = ?";
	        try {
	            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req);
	            pst.setString(1, niveau.getId());
	            ClasseService cs=new ClasseService();
	            for (Classe temp : cs.listeClassesByNiveau(niveau.getId())) {
	                cs.supprimerClasse(temp);
	            }
	            pst.executeUpdate();
	            
	            System.out.println("niveau supprimé");
	        } catch (SQLException ex) {
	            System.out.println(ex.getMessage());
	        }
	    }
	
	 
	 public Niveau getOneById(String id) {
		 Niveau n=new Niveau();
		 n.setId("-1");
		 
		 try {
			 String req = "select * from niveau where id= ?";
	            
	            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req);
				 pst.setString(1,id);
			
		            ResultSet rs = pst.executeQuery();
	            
	            while (rs.next()) {
	            	n.setId(rs.getString("id"));
	            
	            }
			}catch (SQLException ex) {
	            System.out.println(ex.getMessage());
	        }
		 
		 return n;
	 }

}
