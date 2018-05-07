package modele;

import java.util.LinkedList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Joueur {

	// Les noms ont été choisis pour être courts et de sexe neutre.
	static LinkedList<String> noms = new LinkedList<>(
			Arrays.asList("Hall", "Brett", "Alex", "Fynn", "Jude", "Dave"));

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

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}
	
	public String toString() {
		return this.nom;
		//return this.nom + "("+this.x+","+this.y+")";
	}

	public boolean assecher(int i, int j) {
		if (Math.abs(i - x) < 2 && Math.abs(j - y) < 2) {
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

	boolean faitAction(String instruction) {
		Cellule[][] cellules = this.modele.cellules;
		// System.err.println("#"+instruction+"#");
		// System.err.println("#"+(instruction.equals("dh"))+"#");
		System.err.println("#"+(this.y - 1 >= 0)+"#");

		if (instruction.equals("dg") && this.x - 1 >= 0) {
			cellules[this.x][this.y].retirerJoueur(this);
			cellules[this.x - 1][this.y].ajouterJoueur(this);
			this.x = this.x - 1;
			System.out.println("tu es allé(e) à gauche");
		} else if (instruction.equals("dd") && this.x + 1 < Modele.LARGEUR) {
			cellules[this.x][this.y].retirerJoueur(this);
			cellules[this.x + 1][this.y].ajouterJoueur(this);
			this.x = this.x + 1;
			System.out.println("tu es allé(e) à droite");
		} else if (instruction.equals("dh") && this.y - 1 >= 0) {
			System.err.println("coilà");
			cellules[this.x][this.y].retirerJoueur(this);
			;
			cellules[this.x][this.y - 1].ajouterJoueur(this);
			this.y = this.y - 1;
			System.out.println("tu es allé(e) en haut");
		} else if (instruction.equals("db") && this.y + 1 < Modele.HAUTEUR) {
			cellules[this.x][this.y].retirerJoueur(this);
			cellules[this.x][this.y + 1].ajouterJoueur(this);
			this.y = this.y + 1;
			System.out.println("tu es allé(e) en bas");
		}

		else if (instruction.equals("ag") && this.x - 1 >= 0) {
			this.assecher(this.x - 1, this.y);
			System.out.println("tu as asséché à gauche");
		} else if (instruction.equals("ad") && this.x + 1 < Modele.LARGEUR) {
			this.assecher(this.x + 1, this.y);
			System.out.println("tu as asséché à droite");
		} else if (instruction.equals("ah") && this.y - 1 >= 0) {
			this.assecher(this.x, this.y - 1);
			System.out.println("tu as asséché en haut");
		} else if (instruction.equals("ab") && this.y + 1 < Modele.HAUTEUR) {
			this.assecher(this.x, this.y + 1);
			System.out.println("tu as asséché en bas");
		}

		else {
			System.err.println("instructions incorrectes");
			return false;
		}
		
		return true;
	}

	public void demandeAction() {
		// String str;
		// Scanner sc = new Scanner(System.in);
		// //System.out.println("Veuillez saisir g pour Gauche, d pour droite, b pour
		// bas, h pour haut :");
		// System.out.println("A ton tour, "+this.nom);
		// System.out.println("Sélectionner action : (d)éplacement, (a)ssèchement");
		// str = "";
		// while (sc.hasNextLine()) {
		// str = sc.nextLine();
		// }
		// char carac = str.charAt(0);
		// System.out.println("déplacements : (g)auche, (d)roite, (b)as, (h)aut :");
		// str = sc.nextLine();
		// String instruction = Character.toString(carac) +
		// Character.toString(str.charAt(0));
		//
		// this.faitAction(instruction);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println("Choisis une action, "+this.nom);
		try {
			String action,direction;
			do {
				System.out.println("Sélectionner action : (d)éplacement, (a)ssèchement, (q)ue dalle");
				action = br.readLine();
				if (action.equals("q")) {
					System.err.println("Patience et longueur de temps valent mieux que force ni que rage");
					return;
				}
				System.out.println("déplacements : (g)auche, (d)roite, (b)as, (h)aut :");
				direction = br.readLine();
			} while (!this.faitAction(action+direction));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
