package modele;

import java.awt.FlowLayout;

import javax.swing.JFrame;

import vues.*;

/**
 * Définition d'une classe pour les cellules. Cette classe fait encore partie du
 * modèle.
 */
public class Cellule{
	/** On conserve un pointeur vers la classe principale du modèle. */
	private Modele modele;

	/** L'état d'une cellule est donné par un booléen. */
	protected int etat;
	/**
	 * On stocke les coordonnées pour pouvoir les passer au modèle lors de
	 * l'appel à [compteVoisines].
	 */
	private final int x, y;
	/**
	 * Et voilà pour savoir s'il y a un joueur
	 */
	protected boolean YajoueurC;
    protected int CordJoueurX, CordJoueurY;
	private boolean pasCoulee;
	private Joueurs j;
	
	
   
	public Cellule(Modele modele, int x, int y) {
		this.modele = modele;
		this.etat = 2;
		this.x = x;
		this.y = y;
		this.pasCoulee = true;
		this.prochainEtat = 2;
		
			this.YajoueurC=false;
		}
	 /*   this.CordJoueurX=-1;
		this.CordJoueurY=-1; */
	
	/* public void InitJoueur () {
		this.CordJoueurX= 1;
		this.CordJoueurY= 1;
		this.YajoueurC=true;
	}*/
	
	
	/**
	 * Le passage à la génération suivante se fait en deux étapes : - D'abord on
	 * calcule pour chaque cellule ce que sera sont état à la génération
	 * suivante (méthode [evalue]). On stocke le résultat dans un attribut
	 * supplémentaire [prochainEtat]. - Ensuite on met à jour l'ensemble des
	 * cellules (méthode [evolue]). Objectif : éviter qu'une évolution immédiate
	 * d'une cellule pollue la décision prise pour une cellule voisine.
	 */
	private int prochainEtat;
	

	// protected void evalue() {
	// if (this.etat != 0 && this.safe) {
	// this.prochainEtat = this.etat + this.modele.shouldWeKill(x,y);
	// this.safe=false;
	// }
	// }

	protected void evolue() {
		if (this.pasCoulee) {
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
	
	public String toString() {
		return (this.modele+" "+this.etat+" "+this.prochainEtat);
	}

	/** Un test à l'usage des autres classes (sera utilisé par la vue). */
	public int etat() {
		return etat;
	}
	public boolean Yatiljoueur() {
		return YajoueurC;
	}
}

/** Fin de la classe Cellule, et du modèle en général. */
