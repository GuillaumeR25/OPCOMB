package Main;

import java.util.ArrayList;

public class Grue {
	
	/* Identifiant */
	private int id;
	 
	/* Vitesse de d�chargement */
	private double dechargement;
	
	
	
	

	public Grue() {
		super();
	}



	public Grue(int id, double dechargement) {
		super();
		this.id = id;
		this.dechargement = dechargement;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public double getDechargement() {
		return dechargement;
	}



	public void setDechargement(double dechargement) {
		this.dechargement = dechargement;
	}
	
	public void ponderationD(){
		double pond = 16;
		pond=pond/17;
		System.out.println(pond);
		this.setDechargement(getDechargement()*pond);
	}
	
	public double dechargementTot(ArrayList<Grue> grues){
		double rep=0;
		for (int i=0; i<grues.size();i++){
			grues.get(i).ponderationD();
			rep=rep+grues.get(i).getDechargement();
		}
		return rep;
	}
	
	public String toString(){
		return "Identifiant : "+this.getId()+" - Dechargement : "+this.getDechargement();
	}
	
	public static void main(String[] args) {
		
		Grue g1 = new Grue(1,60);
		System.out.println(g1);
		g1.ponderationD();
		System.out.println(g1);
	}
}
