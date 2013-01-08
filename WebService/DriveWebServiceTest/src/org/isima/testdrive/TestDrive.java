package org.isima.testdrive;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class TestDrive {
	
	public static void main(String[] args) {
	
		ClientConfig config = new DefaultClientConfig();
	    Client client = Client.create(config);
	    WebResource service = client.resource(getBaseURI());
	   
	    /* Test methode GET pour le listing d'un repertoire */
	    System.out.println(service.path("drive").path("list").accept(MediaType.TEXT_HTML).get(ClientResponse.class).toString());
	    System.out.println(service.path("drive").path("list").accept(MediaType.TEXT_HTML).get(String.class) + "\n");
	   
	    /* Test methode POST pour la creation d'un fichier */
	    String urlEncoded = null;
	    try 
	    {
			urlEncoded = URLEncoder.encode("/tmp/toto.txt","UTF-8");
		} 
	    catch (UnsupportedEncodingException e) 
	    {
			e.printStackTrace();
		}
	    
	    ClientResponse responsePost = service.path("drive").path("add").path(urlEncoded).type(MediaType.APPLICATION_FORM_URLENCODED).post(ClientResponse.class);
	    System.out.println(responsePost);
	    System.out.println(responsePost.getEntity(String.class) + "\n");
	    
	    /* Test methode DELETE pour la suppression d'un fichier */
	    ClientResponse responseDelete = service.path("drive").path("delete").path(urlEncoded).type(MediaType.APPLICATION_FORM_URLENCODED).delete(ClientResponse.class);
	    System.out.println(responseDelete);
	    System.out.println(responseDelete.getEntity(String.class));
	}
	
	private static URI getBaseURI() {
		
		    return UriBuilder.fromUri("http://localhost:8080/DriveWebService").build();
	}

}
