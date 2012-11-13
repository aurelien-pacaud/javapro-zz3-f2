package fr.isima.binder;

import fr.isima.annotation.Singleton;

public class ValueStrategy extends BindStrategy {

	private Object value;
	
	public ValueStrategy(Object to) {		
		
		value = to;
	}

	@Override
	public Object value() {
		
		if (isSingleton || value.getClass().isAnnotationPresent(Singleton.class)) {
			
			if (instance == null) {
				
				instance = value;				
			}
			
			return instance;
		}
			
		return value;		
	}
}
