package org.isima.services;

import java.io.FileFilter;
import java.io.Serializable;
import java.util.List;

import org.isima.ui.utils.FileLister;
import org.primefaces.model.TreeNode;

public class FileListerService implements IFileService, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Override
	public TreeNode getTree(String path) {
				
		return FileLister.getTree(path);
	}

	@Override
	public List<TreeNode> getFiles(TreeNode node) {
		
		return node.getChildren();
	}
	
	@Override
	public void refresh() {
		
	}

	@Override
	public TreeNode filterTree(TreeNode tree, FileFilter filter) {
		
		
		
		return null;
	}
}
