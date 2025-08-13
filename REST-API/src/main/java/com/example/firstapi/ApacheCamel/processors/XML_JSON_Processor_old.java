package com.example.firstapi.ApacheCamel.processors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;


public class XML_JSON_Processor_old implements Processor  {

    @Override
public void process(Exchange exchange) throws Exception {
        String xmlInput = exchange.getIn().getBody(String.class);

        // Convert XML to JSON using Jackson
        XmlMapper xmlMapper = new XmlMapper();
        ObjectMapper jsonMapper = new ObjectMapper();
        jsonMapper.enable(SerializationFeature.INDENT_OUTPUT);

        Object xmlObject = xmlMapper.readValue(xmlInput, Object.class);
        String jsonOutput = jsonMapper.writeValueAsString(xmlObject);

        exchange.getIn().setBody(jsonOutput);
    }
}
