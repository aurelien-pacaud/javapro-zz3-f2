package org.isima.jaxrs;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.PathParam;

import org.isima.model.FileInfos;
import org.isima.services.FileListerService;
import org.primefaces.model.TreeNode;

@Path("/drive")
public class Drive {
	
	  // This method is called if HTML is request
	  @GET
	  @Produces(MediaType.TEXT_HTML)
	  @Path("list/{drivepath}")
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	  public String getDriveContent(@PathParam("drivepath") String drivepath) {
		 
		  FileListerService f = new FileListerService();
		  StringBuilder str = new StringBuilder();
		  		  
		  str.append("Drive <br />");
		  buildTree(f.getTree(drivepath), 1, str);
		  
	    return str.toString();
	  }
	  
	  /**
	   * Repond a un appel en POST, et va permettre de creer un fichier
	   * dans le filesystem.
	   * @param path : chemin + nom du fichier a creer
	   * @return un message indiquant le succes ou l'echec de l'operation
	   */
	  @POST
	  @Path("add/file/{filepath}")
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	  public Response createNewFile (@PathParam("filepath") String filepath) {

		  FileListerService f = new FileListerService();
		  
		  boolean ret = f.createNewFile(filepath);
		  
		  return Response.status(ret ? Response.Status.OK : Response.Status.UNAUTHORIZED).build();
	  }
	  
	  @DELETE
	  @Path("delete/file/{filepath}")
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	  public Response deleteFile (@PathParam("filepath") String filepath) {
		  
		  FileListerService f = new FileListerService();
		  
		  boolean ret = f.deleteFile(filepath);
		  
		  return Response.status(ret ? Response.Status.OK : Response.Status.UNAUTHORIZED).build();
	  }
	  
	  /***
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
