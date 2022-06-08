package aStarIsBorn;

import java.awt.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import graphe.Graphe;
import graphe.Sommet;

public class TestAStar {

	public static void main(String[] args) {

		Graphe g = new Graphe("reseau_5_10_1.txt");
		System.out.println(g);
		A_Star aStar = new A_Star(Heuristique.EUCLIDEAN);
		List<Sommet> solution = aStar.resolve(g);
		if (solution != null) {
			Collections.reverse(solution);
			StringBuilder sb = new StringBuilder();
			solution.forEach(s -> {
				sb.append(s);
				if (solution.get(solution.size()-1) != s) {
					sb.append(" -> ");
				}
			});
			System.out.println(sb);
			
		}else {
			System.out.println("No path from start to end");
		}

	}

}
