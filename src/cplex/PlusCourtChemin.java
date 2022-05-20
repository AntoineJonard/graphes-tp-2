package cplex;

import graphe.*;

import ilog.concert.*;
import ilog.cplex.*;

import java.util.ArrayList;
import java.util.List;


public class PlusCourtChemin {
	public static void solveMe(Graphe g) {
		// g est le graphe
		
		
		//Constante 
		//d pour le distance entre le point i (x,y que l'on calcul) et j (x,y que l'on calcul)
		double[][] d = new double[g.nbLigne()*g.nbColonne()][g.nbLigne()*g.nbColonne()];
		for (int i=0; i<g.nbLigne()*g.nbColonne(); i++) {
			for (int j=0; j<g.nbColonne()*g.nbColonne(); j++) {
				int xPosI = i/g.nbColonne();
				int yPosI = i%g.nbColonne();
				int xPosJ = j/g.nbColonne();
				int yPosJ = j%g.nbColonne();
				
				//System.out.print("("+xPosI +"," + yPosI+") // ("+xPosJ +"," + yPosJ+")");
				
				d[i][j] = g.getSommet(xPosI,yPosI).getFlightDistTo(g.getSommet(xPosJ,yPosJ));
				
				//System.out.println("\t Distance : "+d[i][j]);
			}
		}
		

		
		try {
			IloCplex cplex = new IloCplex();
			
			// variables
			IloNumVar[][] x = new IloNumVar[g.nbLigne()][];
			for (int i=0; i<g.nbLigne(); i++) {
				x[i] = cplex.boolVarArray(g.nbColonne());
			}
			IloNumVar[] u = cplex.numVarArray(g.nbLigne(), 0, Double.MAX_VALUE);
			
			//Objective
			IloLinearNumExpr obj = cplex.linearNumExpr();
			for (int i=0; i<g.nbLigne(); i++) {
				for (int j=0; j<g.nbColonne(); j++) {
					if(j!=i) {
						obj.addTerm(d[i][j], x[i][j]);
					}
				}
			}
			cplex.minimize(obj);
			
			//Contrainte
			for (int j=0; j<g.nbColonne(); j++) {
				IloLinearNumExpr expr = cplex.linearNumExpr();
				for (int i=0; i<g.nbLigne(); i++) {
					if(i!=j) {
						expr.addTerm(1.0, x[i][j]);
					}
				}
				cplex.addEq(expr, 1.0);
			}
			
			for (int i=0; i<g.nbLigne(); i++) {
				IloLinearNumExpr expr = cplex.linearNumExpr();
				for (int j=0; j<g.nbColonne(); j++) {
					if(i!=j) {
						expr.addTerm(1.0, x[i][j]);
					}
				}
				cplex.addEq(expr, 1.0);
			}
			
			
			for (int i=1; i<g.nbLigne(); i++) {
				for (int j=1; j<g.nbColonne(); j++) {
					if(i!=j) {
						IloLinearNumExpr expr = cplex.linearNumExpr();
						expr.addTerm(1.0, u[i]);
						expr.addTerm(-1.0, u[j]);
						expr.addTerm(g.nbLigne()-1, x[i][j]);
						cplex.addLe(expr, g.nbLigne()-2);
					}
				}
			}
			
			
			// solve
			if (cplex.solve()) {
				System.out.println("\nobj = "+cplex.getObjValue());
				/*for (int i=0; i<n; i++) {
					System.out.println("x"+i+"\t= "+cplex.getValue(x[i][0]));
					
				}
				
				/*System.out.println("x   = "+cplex.getValue(x));
				System.out.println("y   = "+cplex.getValue(y));
				/*for (int i=0;i<constraints.size();i++) {
					System.out.println("dual constraint "+(i+1)+"  = "+cplex.getDual(constraints.get(i)));
					System.out.println("slack constraint "+(i+1)+" = "+cplex.getSlack(constraints.get(i)));
				}*/
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

