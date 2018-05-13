package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import modele.*;
import vues.*;

/**
 * Un lien entre vue et modèle : les informations montr�es à l'utilisateur
 * reflètent l'�tat du modèle et doivent être maintenues à jour.
 * 
 * Pour r�aliser cette synchronisation, on peut suivre le sch�ma de conception
 * observateur/observ�, dont le principe est le suivant :
 *  - Un observateur (en l'occurrence la vue) est li� à un objet observ� et se
 *    met à jour pour refl�ter les changement de l'observ�.
 *  - Un observ� est li� à un ensemble d'objets observateurs et les notifie de
 *    tout changement de son propre �tat.
 *
 * Java fournit une interface [Observer] (observateur) et une classe
 * [Observable] (observ�) assurant cette jonction.
 * Voici une manière sommaire de les recoder.
 */

public class IleInterdite {
	
    public static void main(String[] args) {
    	
    	System.out.println("Bienvenue sur l'�le interdite !");
    	
    	System.out.println("Cette �le a un long pass�, avec notamment des artefacts correspondant aux 4 �l�ments : eau, terre, air et feu.");
    	System.out.println("Il faut les r�cup�rer, puis vite revenir � l'h�liport, avant que l'�le ne sombre...");
    	System.out.println("Les chercheurs ont divis� la carte de l'�le en zones pour faciliter la t�che.");
    	System.out.println("Une zone de l'�le passe par 3 �tats : s�che(grise) -> inond�e(bleue) -> submerg�e(cyan).");
    	System.out.println("A chaque 'tour de table', 3 zones couleront un peu plus.");
    	System.out.println("Mais tant qu'elles ne sont pas submerg�es, vous pouvez les ass�cher !");
    	System.out.println("Vous aurez droit � 3 actions chacun � chaque tour, et vos pouvoirs sont r�duits aux zones coll�es autour de vous � droite, � gauche, en haut et en bas.");
    	System.out.println("Pour r�cup�rer les artefacts, il faut avoir leurs cl�s, mais on ne sait pas o� elles sont :");
    	System.out.println("vous les trouverez certainement en essayant de r�cup�rer les artefacts.");
    	System.out.println("Vous �tes pr�t ?");
    	
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.print("Veuillez indiquer le nombre de joueurs, puis appuyez sur [entr�e] : ");
		
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