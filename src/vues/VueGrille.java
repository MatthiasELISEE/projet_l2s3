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
 * Une classe pour repr�senter la zone d'affichage des cellules.
 *
 * JPanel est une classe d'�l�ments graphiques, pouvant comme JFrame contenir
 * d'autres �l�ments graphiques.
 *
 * Cette vue va �tre un observateur du mod�le et sera mise � jour � chaque
 * nouvelle g�n�ration des cellules.
 */
public class VueGrille extends JPanel implements Observer {
	/** On maintient une r�f�rence vers le mod�le. */
	private Image img;
	private Modele modele;
	/** D�finition d'une taille (en pixels) pour l'affichage des cellules. */
	private final static int TAILLE = 50;

	/** Constructeur. */
	public VueGrille(Modele modele) {
		this.modele = modele;
		/** On enregistre la vue [this] en tant qu'observateur de [modele]. */
		modele.addObserver(this);
		/**
		 * D�finition et application d'une taille fixe pour cette zone de l'interface,
		 * calcul�e en fonction du nombre de cellules et de la taille d'affichage.
		 */
		Dimension dim = new Dimension(TAILLE * Modele.LARGEUR, TAILLE * Modele.HAUTEUR);
		this.setPreferredSize(dim);
	}

	/**
	 * L'interface [Observer] demande de fournir une m�thode [update], qui sera
	 * appel�e lorsque la vue sera notifi�e d'un changement dans le mod�le. Ici on
	 * se content de r�afficher toute la grille avec la m�thode pr�d�finie
	 * [repaint].
	 */
	public void update() {
		super.repaint();
	}

	/**
	 * Les �l�ments graphiques comme [JPanel] poss�dent une m�thode [paintComponent]
	 * qui d�finit l'action � accomplir pour afficher cet �l�ment. On la red�finit
	 * ici pour lui confier l'affichage des cellules.
	 *
	 * La classe [Graphics] regroupe les �l�ments de style sur le dessin, comme la
	 * couleur actuelle.
	 */
	public void paintComponent(Graphics g) {
		super.repaint();
		/** Pour chaque cellule... */
		for (int i = 1; i <= Modele.LARGEUR; i++) {
			for (int j = 1; j <= Modele.HAUTEUR; j++) {
				/**
				 * ... Appeler une fonction d'affichage auxiliaire. On lui fournit les
				 * informations de dessin [g] et les coordonn�es du coin en haut � gauche.
				 */
				paint(g, modele.getCellule(i - 1, j - 1), (i - 1) * TAILLE, (j - 1) * TAILLE);
			}
		}
	}

	/**
	 * Fonction auxiliaire de dessin d'une cellule. Ici, la classe [Cellule] ne peut
	 * �tre d�sign�e que par l'interm�diaire de la classe [CModele] � laquelle elle
	 * est interne, d'o� le type [CModele.Cellule]. Ceci serait impossible si
	 * [Cellule] �tait d�clar�e priv�e dans [CModele].
	 */

	private void paint(Graphics g, Cellule c, int x, int y) {		
		/** S�lection d'une couleur. */

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
