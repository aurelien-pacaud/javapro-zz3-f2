package org.isima.ui.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Comparator;

import org.isima.model.FileInfos;
import org.isima.model.FileNode;
import org.primefaces.model.TreeNode;

public class FileLister {

	/***
	 * Fonction listant le contenu d'un répertoire en le triant
	 * 
	 * @param path Chemin du répertoire dont on veut le contenu
	 * @return Liste des fichiers et répertoires contenus dans le répertoire
	 */
	static public List<FileNode> getFiles(String path) {
		
		final List<FileNode> orderingFiles = new ArrayList<FileNode>();
		final List<FileNode> orderingFolders = new ArrayList<FileNode>();
		
		File[] files = new File(path).listFiles();
		
		if (files != null) {			
		
			Arrays.sort(files, new Comparator<File>() {
			    @Override
			    public int compare(File f1, File f2) {              
			        return f1.getName().compareToIgnoreCase(f2.getName());
			    }
			});
		
			for (File file : files) {			

				FileInfos fileInfos = new FileInfos(file.getAbsolutePath());
				FileNode node = new FileNode(fileInfos, null);				
				
				if (file.isFile())				
					orderingFiles.add(node);
				else
					orderingFolders.add(node);
			}
		}		
		
		orderingFolders.addAll(orderingFiles);
		
		return orderingFolders;
	}
	
	/***
	 * Fonction permettant d'obtenir l'arborescence d'un répertoire.
	 * 
	 * @param path Chemin du répertoire dont on veut l'arborescence.
	 * @return Un arbre représentant l'arborescence du répertoire.
	 */
	static public FileNode getTree(String path) {
				
		final FileNode root = new FileNode(new FileInfos(path), null);				
		FileLister.buildTree(path, root);		
	
		return root;
	}
	

	private static void buildTree(String path, TreeNode parentNode) {	
		
		List<FileNode> filesInfosList = FileLister.getFiles(path);
	
		for(FileNode childFileInfos : filesInfosList) {
		
			FileInfos file = (FileInfos)childFileInfos.getData();
			
			FileNode childNode = new FileNode(file, parentNode);
			buildTree(file.getPath(), childNode);
		}
	}
}
