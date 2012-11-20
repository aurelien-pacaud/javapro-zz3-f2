package org.isima.services;

import java.io.Serializable;
import java.util.List;

import org.isima.model.FileInfos;
import org.isima.ui.utils.FileLister;
import org.primefaces.model.TreeNode;

public class FileListerService implements IFileService, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public TreeNode getTree(String path) {
		
		return FileLister.getTree(path);
	}

	@Override
	public List<FileInfos> getFiles(String dirPath) {
		
		return FileLister.getFiles(dirPath);
	}

}
