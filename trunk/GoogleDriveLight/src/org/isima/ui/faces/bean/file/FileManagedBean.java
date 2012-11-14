package org.isima.ui.faces.bean.file;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;

import org.isima.ui.utils.FileOperations;

/**
 * Permet la gestion des fichiers (ajout, suppression, ...)
 */
@ManagedBean
public class FileManagedBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
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
