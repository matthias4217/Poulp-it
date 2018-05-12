package object_stream;

import java.net.*;
import java.io.*;

public class Main {
	public static void main(String[] args) {
		System.out.println("lol1");
		final Serveur serveur = new Serveur(3000);
		System.out.println("lol2");
        Client client = new Client(null, 3000);
        System.out.println("lol3");
        Runnable r = new Runnable() {
    		public void run(){
				serveur.run();
             }
         };
         System.out.println("lol4");
         Object inputObject;
         System.out.println("lol5");
         new Thread(r).start();
         System.out.println("lol6");
         client.start();
         System.out.println("lol7");
         //client.write(new Objet_test("1"));
         System.out.println("lol8");
         inputObject = client.read();
         System.out.println("lol9");
         System.out.println(inputObject);
         
         
	}
}
