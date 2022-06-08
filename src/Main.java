import graphe.Graphe;
import cplex.*;

public class Main {

	public static void main(String[] args) {
		Graphe g = new Graphe("reseau_5_5_1.txt");
		System.out.println(g);
		

		Exemple1.solveMe();
		
		System.out.println("-----------------------------------------------");
		System.out.println("===============================================");
		System.out.println("-----------------------------------------------");
		
		Exemple2.solveMe();
		
		System.out.println("-----------------------------------------------");
		System.out.println("===============================================");
		System.out.println("-----------------------------------------------");
		
		VoyageurCommerce.solveMe(10);
		
		System.out.println("-----------------------------------------------");
		System.out.println("===============================================");
		System.out.println("-----------------------------------------------");
		
		PlusCourtChemin.solveMe(g);
		
		System.out.println("FIN");
		
		
	}

}
