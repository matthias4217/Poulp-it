package test;

public class Carrelage {

	int x;
	int y;






	public Carrelage(int x, int y) {
		this.x = x;
		this.y = y;
	}






	@Override public boolean equals(Object other) {
		if(!(other instanceof Carrelage)) {
			return false;
		}
		Carrelage carrelage = (Carrelage) other;
		return (this.x == carrelage.x) && (this.y == carrelage.y);
	}

}
