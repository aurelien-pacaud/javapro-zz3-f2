package org.isima.services;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public class FileOperationService implements IFileOperationService, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public void createNewFile(String filename) {
		
		File file = new File(filename);
		
		try {
			
			file.createNewFile();
		} 
		catch (IOException e) {
			
			e.printStackTrace();
		}
	}

	@Override
	public void deleteFile(String filename) {
		
		File file = new File(filename);
		file.delete();
	}

	@Override
	public void createFolder(String dirName) {
		
		File file = new File(dirName);		
		file.mkdir();		
	}
}
