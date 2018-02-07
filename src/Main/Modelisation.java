package Main;

import java.util.ArrayList;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.Task;


public class Modelisation {
	
	public static void main(String[] args) {
		ArrayList<Navire> navires = new ArrayList<>();
		ArrayList<Grue> grues = new ArrayList();
		int NbBat = navires.size();
		int NbGrue = grues.size();
		int Quai = 30;
		int NbMin = 1440;
		
		Model model = new Model("Problème combinatoire");
		
		for (int i =0; i<NbBat;i++){
			// Une tâche de durée pour chaque bateau
			IntVar debT = model.intVar("SBateau_"+i,0,NbMin);
			IntVar durT = model.intVar("PBateau_"+i,0,NbMin);
			IntVar finT = model.intVar("FBateau_"+i,0,NbMin);
			Task tacheT = model.taskVar(debT, durT, finT);
			
			// Une tâche de position pour chaque bateau
			IntVar debP = model.intVar("SBateau_"+i,0,Quai);
			int durP = navires.get(i).getTaille()+2;
			Task tacheP = model.taskVar(debP, durP);
			}
		
		for (int i =0; i<NbGrue;i++){
			
		}
		}
}
