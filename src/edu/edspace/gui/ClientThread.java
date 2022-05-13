/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.edspace.gui;

/**
 *
 * @author aa
 */
import edu.edspace.gui.Message.*;
import edu.edspace.entities.Message;
import edu.edspace.gui.Classe.AllClassesController;
import java.io.*;
import java.net.*;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javax.swing.table.DefaultTableModel;
public class ClientThread implements Runnable{
    
    public int port;
    public String serverAddr;
    public Socket socket;
    public FrontThreadController ui;
    public ObjectInputStream In;
    public ObjectOutputStream Out;

    public ClientThread(FrontThreadController ui) throws IOException {
        
        this.ui = ui;
        this.serverAddr = ui.serverAddr; this.port = ui.port;

        socket = new Socket(InetAddress.getLocalHost(),2018);

         Out = new ObjectOutputStream(socket.getOutputStream());
        Out.flush();
        In = new ObjectInputStream(socket.getInputStream());
    }

    
    
    
      @Override
    public void run() {
         boolean keepRunning = true;
         while(keepRunning){
             try {
                 Message msg = (Message) In.readObject();

                  
                 
                int test=msg.getUser().getId();
                    if((test== ui.uid)){
                   
                    HBox hBox=new HBox();
              hBox.setAlignment(Pos.CENTER_LEFT);
              hBox.setPadding(new Insets(5,5,5,10));
                        
                           Text text=new Text(ui.ms.switcher(msg).getContent());
                              text.setFill(Color.LIGHTGREEN);
                            text.setFont(Font.font("SANS_SERIF", 13));
                           TextFlow textFlow=new TextFlow(text);
                           textFlow.setStyle("-fx-background-color: WHITE"+ " -fx-background-radius: 5px");
                           textFlow.setPadding(new Insets(5,0,5,100));
                           hBox.getChildren().add(textFlow);
                           Platform.runLater(new Runnable(){
                          

                        @Override
                        public void run() {
 ui.vbox.getChildren().add(hBox);                        
                           }
                           });
                    
                                   }
                    else{
 HBox hBox=new HBox();
              hBox.setAlignment(Pos.CENTER_LEFT);
              hBox.setPadding(new Insets(5,5,5,10));
                         
                           Text text=new Text(ui.ms.switcher(msg).getContent());
                             text.setFont(Font.font("SANS_SERIF", 13));
                           text.setFill(Color.VIOLET);
                           TextFlow textFlow=new TextFlow(text);
                           textFlow.setStyle("-fx-background-color: rgb(233,233,235)"+ " -fx-background-radius: 5px");
                           textFlow.setPadding(new Insets(5,100,5,0));
                           hBox.getChildren().add(textFlow);
                            Platform.runLater(new Runnable(){
                                           

     @Override
     public void run() {
ui.vbox.getChildren().add(hBox);
     }
                            });
                                    }
             }
                    
              catch (IOException ex) {
                 Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
                 System.out.println(ex);
             } catch (ClassNotFoundException ex) {
                 Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
             }
             
         }


    }
    
    
    
    
     public void send(Message msg){
        try {
            Out.writeObject(msg);
            Out.flush();

            
        } 
        catch (IOException ex) {
            System.out.println("Exception SocketClient send()"+ ex.getStackTrace());
        }
    }
    
    public void closeThread(Thread t){
        t = null;
    }
    
   

  
}
