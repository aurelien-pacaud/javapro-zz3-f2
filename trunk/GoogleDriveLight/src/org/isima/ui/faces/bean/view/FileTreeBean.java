package org.isima.ui.faces.bean.view;

import java.io.Serializable;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.isima.ui.utils.FileLister;
import org.primefaces.model.TreeNode;

public class FileTreeBean extends HttpServlet implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private TreeNode model;
	private TreeNode selectedNode;
	
	public void init(ServletConfig config) throws ServletException {  
	    	super.init(config);  
	}  
	
	public FileTreeBean() {
		//LoginManagedBean user = (LoginManagedBean)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginManagedBean");
		String userHome = (String)getServletContext().getAttribute("userHome");
		System.out.println("debug : userHome = " + userHome);
		model = FileLister.getTree(userHome);
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



					