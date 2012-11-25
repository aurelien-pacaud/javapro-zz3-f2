package org.isima.model;

import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

public class FileTreeNodeModel extends DefaultTreeNode {

	private static final long serialVersionUID = 1L;
	
	public List<TreeNode> search(FileFilter file) {
		
		List<TreeNode> matchFiles = new ArrayList<TreeNode>();
		
		_search(this, file, matchFiles);
		
		return matchFiles;
	}
	
	private void _search(TreeNode parentNode, FileFilter file, List<TreeNode> list) {		
		
		for (TreeNode childnode : parentNode.getChildren()) {
		
			FileInfos fileInfos = (FileInfos)childnode.getData();
			
			if (file.accept(fileInfos.getFile())) {
			
				list.add(new DefaultTreeNode(fileInfos, null));				
			}
			_search(childnode, file, list);
		}		
	}
	
	public void createFile(TreeNode root, String path) {
		
		new DefaultTreeNode(new FileInfos(path), root);		
	}

	public void deleteFile(TreeNode root, TreeNode selectedFile) {
		root.getChildren().remove(selectedFile);		
	}

	public void createFolder(TreeNode root, String path) {
		
		new DefaultTreeNode(new FileInfos(path), root);		
	}
}
