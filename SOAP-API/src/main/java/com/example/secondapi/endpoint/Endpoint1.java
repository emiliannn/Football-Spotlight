package com.example.secondapi.endpoint;

import com.example.api.second_api.*;
import com.example.secondapi.service.ExistService;
import com.example.secondapi.service.XMLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.xmldb.api.base.XMLDBException;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;

@Endpoint
public class Endpoint1 {
    private static final String NAMESPACE_URI = "http://example.com/api/second-api";

    private XMLService xmlService;
    private ExistService existService;

    @Autowired
    public Endpoint1( XMLService xmlService, ExistService existService) {
        this.xmlService = xmlService;
        this.existService = existService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "ContractOptionRequest")
    @ResponsePayload
    public ContractOptionResponse getContractOption(@RequestPayload ContractOptionRequest playerID) {
        return xmlService.getPlayerContractOption(playerID.getPlayer().getPlayerID());
    }



    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "PlayersRequest")
    @ResponsePayload
    public Players getPlayers(@RequestPayload PlayersRequest request) throws DatatypeConfigurationException {
        return xmlService.getPlayers();
    }


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "PlayersByCFRequest")
    @ResponsePayload
    public Players getPlayers(@RequestPayload PlayersByCFRequest request) throws DatatypeConfigurationException {
        return xmlService.getPlayers(request.getCitizenship(), request.getFoot());
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "PlayersByNRequest")
    @ResponsePayload
    public Players getPlayers(@RequestPayload PlayersByNRequest request) throws DatatypeConfigurationException {
        return xmlService.getPlayers(request.getFullName());
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "PlayerRequest")
    @ResponsePayload
    public ServerResponse addPlayer(@RequestPayload PlayerType request) throws DatatypeConfigurationException, TransformerException {
        return xmlService.addPlayer(request);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "RemovePlayerRequest")
    @ResponsePayload
    public ServerResponse deletePlayer(@RequestPayload RemovePlayerRequest request) throws DatatypeConfigurationException, TransformerException {
        return xmlService.removePlayer(request.getPlayer().getPlayerID());
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "UpdateContract")
    @ResponsePayload
    public ServerResponse updateContract(@RequestPayload UpdateContract request) throws DatatypeConfigurationException, TransformerException {
        return xmlService.updateContract(request);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "UpdateManager")
    @ResponsePayload
    public ServerResponse updateManager(@RequestPayload UpdateManager request) throws DatatypeConfigurationException, TransformerException, XPathExpressionException {
        return xmlService.updateManager(request);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "ManagersRequest")
    @ResponsePayload
    public Managers getManagers(@RequestPayload ManagersRequest request) throws DatatypeConfigurationException, TransformerException, XPathExpressionException {
        return xmlService.getManagers();
    }






    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "ExistDBManagersRequest")
    @ResponsePayload
    public Managers getManagers2(@RequestPayload ExistDBManagersRequest request) throws DatatypeConfigurationException, XMLDBException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, JAXBException {
        return existService.getManagers();
    }


}