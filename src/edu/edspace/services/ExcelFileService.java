/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.edspace.services;
//import EasyXLS.*;
//import EasyXLS.Constants.*;

import com.mysql.jdbc.Connection;
//import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import edu.edspace.utils.MyConnection;
import java.sql.SQLException;
import edu.edspace.entities.User;
import edu.edspace.utils.MyConnection;
import edu.edspace.utils.Statics;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import java.sql.PreparedStatement;
import java.util.logging.Logger;
import java.util.logging.Level;
//import EasySls ;

/**
 *
 * @author YOOSURF
 */
public class ExcelFileService {
    
    
    
    public void generateExcelStudent() 
         {  
           String sql = "select * from user where roles='[\"ROLE_STUDENT\"]'";
         //  public static final String myDocs="C:/Users/MeriamBI/Desktop/pidev/EdSpace_Symfony/public/documents/";
       // Statement ste;
        try {
         
      // PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req);
              PreparedStatement ste=MyConnection.getInstance().getCnx().prepareStatement(sql);
               ResultSet rs = ste.executeQuery(sql);
            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet("Liste des etudiants ");
            HSSFRow header = sheet.createRow(0);
            header.createCell(0).setCellValue("ID");
            header.createCell(1).setCellValue("Nom");
            header.createCell(2).setCellValue("Prenom");
            header.createCell(3).setCellValue("Email");
           // header.createCell(4).setCellValue("");
           // header.createCell(5).setCellValue("Prix");
            int index = 1;
            while (rs.next()) {
                HSSFRow row = sheet.createRow(index);
                row.createCell(0).setCellValue(rs.getString("id"));
                row.createCell(1).setCellValue(rs.getString("username"));
                row.createCell(2).setCellValue(rs.getString("prenom"));
                row.createCell(3).setCellValue(rs.getString("email"));
               // row.createCell(4).setCellValue(rs.getString("id_user_id"));
                //row.createCell(5).setCellValue(rs.getString("prix"));
                index++;
            }
            FileOutputStream fileOut = new FileOutputStream("C:\\Users\\21656\\Desktop\\Liste_des_etudiants.Xls");
            wb.write(fileOut);
            fileOut.close();
           ste.close();
           rs.close();
          Desktop.getDesktop().open(new File("C:\\Users\\21656\\Desktop\\Liste_des_etudiants.Xls"));

        } catch (SQLException e) {
        } catch (IOException ex) {
            Logger.getLogger(ExcelFileService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    
    public void generateExcelAdmin() 
         {  
           String sql = "select * from user where roles='[\"ROLE_Admin\"]'";
         //  public static final String myDocs="C:/Users/MeriamBI/Desktop/pidev/EdSpace_Symfony/public/documents/";
       // Statement ste;
        try {
         
      // PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req);
              PreparedStatement ste=MyConnection.getInstance().getCnx().prepareStatement(sql);
               ResultSet rs = ste.executeQuery(sql);
            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet("Liste des administrateurs ");
            HSSFRow header = sheet.createRow(0);
            header.createCell(0).setCellValue("ID");
            header.createCell(1).setCellValue("Nom");
            header.createCell(2).setCellValue("Prenom");
            header.createCell(3).setCellValue("Email");
           // header.createCell(4).setCellValue("");
           // header.createCell(5).setCellValue("Prix");
            int index = 1;
            while (rs.next()) {
                HSSFRow row = sheet.createRow(index);
                row.createCell(0).setCellValue(rs.getString("id"));
                row.createCell(1).setCellValue(rs.getString("username"));
                row.createCell(2).setCellValue(rs.getString("prenom"));
                row.createCell(3).setCellValue(rs.getString("email"));
               // row.createCell(4).setCellValue(rs.getString("id_user_id"));
                //row.createCell(5).setCellValue(rs.getString("prix"));
                index++;
            }
            FileOutputStream fileOut = new FileOutputStream("C:\\Users\\21656\\Desktop\\liste_des_administrateurs.Xls");
            wb.write(fileOut);
            fileOut.close();
           ste.close();
           rs.close();
          Desktop.getDesktop().open(new File("C:\\Users\\21656\\Desktop\\liste_des_administrateurs.Xls"));

        } catch (SQLException e) {
        } catch (IOException ex) {
            Logger.getLogger(ExcelFileService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
  
    
        
    
}
