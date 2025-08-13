package com.example.firstapi.web;


import com.example.firstapi.model.*;
import com.example.firstapi.service.XMLService;
import com.example.firstapi.service.XSLTService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.XMLDBException;

import javax.validation.Valid;
import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;


//local db controller
@RestController
public class Controller3 {
    private final XSLTService xsltService;

    Controller3(XSLTService xsltService){
        this.xsltService = xsltService;
    }




    @GetMapping( value = "/exist/xslt/players", produces = MediaType.APPLICATION_XHTML_XML_VALUE)
    public Object get() throws XMLDBException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Resource xslt = xsltService.retrievePlayers();
        if(xslt == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return xslt.getContent();
    }

    @GetMapping( value = "/exist/xslt/ui/players", produces = MediaType.APPLICATION_XHTML_XML_VALUE)
    public Object retrievePlayersWithUI() throws XMLDBException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Resource xslt = xsltService.retrievePlayersWithUI();
        if(xslt == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return xslt.getContent();
    }


}