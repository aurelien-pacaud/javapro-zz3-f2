package org.isima.ui.faces.bean.login;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import org.isima.services.ILoginService;
import org.isima.singleton.GoogleDriveLightInjector;
import org.isima.ui.utils.MessageBundle;

import fr.isima.annotation.Inject;
import fr.isima.exception.MultipleBindException;
import fr.isima.exception.NotNullBindingException;
import fr.isima.injector.Injector;

public class LoginManagedBean implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	private String username;
	private String password;
	
	private boolean connected = false;
	
	@Inject
	private ILoginService loginService;
	
	@PostConstruct
	public void init() {
		
		/* Après la création du bean on inject ses attributs annotés. */
		Injector injector = GoogleDriveLightInjector.getInstance();		
		
		try {
			injector.inject(this);
		} catch (NotNullBindingException e) {
			e.printStackTrace();
		} catch (MultipleBindException e) {
			e.printStackTrace();
		}	
	}
	
	/***
	 * Effectue la verification du login afin d'autoriser ou non l'utilisateur à se connecter.
	 * @return Page vers laquelle l'utilisateur sera redirigé. (Utilisation des règles de navigation)
	 */
	public String login() {
		
		/* Si toutes les informations sont bonnes, on autorise la connexion.*/
		if(loginService.accept(username, password)) {
			
			connected = true;		
			return "loginSuccess";
		}
		
		connected = false;
		
		MessageBundle.displayErrorMsg(MessageBundle.getMessage("connectionNotAllowed"));	
		
		return "loginFailed";
	}
	
	public String logout() {
		
		/* L'utilisateur est déconnecté. */
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
