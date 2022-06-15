package cplex;

import graphe.Type;
import ilog.concert.*;
import ilog.cplex.*;

public class VoyageurCommerce {
	
	public static void solveMe(int n, int t, double p) {
		//n le nombre de point
		//t la taille du graph (entre 0 et t)
		//p la probabilité que 2 points est une connexion entre elles (entre 0 et 1)
		
		//random data
		double[] xPos = new double[n];
		double[] yPos = new double[n];
		for (int i=0; i<n; i++) {
			xPos[i] = Math.random()*t;
			yPos[i] = Math.random()*t;
		}
		
		double[][] c = new double[n][n];
		for (int i=0; i<n; i++) {
			for (int j=0; j<n; j++) {
				c[i][j] = Math.sqrt(Math.pow(xPos[i]-xPos[j], 2) + Math.pow(yPos[i]-yPos[j], 2) );
			}
		}
		
		System.out.println("Pour le graph du voyageur de commerce étudié : ");
		//AFFICHAGE DU GRAPH
		for (int i=0; i<n; i++) {
			for (int j=0; j<n; j++) {
				System.out.print(" (" + xPos[i] + "," + yPos[j] + ") ");
			}
			System.out.print("\n");
		}
		
		System.out.println("Pour le graph de liaison de commerce étudié : ");
		//d pour : l'ensemble des arêtes, entre le point i (x,y que l'on calcul) et j (x,y que l'on calcul)
		double[][] d = new double[n][n];
		for (int i=0; i<n; i++) {
			for (int j=0; j<n; j++) {
				double rand = Math.random()*100;
				if(rand <= p*100) {
					d[i][j]=1;
				}
				System.out.print("\t"+ d[i][j]);
			}
			System.out.print("\n");
		}
		
		
		//modele
		try {
			IloCplex cplex = new IloCplex();
			
			//variable
			IloNumVar[][] x = new IloNumVar[n][];
			for (int i=0; i<n; i++) {
				x[i] = cplex.boolVarArray(n);
			}
			
			IloNumVar[] u = cplex.numVarArray(n, 0, Double.MAX_VALUE);
			
			//Objectif
			IloLinearNumExpr obj = cplex.linearNumExpr();
			for (int i=0; i<n; i++) {
				for (int j=0; j<n; j++) {
					if(i!=j) {
						obj.addTerm(c[i][j], x[i][j]);
					}
				}
			}
			cplex.addMinimize(obj);
			
			//contraintes
			for (int i=0; i<n; i++) {
				IloLinearNumExpr expr = cplex.linearNumExpr();
				for (int j=0; j<n; j++) {
					if(i!=j) {
						expr.addTerm(d[i][j], x[i][j]);
					}
				}
				cplex.addEq(expr, 1.0);
			}
			
			for (int j=0; j<n; j++) {
				IloLinearNumExpr expr = cplex.linearNumExpr();
				for (int i=0; i<n; i++) {
					if(i!=j) {
						expr.addTerm(d[i][j], x[i][j]);
					}
				}
				cplex.addEq(expr, 1.0);
			}
			
			//Permet de ne pas prendre en compte multiple boucles pour le problème. Ainsi obtenir qu'une seul boucle
			for (int i=1; i<n; i++) {
				for (int j=1; j<n; j++) {
					if(i!=j) {
						IloLinearNumExpr expr = cplex.linearNumExpr();
						expr.addTerm(1.0, u[i]);
						expr.addTerm(-1.0, u[j]);
						expr.addTerm(n-1, x[i][j]);
						cplex.addLe(expr, n-2);
					}
				}
			}
			
			//Enlève l'affichage de CPLEX
			cplex.setParam(IloCplex.Param.Simplex.Display, 0);
			
			//Résolution
        	if (cplex.solve()) {
        		System.out.println("\n");
        		System.out.println("Status de la Solution = "+ cplex.getStatus());
        		System.out.println("Distance Minimal = " + cplex.getObjValue() + " ");
        		System.out.println();
        		
        		////AFFICHAGE DU RESULTAT DE LA VARIABLE X TROUVER SOUS CPLEX CORRESPONDANT SI ON SE DEPLACE DE LA VILLE I VERS J
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
        		int point = 0 ;
        		do {
        			System.out.print("" + point  + " -> ");
    				for(int i=0; i<x[point].length; i++) {
    					if(point != i ) {
    						if( cplex.getValue(x[point][i]) == 1.0 ) {
        						point = i;
        						break;
        					}
    					}
    				}
    				System.out.print("" + point + "\n");
    				
        			if(point == 0) {
        				arriver ++;
        			}
        			
        		}while(arriver == 0);
        		
        		
        	}
			
			
			cplex.end();
			
			
		}catch(IloException e) {
			e.printStackTrace();
		}
		
	}
	
	
}
