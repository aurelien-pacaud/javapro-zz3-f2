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
	
	// This method is called if TEXT_PLAIN is request
	  @GET
	  @Produces(MediaType.TEXT_PLAIN)
	  public String sayPlainTextHello() {
		  
		  
		  return "Hello Jersey\n";
	  }

	  // This method is called if XML is request
	  @GET
	  @Produces(MediaType.TEXT_XML)
	  public String sayXMLHello() {
	    return "<?xml version=\"1.0\"?>" + "<hello> Hello Jersey" + "</hello>\n";
	  }

	  // This method is called if HTML is request
	  @GET
	  @Produces(MediaType.TEXT_HTML)
	  public String sayHtmlHello() {
		 
		  FileListerService f = new FileListerService();
		  StringBuilder str = new StringBuilder();
		  		  
		  str.append("Drive <br />");
		  buildTree(f.getTree("/tmp/"), 1, str);
		  
	    return str.toString();
	  }
	  
	  private StringBuilder buildTree(TreeNode node, int level, StringBuilder str) {
		  
		  for (TreeNode childNode : node.getChildren()) {

			  str.append("|");
			  if (level > 1) {
				  
				  for (int i = 0; i < (level - 1) * 3 + 3; ++i) {
					  str.append("&nbsp;");
				  }
				  str.append("|");
			  }
			  
			  for (int i = 0; i < 3; ++i) {
				  str.append("-");
			  }
			  
			  str.append(String.format(" %s <br />", ((FileInfos)childNode.getData()).getName()));	
			  buildTree(childNode, level + 1, str);
		  }
		  
		  return str;
	  }
}
