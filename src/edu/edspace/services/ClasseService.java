package edu.edspace.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import edu.edspace.entities.Classe;
import edu.edspace.entities.Message;
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
	
	
	public void supprimerClasse(int classe) {
        String req = "delete from classe where id = ?";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req);
            pst.setInt(1, classe);
            
            modifClasse(listUserClasse(classe));
        	MessageService ms=new MessageService();
            modifMessage(ms.listeMessageClasse(classe));
            
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
                        c.setUsername(rs.getString("username"));
                        c.setPrenom(rs.getString("prenom"));
                        c.setEmail(rs.getString("email"));
                        c.setRoles(rs.getString("roles"));
	            	list.add(c);
	            }
			}catch (SQLException ex) {
	            System.out.println(ex.getMessage());
	        }
			
			return list;
			
		}
                
                
                
                
                		public List<User> listUserNoClasse(int id){
List<User> list=new ArrayList<>();
			
			try {
				String req = "select * from user where classe_id <> ? or classe_id is null"; 
				 PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req);
				 pst.setInt(1,id);
		            ResultSet rs = pst.executeQuery();
	            while (rs.next()) {
	            	User c=new User();
	            	c.setId(rs.getInt("id"));
                        c.setUsername(rs.getString("username"));
                        c.setPrenom(rs.getString("prenom"));
                        c.setEmail(rs.getString("email"));
                        c.setRoles(rs.getString("roles"));
	            	list.add(c);
	            }
			}catch (SQLException ex) {
	            System.out.println(ex.getMessage());
	        }
			System.out.println(list);
			return list;
			
		}
                
                
		
		public void modifClasse(List<User> l) {
			
			for (User temp : l) {
			 String req = "update user set classe_id=? WHERE id=?";
				try {
					PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req);
					pst.setNull(1, Types.INTEGER);
					pst.setInt(2, temp.getId());
			         pst.executeUpdate();
			            System.out.println("User modifié");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		
				
			
			
		}
                
                /*public void modifNiveau(List<Classe> l) {
			
			for (Classe temp : l) {
			 String req = "update classe set niveau_id=? WHERE id=?";
				try {
					PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req);
					pst.setNull(1, Types.INTEGER);
					pst.setInt(2, temp.getId());
			         pst.executeUpdate();
			            System.out.println("Classe modifié");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		
				
			
			
		}
*/
                
                public void modifNiveau(List<Classe> l) {
			
			for (Classe temp : l) {
			 String req = "update classe set niveau_id=? WHERE id=?";
				try {
					PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req);
					pst.setNull(1, Types.INTEGER);
					pst.setInt(2, temp.getId());
			         pst.executeUpdate();
			            System.out.println("Classe modifié");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		
				
			
			
		}
                
		
		public void modifMessage(List<Message> l) {
			for (Message temp : l) {
				 String req = "update message set classe_id=? WHERE id=?";
					try {
						PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req);
						pst.setNull(1, Types.INTEGER);
						pst.setInt(2, temp.getId());
				         pst.executeUpdate();
				            System.out.println("Message modifié");
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			
		}
	
                
                
                public User getStudent(int id){
        User stu = new User();
        try {
            String req = "select * from user where id ="+id;
            Statement st = MyConnection.getInstance().getCnx().createStatement(); //instance of myConnection pour etablir la cnx
            ResultSet rs = st.executeQuery(req); //resultat de la requete
            
            //tant que rs has next get matiere and add it to the list
            while (rs.next()) {
                
                stu.setId(rs.getInt("id")); //set id from req result
                stu.setUsername(rs.getString("username")); 
                stu.setPrenom(rs.getString("prenom")); 
                stu.setEmail(rs.getString("email")); 
                stu.setClasse_id(rs.getInt("classe_id")); 
                
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            
        }
        return stu ;
    }
                
                

        public Classe checkexist(String classe, String niveau){
             Classe c=new Classe();
		 c.setId(-1);
		 
		 try {
			 NiveauService ns=new NiveauService();
			 String req = "select * from classe where classe= ? and niveau_id= ?";
			 PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req);
			 pst.setString(1,classe);
                         pst.setString(2,niveau);
		
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
        
        
            public User emailStudent(String email,int classe){
        User stu = new User();
        stu.setId(-1);
            
            
           
          List<User> l=  listUserNoClasse(classe);
          
          
          
          for (User temp : l) {
                 if(temp.getEmail().equals(email)){
			stu.setId(temp.getId()); //set id from req result
                
                stu.setUsername(temp.getUsername()); 
                stu.setPrenom(temp.getPrenom()); 
                stu.setEmail(temp.getEmail()); 
                stu.setClasse_id(classe);
                 modifstudent(stu);
			}
          }
            


        return stu ;
    }
 
            
            
            public void modifstudent(User u) {
			
			
			 String req = "update user set classe_id=? WHERE id=?";
				try {
					PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req);
					pst.setInt(1, u.getClasse_id());
					pst.setInt(2, u.getId());
                                        System.out.println("etudiant ajouté");
			         pst.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			
		
				
			
			
		}
            
            
            public String[] userToEmail(List<User> l){
              String[] array = new String[l.size()];
              int i =0;
                for (User temp : l) {
                    array[i]=temp.getEmail();
                    i++;
			System.out.println(temp);
			}
                return array;
            }
            
}
