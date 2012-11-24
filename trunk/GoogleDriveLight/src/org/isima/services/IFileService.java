package org.isima.services;

import java.io.FileFilter;
import java.util.List;

import org.primefaces.model.TreeNode;

public interface IFileService  {

	public TreeNode getTree(String path);
	public List<TreeNode> getFiles(TreeNode node);
	public void refresh();
	public TreeNode filterTree(TreeNode tree, FileFilter filter);
}
