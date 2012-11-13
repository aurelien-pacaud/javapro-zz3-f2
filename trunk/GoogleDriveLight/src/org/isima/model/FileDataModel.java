package org.isima.model;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

public class FileDataModel extends ListDataModel<FileInfos> implements SelectableDataModel<FileInfos> {
	
	@Override
	public FileInfos getRowData(String key) {
		
		List<FileInfos> files = (List<FileInfos>)getWrappedData();
        
        for(FileInfos file : files) {  
        	
            if(file.getPath().equals(key))  
                return file;  
        }  
          
        return null;
	}

	@Override
	public Object getRowKey(FileInfos file) {
		
		return file.getPath();
	}
	
	public FilterDataModel filterType(FileInfos.FileType type) {
		
		List<FileInfos> filteredFiles = new ArrayList<FileInfos>();
		
		for (FileInfos file : (List<FileInfos>)getWrappedData()) {
			
			if (file.getType() == type)				
				filteredFiles.add(file);
		}
		
		FilterDataModel filterModel = new FilterDataModel();
		filterModel.setWrappedData(filteredFiles);
		
		return filterModel;		
	}
	
	public FilterDataModel filterName(String pattern) {
		
		List<FileInfos> filteredFiles = new ArrayList<FileInfos>();
		
		for (FileInfos file : (List<FileInfos>)getWrappedData()) {
			
			if (file.getName().matches(pattern))				
				filteredFiles.add(file);
		}
		
		FilterDataModel filterModel = new FilterDataModel();
		filterModel.setWrappedData(filteredFiles);
		
		return filterModel;		
	}

}
