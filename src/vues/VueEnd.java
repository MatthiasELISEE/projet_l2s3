package vues;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;

import javax.swing.JPanel;
import java.io.IOException;
import java.awt.Image;
import java.io.File;
import javax.swing.JFrame;
import javax.imageio.ImageIO;
import modele.*;
import main.*;

public class VueEnd extends JPanel implements Observer {
	/** On maintient une référence vers le modèle. */
	private Image img;
	private Modele modele;
	private JFrame frame;
	/** Définition d'une taille (en pixels) pour l'affichage des cellules. */
	private final static int TAILLE = 50;
	private VueItems joueurFrame;

	/** Constructeur. */
	public VueEnd(Modele modele) {
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
		this.paint(this.getGraphics());
	}

	/**
	 * Les éléments graphiques comme [JPanel] possédent une méthode [paintComponent]
	 * qui définit l'action à accomplir pour afficher cet élément. On la redéfinit
	 * ici pour lui confier l'affichage des cellules.
	 *
	 * La classe [Graphics] regroupe les éléments de style sur le dessin, comme la
	 * couleur actuelle.
	 */

	public void paint(Graphics g) {
		super.paint(g);
		int compt = 0;
		/** Pour chaque cellule... */
		for (int i = 1; i <= Modele.LARGEUR; i++) {
			for (int j = 1; j <= Modele.HAUTEUR; j++) {
				if (modele.getCellule(i - 1, j - 1).estSousLEau()) {
					compt++;
				}
				if (compt == 3) {
					try {
						img = ImageIO.read(new File("end.jpg"));
					} catch (IOException exc) {
						exc.printStackTrace();
					}
					g.drawImage(img, 0, 0, TAILLE * 2, TAILLE * 2, this);
					// System.exit(0);
					/*
					 * frame = new JFrame();
					 * frame.setTitle("L'Ile interdite : plus ça coule moins c'est cool");
					 * frame.setLayout(new FlowLayout());
					 * 
					 * joueurFrame = new VueItems(modele); frame.add(joueurFrame);
					 * 
					 * frame.pack(); frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					 * frame.setVisible(true);
					 */
					try {
						Thread.sleep(4000);
						System.exit(0);
					} catch (InterruptedException ex) {
						Thread.currentThread().interrupt();
					}

				}

			}
		}
	}
}
