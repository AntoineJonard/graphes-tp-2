package graphe;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Sommet {
	private int x,y;
	private Type type;
	private  List<Sommet> adjacents;

	public Sommet(int x, int y, Type type) {
		super();
		this.x = x;
		this.y = y;
		this.type = type;
		adjacents = new ArrayList<>();
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Type getType() {
		return type;
	}

	public double getFlightDistTo(Sommet s) {
		return Math.sqrt(Math.pow(x - s.x, 2) + Math.pow(y - s.y, 2));
	}
	
	public double getDistToAdjacent(Sommet s) {
		
		if (s.type != Type.OBSTACLE && this.type != Type.OBSTACLE) {
			int diff = Math.abs(getCoordSum()+s.getCoordSum());
			
			switch(diff) {
			case 0 :
				return 0;
			case 1 :
				return 1;
			case 2 :
				return Math.sqrt(2);
			default :
				return -1;
			}
		}else {
			return -1;
		}
	}

	public void setAdjacents(List<Sommet> adjacents) {
		this.adjacents = adjacents;
	}

	public List<Sommet> getAdjacents() {
		return adjacents;
	}
	
	public List<Sommet> getAccessibleAdjacents() {
		return adjacents.stream().filter((s)-> s.type != Type.OBSTACLE).collect(Collectors.toList());
	}

	private int getCoordSum() {
		return x + y;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sommet other = (Sommet) obj;
		return type == other.type && x == other.x && y == other.y;
	}

	@Override
	public String toString() {
		return type+" [" + x + ", " + y + "]";
	}
	
}
