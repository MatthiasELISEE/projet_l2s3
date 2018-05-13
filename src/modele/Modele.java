package modele;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

import main.*;

/**
 * Le mod�le : le coeur de l'application.
 *
 * Le mod�le �tend la classe [Observable] : il va poss�der un certain nombre
 * d'observateurs (ici, un : la partie de la vue responsable de l'affichage) et
 * devra les pr�venir avec [notifyObservers] lors des modifications. Voir la
 * m�thode [avance()] pour cela.
 */
public class Modele extends Observable {
	/** On fixe la taille de la grille. */
	public static final int HAUTEUR = 4, LARGEUR = 4;
	/** On stocke un tableau de cellules. */
	Cellule[][] cellules;
	private ArrayList<Joueur> joueurs;
	private int indexJoueurTour;

	LinkedList<Artefact> clesRestantes = new LinkedList<>(
			Arrays.asList(Artefact.eau, Artefact.ter, Artefact.feu, Artefact.air));

	// D�cide si les joueurs sont plac�s n'importe o�, ou sur la case d�part
	static boolean randomInitOfPlayers = true;

	/** Construction : on initialise un tableau de cellules. */
	public Modele() {

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
		 * Pour �viter les probl�mes aux bords, on ajoute une ligne et une colonne de
		 * chaque côt�, dont les cellules n'�volueront pas.
		 */

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
			for (int k = 0; k < nombreDeJoueurs; k++) {
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
			for (int k = 0; k < nombreDeJoueurs; k++) {
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
				cellules[i][j] = new Cellule(this, modele.cellules[i][j]);
			}
		}

		this.indexJoueurTour = modele.indexJoueurTour;
	}

	/**
	 * On coule des cellules prises aux hasard, et on implante aussi les artefacts
	 */
	public void init() {

		// Couler des cellules
		int nombresDeZonesCoulees = 0;
		while (nombresDeZonesCoulees < 3) {
			int X = (int) ((double) LARGEUR * Math.random());
			int Y = (int) ((double) HAUTEUR * Math.random());
			if (this.cellules[X][Y].estSeche()) {
				this.cellules[X][Y].etat = 1;
				nombresDeZonesCoulees++;
			}
		}

		// Ajouter les 4 artefacts
		LinkedList<Artefact> artefactsDeLile = new LinkedList<>();
		for (int k = 0; k < 4;) {
			int i, j;
			i = (int) ((double) LARGEUR * Math.random());
			j = (int) ((double) HAUTEUR * Math.random());
			if (cellules[i][j].estSeche() && cellules[i][j].getArtefact() == null && (i!=0 || j!=0)) {
				k++;
				Artefact artefact = null;
				do {
					artefact = Artefact.randomArtefact();
				} while (artefactsDeLile.contains(artefact));

				this.cellules[i][j].artefact = artefact;
				artefactsDeLile.add(artefact);
			}
		}
	}

	public void avance() {
		/**
		 * On proc�de en deux �tapes. - D'abord, pour chaque cellule on �value ce que
		 * sera son �tat à la prochaine g�n�ration. - Ensuite, on applique les
		 * �volutions qui ont �t� calcul�es.
		 */

		int nombresDeZonesCoulees = 0;
		while (nombresDeZonesCoulees < 3) {
			int X = (int) ((double) LARGEUR * Math.random());
			int Y = (int) ((double) HAUTEUR * Math.random());
			if (this.cellules[X][Y].couler()) {
				nombresDeZonesCoulees++;
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
		 * Pour finir, le mod�le ayant chang�, on signale aux observateurs qu'ils
		 * doivent se mettre � jour.
		 */
		notifyObservers();
	}

	public void tour() {
		// Demande l'action au joueur dont c'est le tour

		for (int k = 0; k < 3; k++) {
			this.joueurTour().demandeAction();
			notifyObservers();
		}

		// Don d'une cl�

		if (this.joueurTour().getCle() == null && Math.random() < 0.4 && !this.clesRestantes.isEmpty()) {
			Artefact cle = null;
			do {
				cle = Artefact.randomArtefact();
			} while (!this.clesRestantes.contains(cle));

			this.joueurTour().recevoirCle(cle);

			System.out.println("Vous avez gagn� la cl� " + cle + ", " + this.joueurTour().toString() + " !");
			this.clesRestantes.remove(cle);
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
	 * Une m�thode pour renvoyer la cellule aux coordonn�es choisies (sera utilis�e
	 * par la vue).
	 */
	public Cellule getCellule(int x, int y) {
		return cellules[x][y];
	}
}
