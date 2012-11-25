package org.isima.services;

import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.MenuModel;

public class BreadCrumbService {

	public MenuModel breadCrumbFromUrl(String url) {
		
		MenuModel breadCrum = new DefaultMenuModel();
		int i = 0;
		
		for (String field : url.split("/")) {
			
			if (i != 0) {
				
				MenuItem menuItem = new MenuItem();
				menuItem.setValue(field);
				breadCrum.addMenuItem(menuItem);
			}
			
			i++;
		}
		
		return breadCrum;
	}
	
}
