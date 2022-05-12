/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.edspace.services;

import edu.edspace.entities.User;
import java.io.FileNotFoundException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import edu.edspace.entities.Session;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author YOOSURF
 */
public class PDFService {
    
     public void liste_admins() throws FileNotFoundException, DocumentException, IOException {
         //String sql = "select * from user";
         AdminService sa = new AdminService();
         Session a = new Session();
       // a = sa.updateAdmin(a, id);
        //String message = "M/MME :" + a.getUsername() + " " + a.getPrenom();

        String file_name = "C:\\Users\\21656\\Desktop\\liste_admins.pdf";
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(file_name));
        document.open();
        Paragraph para = new Paragraph("liste des administrateurs");
        document.add(para);
        List<User> liste_admins = sa.listAdmin();
        PdfPTable table = new PdfPTable(3);

        PdfPCell cl = new PdfPCell(new Phrase("Nom"));
        table.addCell(cl);
        PdfPCell cl1 = new PdfPCell(new Phrase("Prenom"));
        table.addCell(cl1);
         PdfPCell cl11 = new PdfPCell(new Phrase("Email"));
        table.addCell(cl11);

        table.setHeaderRows(1);
        document.add(table);

        int i = 0;
        for (i = 0; i < liste_admins.size(); i++) {
            table.addCell(liste_admins.get(i).getUsername());
            table.addCell(liste_admins.get(i).getPrenom());
             table.addCell(liste_admins.get(i).getEmail());

        }
        document.add(table);

        document.close();
         Desktop.getDesktop().open(new File("C:\\Users\\21656\\Desktop\\liste_admins.pdf"));

    }
     
     
      public void liste_Students() throws FileNotFoundException, DocumentException, IOException {
         //String sql = "select * from user";
        // AdminService sa = new AdminService();
        StudentService sa = new StudentService();
         Session a = new Session();
       // a = sa.updateAdmin(a, id);
        //String message = "M/MME :" + a.getUsername() + " " + a.getPrenom();

        String file_name = "C:\\Users\\MeriamBI\\Desktop\\liste_Students.pdf";
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(file_name));
        document.open();
        Paragraph para = new Paragraph("liste des etudiants");
        document.add(para);
        List<User> liste_admins = sa.listStudent();
        PdfPTable table = new PdfPTable(3);

        PdfPCell cl = new PdfPCell(new Phrase("Nom"));
        table.addCell(cl);
        PdfPCell cl1 = new PdfPCell(new Phrase("Prenom"));
        table.addCell(cl1);
         PdfPCell cl11 = new PdfPCell(new Phrase("Email"));
        table.addCell(cl11);

        table.setHeaderRows(1);
        document.add(table);

        int i = 0;
        for (i = 0; i < liste_admins.size(); i++) {
            table.addCell(liste_admins.get(i).getUsername());
            table.addCell(liste_admins.get(i).getPrenom());
             table.addCell(liste_admins.get(i).getEmail());

        }
        document.add(table);

        document.close();
         Desktop.getDesktop().open(new File("C:\\Users\\MeriamBI\\Desktop\\liste_Students.pdf"));

    }

}
