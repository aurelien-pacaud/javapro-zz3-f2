package org.isima.services;

import java.io.Serializable;

public class MockLoginService implements ILoginService, Serializable {
	
	private static final long serialVersionUID = 1L;

	private final String allowUsername = "admin";
	private final String allwPassword = "admin";
	
	@Override
	public boolean accept(String login, String password) {
		
		/* Si toutes les informations sont bonnes, on autorise la connexion.*/
		return allowUsername.equals(login) && allwPassword.equals(password);
	}

}
