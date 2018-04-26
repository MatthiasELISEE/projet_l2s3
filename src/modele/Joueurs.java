package modele;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.*;
import vues.*;
import java.util.Scanner; 
//Ceci importe toutes les classes du package java.util
import java.util.*;


public class Joueurs{
	private int nbjoueurs ;
	private boolean Yajoueur;
    private int CordJoueurX;
    private int CordJoueurY;
    private Cellule [][] cellules;
    
	public Joueurs(int x, int y) {
		
		this.CordJoueurX=x;
		this.CordJoueurY=y;
		this.Yajoueur=false;
		
	
}
	public void InitJoueur () {
		this.CordJoueurX= 5;
		this.CordJoueurY= 5;
		this.Yajoueur=true;
		
	}
	public int CordJX() {
		return this.CordJoueurX;
	}
	public int CordJY() {
		return this.CordJoueurY;
	}
	public boolean ExisteJoueur(int x , int y) {
		if(this.CordJoueurX==x && this.CordJoueurY==y) {
			return true;
		}
		else return false;
	}
	
	public void deplacement(Joueurs j,Cellule[][] cellules) {
		String str;
		Scanner sc = new Scanner(System.in);
		System.out.println("Veuillez saisir g pour Gauche, d pour droite, b pour bas, h pour haut :");
		str = sc.nextLine();;
		char carac = str.charAt(0);

		if(carac=='g' && j.CordJoueurX-1!=0) {
			cellules[j.CordJoueurX][j.CordJoueurY].YajoueurC=false;
			cellules[j.CordJoueurX-1][j.CordJoueurY].YajoueurC=true;
			j.CordJoueurX=j.CordJoueurX-1;
			System.out.println("tu es allée a gauche");
		}
		if(carac=='d' && j.CordJoueurX+1!=10) {
			cellules[j.CordJoueurX][j.CordJoueurY].YajoueurC=false;
			cellules[j.CordJoueurX+1][j.CordJoueurY].YajoueurC=true;
			j.CordJoueurX=j.CordJoueurX+1;
			System.out.println("tu es allée a droite");
		}
		if(carac=='h' && j.CordJoueurY-1!=0) {
			
			cellules[j.CordJoueurX][j.CordJoueurY].YajoueurC=false;
			cellules[j.CordJoueurX][j.CordJoueurY-1].YajoueurC=true;
			j.CordJoueurY=j.CordJoueurY-1;
			System.out.println("tu es allée en haut");
		}
		if(carac=='b' && j.CordJoueurY+1!=10) {
			cellules[j.CordJoueurX][j.CordJoueurY].YajoueurC=false;
			cellules[j.CordJoueurX][j.CordJoueurY+1].YajoueurC=true;
			
			j.CordJoueurY=j.CordJoueurY+1;
			System.out.println("tu es allée en bas");
		}
		
	/*	if(evt.getKeyCode()==evt.VK_LEFT) {
			
		}
		*/
	//	System.out.println("Vous avez saisi : " + str);
	
		/*	
			  /*public void keyPressed(KeyEvent evt){
	                if(evt.getKeyCode()==evt.VK_RIGHT) {
	                	
	                }
	                
	                	if(evt.getKeyCode()==evt.VK_LEFT)
	                		if(evt.getKeyCode()==evt.VK_DOWN)
	                		if(evt.getKeyCode()==evt.VK_UP)
			  
			  } //on contrôle qu'il ne soit pas trop à droite avant de le déplacer vers la droite
	                
	}
	*/
	
}
}
