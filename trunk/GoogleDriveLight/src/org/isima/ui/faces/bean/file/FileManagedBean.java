package org.isima.ui.faces.bean.file;

import javax.faces.bean.ManagedBean;

import org.isima.ui.utils.FileOperations;

/**
 * Permet la gestion des fichiers (ajout, suppression, ...)
 */
@ManagedBean
public class FileManagedBean {

	private String filename;
	
	public String getFilename ()
	{
		return filename;
	}
	
	public void setFilename (String filename)
	{
		this.filename = filename;
	}
	
	public void createFile ()
	{
		FileOperations.createFile(filename);
	}
}
