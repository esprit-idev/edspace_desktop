/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.edspace.tests;

import edu.edspace.entities.Matiere;
import edu.edspace.entities.Niveau;
import edu.edspace.services.MatiereService;

/**
 *
 * @author MeriamBI
 */
public class MainClass {
    public static void main(String[] args) {
        
        MatiereService ms=new MatiereService(); //instanciation du service MatiereService
        
        /**************TEST AFFICHAGE LISTE DES MATIERES**************/
        System.out.println(ms.listeMatieres());
        /*************************************************************/
        
        
        /**************TEST AJOUT MATIERE**************/
        Niveau n=new Niveau("3A");
        Matiere m=new Matiere("testfx",n.getId());
        ms.ajouterMatiere(m);
        System.out.println(ms.listeMatieres()); //display after add
        /**********************************************/
        
        
        /**************TEST MODIFIER MATIERE**************/
        String oldId=m.getId();
        m.setId("testfxupdate");
        m.setNiveau("3B");
        ms.modifierMatiere(m,oldId);
        System.out.println(ms.listeMatieres()); //display after update
        /*************************************************/
        
        
        /**************TEST SUPPRIMER MATIERE**************/
        ms.supprimerMatiere(m);
        System.out.println(ms.listeMatieres()); //display after delete
        /**************************************************/
        
    }
}
