package org.isima.testdrive;

import java.net.URI;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.representation.Form;

public class TestDrive {
	
	public static void main(String[] args) {
	
	    ClientConfig config = new DefaultClientConfig();
	    Client client = Client.create(config);
	    WebResource service = client.resource(getBaseURI());
	   
	    /* Test methode GET pour le listing d'un repertoire */
	    System.out.println(service.path("drive").path("list").accept(MediaType.TEXT_HTML).get(ClientResponse.class).toString());
	    System.out.println(service.path("drive").path("list").accept(MediaType.TEXT_HTML).get(String.class) + "\n");
	   
	    /* Test methode POST pour la creation d'un fichier */
	    Form form = new Form();
	    form.add("path", "/tmp/testAddFile.txt");
	    ClientResponse response = service.path("drive").path("add").type(MediaType.APPLICATION_FORM_URLENCODED).post(ClientResponse.class, form);
	    System.out.println(response);
	    System.out.println(response.getEntity(String.class));
	}
	
	private static URI getBaseURI() {
		
		    return UriBuilder.fromUri("http://localhost:8080/DriveWebService").build();
	}

}
