
package Test;

import static org.junit.Assert.*;
import org.junit.*;
import org.junit.Test;
import java.util.List;
import java.util.Arrays;
import java.util.LinkedList;
import modele.Joueur;
import modele.Modele;
import modele.*;
public class Test_Joueur {
Joueur j1;
Modele m;
Cellule c;

	@Before
	public void Init() {
		m = new Modele();
		j1 = new Joueur(m,0,0);
		c = new Cellule(m,2,2);
		
		
	}
	
	// Tests du constructeur
    @Test
    public void JoueurZero() {
    	
        Joueur j2 = new Joueur(m,0, 0);
        assertEquals(j1.getX(),j2.getX());
        assertEquals(j1.getY(),j2.getY());
        
    }
    @Test
    public void TestAssecher() {
    	Joueur j2 = new Joueur(m,0, 0);
    	c.VideCellule(c);
    	assertEquals(j1.assecher(0, 0),j2.assecher(0,0));
    	assertEquals(j1.assecher(0, 0),c.estSeche());
    	
    }
    @Test
    public void TestAction() {
    	Joueur j2 = new Joueur(m,0, 0);
    	assertEquals(j2.faitAction("dd"),true);
    }
    
}
