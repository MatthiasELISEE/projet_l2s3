package modele;

public class Joueur {
	//private int nbjoueurs;
	//private boolean Yajoueur;
	private int x;
	private int y;
	private Modele modele;

	public Joueur(int x, int y, Modele modele) {
		this.x = x;
		this.y = y;
		this.modele = modele;
	}
	
	public Joueur (Modele modele) {
		this.modele= modele;
		this.InitJoueur();
	}

	public void InitJoueur() {
		this.x = 1;
		this.y = 1;
	}
	
	public int x() {
		return x;
	}
	
	public int y() {
		return y;
	}
}
