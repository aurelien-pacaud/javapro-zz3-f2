package org.isima.testdrive;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.ws.rs.core.MediaType;

import org.junit.Test;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.LowLevelAppDescriptor;
import com.sun.jersey.test.framework.spi.container.TestContainerFactory;
import com.sun.jersey.test.framework.spi.container.http.HTTPContainerFactory;

import static org.junit.Assert.assertEquals;

public class TestDriveWithJerseyTestFramework extends JerseyTest {

	
	private static final String PACKAGE_NAME = "org.isima.jaxrs";
	private static final String DRIVE_FOLDER = "Drive";
	
	private String driveFolder;
	
	public TestDriveWithJerseyTestFramework() {
		super(new LowLevelAppDescriptor.Builder(PACKAGE_NAME).build());
	}
	
	@Override
	protected TestContainerFactory getTestContainerFactory() {
		
		return new HTTPContainerFactory();
	}
	
	@Override
	public void setUp() throws Exception {
		
		driveFolder = String.format("%s/%s", System.getProperty("java.io.tmpdir"), DRIVE_FOLDER);
		File file = new File(driveFolder);
		
		if (!file.exists())
			file.mkdir();
		
		super.setUp();
	}	
	
	@Test
	public void testGetTree() throws UnsupportedEncodingException {
		
		String urlEncoded = URLEncoder.encode(driveFolder, "UTF-8");
		
	    ClientResponse responseGet = resource().path("drive").path("list").path(urlEncoded)
									   .accept(MediaType.TEXT_HTML)
									   .get(ClientResponse.class);
			
		assertEquals(200, responseGet.getStatus());
	}
	
	@Test
	public void testCreateFilePass() throws UnsupportedEncodingException {
		
		String urlEncoded = URLEncoder.encode(String.format("%s/%s", driveFolder, "toto.txt"), "UTF-8");
		
	    ClientResponse responsePost = resource().path("drive").path("add").path("file").path(urlEncoded)
	    										.post(ClientResponse.class);
	    
	    assertEquals(200, responsePost.getStatus());
	}
	
	@Test
	public void testCreateFileFail() throws UnsupportedEncodingException {
		
		String urlEncoded = URLEncoder.encode(String.format("%s/%s", driveFolder, "toto.txt"), "UTF-8");
		
	    ClientResponse responsePost = resource().path("drive").path("add").path("file").path(urlEncoded)
	    										.post(ClientResponse.class);
	    
	    assertEquals(401, responsePost.getStatus());
	}
	
	@Test
	public void testDeleteFilePass() throws UnsupportedEncodingException {
		
		String urlEncoded = URLEncoder.encode(String.format("%s/%s", driveFolder, "toto.txt"), "UTF-8");
		
	    ClientResponse responseDelete = resource().path("drive").path("delete").path("file").path(urlEncoded)
	    										  .delete(ClientResponse.class);
	    
	    assertEquals(200, responseDelete.getStatus());
	}
	
	@Test
	public void testDeleteFileFail() throws UnsupportedEncodingException {
		
		String urlEncoded = URLEncoder.encode(String.format("%s/%s", driveFolder, "toto.txt"), "UTF-8");
		
	    ClientResponse responseDelete = resource().path("drive").path("delete").path("file").path(urlEncoded)
	    										  .delete(ClientResponse.class);
	    
	    assertEquals(401, responseDelete.getStatus());
	}
}
