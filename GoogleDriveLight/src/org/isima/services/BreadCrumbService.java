package org.isima.services;

import org.isima.model.FileInfos;
import org.isima.model.FileNode;
import org.isima.ui.faces.bean.view.DriveManagedBean;
import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.MenuModel;

public class BreadCrumbService {

	public MenuModel breadCrumbFromNode(DriveManagedBean driveManagedBean, FileNode selectedNode) {
		
		MenuModel breadCrum = new DefaultMenuModel();
		FileNode currentNode = selectedNode;
		
		while (currentNode.getParent() != null) {
			
			MenuItem menuItem = new MenuItem();
			
			menuItem.setValue(((FileInfos)currentNode.getData()).getName());
			menuItem.addActionListener(driveManagedBean);
			menuItem.getAttributes().put("node", currentNode);
			menuItem.setAjax(true);
			menuItem.setUpdate(":formTable :formTree :formBreadCrumb");
			
			breadCrum.getContents().add(0, menuItem);

			currentNode = (FileNode) currentNode.getParent();
		}
				
		return breadCrum;
	}
	
}
