package core;

import java.net.*;
import java.io.*;

class Serveur {
	int portNumber;
	int nbPlayer;
	ServerSocket serverSocket;
	Socket[] clientSockets;
	ObjectOutputStream[] out;
	ObjectInputStream[] in;
	
	
	public Serveur(int portNumber, int nbPlayer) {
		this.portNumber = portNumber;
		this.nbPlayer = nbPlayer;
		this.clientSockets = new Socket[nbPlayer];
		this.in = new ObjectInputStream[nbPlayer];
		this.out = new ObjectOutputStream[nbPlayer];
		try {
			serverSocket = new ServerSocket(portNumber);
		} catch (IOException e2) {
			System.err.println(e2.getCause());
		}
	}
	public void acceptConnection(int k) {
		
			try {
				Socket client = serverSocket.accept();
				clientSockets[k] = client;
				out[k] = new ObjectOutputStream(client.getOutputStream());
		        in[k] = new ObjectInputStream(client.getInputStream());
			}
			catch (IOException e) {
	            System.err.println("Exception caught when trying to listen on port "
	                + portNumber + " or listening for a connection");
	            System.err.println(e.getMessage());
			}
			catch(IndexOutOfBoundsException e1) {
				System.err.println("Can't accept a new player : "+k+"> nbPlayer");
			}
		
	}
	public void close() throws IOException {
		for(int k =0; k<=2;k++) {
			in[k].close();
			out[k].close();
		}
		serverSocket.close();
	
	}
	public void write(Object object, int numClient){
		try {
			out[numClient].writeObject(object);
		}
		catch(IOException e){
			System.err.println("Couldn't get I/O for the connection during the writing by server");
		}
		catch(Exception e1){
			System.err.println("Error during writing by server");
			e1.getMessage();
		}
	}
	public void writeForAll(Object object){
		for(int k = 0; k<nbPlayer; k++) {
			try {
				out[k].writeObject(object);
			}
			catch(IOException e){
				System.err.println("Couldn't get I/O for the connection during the writing by server");
			}
			catch(Exception e1){
				System.err.println("Error during writing by server");
				e1.getMessage();
			}
		}
		
	}
	public Object read(int numClient) throws ClassNotFoundException, IOException{
		return in[numClient].readObject();

	}
	
}
