package aStarIsBorn;

import java.util.function.BiFunction;

import graphe.Sommet;

public enum Heuristique {
	MANHATTAN((s1, s2) -> (double)Math.abs(s2.getX()-s1.getX())+Math.abs(s2.getY()-s1.getY())),
	EUCLIDEAN((s1, s2) -> (double)Math.sqrt(Math.pow(s1.getX() - s2.getX(), 2) + Math.pow(s1.getY() - s2.getY(), 2)));
	
	BiFunction<Sommet,Sommet,Double> heuristique;

	private Heuristique(BiFunction<Sommet,Sommet,Double> heuristique) {
		this.heuristique = heuristique;
	}

}
