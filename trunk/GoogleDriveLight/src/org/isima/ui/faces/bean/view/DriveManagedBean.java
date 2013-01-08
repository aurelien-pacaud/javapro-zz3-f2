package org.isima.ui.faces.bean.view;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.isima.model.FileInfos;
import org.isima.model.FileNode;
import org.isima.services.BreadCrumbService;
import org.isima.services.IFileService;
import org.isima.singleton.GoogleDriveLightInjector;
import org.isima.ui.utils.MessageBundle;
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
	private TreeNode selectedNode;
	
	@Inject
	@InjectedValue
	private String userHome;
	
	@Inject
	private IFileService fileService;
		
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
		
		model = new FileNode(new FileInfos(""), null);
		fileService.getTree(userHome).setParent(model);	
				
		setSelectedNode(model.getChildren().get(0));
	}
	
	public FileNode getModel() {
		
		return model;
	}
	
	public TreeNode getSelectedNode() {
		
		return selectedNode;
	}
	
	public List<TreeNode> getDirContent() {
		
		return dirContent;
	}
	
	public void search() {
		
		dirContent = model.search(new WildcardFileFilter(pattern)); 
	}

	public void setSelectedNode(TreeNode selectedNode) {
		
		if (this.selectedNode != null)
			this.selectedNode.setSelected(false);
		
		this.selectedNode = selectedNode;
		
		dirContent = selectedNode.getChildren();
		
		if (!selectedNode.isLeaf())
			selectedNode.setExpanded(true);
		
		selectedNode.setSelected(true);
		
		pattern = null;
		selectedFile = null;
	}
	
	public void onNodeSelect(NodeSelectEvent event) {  
	    
		setSelectedNode((FileNode)event.getTreeNode());
    } 
	
	public void onRowSelect(SelectEvent event) {  
        
		FileNode node = (FileNode)event.getObject();
		FileInfos file = (FileInfos)selectedFile.getData();
		
		if (file.isDirectory()) {
			
			if (selectedNode != null)
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
	
	public String getFilename() {
		return filename;
	}
	
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	public void setDirname(String dirname)
	{
		this.dirname = dirname;
	}
	
	public String getDirname() {
		return dirname;
	}
	
	
	/**
	 * Permet la suppression d'un fichier depuis l'interface du Drive
	 */
	public void deleteFile() {
		
		FileInfos fileInfos = (FileInfos) selectedFile.getData();
		
		if (fileInfos.isFile()) {
			if (fileService.deleteFile(fileInfos.getPath())) {
				
				/* On recherche le noeud associé dans le model. */		
				((FileNode) selectedNode).deleteChildNode(selectedFile);
				MessageBundle.displayInformationMsg(MessageBundle.getMessage("fileDeleted"));
			}
			else {

				MessageBundle.displayErrorMsg(MessageBundle.getMessage("fileNotDeleted"));
			}				
		}
			
		else {
			
			setSelectedNode(selectedFile);
			deleteDirectory();
		}
	}
	
	/**
	 * Permet la suppression d'un répertoire depuis l'interface du Drive
	 */
	public void deleteDirectory() {
				
		FileNode parent = (FileNode) selectedNode.getParent();
		
		if (fileService.deleteFolder(((FileInfos)selectedNode.getData()).getPath())) {
			
			((FileNode) selectedNode.getParent()).deleteChildNode(selectedNode);
			setSelectedNode(parent);
			
			MessageBundle.displayInformationMsg(MessageBundle.getMessage("folderDeleted"));
		}
		else {

			MessageBundle.displayErrorMsg(MessageBundle.getMessage("folderNotDeleted"));
		}
	}
	
	/**
	 * Permet la creation d'un fichier depuis l'interface du Drive
	 */
	public void createFile() {
		
		String currentDirectory = ((FileInfos) selectedNode.getData()).getPath();
		String path = String.format("%s/%s", currentDirectory, filename);		
		
		if (fileService.createNewFile(path)) {
			
			((FileNode) selectedNode).appendChild(new FileInfos(path));
			MessageBundle.displayInformationMsg(MessageBundle.getMessage("fileCreated"));
		}
		else
			MessageBundle.displayErrorMsg(MessageBundle.getMessage("fileNotCreated"));
	}
	
	/**
	 * Permet la création d'un nouveau répertoire depuis l'interface du Drive
	 */
	public void createDirectory() {
		
		String currentDirectory = ((FileInfos) selectedNode.getData()).getPath();
		String path = String.format("%s/%s", currentDirectory, dirname);			
		
		if (fileService.createFolder(path)) {
			
			setSelectedNode(((FileNode) selectedNode).appendChild(new FileInfos(path)));
			selectedNode.getParent().setExpanded(true);			

			MessageBundle.displayInformationMsg(MessageBundle.getMessage("folderCreated"));			
		}	
		else
			MessageBundle.displayErrorMsg(MessageBundle.getMessage("folderNotCreated"));			
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		
		this.pattern = pattern;
		
		/* Suppression des informations enregistrées pour avoir un affichage neutre. */
		selectedNode.setSelected(false);
		
		selectedFile = null;
		selectedNode = null;		
	}

	public MenuModel getBreadCrumb() {		
		
		return new BreadCrumbService().breadCrumbFromNode(this, (FileNode)selectedNode);
	}
	
	public void handleFileUpload(FileUploadEvent event) {
		
		String currentDirectory = ((FileInfos) selectedNode.getData()).getPath();
		String path = String.format("%s/%s", currentDirectory, event.getFile().getFileName());
					
		if (fileService.copyFile(event.getFile(), path)) {

			((FileNode) selectedNode).appendChild(new FileInfos(path));
			
			MessageBundle.displayInformationMsg(String.format(MessageBundle.getMessage("uploadSuccesful"), event.getFile().getFileName()));		
		}
		else {
			MessageBundle.displayErrorMsg(MessageBundle.getMessage("uploadNotSuccesful"));	
		}		
	}

	@Override
	public void processAction(ActionEvent event) throws AbortProcessingException {
		
		if(event.getSource().getClass() == MenuItem.class) {
			
            MenuItem sourceItem = (MenuItem) event.getSource();
            
            FileNode node = (FileNode) sourceItem.getAttributes().get("node");
            
            selectedNode.setSelected(false);
			setSelectedNode(node);
			selectedFile = null;
       }		
	}
}
