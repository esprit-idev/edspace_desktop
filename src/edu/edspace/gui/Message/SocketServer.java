package edu.edspace.gui.Message;

import edu.edspace.entities.Message;
import java.io.*;
import java.net.*;

class ServerThread extends Thread { 
	
    public SocketServer server = null;
    public Socket socket = null;
    public int ID = -1;
    public ObjectInputStream streamIn  =  null;
    public ObjectOutputStream streamOut = null;

    
    public ServerThread(SocketServer _server, Socket _socket){  
   	super();
        server = _server;
        socket = _socket;
        ID     = socket.getPort();
        Server ui;
     }
    
    public void send(Message msg){
        try {
            streamOut.writeObject(msg);
            streamOut.flush();
            System.out.println("send done");
        } 
        catch (IOException ex) {
            System.out.println("Exception [SocketClient : send(...)]");
        }
    }
    
    public int getID(){  
	    return ID;
    }
   
    @SuppressWarnings("deprecation")
	public void run(){  
        while (true){  
    	    try{  
                Message msg = (Message) streamIn.readObject();
                
                System.out.println("message1");
                System.out.println(msg.getContent());
                System.out.println(msg.getContent());
                System.out.println(msg.getContent());
                System.out.println(msg.getContent());
    	    	server.handle(ID, msg);
            }
            catch(Exception ioe){  
                System.out.println(ioe);
            	System.out.println(ID + " ERROR reading: " + ioe.getMessage());
                server.remove(ID);
                stop();
            }
        }
    }
    
    public void open() throws IOException {  
        streamOut = new ObjectOutputStream(socket.getOutputStream());
        streamOut.flush();
        streamIn = new ObjectInputStream(socket.getInputStream());
        System.out.println("open done");
        
    }
    
    public void close() throws IOException {  
    	if (socket != null)    socket.close();
        if (streamIn != null)  streamIn.close();
        if (streamOut != null) streamOut.close();
    }
}





public class SocketServer implements Runnable {
    
    public ServerThread clients[];
    public ServerSocket server = null;
    public Thread       thread = null;
    public int clientCount = 0, port = 2018;

    public SocketServer(){
       
        clients = new ServerThread[50];
     
        
	try{  
	    server = new ServerSocket(port);
            port = server.getLocalPort();
            System.out.println("Server startet. IP : " + InetAddress.getLocalHost() + ", Port : " + server.getLocalPort());
	    start(); 
        }
	catch(IOException ioe){  
         System.out.println(ioe);
	}
    }
    
    public SocketServer( int Port){
       
        clients = new ServerThread[50];
        port = Port;
     
	try{  
	    server = new ServerSocket(port);
            port = server.getLocalPort();
	    start(); 
        }
	catch(IOException ioe){  
            System.out.println(ioe);
	}
    }
	
    public void run(){  
	while (thread != null){  
            try{  
		
	        addThread(server.accept());
                System.out.println("accepted ");
                System.out.println("test test");
        System.out.println("test test");
        System.out.println("test test");
        System.out.println("test test");
        System.out.println("test test");
	    }
	    catch(Exception ioe){ 
               System.out.println(ioe);
	    }
        }
    }
	
    public void start(){  
    	if (thread == null){  
            thread = new Thread(this); 
	    thread.start();
	}
    }
    
    @SuppressWarnings("deprecation")
    public void stop(){  
        if (thread != null){  
            thread.stop(); 
	    thread = null;
	}
    }
    
    private int findClient(int ID){  
    	for (int i = 0; i < clientCount; i++){
        	if (clients[i].getID() == ID){
                    return i;
                }
	}
	return -1;
    }
	
    public synchronized void handle(int ID, Message msg){  
            
              
                    Announce(msg);
              
    }
    
    public void Announce(Message msg){
        //Message msg = new Message(type, sender, content, "All");
        for(int i = 0; i < clientCount; i++){
            clients[i].send(msg);
        }
    }

	
    @SuppressWarnings("deprecation")
    public synchronized void remove(int ID){  
    int pos = findClient(ID);
        if (pos >= 0){  
            ServerThread toTerminate = clients[pos];
	    if (pos < clientCount-1){
                for (int i = pos+1; i < clientCount; i++){
                    clients[i-1] = clients[i];
	        }
	    }
	    clientCount--;
	    try{  
	      	toTerminate.close(); 
	    }
	    catch(IOException ioe){  
                System.out.println(ioe);
	    }
	    toTerminate.stop(); 
	}
    }
    
    private void addThread(Socket socket){  
	if (clientCount < clients.length){  
           
	    clients[clientCount] = new ServerThread(this, socket);
	    try{  
	      	clients[clientCount].open(); 
	        clients[clientCount].start();  
	        clientCount++; 
	    }
	    catch(IOException ioe){  
	      System.out.println(ioe);
	    } 
	}
	else{
           System.out.println("erreurrr");
	}
    }
}
