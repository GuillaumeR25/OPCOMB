package Main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import jxl.CellView;
import jxl.Workbook;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;


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
	
	// Fonction qui rend la diff�rence en heure entre deux repr�sentation d'heure en "format double"
	// h1 est ant�rieure � h2
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
		
		
		//Donn�es du probl�me
		
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
		
		// Capacit� de d�chargement des grues
		int capaGrue = 20;
		
		capaGrue = (int)((double)capaGrue*(16.0/17.0));
		
		// Nombre de grutiers
		int NbGrutiers = 50;
		
		// Longueur du quai;
		int  quaiL = 15;
		
		// Variables du probl�me
		
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
		
		// Liste des taches termin�es
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
			compt += 100;
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
		
		//Creation de la feuille de planning excel excel
		
		WritableWorkbook workbook = null;
		try {
			/* On créé un nouveau worbook et on l'ouvre en écriture */
			workbook = Workbook.createWorkbook(new File("/Users/clovismonmousseau/git/OPCOMB/data/Sortie.xls"));
			/* On créé une nouvelle feuille (test en position 0) et on l'ouvre en écriture */
			WritableSheet sheet = workbook.createSheet("Planning Navire", 0); 
			/* Creation d'un champ au format texte */
			int posLigne =2;
			for(int j =0; j<24;j++){
				Label label0 = new Label(j+5, posLigne-1, "Heure " + j);
				sheet.addCell(label0);				
			}
			for(Tache t : solution){
				for(int j =0; j<24;j++){
					if(t.getDebut()/100>(double)j){
						Label label = new Label(j+5, posLigne, " ");
						sheet.addCell(label);
						Label label1 = new Label(2, posLigne, "Navire "+t.getNav().getId());
						sheet.addCell(label1);
						Label label2 = new Label(3, posLigne," Quai : " + t.getPosition() + " - " +  (t.getPosition()+t.getNav().getTaille()));
						sheet.addCell(label2);
						Label label3 = new Label(4, posLigne,"Grues : " + t.getGrue());
						sheet.addCell(label3);
						CellView cv2 = sheet.getColumnView(5);
						cv2.setAutosize(true);
						sheet.setColumnView(5, cv2);
						CellView cv1 = sheet.getColumnView(4);
						cv1.setAutosize(true);
						sheet.setColumnView(4, cv1);
						CellView cv = sheet.getColumnView(3);
						cv.setAutosize(true);
						sheet.setColumnView(3, cv);
						
						WritableCell c = sheet.getWritableCell(j+5,posLigne);
					    WritableCellFormat newFormat = new WritableCellFormat(c.getCellFormat());
					    newFormat.setBackground(Colour.GRAY_25);
					    c.setCellFormat(newFormat);
					}
					if(t.getDebut()/100-1<=(double)j &&t.getFin()>=(double)j){
						Label label = new Label(j+5, posLigne, " ");
						sheet.addCell(label);
						Label label1 = new Label(2, posLigne, "Navire "+t.getNav().getId());
						sheet.addCell(label1);
						Label label2 = new Label(3, posLigne," Quai : " + t.getPosition() + " - " +  (t.getPosition()+t.getNav().getTaille()));
						sheet.addCell(label2);
						Label label3 = new Label(4, posLigne,"Grues : "+t.getGrue());
						sheet.addCell(label3);
						CellView cv2 = sheet.getColumnView(5);
						cv2.setAutosize(true);
						sheet.setColumnView(5, cv2);
						CellView cv1 = sheet.getColumnView(4);
						cv1.setAutosize(true);
						sheet.setColumnView(4, cv1);
						CellView cv = sheet.getColumnView(3);
						cv.setAutosize(true);
						sheet.setColumnView(3, cv);
						
						WritableCell c = sheet.getWritableCell(j+5,posLigne);
					    WritableCellFormat newFormat = new WritableCellFormat(c.getCellFormat());
					    newFormat.setBackground(Colour.YELLOW);
					    c.setCellFormat(newFormat);
					}
					if(t.getFin()/100<(double)j){
						Label label = new Label(j+5, posLigne, " ");
						sheet.addCell(label);
						Label label1 = new Label(2, posLigne, "Navire "+t.getNav().getId());
						sheet.addCell(label1);
						Label label2 = new Label(3, posLigne," Quai : " + t.getPosition() + " - " +  (t.getPosition()+t.getNav().getTaille()));
						sheet.addCell(label2);
						Label label3 = new Label(4, posLigne,"Grues : "+t.getGrue());
						sheet.addCell(label3);
						CellView cv2 = sheet.getColumnView(5);
						cv2.setAutosize(true);
						sheet.setColumnView(5, cv2);
						CellView cv1 = sheet.getColumnView(4);
						cv1.setAutosize(true);
						sheet.setColumnView(4, cv1);
						CellView cv = sheet.getColumnView(3);
						cv.setAutosize(true);
						sheet.setColumnView(3, cv);
						
						WritableCell c = sheet.getWritableCell(j+5,posLigne);
					    WritableCellFormat newFormat = new WritableCellFormat(c.getCellFormat());
					    newFormat.setBackground(Colour.GREEN);
					    c.setCellFormat(newFormat);
					}
				}
				posLigne++;
			}
			/* On ecrit le classeur */
			workbook.write(); 
		} 
		catch (IOException e) {
			e.printStackTrace();
		} 
		catch (RowsExceededException e) {
			e.printStackTrace();
		}
		catch (WriteException e) {
			e.printStackTrace();
		}
		finally {
			if(workbook!=null){
				/* On ferme le worbook pour libérer la mémoire */
				/**/
				try {
					workbook.close();
				} 
				catch (WriteException e) {
					e.printStackTrace();
				} 
				catch (IOException e) {
					e.printStackTrace();
				}
				//**/
			}
		}
		
	}

}
