package fr.isima.binder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class AnnotatedStrategy extends BindStrategy {

	private BindStrategy toType;
	private AnnotatedStrategy nextAnnotation = null;	
	private Class<? extends Annotation> annotation;
	
	public AnnotatedStrategy(Class<? extends Annotation> annotation) {

		this.annotation = annotation;
	}
	
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

	@Override
	public boolean apply(Field field) {		
		
		Boolean ret = true;
		
		if (nextAnnotation != null)
			ret &= nextAnnotation.apply(field);

		return ret & field.isAnnotationPresent(annotation); 
	}

	@Override
	public Object value() {
		return toType.value();
	}
}
