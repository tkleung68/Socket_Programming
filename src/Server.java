import java.io.*;
import java.net.*;
import java.lang.*;
import java.util.*;
public class Server
{
	
    private int count = 0;
    private ArrayList<DataOutputStream> clientOutputStreams = new ArrayList<DataOutputStream>();
    private ArrayList<DataInputStream> serverInputStreams = new ArrayList<DataInputStream>();
    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private String stdin;

  public Server()
  {

    try
    {
      BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
      int port;
      System.out.print("port : ");
      port = Integer.parseInt(in.readLine());
      System.out.println("Waiting for connection");
      ServerSocket serverSocket = new ServerSocket(port);
      Typeto t = new Typeto();
      Thread th = new Thread(t);
      th.start();

      while (t.contin) 
      {
        Socket clientSocket = serverSocket.accept();
        if(clientSocket.isConnected())
        {
          try
          {
            count ++ ;
            System.out.println("Currently " + count + " Clients");
            DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());
            clientOutputStreams.add(dos);
            dos.writeUTF("you are the " + count + " client");

            Thread thread = new Thread(new Clientto(clientSocket , clientOutputStreams));
            thread.start(); 

        
         }
          catch (Exception e) 
          {
            count --;
            System.out.println(e.toString() + "count = " + count);
          }
        }
 
      }
      
      br.close();
      in.close();
      serverSocket.close();

    	System.out.println("Server is Closed");
    }
    catch (Exception e) 
    {
      System.out.println("Someone left");
    }
    
 
  }
  
static class Typeto implements Runnable
{
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static boolean contin = true;


	@Override
	public void run() {
		while (true)
		{
	    try {
	    	
			String stdin = br.readLine();
			if(!stdin.equals("Quit"))
				Clientto.broadCast("Server: " + stdin);
			else
				Clientto.broadCast(stdin);	
			
              }
	    catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	}
	} 
static class Clientto implements Runnable 
{
  DataInputStream dis;
  
  static ArrayList<DataOutputStream> clientOutputStreams = new ArrayList<DataOutputStream>();
  protected int count ;
  protected Socket clientSocket;
  public Clientto(Socket clientSocket , ArrayList<DataOutputStream> clientOutputStreams )
  {
    this.clientOutputStreams = clientOutputStreams;
    this.clientSocket = clientSocket;
    try
    {
      dis = new DataInputStream(clientSocket.getInputStream());
    }
    catch(Exception e)
    {
      System.out.println(e.toString());
    }    
  }
  public void run() 
  {
    try 
    {
      while (true) 
      {        
        String read = dis.readUTF();
        System.out.println(read);
        broadCast(read);
      }
    }
    catch(IOException e) 
    {
      System.out.println(e.toString());
    }
  }
  

  public static void broadCast(String message)
  {
      DataOutputStream writer = null;
      Iterator<DataOutputStream> it = clientOutputStreams.iterator();
      while( it.hasNext() )
      {
          try
          {
              writer = it.next();      
              writer.writeUTF(message);
              writer.flush();
          }
          catch (Exception e)
          {
              System.out.println(e.toString());
              clientOutputStreams.remove(writer);
          }
      }
  }
}
}