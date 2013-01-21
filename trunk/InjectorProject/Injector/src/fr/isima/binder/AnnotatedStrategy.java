package fr.isima.binder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Classe implémentant la stratégie d'annotation.
 */
public class AnnotatedStrategy extends BindStrategy {

	private BindStrategy toType;
	private AnnotatedStrategy nextAnnotation = null;	
	private Class<? extends Annotation> annotation;
	
	public AnnotatedStrategy(Class<? extends Annotation> annotation) {

		this.annotation = annotation;
	}
	
	/**
	 * Méthode permettant, par l'API fluent, de chainer les annotatedWith.
	 * 
	 * @param annotation annotation que l'on veut chainer à la courante.
	 * @return le binding associé.
	 */
	public AnnotatedStrategy annotatedWith(Class<? extends Annotation> annotation) {
		
		if (nextAnnotation == null) {

			nextAnnotation = new AnnotatedStrategy(annotation);
		}
		else {
		
			nextAnnotation.annotatedWith(annotation);
		}
		
		return this;
	}
	
	public void to(Object to) {
		
		toType = new ValueStrategy(to);
	}
	
	public void to(Class<?> to) {
		
		toType = new ToStrategy(to);
	}

	/**
	 * Méthode permettant de vérifier si l'injection peut être fait sur le champ courant.
	 * 
	 * @param field champ dont on veut savoir s'il est injectable.
	 * @return true si oui faux sinon.
	 */
	@Override
	public boolean apply(Field field) {		
		
		Boolean ret = true;
		
		/* Si il y a chainage des annotations. */
		if (nextAnnotation != null)
			ret &= nextAnnotation.apply(field);

		return ret & field.isAnnotationPresent(annotation); 
	}

	@Override
	public Object value() {
		return toType.value();
	}
}
