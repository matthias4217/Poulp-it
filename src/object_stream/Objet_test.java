package object_stream;

import java.io.Serializable;

public class Objet_test implements Serializable {
	
	String message;
	public Objet_test(String message) {
		this.message = message;
	}
	public String toString() {
		return(this.message);
	}
}
