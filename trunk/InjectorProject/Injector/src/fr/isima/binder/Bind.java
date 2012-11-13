package fr.isima.binder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import fr.isima.exception.CannotCreateBindingException;

public class Bind {

	private BindStrategy bindingType = null;
	
	public Bind to(Object to) {
	
		bindingType = new ValueStrategy(to);
		
		return this;
	}
	
	public Bind to(Class<?> to) throws CannotCreateBindingException {
		
		if (to.isInterface())
			throw new CannotCreateBindingException(to.getName() + "is an interface");
		
		bindingType = new ToStrategy(to);
		
		return this;
	}
	
	public void asSingleton() {
		
		bindingType.setSingleton(true);
	}
	
	public AnnotatedStrategy annotatedWith(Class<? extends Annotation> annotation) {
		
		bindingType = new AnnotatedStrategy(annotation);
		
		return (AnnotatedStrategy) bindingType;
	}	
	
	public Boolean apply(Field field) {
		
		return bindingType.apply(field);
	}

	public Object value() {
		return bindingType.value();
	}
	
	public Object newInstance(Class<?> instance) {
		
		return bindingType.newInstance(instance);
	}
}
