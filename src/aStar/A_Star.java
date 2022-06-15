package aStar;

import java.io.File;
import java.io.PrintStream;
import java.util.*;

import graphe.Graphe;
import graphe.Sommet;
import graphe.Type;

public class A_Star {
	
	Heuristique heuristique;

    A_StarListener listener;

    public A_Star(Heuristique heuristique) {
		super();
		this.heuristique = heuristique;
	}

	public List<Sommet> resolve(Graphe g){

        Map<Sommet,AStarSommetInfo> mappedSommets = new HashMap<>();

        long startTime = System.currentTimeMillis();

        for (Sommet s : g.allSommets()){
            AStarSommetInfo sommetInfo = new AStarSommetInfo();

            if (s == g.getStart()){
                sommetInfo.minDist = 0;
                sommetInfo.hDist = heuristique.heuristique.apply(s,g.getGoal());
            }

            mappedSommets.put(s, sommetInfo);
        }

        PriorityQueue<Sommet> discoveredNodes = new PriorityQueue<>(
                Comparator.comparingDouble(sommet -> mappedSommets.get(sommet).hDist)
        );

        discoveredNodes.add(g.getStart());

        while (!discoveredNodes.isEmpty()){
            Sommet current = discoveredNodes.poll();

            if (listener != null){
                listener.onNewCurrent(current);
            }

            if (current == g.getGoal()){

                long endTime = System.currentTimeMillis();

                System.out.println("Resolution time (ms) : " + (endTime - startTime));

                List<Sommet> solution = getPath(mappedSommets, current);
                if (listener != null){
                    listener.onResolutionFound(solution);
                }
                writeSolution(solution, g);
                return solution;
            }

            for (Sommet adjacent : current.getAccessibleAdjacents()){
                double newMinDist = mappedSommets.get(current).minDist + current.getFlightDistTo(adjacent);
                if (Double.compare(newMinDist, mappedSommets.get(adjacent).minDist) < 0 ){
                    mappedSommets.get(adjacent).from = current;
                    mappedSommets.get(adjacent).minDist = newMinDist;
                    mappedSommets.get(adjacent).hDist = newMinDist + heuristique.heuristique.apply(adjacent,g.getGoal());

                    if (!discoveredNodes.contains(adjacent)){
                        discoveredNodes.add(adjacent);
                        if (listener != null){
                            listener.onNewDiscovered(adjacent);
                        }
                    }
                }
            }
        }
        if (listener != null){

            long endTime = System.currentTimeMillis();

            System.out.println("Resolution time (ms) : " + (endTime - startTime));

            writeSolution(null, g);

            listener.onResolutionFound(null);
        }
        return null;
    }

    private List<Sommet> getPath(Map<Sommet,AStarSommetInfo> infos, Sommet current){
        List<Sommet> path = new ArrayList<>();
        path.add(current);
        while (current != null){
            current = infos.get(current).from;
            if (current != null)
            	path.add(current);
        }
        return path;
    }
    public void setListener(A_StarListener listener) {
        this.listener = listener;
    }

    public static String solutionToString(List<Sommet> solution){
        if (solution.get(0).getType() == Type.END){
            Collections.reverse(solution);
        }
        StringBuilder sb = new StringBuilder();
        solution.forEach(s -> {
            sb.append(s);
            if (solution.get(solution.size()-1) != s) {
                sb.append(" -> ");
            }
        });
        return sb.toString();
    }

    private void writeSolution(List<Sommet> solution, Graphe graphe){
        File solutionFile = new File("src/data/sol_"+graphe.getName());
        String stringSolution = solution != null ? solutionToString(solution) : "No path from "+graphe.getStart()+ " to "+graphe.getGoal();
        try (PrintStream out = new PrintStream(solutionFile)) {
            out.print(stringSolution);
        }catch (Exception e){
            System.out.println("Can't write to file");
        }
    }

    static class AStarSommetInfo implements Comparable<AStarSommetInfo>{
        Sommet from;
        double minDist;
        double hDist;

        public AStarSommetInfo() {
            this.from = null;
            minDist = Double.MAX_VALUE;
            hDist = Double.MAX_VALUE;
        }

        @Override
        public int compareTo(AStarSommetInfo o) {
            return Double.compare(this.hDist,o.hDist);
        }

    }

}

