package org.isima.singleton;

import fr.isima.injector.Injector;

/**
 * Classe permettant de n'avoir qu'une seule instance de l'injector tout au long de l'application.
 */
public class GoogleDriveLightInjector {

	private static Injector instance = null;
	
	public static Injector getInstance() {
		
		if (instance == null)
			instance = new Injector();
		
		return instance;
	}
	
	private GoogleDriveLightInjector() {
		
	}
}
