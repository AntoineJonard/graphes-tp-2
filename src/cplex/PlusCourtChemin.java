package cplex;

import graphe.*;

import ilog.concert.*;
import ilog.cplex.*;

import java.util.ArrayList;
import java.util.List;


public class PlusCourtChemin {
	public static void solveMe(Graphe g) {
		// g est le graphe
		System.out.println(g); //affichage
		
		// définition de "n" le nombre de sommets dans le graph
		int n = g.nbColonne() * g.nbLigne();		
		
		//Constante 
		System.out.println("Pour le graph des couts en i et j : ");
		//c pour : le cout/la distance, entre le point i (x,y que l'on calcul) et j (x,y que l'on calcul)
		double[][] c = new double[n][n];
		for (int i=0; i<n; i++) {
			for (int j=0; j<n; j++) {
				int xPosI = i%g.nbColonne();
				int yPosI = i/g.nbColonne();
				int xPosJ = j%g.nbColonne();
				int yPosJ = j/g.nbColonne();
				
				//System.out.print("("+xPosI +"," + yPosI+") // ("+xPosJ +"," + yPosJ+")");
				
				c[i][j] = g.getSommet(xPosI,yPosI).getFlightDistTo(g.getSommet(xPosJ,yPosJ));
				
				System.out.print("\t"+c[i][j]); //affichage
			}
			System.out.print("\n"); //affichage
		}
		
		System.out.println("Pour le graph des déplacement possible en i et j : ");
		//d pour : l'ensemble des arêtes, entre le point i (x,y que l'on calcul) et j (x,y que l'on calcul)
		double[][] d = new double[n][n];
		for (int i=0; i<n; i++) {
			for (int j=0; j<n; j++) {
				int xPosI = i%g.nbColonne();
				int yPosI = i/g.nbColonne();
				int xPosJ = j%g.nbColonne();
				int yPosJ = j/g.nbColonne();
				
				//System.out.print("("+xPosI +"," + yPosI+") // ("+xPosJ +"," + yPosJ+")");
				
				if((xPosJ<=xPosI+1 && xPosJ>=xPosI-1) && (yPosJ<=yPosI+1 && yPosJ>=yPosI-1)) {
					if( (xPosI == xPosJ && yPosI == yPosJ) ) {
						d[i][j] = 0;
					}else {
						Type typeSommetActuel = g.getSommet( xPosI, yPosI ).getType();
						if(typeSommetActuel.equals(Type.OBSTACLE) ) {
							d[i][j] = 0;
						}else {
							Type typeSommetActuel_J = g.getSommet( xPosJ, yPosJ ).getType();
							if(typeSommetActuel_J.equals(Type.OBSTACLE) ) {
								d[i][j] = 0;
							}else {
								d[i][j] = 1;
							}
						}
					}
				}else {
					d[i][j] = 0;
				}
				System.out.print("\t"+ d[i][j]); //affichage
			}
			System.out.print("\n"); //affichage
		}
		

		
		try {
			IloCplex cplex = new IloCplex();
			
			// variables
			IloNumVar[][] x = new IloNumVar[n][];
			for (int i=0; i<n; i++) {
				x[i] = cplex.boolVarArray(n);
			}
			
			
			//Objective
			IloLinearNumExpr obj = cplex.linearNumExpr();
			for (int i=0; i<n; i++) {
				for (int j=0; j<n; j++) {
					if(j!=i) {
						obj.addTerm(c[i][j], x[i][j]);
					}
				}
			}
			cplex.addMinimize(obj);
			
			//Contraintes
			//System.out.println(g);
			
			for (int i=0; i<n; i++) {
				IloLinearNumExpr expr = cplex.linearNumExpr();
				for (int j=0; j<n; j++) {
					if(i!=j) {
						expr.addTerm(d[i][j], x[i][j]);
					}
				}
				for (int j=0; j<n; j++) {
					if(i!=j) {
						expr.addTerm(-1*d[i][j], x[j][i]);
					}
				}
				//System.out.println("i = " + (int)i/g.nbColonne() + "\nj = " + (int)i%g.nbColonne() + "\n");
				Type typeSommetActuel = g.getSommet( (int)i%g.nbColonne(), (int)i/g.nbColonne() ).getType();
				if(typeSommetActuel.equals(Type.START) ) {
					cplex.addEq(expr, 1);
				}else {
					if(typeSommetActuel.equals(Type.END)) {
						cplex.addEq(expr, -1);
					}else {
						cplex.addEq(expr, 0);
					}
				}
			}
			
			
			//Enlève l'affichage de CPLEX
			cplex.setParam(IloCplex.Param.Simplex.Display, 0);
			
			
			// solve
			if (cplex.solve()) {
				//System.out.println("\nobj = "+cplex.getObjValue());
				System.out.println("\n");
        		System.out.println("Status de la Solution = "+ cplex.getStatus());
        		System.out.println("Distance Minimal = " + cplex.getObjValue() + " ");
        		System.out.println();
        		
        		//AFFICHAGE DU RESULTAT DE LA VARIABLE X TROUVER SOUS CPLEX CORRESPONDANT SI ON SE DEPLACE DE LA VILLE I VERS J
        		for(int i = 0; i<x.length; i++) {
        		    for(int j = 0 ; j < x[i].length; j ++) {
        		        if(i != j) {
        		        	System.out.print(cplex.getValue(x[i][j]) + "\t");
        		        }else {
        		        	System.out.print("---\t");
        		        } 
        		    }
        		    System.out.print("\n");
        		}
        		
        		
        		int arriver = 0;
        		int point = g.getStart().getX() + g.getStart().getY() * g.nbColonne() ;
        		while(arriver == 0) {
        			if(g.getSommet(point % g.nbColonne(), point / g.nbColonne()).getType().equals(Type.END)) {
        				arriver = 1;
        			}else {
        				System.out.print("(" + point % g.nbColonne() + "," + point / g.nbColonne() + ") -> ");
        				for(int i=0; i<x[point].length; i++) {
        					if(point != i ) {
        						if( cplex.getValue(x[point][i]) == 1.0 ) {
            						point = i;
            						break;
            					}
        					}
        				}
        				System.out.print("(" + point % g.nbColonne() + "," + point / g.nbColonne() + ")\n");
        			}
        		}
        				
			}
			else {
				System.out.println("Model not solved");
			}
			
			cplex.end();
			cplex.close();
		}
		catch (IloException exc) {
			exc.printStackTrace();
		}
	}
	
}

