package org.isima.ui.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.isima.model.FileInfos;
import org.primefaces.model.TreeNode;
import org.primefaces.model.DefaultTreeNode;

public class FileLister {

	static public List<FileInfos> getFiles(String path) {
		
		final List<FileInfos> orderingFiles = new ArrayList<FileInfos>();
		final List<FileInfos> orderingDirectories = new ArrayList<FileInfos>();
		
		File[] files = new File(path).listFiles();
		Arrays.sort(files);
		
		for (File file : files) {
			
			FileInfos fileInfos = new FileInfos(file.getAbsolutePath());
			
			if (!file.isDirectory())
				orderingFiles.add(fileInfos);
			else
				orderingDirectories.add(fileInfos);
		}
			
		orderingDirectories.addAll(orderingFiles);
		
		return orderingDirectories;
	}
	
	static public TreeNode getTree(String path) {
				
		final TreeNode root = new DefaultTreeNode("Root", null);		

		DefaultTreeNode childNode = new DefaultTreeNode(new FileInfos("Mon Drive", path), root);
		
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
