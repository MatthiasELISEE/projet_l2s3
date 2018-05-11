package vues;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;
import java.io.IOException;
import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;
import modele.*;
import main.*;

/**
 * Une classe pour représenter la zone d'affichage des cellules.
 *
 * JPanel est une classe d'éléments graphiques, pouvant comme JFrame contenir
 * d'autres éléments graphiques.
 *
 * Cette vue va étre un observateur du modèle et sera mise à jour à chaque
 * nouvelle génération des cellules.
 */
public class VueGrille extends JPanel implements Observer {
	/** On maintient une référence vers le modèle. */
	private Image img;
	private Modele modele;
	/** Définition d'une taille (en pixels) pour l'affichage des cellules. */
	private final static int TAILLE = 50;

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
	 * appelée lorsque la vue sera notifiée d'un changement dans le modèle. Ici on
	 * se content de réafficher toute la grille avec la méthode prédéfinie
	 * [repaint].
	 */
	public void update() {
		super.repaint();
	}

	/**
	 * Les éléments graphiques comme [JPanel] possédent une méthode [paintComponent]
	 * qui définit l'action à accomplir pour afficher cet élément. On la redéfinit
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
				 * informations de dessin [g] et les coordonnées du coin en haut à gauche.
				 */
				paint(g, modele.getCellule(i - 1, j - 1), (i - 1) * TAILLE, (j - 1) * TAILLE);
			}
		}
	}

	/**
	 * Fonction auxiliaire de dessin d'une cellule. Ici, la classe [Cellule] ne peut
	 * étre désignée que par l'intermédiaire de la classe [CModele] é laquelle elle
	 * est interne, d'oé le type [CModele.Cellule]. Ceci serait impossible si
	 * [Cellule] était déclarée privée dans [CModele].
	 */

	private void paint(Graphics g, Cellule c, int x, int y) {		
		/** Sélection d'une couleur. */

		if (c.etat == 0) {
			g.setColor(Color.cyan);
		} else if (c.etat == 1) {
			g.setColor(Color.BLUE);
		} else {
			g.setColor(Color.gray);
		}
		/** Coloration d'un rectangle. */

		g.fillRect(x, y, TAILLE, TAILLE);
		if (x == 0 && y == 0) {
			try {
				img = ImageIO.read(new File("image_case_depart.png"));
			} catch (IOException exc) {
				exc.printStackTrace();
			}

			// g.drawImage(img, x, y, this);
			// g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
			g.drawImage(img, 0, 0, TAILLE, TAILLE, this);
			
		}
		
		if (c.Yatiljoueur()) {
			g.setColor(Color.RED);
			
			g.fillRect(x + TAILLE / 3, y + TAILLE / 3, TAILLE / 3, TAILLE / 3);
			g.drawRect(x + TAILLE / 3, y + TAILLE / 3, TAILLE / 3, TAILLE / 3);
			g.setColor(Color.BLACK);
			
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
