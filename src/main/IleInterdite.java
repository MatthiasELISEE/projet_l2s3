package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import modele.*;
import vues.*;

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

public class IleInterdite {
    /**
     * L'amorçage est fait en créant le modèle et la vue, par un simple appel
     * à chaque constructeur.
     * Ici, le modèle est créé indépendamment (il s'agit d'une partie autonome
     * de l'application), et la vue prend le modèle comme paramètre (son
     * objectif est de faire le lien entre modèle et utilisateur).
     */
    public static void main(String[] args) {
    	
    	System.out.println("Bienvenue sur l'�le interdite !");
    	
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.print("Nombre de joueurs ? ");
		
		Modele modele = null;
		
		boolean success = false;
		while (!success) {
	        try{
	            modele = new Modele(Integer.parseInt(br.readLine()));
	            success=true;
	        } catch (IndexOutOfBoundsException e) {
	            System.err.print("Nombre de joueur trop grand !");
			}catch(NumberFormatException nfe){
	            System.err.print("Mais non ! Entrez un chiffre !");
	        } catch (IOException e1) {
				e1.printStackTrace();
	        }
		}
        Vue vue = new Vue(modele);
        System.out.println("Cliquez sur tour suivant !");
    }
}