/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.edspace.services;


//import java.util.Properties;
//import javax.mail.Message;
//import javax.mail.MessagingException;
//import javax.mail.PasswordAuthentication;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;


/**
 *
 * @author YOOSURF
 */
public class MailService {
    
    
     public  void send(String Text,String email) {

        final String username = "edspace2.plateforme@gmail.com";
        final String password = "edspace123" ;

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
       
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");  
                
       // props.put("mail.smtp.host", "smtp.gmail.com");
        //props.put("mail.smtp.port", "465");
       // props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.ssl.required", "true");
       // props.put("mail.smtp.ssl.protocols", "TLSv1.2");

       // Session session = Session.
     

        

               Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username,password);
                    }
                });
            try {
                
            Message message = new MimeMessage(session);
           
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email));
            message.setSubject("Rappel");
           
            message.setText(Text );
            message.setContent(Text,"text/html; charset=utf-8");
          
            Transport.send(message);
  
            System.out.println("Mail sent succesfully!");

        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
    }
     
     
    
}
