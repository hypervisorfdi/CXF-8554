package cxftest;

import java.io.ByteArrayInputStream;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.ContentDisposition;
import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;
import org.junit.jupiter.api.Test;

public class TaskApiTest {

	@Test
	public void test() {
		JAXRSServerFactoryBean serverFactory = new JAXRSServerFactoryBean();
		serverFactory.setAddress("http://localhost:8080");
		serverFactory.setResourceClasses(TaskApi.class);
		serverFactory.setResourceProvider(TaskApi.class, new SingletonResourceProvider(new TaskApi()));
		serverFactory.create();

		List<Attachment> atts = new LinkedList<Attachment>();
		ContentDisposition cd = new ContentDisposition("form-data; name=\"file\";filename=\"test.txt\"");
		atts.add(new Attachment("file", new ByteArrayInputStream("testContent".getBytes()), cd));
		atts.add(new Attachment("title", MediaType.TEXT_PLAIN, "title here"));
		atts.add(new Attachment("description", MediaType.TEXT_PLAIN, "description here"));
		MultipartBody body = new MultipartBody(atts);
		WebClient.create("http://localhost:8080").type(MediaType.MULTIPART_FORM_DATA_TYPE).post(body);
	}
}
