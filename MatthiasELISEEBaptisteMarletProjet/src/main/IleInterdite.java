package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import modele.*;
import vues.*;


public class IleInterdite {
	
    public static void main(String[] args) {
    	
    	System.out.println("Bienvenue sur l'éle interdite !");
    	
    	System.out.println("Cette île a un long passé, avec notamment des artefacts correspondant aux 4 éléments : eau, terre, air et feu.");
    	System.out.println("Il faut les récupérer, puis vite revenir à l'héliport, avant que l'île ne sombre...");
    	System.out.println("Les chercheurs ont divisé la carte de l'île en zones pour faciliter la tâche.");
    	System.out.println("Une zone de l'île passe par 3 états : séche(grise) -> inondée(bleue) -> submergée(cyan).");
    	System.out.println("A chaque 'tour de table', 3 zones couleront un peu plus.");
    	System.out.println("Mais tant qu'elles ne sont pas submergées, vous pouvez les assécher !");
    	System.out.println("Vous aurez droit à 3 actions chacun à chaque tour, et vos pouvoirs sont réduits aux zones collées autour de vous à droite, à gauche, en haut et en bas.");
    	System.out.println("Pour récupérer les artefacts, il faut avoir leurs clés, mais on ne sait pas où elles sont :");
    	System.out.println("vous les trouverez certainement en essayant de récupérer les artefacts.");
    	System.out.println("Vous étes prêt ?");
    	
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