package modele;

import java.util.LinkedList;
import java.util.Arrays;
import java.util.Collections;

public class Joueur {
	
	static LinkedList<String> noms = new LinkedList<>(Arrays.asList("Oakley", "Charlie", "Azariah", "Skyler", "Frankie", "Finley"));
	
	private int x;
	private int y;
	String nom;
	private Modele modele;
	
	public Joueur(Modele modele, int x, int y) {
		this.x = x;
		this.y = y;
		this.modele = modele;
		
		// Prendre un nom au hasard
		Collections.shuffle(Joueur.noms);
		this.nom = Joueur.noms.pop();
	}
	
	public Joueur() {
		this.x = 5;
		this.y = 5;
	}

	public int x() {
		return this.x;
	}

	public int y() {
		return this.y;
	}
	
	public boolean assecher(int i, int j) {
		if (Math.abs(i-x)<2 && Math.abs(j-y)<2) {
			this.modele.cellules[i][j].assecher();
			return true;
		} else {
			return false;
		}
	}

	public boolean ExisteJoueur(int x, int y) {
		if (this.x == x && this.y == y) {
			return true;
		} else
			return false;
	}
	
	public void faitAction(char carac) {
		Cellule[][] cellules = this.modele.cellules;
		
		// TODO : il faut qu'on puisse choisir autre chose que les déplacements 
		
		if (carac == 'g' && this.x - 1 != 0) {
			cellules[this.x][this.y].retirerJoueur(this);
			cellules[this.x - 1][this.y].ajouterJoueur(this);
			this.x = this.x - 1;
			System.out.println("tu es all�e a gauche");
		}
		if (carac == 'd' && this.x + 1 != 10) {
			cellules[this.x][this.y].retirerJoueur(this);
			cellules[this.x + 1][this.y].ajouterJoueur(this);
			this.x = this.x + 1;
			System.out.println("tu es all�e a droite");
		}
		if (carac == 'h' && this.y - 1 != 0) {

			cellules[this.x][this.y].retirerJoueur(this);;
			cellules[this.x][this.y - 1].ajouterJoueur(this);
			this.y = this.y - 1;
			System.out.println("tu es all�e en haut");
		}
		if (carac == 'b' && this.y + 1 != 10) {
			cellules[this.x][this.y].retirerJoueur(this);
			cellules[this.x][this.y + 1].ajouterJoueur(this);

			this.y = this.y + 1;
			System.out.println("tu es all�e en bas");
		}
	}
	
	
	public void demandeAction() {
		String str;
		Scanner sc = new Scanner(System.in);
		//System.out.println("Veuillez saisir g pour Gauche, d pour droite, b pour bas, h pour haut :");
		System.out.println("déplacements : (g)auche, (d)roite, (b)as, (h)aut :");
		str = sc.nextLine();
		char carac = str.charAt(0);
		
		this.faitAction(carac);
	}
}
