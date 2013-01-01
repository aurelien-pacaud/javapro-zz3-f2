package org.isima.ui.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.isima.model.FileInfos;
import org.isima.model.FileNode;
import org.primefaces.model.TreeNode;

public class FileLister {

	static public List<FileNode> getFiles(String path) {
		
		final List<FileNode> orderingFiles = new ArrayList<FileNode>();
		
		File[] files = new File(path).listFiles();
		
		if (files != null) {			
		
			Arrays.sort(files);
		
			for (File file : files) {
			
				FileInfos fileInfos = new FileInfos(file.getAbsolutePath());
				orderingFiles.add(new FileNode(fileInfos, null));					
			}
		}		
		
		return orderingFiles;
	}
	
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
