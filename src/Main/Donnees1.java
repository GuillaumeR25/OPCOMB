package Main;

public class Donnees1 {
	
	/* Plage horaire sur laquelle on cherche à établir une planification */
	private int temps = 24;
	
	public static void main (String[] args){
		
		/* Génération de la première instance */
		
		/* Longueur du quai */
		int quai = 12;
		
		/* Navires */
		Navire n1 = new Navire(1, 250, 4, 1);
		Navire n2 = new Navire(2, 2500, 7, 1);
		Navire n3 = new Navire(3, 1200, 6, 3);
		Navire n4 = new Navire(4, 130, 2, 5);
		Navire n5 = new Navire(5, 1780, 4, 12);
		Navire n6 = new Navire(6, 670, 5, 13);
		Navire n7 = new Navire(7, 344, 3, 16);
		
		/* Grues */
		Grue g1 = new Grue(1, 20);
		Grue g2 = new Grue(2, 25);
		Grue g3 = new Grue(3, 16);
		Grue g4 = new Grue(4, 22);
	}
		

}
