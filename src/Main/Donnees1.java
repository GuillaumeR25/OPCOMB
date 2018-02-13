package Main;

import java.util.ArrayList;

public class Donnees1 {
	
	/* Plage horaire sur laquelle on cherche à établir une planification */
	private int temps = 24;
	

		
		/* Génération de la première instance */
		
		/* Longueur du quai */
		int quai = 12;
		
		public static ArrayList<Navire> dNav(){
		
			ArrayList<Navire> rep = new ArrayList<>();
			Navire n1 = new Navire(1, 250, 4, 100);
			Navire n2 = new Navire(2, 2500, 7, 100);
			Navire n3 = new Navire(3, 1200, 6, 300);
			Navire n4 = new Navire(4, 130, 2, 500);
			Navire n5 = new Navire(5, 1780, 4, 1200);
			Navire n6 = new Navire(6, 670, 5, 1300);
			Navire n7 = new Navire(7, 344, 3, 1600);
			rep.add(n1);
			rep.add(n2);
			rep.add(n3);
			rep.add(n4);
			rep.add(n5);
			rep.add(n6);
			rep.add(n7);
			return rep;
		
		}
		
		public static ArrayList<Grue> dGrue(){
			ArrayList<Grue> rep = new ArrayList<>();
			Grue g1 = new Grue(1, 20);
			Grue g2 = new Grue(2, 25);
			Grue g3 = new Grue(3, 16);
			Grue g4 = new Grue(4, 22);
			rep.add(g1);
			rep.add(g2);
			rep.add(g3);
			rep.add(g4);
			return rep;
		}
		
		public static void main(String[] args) {
			ArrayList<Grue> grues = dGrue();
			System.out.println(grues);
			ArrayList<Navire> navires = dNav();
			System.out.println(navires);
		}

}
