package org.isima.services;

import org.isima.model.FileNode;
import org.primefaces.model.UploadedFile;

public interface IFileService  {

	public FileNode getTree(final String path);
	
	public boolean createNewFile(String filename);
	public boolean deleteFile(String path);
	
	public boolean createFolder(String dirName);
	public boolean deleteFolder(String path);
	
	public boolean copyFile(UploadedFile destFile, String destFilename);
}
