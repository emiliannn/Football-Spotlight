package com.example.firstapi.ApacheCamel.routes.active;
import com.example.firstapi.ApacheCamel.processors.XMLProcessor;
import com.example.firstapi.service.XMLService;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.support.processor.idempotent.MemoryIdempotentRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import javax.xml.xpath.XPathExpressionException;
import java.text.SimpleDateFormat;
import java.util.Date;

    @Component
    public class WriteInFolderPlayers extends RouteBuilder {

        public XMLService XMLService;
        @Autowired
        public WriteInFolderPlayers() throws XPathExpressionException {
            this.XMLService = new XMLService();
        }


        private static final String SOURCE_FOLDER =
                "src/main/ASSETS/source-folder";
        private static final String DESTINATION_FOLDER =
                "src/main/ASSETS/destination-folder";


        @Override
        public void configure() throws Exception {
            from("timer:trigger?period=5000")
                    .to("direct:callSer");

            from("direct:callSer")
                    .process(new XMLProcessor())
                    .to("file:" + SOURCE_FOLDER  + "?fileName=players.xml");
        }
    }