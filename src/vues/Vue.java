package vues;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import modele.*;

/**
 * La vue : l'interface avec l'utilisateur.
 *
 * On dÃ©finit une classe chapeau [CVue] qui crÃ©e la fenÃªtre principale de
 * l'application et contient les deux parties principales de notre vue : - Une
 * zone d'affichage oÃ¹ on voit l'ensemble des cellules. - Une zone de commande
 * avec un bouton pour passer Ã  la gÃ©nÃ©ration suivante.
 */
public class Vue {
	/**
	 * JFrame est une classe fournie pas Swing. Elle reprÃ©sente la fenÃªtre de
	 * l'application graphique.
	 */
	private JFrame frame;
	/**
	 * VueGrille et VueCommandes sont deux classes dÃ©finies plus loin, pour nos
	 * deux parties de l'interface graphique.
	 */
	private VueGrille grille;
	private VueCommandes commandes;
	private VueItems joueurFrame;

	/** Construction d'une vue attachÃ©e Ã  un modÃ¨le. */
	public Vue(Modele modele) {
		/** DÃ©finition de la fenÃªtre principale. */
		frame = new JFrame();
		frame.setTitle("L'Ile interdite : plus ça coule moins c'est cool");
		/**
		 * On prÃ©cise un mode pour disposer les diffÃ©rents Ã©lÃ©ments Ã 
		 * l'intÃ©rieur de la fenÃªtre. Quelques possibilitÃ©s sont : -
		 * BorderLayout (dÃ©faut pour la classe JFrame) : chaque Ã©lÃ©ment est
		 * disposÃ© au centre ou le long d'un bord. - FlowLayout (dÃ©faut pour un
		 * JPanel) : les Ã©lÃ©ments sont disposÃ©s l'un Ã  la suite de l'autre, dans
		 * l'ordre de leur ajout, les lignes se formant de gauche Ã  droite et de
		 * haut en bas. Un Ã©lÃ©ment peut passer Ã  la ligne lorsque l'on
		 * redimensionne la fenÃªtre. - GridLayout : les Ã©lÃ©ments sont disposÃ©s
		 * l'un Ã  la suite de l'autre sur une grille avec un nombre de lignes et
		 * un nombre de colonnes dÃ©finis par le programmeur, dont toutes les
		 * cases ont la mÃªme dimension. Cette dimension est calculÃ©e en fonction
		 * du nombre de cases Ã  placer et de la dimension du contenant.
		 */
		frame.setLayout(new FlowLayout());

		/** DÃ©finition des deux vues et ajout Ã  la fenÃªtre. */
		grille = new VueGrille(modele);
		frame.add(grille);
		
		commandes = new VueCommandes(modele);
		frame.add(commandes);
		
		
		joueurFrame = new VueItems(modele);
		frame.add(joueurFrame);
		
		/**
		 * Remarque : on peut passer Ã  la mÃ©thode [add] des paramÃ¨tres
		 * supplÃ©mentaires indiquant oÃ¹ placer l'Ã©lÃ©ment. Par exemple, si on
		 * avait conservÃ© la disposition par dÃ©faut [BorderLayout], on aurait pu
		 * Ã©crire le code suivant pour placer la grille Ã  gauche et les
		 * commandes Ã  droite. frame.add(grille, BorderLayout.WEST);
		 * frame.add(commandes, BorderLayout.EAST);
		 */

		/**
		 * Fin de la plomberie : - Ajustement de la taille de la fenÃªtre en
		 * fonction du contenu. - Indiquer qu'on quitte l'application si la
		 * fenÃªtre est fermÃ©e. - PrÃ©ciser que la fenÃªtre doit bien apparaÃ®tre Ã 
		 * l'Ã©cran.
		 */
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
