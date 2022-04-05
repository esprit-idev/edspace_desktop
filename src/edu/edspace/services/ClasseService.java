package edu.edspace.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import edu.edspace.entities.Classe;
import edu.edspace.entities.Niveau;
import edu.edspace.entities.User;
import edu.edspace.utils.MyConnection;

public class ClasseService {
	
	public void ajouterClasse(Classe classe) {
		try {
			String req = "insert into classe (niveau_id,classe) values"
                    + "(?,?)";
			PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, classe.getNiveau().getId());
			pst.setString(2, classe.getClasse());
			
			
			
			if(this.CheckExist(this.listeClasses(), classe)) {
			
				System.out.println("deja exits");
			}
			else {
				
				pst.executeUpdate();
				 System.out.println("Classe ajouté");
			}
		}catch(SQLException ex) {
			 System.out.println(ex.getMessage());
		}
	}
	
	
	
	public List<Classe> listeClasses(){
		List<Classe> list=new ArrayList<>();
		
		try {
			String req = "select * from classe"; 
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(req);
            NiveauService ns=new NiveauService();
            while (rs.next()) {
            	Classe c=new Classe();
            	c.setId(rs.getInt("id"));
            	c.setClasse(rs.getString("classe"));
            	c.setNiveau(ns.getOneById(rs.getString("niveau_id")));
            	list.add(c);
            }
		}catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
		
		return list;
	}
	
	
	public void modifierClasse(Classe classe) {
		 String req = "update classe set niveau_id=?, classe=? WHERE id=?";
		try {
			NiveauService ns=new NiveauService();
			PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req);
			pst.setString(1, classe.getNiveau().getId());
	         pst.setString(2, classe.getClasse());
	         pst.setInt(3, classe.getId());
	         pst.executeUpdate();
	            System.out.println("Classe modifié");
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
	}
	
	
	public void supprimerClasse(Classe classe) {
        String req = "delete from classe where id = ?";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req);
            pst.setInt(1, classe.getId());
            
            modifClasse(listUserClasse(classe.getId()));
            
            pst.executeUpdate();
            
            System.out.println("Classe supprimé");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
	
	
	
	 public Classe getOneById(int id) {
		 Classe c=new Classe();
		 c.setId(-1);
		 
		 try {
			 NiveauService ns=new NiveauService();
			 String req = "select * from classe where id= ?";
			 PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req);
			 pst.setInt(1,id);
		
	            ResultSet rs = pst.executeQuery();
	            while (rs.next()) {
	            	c.setId(rs.getInt("id"));
	            	c.setClasse(rs.getString("classe"));
	            	c.setNiveau(ns.getOneById(rs.getString("niveau_id")));
	            }
			}catch (SQLException ex) {
	            System.out.println(ex.getMessage());
	        }
		 
		 return c;
	 }
	 
	 
	 public boolean CheckExist(List<Classe> l,Classe classe){
		 boolean i=false;
		 for (Classe temp : l) {
			 if(temp.getNiveau().getId().equals(classe.getNiveau().getId())) {
				 if(temp.getClasse().equals(classe.getClasse())) {
					 i=true;
				 }
			 }
	            
	        }
		 
		 return i;
	 }
	 
		public List<Classe> listeClassesByNiveau(String id){
			List<Classe> list=new ArrayList<>();
			
			try {
				String req = "select * from classe where niveau_id=?"; 
				 PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req);
				 pst.setString(1,id);
		            ResultSet rs = pst.executeQuery();
	            NiveauService ns=new NiveauService();
	            while (rs.next()) {
	            	Classe c=new Classe();
	            	c.setId(rs.getInt("id"));
	            	c.setClasse(rs.getString("classe"));
	            	c.setNiveau(ns.getOneById(rs.getString("niveau_id")));
	            	list.add(c);
	            }
			}catch (SQLException ex) {
	            System.out.println(ex.getMessage());
	        }
			
			return list;
		}
		
		
		
		public List<User> listUserClasse(int id){
List<User> list=new ArrayList<>();
			
			try {
				String req = "select * from user where classe_id=?"; 
				 PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req);
				 pst.setInt(1,id);
		            ResultSet rs = pst.executeQuery();
	            while (rs.next()) {
	            	User c=new User();
	            	c.setId(rs.getInt("id"));
	            	list.add(c);
	            }
			}catch (SQLException ex) {
	            System.out.println(ex.getMessage());
	        }
			
			return list;
			
		}
		
		public void modifClasse(List<User> l) {
			
			for (User temp : l) {
			 String req = "update classe set classe_id=? WHERE id=?";
				try {
					NiveauService ns=new NiveauService();
					PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req);
					pst.setInt(1,);
			         pst.executeUpdate();
			            System.out.println("User modifié");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
		}
	

}
