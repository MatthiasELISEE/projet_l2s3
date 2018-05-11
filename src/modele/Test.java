
package modele;

import static org.junit.Assert.*;
import org.junit.*;
import java.util.Arrays;
import java.util.LinkedList;

class Test_Joueur {
	Joueur j1;
	Modele m;
	Cellule c;
	static LinkedList<String> noms = new LinkedList<>(Arrays.asList("Hall", "Brett", "Alex", "Fynn", "Jude", "Dave"));
	
	@Test
    public void JoueurZero() {
    	
        Joueur j2 = new Joueur(m,0, 0);
        assertEquals(j1.getX(),j2.getX());
        assertEquals(j1.getY(),j2.getY());
        
    }
    @Test
    public void TestAssecher() {
    	Joueur j2 = new Joueur(m,0, 0);
    	c.assecher();
    	assertEquals(j1.assecher(0, 0),j2.assecher(0,0));
    	assertEquals(j1.assecher(0, 0),c.estSeche());
    }
    @Test
    public void TestAction() {
    	Joueur j2 = new Joueur(m,0, 0);
    	assertEquals(j2.faitAction("dd"),true);
    }
    
}
