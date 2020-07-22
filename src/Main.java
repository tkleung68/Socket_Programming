import java.util.*;

import java.net.*; 
import java.io.*; 

public class Main {
    public static void main(String args[]) 
    { 
    	System.out.print("*	SUPER TEXT MESSENGER	*\r\n" + 
    			"*	Ver. 2.0	*\r\n" + "(C)onnect peer\n"
    			+ "(W)ait for the other peer connecting\n" +
    			"(Q)uit\n" + "Please choose :");
    	String ip;
    	int port;
    	char operation;
    	
    	
    	Scanner in = new Scanner(System.in);

    	operation = in.next().charAt(0);
    	
    	
    	if(operation == 'c' || operation == 'C') {
 
    		Client client = new Client();
    		while(client.ask) {
    			client = new Client();
    		}

    	}else if(operation =='w' || operation =='W' ) {

        	Server server = new Server();

        	
    	}else if(operation == 'Q' || operation == 'q') {
    		System.out.println("End");
    	}
    	in.close();
    	
    } 

}