package Main;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.Task;


public class Modelisation {
	
	public static void main(String[] args) {
		int NbBat = 5;
		
		Model model = new Model("Problème combinatoire");
		
		for (int i =0; i<NbBat;i++){
			// Une tâche pour chaque bateau
			IntVar deb = model.intVar("SBateau_"+i,0,1500);
			IntVar dur = model.intVar("PBateau_"+i,0,1500);
			IntVar fin = model.intVar("FBateau_"+i,0,1500);
			Task test = model.taskVar(deb, dur, fin);
			}
		}
}
