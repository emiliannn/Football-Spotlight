package com.example.firstapi.web;


import com.example.firstapi.model.*;

import com.example.firstapi.service.ExistDBOperationsService;
import com.example.firstapi.service.ExistService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;

import javax.validation.Valid;
import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



//eXist-db controller
@RestController
public class Controller2 {
    private final ExistService existService;

    Controller2(ExistService existService){

        this.existService = existService;
    }

    @PutMapping(value = "exist/update/manager/{id}",  consumes={MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<String> updateManager(@PathVariable Integer id, @RequestBody Manager manager) throws XPathExpressionException, TransformerException, XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException, JsonProcessingException {
        return existService.updateManager(manager);
    }



    @GetMapping(value = "exist/managers", produces = MediaType.APPLICATION_XML_VALUE)
    public ManagersList getManagers() throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException, JsonProcessingException, JAXBException {
        ManagersList managers = (ManagersList) existService.getManagers();
        if(managers.getManagers() == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return managers;
    }



    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping(value ="/exist/contracts/add",  produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_XML_VALUE},consumes={MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<String> addContract (@RequestBody @Valid String contract) throws IOException, TransformerException, XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        return existService.addContract(contract);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    @GetMapping( value = "/exist/players/{id}/contractOption", produces = MediaType.APPLICATION_XML_VALUE)
    public ContractOption getContractOption(@PathVariable String id) throws XMLDBException, ClassNotFoundException, JsonProcessingException, InstantiationException, IllegalAccessException {
        ContractOption contractOption = existService.getPlayerContractOption(Integer.valueOf(id));
        if(contractOption.getContractOption() == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return contractOption;
    }

    @GetMapping( value = "/exist/players", produces = MediaType.APPLICATION_XML_VALUE)
    public PlayersList get() throws XMLDBException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        PlayersList players = existService.getPlayers();
        if(players.getPlayers() == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return players;
    }


    @GetMapping(value = "/exist/players/citizenship/{citizenship}/foot/{foot}", produces = MediaType.APPLICATION_XML_VALUE)
    public PlayersList get(@PathVariable String citizenship, @PathVariable String foot) throws XMLDBException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        PlayersList players = existService.getPlayers(citizenship, foot);
        if(players.getPlayers() == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return players;
    }

    @GetMapping(value = "/exist/players/{fullName}", produces = MediaType.APPLICATION_XML_VALUE)
    public PlayersList get(@PathVariable String fullName) throws XMLDBException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        PlayersList players = existService.getPlayers(fullName);
        if(players.getPlayers() == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return players;
    }

    @PutMapping(value ="/exist/players/add",  produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_XML_VALUE},consumes={MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<String> create (@RequestBody @Valid String player) throws IOException, TransformerException, XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        return existService.addPlayer(player);
    }

    @PostMapping(value ="/exist/players/update",  produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_XML_VALUE},consumes={MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<String> updatePlayer (@RequestBody @Valid Player player) throws IOException, TransformerException, XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        return existService.updatePlayer(player);
    }

    @DeleteMapping(value ="/exist/players/{id}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> delete(@PathVariable Integer id) throws TransformerException, XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        return existService.removePlayer(id);
    }


}