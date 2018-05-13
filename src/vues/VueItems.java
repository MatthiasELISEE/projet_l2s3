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
	/** On maintient une rÃ©fÃ©rence vers le modÃ©le. */
	private Image img;
	Font f;
	private final static int TAILLE = 80;

	/** Constructeur. */
	public VueItems(Modele modele) {
		this.modele = modele;
		/** On enregistre la vue [this] en tant qu'observateur de [modele]. */
		modele.addObserver(this);
		/**
		 * DÃ©finition et application d'une taille fixe pour cette zone de l'interface,
		 * calculÃ©e en fonction du nombre de cellules et de la taille d'affichage.
		 */
		Dimension dim = new Dimension(TAILLE * Modele.LARGEUR, TAILLE * Modele.HAUTEUR);
		this.setPreferredSize(dim);
	}

	public void update() {
		this.paint(this.getGraphics());
	}

	public void paint(Graphics g) {
		super.paint(g);
		try {
			img = ImageIO.read(new File("scores.jpeg"));
		} catch (IOException exc) {
			exc.printStackTrace();
		}

		// image score

		g.drawImage(img, 50, 10, TAILLE * 4, TAILLE * 2, this);
		f = new Font("Arial", Font.BOLD, 15);
		int coordString = 90;
		int coord = 200;
		int coordXImg = 240;

		for (Joueur j : this.modele.joueurs()) {
			String str = "";
			if (j.getCle() == Artefact.eau) {
				str = " : possède la clee eau";
				try {

					Image img2 = ImageIO.read(new File("eau.png"));
					g.setFont(f);
					g.setColor(Color.BLUE);
					g.drawString(j.toString() + str, coordString, coord);
					g.drawImage(img2, coordXImg + str.length() * 2 + j.toString().length() * 2, coord - 15, TAILLE / 4,
							TAILLE / 4, this);
					coord = coord + 20;
				} catch (IOException exc) {
					exc.printStackTrace();
				}

			}
			if (j.getCle() == Artefact.feu) {
				str = " : possède la clee feu";
				try {
					Image img2 = ImageIO.read(new File("feu.png"));
					g.setFont(f);
					g.setColor(Color.BLUE);
					g.drawString(j.toString() + str, coordString, coord);
					g.drawImage(img2, coordXImg + str.length() * 2 + j.toString().length() * 2, coord - 15, TAILLE / 4,
							TAILLE / 4, this);
					coord = coord + 20;
				} catch (IOException exc) {
					exc.printStackTrace();
				}
			}
			if (j.getCle() == Artefact.ter) {
				str = " : possède la clee ter";
				try {
					Image img2 = ImageIO.read(new File("terre.png"));
					g.setFont(f);
					g.setColor(Color.BLUE);
					g.drawString(j.toString() + str, coordString, coord);
					g.drawImage(img2, coordXImg + str.length() * 2 + j.toString().length() * 2, coord - 15, TAILLE / 4,
							TAILLE / 4, this);
					coord = coord + 20;
				} catch (IOException exc) {
					exc.printStackTrace();
				}
			}
			if (j.getCle() == Artefact.air) {
				str = " : possède la clee air";
				try {
					Image img2 = ImageIO.read(new File("air.png"));
					g.setFont(f);
					g.setColor(Color.BLUE);
					g.drawString(j.toString() + str, coordString, coord);
					g.drawImage(img2, coordXImg + str.length() * 2 + j.toString().length() * 2, coord - 15, TAILLE / 4,
							TAILLE / 4, this);
					coord = coord + 20;
				} catch (IOException exc) {
					exc.printStackTrace();
				}
			}

		}

	}

}
