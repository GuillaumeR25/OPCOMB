package Main;

import java.util.ArrayList;

public class Glouton2 {
	
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
	
	// Fonction mettant à jour le quai lors de l'arrivée d'un navire.
	public static void arrive(ArrayList<Integer> quai, Navire n, int pos){
		int encombrement = n.encombrement();
		int id = n.getId();
		for(int i=0; i<encombrement; i++){
			quai.set(i+pos, id);
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
	
	// Fonction qui trouve un emplacement pour un navire
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
	
	// Fonction qui rend la différence en heure entre deux représentation d'heure en "format double"
	// h1 est antérieure à h2
	public static double difference (double h1, double h2){
		double res = 0;
		if (h1 < h2){
			if(h1%100 <= h2%100){
				res = (int)h2/100 - (int)h1/100;
				res += (h2%100-h1%100)/60;
			}else{
				res = (int)h2/100 - (int)h1/100;
				res += (h2%100-h1%100)/60;
			}
		}
		return res;
	}
	
	// Fonction qui répartie les grues en fonction du chargement des navires
	public static void repartition(ArrayList<Tache> taches, int NbG){
		double s = 0;
		for(Tache t : taches){
			s += t.getChargement();
		}
		for(int i=0; i<taches.size()-1; i++){
			int temp = (int)Math.floor(NbG*(taches.get(i).getChargement()/s));
			taches.get(i).setNbG(temp);
		}
		int g = 0;
		for(int i=0; i<taches.size()-1; i++){
			g += taches.get(i).getNbG();
		}
		taches.get(taches.size()-1).setNbG(NbG-g);
	}
	
	// Fonction qui met à jour les taches en cours.
	
	public static void update_tache (ArrayList<Tache> taches, ArrayList<Tache> rep, ArrayList<Navire> restant, ArrayList<Navire> attente, 
			ArrayList<Integer> quai, double temps, int capaGrue, int NbG){
		ArrayList<Tache> temp = new ArrayList<Tache>();
		for(Tache t : taches){
			t.getGrue().add(temps+" - "+t.getNbG());
			System.out.println(quai.toString());
			t.setChargement(Math.max(t.getChargement()-Math.floor(difference(t.getLastChange(), temps)*capaGrue*t.getNbG()), 0));
			t.setLastChange(temps);
			if(t.getChargement() == 0){
				t.setNbG(0);
				t.setFin(t.getLastChange());
				temp.add(t);
				depart(quai, t.getNav(), t.getPosition());
				rep.add(t);
			}
		}
		repartition(taches, NbG);
		for(Tache t : temp){
			taches.remove((Object)t);
		}
	}
	
	public static void next_iter_Arrive(ArrayList<Tache> taches, ArrayList<Tache> rep, ArrayList<Navire> restant, ArrayList<Navire> attente, 
			ArrayList<Integer> quai, double temps, int capaGrue, int NbG, Navire n){
		int newpos = placement(quai, n.encombrement());
		if(newpos >= quai.size()){
			attente.add(n);
			update_tache(taches, rep, restant, attente, quai, temps, capaGrue, NbG);
		}else{
			arrive(quai, n, newpos);
			double temp = ((int)n.getArrive()/100)*100+((int)(n.getArrive()%100+15)/60)*100+((int)(n.getArrive()%100+15))%60;
			Tache Tcurrent = new Tache(n, temp, temp, newpos, new ArrayList<String>(), 0, n.getChargement(), temp); 
			taches.add(Tcurrent);
			update_tache(taches, rep, restant, attente, quai, temp, capaGrue, NbG);
		}
		restant.remove((Object)n);
		
	}
	
	public static void update_attente (ArrayList<Tache> taches, ArrayList<Tache> rep, ArrayList<Navire> restant, ArrayList<Navire> attente, 
			ArrayList<Integer> quai, double temps, int capaGrue, int NbG){
		int quaiL = quai.size();
		ArrayList<Navire> PEC = new ArrayList<Navire>();
		for(Navire n : attente){
			int encombrement = n.encombrement();
			int newpos = placement(quai, encombrement);
			if(newpos < quaiL){
				attente_enCours(taches, rep, restant, attente, quai, temps, capaGrue, NbG, n, newpos);
				PEC.add(n);
			}
		}
		for(Navire n : PEC){
			attente.remove((Object)n);
		}
	}
	
	public static void attente_enCours (ArrayList<Tache> taches, ArrayList<Tache> rep, ArrayList<Navire> restant, ArrayList<Navire> attente, 
			ArrayList<Integer> quai, double temps, int capaGrue, int NbG, Navire n, int pos){
		arrive(quai, n, pos);
		double temp = ((int)temps/100)*100+((int)(temps%100+15)/60)*100+((int)(temps%100+15))%60;
		System.out.println("TEMPS : "+temps);
		Tache t = new Tache(n, temp, temp, pos, new ArrayList<String>(), 0, n.getChargement(), temp);
		taches.add(t);
		update_tache(taches, rep, restant, attente, quai, temp, capaGrue, NbG);
		
		
	}
	
	public static void main(String[] args) {
		
		
		//Données du problème
		
		//DonneesAl donnees = new DonneesAl(3);
		
		// Liste des navires
		ArrayList<Navire> navires = new ArrayList<Navire>();
		
		navires.add(new Navire(1, 234, 5, 123));
		navires.add(new Navire(2, 124, 3, 234));
		navires.add(new Navire(3, 321, 6, 447));
		navires.add(new Navire(4, 435, 7, 1023));
		navires.add(new Navire(5, 276, 5, 1245));
		navires.add(new Navire(6, 94, 2, 1254));
		navires.add(new Navire(7, 157, 4, 1732));
		System.out.println(navires);
		
		//Nombre de grue
		int NbG = 6;
		
		// Capacité de déchargement des grues
		int capaGrue = 20;
		
		capaGrue = (int)((double)capaGrue*(16.0/17.0));
		
		// Nombre de grutiers
		int NbGrutiers = 50;
		
		// Longueur du quai;
		int  quaiL = 15;
		
		// Variables du problème
		
		// Navires restants
		ArrayList<Navire> restant = navires;
		
		// Navire en attente
		ArrayList<Navire> attente = new ArrayList<Navire>();
		
		// Tache en cours
		ArrayList<Tache> taches = new ArrayList<Tache>();
		
		// Quai
		ArrayList<Integer> quai = new ArrayList<Integer>();
		for( int i=0; i<quaiL; i++){
			quai.add(0);
		}
		
		// Liste des taches terminées
		ArrayList<Tache> solution = new ArrayList<Tache>();;
		
		
		//Execution du code
		
		for(int i=1; i<8; i++){
			double temp = restant.get(0).getArrive();
			double time = restant.get(0).getArrive();
			next_iter_Arrive(taches, solution, restant, attente, quai, time, capaGrue, NbG, restant.get(0));
			update_attente(taches, solution, restant, attente, quai, time, capaGrue, NbG);
		}
		
		
		int compt = 1900;
		while(!restant.isEmpty() || !attente.isEmpty() || !taches.isEmpty()){
			update_tache(taches, solution, restant, attente, quai, compt, capaGrue, NbG);
			update_attente(taches, solution, restant, attente, quai, compt, capaGrue, NbG);
			compt += 15;
		}

		
		
		
		//Affichage des variables
		System.out.println("Restant : "+restant.toString());
		System.out.println("Attente : "+attente.toString());
		System.out.println("Taches en cours : "+taches.toString());
		System.out.println("Quai : "+quai.toString());
		System.out.println();
		System.out.println("Solution : ");
		for(Tache t : solution){
			System.out.println(t.toString());
		}
		
		
		
	}

}
