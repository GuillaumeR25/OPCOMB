package Main;

import java.util.ArrayList;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.Task;


public class Modelisation {
	
	// les tableaux: première arrayList: temps, deuxième: position des navires
	
	public static ArrayList<ArrayList<Grue>> GruesBateaux(ArrayList<ArrayList<Integer>> navirest, ArrayList<ArrayList<Integer>> gruest, int temps, int NbBat, ArrayList<Grue> grues){
		ArrayList<ArrayList<Grue>> a = new ArrayList<>();
		for (int i = 0; i<NbBat; i++){
			ArrayList<Grue> b = new ArrayList<Grue>();
			a.add(b);
		}
		
		for (int i = 0; i<navirest.size(); i++){
			ArrayList<Grue> bateau = new ArrayList<Grue>();
			
			for (int j = 0; j<gruest.size(); j++){
				
				if (navirest.get(temps).get(i) == gruest.get(temps).get(j)){
					bateau.add(grues.get(j));
				}
				
			}
			a.add(bateau);
		}
		
		return a;
	}
	
	public static ArrayList<Grue> gruesUtilisées(ArrayList<ArrayList<Grue>> b){
		ArrayList<Grue> a = new ArrayList<Grue>();
		for (int i = 0; i<b.size(); i++){
			for (int j=0; j<b.get(i).size(); i++){
				if (!a.contains(b.get(i).get(j))){
					a.add(b.get(i).get(j));
				}
			}
		}
		return a;
	}
	
	
	
	public static void main(String[] args) {
		ArrayList<Navire> navires = new ArrayList<>();
		ArrayList<Grue> grues = new ArrayList();
		int NbBat = navires.size();
		int NbGrue = grues.size();
		int Quai = 30;
		int NbMin = 1440;
		
		Model model = new Model("ProblÃ¨me combinatoire");
		
		
		
		for (int i =0; i<NbBat;i++){
			// Une tÃ¢che de durÃ©e pour chaque bateau
			IntVar debT = model.intVar("SBateau_"+i,0,NbMin);
			IntVar durT = model.intVar("PBateau_"+i,0,NbMin);
			IntVar finT = model.intVar("FBateau_"+i,0,NbMin);
			Task tacheT = model.taskVar(debT, durT, finT);
			
			// Une tÃ¢che de position pour chaque bateau
			IntVar debP = model.intVar("SBateau_"+i,0,Quai);
			int durP = navires.get(i).getTaille()+2;
			//Task tacheP = model.taskVar(debP, durP);
			}
		
		for (int i =0; i<NbGrue;i++){
			
		}
		}
}
