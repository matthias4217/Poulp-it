package object_stream;

import java.net.*;
import java.io.*;

public class Main {
	public static void main(String[] args) throws Exception {
		final Serveur serveur = new Serveur(3000);
        Client client = new Client(null, 3000);

        Runnable r = new Runnable() {
    		public void run(){
					serveur.run();
					Objet_test ob = new Objet_test("hello");
					serveur.write(ob);
				}
         };
         Object inputObject;
         new Thread(r).start();
         client.start();
         //client.write(new Objet_test("1"));
         inputObject = client.read();
         System.out.println(inputObject);
         
         
	}
}
