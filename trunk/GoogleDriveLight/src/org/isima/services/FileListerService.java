package org.isima.services;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.List;

import org.isima.model.FileInfos;
import org.isima.model.FileNode;
import org.isima.ui.utils.FileLister;
import org.primefaces.model.TreeNode;
import org.primefaces.model.UploadedFile;

public class FileListerService implements IFileService, Serializable {

	private static final long serialVersionUID = 1L;
	
	private FileNode model;
	private FileNode selectedNode;
	
	public FileListerService() {
		
		model = new FileNode();
	}
	
	@Override
	public FileNode getTree(final String path) {
		
		return FileLister.getTree(path);
	}

	@Override
	public List<TreeNode> getFiles(final String path) {
		
		List<TreeNode> list = model.search(new FileFilter() {
			
			@Override
			public boolean accept(File file) {
				return file.getPath().equals(path);
			}
		});
				
		return list.get(0).getChildren();
	}
	
	@Override
	public void createNewFile(String filename) {
		
		File file = new File(filename);
		
		try {
			
			file.createNewFile();
			selectedNode.appendChild(new FileInfos(filename));
		} 
		catch (IOException e) {
			
			e.printStackTrace();
		}
	}

	@Override
	public void deleteFile(TreeNode fileNode) throws IOException {
		
		File file = new File(((FileInfos)fileNode.getData()).getPath());
		
		FileUtils.deleteDirectory(file);
		
		selectedNode.deleteFile(fileNode);
	}

	@Override
	public void createFolder(String dirName) {
		
		File file = new File(dirName);				
		file.mkdir();	

		selectedNode.appendChild(new FileInfos(dirName));
	}

	@Override
	public void setSelectedNode(FileNode selectedNode) {
		this.selectedNode = selectedNode;		
	}

	@Override
	public void copyFile(UploadedFile file, String destFilename) throws IOException {

	    InputStream input = file.getInputstream();
	    OutputStream output = new FileOutputStream(new File(destFilename));

	    try {
	        IOUtils.copy(input, output);
	    } finally {
	        IOUtils.closeQuietly(input);
	        IOUtils.closeQuietly(output);
	    }
		
		selectedNode.appendChild(new FileInfos(destFilename));
	}
}
