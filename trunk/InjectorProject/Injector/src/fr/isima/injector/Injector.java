package fr.isima.injector;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.isima.annotation.ImplementedBy;
import fr.isima.annotation.Inject;
import fr.isima.binder.Bind;
import fr.isima.exception.MultipleBindException;
import fr.isima.exception.NotNullBindingException;

public class Injector {	
	
	private Map<String, List<Bind>> bindings;
	
	public Injector() {
		
		bindings = new HashMap<String, List<Bind>>();		
	}
	
	public Object newInstance(Class<?> instance) throws NotNullBindingException, MultipleBindException {
		
		Object obj = null;
		
		/* Si la classe est annotée avec ImplementedBy. */
		if (instance.isAnnotationPresent(ImplementedBy.class)) {
			
			try {
				obj = instance.getAnnotation(ImplementedBy.class).value().newInstance();
			} catch (Exception e) {
				
			}
		}
		
		if (bindings.containsKey(instance.getName())) {
			
			List<Bind> binders = bindings.get(instance.getName());
			
			/* Pour tous les couples (interface, annotation, implementation). */
			for (Bind binder : binders) {
				
				Object tempInstance = binder.newInstance(instance);
				
				if (tempInstance != null) {
					
					obj = tempInstance;
					inject(obj);
				}				
			}				
		}
		
		if (obj == null) {
			
			throw new NotNullBindingException("No binding found for this instance");			
		}
		
		return obj;
	}
	
	public Bind bind(Class<?> from) throws MultipleBindException {		
		
		Bind binder = new Bind();
		
		if (bindings.containsKey(from.getName())) {
			
			bindings.get(from.getName()).add(binder);
		}
		else {
			
			List<Bind> binders = new ArrayList<Bind>();
			
			binders.add(binder);			
			
			bindings.put(from.getName(), binders);	
		}	
		
		return binder;
	}	
	
	
	private void setField(Object obj, Field field, Object instance) {
		
		field.setAccessible(true);
		
		try {
			field.set(obj, instance);
		} catch (Exception e) {
		}
		
		field.setAccessible(false);	
	}
	
	public Object inject(Object obj) throws NotNullBindingException, MultipleBindException {
			
		/* Recuperation des attributs de notre objet passe en parametre. */
		for (Field field : obj.getClass().getDeclaredFields()) {
			
			/* Si l'attribut est annoté avec @Inject nous le traitons pour l'injecté. */
			if (field.isAnnotationPresent(Inject.class)) {
			
				if (field.getType().isAnnotationPresent(ImplementedBy.class)) {
					
					try {
						setField(obj, field, field.getType().getAnnotation(ImplementedBy.class).value().newInstance());
					} catch (Exception e) {
					}
				}
				/* Si un binding existe pour cette interface. */
				if (bindings.containsKey(field.getType().getName())) {
						
					boolean change = false;
					List<Bind> binders = bindings.get(field.getType().getName());
					
					/* Pour tous les couples (interface, annotation, implementation). */
					for (Bind binder : binders) {
						
						boolean applicable = binder.apply(field);
						
						if (applicable == true && change == false) {
	
							setField(obj, field, binder.value());	
							inject(field);
							change = true;
						}
						else if (applicable == true && change == true){
							
							throw new MultipleBindException("test");
						}
					}				
				}
			}
		}	
		
		return obj;
	}
}
