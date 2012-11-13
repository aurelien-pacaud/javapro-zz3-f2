package fr.isima.testClasses;

import fr.isima.annotation.Inject;
import fr.isima.annotation.InjectedValue;

public class DelegateIsiLogger implements IsiLogger{
	
	@Inject
	@InjectedValue
	public String injectedValue ;
	
	@Inject
	public IsiLogger delegate ;

}
