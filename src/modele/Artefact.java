package modele;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum Artefact {
	
	// Types d'Artefact
	
	air, eau, feu, ter;
	
	// Fonction de choix au hasard et ce qu'elle utilise.
	
	private static final List<Artefact> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
	private static final int SIZE = VALUES.size();
	private static final Random RANDOM = new Random();

	public static Artefact randomArtefact() {
		return VALUES.get(RANDOM.nextInt(SIZE));
	}
}