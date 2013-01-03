package org.isima.ui.faces.bean.login;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.isima.ui.utils.MessageBundle;

public class LoginManagedBean implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	private final String allowUsername = "admin";
	private final String allwPassword = "admin";
	
	private String username;
	private String password;
	
	private boolean connected = false;
	
	/***
	 * Effectue la verification du login afin d'autoriser ou non l'utilisateur à se connecter.
	 * @return Page vers laquelle l'utilisateur sera redirigé. (Utilisation des règles de navigation)
	 */
	public String login() {
		
		/* Si toutes les informations sont bonnes, on autorise la connexion.*/
		if(allowUsername.equals(username) && allwPassword.equals(password)) {
			
			connected = true;
			return "loginSuccess";
		}
		
		connected = false;
		
		FacesContext.getCurrentInstance().addMessage(null, 
				new FacesMessage(MessageBundle.getMessage("connectionNotAllowed")));		
		
		return "loginFailed";
	}
	
	public String logout() {
		
		connected = false;
		return "logoutSuccess";
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean isConnected() {
		return connected;
	}
}
