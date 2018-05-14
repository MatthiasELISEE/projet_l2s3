package vues;

import javax.swing.JButton;
import javax.swing.JPanel;

import controleurs.Controleur;
import modele.*;

/**
 * Une classe pour représenter la zone contenant le bouton.
 *
 * Cette zone n'aura pas à être mise à jour et ne sera donc pas un observateur.
 * En revanche, comme la zone précédente, celle-ci est un panneau [JPanel].
 */
public class VueCommandes extends JPanel {
	/**
	 * Pour que le bouton puisse transmettre ses ordres, on garde une référence au
	 * modèle.
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
		Controleur ctrl = new Controleur(modele);
		/** Enregistrement du contrôleur comme auditeur du bouton. */
		boutonAvance.addActionListener(ctrl);
	}
}