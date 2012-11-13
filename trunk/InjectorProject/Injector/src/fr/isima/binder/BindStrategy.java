package fr.isima.binder;

import java.lang.reflect.Field;

public abstract class BindStrategy {
	
	protected boolean isSingleton = false;
	protected Object instance = null;
	
	public boolean apply(Field field) {
		
		return true;
	}
	
	public abstract Object value();

	public Object newInstance(Class<?> instance) {
		
		return null;
	}

	public void setSingleton(boolean asSingelton) {
		
		isSingleton = asSingelton;		
	}
}
