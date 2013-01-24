package org.isima.ui.utils;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/***
 * 
 * Classe permettant de récupérer les messages disponibles dans le bundle pour l'i18n. *
 * 
 */
public class MessageBundle {

	/***
	 * Permet d'obtenir un message pour l'i18n depuis sa clé.
	 * 
	 * @param key clé dont nous voulons la valeur associée
	 * @return Message associé à la clé passée en paramètre depuis les fichiers d'i18n.
	 */
	public static String getMessage(String key) {
		
		/* Récupération des informations sur la locale et le bundle associé à l'application. */
		FacesContext facesContext = FacesContext.getCurrentInstance();
		String messageBundleName = facesContext.getApplication().getMessageBundle();
		Locale locale = facesContext.getViewRoot().getLocale();
		ResourceBundle bundle = ResourceBundle.getBundle(messageBundleName, locale);
		
		return bundle.getString(key);
	}
	
	/**
	 * Méthode permettant d'afficher un message d'information.
	 * 
	 * @param content Message a afficher en information
	 * @return le message a afficher.
	 */
	public static FacesMessage displayInformationMsg(String content) {
		
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, MessageBundle.getMessage("successful"), content);
		FacesContext.getCurrentInstance().addMessage(null, msg);
		
		return msg;
	}
	
	/**
	 * Méthode permettant d'afficher un message d'erreur.
	 * 
	 * @param content Message a afficher en erreur.
	 * @return le message a afficher.
	 */
	public static FacesMessage displayErrorMsg(String content) {
		
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, MessageBundle.getMessage("error"), content);
		FacesContext.getCurrentInstance().addMessage(null, msg);
		
		return msg;
	}
}
