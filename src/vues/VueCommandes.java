package vues;

import javax.swing.JButton;
import javax.swing.JPanel;

import controleurs.Controleur;
import modele.*;

/**
 * Une classe pour repr�senter la zone contenant le bouton.
 *
 * Cette zone n'aura pas à être mise à jour et ne sera donc pas un observateur.
 * En revanche, comme la zone pr�c�dente, celle-ci est un panneau [JPanel].
 */
public class VueCommandes extends JPanel {
	/**
	 * Pour que le bouton puisse transmettre ses ordres, on garde une r�f�rence au
	 * modèle.
	 */
	private Modele modele;

	/** Constructeur. */
	public VueCommandes(Modele modele) {
		this.modele = modele;
		/**
		 * On cr�e un nouveau bouton, de classe [JButton], en pr�cisant le texte qui
		 * doit l'�tiqueter. Puis on ajoute ce bouton au panneau [this].
		 */
		JButton boutonAvance = new JButton("Tour suivant");
		this.add(boutonAvance);
		/**
		 * Le bouton, lorsqu'il est cliqu� par l'utilisateur, produit un �v�nement, de
		 * classe [ActionEvent].
		 *
		 * On a ici une variante du sch�ma observateur/observ� : un objet impl�mentant
		 * une interface [ActionListener] va s'inscrire pour "�couter" les �v�nements
		 * produits par le bouton, et recevoir automatiquements des notifications.
		 * D'autres variantes d'auditeurs pour des �v�nements particuliers :
		 * [MouseListener], [KeyboardListener], [WindowListener].
		 *
		 * Cet observateur va enrichir notre sch�ma Modèle-Vue d'une couche
		 * interm�diaire Contrôleur, dont l'objectif est de r�cup�rer les �v�nements
		 * produits par la vue et de les traduire en instructions pour le modèle. Cette
		 * strate interm�diaire est potentiellement riche, et peut notamment traduire
		 * les mêmes �v�nements de diff�rentes fa�ons en fonction d'un �tat de
		 * l'application. Ici nous avons un seul bouton r�alisant une seule action,
		 * notre contrôleur sera donc particulièrement simple. Cela n�cessite n�anmoins
		 * la cr�ation d'une classe d�di�e.
		 */
		Controleur ctrl = new Controleur(modele);
		/** Enregistrement du contrôleur comme auditeur du bouton. */
		boutonAvance.addActionListener(ctrl);

		/**
		 * Variante : une lambda-expression qui �vite de cr�er une classe sp�cifique
		 * pour un contrôleur simplissime.
		 *
		 * JButton boutonAvance = new JButton(">"); this.add(boutonAvance);
		 * boutonAvance.addActionListener(e -> { modele.avance(); });
		 *
		 */

	}
}