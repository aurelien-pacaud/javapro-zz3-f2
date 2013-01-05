package org.isima.ui.utils;

import java.io.File;
import java.io.FileFilter;

public class FilePathFilter implements FileFilter {

	private String path;
	
	public FilePathFilter(String path) {
		this.path = path;
	}
	
	@Override
	public boolean accept(File file) {
	
		return file.getPath().equals(path);
	}
}
