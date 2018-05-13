package main;

import java.util.ArrayList;

/**
 * Classe des objets pouvant être observ�s.
 */
public abstract class Observable {
	/**
	 * On a une liste [observers] d'observateurs, initialement vide, à laquelle
	 * viennent s'inscrire les observateurs via la m�thode [addObserver].
	 */
	private ArrayList<Observer> observers;

	public Observable() {
		this.observers = new ArrayList<Observer>();
	}

	public void addObserver(Observer o) {
		observers.add(o);
	}

	/**
	 * Lorsque l'�tat de l'objet observ� change, il est convenu d'appeler la
	 * m�thode [notifyObservers] pour pr�venir l'ensemble des observateurs
	 * enregistr�s. On le fait ici concrètement en appelant la m�thode [update]
	 * de chaque observateur.
	 */
	public void notifyObservers() {
		for (Observer o : observers) {
			o.update();
		}
	}
}
