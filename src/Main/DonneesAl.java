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
	
	public static void main(String[] args) {
		DonneesAl d = new DonneesAl(6);
		System.out.println(d.navires);
	}

}
