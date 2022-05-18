package graphe;

public class Sommet {
	int x,y;
	Type type;

	public Sommet(int x, int y, Type type) {
		super();
		this.x = x;
		this.y = y;
		this.type = type;
	}
	
	double getFlightDistTo(Sommet s) {
		return Math.sqrt(Math.pow(x - s.x, 2) + Math.pow(y - s.y, 2));
	}
	
	double getDistToAdjacent(Sommet s) {
		
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
