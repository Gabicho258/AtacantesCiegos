
public class Tanque {
	
	
	private boolean isVisible = false;
	private boolean isAlive;
	private int vida;
	private int points;
	private boolean wasAtacked = false;
	private String name;
	
	public Tanque() {
		//Esto es para hacerlo pasar por el if al mostrar el tablero
		//De igual manera será reemplazado ya que no está vivo
		//this.name = key;
		isAlive = false;
	}
	public Tanque(String name, int points, int vida) {
		this.name = name;
		isAlive = true;
		this.points = points;
		this.vida = vida;
	}
	public int getVida() {
		return vida;
	}
	public boolean isAlive() {
		return isAlive;
	}
	public boolean isVisible() {
		return isVisible;
	}
	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
	public boolean wasAtacked() {
		return wasAtacked;
	}
	public int getPoints() {
		return points;
	}
	public void dicreaseHealth() {
		if (this.vida >1) 
			this.vida--;
		else 
			this.isAlive = false;
		this.wasAtacked = true;
	}
	public void changeVisibility() {
		if (isAlive) 
			this.isVisible = !(this.isVisible);
		else 
			this.isVisible = true;
	}
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return "- " + name;
	}
}
