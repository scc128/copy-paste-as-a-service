import java.net.*;
import java.io.*;
import java.util.*;
public class Server{
	private static String fileName = "clipboard";
	public static void main(String [] args) throws Exception{
		try{
			ServerSocket listener = new ServerSocket(5123);
			while(true){
				Socket newClient = listener.accept();
				PrintWriter out = new PrintWriter(newClient.getOutputStream() , true );
				BufferedReader in = new BufferedReader(new InputStreamReader(newClient.getInputStream()));
				processCommand(out , in);
			}
		}catch(Throwable e){
			
		}
	}
	private static boolean processCommand(PrintWriter out , BufferedReader in) throws Exception{
		String command = in.readLine();
		String [] partsOfCommand = command.split(":");
		if(partsOfCommand[0].equals("get")){
			try{
				if(partsOfCommand.length == 1 ){
					return processGetCommand(out , in , "");
				}
				return processGetCommand(out , in , partsOfCommand[1]);
			}catch(ArrayIndexOutOfBoundsException e ){
				return false; 
			}
		}else if(partsOfCommand[0].equals("put")){
			try{
				return processPutCommand(out , in , partsOfCommand[1] , partsOfCommand[2]);
			}catch(ArrayIndexOutOfBoundsException e ){
				return false; 
			}
			
		}else{
			return false;
		}
	}
	private static boolean processPutCommand(PrintWriter out , BufferedReader in , String id,  String toPut ){
		// open file 
		try{
			File output = new File(fileName);
			PrintWriter writer = new PrintWriter(new FileWriter(output , true ));
			writer.append(id + ":" + toPut + "\n");
			out.println("Put " + toPut + " with id " +id );
			writer.close();
			
			out.close();
			in.close();
		}catch(Throwable e){
			return false;
		}
		
		// close the file
		return true; 
	}
	private static boolean processGetCommand(PrintWriter out , BufferedReader in , String toGet ){
		// open file 
		// find string 
		// return string in the print writer
		// close the file
		try{
			File output = new File(fileName);
			Scanner input = new Scanner(output);
			Boolean printed = false;
			String lastStr = "";
			while(input.hasNextLine()){
				String tmp = input.nextLine();
				//System.out.println(toGet);
				String [] strings = tmp.split(":");
				if(toGet == ""){
					lastStr = strings[1];
					continue; 
				}
				//System.out.println(strings[0]);
				if(strings[0].equals(toGet)){
					out.println(strings[1]);
					out.close();
					in.close();
					
					return true ; 
				} 
				
			}
			
			out.println(lastStr);
			
			out.close();
			in.close();
		}catch(Throwable e){
			return false;
		}
		
		return true;
	}
}