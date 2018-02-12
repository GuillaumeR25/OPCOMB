package Main;

public class Tache {
	
	private Navire nav;
	private int fin;
	private int position;
	private int nbG;
	
	public Tache(Navire nav, int fin, int position, int nbG) {
		super();
		this.nav = nav;
		this.fin = fin;
		this.position = position;
		this.nbG = nbG;
	}

	public Navire getNav() {
		return nav;
	}

	public void setNav(Navire nav) {
		this.nav = nav;
	}

	public int getFin() {
		return fin;
	}

	public void setFin(int fin) {
		this.fin = fin;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getNbG() {
		return nbG;
	}

	public void setNbG(int nbG) {
		this.nbG = nbG;
	}
	
	public String toString(){
		return this.nav+" "+this.position+" "+this.fin;
	}

}
