package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import modele.*;
import vues.*;

/**
 * Un lien entre vue et modÃ¨le : les informations montrées Ã  l'utilisateur
 * reflÃ¨tent l'état du modÃ¨le et doivent Ãªtre maintenues Ã  jour.
 * 
 * Pour réaliser cette synchronisation, on peut suivre le schéma de conception
 * observateur/observé, dont le principe est le suivant :
 *  - Un observateur (en l'occurrence la vue) est lié Ã  un objet observé et se
 *    met Ã  jour pour refléter les changement de l'observé.
 *  - Un observé est lié Ã  un ensemble d'objets observateurs et les notifie de
 *    tout changement de son propre état.
 *
 * Java fournit une interface [Observer] (observateur) et une classe
 * [Observable] (observé) assurant cette jonction.
 * Voici une maniÃ¨re sommaire de les recoder.
 */

public class IleInterdite {
	
    public static void main(String[] args) {
    	
    	System.out.println("Bienvenue sur l'Île interdite !");
    	
    	System.out.println("Cette île a un long passé, avec notamment des artefacts correspondant aux 4 éléments : eau, terre, air et feu.");
    	System.out.println("Il faut les récupérer, puis vite revenir à l'héliport, avant que l'île ne sombre...");
    	System.out.println("Les chercheurs ont divisé la carte de l'île en zones pour faciliter la tâche.");
    	System.out.println("Une zone de l'île passe par 3 états : sèche(grise) -> inondée(bleue) -> submergée(cyan).");
    	System.out.println("A chaque 'tour de table', 3 zones couleront un peu plus.");
    	System.out.println("Mais tant qu'elles ne sont pas submergées, vous pouvez les assécher !");
    	System.out.println("Vous aurez droit à 3 actions chacun à chaque tour, et vos pouvoirs sont réduits aux zones collées autour de vous à droite, à gauche, en haut et en bas.");
    	System.out.println("Pour récupérer les artefacts, il faut avoir leurs clés, mais on ne sait pas où elles sont :");
    	System.out.println("vous les trouverez certainement en essayant de récupérer les artefacts.");
    	System.out.println("Vous êtes prêt ?");
    	
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.print("Veuillez indiquer le nombre de joueurs, puis appuyez sur [entrée] : ");
		
		Modele modele = null;
		
		boolean success = false;
		while (!success) {
	        try{
	            modele = new Modele(Integer.parseInt(br.readLine()));
	            success=true;
	        } catch (IndexOutOfBoundsException e) {
	            System.err.print("Nombre de joueur trop grand ! Donnez un autre nombre. ");
			}catch(NumberFormatException nfe){
	            System.err.print("Mais non ! Entrez un chiffre ! ");
	        } catch (IOException e1) {
				e1.printStackTrace();
	        }
		}
        Vue vue = new Vue(modele);
        System.out.println("Cliquez sur tour suivant !");
    }
}