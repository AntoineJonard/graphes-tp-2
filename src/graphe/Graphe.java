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
			
			boolean start = false;
			boolean end = false;
			
	        while ((line = bufferedReader.readLine()) != null && cptLigne < nbLigne){
	            String[] infoSommets = line.split("\\s+");
	            sommets.add(new ArrayList<>());
	            for (int cptCol = 0 ; cptCol < nbCol ; cptCol++) {
	            	Type type = Type.getTypeFrom(Integer.parseInt(infoSommets[cptCol]));
	            	if (type == Type.START)
	            		start = true;
	            	if (type == Type.END)
	            		end = true;
	            	Sommet toAdd = new Sommet(cptCol, cptLigne, type);
	            	sommets.get(cptLigne).add(toAdd);
	            }
	            
	            cptLigne ++;
	        }
	        
	        bufferedReader.close();
	        
	        if (end && start && nbCol < 500 && nbLigne <500)
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
        
	}
	
	public Sommet getSommet(int x, int y) {
		try {
			return sommets.get(x).get(y);
		}catch (IndexOutOfBoundsException e){
			return null;
		}
	}
	
	public List<Sommet> getAdjacentsTo(Sommet s){
		
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
	
	public Sommet coordStart() {
		for(int i=0; i<this.nbLigne(); i++) {
			for(int j=0; j<this.nbColonne(); j++) {
				if(this.getSommet(i, j).type.equals(Type.START)) {
					return this.getSommet(i, j);
				}
			}
		}
		return null;
	}
	
	public Sommet coordEnd() {
		for(int i=0; i<this.nbLigne(); i++) {
			for(int j=0; j<this.nbColonne(); j++) {
				if(this.getSommet(i, j).type.equals(Type.END)) {
					return this.getSommet(i, j);
				}
			}
		}
		return null;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		
		for (List<Sommet> ligne : sommets) {
			for (Sommet s : ligne) {
				sb.append(s+" ");
			}
			sb.append("\n");
		}
		
		return sb.toString();
	}
	
	
}
