package Main;

import java.util.ArrayList;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
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
		Solver solver = model.getSolver();
		
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
			
		
			//  Tableaux retraçant la position des grues au cours du temps
		IntVar[][] PositionsGrues = new IntVar[NbGrue][NbMin];
		for (int i =0; i<NbGrue-1;i++){
			PositionsGrues[i] = model.intVarArray("Grue " + i,NbMin,0,NbGrue);
		}
		
		
		
		for (int i =0; i<NbGrue-1;i++){
			for(int j =0; j< NbMin;j++){
				model.arithm(PositionsGrues[i][j],"<=",PositionsGrues[i+1][j]).post();
			}
		}
		
		solver.showSolutions(); 
		solver.findSolution();
		solver.printStatistics();
		
		for (int i =0; i<NbGrue-1;i++){
			for(int j =0; j< NbMin;j++){
				System.out.print( PositionsGrues[i][j] + " ");
			}
			System.out.println();
		}
		
		
		
		}
}
