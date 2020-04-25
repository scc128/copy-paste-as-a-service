import java.net.*;
import java.io.*;


public class Client{
	public static void main(String [] args) throws Exception {
		if(args.length > 3 || args.length < 1){
			System.exit(1); 
		}
		Socket server = new Socket("35.199.167.255" , 5123);
		BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));
	    PrintWriter out = new PrintWriter(server.getOutputStream(), true);
	    if(args.length == 3){
	    	out.println(args[0] + ":"+ args[1] +":"+ args[2]);
	    }else if(args.length ==2 ){
	    	out.println(args[0] + ":"+ args[1]);
	    }else if(args.length ==1 ){
	    	out.println(args[0]);
	    }else{
	    	System.out.println("will never get here ");
	    }
	    System.out.println(in.readLine());
	    server.close();
	    
	}
}