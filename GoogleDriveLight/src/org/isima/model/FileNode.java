package org.isima.model;

import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

public class FileNode extends DefaultTreeNode {

	private static final long serialVersionUID = 1L;
	
	public FileNode(Object data, TreeNode parent) {
		super(data, parent);
	}
	
	public FileNode(String type, Object data, TreeNode parent) {
		super(type, data, parent);
	}
	
	public FileNode() {
		super();
	}
	
	public TreeNode searchFile(FileFilter file) {
		
		List<TreeNode> matchFiles = new ArrayList<TreeNode>();
		
		_search(this, file, matchFiles);
		
		return matchFiles.get(0);
	}
	
	public List<TreeNode> search(FileFilter file) {
		
		List<TreeNode> matchFiles = new ArrayList<TreeNode>();
		
		_search(this, file, matchFiles);
		
		return matchFiles;
	}
	
	private void _search(TreeNode parentNode, FileFilter file, List<TreeNode> list) {		
		
		for (TreeNode childnode : parentNode.getChildren()) {
		
			FileInfos fileInfos = (FileInfos)childnode.getData();
			
			if (file.accept(fileInfos.getFile())) {
			
				list.add(childnode);				
			}
			_search(childnode, file, list);
		}		
	}

	public void deleteChildNode(TreeNode fileNode) {
		
		/* Supprime tous les fils du noeuds.*/
		fileNode.getChildren().clear();
		
		/* Supprime le noeud. */
        getChildren().remove(fileNode);
        
        fileNode.setParent(null);
        fileNode = null;
	}
		
	@Override
	public boolean isLeaf() {
		
		boolean isLeaf = true;
		
		for (TreeNode node : getChildren()) {
			
			FileInfos file = (FileInfos) node.getData();
			
			if (file.isDirectory()) {
				isLeaf = false;
				break;
			}
			else if (file.isFile())		
				isLeaf = true;
		}
		
		if (((FileInfos)getData()).isFile())
			isLeaf = true;
		
		return isLeaf;		
	}

	public FileNode appendChild(FileInfos file) {
		
		FileNode node = new FileNode("default", file, this);
		
		/* Tri des fils afin de conserver l'ordre lexicographique.*/
		Collections.sort(getChildren(), new Comparator<TreeNode>() {
			
			@Override
			public int compare(TreeNode t1, TreeNode t2) {
				
				FileInfos f1 = (FileInfos) t1.getData();
				FileInfos f2 = (FileInfos) t2.getData();
				
				int ret = 0;
				
				if ((f1.isDirectory() && f2.isDirectory()) ||
					(f1.isFile() && f2.isFile()))
					ret = f1.getName().compareToIgnoreCase(f2.getName());
				else
					if (f1.isDirectory() && f2.isFile())
						ret = -1;
					else if (f1.isFile() && f2.isDirectory())
						ret = 1;

				/* Comparaison des noms de fichiers. */
				return ret;
			}
		});
		
		return node;
	}
}
