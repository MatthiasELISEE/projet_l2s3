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
public class VueItems extends JPanel implements Observer {
	
	/** On maintient une référence vers le modéle. */
	private Image img;
	private Modele modele;
	/** Définition d'une taille (en pixels) pour l'affichage des cellules. */
	private final static int TAILLE = 80;

	/** Constructeur. */
	public VueItems(Modele modele) {
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
	public void paint(Graphics g) {
		
		g.setColor(Color.BLUE);
		g.drawString("Je suis ton père!!!!!!!!", 50, 50);
		
	
	}

	
}
