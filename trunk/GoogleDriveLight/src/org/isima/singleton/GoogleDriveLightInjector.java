package org.isima.singleton;

import fr.isima.injector.Injector;

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
