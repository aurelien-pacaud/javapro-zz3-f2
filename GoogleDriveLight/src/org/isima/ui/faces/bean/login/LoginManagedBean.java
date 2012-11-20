package org.isima.ui.faces.bean.login;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import fr.isima.annotation.InjectedValue;
import fr.isima.exception.MultipleBindException;
import fr.isima.injector.Injector;

public class LoginManagedBean implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	private final String allowUsername = "admin";
	private final String allwPassword = "admin";
	private final String userHome = "/home/aurelien/Cours/";
	
	private String username;
	private String password;
	
	public String login() {
		
		if(allowUsername.equals(username) && allwPassword.equals(password)) {

			FacesContext context = FacesContext.getCurrentInstance();
			ServletContext servletContext = (ServletContext)context.getExternalContext().getContext();
			
			Injector injector = (Injector)servletContext.getAttribute("injector");
			try {
				injector.bind(String.class).annotatedWith(InjectedValue.class).to(userHome);
			} catch (MultipleBindException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return "loginSuccess";
		}
		
		FacesContext.getCurrentInstance().addMessage(null, 
				new FacesMessage("Not authorized !"));		
		
		return "loginFailed";
	}
	
	public String logout() {
		
		//FacesContext facesContext = FacesContext.getCurrentInstance();
		//HttpSession httpSession = (HttpSession)facesContext.getExternalContext().getSession(false);
		//httpSession.invalidate();
				
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
}
