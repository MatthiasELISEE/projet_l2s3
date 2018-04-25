package modele;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CelluleTest {
	
	// Test de la cellule
	
	@Test
	void coulerTest() {
		Cellule c = new Cellule(new Modele(),2,3);
		assertEquals(2,c.etat);
		assertEquals(true,c.couler());
		assertEquals(1,c.prochainEtat);
		assertEquals(2,c.etat);
		assertEquals(false,c.estCoulee());
		c.evolue();
		assertEquals(1,c.prochainEtat);
		assertEquals(1,c.etat);
		assertEquals(true,c.estCoulee());
		
		assertEquals(true,c.couler());
		c.evolue();
		assertEquals(0,c.etat);
		assertEquals(0,c.prochainEtat);
		assertEquals(true,c.estCoulee());
		
		//fail("Not yet implemented");
	}
	
	@Test
	void ajoutJoueurTest() {
		Cellule c = new Cellule(new Modele(),2,3);
		assertEquals(false,c.YajoueurC);
		c.ajoutJoueur();
		assertEquals(true,c.YajoueurC);
		assertEquals(c.x(),c.joueur().x());
		assertEquals(c.y(),c.joueur().y());
	}
}
