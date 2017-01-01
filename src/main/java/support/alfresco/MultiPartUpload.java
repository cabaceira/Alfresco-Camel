package support.alfresco;

import java.io.File;

import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import java.io.FileNotFoundException;
import java.net.URI;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class MultiPartUpload implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		String filename = "file:///tmp/outbox/testFile.docx";
        final StringBody destination = new StringBody("workspace://SpacesStore/2ca7b7e1-085b-47ba-b008-a7dc4c4ef0a6", ContentType.MULTIPART_FORM_DATA);
        final StringBody siteId = new StringBody("test", ContentType.MULTIPART_FORM_DATA);
        final StringBody containerId = new StringBody("documentLibrary", ContentType.MULTIPART_FORM_DATA);
        final StringBody uploadDirectory = new StringBody("test1", ContentType.MULTIPART_FORM_DATA);

        URI uri = new URI(filename);
	    File file = new File(uri);
	    if (!file.exists()) {
	        throw new FileNotFoundException(String.format("File %s not found", filename));
	    }

	    MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
        multipartEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
		multipartEntityBuilder.addPart("siteid", siteId);
		multipartEntityBuilder.addPart("containerid", containerId);
		multipartEntityBuilder.addPart("destination", destination);
		multipartEntityBuilder.addPart("uploaddirectory", uploadDirectory);
        multipartEntityBuilder.addBinaryBody("filedata", file);
        exchange.getIn().setBody(multipartEntityBuilder.build()); 
	}
}	