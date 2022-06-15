import graphe.Graphe;
import cplex.*;

public class Main {

	public static void main(String[] args) {
		Graphe g = new Graphe("reseau_10_10_1.txt");
		System.out.println(g);
		
		//le premier paramètre : le nombre de point //le deuxième : la taille du carré d'étude  //le troisième : la probabilité qu'une liaison entre 2 points ce face
		VoyageurCommerce.solveMe(10,100,1);
		
		
		System.out.println("-----------------------------------------------");
		System.out.println("===============================================");
		System.out.println("-----------------------------------------------");
		
		PlusCourtChemin.solveMe(g);
		
		System.out.println("FIN");
		
		
	}

}
