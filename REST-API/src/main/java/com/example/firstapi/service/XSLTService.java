package com.example.firstapi.service;

import com.example.firstapi.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.exist.xmldb.EXistResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.*;
import org.xmldb.api.modules.CollectionManagementService;
import org.xmldb.api.modules.XMLResource;
import org.xmldb.api.modules.XPathQueryService;
import org.xmldb.api.modules.XQueryService;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.OutputKeys;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

@Service
public class XSLTService {

    String collectionPath = "my-data";
    ExistDBOperationsService existDBOperationsService;


    public XSLTService(){
//            this.existDBOperationsService = existDBOperationsService;
        this.existDBOperationsService = new ExistDBOperationsService();
    }




    public Resource retrievePlayers() throws XMLDBException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String XQuery = "xquery version \"3.1\";\n" +
                "let $input := doc(\"/db/my-data/DB.xml\")\n" +
                "let $xsl := doc(\"/db/my-data/xslTransformations/getPlayers.xsl\")\n" +
                "return\n" +
                "    transform:transform($input, $xsl, ())";
        ResourceSet resourceSet = existDBOperationsService.executeXQuery(collectionPath, XQuery);
        ResourceIterator i = resourceSet.getIterator();
        Resource res = i.nextResource();
        return res;
    }

    public Resource retrievePlayersWithUI() throws XMLDBException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String XQuery = "xquery version \"3.1\";\n" +
                "let $input := doc(\"/db/my-data/DB.xml\")\n" +
                "let $xsl := doc(\"/db/my-data/xslTransformations/getPlayersInfo.xsl\")\n" +
                "return\n" +
                "    transform:transform($input, $xsl, ())";
        ResourceSet resourceSet = existDBOperationsService.executeXQuery(collectionPath, XQuery);
        ResourceIterator i = resourceSet.getIterator();
        Resource res = i.nextResource();
        return res;
    }



}
