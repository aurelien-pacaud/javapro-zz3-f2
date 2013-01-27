package fr.isima.testClasses;

import fr.isima.annotation.ImplementedBy;
import fr.isima.annotation.Inject;
import fr.isima.annotation.Singleton;
import fr.isima.annotation.Logger1;
import fr.isima.annotation.Logger2;

@Singleton
@ImplementedBy(MultipleIsiLogger.class)
public class MultipleIsiLogger implements IsiLogger {
	
	@Inject
	public IsiLogger defaultLogger;

	@Inject
	@Logger1
	protected IsiLogger logger1 ;
	
	@Inject
	@Logger2
	private IsiLogger logger2 ;
	
	public IsiLogger getLogger1() {
		return logger1;
	}
	
	public IsiLogger getLogger2() {
		return logger2;
	}
}
