package org.isima.ui.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.isima.model.FileInfos;
import org.primefaces.model.TreeNode;
import org.primefaces.model.DefaultTreeNode;

public class FileLister {

	static public List<FileInfos> getFiles(String path) {
		
		final List<FileInfos> files = new ArrayList<FileInfos>();
				
		String [] filesList = new File(path).list();
		
		for (String file : filesList)
			files.add(new FileInfos(String.format("%s/%s", path, file)));			
	
		
		return files;
	}
	
	static public TreeNode getTree(String path) {
		
		final TreeNode root = new DefaultTreeNode("Root", null);		

		DefaultTreeNode childNode = new DefaultTreeNode(new FileInfos(path), root);
		
		FileLister.buildTree(new FileInfos(path), childNode);		
	
		return root;
	}
	

	private static void buildTree(FileInfos fileInfos, TreeNode parentNode) {	
		
		List<FileInfos> filesInfosList = FileLister.getFiles(fileInfos.getPath());
	
		for(FileInfos childFileInfos : filesInfosList) {
		
			if (childFileInfos.isDirectory()) {
				DefaultTreeNode childNode = new DefaultTreeNode(childFileInfos, parentNode);
				buildTree(childFileInfos, childNode);
			}
		}
	}
}
