package cxftest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.cxf.jaxrs.ext.MessageContext;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;
import org.apache.cxf.jaxrs.utils.multipart.AttachmentUtils;

public class TaskApi {
	@POST
	@Path("")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public void createTask(
		@Multipart(value = "title", type = "text/plain", required = false) final String title,
		@Multipart(value = "description", type = "text/plain", required = false) final String description,
		@javax.ws.rs.core.Context final MessageContext mc) {

		/* param well filled */
		System.out.println("title: " + title); 
		// >title: myTitle
		System.out.println("description: " + description); 
		// >description: myDescription

		/* first attachment of attachment map is empty */
		final MultivaluedMap<String, String> attachmentMap = AttachmentUtils.populateFormMap(mc);
		System.out.println("The set is: " + attachmentMap.entrySet()); 
		// >The set is: [title=[], description=[myDescription]]
	}
}
