package Main;

public class Tache {
	
	private Navire nav;
	private double debut;
	private double fin;
	private int position;
	private int nbG;
	private double chargement;
	private double lastChange;
	
	public Tache(Navire nav, double debut, double fin, int position, int nbG, double chargement, double lastChange) {
		super();
		this.nav = nav;
		this.debut=debut;
		this.fin = fin;
		this.position = position;
		this.nbG = nbG;
		this.chargement = chargement;
		this.lastChange = lastChange;
	}

	public double getLastChange() {
		return lastChange;
	}

	public void setLastChange(double lastChange) {
		this.lastChange = lastChange;
	}

	public double getDebut() {
		return debut;
	}

	public void setDebut(double debut) {
		this.debut = debut;
	}

	public double getChargement() {
		return chargement;
	}

	public void setChargement(double chargement) {
		this.chargement = chargement;
	}

	public void setFin(double fin) {
		this.fin = fin;
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
		return "Navire "+this.nav+" Position " +this.position+" Fin "+this.fin+" NbGrue "+this.nbG+" Chargement "+this.chargement+" LastCgange "+this.lastChange;
	}

}
