package org.isima.ui.faces.bean.view;

import java.io.File;
import java.io.FileFilter;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;

import org.isima.annotation.FileLister;
import org.isima.model.FileInfos;
import org.isima.model.FileTreeNodeModel;
import org.isima.services.BreadCrumbService;
import org.isima.services.IFileOperationService;
import org.isima.services.IFileService;
import org.isima.singleton.GoogleDriveLightInjector;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.MenuModel;
import org.primefaces.model.TreeNode;

import fr.isima.annotation.Inject;
import fr.isima.annotation.InjectedValue;
import fr.isima.exception.MultipleBindException;
import fr.isima.exception.NotNullBindingException;
import fr.isima.injector.Injector;

public class DriveManagedBean implements Serializable {

	private static final long serialVersionUID = 1L;

	/* Model représentant l'arbre des données. */
	private FileTreeNodeModel model;
	
	/* Sous model représentant la hérarchie sélectionnée. */
	private TreeNode selectedNode;
	
	/* Contenu du répertoire sélectionné. */
	private List<TreeNode> dirContent;
	
	@Inject
	@InjectedValue
	private String userHome;
	
	@Inject
	@FileLister
	private IFileService service;
	
	private String currentDirectory;
	
	private TreeNode selectedFile;	
	@Inject
	private IFileOperationService fileOperationService;
	
	private String filename;
	private String dirname;
	
	private String pattern;
		
	@PostConstruct
	public void init() {
	
		Injector injector = GoogleDriveLightInjector.getInstance();		
		
		try {
			injector.inject(this);
		} catch (NotNullBindingException e) {
			e.printStackTrace();
		} catch (MultipleBindException e) {
			e.printStackTrace();
		}
		
		model = new FileTreeNodeModel();
		
		currentDirectory = userHome;
		TreeNode driveNode = service.getTree(userHome);	
		
		driveNode.setParent(model);
		
		setSelectedNode(driveNode);
		
		selectedNode.setExpanded(true);
		selectedNode.setSelected(true);
		
		dirContent = service.getFiles(selectedNode);
	}
	
	public TreeNode getModel() {
		
		return model;
	}
	
	public TreeNode getSelectedNode() {
		
		return selectedNode;
	}
	
	public List<TreeNode> getDirContent() {
		
		return dirContent;
	}
	
	public void search() {
		
		dirContent = model.search(new FileFilter() {
			
			@Override
			public boolean accept(File file) {
				
				return (file.getName().matches(pattern.replace("*", ".*")) && file.isFile());
			}
		});
		
		currentDirectory = pattern;
	}

	public void setSelectedNode(TreeNode selectedNode) {
		
		this.selectedNode = selectedNode;
		pattern = null;
	}
	
	public void onNodeSelect(NodeSelectEvent event) {  
	    
		selectedNode = event.getTreeNode();
		selectedNode.setExpanded(true);
		
		FileInfos file = (FileInfos)(selectedNode.getData());	
		currentDirectory = file.getPath();
		
		dirContent = service.getFiles(selectedNode);
    } 
	
	public void onRowSelect(SelectEvent event) {  
        
		TreeNode node = (TreeNode)event.getObject();
		FileInfos file = (FileInfos)selectedFile.getData();
		
		if (file.isDirectory()) {
						
			selectedNode.setSelected(false);
			selectedNode.setExpanded(true);
			
			node.setSelected(true);
			node.setExpanded(true);

			selectedNode = node;
			
			currentDirectory = file.getPath();
			
			dirContent = service.getFiles(selectedNode);
			selectedFile = null;			
		}
    }
	
	public void setSelectedFile(TreeNode selectedFile) {
		this.selectedFile = selectedFile;		
		pattern = null;
	}
	
	public TreeNode getSelectedFile() {
		return selectedFile;
	}
	
	public String getCurrentDirectory() {
		return currentDirectory;
	}
	
	public String getFilename() {
		return filename;
	}
	
	public void setFilename (String filename) {
		this.filename = filename;
	}
	
	public void setDirname (String dirname)
	{
		this.dirname = dirname;
	}
	
	public String getDirname () {
		return dirname;
	}
	
	/**
	 * Permet la creation d'un fichier depuis l'interface du Drive
	 */
	public void createFile () {
		
		String path = String.format("%s/%s", currentDirectory, filename);
		
		fileOperationService.createNewFile(path);
		model.createFile(selectedNode, path);
	}
	
	/**
	 * Permet la suppression d'un fichier depuis l'interface du Drive
	 */
	public void deleteFile () {
		
		fileOperationService.deleteFile(((FileInfos)selectedFile.getData()).getPath());
		model.deleteFile(selectedNode, selectedFile);
	}
	
	/**
	 * Permet la création d'un nouveau répertoire depuis l'interface du Drive
	 */
	public void createDirectory () {
		
		String path = String.format("%s/%s", currentDirectory, dirname);
		System.out.print(path);
		
		fileOperationService.createFolder(path);
		model.createFolder(selectedNode, path);
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public MenuModel getBreadCrumb() {		
		
		return new BreadCrumbService().breadCrumbFromUrl(currentDirectory);
	}
}
