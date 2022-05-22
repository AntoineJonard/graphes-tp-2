import graphe.Graphe;
import cplex.*;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Graphe g = new Graphe("reseau_10_10_1.txt");
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
		
		System.out.println("FIN");
		
		//PlusCourtChemin.solveMe(g);
		
	}

}
