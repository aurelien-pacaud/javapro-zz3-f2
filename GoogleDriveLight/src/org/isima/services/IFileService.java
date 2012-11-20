package org.isima.services;

import java.util.List;

import org.isima.model.FileInfos;
import org.primefaces.model.TreeNode;

public interface IFileService  {

	public TreeNode getTree(String path);
	public List<FileInfos> getFiles(String dirPath);
}
