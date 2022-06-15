package aStar;

import java.util.List;

import graphe.Graphe;
import graphe.Sommet;

public class TestAStar {

	public static void main(String[] args) {

		Graphe g = new Graphe("reseau_50_50_1.txt");
		System.out.println(g);

		A_Star aStar = new A_Star(Heuristique.EUCLIDEAN);

		List<Sommet> solution = aStar.resolve(g);
		if (solution != null) {
			System.out.println(A_Star.solutionToString(solution));
			
		}else {
			System.out.println("No path from start to end");
		}

	}

}
