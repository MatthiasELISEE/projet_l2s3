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
    /**
     * L'amorÃ§age est fait en créant le modÃ¨le et la vue, par un simple appel
     * Ã  chaque constructeur.
     * Ici, le modÃ¨le est créé indépendamment (il s'agit d'une partie autonome
     * de l'application), et la vue prend le modÃ¨le comme paramÃ¨tre (son
     * objectif est de faire le lien entre modÃ¨le et utilisateur).
     */
    public static void main(String[] args) {
    	
    	System.out.println("Bienvenue sur l'Île interdite !");
    	
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