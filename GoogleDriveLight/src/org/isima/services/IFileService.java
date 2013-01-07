package org.isima.services;

import java.io.IOException;

import org.isima.model.FileNode;
import org.primefaces.model.UploadedFile;

public interface IFileService  {

	public FileNode getTree(final String path);
	
	public boolean createNewFile(String filename) throws IOException;
	public boolean deleteFile(String path) throws IOException;
	
	public boolean createFolder(String dirName);
	public boolean deleteFolder(String path) throws IOException;
	
	public boolean copyFile(UploadedFile destFile, String destFilename) throws IOException;
}
