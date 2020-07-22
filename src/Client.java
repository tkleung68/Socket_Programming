import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.lang.*;
public class Client
{
    private String message, stdin , ip;
    private int port;
    public static boolean ask;
    
    public static void Needask() {
    	ask = true;
    }
  public Client()
  {
    try
    {
      ask = false;
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      System.out.print("Remote IP: ");
      ip = br.readLine();
      System.out.print("And Port:");
      port = Integer.parseInt(br.readLine());
      Socket con = new Socket(ip,port);
      if(con.isConnected())
      {
        System.out.println("Connect Success");
        System.out.println("Input your user name: ");
        String name = br.readLine();
        
        Thread thread = new Thread(new ClientTalk(con));
        
        thread.start();
        DataOutputStream dos = new DataOutputStream(con.getOutputStream());
     
    
        while(true){ 
        stdin = br.readLine();
        
        if(stdin.equals("Quit")) {
        	br.close();
        	con.close();
        	dos.close();
        	System.out.println("You have just left");
        	break;
        }
        	dos.writeUTF(name + " : " + stdin);
        
      }
        
             
      }
      else
        System.out.println("Connect fails");
    }
    catch(Exception e)
    {
      System.out.println(e.toString());
    }
  }
}


class ClientTalk implements Runnable 
{
  DataInputStream dis;
  Socket clientSocket;
  public ClientTalk(Socket clientSocket) 
  {
    this.clientSocket = clientSocket;    
  }
  public void run() 
  {
    try 
    {
      dis = new DataInputStream(clientSocket.getInputStream());
      while (true) 
      {
        String read = dis.readUTF();
        Client.Needask();
        System.out.println(read);
      }
    }
    catch(IOException e) 
    {
      //System.out.println(e.toString());
    }
  }
}

