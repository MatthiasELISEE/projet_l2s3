package modele;

import java.util.ArrayList;

/**
 * Définition d'une classe pour les cellules. Cette classe fait encore partie du
 * modèle.
 */
public class Cellule {
	/** On conserve un pointeur vers la classe principale du modèle. */
	private Modele modele;

	int etat;
	/**
	 * On stocke les coordonnées pour pouvoir les passer au modèle lors de l'appel à
	 * [compteVoisines].
	 */
	private final int x, y;
	
	private boolean pasCoulee;
	private boolean pasAssechee;
	private ArrayList<Joueur> joueurs;
	private int prochainEtat;
	
	Artefact artefact = null;

	public Cellule(Modele modele, int x, int y) {
		this.modele = modele;
		this.etat = 2;
		this.x = x;
		this.y = y;
		this.pasCoulee = true;
		this.pasAssechee = true;
		this.prochainEtat = 2;
		joueurs = new ArrayList<>(0);
	}
	
	public Cellule(Modele modele, Cellule c) {
		this.modele = modele;
		this.etat = c.etat;
		this.x = c.x;
		this.y = c.y;
		this.pasCoulee = c.pasCoulee;
		this.pasAssechee = c.pasAssechee;
		this.prochainEtat = c.prochainEtat;
		joueurs = c.joueurs;
	}
	
	public Cellule(Cellule c) {
		this(new Modele(c.modele),c);
	}

	protected void evolue() {
		if (this.pasCoulee && this.pasAssechee) {
			prochainEtat = etat;
		} else {
			etat = prochainEtat;
		}
		this.pasCoulee = true;
	}

	protected boolean couler() {
		if (this.etat <= 0) {
			this.pasCoulee = true;
			return false;
		} else {
			this.pasCoulee = false;
			this.prochainEtat = this.etat - 1;
			return true;
		}
	}
	
	void assecher() {
		this.prochainEtat=2;
		this.pasAssechee = false;
	}

	public String toString() {
		return (this.modele + " " + this.etat + " " + this.prochainEtat);
	}

	public boolean Yatiljoueur() {
		return !joueurs.isEmpty();
	}

	public void ajouterJoueur(Joueur joueur) {
		joueurs.add(joueur);
	}

	public void retirerJoueur(Joueur joueur) {
		joueurs.remove(joueur);
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public boolean estSousLEau() {
		return this.etat==0;
	}
	
	public boolean estSeche() {
		return this.etat==2;
	}
	
	public ArrayList<Joueur> getJoueurs() {
		return this.joueurs;
	}
	
	public Artefact getArtefact() {
		return this.artefact;
	}
}