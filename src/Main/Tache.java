package Main;

public class Tache {
	
	private Navire nav;
	private double debut;
	private double fin;
	private int position;
	private int nbG;
	
	public Tache(Navire nav, double debut, double fin, int position, int nbG) {
		super();
		this.nav = nav;
		this.debut=debut;
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

	public double getFin() {
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
		return "Navire "+this.nav+" Position " +this.position+" Fin "+this.fin;
	}

}
