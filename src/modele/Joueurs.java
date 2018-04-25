package modele;
import java.awt.FlowLayout;

import javax.swing.JFrame;

import vues.*;


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
		this.CordJoueurX= 1;
		this.CordJoueurY= 1;
		this.Yajoueur=true;
		
	}
	public boolean ExisteJoueur(int x , int y) {
		if(this.CordJoueurX==x && this.CordJoueurY==y) {
			return true;
		}
		else return false;
	}
	
	
}
