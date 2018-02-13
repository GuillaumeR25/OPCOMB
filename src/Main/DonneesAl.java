package Main;

import java.util.ArrayList;
import java.util.Collections;

public class DonneesAl {
	
	private int NbNav;
	private ArrayList<Navire> navires;
	
	
	public DonneesAl(int nbNav) {
		NbNav = nbNav;
		ArrayList<Navire> navires = new ArrayList<>();
		int count=0;
		while(count<NbNav){
			int chargement = (int)((Math.random()*1920)+80);
			int taille = chargement/300+2;
			int arrive = (int)(Math.random()*2000);
			Navire n_i = new Navire(count+1,chargement,taille,arrive);
			navires.add(n_i);
			count++;	
		}
		Collections.sort(navires);
		this.navires=navires;
	}


	
	public static void main(String[] args) {
		DonneesAl d = new DonneesAl(9);
	}

}
