package modele;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

import main.*;

/**
 * Le modéle : le coeur de l'application.
 *
 * Le modéle étend la classe [Observable] : il va posséder un certain nombre
 * d'observateurs (ici, un : la partie de la vue responsable de l'affichage) et
 * devra les prévenir avec [notifyObservers] lors des modifications. Voir la
 * méthode [avance()] pour cela.
 */
public class Modele extends Observable {
	/** On fixe la taille de la grille. */
	public static final int HAUTEUR = 4, LARGEUR = 4;
	/** On stocke un tableau de cellules. */
	Cellule[][] zones;
	private ArrayList<Joueur> joueurs;
	private int indexJoueurTour;

	LinkedList<Artefact> clesRestantes = new LinkedList<>(
			Arrays.asList(Artefact.eau, Artefact.ter, Artefact.feu, Artefact.air));

	// Décide si les joueurs sont placés n'importe où, ou sur la case départ
	static boolean randomInitOfPlayers = false;

	/** Construction : on initialise un tableau de zones. */
	public Modele() {

		zones = new Cellule[LARGEUR + 2][HAUTEUR + 2];
		this.joueurs = new ArrayList<>();

		for (int i = 0; i < LARGEUR + 2; i++) {
			for (int j = 0; j < HAUTEUR + 2; j++) {
				zones[i][j] = new Cellule(this, i, j);
				if (i == 2 && j == 2) {
					Joueur joueur = new Joueur(this, 2, 2);
					zones[2][2].ajouterJoueur(joueur);
					this.joueurs.add(joueur);
				}
			}
		}
		init();
	}

	public Modele(int nombreDeJoueurs) throws IndexOutOfBoundsException {
		
		if (nombreDeJoueurs > Joueur.noms.size()) {
			throw new IndexOutOfBoundsException();
		}

		zones = new Cellule[LARGEUR + 2][HAUTEUR + 2];
		this.joueurs = new ArrayList<>(nombreDeJoueurs);

		for (int i = 0; i < LARGEUR + 2; i++) {
			for (int j = 0; j < HAUTEUR + 2; j++) {
				zones[i][j] = new Cellule(this, i, j);
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
				} while (zones[i][j].estSousLEau());
				Joueur joueur = new Joueur(this, i, j);
				this.zones[i][j].ajouterJoueur(joueur);
				this.joueurs.add(joueur);
			}
		} else {
			for (int k = 0; k < nombreDeJoueurs; k++) {
				Joueur joueur = new Joueur(this, 0, 0);
				this.zones[0][0].ajouterJoueur(joueur);
				this.joueurs.add(joueur);
			}
		}
	}

	public Modele(Modele modele) {
		zones = new Cellule[LARGEUR + 2][HAUTEUR + 2];
		this.joueurs = new ArrayList<>(modele.joueurs);

		for (int i = 0; i <= LARGEUR; i++) {
			for (int j = 0; j <= HAUTEUR; j++) {
				zones[i][j] = new Cellule(this, modele.zones[i][j]);
			}
		}

		this.indexJoueurTour = modele.indexJoueurTour;
	}

	/**
	 * On coule des zones prises aux hasard, et on implante aussi les artefacts
	 */
	public void init() {

		// Couler des cellules
		int nombresDeZonesCoulees = 0;
		while (nombresDeZonesCoulees < 3) {
			int X = (int) ((double) LARGEUR * Math.random());
			int Y = (int) ((double) HAUTEUR * Math.random());
			if (this.zones[X][Y].estSeche()) {
				this.zones[X][Y].etat = 1;
				nombresDeZonesCoulees++;
			}
		}

		// Ajouter les 4 artefacts
		LinkedList<Artefact> artefactsDeLile = new LinkedList<>();
		for (int k = 0; k < 4;) {
			int i, j;
			i = (int) ((double) LARGEUR * Math.random());
			j = (int) ((double) HAUTEUR * Math.random());
			if (zones[i][j].estSeche() && zones[i][j].getArtefact() == null && (i!=0 || j!=0)) {
				k++;
				Artefact artefact = null;
				do {
					artefact = Artefact.randomArtefact();
				} while (artefactsDeLile.contains(artefact));

				this.zones[i][j].artefact = artefact;
				artefactsDeLile.add(artefact);
			}
		}
	}

	public void avance() {
		/**
		 * On procéde en deux étapes. - D'abord, pour chaque cellule on évalue ce que
		 * sera son état à la prochaine génération. - Ensuite, on applique les
		 * évolutions qui ont été calculées.
		 */

		int nombresDeZonesCoulees = 0;
		while (nombresDeZonesCoulees < 3) {
			int X = (int) ((double) LARGEUR * Math.random());
			int Y = (int) ((double) HAUTEUR * Math.random());
			if (this.zones[X][Y].couler()) {
				nombresDeZonesCoulees++;
			}
		}

		for (int i = 0; i < LARGEUR; i++) {
			for (int j = 0; j < HAUTEUR; j++) {
				zones[i][j].evolue();
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
		// Demande l'action au joueur dont c'est le tour

		for (int k = 0; k < 3; k++) {
			this.joueurTour().demandeAction();
			notifyObservers();
		}

		// Don d'une clé : à 40% de chances.
		if (this.joueurTour().getCle() == null && Math.random() < 0.4 && !this.clesRestantes.isEmpty()) {
			Artefact cle = null;
			do {
				cle = Artefact.randomArtefact();
			} while (!this.clesRestantes.contains(cle));

			this.joueurTour().recevoirCle(cle);

			System.out.println("Vous avez gagné la clé " + cle + ", " + this.joueurTour().toString() + " !");
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
	 * Une méthode pour renvoyer la cellule aux coordonnées choisies (sera utilisée
	 * surtout par la vue).
	 */
	public Cellule getCellule(int x, int y) {
		return zones[x][y];
	}
}
