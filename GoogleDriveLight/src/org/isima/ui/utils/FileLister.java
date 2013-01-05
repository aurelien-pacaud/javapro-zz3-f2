package org.isima.ui.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.isima.model.FileInfos;
import org.isima.model.FileNode;

public class FileLister {

	/***
	 * Fonction listant le contenu d'un répertoire en le triant
	 * 
	 * @param path Chemin du répertoire dont on veut le contenu
	 * @return Liste des fichiers et répertoires contenus dans le répertoire
	 */
	static public List<FileNode> getFiles(String path, FileNode parent) {
		
		final List<FileNode> files = new ArrayList<FileNode>();			
			
		File [] filesList = new File(path).listFiles();
		
		if (filesList != null) {
		
			for (File file : filesList) {			
					
				FileNode node = parent.appendChild(new FileInfos(file.getAbsolutePath()));		
				files.add(node);
			}
		}
		
		return files;
	}
	
	/***
	 * Fonction permettant d'obtenir l'arborescence d'un répertoire.
	 * 
	 * @param path Chemin du répertoire dont on veut l'arborescence.
	 * @return Un arbre représentant l'arborescence du répertoire.
	 */
	static public FileNode getTree(String path) {
				
		final FileNode root = new FileNode("drive", new FileInfos(MessageBundle.getMessage("driveName"), path), null);				
		FileLister.buildTree(path, root);		
	
		return root;
	}
	

	private static void buildTree(String path, FileNode parentNode) {	
		
		List<FileNode> filesInfosList = FileLister.getFiles(path, parentNode);
	
		for(FileNode childFileInfos : filesInfosList) {
		
			FileInfos file = (FileInfos)childFileInfos.getData();
			buildTree(file.getPath(), childFileInfos);
		}
	}
}
