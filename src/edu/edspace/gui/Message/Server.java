/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.edspace.gui.Message;

/**
 *
 * @author aa
 */
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
public class Server {
    
    //public Thread serverThread;
    

    
		public static void main(String[] args) {
                    Thread serverThread;
	    	 final int port = 2018;
               SocketServer   server = new SocketServer();
               server.start();
               
	    }
}

