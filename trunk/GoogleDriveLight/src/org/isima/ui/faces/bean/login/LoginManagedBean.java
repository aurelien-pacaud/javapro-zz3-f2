package org.isima.ui.faces.bean.login;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class LoginManagedBean {
	
	private final String allowUsername = "admin";
	private final String allwPassword = "admin";
	private final String userHome = "/home";
	
	private String username;
	private String password;
	
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
	
	public String getUserHome() {
		return userHome;
	}	
	
	public String login(){
		
		if(allowUsername.equals(username) && allwPassword.equals(password)) {

			return "loginSuccess";
		}
		
		FacesContext.getCurrentInstance().addMessage(null, 
				new FacesMessage("Not authorized !"));		
		
		return "loginFailed";
	}
	
	public String logout() {
		
		FacesContext facesContext = FacesContext.getCurrentInstance();
		//HttpSession httpSession = (HttpSession)facesContext.getExternalContext().getSession(false);
		//httpSession.invalidate();
				
		return "logoutSuccess";
	}
}
