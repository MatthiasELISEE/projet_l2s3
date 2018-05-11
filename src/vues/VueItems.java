package vues;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Font;
import javax.swing.JPanel;
import java.io.IOException;
import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;
import modele.*;
import main.*;

public class VueItems extends JPanel implements Observer {
	private Modele modele;
	/** On maintient une référence vers le modéle. */
	private Image img;
	private Image imgFeu;
	Font f;
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

	public void update() {

	}

	public void paint(Graphics g) {
		try {
			img = ImageIO.read(new File("scores.jpeg"));
		} catch (IOException exc) {
			exc.printStackTrace();
		}

		// g.drawImage(img, x, y, this);
		// g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
		g.drawImage(img, 50, 10, TAILLE * 4, TAILLE * 2, this);
		f = new Font("Courier", Font.BOLD, 15);

		int coord = 200;
		int coordXImg = 305;
		for (Joueur j : this.modele.joueurs()) {
			String str = "";
			if (j.JoueurtArtefact(j) == Artefact.eau) {
				str = " : contient l'artefact eau";
				try {

					Image img2 = ImageIO.read(new File("eau.png"));
					g.setFont(f);
					g.setColor(Color.BLUE);
					g.drawString(j.getNom(j) + str, 120, coord);
					g.drawImage(img2, coordXImg + str.length() * 2 + j.getNom(j).length() * 2, coord - 15, TAILLE / 4,
							TAILLE / 4, this);
					coord = coord + 20;
				} catch (IOException exc) {
					exc.printStackTrace();
				}

			}
			if (j.JoueurtArtefact(j) == Artefact.feu) {
				str = " : contient l'artefact feu";
				try {
					Image img2 = ImageIO.read(new File("feu.png"));
					g.setFont(f);
					g.setColor(Color.BLUE);
					g.drawString(j.getNom(j) + str, 120, coord);
					g.drawImage(img2, coordXImg + str.length() * 2 + j.getNom(j).length() * 2, coord - 15, TAILLE / 4,
							TAILLE / 4, this);
					coord = coord + 20;
				} catch (IOException exc) {
					exc.printStackTrace();
				}
			}
			if (j.JoueurtArtefact(j) == Artefact.ter) {
				str = " : contient l'artefact ter";
				try {
					Image img2 = ImageIO.read(new File("terre.png"));
					g.setFont(f);
					g.setColor(Color.BLUE);
					g.drawString(j.getNom(j) + str, 120, coord);
					g.drawImage(img2, coordXImg + str.length() * 2 + j.getNom(j).length() * 2, coord - 15, TAILLE / 4,
							TAILLE / 4, this);
					coord = coord + 20;
				} catch (IOException exc) {
					exc.printStackTrace();
				}
			}
			if (j.JoueurtArtefact(j) == Artefact.air) {
				str = " : contient l'artefact air";
				try {
					Image img2 = ImageIO.read(new File("air.png"));
					g.setFont(f);
					g.setColor(Color.BLUE);
					g.drawString(j.getNom(j) + str, 120, coord);
					g.drawImage(img2, coordXImg + str.length() * 2 + j.getNom(j).length() * 2, coord - 15, TAILLE / 4,
							TAILLE / 4, this);
					coord = coord + 20;
				} catch (IOException exc) {
					exc.printStackTrace();
				}
			} else {
				str = " :Artefact à trouver";
				try {
					Image img2 = ImageIO.read(new File("elements.jpg"));
					g.setFont(f);
					g.setColor(Color.BLUE);
					g.drawString(j.getNom(j) + str, 120, coord);
					g.drawImage(img2, coordXImg + str.length() * 2 + j.getNom(j).length() * 2, coord - 15, TAILLE / 4,
							TAILLE / 4, this);
					coord = coord + 20;
				} catch (IOException exc) {
					exc.printStackTrace();
				}

			}

		}

	}

}
