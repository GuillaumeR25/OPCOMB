package Main;

import java.util.ArrayList;

public class DonneesAl {
	
	private int NbNav;
	private ArrayList<Navire> navires;
	
	
	public DonneesAl(int nbNav) {
		NbNav = nbNav;
		ArrayList<Navire> navires = new ArrayList<>();
		int GB=0;
		int late=0;
		int count=0;
		while(count<NbNav){
			int chargement = (int)((Math.random()*2420)+80);
			if(chargement>1600){
				GB++;
			}
			int taille = chargement/300+2;
			int arrive = (int)(Math.random()*2000);
			if(arrive>1400){
				late++;
			}
			if(GB<3&&late<3){
			Navire n_i = new Navire(count+1,chargement,taille,arrive);
			navires.add(n_i);
			count++;
			}	
		}
		this.navires=navires;
	}


	
	public static void main(String[] args) {
		DonneesAl d = new DonneesAl(6);
		System.out.println(d.navires);
	}

}
