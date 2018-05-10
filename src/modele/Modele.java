package modele;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

import main.*;

/**
 * Le modèle : le coeur de l'application.
 *
 * Le modèle étend la classe [Observable] : il va posséder un certain nombre
 * d'observateurs (ici, un : la partie de la vue responsable de l'affichage) et
 * devra les prévenir avec [notifyObservers] lors des modifications. Voir la
 * méthode [avance()] pour cela.
 */
public class Modele extends Observable {
	/** On fixe la taille de la grille. */
	public static final int HAUTEUR = 10, LARGEUR = 10;
	/** On stocke un tableau de cellules. */
	Cellule[][] cellules;
	private ArrayList<Joueur> joueurs;
	private int indexJoueurTour;
	private double chancesOfGettingKilled = 0.05;
	
	LinkedList<Artefact> clesRestantes = new LinkedList<>(Arrays.asList(Artefact.EAU,Artefact.TERRE,Artefact.FEU,Artefact.AIR));
	
	// Décide si les joueurs sont placés n'importe où ou sur la case départ
	static boolean randomInitOfPlayers = true;

	/** Construction : on initialise un tableau de cellules. */
	public Modele() {
		/**
		 * Pour éviter les problèmes aux bords, on ajoute une ligne et une colonne de
		 * chaque cÃ´té, dont les cellules n'évolueront pas.
		 */

		// cellules = new Cellule[LARGEUR + 2][HAUTEUR + 2];
		// g = new Joueur(2,2);
		//
		// for (int i = 0; i < LARGEUR + 2; i++) {
		// for (int j = 0; j < HAUTEUR + 2; j++) {
		// cellules[i][j] = new Cellule(this, i, j);
		// if(g.ExisteJoueur(i, j)==true) {
		// cellules[i][j].YajoueurC=true;
		// }
		// }
		// }

		cellules = new Cellule[LARGEUR + 2][HAUTEUR + 2];
		this.joueurs = new ArrayList<>();

		for (int i = 0; i < LARGEUR + 2; i++) {
			for (int j = 0; j < HAUTEUR + 2; j++) {
				cellules[i][j] = new Cellule(this, i, j);
				if (i == 2 && j == 2) {
					Joueur joueur = new Joueur(this, 2, 2);
					cellules[2][2].ajouterJoueur(joueur);
					this.joueurs.add(joueur);
				}
			}
		}
		init();
	}

	public Modele(int nombreDeJoueurs) throws IndexOutOfBoundsException {
		/**
		 * Pour éviter les problèmes aux bords, on ajoute une ligne et une colonne de
		 * chaque cÃ´té, dont les cellules n'évolueront pas.
		 */
		// }
		
		if (nombreDeJoueurs > Joueur.noms.size()) {
			throw new IndexOutOfBoundsException();
		}

		cellules = new Cellule[LARGEUR + 2][HAUTEUR + 2];
		this.joueurs = new ArrayList<>(nombreDeJoueurs);

		for (int i = 0; i < LARGEUR + 2; i++) {
			for (int j = 0; j < HAUTEUR + 2; j++) {
				cellules[i][j] = new Cellule(this, i, j);
			}
		}

		init();
		
		if (randomInitOfPlayers) {
			Random random = new Random();
			for (int k= 0; k < nombreDeJoueurs; k++) {
				int i;
				int j;
				do {
					i = random.nextInt(LARGEUR);
					j = random.nextInt(HAUTEUR);
				} while (cellules[i][j].estSousLEau());
				Joueur joueur = new Joueur(this, i, j);
				this.cellules[i][j].ajouterJoueur(joueur);
				this.joueurs.add(joueur);
			}
		} else {
			for (int k= 0; k < nombreDeJoueurs; k++) {
				Joueur joueur = new Joueur(this, 0, 0);
				this.cellules[0][0].ajouterJoueur(joueur);
				this.joueurs.add(joueur);
			}
		}
	}
	
	

	public Modele(Modele modele) {
		cellules = new Cellule[LARGEUR + 2][HAUTEUR + 2];
		this.joueurs = new ArrayList<>(modele.joueurs);
		
		for (int i = 0; i <= LARGEUR; i++) {
			for (int j = 0; j <= HAUTEUR; j++) {
				cellules[i][j] = new Cellule(this,modele.cellules[i][j]);
			}
		}
		
		this.indexJoueurTour = modele.indexJoueurTour;
	}

	/**
	 * On coule des cellules prises aux hasard, et on implante aussi les artefacts
	 */
	public void init() {
		for (int i = 0; i <= LARGEUR; i++) {
			for (int j = 0; j <= HAUTEUR; j++) {
				if (Math.random() < chancesOfGettingKilled) {
					cellules[i][j].etat = 1;
				}
			}
		}
		
		Random random = new Random();
		LinkedList<Artefact> artefactsDeLile = new LinkedList<>();
		for (int k= 0; k < 4; k++) {
			int i,j;
			do {
				i = random.nextInt(LARGEUR);
				j = random.nextInt(HAUTEUR);
			} while (!cellules[i][j].estEmergee());
			
			Artefact artefact = null;
			
			do {
				artefact = Artefact.randomArtefact();
			} while (artefactsDeLile.contains(artefact));
			
			this.cellules[i][j].artefact = artefact;
		}
	}

	public void avance() {
		/**
		 * On procède en deux étapes. - D'abord, pour chaque cellule on évalue ce que
		 * sera son état Ã  la prochaine génération. - Ensuite, on applique les
		 * évolutions qui ont été calculées.
		 */

		int nombresDeCool = 0;
		while (nombresDeCool < 3) {
			int X = (int) ((double) LARGEUR * Math.random());
			int Y = (int) ((double) HAUTEUR * Math.random());
			if (this.cellules[X][Y].couler()) {
				nombresDeCool++;
			}
		}

		for (int i = 0; i < LARGEUR; i++) {
			for (int j = 0; j < HAUTEUR; j++) {
				// System.out.println(cellules[i][j]+" "+i+" "+j);
				cellules[i][j].evolue();
				// System.out.println(cellules[i][j]+" "+i+" "+j);
			}
		}

		this.indexJoueurTour = 0;

		/**
		 * Pour finir, le modèle ayant changé, on signale aux observateurs qu'ils
		 * doivent se mettre à jour.
		 */
		notifyObservers();
	}

	public void tour() {
		for (int k = 0; k < 3; k++) {
			this.joueurTour().demandeAction();
			notifyObservers();
		}
		this.indexJoueurTour++;
		notifyObservers();
	}

	public Joueur joueurTour() {
		return this.joueurs.get(indexJoueurTour);
	}

	public ArrayList<Joueur> joueurs() {
		return joueurs;
	}

	/**
	 * Une méthode pour renvoyer la cellule aux coordonnées choisies (sera utilisée
	 * par la vue).
	 */
	public Cellule getCellule(int x, int y) {
		return cellules[x][y];
	}
	/**
	 * Notez qu'Ã  l'intérieur de la classe [CModele], la classe interne est connue
	 * sous le nom abrégé [Cellule]. Son nom complet est [CModele.Cellule], et cette
	 * version complète est la seule Ã  pouvoir Ãªtre utilisée depuis l'extérieur de
	 * [CModele]. Dans [CModele], les deux fonctionnent.
	 */
}
