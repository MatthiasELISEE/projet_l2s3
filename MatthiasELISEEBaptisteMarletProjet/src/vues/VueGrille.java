package vues;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;
import java.awt.Image;

import modele.*;
import main.*;

/**
 * Cette vue va être un observateur du modèle et sera mise à jour à chaque
 * nouvelle génération des cellules.
 */
public class VueGrille extends JPanel implements Observer {
	/** On maintient une référence vers le modèle. */
	private Image img;
	private Modele modele;
	/** Définition d'une taille (en pixels) pour l'affichage des cellules. */
	private final static int TAILLE = 75;

	/** Constructeur. */
	public VueGrille(Modele modele) {
		this.modele = modele;
		/** On enregistre la vue [this] en tant qu'observateur de [modele]. */
		modele.addObserver(this);
		/**
		 * Définition et application d'une taille fixe pour cette zone de l'interface,
		 * calculée en fonction du nombre de cellules et de la taille d'affichage.
		 */
		Dimension dim = new Dimension(TAILLE * Modele.LARGEUR, TAILLE * Modele.HAUTEUR);
		this.setPreferredSize(dim);
	}

	/**
	 * L'interface [Observer] demande de fournir une méthode [update], qui sera
	 * appelée lorsque la vue sera notifiée d'un changement dans le modèle.
	 */
	public void update() {
		this.paintComponent(this.getGraphics());;
	}

	/**
	 * Les éléments graphiques comme [JPanel] possédent une méthode [paintComponent]
	 * qui définit l'action é accomplir pour afficher cet élément. On la redéfinit
	 * ici pour lui confier l'affichage des cellules.
	 *
	 * La classe [Graphics] regroupe les éléments de style sur le dessin, comme la
	 * couleur actuelle.
	 */
	public void paintComponent(Graphics g) {
		super.repaint();
		/** Pour chaque cellule... */
		for (int i = 1; i <= Modele.LARGEUR; i++) {
			for (int j = 1; j <= Modele.HAUTEUR; j++) {
				/**
				 * ... Appeler une fonction d'affichage auxiliaire. On lui fournit les
				 * informations de dessin [g] et les coordonnées du coin en haut é gauche.
				 */
				paint(g, modele.getCellule(i - 1, j - 1), (i - 1) * TAILLE, (j - 1) * TAILLE);
			}
		}
	}

	private void paint(Graphics g, Cellule c, int x, int y) {
		/** Sélection d'une couleur. */

		if (c.estSousLEau()) {
			g.setColor(Color.cyan);
		} else if (c.estSeche()) {
			g.setColor(Color.gray);
		} else {
			g.setColor(Color.BLUE);
		}
		/** Coloration d'un rectangle. */

		g.fillRect(x, y, TAILLE, TAILLE);
		if (x == 0 && y == 0) {
			g.setColor(Color.WHITE);
			g.drawString("Héliport", 0, TAILLE);
		}
		
		if (c.Yatiljoueur()) {
			g.setColor(Color.RED);
			
			g.fillRect(x + TAILLE / 3, y + TAILLE / 3, TAILLE / 3, TAILLE / 3);
			g.drawRect(x + TAILLE / 3, y + TAILLE / 3, TAILLE / 3, TAILLE / 3);
			g.setColor(Color.WHITE);
			
			int X = x + TAILLE / 3;
			int Y = y + TAILLE / 3;
			
			for (Joueur j : c.getJoueurs()) {
				g.drawString(j.toString(), X, Y);
				Y += 10;
			}
		}
		
		if (c.getArtefact() != null) {
			g.setColor(Color.GREEN);
			int X = x + TAILLE / 2;
			int Y = y + TAILLE / 2;
			g.drawString(c.getArtefact().toString(), X, Y);
			g.fillRect(x + TAILLE / 2, y + TAILLE / 2, TAILLE / 4, TAILLE / 4);
			g.drawRect(x + TAILLE / 2, y + TAILLE / 2, TAILLE / 4, TAILLE / 4);
		}
	}
}
