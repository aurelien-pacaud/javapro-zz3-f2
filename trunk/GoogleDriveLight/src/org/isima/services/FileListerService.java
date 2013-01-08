package org.isima.services;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import org.isima.model.FileNode;
import org.isima.ui.utils.FileLister;
import org.primefaces.model.UploadedFile;

public class FileListerService implements IFileService, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Override
	public FileNode getTree(final String path) {
		
		return (FileNode) FileLister.getTree(path);
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
	public boolean deleteFolder(String path) {
		
		boolean ret = false;
		
		try {
			FileUtils.deleteDirectory(new File(path));
			ret = true;
		} catch (IOException e) {
			ret = false;
		}	
		
		return ret;
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
	public boolean deleteFile(String path) {
		
		File file = new File(path);
		
		return file.delete();
	}

	/***
	 * 
	 * Méthode permettant de créer un fichier dans le drive de l'utilisateur.
	 * 
	 * @param filename Nom du fichier que l'on veut créer.
	 * 
	 * @return True si le fichier a été créé. Faux sinon.
	 * @throws IOException 
	 */
	@Override
	public boolean createNewFile(String filename) {
		
		File file = new File(filename);
		boolean ret = false;
		
		try {
			
			if (!file.exists()) {
				
				file.createNewFile();
				ret = true;
			}
			
		} catch (IOException e) {
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
		
		if (!file.exists()) {
			
			file.mkdir();
			ret = true;
		}
				
		return ret;
	}

	@Override
	public boolean copyFile(UploadedFile file, String destFilename) {

	    InputStream input = null;
	    boolean ret = false;
	    
		try {
			input = file.getInputstream();
		} catch (IOException e1) {
			ret = false;
		}	  
	    

    	File f = new File(destFilename);
    	
    	if (!f.exists()) {
    		
    		OutputStream output = null;
    		
			try {
				output = new FileOutputStream(f);
			} catch (FileNotFoundException e) {
				ret = false;
			}

    		try {	        
	    	
	    		IOUtils.copy(input, output);
	    		ret = true;
	    		
    		} catch (IOException e) {
				ret = false;
			} finally {
    			IOUtils.closeQuietly(input);
    			IOUtils.closeQuietly(output);
    		}
    	}
    	
    	return ret;
	}
}
