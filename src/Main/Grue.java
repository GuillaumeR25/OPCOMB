package Main;

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
		double pond = 16/17;
		this.setDechargement(getDechargement()*pond);
	}
	
	public String toString(){
		return "Identifiant : "+this.getId()+" - Dechargement : "+this.getDechargement();
	}
	
	

}
