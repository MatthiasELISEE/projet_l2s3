package modele;

import java.awt.FlowLayout;

import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.*;
import vues.*;
import java.util.Scanner;
//Ceci importe toutes les classes du package java.util
import java.util.*;

public class Joueur {
	private int x;
	private int y;
	private Modele modele;
	
	public Joueur(Modele modele, int x, int y) {
		this.x = x;
		this.y = y;
		this.modele = modele;
	}
	
	public Joueur() {
		InitJoueur();
	}

	public void InitJoueur() {
		this.x = 5;
		this.y = 5;
	}

	public int x() {
		return this.x;
	}

	public int y() {
		return this.y;
	}

	public boolean ExisteJoueur(int x, int y) {
		if (this.x == x && this.y == y) {
			return true;
		} else
			return false;
	}
	
	public void faitAction(char carac) {
		Cellule[][] cellules = this.modele.cellules;
		
		// TODO : il faut qu'on puisse choisir autre chose que les déplacements 
		
		if (carac == 'g' && this.x - 1 != 0) {
			cellules[this.x][this.y].retirerJoueur(this);
			cellules[this.x - 1][this.y].ajouterJoueur(this);
			this.x = this.x - 1;
			System.out.println("tu es all�e a gauche");
		}
		if (carac == 'd' && this.x + 1 != 10) {
			cellules[this.x][this.y].retirerJoueur(this);
			cellules[this.x + 1][this.y].ajouterJoueur(this);
			this.x = this.x + 1;
			System.out.println("tu es all�e a droite");
		}
		if (carac == 'h' && this.y - 1 != 0) {

			cellules[this.x][this.y].retirerJoueur(this);;
			cellules[this.x][this.y - 1].ajouterJoueur(this);
			this.y = this.y - 1;
			System.out.println("tu es all�e en haut");
		}
		if (carac == 'b' && this.y + 1 != 10) {
			cellules[this.x][this.y].retirerJoueur(this);
			cellules[this.x][this.y + 1].ajouterJoueur(this);

			this.y = this.y + 1;
			System.out.println("tu es all�e en bas");
		}
	}
	
	
	public void demandeAction() {
		String str;
		Scanner sc = new Scanner(System.in);
		//System.out.println("Veuillez saisir g pour Gauche, d pour droite, b pour bas, h pour haut :");
		System.out.println("déplacements : (g)auche, (d)roite, (b)as, (h)aut :");
		str = sc.nextLine();
		char carac = str.charAt(0);
		
		this.faitAction(carac);
		
		
		
		/*
		 * if(evt.getKeyCode()==evt.VK_LEFT) {
		 * 
		 * }
		 */
		// System.out.println("Vous avez saisi : " + str);

		/*
		 * /*public void keyPressed(KeyEvent evt){ if(evt.getKeyCode()==evt.VK_RIGHT) {
		 * 
		 * }
		 * 
		 * if(evt.getKeyCode()==evt.VK_LEFT) if(evt.getKeyCode()==evt.VK_DOWN)
		 * if(evt.getKeyCode()==evt.VK_UP)
		 * 
		 * } //on contr�le qu'il ne soit pas trop � droite avant de le d�placer vers la
		 * droite
		 * 
		 * }
		 */

	}
}
