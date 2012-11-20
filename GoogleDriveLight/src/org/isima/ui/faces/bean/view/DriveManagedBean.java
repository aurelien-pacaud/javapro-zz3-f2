package org.isima.ui.faces.bean.view;

import java.io.Serializable;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.isima.annotation.FileLister;
import org.isima.model.FileInfos;
import org.isima.services.IFileService;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.TreeNode;

import fr.isima.annotation.Inject;
import fr.isima.annotation.InjectedValue;
import fr.isima.exception.MultipleBindException;
import fr.isima.exception.NotNullBindingException;
import fr.isima.injector.Injector;

public class DriveManagedBean implements Serializable {

	private static final long serialVersionUID = 1L;

	/* Model représentant l'arbre des données à afficher. */
	private TreeNode model;
	
	/* Sous model représentant la hérarchie sélectionnée. */
	private TreeNode selectedNode;
	
	/* Contenu du répertoire sélectionné. */
	private List<FileInfos> dirContent;
	
	@Inject
	@InjectedValue
	private String userHome;
	
	@Inject
	@FileLister
	private IFileService service;
	
	@Inject
	@InjectedValue
	private String currentDirectory;
	
	private FileInfos selectedFile;	
	
	public DriveManagedBean() throws NotNullBindingException, MultipleBindException {
		
		FacesContext context = FacesContext.getCurrentInstance();
		ServletContext servletContext = (ServletContext)context.getExternalContext().getContext();
		
		Injector injector = (Injector)servletContext.getAttribute("injector");		
		injector.inject(this);
		
		model = service.getTree(userHome);
		dirContent = service.getFiles(currentDirectory);
	}

	public TreeNode getModel() {
		
		return model;
	}
	
	public TreeNode getSelectedNode() {
		
		return selectedNode;
	}
	
	public List<FileInfos> getDirContent() {
		
		return dirContent;
	}

	public void setSelectedNode(TreeNode selectedNode) {
		
		this.selectedNode = selectedNode;
	}
	
	public void onNodeSelect(NodeSelectEvent event) {  
	     
		FileInfos file = (FileInfos) event.getTreeNode().getData();	
		currentDirectory = file.getPath();
		
		dirContent = service.getFiles(currentDirectory);
    } 
	
	public void onRowSelect(SelectEvent event) {  
        
		selectedFile = (FileInfos)event.getObject();		
    }
	
	public void setSelectedFile(FileInfos selectedFile) {
		this.selectedFile = selectedFile;
	}
	
	public FileInfos getSelectedFile() {
		return selectedFile;
	}
	
	public String getCurrentDirectory() {
		return currentDirectory;
	}
}
