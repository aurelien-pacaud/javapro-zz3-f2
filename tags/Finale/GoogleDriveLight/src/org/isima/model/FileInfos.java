package org.isima.model;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FileInfos implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static Map<String, FileType> types = new HashMap<String, FileInfos.FileType>();
	
	static {
		
		types.put(".txt"   , FileType.TXT);
		types.put(".pdf"   , FileType.PDF);
		types.put(".zip"   , FileType.ARCHIVE);
		types.put(".tar"   , FileType.ARCHIVE);
		types.put(".tar.gz", FileType.ARCHIVE);
		types.put(".tgz"   , FileType.ARCHIVE);
		types.put(".png"   , FileType.IMG);
		types.put(".gif"   , FileType.IMG);
		types.put(".jpg"   , FileType.IMG);
		types.put(".bmp"   , FileType.IMG);
	}
	
	public enum FileType {
	    TXT, ARCHIVE, PDF, FOLDER, FILE, IMG
	}	
	
	private String path;
	private String name;
	private File file;
	
	public FileInfos(String path) {	
		
		setPath(path);
		name = file.getName();
	}
	
	public FileInfos(String name, String path) {	
		
		this.name = name;
		setPath(path);
	}
	
	public String getName() {
		
		return name;
	}
	
	public void setPath(String path) {
		
		this.file = new File(path);	
		this.path = file.getAbsolutePath();
	}
	
	public String getPath() {
		
		return path;
	}	
	
	public FileType getType() {
		
		FileType type = FileType.FILE;		
				
		if (file.isDirectory()) {
			
			type = FileType.FOLDER;
		}
		else {

			String name = getName();
			int dotPos = name.lastIndexOf('.');
						
			if (dotPos != -1) {
			
				String ext = name.substring(dotPos).toString().toLowerCase();
				
				if (types.containsKey(ext))
					type = types.get(ext);
			}
		}
		
		return type;
	}
	
	
	public Date getLastModified() {
		
		return new Date(file.lastModified());
	}
	
	public double getSize() {
		
		return (file.length() / (2.0 * 1024)) ;
	}
		
	
	public String getOwner() {
		
		return "N/A";
	}
		
	
	public boolean isDirectory() {
		
		return (getType() == FileType.FOLDER);
	}
	
	public boolean isFile() {
		
		return (getType() != FileType.FOLDER);
	}
	
	public String toString() {
		
		return getName();
	}
	
	public boolean isHidden() {
		
		return file.isHidden();
	}
	
	public File getFile() {
		return file;
	}
}
