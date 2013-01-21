package fr.isima.binder;

import fr.isima.annotation.Singleton;

/**
 * Classe traduisant la stratégie d'injection par valeur.
 */
public class ValueStrategy extends BindStrategy {

	private Object value;
	
	public ValueStrategy(Object to) {		
		
		value = to;
	}

	@Override
	public Object value() {
		
		/* Test de la présence d'un bind en singleton. */
		if (isSingleton || value.getClass().isAnnotationPresent(Singleton.class)) {
			
			if (instance == null) {
				
				instance = value;				
			}
			
			return instance;
		}
			
		return value;		
	}
}
