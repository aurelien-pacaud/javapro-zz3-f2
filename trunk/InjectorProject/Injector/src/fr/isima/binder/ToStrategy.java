package fr.isima.binder;

import fr.isima.annotation.Singleton;

/**
 * Classe traduisant la stratégie d'injection par classe Java.
 */
public class ToStrategy extends BindStrategy {

	private Class<?> implementation;
	
	public ToStrategy(Class<?> to) {
		
		implementation = to;
	}
	
	@Override
	public Object newInstance(Class<?> instance) {
		
		return value();
	}

	@Override
	public Object value() {
		
		try {			
			
			/* Test de la présence d'un binding en singleton. */
			if (isSingleton || implementation.isAnnotationPresent(Singleton.class)) {
				
				if (instance == null) {
					
					instance = implementation.newInstance();
				}
					
				return instance;
			}
			
			return implementation.newInstance();
			
		} catch (Exception e) {
			
		}
		
		return null;
	}
}
