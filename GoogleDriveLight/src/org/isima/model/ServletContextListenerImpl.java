package org.isima.model;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.isima.annotation.FileLister;
import org.isima.services.FileListerService;
import org.isima.services.IFileService;

import fr.isima.exception.MultipleBindException;
import fr.isima.injector.Injector;

public class ServletContextListenerImpl implements ServletContextListener {


	@Override
	public void contextDestroyed(ServletContextEvent contextEvent) {
		System.out.println("debug : context destroyed");
	}

	@Override
	public void contextInitialized(ServletContextEvent contextEvent) {
		
		System.out.println("debug : context initialized");
		
		Injector injector = new Injector();
			
		try {
			
			injector.bind(IFileService.class).annotatedWith(FileLister.class).to(FileListerService.class);
			
		} catch (MultipleBindException e) {
			e.printStackTrace();
		}
        /* On ajoute l'injecteur au contexte */
		contextEvent.getServletContext().setAttribute("injector", injector);
	}
}
