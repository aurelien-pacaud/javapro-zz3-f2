package org.isima.ui.faces.bean.view;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import org.isima.annotation.FileLister;
import org.isima.model.FileInfos;
import org.isima.model.FileNode;
import org.isima.services.BreadCrumbService;
import org.isima.services.IFileService;
import org.isima.singleton.GoogleDriveLightInjector;
import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.MenuModel;
import org.primefaces.model.TreeNode;

import fr.isima.annotation.Inject;
import fr.isima.annotation.InjectedValue;
import fr.isima.exception.MultipleBindException;
import fr.isima.exception.NotNullBindingException;
import fr.isima.injector.Injector;

public class DriveManagedBean implements Serializable, ActionListener {

	private static final long serialVersionUID = 1L;

	/* Model représentant l'arbre des données. */
	private FileNode model;
	
	/* Contenu du dossier courant. */
	private List<TreeNode> dirContent;
	
	/* Sous model représentant la hérarchie sélectionnée. */
	private FileNode selectedNode;
	
	@Inject
	@InjectedValue
	private String userHome;
	
	@Inject
	@FileLister
	private IFileService fileService;
	
	private String currentDirectory;
	
	private TreeNode selectedFile;	
	
	private String filename;
	private String dirname;
	
	private String pattern;
	
	/* Methode appelée lorsque que l'objet est complétement construit. */
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
		
		currentDirectory = userHome;
		model = fileService.getTree(userHome);	
				
		setSelectedNode(model);
	}
	
	public FileNode getModel() {
		
		return model;
	}
	
	public FileNode getSelectedNode() {
		
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

	public void setSelectedNode(FileNode selectedNode) {
				
		this.selectedNode = selectedNode;
		
		dirContent = selectedNode.getChildren();
		
		FileInfos file = (FileInfos)(selectedNode.getData());	
		currentDirectory = file.getPath();
		
		if (!selectedNode.isLeaf())
			selectedNode.setExpanded(true);
		
		selectedNode.setSelected(true);
		
		fileService.setSelectedNode(selectedNode);
	}
	
	public void onNodeSelect(NodeSelectEvent event) {  
	    
		setSelectedNode((FileNode)event.getTreeNode());
    } 
	
	public void onRowSelect(SelectEvent event) {  
        
		FileNode node = (FileNode)event.getObject();
		FileInfos file = (FileInfos)selectedFile.getData();
		
		if (file.isDirectory()) {
			
			selectedNode.setSelected(false);
			setSelectedNode(node);
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
		
		fileService.createNewFile(path);
	}
	
	/**
	 * Permet la suppression d'un fichier depuis l'interface du Drive
	 */
	public void deleteFile () {
		
		try {
			fileService.deleteFile(selectedFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Permet la suppression d'un répertoire depuis l'interface du Drive
	 */
	public void deleteDirectory () {
		
		try {
			fileService.deleteFile(selectedNode);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Permet la création d'un nouveau répertoire depuis l'interface du Drive
	 */
	public void createDirectory () {
		
		String path = String.format("%s/%s", currentDirectory, dirname);
		System.out.print(path);
		
		fileService.createFolder(path);
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public MenuModel getBreadCrumb() {		
		
		return new BreadCrumbService().breadCrumbFromNode(this, selectedNode);
	}
	
	public void handleFileUpload(FileUploadEvent event) {
		
		try {
			
			fileService.copyFile(event.getFile(), currentDirectory + "/" + event.getFile().getFileName());			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
		FacesContext.getCurrentInstance().addMessage("messages", msg);
	}

	@Override
	public void processAction(ActionEvent event) throws AbortProcessingException {
	
		System.out.println(event.getSource().getClass());
		
		if(event.getSource().getClass() == MenuItem.class) {
			
			System.out.println("Item click");
			
            MenuItem sourceItem = (MenuItem) event.getSource();
            
            FileNode node = (FileNode) sourceItem.getAttributes().get("node");
            
            selectedNode.setSelected(false);
			setSelectedNode(node);
			selectedFile = null;
       }		
	}
}
