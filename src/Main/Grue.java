package Main;

public class Grue {
	
	/* Identifiant */
	private int id;
	 
	/* Vitesse de déchargement */
	private int dechargement;
	
	

	public Grue() {
		super();
	}



	public Grue(int id, int dechargement) {
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



	public int getDechargement() {
		return dechargement;
	}



	public void setDechargement(int dechargement) {
		this.dechargement = dechargement;
	}
	
	public String toString(){
		return "Identifiant : "+this.getId()+" - Dechargement : "+this.getDechargement();
	}
	
	

}
