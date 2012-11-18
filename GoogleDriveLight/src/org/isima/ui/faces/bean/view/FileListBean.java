package org.isima.ui.faces.bean.view;

import java.io.Serializable;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.isima.model.FileDataModel;
import org.isima.model.FileInfos;
import org.isima.ui.utils.FileLister;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.SelectEvent;

public class FileListBean extends HttpServlet implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private FileInfos selectedFile;
	private FileDataModel model;
	private String currentDirectory;
	
    public void init(ServletConfig config) throws ServletException {  
    	super.init(config);  
    }     
	
	public FileListBean() {
		
		model = new FileDataModel();
		
		//LoginManagedBean user = (LoginManagedBean)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginManagedBean");
		String userHome = (String)getServletContext().getAttribute("userHome");
		System.out.println("debug : userHome = " + userHome);
		setCurrentDirectory(userHome);
	}
	
	public String getCurrentDirectory() {
		
		return currentDirectory;
	}
	
	public void setCurrentDirectory(String currentDirectory) {
		
		this.currentDirectory = currentDirectory;			
		
		model.setWrappedData(FileLister.getFiles(currentDirectory));		
		model = model.filterType(FileInfos.FileType.FILE);		
	}

	public FileDataModel getModel() {			
		
		return model;
	}

	public FileInfos getSelectedFile() {
		return selectedFile;
	}

	public void setSelectedFile(FileInfos selectedFile) {
		this.selectedFile = selectedFile;
	}
	
	public void onRowSelect(SelectEvent event) {  
        
		selectedFile = (FileInfos)event.getObject();
    }
	
	public void onNodeSelect(NodeSelectEvent event) {  
     
		FileInfos file = (FileInfos) event.getTreeNode().getData();	
		setCurrentDirectory(file.getPath());
    }  
}