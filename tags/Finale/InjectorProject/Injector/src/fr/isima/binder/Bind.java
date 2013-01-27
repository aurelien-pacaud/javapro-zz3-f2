package fr.isima.binder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import fr.isima.exception.CannotCreateBindingException;

/**
 * Cette classe permet de définir le stratégie de bind retenue.
 * Elle dispose d'une API fluent pour une meilleur compréhension du code.
 */
public class Bind {

	private BindStrategy bindingType = null;
	
	/**
	 * Méthode permettant d'injecter une valeur.
	 * 
	 * @param to objet dont on veut copier la valeur pour l'injection.
	 * @return le binding associé.
	 */
	public Bind to(Object to) {
	
		bindingType = new ValueStrategy(to);
		
		return this;
	}
	
	/**
	 * Méthode permettant d'injecter un champ par une implémentation concrète de son interface.
	 * 
	 * @param to classe que l'on veut injecter.
	 * @return le binding associé.
	 * @throws CannotCreateBindingException si la classe passée en paramètre est une interface.
	 */
	public Bind to(Class<?> to) throws CannotCreateBindingException {
		
		if (to.isInterface())
			throw new CannotCreateBindingException(to.getName() + "is an interface");
		
		bindingType = new ToStrategy(to);
		
		return this;
	}
	
	/**
	 * Méthode permettant de définir que l'injection se fera en singleton.
	 */
	public void asSingleton() {
		
		bindingType.setSingleton(true);
	}
	
	/**
	 * Méthode permettant de faire de l'injection par annotation des champs.
	 * 
	 * @param annotation annotation associé au champ que l'on veut injecter. 
	 * @return le binding associé.
	 */
	public AnnotatedStrategy annotatedWith(Class<? extends Annotation> annotation) {
		
		bindingType = new AnnotatedStrategy(annotation);
		
		return (AnnotatedStrategy) bindingType;
	}	
	
	/**
	 * Méthode permettant de tester si le binding est applicable sur un champ donné.
	 * 
	 * @param field champ que l'on veut injecter par le binding courant.
	 * @return True si le binding est applicable. Faux sinon.
	 */
	public Boolean apply(Field field) {
		
		return bindingType.apply(field);
	}

	/**
	 * Méthode permettant de récupérer l'instance du champ que l'on est en train d'injecter.
	 * 
	 * @return nouvelle instance correspondant au bind courant.
	 */
	public Object value() {
		return bindingType.value();
	}
	
	public Object newInstance(Class<?> instance) {
		
		return bindingType.newInstance(instance);
	}
}
