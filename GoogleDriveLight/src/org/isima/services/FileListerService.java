package org.isima.services;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import org.isima.model.FileInfos;
import org.isima.model.FileNode;
import org.isima.ui.utils.FileLister;
import org.isima.ui.utils.FilePathFilter;
import org.primefaces.model.TreeNode;
import org.primefaces.model.UploadedFile;

public class FileListerService implements IFileService, Serializable {

	private static final long serialVersionUID = 1L;
	
	private FileNode model;
	private FileNode currentNode;
	
	@Override
	public FileNode getTree(final String path) {

		model = (FileNode) FileLister.getTree(path);
		
		currentNode = model;
		
		return model;
	}
	
	@Override
	public void setCurrentNode(FileNode node) {
		this.currentNode = node;		
	}
	
	@Override
	public FileNode getCurrentNode() {
		return currentNode;
	}
	
	/***
	 * 
	 * Méthode permettant de supprimer un dossier dans le drive de l'utilisateur.
	 * 
	 * @param path Chemin vers le dossier que l'on veut supprimer.
	 * 
	 * @return True si le dossier a été supprimé. Faux sinon.
	 */
	@Override
	public boolean deleteFolder(String path) throws IOException {
		
		FileUtils.deleteDirectory(new File(path));					

		((FileNode) currentNode.getParent()).deleteChildNode(currentNode);
		
		return true;
	}
	
	/***
	 * 
	 * Méthode permettant de supprimer un fichier dans le drive de l'utilisateur.
	 * 
	 * @param path Chemin vers le fichier que l'on veut supprimer.
	 * 
	 * @return True si le fichier a été supprimé. Faux sinon.
	 */
	@Override
	public boolean deleteFile(String path) throws IOException {
		
		File file = new File(path);
		boolean ret = false;
		
		ret = file.delete();
		
		/* Si la suppression du fichier est faite. */
		if (ret) {
			
			/* On recherche le noeud associé dans le model. */
			TreeNode node = currentNode.searchFile(new FilePathFilter(path));			
			currentNode.deleteChildNode(node);
		}
		
		return ret;
	}

	/***
	 * 
	 * Méthode permettant de créer un fichier dans le drive de l'utilisateur.
	 * 
	 * @param filename Nom du fichier que l'on veut créer.
	 * 
	 * @return True si le fichier a été créé. Faux sinon.
	 */
	@Override
	public boolean createNewFile(String filename) {
		
		File file = new File(filename);
		boolean ret = false;
		
		try {
			
			ret = file.createNewFile();
			
			if (ret)
				currentNode.appendChild(new FileInfos(filename));
		} 
		catch (IOException e) {
			
			ret = false;
		}
		
		return ret;
	}
	
	/***
	 * 
	 * Méthode permettant de créer un répertoire dans le drive de l'utilisateur.
	 * 
	 * @param filename Nom du répertoire que l'on veut créer.
	 * 
	 * @return True si le fichier a été créé. Faux sinon.
	 */
	@Override
	public boolean createFolder(String dirName) {
		
		File file = new File(dirName);	
		boolean ret = false;
		
		ret = file.mkdir();	
		
		if (ret) {
			currentNode = currentNode.appendChild(new FileInfos(dirName));
		}
		
		return ret;
	}

	@Override
	public void copyFile(UploadedFile file, String destFilename) throws IOException {

	    InputStream input = file.getInputstream();	    

    	File f = new File(destFilename);
    	
    	if (!f.exists()) {
    		
    		OutputStream output = new FileOutputStream(f);

    		try {	        
	    	
	    		IOUtils.copy(input, output);	    		
	    		currentNode.appendChild(new FileInfos(destFilename));		
	    		
    		} finally {
    			IOUtils.closeQuietly(input);
    			IOUtils.closeQuietly(output);
    		}
    	}
	}
}
