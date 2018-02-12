package Main;

import java.util.ArrayList;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.Task;


public class Modelisation {
	
	
	//Fonction permettant de savoir l'identifiant des grues sur chaque bateau a chaque temps t
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
    
	//Fonction permettant de savoir l'identifiant des grues sur chaque bateau
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
	
    //Solver
	public static void main(String[] args) {
		ArrayList<Navire> navires = Donnees1.dNav();
		ArrayList<Grue> grues = Donnees1.dGrue();
		int NbBat = navires.size();
		int NbGrue = grues.size();
		int Quai = 20;
		int NbMin = 1440;
		
		Model model = new Model("Problème combinatoire");
		
		IntVar capacity = model.intVar(Quai);
		
		Task[] tasks = new Task[NbBat];
		
		IntVar[] height = new IntVar[NbBat];
		
		for (int i =0; i<NbBat;i++){
			// Une tâche de durée pour chaque bateau
			IntVar debT = model.intVar("SBateau_"+i,0,NbMin);
			//IntVar durT = model.intVar("PBateau_"+i,0,NbMin);
			IntVar durT = model.intVar(30);
			IntVar finT = model.intVar("FBateau_"+i,0,NbMin);
			Task tacheT = model.taskVar(debT, durT, finT);
			IntVar taille = model.intVar(navires.get(i).getTaille()+2);
			tasks[i]=tacheT;
			height[i]=taille;
			
			}
		
		//Contrainte : les bateaux ne peuvent pas dépasser le quai
		model.cumulative(tasks, height, capacity).post();
		
	/*	//  Tableaux retraçant la position des grues au cours du temps
        IntVar[][] PositionsGrues = new IntVar[NbGrue][NbMin];
        for (int i =0; i<NbGrue-1;i++){
                PositionsGrues[i] = model.intVarArray("Grue " + i,NbMin,0,NbGrue);
        }
        
        //Contrainte sur l'ordre des grues
        for (int i =0; i<NbGrue-1;i++){
                for(int j =0; j< NbMin;j++){
                        model.arithm(PositionsGrues[i][j],"<=",PositionsGrues[i+1][j]).post();
                }
        }*/	
		Solution solution = model.getSolver().findSolution();
		if(solution != null){
		    System.out.println(solution.toString());
		}else{
			System.out.println("Hello");
		}
	}
}
