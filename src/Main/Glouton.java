package Main;

import java.util.ArrayList;

public class Glouton {
	
	//fonction qui place un navire sur le quai
	public boolean arrivee(ArrayList<Integer> quai, Navire nav, int pos){
		boolean rep = false;
		for(int i=0; i<nav.getTaille();i++){
			if(quai.get(pos+i)==0){
				quai.set(pos+i,nav.getId());
				rep=true;
			}
		}
		return rep;
	}
	
	//fonction qui trouve un emplacement pour un navire
	public int placement(ArrayList<Integer> quai, int taille){
		int rep = quai.size()+1;
		int pos=0;
		int t=0;
		int fpos=0;
		while(pos<quai.size()&&t<taille){
			if(quai.get(pos)!=0){
				pos++;
				t=0;
			}else{
				fpos=pos;
				pos++;
				t++;	
			}
		}
		if(t==taille){
			rep=fpos;
		}
		return rep;
	}
	
	public boolean insertion (ArrayList<Integer> quai, ArrayList<Navire> attente,  Navire n){
		int taille = n.encombrement();
		int newpos = placement(quai, taille);
		boolean res;
		if(newpos >= quai.size()){
			attente.add(n);
			res = false;
		}else{
			for(int i=0; i<taille; i++){
				quai.set(i+newpos, n.getId());
			}
			res = true;
		}
		return res;
	}
	
	public void next_iter_Arrive 
	(ArrayList<Navire> navires, ArrayList<Grue> grues, ArrayList<Tache> taches, ArrayList<Navire> attente, ArrayList<Integer> quai, Navire n){
		boolean temp = insertion(quai, attente, n);
		if(!temp){
			attente.add(n);
		}
		Tache
		
	}
	
	
	public static void main(String[] args) {
		ArrayList<Navire> navires = Donnees1.dNav();
		ArrayList<Grue> grues = Donnees1.dGrue();
		ArrayList<Navire> attente = new ArrayList<>();
		int NbBat = navires.size();
		int NbGrue = 5;
		int capGrue = 12;
		int Quai = 20;
		int NbMin = 1440;
		
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
		System.out.println(rep);
		
	}
}
