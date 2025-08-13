package com.example.firstapi.ApacheCamel.processors;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.exist.xmldb.RemoteXMLResource;

public class XML_JSON_Processor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        try{
            Object body = exchange.getIn().getBody();
            String jsonOutput = "";
            ObjectMapper jsonMapper = new ObjectMapper();
            if (body instanceof RemoteXMLResource) {
                // Extract XML content from RemoteXMLResource
                RemoteXMLResource xmlResource = (RemoteXMLResource) body;
//            String xmlContent = new String((byte[]) xmlResource.getContent());
                String xmlContent = (String) xmlResource.getContent();

                //Clean up XML content (remove invalid characters)
//            xmlContent = xmlContent.replaceAll("[^\\x20-\\x7e]", ""); // Remove non-printable ASCII characters


                // Convert XML to JSON using Jackson
                XmlMapper xmlMapper = new XmlMapper();
                jsonMapper = new ObjectMapper();
                jsonMapper.enable(SerializationFeature.INDENT_OUTPUT);

                Object xmlObject = xmlMapper.readValue(xmlContent, Object.class);

                jsonOutput = jsonMapper.writeValueAsString(xmlObject);
            }
            else{
                    jsonOutput = jsonMapper.writeValueAsString(body);
                }

                exchange.getIn().setBody(jsonOutput);
        }
        catch (Exception e){
            // Log a warning for unexpected body type
            exchange.getIn().setBody("Invalid body type received ");
            exchange.getIn().setHeader(Exchange.CONTENT_TYPE, "text/plain");
            exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, 500);
        }

    }
}
