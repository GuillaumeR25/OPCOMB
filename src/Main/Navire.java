package Main;

public class Navire {
	
	/* Identifiant */
	private int id;
	
	/* Nombre de conteneurs */
	private int chargement;
	
	/* Taille du navire */
	private int taille;
	
	/* Heure d'arrivée du navire */
	private int arrive;
	
	
	
	

	public Navire() {
		super();
	}



	public Navire(int id, int chargement, int taille, int arrive) {
		super();
		this.id = id;
		this.chargement = chargement;
		this.taille = taille;
		this.arrive = arrive;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public int getChargement() {
		return chargement;
	}



	public void setChargement(int chargement) {
		this.chargement = chargement;
	}



	public int getTaille() {
		return taille;
	}



	public void setTaille(int taille) {
		this.taille = taille;
	}
	

	public int getArrive() {
		return arrive;
	}



	public void setArrive(int arrive) {
		this.arrive = arrive;
	}



	/* Retourne le nombre d'unité de quai indisponible lorsque le navire est à quai.
	 * Correspond à la taille du navire plus une unité à l'arrière et une unité à l'avant.
	 */
	public int encombrement(){
		 return this.getTaille()+2;
	}
	
	public String toString(){
		return "Identifiant : "+this.getId()+" - Chargement"+this.getChargement()+
				" - Taille : "+this.getTaille()+" - Arrive : "+this.getArrive();
	}
	
	
	
	

}
