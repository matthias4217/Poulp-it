package object_stream;

import java.net.*;
import java.io.*;

class Serveur {
	int portNumber;
	ServerSocket serverSocket;
	Socket clientSocket;
	ObjectOutputStream out;
	ObjectInputStream in;
	
	public Serveur(int portNumber) {
		this.portNumber = portNumber;
	}
	
	public void close() throws IOException {
		in.close();
		out.close();
		serverSocket.close();
	
	}
	public void write(Object object){
		try {
			out.writeObject(object);
		}
		catch(IOException e){
			System.err.println("Couldn't get I/O for the connection during the writing by server");
		}
		catch(Exception e1){
			System.err.println("Error during writing by server");
			e1.getMessage();
		}
	}
	public Object read() throws ClassNotFoundException, IOException{
		return in.readObject();

	}
	public void run() {
		try {
			this.serverSocket = new ServerSocket(portNumber);
			this.clientSocket = serverSocket.accept();
			this.out = new ObjectOutputStream(clientSocket.getOutputStream());
	        this.in = new ObjectInputStream(clientSocket.getInputStream());
			
		}
		catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
		}
		catch(Exception e1) {
			System.out.println("Exception catch during the construction of the server");
			e1.getMessage();
		}
	}
}
