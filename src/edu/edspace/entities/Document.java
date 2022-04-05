/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.edspace.entities;

import java.io.File;

public class Document {
    private int id,signalements;
    private String nom,date_insert,prop,url,base64,niveau,matiere,type;
    private File fichier;
    
    public Document() {
    }

    public Document(int signalements, String nom, String date_insert, String prop, String url, String base64, String niveau, String matiere, String type, File fichier) {
        this.signalements = signalements;
        this.nom = nom;
        this.date_insert = date_insert;
        this.prop = prop;
        this.url = url;
        this.niveau = niveau;
        this.matiere = matiere;
        this.type = type;
        this.fichier = fichier;
    }

    public File getFichier() {
        return fichier;
    }

    public void setFichier(File fichier) {
        this.fichier = fichier;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDate_insert() {
        return date_insert;
    }

    public void setDate_insert(String date_insert) {
        this.date_insert = date_insert;
    }

    public String getProp() {
        return prop;
    }

    public void setProp(String prop) {
        this.prop = prop;
    }

    public String getMatiere() {
        return matiere;
    }

    public void setMatiere(String matiere) {
        this.matiere = matiere;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public int getSignalements() {
        return signalements;
    }

    public void setSignalements(int signalements) {
        this.signalements = signalements;
    }

    @Override
    public String toString() {
        return "Document{" + "id=" + id + ", signalements=" + signalements + ", nom=" + nom + ", date_insert=" + date_insert + ", prop=" + prop + ", url=" + url + ", niveau=" + niveau + ", matiere=" + matiere + ", type=" + type + ", fichier=" + fichier + '}';
    }
    
    /* blob to file
    Blob blob = rs.getBlob("BLOB_COLUMN_NAME");        
    InputStream blobStream = blob.getBinaryStream();

    //e.g. save blob to a file
    FileOutputStream out = new FileOutputStream("file.txt");
    byte[] buffer = new byte[1024];
    int n = 0;  
    while( (n = blobStream.read(buffer)) != -1 ) {
        out.write(buffer, 0, n);
    }
    out.flush();
    out.close();
    blobStream.close();
    */
    
    
    /*file to blob
    public static byte[] convertFileContentToBlob(String filePath) throws IOException {
   // create file object
   File file = new File(filePath);
   // initialize a byte array of size of the file
   byte[] fileContent = new byte[(int) file.length()];
   FileInputStream inputStream = null;
   try {
	// create an input stream pointing to the file
	inputStream = new FileInputStream(file);
	// read the contents of file into byte array
	inputStream.read(fileContent);
   } catch (IOException e) {
	throw new IOException("Unable to convert file to byte array. " + 
              e.getMessage());
   } finally {
	// close input stream
	if (inputStream != null) {
           inputStream.close();
	}
   }
   return fileContent;
}
    */
    
    
}
