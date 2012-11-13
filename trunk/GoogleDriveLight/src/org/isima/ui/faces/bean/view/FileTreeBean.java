package org.isima.ui.faces.bean.view;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.isima.ui.faces.bean.login.LoginManagedBean;
import org.isima.ui.utils.FileLister;
import org.primefaces.model.TreeNode;

@ManagedBean
public class FileTreeBean implements Serializable {

	private TreeNode model;
	private TreeNode selectedNode;	
	
	public FileTreeBean() {
		
		LoginManagedBean user = (LoginManagedBean)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginManagedBean");
			
		model = FileLister.getTree(user.getUserHome());
	}

	public TreeNode getModel() {
		
		return model;
	}
	
	public TreeNode getSelectedNode() {
		
		return selectedNode;
	}

	public void setSelectedNode(TreeNode selectedNode) {
		
		this.selectedNode = selectedNode;
	}
}



					