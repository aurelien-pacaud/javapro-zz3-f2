package fr.isima.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annation permettant de faire une injection en singleton afin de n'avoir
 * qu'une et une seule instance de la classe que l'on veut injecter.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Singleton {

}
