package org.isima.ui.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.isima.model.FileInfos;
import org.primefaces.model.TreeNode;
import org.primefaces.model.DefaultTreeNode;

public class FileLister {

	static public List<TreeNode> getFiles(String path) {
		
		final List<TreeNode> orderingFiles = new ArrayList<TreeNode>();
		final List<TreeNode> orderingDirectories = new ArrayList<TreeNode>();
		
		File[] files = new File(path).listFiles();
		
		if (files != null) {			
		
			Arrays.sort(files);
		
			for (File file : files) {
			
				FileInfos fileInfos = new FileInfos(file.getAbsolutePath());
			
				if (!file.isDirectory())
					orderingFiles.add(new DefaultTreeNode(fileInfos, null));
				else
					orderingDirectories.add(new DefaultTreeNode(fileInfos, null));
			}
		}
			
		orderingDirectories.addAll(orderingFiles);
		
		return orderingDirectories;
	}
	
	static public TreeNode getTree(String path) {
				
		final TreeNode root = new DefaultTreeNode(new FileInfos(path), null);				
		FileLister.buildTree(path, root);		
	
		return root;
	}
	

	private static void buildTree(String path, TreeNode parentNode) {	
		
		List<TreeNode> filesInfosList = FileLister.getFiles(path);
	
		for(TreeNode childFileInfos : filesInfosList) {
		
			FileInfos file = (FileInfos)childFileInfos.getData();
			
			DefaultTreeNode childNode = new DefaultTreeNode(file, parentNode);
			buildTree(file.getPath(), childNode);
		}
	}
}
