package graphe;

public class InvalidGraphException extends Exception {

	@Override
	public void printStackTrace() {
		// TODO Auto-generated method stub
		super.printStackTrace();
		System.out.println("Invalid graph");
	}
	

}
