/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.edspace.services;

import edu.edspace.entities.Club;
import edu.edspace.entities.User;
import edu.edspace.utils.MyConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

/**
 *
 * @author anash
 */
public class ClubService {

    public boolean ajouterClub(Club club) {
        boolean res = false;
        try {
            String req = "insert into club (club_categorie_id,club_responsable_id,club_nom,club_description,club_pic) values"
                    + "(?,?,?,?,?)"; //requete d'inertion avec parametres

            /*PreparedStatement used with dynamic requests+faster and more secure than Statement (used in the method above)
                can't drop or alter with PreparedStatement*/
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req); //instance of myconnection pour etablir la cnx
            pst.setString(1, club.getClubCategorie()); //parameter1=index in request(req) and parameter2=data to pass (nom de la personne)
            pst.setString(2, club.getClubRespo());
            pst.setString(3, club.getClubName());
            pst.setString(4, club.getClubDesc());
            pst.setString(5, "defaultProfilePicture.png");
            pst.executeUpdate(); //pour exécuter la requete
            String req2 = "update user set club_id=?, roles=? WHERE id=" + club.getClubRespo();
            PreparedStatement pst2 = MyConnection.getInstance().getCnx().prepareStatement(req2); //instance of myconnection pour etablir la cnx
            pst2.setInt(1, getClubIdByRespoEmail(club.getClubRespo())); //parameter1=index in request(req) and parameter2=data to pass (nom de la personne)
            pst2.setString(2, "[\"ROLE_STUDENT\",\"ROLE_RESPONSABLEC\"]");
            pst2.executeUpdate(); //pour exécuter la requete

            System.out.println("Club added");
            res = true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return res;
    }

