package org.isima.ui.utils;

import java.io.File;
import java.io.IOException;

import javax.faces.context.FacesContext;
import org.isima.ui.faces.bean.view.FileListBean;

public class FileOperations {
	
	static public void createFile (String filename)
	{
		FileListBean flb = (FileListBean)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("fileListBean");
		String path = flb.getCurrentDirectory();
		
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
