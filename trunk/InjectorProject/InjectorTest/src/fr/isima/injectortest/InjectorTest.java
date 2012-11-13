package fr.isima.injectortest;

import junit.framework.Assert;

import org.junit.Test;

import fr.isima.testClasses.DelegateIsiLogger;
import fr.isima.testClasses.FileIsiLogger;
import fr.isima.testClasses.MultipleIsiLogger;
import fr.isima.testClasses.SysoutIsiLogger;
import fr.isima.testClasses.IsiLogger;

import fr.isima.annotation.Inject;
import fr.isima.annotation.InjectedValue;
import fr.isima.annotation.Logger1;
import fr.isima.annotation.Logger2;
import fr.isima.exception.CannotCreateBindingException;
import fr.isima.exception.MultipleBindException;
import fr.isima.exception.NotNullBindingException;
import fr.isima.injector.Injector;

public class InjectorTest {
	
	@Inject
	@InjectedValue
	@Logger1
	protected IsiLogger isiLogger ;

	@Test
	public void bindImplementedBy() throws Exception {
				
		Injector injector = new Injector();
		
		Assert.assertTrue(injector.newInstance(IsiLogger.class) instanceof SysoutIsiLogger);
	}
	
	@Test
	public void bindNotSingleton() throws Exception {
		
		Injector injector = new Injector();
		
		Assert.assertTrue(injector.newInstance(IsiLogger.class) != injector.newInstance(IsiLogger.class));
	}
	
	@Test
	public void bindSingletonAnnotation() throws Exception {
		
		Injector injector = new Injector();
		
		injector.bind(IsiLogger.class).to(MultipleIsiLogger.class);
		
		IsiLogger instance = (IsiLogger) injector.newInstance(IsiLogger.class);
		Assert.assertNotNull(instance);
		Assert.assertTrue(instance == injector.newInstance(IsiLogger.class));
	}
	
	@Test
	public void bindTo() throws Exception {
		
		Injector injector = new Injector();
		
		injector.bind(IsiLogger.class).to(FileIsiLogger.class);
		
		Assert.assertTrue(injector.newInstance(IsiLogger.class) instanceof FileIsiLogger);
	}
	
	@Test
	public void bindToAsSingleton() throws Exception {
		
		Injector injector = new Injector();
		
		injector.bind(IsiLogger.class).to(FileIsiLogger.class).asSingleton();
		
		IsiLogger instance = (IsiLogger) injector.newInstance(IsiLogger.class);
		Assert.assertNotNull(instance);
		Assert.assertTrue(instance == injector.newInstance(IsiLogger.class));
	}
	
	@Test(expected=NotNullBindingException.class)
	public void newInstanceNoBinding() throws Exception {
		
		Injector injector = new Injector();
		
		injector.newInstance(FileIsiLogger.class);
	}
	
	@Test
	public void inject() throws Exception {
		
		Injector injector = new Injector();
		
		injector.inject(this);
		
		Assert.assertTrue(isiLogger instanceof IsiLogger);
	}
	
	@Test
	public void createInstanceInCascade() throws Exception {
		
		Injector injector = new Injector();
		
		injector.bind(IsiLogger.class).to(DelegateIsiLogger.class);
		
		IsiLogger instance = (IsiLogger) injector.newInstance(IsiLogger.class);
		Assert.assertTrue(instance instanceof DelegateIsiLogger);
		
		DelegateIsiLogger logger = (DelegateIsiLogger) instance;
		Assert.assertTrue(logger.delegate instanceof IsiLogger);
	}
	
	@Test
	public void bindAnnotatedWith() throws Exception {
		
		Injector injector = new Injector();
		
		injector.bind(MultipleIsiLogger.class).to(MultipleIsiLogger.class);
		injector.bind(IsiLogger.class).annotatedWith(Logger1.class).to(FileIsiLogger.class);
		injector.bind(IsiLogger.class).annotatedWith(Logger2.class).to(DelegateIsiLogger.class);
		
		MultipleIsiLogger instance = (MultipleIsiLogger) injector.newInstance(MultipleIsiLogger.class);
		MultipleIsiLogger logger = (MultipleIsiLogger) instance;
		
		Assert.assertTrue(logger.defaultLogger instanceof SysoutIsiLogger);
		Assert.assertTrue(logger.getLogger1() instanceof FileIsiLogger);
		Assert.assertTrue(logger.getLogger2() instanceof DelegateIsiLogger);
	}
	
	@Test
	public void bindMultipleAnnotatedWith() throws Exception {
		
		Injector injector = new Injector();
		
		injector.bind(IsiLogger.class).annotatedWith(Logger1.class)
			.annotatedWith(InjectedValue.class)
			.to(FileIsiLogger.class);
		
		injector.inject(this);
		
		Assert.assertTrue(this.isiLogger instanceof FileIsiLogger);
	}
	
	@Test
	public void bindValue() throws Exception {
		
		Injector injector = new Injector();
		
		injector.bind(String.class).annotatedWith(InjectedValue.class).to("Hello world!!!");
		
		DelegateIsiLogger instance = (DelegateIsiLogger) injector.inject(new DelegateIsiLogger());
		
		Assert.assertEquals("Hello world!!!", instance.injectedValue);
	}
	
	@Test(expected=CannotCreateBindingException.class)
	public void bindAbstract() throws Exception {
		
		Injector injector = new Injector();
		
		injector.bind(IsiLogger.class).to(IsiLogger.class);
		
		injector.inject(this);
	}
	
	@Test(expected=MultipleBindException.class)
	public void bindAmbigous() throws Exception {
		
		Injector injector = new Injector();
		
		injector.bind(IsiLogger.class).to(SysoutIsiLogger.class);
		injector.bind(IsiLogger.class).to(MultipleIsiLogger.class);
		injector.bind(IsiLogger.class).to(FileIsiLogger.class);
		
		injector.inject(this);
	}
}