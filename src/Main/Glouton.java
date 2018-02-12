package Main;

import java.util.ArrayList;

public class Glouton {
	
	//fonction qui place un navire sur le quai
	public static boolean arrivee(ArrayList<Integer> quai, Navire nav, int pos){
		boolean rep = false;
		for(int i=0; i<nav.getTaille()+2;i++){
			if(pos+nav.getTaille()+2<quai.size()){
			if(quai.get(pos+i)==0){
				quai.set(pos+i,nav.getId());
				rep=true;
			}
			}
		}
		return rep;
	}
	
	//Fonction qui trouve un emplacement pour un navire
	public static int placement(ArrayList<Integer> quai, int taille){
		int rep = quai.size()+1;
		int pos=0;
		int t=0;
		int fpos=0;
		while(pos<quai.size()&&t<taille){
			if(quai.get(pos)!=0){
				pos++;
				t=0;
			}else{
				pos++;
				fpos=pos;
				t++;	
			}
		}
		if(t==taille){
			rep=fpos-taille;
		}
		return rep;
	}
	
	public void next_iter_Arrive 
	(ArrayList<Navire> navires, ArrayList<Grue> grues, ArrayList<Tache> taches, ArrayList<Navire> attente, ArrayList<Integer> quai, Navire n){
		/*boolean temp = insertion(quai, attente, n);
		if(!temp){
			attente.add(n);
		}*/
		
	}
	
	public void next_iter_Depart(ArrayList<Navire> navires, ArrayList<Grue> grues, ArrayList<Tache> taches, ArrayList<Navire> attente, ArrayList<Integer> quai, Navire n,double fin){
		Tache Tbat = taches.get(taches.size()-1);
		for(int i=n.getId()+1;i<navires.size();i++){
			if(navires.get(i).getArrive()<fin&& !attente.contains(navires.get(i))){
				attente.add(navires.get(i));
			}
		}
	}
	
	
	public static void main(String[] args) {
		ArrayList<Navire> navires = Donnees1.dNav();
		ArrayList<Grue> grues = Donnees1.dGrue();
		ArrayList<Navire> attente = new ArrayList<>();
		int NbBat = navires.size();
		int NbGrue = 5;
		int capGrue = 12;
		int Quai = 20;
		
		ArrayList<Tache> rep = new ArrayList<>();
		ArrayList<Integer> quai = new ArrayList<>();
		
		double deb=0;
		double dTot= NbGrue*capGrue;
		for (int i=0; i<NbBat;i++){
			double fin= deb+navires.get(i).getChargement()/dTot;
			fin=((int)(fin*100));
			fin=((double)(fin))/100;
			Tache t = new Tache(navires.get(i),deb,fin,0,NbGrue);
			deb=fin;
			rep.add(t);
		}
		//System.out.println(rep);
		
		//Test fonction placement
		
		ArrayList<Integer> quaiF = new ArrayList<>();
		for(int i=0;i<Quai;i++){
			quaiF.add(0);
		}
		System.out.println(quaiF);
		arrivee(quaiF,navires.get(0),0);
		arrivee(quaiF,navires.get(3),8);
		arrivee(quaiF,navires.get(5),14);
		System.out.println(quaiF);
		
	}
}
