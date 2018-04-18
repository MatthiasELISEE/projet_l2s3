import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Application avec interface graphique.
 * Thibaut Balabonski, Université Paris-Sud.
 * Matériel pédagogique lié au cours POGL, séance du 9 avril 2017.
 * 
 * Un principe directeur est la séparation stricte des deux parties suivantes :
 *  - Le coeur de l'application, appelé le modèle, où est fait l'essentiel
 *    du travail.
 *  - L'interface utilisateur, appelée la vue, qui à la fois montre des choses
 *    à l'utilisateur et lui fournit des moyens d'interagir.
 *
 * Notre cas d'étude : le jeu de la vie de Conway.
 * Une grille bidimensionnelle de dimensions finies est peuplée de cellules
 * pouvant être vivantes ou mortes. À chaque tour un nouvel état est calculé
 * pour chaque cellule en fonction de l'état de ses voisines immédiates.
 * Un bouton permet de passer au tour suivant (on dit aussi la génération
 * suivante).
 */

/**
 * Un lien entre vue et modèle : les informations montrées à l'utilisateur
 * reflètent l'état du modèle et doivent être maintenues à jour.
 * 
 * Pour réaliser cette synchronisation, on peut suivre le schéma de conception
 * observateur/observé, dont le principe est le suivant :
 *  - Un observateur (en l'occurrence la vue) est lié à un objet observé et se
 *    met à jour pour refléter les changement de l'observé.
 *  - Un observé est lié à un ensemble d'objets observateurs et les notifie de
 *    tout changement de son propre état.
 *
 * Java fournit une interface [Observer] (observateur) et une classe
 * [Observable] (observé) assurant cette jonction.
 * Voici une manière sommaire de les recoder.
 */

/**
 * Interface des objets observateurs.
 */ 
interface Observer {
    /**
     * Un observateur doit posséder une méthode [update] déclenchant la mise à
     * jour.
     */
    public void update();
    /**
     * La version officielle de Java possède des paramètres précisant le
     * changement qui a eu lieu.
     */
}

/**
 * Classe des objets pouvant être observés.
 */
abstract class Observable {
    /**
     * On a une liste [observers] d'observateurs, initialement vide, à laquelle
     * viennent s'inscrire les observateurs via la méthode [addObserver].
     */
    private ArrayList<Observer> observers;
    public Observable() {
	this.observers = new ArrayList<Observer>();
    }
    public void addObserver(Observer o) {
	observers.add(o);
    }

    /**
     * Lorsque l'état de l'objet observé change, il est convenu d'appeler la
     * méthode [notifyObservers] pour prévenir l'ensemble des observateurs
     * enregistrés.
     * On le fait ici concrètement en appelant la méthode [update] de chaque
     * observateur.
     */
    public void notifyObservers() {
	for(Observer o : observers) {
	    o.update();
	}
    }
}
/** Fin du schéma observateur/observé. */


/**
 * Nous allons commencer à construire notre application, en voici la classe
 * principale.
 */
public class Conway {
    /**
     * L'amorçage est fait en créant le modèle et la vue, par un simple appel
     * à chaque constructeur.
     * Ici, le modèle est créé indépendamment (il s'agit d'une partie autonome
     * de l'application), et la vue prend le modèle comme paramètre (son
     * objectif est de faire le lien entre modèle et utilisateur).
     */
    public static void main(String[] args) {
        Modele modele = new Modele();
        Vue vue = new Vue(modele);
    }
}
/** Fin de la classe principale. */

/**
 * Définition d'une classe pour les cellules.
 * Cette classe fait encore partie du modèle.
 */
class Cellule {
    /** On conserve un pointeur vers la classe principale du modèle. */
    private Modele modele;

    /** L'état d'une cellule est donné par un booléen. */
    protected boolean etat;
    /**
     * On stocke les coordonnées pour pouvoir les passer au modèle lors
     * de l'appel à [compteVoisines].
     */
    private final int x, y;
    public Cellule(Modele modele, int x, int y) {
        this.modele = modele;
        this.etat = false;
        this.x = x; this.y = y;
    }
    /**
     * Le passage à la génération suivante se fait en deux étapes :
     *  - D'abord on calcule pour chaque cellule ce que sera sont état à la
     *    génération suivante (méthode [evalue]). On stocke le résultat
     *    dans un attribut supplémentaire [prochainEtat].
     *  - Ensuite on met à jour l'ensemble des cellules (méthode [evolue]).
     * Objectif : éviter qu'une évolution immédiate d'une cellule pollue
     * la décision prise pour une cellule voisine.
     */
    private boolean prochainEtat;
    protected void evalue() {
        switch (this.modele.compteVoisines(x, y)) {
        case 2: prochainEtat=etat; break;
        case 3: prochainEtat=true; break;
        default: prochainEtat=false;
        }
    }
    protected void evolue() {
        etat = prochainEtat;
    }
    
    /** Un test à l'usage des autres classes (sera utilisé par la vue). */
    public boolean estVivante() {
        return etat;
    }
}    
/** Fin de la classe Cellule, et du modèle en général. */







/** Fin de la vue. */



