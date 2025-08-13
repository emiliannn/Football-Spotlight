package com.example.firstapi.ApacheCamel.routes.active;
import com.example.firstapi.ApacheCamel.processors.FileProcessor;
import com.example.firstapi.ApacheCamel.processors.XMLProcessor;
import com.example.firstapi.service.XMLService;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import javax.xml.xpath.XPathExpressionException;


@Component
public class FileTransferRoute  extends RouteBuilder {

    public XMLService XMLService;
    @Autowired
    public FileTransferRoute () throws XPathExpressionException {
        this.XMLService = new XMLService();
    }
    private static final String SOURCE_FOLDER =
            "src/main/ASSETS/source-folder";
    private static final String DESTINATION_FOLDER =
            "src/main/ASSETS/destination-folder";

    @Override
    public void configure() throws Exception {
        from("file:" + SOURCE_FOLDER + "?delete=true&delay=15000") // Source folder
                .process(new FileProcessor())
                .to("file:" + DESTINATION_FOLDER); // Destination folder
    }


}