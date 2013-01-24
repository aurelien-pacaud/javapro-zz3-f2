package org.isima.model;

import java.io.File;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.isima.services.FileListerService;
import org.isima.services.IFileService;
import org.isima.services.ILoginService;
import org.isima.services.MockLoginService;
import org.isima.singleton.GoogleDriveLightInjector;

import fr.isima.annotation.InjectedValue;
import fr.isima.exception.CannotCreateBindingException;
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
		
		Injector injector = GoogleDriveLightInjector.getInstance();
			
		try {
           
			/* TODO A modifier! Création d'un répertoire temporaire pour le drive de l'user. */
			String folder = String.format("%s/%s", System.getProperty("java.io.tmpdir"), "Drive");
			File file = new File(folder);
			
			if (!file.exists())
				file.mkdir();
			
			/* Création des régles de bind pour l'injection des services. */
			injector.bind(IFileService.class).to(FileListerService.class);
			injector.bind(ILoginService.class).to(MockLoginService.class);
			injector.bind(String.class).annotatedWith(InjectedValue.class).to(folder);
			
		} catch (MultipleBindException e) {
			e.printStackTrace();
		} catch (CannotCreateBindingException e) {
			e.printStackTrace();
		}
	}
}
