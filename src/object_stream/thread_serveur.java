package object_stream;

public class thread_serveur implements Runnable{
	public void run(){
		serveur.run();
		Objet_test ob = new Objet_test("hello");
		serveur.write(ob);
	}

}
