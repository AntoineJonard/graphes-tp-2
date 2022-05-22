package graphe;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Graphe {
	
	private List<List<Sommet>> sommets;
	private Sommet start;
	private Sommet goal;

	public Graphe(String fileName) {
		super();
		sommets = new ArrayList<>();
		
        File file = new File("src/data/"+fileName);

        BufferedReader bufferedReader;
		try {
			bufferedReader = new BufferedReader(new FileReader(file));
			
			String line = bufferedReader.readLine();
			String[] infoGraphe = line.split("\\s+");
			
			int nbLigne = Integer.parseInt(infoGraphe[0]);
			int nbCol = Integer.parseInt(infoGraphe[1]);
			
			int cptLigne = 0;
			
	        while ((line = bufferedReader.readLine()) != null && cptLigne < nbLigne){
	            String[] infoSommets = line.split("\\s+");
	            sommets.add(new ArrayList<>());
	            for (int cptCol = 0 ; cptCol < nbCol ; cptCol++) {
	            	Type type = Type.getTypeFrom(Integer.parseInt(infoSommets[cptCol]));
					Sommet toAdd = new Sommet(cptCol, cptLigne, type);
					sommets.get(cptLigne).add(toAdd);
	            	if (type == Type.START)
	            		start = toAdd;
	            	if (type == Type.END)
	            		goal = toAdd;
	            }
	            
	            cptLigne ++;
	        }
	        
	        bufferedReader.close();
	        
	        if (goal != null && start != null && nbCol < 500 && nbLigne <500)
	        	System.out.println("Réussite de la création du graphe");
	        else
	        	throw new InvalidGraphException();
			
		} catch (FileNotFoundException e) {
			System.out.println("Error while openning file, verify it location");
		} catch (IOException e) {
			System.out.println("Error while reading file, verify it validity");
		} catch (InvalidGraphException e) {
			System.out.println("Invalid graph, too many cols, lines, or no end and start");
		}

		for (List<Sommet> line : sommets){
			for (Sommet sommet : line){
				sommet.setAdjacents(getAdjacentsTo(sommet));
			}
		}
        
	}

	public List<List<Sommet>> getSommets() {
		return sommets;
	}

	public Sommet getSommet(int x, int y) {
		try {
			return sommets.get(x).get(y);
		}catch (IndexOutOfBoundsException e){
			return null;
		}
	}
	
	private List<Sommet> getAdjacentsTo(Sommet s){
		
		List<Sommet> adjacent = new ArrayList<>();
		
		for (int x = -1 ; x < 2 ; x++) {
			for (int y = -1 ; y < 2 ; y++) {
				Sommet adj = getSommet(s.x + x,s.y + y);
				if (adj != null)
					adjacent.add(adj);
			}	
		}
		
		adjacent.remove(s);
		
		return adjacent;
	}
	
	public int nbLigne() {
		return this.sommets.size();
	}
	
	public int nbColonne() {
		return this.sommets.get(0).size();
	}

	public List<Sommet> allSommets(){
		List<Sommet> allSommets = new ArrayList<>();

		for (List<Sommet> line : sommets){
			for (Sommet sommet : line){
				allSommets.add(sommet);
			}
		}

		return allSommets;
	}
	
	public Sommet getStart() {
		return start;
	}
	
	public Sommet getGoal() {
		return goal;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for (List<Sommet> line : sommets) {
			for (Sommet s : line) {
				sb.append(s).append(" ");
			}
			sb.append("\n");
		}
		
		return sb.toString();
	}
	
	
}
