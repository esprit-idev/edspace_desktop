package edu.edspace.services;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import edu.edspace.entities.Classe;
import edu.edspace.entities.Message;
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
			pst.setDate(4, (Date) message.getPostDate());
			pst.executeUpdate();
			 System.out.println("Message ajouté");
		}catch(SQLException ex) {
			 System.out.println(ex.getMessage());
		}
		
		
		
	}

}
