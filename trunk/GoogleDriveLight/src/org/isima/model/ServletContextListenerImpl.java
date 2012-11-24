package org.isima.model;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.isima.annotation.FileLister;
import org.isima.services.FileListerService;
import org.isima.services.FileOperationService;
import org.isima.services.IFileOperationService;
import org.isima.services.IFileService;
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
			
			injector.bind(IFileService.class).annotatedWith(FileLister.class).to(FileListerService.class);
			injector.bind(String.class).annotatedWith(InjectedValue.class).to("/home/aurelien/");
			injector.bind(IFileOperationService.class).to(FileOperationService.class);
			
		} catch (MultipleBindException e) {
		} catch (CannotCreateBindingException e) {
			e.printStackTrace();
		}
	}
}