    public List<Club> displayClub() {
        List<Club> clubList = new ArrayList<>();
        try {
            String req = "select * from club"; //requete select from db
            Statement st = MyConnection.getInstance().getCnx().createStatement(); //instance of myConnection pour etablir la cnx
            ResultSet rs = st.executeQuery(req); //resultat de la requete

            //tant que rs has next get matiere and add it to the list
            while (rs.next()) {
                Club club = new Club();
                club.setClubId(rs.getString(1)); //set id from req result
                club.setClubCategorie(getCatName(rs.getInt(2)));
                club.setClubRespo(getRespoName(rs.getInt(3)));
                club.setClubName(rs.getString(4));
                club.setClubDesc(rs.getString(5));
                club.setClubPic(rs.getString(6));
                clubList.add(club); //ajout de la matiere a la liste
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return clubList;
    }

    public String getCatName(int id) {
        String cate = "";
        try {
            String req = "SELECT categorie_nom FROM categorie_club WHERE id=" + id;

            Statement st = MyConnection.getInstance().getCnx().createStatement(); //instance of myConnection pour etablir la cnx
            ResultSet rs = st.executeQuery(req); //resultat de la requete

            //tant que rs has next get matiere and add it to the list
            while (rs.next()) {
                cate = rs.getString(1); //set id from req result
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return cate;
    }

    public String getRespoName(int id) {
        String email = "";
        try {
            String req = "SELECT email FROM user WHERE id=" + id;

            Statement st = MyConnection.getInstance().getCnx().createStatement(); //instance of myConnection pour etablir la cnx
            ResultSet rs = st.executeQuery(req); //resultat de la requete

            //tant que rs has next get matiere and add it to the list
            while (rs.next()) {
                email = rs.getString(1); //set id from req result
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return email;
    }

    public boolean updateClub(Club club, int currentId) {
        boolean res = false;
        String req = "update club set club_categorie_id=?,club_responsable_id=?,club_nom=?,club_description=? WHERE id=?";
        try {
            String req2 = "update user set club_id=?, roles=? WHERE club_id=" + currentId;
            PreparedStatement pst2 = MyConnection.getInstance().getCnx().prepareStatement(req2); //instance of myconnection pour etablir la cnx
            pst2.setString(1, null); //parameter1=index in request(req) and parameter2=data to pass (nom de la personne)
            pst2.setString(2, "[\"ROLE_STUDENT\"]");
            pst2.executeUpdate(); //pour exécuter la requete
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req);
            pst.setString(1, club.getClubCategorie());
            pst.setString(2, club.getClubRespo());
            pst.setString(3, club.getClubName());
            pst.setString(4, club.getClubDesc());
            pst.setInt(5, currentId);
            pst.executeUpdate();
            String req3 = "update user set club_id=?, roles=? WHERE id=" + club.getClubRespo();
            PreparedStatement pst3 = MyConnection.getInstance().getCnx().prepareStatement(req3); //instance of myconnection pour etablir la cnx
            pst3.setInt(1, currentId); //parameter1=index in request(req) and parameter2=data to pass (nom de la personne)
            pst3.setString(2, "[\"ROLE_STUDENT\",\"ROLE_RESPONSABLEC\"]");
            pst3.executeUpdate(); //pour exécuter la requete

            System.out.println("Club updated");
            res = true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return res;
    }

    public boolean deleteClub(int clubId) {
        boolean res = false;
        String req = "delete from club where id = ?";
        try {

            String req3 = "delete from club_pub WHERE club_id=" + clubId;
            PreparedStatement pst3 = MyConnection.getInstance().getCnx().prepareStatement(req3); //instance of myconnection pour etablir la cnx
            pst3.executeUpdate(); //pour exécuter la requete

            String req2 = "update user set club_id=?, roles=? WHERE club_id=" + clubId;
            PreparedStatement pst2 = MyConnection.getInstance().getCnx().prepareStatement(req2); //instance of myconnection pour etablir la cnx
            pst2.setString(1, null); //parameter1=index in request(req) and parameter2=data to pass (nom de la personne)
            pst2.setString(2, "[\"ROLE_STUDENT\"]");
            pst2.executeUpdate(); //pour exécuter la requete
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req);
            pst.setInt(1, clubId);
            pst.executeUpdate();
            System.out.println("Club deleted");
            res = true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return res;
    }

    public void getClubs(ObservableList<Club> clubList, TableView<Club> tab) {
        clubList.clear();

        try {
            String req = "select * from club"; //requete select from db
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req); //instance of myConnection pour etablir la cnx
            ResultSet rs = pst.executeQuery(req); //resultat de la requete

            //tant que rs has next get matiere and add it to the list
            while (rs.next()) {
                Club club = new Club();
                club.setClubId(rs.getString("id")); //set id from req result
                club.setClubCategorie(getCatName(Integer.parseInt(rs.getString("club_categorie_id"))));
                club.setClubRespo(getRespoName(Integer.parseInt(rs.getString("club_responsable_id"))));
                club.setClubName(rs.getString("club_nom"));
                club.setClubDesc(rs.getString("club_description"));
                club.setClubPic(rs.getString("club_pic"));
                clubList.add(club); //ajout de la matiere a la liste
                tab.setItems(clubList);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public void getClubsByName(ObservableList<Club> clubList, TableView<Club> tab, String clubName) {
        clubList.clear();

        try {
            String req = "select * from club WHERE club_nom like'%" + clubName + "%';";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req); //instance of myConnection pour etablir la cnx
            ResultSet rs = pst.executeQuery(req); //resultat de la requete

            //tant que rs has next get matiere and add it to the list
            while (rs.next()) {
                Club club = new Club();
                club.setClubId(rs.getString("id")); //set id from req result
                club.setClubCategorie(getCatName(Integer.parseInt(rs.getString("club_categorie_id"))));
                club.setClubRespo(getRespoName(Integer.parseInt(rs.getString("club_responsable_id"))));
                club.setClubName(rs.getString("club_nom"));
                club.setClubDesc(rs.getString("club_description"));
                club.setClubPic(rs.getString("club_pic"));
                clubList.add(club); //ajout de la matiere a la liste
                tab.setItems(clubList);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public void getClubsByCatName(ObservableList<Club> clubList, TableView<Club> tab, String catName) {
        clubList.clear();
        int catId = getCatId(catName);
        try {
            String req = "select * from club WHERE club_categorie_id=" + catId + ";";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req); //instance of myConnection pour etablir la cnx
            ResultSet rs = pst.executeQuery(req); //resultat de la requete

            //tant que rs has next get matiere and add it to the list
            while (rs.next()) {
                Club club = new Club();
                club.setClubId(rs.getString("id")); //set id from req result
                club.setClubCategorie(getCatName(Integer.parseInt(rs.getString("club_categorie_id"))));
                club.setClubRespo(getRespoName(Integer.parseInt(rs.getString("club_responsable_id"))));
                club.setClubName(rs.getString("club_nom"));
                club.setClubDesc(rs.getString("club_description"));
                club.setClubPic(rs.getString("club_pic"));
                clubList.add(club); //ajout de la matiere a la liste
                tab.setItems(clubList);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public int getCatId(String categorie) {
        int cate = 0;
        try {
            String req = "SELECT id FROM categorie_club WHERE categorie_nom='" + categorie + "'";

            Statement st = MyConnection.getInstance().getCnx().createStatement(); //instance of myConnection pour etablir la cnx
            ResultSet rs = st.executeQuery(req); //resultat de la requete

            //tant que rs has next get matiere and add it to the list
            while (rs.next()) {
                cate = rs.getInt(1); //set id from req result
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return cate;
    }

    public int getRespoid(String respo) {
        int email = 0;
        try {
            String req = "SELECT id FROM user WHERE email='" + respo + "'";

            Statement st = MyConnection.getInstance().getCnx().createStatement(); //instance of myConnection pour etablir la cnx
            ResultSet rs = st.executeQuery(req); //resultat de la requete

            //tant que rs has next get matiere and add it to the list
            while (rs.next()) {
                email = rs.getInt(1); //set id from req result
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return email;
    }

    public List<User> listStudentNotResponsable() {
        List<User> listStudent = new ArrayList<>();
        try {
            String req = "select * from user where roles='[\"ROLE_STUDENT\"]'"; //requete select from db
            Statement st = MyConnection.getInstance().getCnx().createStatement(); //instance of myConnection pour etablir la cnx
            ResultSet rs = st.executeQuery(req); //resultat de la requete

            //tant que rs has next get personne and add it to the list
            while (rs.next()) {
                User stu = new User();
                stu.setId(rs.getInt("id")); //set id from req result
                stu.setUsername(rs.getString("username"));
                stu.setPrenom(rs.getString("prenom"));
                stu.setEmail(rs.getString("email"));
                // stu.setIsBanned(rs.getBoolean("IsBanned"));
                listStudent.add(stu); //ajout de la personne a la liste
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return listStudent;
    }

    public List<User> listStudentNotResponsableAndCurrent(int clubid) {
        List<User> listStudent = new ArrayList<>();
        try {
            String req = "select * from user where roles='[\"ROLE_STUDENT\"]' or club_id=" + clubid; //requete select from db
            Statement st = MyConnection.getInstance().getCnx().createStatement(); //instance of myConnection pour etablir la cnx
            ResultSet rs = st.executeQuery(req); //resultat de la requete

            //tant que rs has next get personne and add it to the list
            while (rs.next()) {
                User stu = new User();
                stu.setId(rs.getInt("id")); //set id from req result
                stu.setUsername(rs.getString("username"));
                stu.setPrenom(rs.getString("prenom"));
                stu.setEmail(rs.getString("email"));
                // stu.setIsBanned(rs.getBoolean("IsBanned"));
                listStudent.add(stu); //ajout de la personne a la liste
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return listStudent;
    }

    public int getClubIdByRespoEmail(String respo) {
        int cate = 0;
        try {
            String req = "SELECT id FROM club WHERE club_responsable_id=" + respo;

            Statement st = MyConnection.getInstance().getCnx().createStatement(); //instance of myConnection pour etablir la cnx
            ResultSet rs = st.executeQuery(req); //resultat de la requete

            //tant que rs has next get matiere and add it to the list
            while (rs.next()) {
                cate = rs.getInt(1); //set id from req result
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return cate;
    }

    public String getClubImage(int clubid) {
        String img = "";
        try {
            String req = "SELECT club_pic FROM club WHERE id=" + clubid;

            Statement st = MyConnection.getInstance().getCnx().createStatement(); //instance of myConnection pour etablir la cnx
            ResultSet rs = st.executeQuery(req); //resultat de la requete

            //tant que rs has next get matiere and add it to the list
            while (rs.next()) {
                img = rs.getString(1); //set id from req result
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return img;
    }

    public String getClubName(int clubid) {
        String name = "";
        try {
            String req = "SELECT club_nom FROM club WHERE id=" + clubid;

            Statement st = MyConnection.getInstance().getCnx().createStatement(); //instance of myConnection pour etablir la cnx
            ResultSet rs = st.executeQuery(req); //resultat de la requete

            //tant que rs has next get matiere and add it to the list
            while (rs.next()) {
                name = rs.getString(1); //set id from req result
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return name;
    }

    public boolean updateClubPic(String clubImg, int currentId) {
        boolean res = false;
        String req = "update club set club_pic=? WHERE id=?";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req);
            pst.setString(1, clubImg);
            pst.setString(2, String.valueOf(currentId));
            pst.executeUpdate();
            System.out.println("Club updated");
            res = true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return res;
    }

    public boolean updateClubDesc(String desc, int currentId) {
        boolean res = false;
        String req = "update club set club_description=? WHERE id=?";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req);
            pst.setString(1, desc);
            pst.setString(2, String.valueOf(currentId));
            pst.executeUpdate();
            System.out.println("Club updated");
            res = true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return res;
    }

    public int getUserClubID(int userId) {
        int res = 0;
        String req = "select club_id from user WHERE id=" + userId;
        try {
            Statement st = MyConnection.getInstance().getCnx().createStatement(); //instance of myConnection pour etablir la cnx
            ResultSet rs = st.executeQuery(req); //resultat de la requete

            //tant que rs has next get matiere and add it to the list
            while (rs.next()) {
                res = rs.getInt(1); //set id from req result
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return res;
    }

    public int getClubIdByClubName(String clubName) {
        int clubid = 0;
        try {
            String req = "SELECT id FROM club WHERE club_nom='" + clubName + "';";

            Statement st = MyConnection.getInstance().getCnx().createStatement(); //instance of myConnection pour etablir la cnx
            ResultSet rs = st.executeQuery(req); //resultat de la requete

            //tant que rs has next get matiere and add it to the list
            while (rs.next()) {
                clubid = rs.getInt(1); //set id from req result
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return clubid;
    }

    public boolean clubExists(String clubName) {
        boolean exist = false;
        try {
            String req = "SELECT count(id) FROM club WHERE club_nom='" + clubName + "';";

            Statement st = MyConnection.getInstance().getCnx().createStatement(); //instance of myConnection pour etablir la cnx
            ResultSet rs = st.executeQuery(req); //resultat de la requete

            //tant que rs has next get matiere and add it to the list
            while (rs.next()) {
                if (rs.getInt(1) > 0) {
                    exist = true;

                } else {
                    exist = false;
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return exist;
    }
     public boolean clubExistsEdit(String clubName,String clubID) {
        boolean exist = false;
        try {
            String req = "SELECT count(id) FROM club WHERE club_nom='" + clubName + "' and id!=+"+clubID+";";

            Statement st = MyConnection.getInstance().getCnx().createStatement(); //instance of myConnection pour etablir la cnx
            ResultSet rs = st.executeQuery(req); //resultat de la requete

            //tant que rs has next get matiere and add it to the list
            while (rs.next()) {
                if (rs.getInt(1) > 0) {
                    exist = true;

                } else {
                    exist = false;
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return exist;
    }

    public List<Club> displayClubFiltredByName(String clubName) {
        List<Club> clubList = new ArrayList<>();
        int cat = getCatId(clubName);
        try {
            String req = "select * from club where club_categorie_id=" + cat + ";"; //requete select from db
            Statement st = MyConnection.getInstance().getCnx().createStatement(); //instance of myConnection pour etablir la cnx
            ResultSet rs = st.executeQuery(req); //resultat de la requete

            //tant que rs has next get matiere and add it to the list
            while (rs.next()) {
                Club club = new Club();
                club.setClubId(rs.getString(1)); //set id from req result
                club.setClubCategorie(getCatName(rs.getInt(2)));
                club.setClubRespo(getRespoName(rs.getInt(3)));
                club.setClubName(rs.getString(4));
                club.setClubDesc(rs.getString(5));
                club.setClubPic(rs.getString(6));
                clubList.add(club); //ajout de la matiere a la liste
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return clubList;
    }
}
