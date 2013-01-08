package org.isima.testdrive;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.ws.rs.core.MediaType;

import org.junit.Test;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;
import com.sun.jersey.test.framework.spi.container.TestContainerFactory;
import com.sun.jersey.test.framework.spi.container.http.HTTPContainerFactory;

import static org.junit.Assert.assertEquals;

public class TestDriveWithJerseyTestFramework extends JerseyTest {

	
	public static final String PACKAGE_NAME = "org.isima.jaxrs";
	private WebResource service;
	
	public TestDriveWithJerseyTestFramework() {
		super(new WebAppDescriptor.Builder(PACKAGE_NAME).build());
	}
	
	@Override
	protected TestContainerFactory getTestContainerFactory() {
		
		return new HTTPContainerFactory();
	}
	
	@Test
	public void testGet() throws UnsupportedEncodingException {
		
		service = resource().path("drive").path("list");
		ClientResponse responseGet = service.accept(MediaType.TEXT_HTML).get(ClientResponse.class);
		assertEquals(200, responseGet.getStatus());
	}
	
	@Test
	public void testPost () throws UnsupportedEncodingException {
		
		String urlEncoded = null;
	    urlEncoded = URLEncoder.encode("/tmp/toto.txt","UTF-8");
		
	    ClientResponse responsePost = resource().path("drive").path("add").path(urlEncoded).type(MediaType.APPLICATION_FORM_URLENCODED).post(ClientResponse.class);
	    assertEquals(200, responsePost.getStatus());
	}
	
	@Test
	public void testDelete () throws UnsupportedEncodingException {
		
		String urlEncoded = null;
	    urlEncoded = URLEncoder.encode("/tmp/toto.txt","UTF-8");
		
	    ClientResponse responseDelete = resource().path("drive").path("delete").path(urlEncoded).type(MediaType.APPLICATION_FORM_URLENCODED).delete(ClientResponse.class);
	    assertEquals(200, responseDelete.getStatus());
	}
}
