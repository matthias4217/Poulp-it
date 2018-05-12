package object_stream;

import java.net.*;
import java.io.*;

class Serveur {
	int portNumber;
	ServerSocket serverSocket;
	Socket clientSocket;
	ObjectOutputStream out;
	ObjectInputStream in;
	Objet_test inputObject;
	Objet_test OutputObject;
	
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
	public Object read(){
		try {
			return in.readObject();
		}
		catch(IOException e){
			System.err.println("Couldn't get I/O for the connection to during the reading by server");
		}
		catch(Exception e1){
			System.err.println("Error during reading by server");
			e1.getMessage();
		}
		finally {return null;}
		
	}
	public void run() {
		try (
			ServerSocket serverSocket = new ServerSocket(portNumber);
			Socket clientSocket = serverSocket.accept();
			ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
	        ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
			
		){
			this.write("allo ?");
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
		
		/*inputObject = (Objet_test) this.read();
		this.write(inputObject);
		while() {
			Objet_test answer = new Objet_test("server" + inputObject.message);
			this.write(answer);
			this.write(inputObject);
			}*/
	}
}
