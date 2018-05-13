package vues;

import javax.swing.JButton;
import javax.swing.JPanel;

import controleurs.Controleur;
import modele.*;

/**
 * Une classe pour représenter la zone contenant le bouton.
 *
 * Cette zone n'aura pas Ã  Ãªtre mise Ã  jour et ne sera donc pas un observateur.
 * En revanche, comme la zone précédente, celle-ci est un panneau [JPanel].
 */
public class VueCommandes extends JPanel {
	/**
	 * Pour que le bouton puisse transmettre ses ordres, on garde une référence au
	 * modÃ¨le.
	 */
	private Modele modele;

	/** Constructeur. */
	public VueCommandes(Modele modele) {
		this.modele = modele;
		/**
		 * On crée un nouveau bouton, de classe [JButton], en précisant le texte qui
		 * doit l'étiqueter. Puis on ajoute ce bouton au panneau [this].
		 */
		JButton boutonAvance = new JButton("Tour suivant");
		this.add(boutonAvance);
		/**
		 * Le bouton, lorsqu'il est cliqué par l'utilisateur, produit un événement, de
		 * classe [ActionEvent].
		 *
		 * On a ici une variante du schéma observateur/observé : un objet implémentant
		 * une interface [ActionListener] va s'inscrire pour "écouter" les événements
		 * produits par le bouton, et recevoir automatiquements des notifications.
		 * D'autres variantes d'auditeurs pour des événements particuliers :
		 * [MouseListener], [KeyboardListener], [WindowListener].
		 *
		 * Cet observateur va enrichir notre schéma ModÃ¨le-Vue d'une couche
		 * intermédiaire ContrÃ´leur, dont l'objectif est de récupérer les événements
		 * produits par la vue et de les traduire en instructions pour le modÃ¨le. Cette
		 * strate intermédiaire est potentiellement riche, et peut notamment traduire
		 * les mÃªmes événements de différentes façons en fonction d'un état de
		 * l'application. Ici nous avons un seul bouton réalisant une seule action,
		 * notre contrÃ´leur sera donc particuliÃ¨rement simple. Cela nécessite néanmoins
		 * la création d'une classe dédiée.
		 */
		Controleur ctrl = new Controleur(modele);
		/** Enregistrement du contrÃ´leur comme auditeur du bouton. */
		boutonAvance.addActionListener(ctrl);

		/**
		 * Variante : une lambda-expression qui évite de créer une classe spécifique
		 * pour un contrÃ´leur simplissime.
		 *
		 * JButton boutonAvance = new JButton(">"); this.add(boutonAvance);
		 * boutonAvance.addActionListener(e -> { modele.avance(); });
		 *
		 */

	}
}