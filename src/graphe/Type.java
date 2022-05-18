package graphe;

public enum Type {
	OBSTACLE,COMMON,START,END;
	
	static Type getTypeFrom(int t) {
		switch(t) {
		case 0 :
			return OBSTACLE;
		case 1 :
			return COMMON;
		case 2 : 
			return START;
		case 3 :
			return END;
		default :
			return null;
		}
	}
}
