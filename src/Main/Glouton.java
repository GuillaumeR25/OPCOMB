package Main;

import java.util.ArrayList;

public class Glouton {
	
	// Fonction qui place un navire sur le quai
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
	
	// Fonction mettant à jour le quai lors du départ d'un navire
	public static void depart(ArrayList<Integer> quai, Navire nav, int pos){
		for(int i=0;i<nav.getTaille()+2;i++){
			quai.set(pos+i, 0);
		}
	}
	
	// Fonction mettant à jour la liste des navires en attente dans le port pour un temps t
	public static void aJour(ArrayList<Navire> restant,ArrayList<Navire> attente, double temps){
		int l =restant.size();
		ArrayList<Navire> withdraw =new ArrayList<>();
		for(int i=0; i<l; i++){
			Navire n = restant.get(i);
			if(n.getArrive()<=temps){
				attente.add(n);
				withdraw.add(n);
			}
		}
		for(int i=0; i<withdraw.size();i++){
			restant.remove(withdraw.get(i));
		}
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
	
	// Fonction mettant à jour lors d'un deprt de bateau la liste des bateau restant, la liste des bateau en attente, le quai
	public static void next_iter_Depart(ArrayList<Navire> restant,ArrayList<Tache> taches, ArrayList<Navire> attente, ArrayList<Integer> quai,double fin){
		Tache Tcurrent = taches.get(taches.size()-1);
		Navire n = Tcurrent.getNav();
		aJour(restant, attente,fin);
		depart(quai,n,Tcurrent.getPosition());
		ArrayList<Navire> withdraw = new ArrayList<>();
		for (int i=0; i<attente.size();i++){
			System.out.println(attente.size());
			Navire nav = attente.get(i);
			int place = placement(quai,nav.getTaille()+2);
			if(place<quai.size()){
				arrivee(quai,nav,place);
				withdraw.add(nav);
			}
		}
		for (int i=0;i<withdraw.size();i++){
			attente.remove(withdraw.get(i));
		}
	}
	
	
	public static void main(String[] args) {
		// Liste des navires à placer
		ArrayList<Navire> navires = Donnees1.dNav();
		
		// Liste des grues disponibles
		ArrayList<Grue> grues = Donnees1.dGrue();
		
		// Liste des navires en attente dans le port
		ArrayList<Navire> attente = new ArrayList<>();
		
		// Liste des navires qui n'ont pas été déchargés
		ArrayList<Navire> restant = new ArrayList<>();
		restant.addAll(navires);
		
		
		int NbBat = navires.size();
		int NbGrue = grues.size();
		int capGrue = 9;
		int Quai = 20;
		
		// Réponse
		ArrayList<Tache> rep = new ArrayList<>();
		
		// Liste représentant le quai
		ArrayList<Integer> quai = new ArrayList<>();
		
		/*double deb=0;
		double dTot= NbGrue*capGrue;
		for (int i=0; i<NbBat;i++){
			double fin= deb+navires.get(i).getChargement()/dTot;
			fin=((int)(fin*100));
			fin=((double)(fin))/100;
			Tache t = new Tache(navires.get(i),deb,fin,0,NbGrue);
			deb=fin;
			rep.add(t);
		}*/
		//System.out.println(rep);
		
		//Test fonction placement
		
		ArrayList<Integer> quaiF = new ArrayList<>();
		for(int i=0;i<Quai;i++){
			quaiF.add(0);
		}
		//System.out.println(quaiF);
		//arrivee(quaiF,navires.get(0),0);
		Tache t = new Tache(navires.get(0),0,4,0,NbGrue);
		rep.add(t);
		//arrivee(quaiF,navires.get(3),8);
		//System.out.println(quaiF);
		//depart(quaiF,navires.get(0),0);
		//System.out.println(quaiF);
		System.out.println(rep);
		
		//Test fonction next_iter_Depart
		restant.remove(0);
		System.out.println("Fonction next_iter_Depart");
		next_iter_Depart(restant, rep, attente, quaiF, 4);
		System.out.println("Résultats");
		System.out.println("Bateau restant "+restant);
		System.out.println("Bateau en attentes "+attente);
		System.out.println("Quai : "+quaiF);
		
	}
}
