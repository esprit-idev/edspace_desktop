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
                ClasseService cs=new ClasseService();
                n.setNbClasse(cs.listeClassesByNiveau(rs.getString("id")).size());
               // System.out.println(n);
            	list.add(n);
            }
		}catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
		
		return list;
	}
	
	/*public void modifierNiveau(String id,String id2) {
		 String req = "update niveau set id=? WHERE id=?";
		try {
			PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req);
			pst.setString(1, id2);
	         pst.setString(2, id);
                 
                 	             ClasseService cs=new ClasseService();
                 List<Classe> x=cs.listeClassesByNiveau(id);
                 	           
	                cs.modifNiveau(cs.listeClassesByNiveau(id));
	           
                 
	         pst.executeUpdate();
	            System.out.println("Niveau modifié");
                    Niveau v=new Niveau(id2);
                    
                     for (Classe temp : x) {
                         temp.setNiveau(v);
	                cs.modifierClasse(temp);
	            }
                    
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         
	}*/
        
        
        public void modifierNiveau(String id, String id2) {
			 String req = "update niveau set id=? WHERE id=?";
		try {
                    ClasseService cs=new ClasseService();
                                     List<Classe> x=cs.listeClassesByNiveau(id);
                                     cs.modifNiveau(x);
			PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req);
			pst.setString(1, id2);
	         pst.setString(2, id);
                 
                 	             
 pst.executeUpdate();
                 	           
	                cs.modifNiveau2(x,id2);
	           
                 
	        
	            System.out.println("Niveau modifié");
                    Niveau v=new Niveau(id2);
                    
                     for (Classe temp : x) {
                         temp.setNiveau(v);
	                cs.modifierClasse(temp);
	            }
                    
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}}
	
	
	 public void supprimerNiveau(Niveau niveau) {
	        String req = "delete from niveau where id = ?";
	        try {
	            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req);
	            pst.setString(1, niveau.getId());
	            ClasseService cs=new ClasseService();
	            for (Classe temp : cs.listeClassesByNiveau(niveau.getId())) {
	                cs.supprimerClasse(temp.getId());
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
	 
	 
	 //////////////autofill///
	
	 
	 /*
	  * 
	  * String[] ln= {"0","1","1","1","5twig","java"};
	int[] lc= {0,2,3,2,1,2,1};

	cs.autofillA(ln, lc);
	  */

	 public void autofillA(String[] ln,int[] lc) {
		 for(int i=1;i<ln.length;i++){
			 ClasseService cs=new ClasseService();
			 
			 if (!ln[i].isEmpty()) {
				 if(i<4) {
				 if(getOneById(i+"A")!=null) {
					 Niveau n=new Niveau(i+"A");
					 ajouterNiveau(n);
					 for(int j=1;j<lc[i]+1;j++){
						// System.out.println(lc[i]);
						// System.out.println(j);
						 Classe c=new Classe();
						 c.setClasse(Integer.toString(j));
						 c.setNiveau(n);
						 cs.ajouterClasse(c);
					 }
				 }
				 if(getOneById(i+"B")!=null) {
					 Niveau n2=new Niveau(i+"B");
					 ajouterNiveau(n2);
					 for(int j=1;j<lc[i]+1;j++){
						// System.out.println(lc[i]);
						// System.out.println(j);
						 Classe c=new Classe();
						 c.setClasse(Integer.toString(j));
						 c.setNiveau(n2);
						 cs.ajouterClasse(c);
					 }
				 
			 }}else {
				 Boolean flag = Character.isDigit(ln[i].charAt(0));
		         if(flag) {
		        	 Niveau n=new Niveau(ln[i]);
					 ajouterNiveau(n);
					 for(int j=1;j<lc[i]+1;j++) {
						 
						 Classe c=new Classe();
						 c.setClasse(Integer.toString(j));
						 c.setNiveau(n);
						 cs.ajouterClasse(c);
		            
		         }
		         } else {
		            System.out.println("'"+ ln[i].charAt(0)+"' is a letter");
		         }
				 
				 
			 }
 
			 
		 }
		 
	 }
	 }
	 
	 
	 //////////////////////////////////////
	 
         
         
}
