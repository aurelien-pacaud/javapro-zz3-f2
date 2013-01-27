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
	
	/**
	 * Map contenant les couples (interface, annotation, implementation)
	 */
	private Map<String, List<Bind>> bindings;
	
	public Injector() {
		
		bindings = new HashMap<String, List<Bind>>();		
	}
	
	/**
	 * Méthode permettant de créer une nouvelle instance d'une classe Java.
	 * 
	 * @param instance classe Java dont on veut une nouvelle instance injectée.
	 * @return la nouvelle instance créée.
	 * @throws NotNullBindingException
	 * @throws MultipleBindException
	 */
	public Object newInstance(Class<?> instance) throws NotNullBindingException, MultipleBindException {
		
		Object obj = null;
		
		/* Si la classe est annotée avec ImplementedBy. */
		if (instance.isAnnotationPresent(ImplementedBy.class)) {
			
			try {
				obj = instance.getAnnotation(ImplementedBy.class).value().newInstance();
			} catch (Exception e) {
				
			}
		}
		
		/* Si la classe est enregistrée pour un binding. */
		if (bindings.containsKey(instance.getName())) {
			
			/* Récupération des bind associés. */
			List<Bind> binders = bindings.get(instance.getName());
			
			/* Pour tous les bind. */
			for (Bind binder : binders) {
				
				/* Création de l'instance.*/
				Object tempInstance = binder.newInstance(instance);
				
				if (tempInstance != null) {
					
					obj = tempInstance;
					/* Injection des champs de l'instance. */
					inject(obj);
				}				
			}				
		}
		
		if (obj == null) {
			
			throw new NotNullBindingException("No binding found for this Java class.");			
		}
		
		return obj;
	}
	
	/**
	 * Méthode permettant de bind une interface par une de ses implémentations.
	 * 
	 * @param from interface que l'on veut injecter.
	 * @return un binding.
	 * @throws MultipleBindException
	 */
	public Bind bind(Class<?> from) throws MultipleBindException {		
		
		Bind binder = new Bind();
		
		/* Si l'interface est déjà enregistrée, on ajoute un nouveau bind. */
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
	
	/**
	 * Methode permettant de fixer la valeur d'un champs d'une instance donnée.
	 * 
	 * @param obj Valeur que l'on veut donner au champs.
	 * @param field Champs de la classe que l'on veut injecter.
	 * @param instance Instance de la classe dont on veut injecter le champs.
	 */
	private void setField(Object obj, Field field, Object instance) {
		
		field.setAccessible(true);
		
		try {
			field.set(obj, instance);
		} catch (Exception e) {
		}
		
		field.setAccessible(false);	
	}
	
	/**
	 * Méthode permettant d'injecter une instance déjà existante.
	 * 
	 * @param obj Objet que l'on veut injecter.
	 * @return L'object injecté.
	 * @throws NotNullBindingException
	 * @throws MultipleBindException
	 */
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
						
						/* Si le champ peut être injecté et qu'il ne l'a pas déjà été. */
						if (applicable == true && change == false) {
	
							setField(obj, field, binder.value());	
							inject(field);
							change = true;
						}
						else if (applicable == true && change == true){
							
							throw new MultipleBindException("Multiple bind detected.");
						}
					}				
				}
			}
		}	
		
		return obj;
	}
}
