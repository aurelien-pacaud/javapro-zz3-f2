package org.isima.jaxrs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.isima.model.FileInfos;
import org.isima.services.FileListerService;
import org.primefaces.model.TreeNode;

@Path("/drive")
public class Drive {
	
	  // This method is called if HTML is request
	  @GET
	  @Produces(MediaType.TEXT_HTML)
	  public String getDriveContent() {
		 
		  FileListerService f = new FileListerService();
		  StringBuilder str = new StringBuilder();
		  		  
		  str.append("Drive <br />");
		  buildTree(f.getTree("/tmp/"), 1, str);
		  
	    return str.toString();
	  }
	  
	  /***
	   * 
	   * Fonction récursive permettant d'afficher l'arborescence du drive.
	   * 
	   * @param node Noeud courant dont on va traiter les fils.
	   * @param level Niveau du noeud afin de savoir comment l'afficher.
	   * @param str String contenant l'arborescence en cours de construction.
	   * @return L'arborescence du drive.
	   */
	  private StringBuilder buildTree(TreeNode node, int level, StringBuilder str) {
		  
		  for (TreeNode childNode : node.getChildren()) {

			  if (level > 1) {
				  
				  for (int j = 0; j < (level - 1) * 4; ++j) {
					  
					  if (j % 4 == 0)
						  str.append("&#9474;");
					  
					  str.append("&nbsp;");
				  } 
			  }
			  			  
			  str.append(String.format("&#9492;&#9472;&#9472; %s <br />", ((FileInfos)childNode.getData()).getName()));	
			  buildTree(childNode, level + 1, str);
		  }
		  
		  return str;
	  }
}