package org.isima.services;

import org.isima.model.FileInfos;
import org.isima.model.FileNode;
import org.isima.ui.faces.bean.view.DriveManagedBean;
import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.MenuModel;

public class BreadCrumbService {

	/**
	 * Méthode permettant d'afficher l'arborescence dans le breadCrumb.
	 * 
	 * @param driveManagedBean instance du driveManagedBean courant
	 * @param selectedNode Noeud sélectionné.
	 * @return menumodel associé au noeud sélectionné.
	 */
	public MenuModel breadCrumbFromNode(DriveManagedBean driveManagedBean, FileNode selectedNode) {
		
		MenuModel breadCrum = new DefaultMenuModel();
		
		/* Si le noeud courant n'est pas null. */
		if (selectedNode != null) {
		
			FileNode currentNode = selectedNode;
			
			/* Pour tous les noeuds jusqu'au root. */
			while (currentNode.getParent() != null) {
				
				/* Création d'un item associé. */
				MenuItem menuItem = new MenuItem();
				
				menuItem.setValue(((FileInfos)currentNode.getData()).getName());
				menuItem.addActionListener(driveManagedBean);
				menuItem.getAttributes().put("node", currentNode);
				menuItem.setAjax(true);
				menuItem.setUpdate(":formTable :formTree :formBreadCrumb");
				
				breadCrum.getContents().add(0, menuItem);
	
				currentNode = (FileNode) currentNode.getParent();
			}
		}
				
		return breadCrum;
	}
	
}
