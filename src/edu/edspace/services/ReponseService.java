/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.edspace.services;

import edu.edspace.entities.Reponse;
import edu.edspace.entities.User;
import edu.edspace.utils.MyConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 21656
 */
public class ReponseService {
     public boolean addReponse(Reponse r){
         DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  
        LocalDateTime now = LocalDateTime.now(); 
        try {
            MailService ms =new MailService();
            UserService u = new UserService();
            User us = u.find(r.getUser());
            ms.send("<!doctype html>\n" +
"<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">\n" +
"  <head>\n" +
"    <title>\n" +
"    </title>\n" +
"    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
"    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
"    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
"    <style type=\"text/css\">\n" +
"      #outlook a{padding: 0;}\n" +
"      			.ReadMsgBody{width: 100%;}\n" +
"      			.ExternalClass{width: 100%;}\n" +
"      			.ExternalClass *{line-height: 100%;}\n" +
"      			body{margin: 0; padding: 0; -webkit-text-size-adjust: 100%; -ms-text-size-adjust: 100%;}\n" +
"      			table, td{border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt;}\n" +
"      			img{border: 0; height: auto; line-height: 100%; outline: none; text-decoration: none; -ms-interpolation-mode: bicubic;}\n" +
"      			p{display: block; margin: 13px 0;}\n" +
"    </style>\n" +
"    <!--[if !mso]><!-->\n" +
"    <style type=\"text/css\">\n" +
"      @media only screen and (max-width:480px) {\n" +
"      			  		@-ms-viewport {width: 320px;}\n" +
"      			  		@viewport {	width: 320px; }\n" +
"      				}\n" +
"    </style>\n" +
"    <!--<![endif]-->\n" +
"    <!--[if mso]> \n" +
"		<xml> \n" +
"			<o:OfficeDocumentSettings> \n" +
"				<o:AllowPNG/> \n" +
"				<o:PixelsPerInch>96</o:PixelsPerInch> \n" +
"			</o:OfficeDocumentSettings> \n" +
"		</xml>\n" +
"		<![endif]-->\n" +
"    <!--[if lte mso 11]> \n" +
"		<style type=\"text/css\"> \n" +
"			.outlook-group-fix{width:100% !important;}\n" +
"		</style>\n" +
"		<![endif]-->\n" +
"    <style type=\"text/css\">\n" +
"      @media only screen and (min-width:480px) {\n" +
"      .dys-column-per-100 {\n" +
"      	width: 100.000000% !important;\n" +
"      	max-width: 100.000000%;\n" +
"      }\n" +
"      }\n" +
"      @media only screen and (max-width:480px) {\n" +
"      \n" +
"      			  table.full-width-mobile { width: 100% !important; }\n" +
"      				td.full-width-mobile { width: auto !important; }\n" +
"      \n" +
"      }\n" +
"      @media only screen and (min-width:480px) {\n" +
"      .dys-column-per-90 {\n" +
"      	width: 90% !important;\n" +
"      	max-width: 90%;\n" +
"      }\n" +
"      }\n" +
"      @media only screen and (min-width:480px) {\n" +
"      .dys-column-per-100 {\n" +
"      	width: 100.000000% !important;\n" +
"      	max-width: 100.000000%;\n" +
"      }\n" +
"      }\n" +
"    </style>\n" +
"  </head>\n" +
"  <body>\n" +
"    <div>\n" +
"      <table align='center' border='0' cellpadding='0' cellspacing='0' role='presentation' style='background:#f7f7f7;background-color:#f7f7f7;width:100%;'>\n" +
"        <tbody>\n" +
"          <tr>\n" +
"            <td>\n" +
"              <div style='margin:0px auto;max-width:600px;'>\n" +
"                <table align='center' border='0' cellpadding='0' cellspacing='0' role='presentation' style='width:100%;'>\n" +
"                  <tbody>\n" +
"                    <tr>\n" +
"                      <td style='direction:ltr;font-size:0px;padding:20px 0;text-align:center;vertical-align:top;'>\n" +
"                        <!--[if mso | IE]>\n" +
"<table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td style=\"vertical-align:top;width:600px;\">\n" +
"<![endif]-->\n" +
"                        <div class='dys-column-per-100 outlook-group-fix' style='direction:ltr;display:inline-block;font-size:13px;text-align:left;vertical-align:top;width:100%;'>\n" +
"                          <table border='0' cellpadding='0' cellspacing='0' role='presentation' style='vertical-align:top;' width='100%'>\n" +
"                            <tr>\n" +
"                              <td align='center' style='font-size:0px;padding:10px 25px;word-break:break-word;'>\n" +
"                                <div style='color:#4d4d4d;font-family:Oxygen, Helvetica neue, sans-serif;font-size:32px;font-weight:700;line-height:37px;text-align:center;'>\n" +
"                                  You've recieve a notification \n" +
"                                </div>\n" +
"                              </td>\n" +
"                            </tr>\n" +
"                            <tr>\n" +
"                              <td align='center' style='font-size:0px;padding:10px 25px;word-break:break-word;'>\n" +
"                                <div style='color:#777777;font-family:Oxygen, Helvetica neue, sans-serif;font-size:14px;line-height:21px;text-align:center;'>\n" +
"                                  After posting on our plateforme, we wanted to notify you that you've recieved an answer for your thread, please feel free to visit your Thread you may find something that will help you, Thanks for using edSpace.\n" +
"                                </div>\n" +
"                              </td>\n" +
"                            </tr>\n" +
"                          </table>\n" +
"                        </div>\n" +
"                        <!--[if mso | IE]>\n" +
"</td></tr></table>\n" +
"<![endif]-->\n" +
"                      </td>\n" +
"                    </tr>\n" +
"                  </tbody>\n" +
"                </table>\n" +
"              </div>\n" +
"            </td>\n" +
"          </tr>\n" +
"        </tbody>\n" +
"      </table>\n" +
"      <table align='center' border='0' cellpadding='0' cellspacing='0' role='presentation' style='background:#f7f7f7;background-color:#f7f7f7;width:100%;'>\n" +
"        <tbody>\n" +
"          <tr>\n" +
"            <td>\n" +
"              <div style='margin:0px auto;max-width:600px;'>\n" +
"                <table align='center' border='0' cellpadding='0' cellspacing='0' role='presentation' style='width:100%;'>\n" +
"                  <tbody>\n" +
"                    <tr>\n" +
"                      <td style='direction:ltr;font-size:0px;padding:20px 0;text-align:center;vertical-align:top;'>\n" +
"                        <!--[if mso | IE]>\n" +
"<table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td style=\"vertical-align:top;width:540px;\">\n" +
"<![endif]-->\n" +
"                        <div class='dys-column-per-90 outlook-group-fix' style='direction:ltr;display:inline-block;font-size:13px;text-align:left;vertical-align:top;width:100%;'>\n" +
"                          <table border='0' cellpadding='0' cellspacing='0' role='presentation' width='100%'>\n" +
"                            <tbody>\n" +
"                              <tr>\n" +
"                                <td style='background-color:#ffffff;border:1px solid #ccc;padding:45px 75px;vertical-align:top;'>\n" +
"                                  <table border='0' cellpadding='0' cellspacing='0' role='presentation' style='' width='100%'>\n" +
"                                    <tr>\n" +
"                                      <td align='center' style='font-size:0px;padding:10px 25px;word-break:break-word;'>\n" +
"                                        <table border='0' cellpadding='0' cellspacing='0' role='presentation' style='border-collapse:collapse;border-spacing:0px;'>\n" +
"                                          <tbody>\n" +
"                                            <tr>\n" +
"                                              <td style='width:130px;'>\n" +
"                                                <img alt='Profile Picture' height='auto' src='https://www.prodigemobile.com/wp-content/uploads/2019/11/pastille-notification-android.jpg' style='border:1px solid #ccc;border-radius:5px;display:block;font-size:13px;height:auto;outline:none;text-decoration:none;width:450px;' width='1000' />\n" +
"                                              </td>\n" +
"                                            </tr>\n" +
"                                          </tbody>\n" +
"                                        </table>\n" +
"                                      </td>\n" +
"                                    </tr>\n" +
"                                    <tr>\n" +
"                                      <td align='center' style='font-size:0px;padding:10px 25px;word-break:break-word;'>\n" +
"                                        <div style='color:#777777;font-family:Oxygen, Helvetica neue, sans-serif;font-size:14px;line-height:21px;text-align:center;'>\n" +
"                                          <a href='#' style='display:block; color: #ff6f6f; font-weight: bold; text-decoration: none;'>\n" +
"                                            "+us.getPrenom()+" "+us.getUsername()+
"                                          </a>\n" +
"                                          <span>\n" + r.getReply()+
"                                          </span>\n" +
"                                        </div>\n" +
"                                      </td>\n" +
"                                    </tr>\n" +
"                                    <tr>\n" +
"                                      <td align='center' style='font-size:0px;padding:10px 25px;word-break:break-word;' vertical-align='middle'>\n" +
"                                      </td>\n" +
"                                    </tr>\n" +
"                                  </table>\n" +
"                                </td>\n" +
"                              </tr>\n" +
"                            </tbody>\n" +
"                          </table>\n" +
"                        </div>\n" +
"                        <!--[if mso | IE]>\n" +
"</td></tr></table>\n" +
"<![endif]-->\n" +
"                      </td>\n" +
"                    </tr>\n" +
"                  </tbody>\n" +
"                </table>\n" +
"              </div>\n" +
"            </td>\n" +
"          </tr>\n" +
"        </tbody>\n" +
"      </table>\n" +
"      <table align='center' background='https://s3.amazonaws.com/swu-filepicker/4E687TRe69Ld95IDWyEg_bg_top_02.jpg' border='0' cellpadding='0' cellspacing='0' role='presentation' style='background:url(https://s3.amazonaws.com/swu-filepicker/4E687TRe69Ld95IDWyEg_bg_top_02.jpg) top center / auto repeat;width:100%;'>\n" +
"        <tbody>\n" +
"          <tr>\n" +
"            <td>\n" +
"              <!--[if mso | IE]>\n" +
"<v:rect style=\"mso-width-percent:1000;\" xmlns:v=\"urn:schemas-microsoft-com:vml\" fill=\"true\" stroke=\"false\"><v:fill src=\"https://s3.amazonaws.com/swu-filepicker/4E687TRe69Ld95IDWyEg_bg_top_02.jpg\" origin=\"0.5, 0\" position=\"0.5, 0\" type=\"tile\" /><v:textbox style=\"mso-fit-shape-to-text:true\" inset=\"0,0,0,0\">\n" +
"<![endif]-->\n" +
"              <div style='margin:0px auto;max-width:600px;'>\n" +
"                <div style='font-size:0;line-height:0;'>\n" +
"                  <table align='center' border='0' cellpadding='0' cellspacing='0' role='presentation' style='width:100%;'>\n" +
"                    <tbody>\n" +
"                      <tr>\n" +
"                        <td style='direction:ltr;font-size:0px;padding:20px 0px 30px 0px;text-align:center;vertical-align:top;'>\n" +
"                          <!--[if mso | IE]>\n" +
"<table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td style=\"vertical-align:top;width:600px;\">\n" +
"<![endif]-->\n" +
"                          <div class='dys-column-per-100 outlook-group-fix' style='direction:ltr;display:inline-block;font-size:13px;text-align:left;vertical-align:top;width:100%;'>\n" +
"                            <table border='0' cellpadding='0' cellspacing='0' role='presentation' width='100%'>\n" +
"                              <tbody>\n" +
"                                <tr>\n" +
"                                  <td style='padding:0px 20px;vertical-align:top;'>\n" +
"                                    <table border='0' cellpadding='0' cellspacing='0' role='presentation' style='' width='100%'>\n" +
"                                      <tr>\n" +
"                                        <td align='left' style='font-size:0px;padding:0px;word-break:break-word;'>\n" +
"                                          <table border='0' cellpadding='0' cellspacing='0' style='cellpadding:0;cellspacing:0;color:#000000;font-family:Helvetica, Arial, sans-serif;font-size:13px;line-height:22px;table-layout:auto;width:100%;' width='100%'>\n" +
"                                            <tr>\n" +
"                                              <td align='left'>\n" +
"                                                <a href='#'>\n" +
"                                                  <img align='left' alt='Logo' height='33' padding='5px' src='https://swu-cs-assets.s3.amazonaws.com/OSET/oxy-logo.png' width='120' />\n" +
"                                                </a>\n" +
"                                              </td>\n" +
"                                              <td align='right' style='vertical-align:bottom;' width='34px'>\n" +
"                                                <a href='#'>\n" +
"                                                  <img alt='Twitter' height='22' src='https://s3.amazonaws.com/swu-cs-assets/OSET/social/Twitter_grey.png' width='22' />\n" +
"                                                </a>\n" +
"                                              </td>\n" +
"                                              <td align='right' style='vertical-align:bottom;' width='34px'>\n" +
"                                                <a href='#'>\n" +
"                                                  <img alt='Facebook' height='22' src='https://swu-cs-assets.s3.amazonaws.com/OSET/social/f_grey.png' width='22' />\n" +
"                                                </a>\n" +
"                                              </td>\n" +
"                                              <td align='right' style='vertical-align:bottom;' width='34px'>\n" +
"                                                <a href='#'>\n" +
"                                                  <img alt='Instagram' height='22' src='https://swu-cs-assets.s3.amazonaws.com/OSET/social/instagrey.png' width='22' />\n" +
"                                                </a>\n" +
"                                              </td>\n" +
"                                            </tr>\n" +
"                                          </table>\n" +
"                                        </td>\n" +
"                                      </tr>\n" +
"                                    </table>\n" +
"                                  </td>\n" +
"                                </tr>\n" +
"                              </tbody>\n" +
"                            </table>\n" +
"                          </div>\n" +
"                          <!--[if mso | IE]>\n" +
"</td></tr></table>\n" +
"<![endif]-->\n" +
"                        </td>\n" +
"                      </tr>\n" +
"                    </tbody>\n" +
"                  </table>\n" +
"                </div>\n" +
"              </div>\n" +
"              <!--[if mso | IE]>\n" +
"</v:textbox></v:rect>\n" +
"<![endif]-->\n" +
"            </td>\n" +
"          </tr>\n" +
"        </tbody>\n" +
"      </table>\n" +
"    </div>\n" +
"  </body>\n" +
"</html>", us.getEmail());
            String query = "Insert into reponse values"+"(?,?,?,?,?,?)";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query);
            pst.setString(1, null);
            pst.setInt(2, r.getThread());
            pst.setInt(3, r.getUser());
            pst.setString(4,r.getReply());
            pst.setString(5, dtf.format(now));
            pst.setBoolean(6, false);
            pst.executeUpdate();
            System.out.println("reponse ajoutée à thread "+r.getThread());
            
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    return true;
    }
    public List<Reponse> listReponses() {
        List<Reponse> reps = new ArrayList<>();
        try {
            String query = "select * from reponse where display = 0"; //requete select from db
            Statement st = MyConnection.getInstance().getCnx().createStatement(); //instance of myConnection pour etablir la cnx
            ResultSet rs = st.executeQuery(query); //resultat de la requete

            //tant que rs has next get matiere and add it to the list
            while (rs.next()) {
                Reponse r = new Reponse();
                r.setId(rs.getString("id"));
                r.setReply(rs.getString("reply"));
                r.setUser(rs.getInt("user_id"));
                
                r.setReplyDate(rs.getString("reply_date"));
                r.setDisplay(rs.getBoolean("display"));
               
                
                reps.add(r); //ajout de la matiere a la liste
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return reps;
    }
    
    
    public void deleteReponse(Reponse r) {
        String req = "update reponse set display=1 WHERE id=?";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req);
            pst.setString(1,r.getId());
            pst.executeUpdate();
            System.out.println("Reponse deleted");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public List<Reponse> getReponsesByThread(int id){
        List<Reponse> reps = new ArrayList<>();
        try {
            String query = "select * from reponse where display = 0 and thread_id = "+id; //requete select from db
            Statement st = MyConnection.getInstance().getCnx().createStatement(); //instance of myConnection pour etablir la cnx
            ResultSet rs = st.executeQuery(query); //resultat de la requete

            //tant que rs has next get matiere and add it to the list
            while (rs.next()) {
                Reponse r = new Reponse();
                r.setId(rs.getString("id"));
                r.setReply(rs.getString("reply"));
                r.setUser(rs.getInt("user_id"));
                
                r.setReplyDate(rs.getString("reply_date"));
                r.setDisplay(rs.getBoolean("display"));
               
                
                reps.add(r); //ajout de la matiere a la liste
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return reps;
    }
    public Reponse getReponse(int id){
        Reponse r = new Reponse();
        try {
            String req = "select * from reponse where id ="+id;
            Statement st = MyConnection.getInstance().getCnx().createStatement(); //instance of myConnection pour etablir la cnx
            ResultSet rs = st.executeQuery(req); //resultat de la requete
            
            //tant que rs has next get matiere and add it to the list
            while (rs.next()) {
                
                r.setId(rs.getString("id"));
                r.setReply(rs.getString("reply"));
                r.setUser(rs.getInt("user_id"));
                
                r.setReplyDate(rs.getString("reply_date"));
                r.setDisplay(rs.getBoolean("display"));
                
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            
        }
        return r;
    }
}
