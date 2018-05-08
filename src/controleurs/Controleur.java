package controleurs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import modele.*;

/**
 * Classe pour notre contr�leur rudimentaire.
 * 
 * Le contr�leur impl�mente l'interface [ActionListener] qui demande
 * uniquement de fournir une m�thode [actionPerformed] indiquant la
 * r�ponse du contr�leur à la r�ception d'un �v�nement.
 */
public class Controleur implements ActionListener {
    /**
     * On garde un pointeur vers le mod�le, car le contr�leur doit
     * provoquer un appel de m�thode du mod�le.
     * Remarque : comme cette classe est interne, cette inscription
     * explicite du mod�le est inutile. On pourrait se contenter de
     * faire directement r�f�rence au mod�le enregistr� pour la classe
     * englobante [VueCommandes].
     */
    Modele modele;
    public Controleur(Modele modele) { this.modele = modele;}
    /**
     * Action effectu�e �la r�ception d'un �v�nement
     */
    public void actionPerformed(ActionEvent e) {
        
    	for (Joueur j : this.modele.joueurs()) {
    		modele.tour();
    	}
        modele.avance();
    }
}
