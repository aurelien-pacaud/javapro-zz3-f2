package org.isima.ui.utils;

import java.io.File;
import java.io.IOException;

public class FileOperations {
	
	static public void createFile (String filename)
	{
		
		String path = "";
		
		File newFile = new File(path,filename);
		try 
		{
			newFile.createNewFile();
		} 
		catch (IOException e) 
		{
			System.err.println(e.getMessage());
			System.exit(1);
		}
	}
}
