package org.isima.services;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public class FileOperationService implements IFileOperationService, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public void createNewFile(String filename) {
		
		File newFile = new File(filename);
		try 
		{
			newFile.createNewFile();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	@Override
	public void deleteFile(String filename) {
		
		System.out.println("debug : deleting file : " + filename);
		File file = new File(filename);
		file.delete();
	}
}
