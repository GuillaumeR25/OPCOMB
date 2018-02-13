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
		
		// Fonction mettant à jour le quai lors de l'arrivée d'un navire.
		public static void arrive(ArrayList<Integer> quai, Navire n, int pos){
			int encombrement = n.encombrement();
			int id = n.getId();
			for(int i=0; i<encombrement; i++){
				quai.set(i+encombrement, id);
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
					System.out.println(pos);
				}else{
					pos++;
					System.out.println(pos);
					fpos=pos;
					t++;
					System.out.println(t);
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
			if (h1 > h2){
				return -1;
			}else{
			double res = Math.floor((h2-h1)/100) + (h2-h1)%100/60;
			return res;
			}
		}
		
		// Fonction qui met à jour les taches en cours.
		
		public static void update_tache (ArrayList<Tache> taches, ArrayList<Tache> rep, ArrayList<Navire> restant, ArrayList<Navire> attente, 
				ArrayList<Integer> quai, double temps, int capaGrue){
			for(Tache t : taches){
				t.setChargement(Math.max(t.getChargement()-Math.floor(difference(t.getLastChange(), temps)*capaGrue*t.getNbG()), 0));
				t.setLastChange(temps);
				if(t.getChargement() == 0){
					t.setFin(t.getLastChange());
					taches.remove((Object)t);
					next_iter_Depart(restant, taches, attente, quai, t.getFin());
					rep.add(t);
				}
			}
		}
		
		public static void next_iter_Arrive 
		(ArrayList<Navire> navires, ArrayList<Grue> grues, ArrayList<Tache> taches, ArrayList<Tache> rep, ArrayList<Navire> restant, 
				ArrayList<Navire> attente, ArrayList<Integer> quai, Navire n, int capaGrue){
			int newpos = placement(quai, n.encombrement());
			if(newpos >= quai.size()){
				attente.add(n);
			}else{
				arrive(quai, n, newpos);
				Tache Tcurrent = new Tache(n, n.getArrive(), n.getArrive(), newpos, 0, n.getChargement(), n.getArrive()); 
				taches.add(Tcurrent);
				update_tache(taches, rep, restant, attente, quai, n.getArrive(), capaGrue);
			}
			
		}
		
		// Fonction mettant à jour lors d'un deprt de bateau la liste des bateau restant, la liste des bateau en attente, le quai
		public static void next_iter_Depart(ArrayList<Navire> restant,ArrayList<Tache> taches, ArrayList<Tache> rep, ArrayList<Navire> attente, ArrayList<Integer> quai,double fin){
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
					next_iter_Arrive(navires, grues, taches, rep, restant, attente, quai, nav, capGrue);
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
			
			// Liste des taches en cours
			ArrayList<Tache> taches = new ArrayList<Tache>();
			
			
			int NbBat = navires.size();
			int NbGrue = 5;
			int capGrue = 12;
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
			/*Tache t = new Tache(navires.get(0),0,4,0,NbGrue, navires.get(0).getChargement(), 0);
			rep.add(t);*/
			//arrivee(quaiF,navires.get(3),8);
			//System.out.println(quaiF);
			//depart(quaiF,navires.get(0),0);
			//System.out.println(quaiF);
			
			
			//Test fonction next_iter_Depart
			/*restant.remove(0);
			System.out.println("Fonction next_iter_Depart");
			next_iter_Depart(restant, rep, attente, quaiF, 4);
			System.out.println("Résultats");
			System.out.println("Bateau restant "+restant);
			System.out.println("Bateau en attentes "+attente);
			System.out.println("Quai : "+quaiF);
			*/
			
			next_iter_Arrive(navires, grues, taches, rep, restant, attente, quaiF, navires.get(0), capGrue);
			next_iter_Arrive(navires, grues, taches, rep, restant, attente, quaiF, navires.get(1), capGrue);
			
			System.out.println("Restant : "+restant.toString());
			System.out.println("Attente : "+attente.toString());
			System.out.println("Quai : "+quai.toString());
			System.out.println("Taches en cours : "+taches);
			
			
			
		}
	}
