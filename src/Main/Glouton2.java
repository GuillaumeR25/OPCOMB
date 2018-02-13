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
	
	//Fonction permmettant de dicrétiser le temps
	public static ArrayList<Navire> timeLine (ArrayList<Navire> navires, double pas){
		ArrayList<Navire> rep = new ArrayList<>();
		double prec=0;
		double suiv=prec+pas;
		int count = 0;
		int tot=(int)(2400/pas);
		int nav=0;
		while(count<tot){
			System.out.println(prec);
			if(navires.get(nav).getArrive()>=prec && navires.get(nav).getArrive()<suiv){
				rep.add(navires.get(nav));
				System.out.println("nav "+nav);
				nav++;
			}else{
				rep.add(null);
				prec=suiv;
				suiv=prec+pas;
				count++;
			}
			
		}
		return rep;
	}
	
	// Fonction mettant � jour le quai lors du d�part d'un navire
	public static void depart(ArrayList<Integer> quai, Navire nav, int pos){
		for(int i=0;i<nav.getTaille()+2;i++){
			quai.set(pos+i, 0);
		}
	}
	
	// Fonction mettant � jour le quai lors de l'arriv�e d'un navire.
	public static void arrive(ArrayList<Integer> quai, Navire n, int pos){
		int encombrement = n.encombrement();
		int id = n.getId();
		for(int i=0; i<encombrement; i++){
			quai.set(i+pos, id);
		}
	}
	
	// Fonction mettant � jour la liste des navires en attente dans le port pour un temps t
	public static void aJour(ArrayList<Navire> restant,ArrayList<Navire> attente, double temps){
		int l = restant.size();
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
	
	// Fonction qui rend la diff�rence en heure entre deux repr�sentation d'heure en "format double"
	// h1 est ant�rieure � h2
	public static double difference (double h1, double h2){
		if (h1 > h2){
			return -1;
		}else{
		double res = Math.floor((h2-h1)/100) + (h2-h1)%100/60;
		return res;
		}
	}
	
	// Fonction qui r�partie les grues en fonction du chargement des navires
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
	
	// Fonction qui met � jour les taches en cours.
	
	public static void update_tache (ArrayList<Tache> taches, ArrayList<Tache> rep, ArrayList<Navire> restant, ArrayList<Navire> attente, 
			ArrayList<Integer> quai, double temps, int capaGrue, int NbG){
		for(Tache t : taches){
			System.out.println(t.getLastChange());
			System.out.println(temps);
			System.out.println("Diff : "+difference(t.getLastChange(), temps));
			System.out.println(t.getChargement()+" -- "+Math.floor(difference(t.getLastChange(), temps)*capaGrue*t.getNbG()));
			System.out.println(t.getNbG());
			t.setChargement(Math.max(t.getChargement()-Math.floor(difference(t.getLastChange(), temps)*capaGrue*t.getNbG()), 0));
			t.setLastChange(temps);
			if(t.getChargement() == 0){
				t.setNbG(0);
				t.setFin(t.getLastChange());
				taches.remove((Object)t);
				depart(quai, t.getNav(), t.getPosition());
				rep.add(t);
			}
		}
		repartition(taches, NbG);
	}
	
	public static void next_iter_Arrive(ArrayList<Tache> taches, ArrayList<Tache> rep, ArrayList<Navire> restant, ArrayList<Navire> attente, 
			ArrayList<Integer> quai, double temps, int capaGrue, int NbG, Navire n){
		int newpos = placement(quai, n.encombrement());
		if(newpos >= quai.size()){
			attente.add(n);
			update_tache(taches, rep, restant, attente, quai, temps, capaGrue, NbG);
		}else{
			arrive(quai, n, newpos);
			Tache Tcurrent = new Tache(n, n.getArrive(), n.getArrive(), newpos, 0, n.getChargement(), n.getArrive()); 
			taches.add(Tcurrent);
			update_tache(taches, rep, restant, attente, quai, temps, capaGrue, NbG);
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
				next_iter_Arrive(taches, rep, restant, attente, quai, temps, capaGrue, NbG, n);
				PEC.add(n);
			}
		}
		for(Navire n : PEC){
			attente.remove((Object)n);
		}
	}
	
	public static String affichage (ArrayList<Tache> taches){
		String rep ="";
		for(int i=0; i<taches.size(); i++){
			rep=rep+taches.get(i)+"\n";
		}
		return rep;
	}
	
	public static void main(String[] args) {
		
		
		//Donn�es du probl�me
		
		//Liste des navires
		ArrayList<Navire> navires = new ArrayList<Navire>();
		
		for(int i=0; i<5; i++){
			navires.add(new Navire(i+1, 200, 4, 100*i));
		}
		
		
		//ArrayList<Navire> navires = Donnees1.dNav();
		
		//Nombre de navires
		int NbV = navires.size();
		
		//Nombre de grue
		int NbG = 5;
		
		// Capacit� de d�chargement des grues
		int capaGrue = 10;
		
		capaGrue = (int)(((double)capaGrue)*(16.0/17.0));
		
		// Longueur du quai;
		int  quaiL = 20;
		
		// Variables du probl�me
		
		// Navires restants
		ArrayList<Navire> restant = navires;
		
		// Navire en attente
		ArrayList<Navire> attente = new ArrayList<Navire>();
		
		// Navire ayant ete décharge
		ArrayList<Navire> decharge = new ArrayList<>();
		
		// Tache en cours
		ArrayList<Tache> taches = new ArrayList<Tache>();
		
		// Quai
		ArrayList<Integer> quai = new ArrayList<Integer>();
		for( int i=0; i<quaiL; i++){
			quai.add(0);
		}
		
		// Boolean solution
		boolean sol = false;
		
		// Liste des taches termin�es
		ArrayList<Tache> solution = new ArrayList<Tache>();;
		
		/*
		//Execution du code
		System.out.println("Début éxécution");
		for(int i=0; i<3; i++){
			System.out.println("i"+i);
			//double temp = restant.get(0).getArrive();
			next_iter_Arrive(taches, solution, restant, attente, quai, restant.get(0).getArrive(), capaGrue, NbG, restant.get(0));
			if(taches.size() > 0){
				System.out.println(taches.get(0).toString());
			}
			if(taches.size() > 1){
				System.out.println(taches.get(1).toString());
			}
			update_attente(taches, solution, restant, attente, quai, i*100, capaGrue, NbG);
			if(taches.size() > 0){
				System.out.println(taches.get(0).toString());
			}
			if(taches.size() > 1){
				System.out.println(taches.get(1).toString());
			}
		}
		*/
		System.out.println("navires");
		System.out.println(navires);
		ArrayList<Navire> timeL = timeLine(navires,15);
		System.out.println("timeL");
		System.out.println(timeL);
		
		
		//Affichage des variables
		/*System.out.println("Restant : "+restant.toString());
		System.out.println("Attente : "+attente.toString());
		System.out.println("Taches en cours : "+taches.toString());
		System.out.println("Quai : "+quai.toString());
		System.out.println("Affichage solution");
		//System.out.println(affichage(solution));
		System.out.println("Hello");
		System.out.println("Solution : "+solution.toString());*/
		
		System.out.println(capaGrue);
		
		
		
	}

}
