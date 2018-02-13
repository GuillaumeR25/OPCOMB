package Main;

import java.util.ArrayList;

public class Tache {
	
	private Navire nav;
	private double debut;
	private double fin;
	private int position;
	private ArrayList<String> grue;
	private int NbG;
	private double chargement;
	private double lastChange;
	
	public Tache(Navire nav, double debut, double fin, int position, ArrayList<String> grue, int NbG, double chargement, double lastChange) {
		super();
		this.nav = nav;
		this.debut=debut;
		this.fin = fin;
		this.position = position;
		this.grue = grue;
		this.NbG = NbG;
		this.chargement = chargement;
		this.lastChange = lastChange;
	}

	public int getNbG() {
		return NbG;
	}

	public void setNbG(int nbG) {
		NbG = nbG;
	}

	public void setGrue(ArrayList<String> grue) {
		this.grue = grue;
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

	public ArrayList<String> getGrue() {
		return grue;
	}
	
	public String toString(){
		return "Navire "+this.nav+" Position " +this.position+" Debut "+this.debut+" Fin "+this.fin+" Grue "+grue.toString()+" Chargement "+this.chargement+" LastChange "+this.lastChange;
	}

}
