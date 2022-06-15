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

    /**
     *
     * @param heuristique l'heuristique choisie pour evaluer l'interet d'un sommet
     */
    public A_Star(Heuristique heuristique) {
		super();
		this.heuristique = heuristique;
	}

    /**
     * Applique A* sur un graphe avec l'heuristique chosie
     * @param g le graphe sur lequel on veut trouver le plus court chemin entre deux sommets
     * @return le plus court chemin sous forme de liste des sommets qui le constitue
     */
	public List<Sommet> resolve(Graphe g){

        // Informations de distances et prédecesseurs pour chque sommet du graphe
        Map<Sommet,AStarSommetInfo> mappedSommets = new HashMap<>();

        long startTime = System.currentTimeMillis();

        // Initialisation des informations
        for (Sommet s : g.allSommets()){
            AStarSommetInfo sommetInfo = new AStarSommetInfo();

            if (s == g.getStart()){
                sommetInfo.minDist = 0;
                sommetInfo.hDist = heuristique.heuristique.apply(s,g.getGoal());
            }

            mappedSommets.put(s, sommetInfo);
        }

        // Liste des sommets triés par leurs heuristique (distance minimum potentielle)
        PriorityQueue<Sommet> discoveredNodes = new PriorityQueue<>(
                Comparator.comparingDouble(sommet -> mappedSommets.get(sommet).hDist)
        );

        // Ajout du premier sommet
        discoveredNodes.add(g.getStart());

        // Tant qu'il y a des sommets a explorer ...
        while (!discoveredNodes.isEmpty()){
            Sommet current = discoveredNodes.poll();

            if (listener != null){
                listener.onNewCurrent(current);
            }

            // ... Et tant qu'on a pas trouvé la solution
            if (current == g.getGoal()){

                long endTime = System.currentTimeMillis();

                System.out.println("Resolution time (ms) : " + (endTime - startTime));

                // Reconstitution du plus court chemin via la liste des predecesseurs
                List<Sommet> solution = getPath(mappedSommets, current);
                if (listener != null){
                    listener.onResolutionFound(solution);
                }
                writeSolution(solution, g);
                return solution;
            }

            // On itére sur les sommets adjacents
            for (Sommet adjacent : current.getAccessibleAdjacents()){
                // Calcul de la nouvelle valeur de plus court chemin jusqu'au sommot adjacent
                double newMinDist = mappedSommets.get(current).minDist + current.getFlightDistTo(adjacent);
                // Si la valeur est plus intéressante que la précédente, on met à jour les informations
                if (Double.compare(newMinDist, mappedSommets.get(adjacent).minDist) < 0 ){
                    mappedSommets.get(adjacent).from = current;
                    mappedSommets.get(adjacent).minDist = newMinDist;
                    mappedSommets.get(adjacent).hDist = newMinDist + heuristique.heuristique.apply(adjacent,g.getGoal());

                    // Et on ajoute ce sommet a la liste des sommets qu'il faudra explorer
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

    /**
     * Reconstitue le plus court chemin
     * @param infos informations des sommets apres execution de A*
     * @param current Sommet d'arrivée
     * @return Le plus court chemin sous forme de liste des sommets qui le compose
     */
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

    /**
     * Créer une chaine de caractére permettant de visualiser le resultat de A*
     * @param solution la liste des sommets constituant le plus court chemin
     * @return Le string représentant la solution
     */
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

