import graphe.Graphe;
import cplex.*;

public class Main {

	public static void main(String[] args) {
		Graphe g = new Graphe("reseau_3_3_1.txt");
		//System.out.println(g);
		
		PlusCourtChemin.solveMe(g);
		
		System.out.println("-----------------------------------------------");
		System.out.println("===============================================");
		System.out.println("-----------------------------------------------");
		
		//le premier param�tre : le nombre de point //le deuxi�me : la taille du carr� d'�tude  //le troisi�me : la probabilit� qu'une liaison entre 2 points ce face
		VoyageurCommerce.solveMe(10,100,0.5);
				
				
		System.out.println("FIN");
		
		
	}

}
