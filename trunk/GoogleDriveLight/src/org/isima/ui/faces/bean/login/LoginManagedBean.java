package org.isima.ui.faces.bean.login;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class LoginManagedBean implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	private final String allowUsername = "admin";
	private final String allwPassword = "admin";
	
	private String username;
	private String password;
	
	public String login() {
		
		if(allowUsername.equals(username) && allwPassword.equals(password)) {
			
			return "loginSuccess";
		}
		
		FacesContext.getCurrentInstance().addMessage(null, 
				new FacesMessage("Not authorized !"));		
		
		return "loginFailed";
	}
	
	public String logout() {
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
