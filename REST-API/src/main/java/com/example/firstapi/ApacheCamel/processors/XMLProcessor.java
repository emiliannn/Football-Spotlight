package com.example.firstapi.ApacheCamel.processors;

import com.example.firstapi.service.XSLTService;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.xmldb.api.base.Resource;

import java.text.SimpleDateFormat;
import java.util.Date;


public class XMLProcessor implements Processor  {
    private final XSLTService xsltService;

    public XMLProcessor(){
                this.xsltService = new XSLTService();;
    }
@Override
public void process(Exchange exchange) throws Exception {
    Resource xmlResponse = xsltService.retrievePlayers();

    String xmlString = xmlResponse.getContent().toString();

    exchange.getIn().setBody(xmlString, String.class);
}
}
