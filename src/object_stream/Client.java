package object_stream;

import java.net.*;
import java.io.*;

class Client {
	String hostName;
	int portNumber;
	Socket dSocket;
	ObjectOutputStream out;
	ObjectInputStream in;
	
	public Client(String hostName, int portNumber) {
		this.hostName = hostName;
		this.portNumber = portNumber;
		
		
	}
	public void start() {
		try {
			this.dSocket = new Socket(hostName, portNumber);
			this.out = new ObjectOutputStream(dSocket.getOutputStream());
			this.in = new ObjectInputStream(dSocket.getInputStream());
		} 
		catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
            } 
		catch (IOException e1) {
            System.err.println("Couldn't get I/O for the connection to " +
                hostName + "during the construction of the Client");
            System.exit(1);
            }
	}
	public void close() throws IOException {
		in.close();
		out.close();
		dSocket.close();
	}
	public void write(Object object){
		try {
			out.writeObject(object);
		}
		catch(IOException e){
			System.err.println("Couldn't get I/O for the connection to " + hostName + "during the writing by client");
		}
		catch(Exception e1){
			System.err.println("Error during writing by client");
			e1.getMessage();
		}
	}
	public Object read() throws ClassNotFoundException, IOException {
			return(this.in.readObject());
	}
	
}