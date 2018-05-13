package modele;

import java.util.LinkedList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.NoSuchElementException;

public class Joueur {

	// Les noms ont été choisis pour être courts et de sexe neutre.
	static LinkedList<String> noms = new LinkedList<>(Arrays.asList("Hall", "Brett", "Alex", "Fynn", "Jude", "Dave"));

	private int x;
	private int y;
	String nom;
	private Modele modele;

	private Artefact cle;

	public Joueur(Modele modele, int x, int y) throws NoSuchElementException {
		this.x = x;
		this.y = y;
		this.modele = modele;

		// Prendre un nom au hasard
		Collections.shuffle(Joueur.noms);
		try {
			this.nom = Joueur.noms.pop();
		} catch (NoSuchElementException n) {
			throw new NoSuchElementException("Plus de noms disponibles !");
		}

	}

	public Artefact getCle() {
		return cle;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public String toString() {
		return this.nom;
	}

	public boolean valide(int i, int j) {
		return (x >= 0 && y >= 0 && x < Modele.LARGEUR && y < Modele.HAUTEUR && Math.abs(i - x) < 2
				&& Math.abs(j - y) < 2);
	}

	boolean assecher(int i, int j) {
		if (valide(i, j)) {
			if (this.modele.cellules[i][j].etat == 1) {
			this.modele.cellules[i][j].assecher();
			this.modele.cellules[i][j].evolue();
			return true;
			} else {
				System.err.println("Impossible d'assécher une zone qui est sèche, ou complètement inondée.");
				return false;
			}
		} else {
			System.err.println("Impossible de se déplacer vers une zone submergée.");
			return false;
		}
	}

	boolean deplacer(int i, int j) {
		if (valide(i, j)) {
			if (this.modele.cellules[i][j].etat > 0) {
				this.modele.cellules[this.x][this.y].retirerJoueur(this);
				this.modele.cellules[i][j].ajouterJoueur(this);
				this.x = i;
				this.y = j;
				return true;
			} else {
				System.err.println("Impossible de se déplacer vers une zone submergée.");
				return false;
			}
		} else {
			return false;
		}
	}

	// Cette fonction permet de récupérer un artefact depuis des coordonnées
	Artefact recupereArtefact(int i, int j) {
		if (valide(i, j)) {
			Cellule c = this.modele.getCellule(i, j);

			if (Math.abs(i - x) < 2 && Math.abs(j - y) < 2 && this.cle == c.artefact) {
				Artefact returned = c.artefact;
				this.cle = null;
				c.artefact = null;
				return returned;
			}

			return null;
		} else {
			return null;
		}
	}

	boolean faitAction(String instruction) {

		if (instruction.equals("dg") && deplacer(x - 1, y)) {
			System.out.println("tu es allé(e) à gauche");
		} else if (instruction.equals("dd") && deplacer(x + 1, y)) {
			System.out.println("tu es allé(e) à droite");
		} else if (instruction.equals("dh") && deplacer(x, y - 1)) {
			System.out.println("tu es allé(e) en haut");
		} else if (instruction.equals("db") && deplacer(x, y + 1)) {
			System.out.println("tu es allé(e) en bas");
		}

		else if (instruction.equals("ag") && assecher(this.x - 1, this.y)) {
			System.out.println("tu as asséché à gauche");
		} else if (instruction.equals("ad") && assecher(this.x + 1, this.y)) {
			System.out.println("tu as asséché à droite");
		} else if (instruction.equals("ah") &&  assecher(this.x, this.y - 1)) {
			System.out.println("tu as asséché en haut");
		} else if (instruction.equals("ab") &&  assecher(this.x, this.y + 1)) {
			System.out.println("tu as asséché en bas");
		}

		else if (instruction.equals("rg") && this.x - 1 >= 0) {
			if (this.recupereArtefact(this.x - 1, this.y) == null) {
				System.out.println("Ta clé n'est pas compatible avec cette artefact.");
			} else {
				System.out.println("Bravo ! On va y arriver !");
			}
		} else if (instruction.equals("rd") && this.x + 1 < Modele.LARGEUR) {
			if (this.recupereArtefact(this.x + 1, this.y) == null) {
				System.out.println("Ta clé n'est pas compatible avec cette artefact.");
			} else {
				System.out.println("Bravo ! On va y arriver !");
			}
		} else if (instruction.equals("rh") && this.y - 1 >= 0) {
			if (this.recupereArtefact(this.x, this.y - 1) == null) {
				System.out.println("Ta clé n'est pas compatible avec cette artefact.");
			} else {
				System.out.println("Bravo ! On va y arriver !");
			}
		} else if (instruction.equals("rb") && this.y + 1 < Modele.HAUTEUR) {
			if (this.recupereArtefact(this.x, this.y + 1) == null) {
				System.out.println("Ta clé n'est pas compatible avec cette artefact.");
			} else {
				System.out.println("Bravo ! On va y arriver !");
			}
		}

		else {
			System.err.println("instructions incorrectes");
			return false;
		}

		return true;
	}

	// Demande puis fait une action, renvoie si l'action a été annulée ou non
	public void demandeAction() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		System.out.print("Choisis une action, " + this.nom);
		if (this.cle != null) {
			System.out.print("; tu possèdes la clé : " + this.cle);
		}
		System.out.println();

		try {
			String action, direction;
			do {
				System.out.println(
						"Sélectionner action : (d)éplacement, (a)ssèchement, (r)écupérer artefact, (n)e rien faire, puis appuyer sur Entrée");
				action = br.readLine();
				if (action.equals("n")) {
					System.out.println("Patience et longueur de temps valent mieux que force ni que rage");
					return;
				}
				System.out.println("direction : (g)auche, (d)roite, (b)as, (h)aut :");
				direction = br.readLine();
			} while (!this.faitAction(action + direction));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	void recevoirCle(Artefact cle) {
		this.cle = cle;
	}

}
