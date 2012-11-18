package org.isima.model;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ServletContextListenerImpl implements ServletContextListener
{
	ServletContext context;

	@Override
	public void contextDestroyed(ServletContextEvent contextEvent) {
		System.out.println("debug : context destroyed");
	}

	@Override
	public void contextInitialized(ServletContextEvent contextEvent) {
		System.out.println("debug : context initialized");
		context = contextEvent.getServletContext();
		
		/* On ajoute le repertoire de l'user au contexte */
		context.setAttribute("userHome", "/home");
	}

}
