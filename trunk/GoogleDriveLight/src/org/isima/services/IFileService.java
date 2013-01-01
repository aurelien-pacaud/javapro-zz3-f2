package org.isima.services;

import java.io.IOException;
import java.util.List;

import org.isima.model.FileNode;
import org.primefaces.model.TreeNode;
import org.primefaces.model.UploadedFile;

public interface IFileService  {

	public FileNode getTree(final String path);
	public List<TreeNode> getFiles(final String path);
	
	public void createNewFile(String filename);
	public void deleteFile(TreeNode selectedFile) throws IOException;
	public void createFolder(String dirName);
	public void setSelectedNode(FileNode selectedNode);
	public void copyFile(UploadedFile destFile, String destFilename) throws IOException;
}
