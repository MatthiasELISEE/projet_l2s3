package modele;

import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JFrame;

import vues.*;

/**
 * D�finition d'une classe pour les cellules. Cette classe fait encore partie du
 * modèle.
 */
public class Cellule {
	/** On conserve un pointeur vers la classe principale du modèle. */
	private Modele modele;

	/** L'�tat d'une cellule est donn� par un bool�en. */
	public int etat;
	/**
	 * On stocke les coordonn�es pour pouvoir les passer au modèle lors de l'appel à
	 * [compteVoisines].
	 */
	private final int x, y;
	/**
	 * Et voilà pour savoir s'il y a un joueur
	 */
	private boolean pasCoulee;
	private boolean pasAssechee;
	private ArrayList<Joueur> joueurs;
	
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
	/*
	 * this.CordJoueurX=-1; this.CordJoueurY=-1;
	 */

	/*
	 * public void InitJoueur () { this.CordJoueurX= 1; this.CordJoueurY= 1;
	 * this.YajoueurC=true; }
	 */
	/**
	 * Le passage à la g�n�ration suivante se fait en deux �tapes : - D'abord on
	 * calcule pour chaque cellule ce que sera sont �tat à la g�n�ration suivante
	 * (m�thode [evalue]). On stocke le r�sultat dans un attribut suppl�mentaire
	 * [prochainEtat]. - Ensuite on met à jour l'ensemble des cellules (m�thode
	 * [evolue]). Objectif : �viter qu'une �volution imm�diate d'une cellule pollue
	 * la d�cision prise pour une cellule voisine.
	 */
	private int prochainEtat;


	// protected void evalue() {
	// if (this.etat != 0 && this.safe) {
	// this.prochainEtat = this.etat + this.modele.shouldWeKill(x,y);
	// this.safe=false;
	// }
	// }

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