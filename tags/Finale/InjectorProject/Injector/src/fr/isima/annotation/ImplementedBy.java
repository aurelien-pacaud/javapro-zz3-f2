package fr.isima.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/***
 * Annotation utilisée afin d'annoter directement une classe pour l'injection.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ImplementedBy {

	Class<?> value();
}
