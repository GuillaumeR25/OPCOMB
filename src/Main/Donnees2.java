package Main;

public class Donnees2 {
	
	/* Plage horaire sur laquelle on cherche à établir une planification */
	private int temps = 24;
	
	public static void main (String[] args){
	
	/* Génération de la deuxième instance */
	
	/* Longueur du quai */
	int quai = 16;
	
	/* Navires */
	Navire n1 = new Navire(1, 2500, 8, 1);
	Navire n2 = new Navire(2, 1250, 4, 2);
	Navire n3 = new Navire(3, 943, 4, 4);
	Navire n4 = new Navire(4, 1768, 6, 5);
	Navire n5 = new Navire(5, 367, 2, 7);
	Navire n6 = new Navire(6, 1345, 5, 7);
	Navire n7 = new Navire(7, 889, 4, 8);
	Navire n8 = new Navire(8, 1234, 4, 10);
	Navire n9 = new Navire(9, 789, 3, 13);
	Navire n10 = new Navire(10, 123, 1, 13);
	Navire n11 = new Navire(11, 456, 3, 17);
	Navire n12 = new Navire(12, 2387, 7, 18);
	
	/* Grues */
	Grue g1 = new Grue(1, 15);
	Grue g2 = new Grue(2, 23);
	Grue g3 = new Grue(3, 17);
	Grue g4 = new Grue(4, 25);
	Grue g5 = new Grue(5, 25);
	Grue g6 = new Grue(6, 22);
	Grue g7 = new Grue(7, 20);
	Grue g8 = new Grue(8, 21);
	Grue g9 = new Grue(9, 16);
	Grue g10 = new Grue(10, 24);
	}

}
