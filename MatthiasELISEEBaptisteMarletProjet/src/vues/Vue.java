package vues;

import java.awt.FlowLayout;

import javax.swing.JFrame;
import modele.*;

/**
 * La vue : l'interface avec l'utilisateur.
 *
 * On définit une classe chapeau [CVue] qui crée la fenêtre principale de
 * l'application et contient les deux parties principales de notre vue : - Une
 * zone d'affichage où on voit l'ensemble des cellules. - Une zone de commande
 * avec un bouton pour passer à la génération suivante.
 */
public class Vue {

	private JFrame frame;
	private VueEnd end;
	private VueGrille grille;
	private VueCommandes commandes;
	private VueItems joueurFrame;

	/** Construction d'une vue attachée à un modà¨le. */
	public Vue(Modele modele) {
		/** Définition de la fenêtre principale. */
		frame = new JFrame();
		frame.setTitle("L'Ile interdite");
		frame.setLayout(new FlowLayout());

		/** Définition des 3 vues et ajout à la fenêtre. */
		grille = new VueGrille(modele);
		frame.add(grille);

		commandes = new VueCommandes(modele);
		frame.add(commandes);

		joueurFrame = new VueItems(modele);
		frame.add(joueurFrame);

		end = new VueEnd(modele);
		frame.add(end);

		/**
		 * Fin de la plomberie : - Ajustement de la taille de la fenêtre en fonction du
		 * contenu. - Indiquer qu'on quitte l'application si la fenêtre est fermée. -
		 * Préciser que la fenêtre doit bien apparaà®tre à l'écran.
		 */
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
