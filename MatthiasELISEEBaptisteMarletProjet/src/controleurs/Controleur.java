package controleurs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import modele.*;

/**
 * Classe pour notre contrôleur rudimentaire.
 */
public class Controleur implements ActionListener {
    Modele modele;
    public Controleur(Modele modele) { this.modele = modele;}
    /**
     * Action effectuée à la réception d'un évènement
     */
    public void actionPerformed(ActionEvent e) {
    	for (Joueur j : this.modele.joueurs()) {
    		modele.tour();
    	}
        modele.avance();
    }
}
