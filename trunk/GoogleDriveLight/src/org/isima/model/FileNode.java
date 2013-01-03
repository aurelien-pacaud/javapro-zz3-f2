package org.isima.model;

import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

public class FileNode extends DefaultTreeNode {

	private static final long serialVersionUID = 1L;
	
	public FileNode(Object data, TreeNode parent) {
		super(data, parent);
	}
	
	public FileNode() {
		super();
	}
	
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

	public void deleteFile(TreeNode fileNode) {
		getChildren().remove(fileNode);		
	}
	
	@Override
	public boolean isLeaf() {
		
		boolean isLeaf = true;
		
		for (TreeNode node : getChildren()) {
			
			FileInfos file = (FileInfos) node.getData();
			
			if (file.isDirectory()) {
				isLeaf = false;
				break;
			}
			else if (file.isFile())		
				isLeaf = true;
		}
		
		if (((FileInfos)getData()).isFile())
			isLeaf = true;
		
		return isLeaf;		
	}

	public void appendChild(FileInfos file) {
		new FileNode(file, this);		
	}
}
