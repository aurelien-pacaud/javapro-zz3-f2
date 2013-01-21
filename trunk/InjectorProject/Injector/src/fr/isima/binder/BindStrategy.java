package fr.isima.binder;

import java.lang.reflect.Field;

/**
 * Classe abstraite permettant de définir l'interface des stratégies.
 */
public abstract class BindStrategy {
	
	protected boolean isSingleton = false;
	protected Object instance = null;
	
	/**
	 * Méthode permettant de tester si le binding est applicable sur un champ donné.
	 * 
	 * @param field champ que l'on veut injecter par le binding courant.
	 * @return True si le binding est applicable. Faux sinon.
	 */
	public boolean apply(Field field) {
		
		return true;
	}
	
	/**
	 * Méthode permettant de récupérer l'instance du champ injecter.
	 * 
	 * @return une instance correspondant au binding courant.
	 */
	public abstract Object value();

	/**
	 * Méthode permettant de créer une instance d'une classe Java tout en l'injectant.
	 * 
	 * @param instance classe Java que dont on veut une instance injectée.
	 * @return null.
	 */
	public Object newInstance(Class<?> instance) {
		
		return null;
	}

	/**
	 * Méthode permettant de dire si l'injection de se binding est faite en singleton.
	 */
	public void setSingleton(boolean asSingelton) {
		
		isSingleton = asSingelton;		
	}
}
