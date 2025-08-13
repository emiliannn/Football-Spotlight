package com.example.firstapi.web;


import com.example.firstapi.model.*;
import com.example.firstapi.service.XMLService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.function.ServerResponse;

import javax.validation.Valid;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.util.List;




//local db controller
@RestController
public class Controller1 {
    private final XMLService XMLService;

    Controller1(XMLService XMLService){
        this.XMLService = XMLService;
    }

    @PutMapping(value = "/update/manager/{id}",  consumes={MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<String> updateManager(@PathVariable Integer id,  @RequestBody Manager manager) throws XPathExpressionException, TransformerException {
        return XMLService.updateManager(id, manager);
    }



    @GetMapping(value = "/managers", produces = MediaType.APPLICATION_XML_VALUE)
    public ManagersList getManagers(){
        ManagersList managers = XMLService.getManagers();
        if(managers.getManagers() == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return managers;
    }



    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping(value ="/contracts/add",  produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_XML_VALUE},consumes={MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<String> addContract (@RequestBody @Valid Contract contract) throws IOException, TransformerException {
        return XMLService.updateContract(contract);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    @GetMapping( value = "/players/{id}/contractOption", produces = MediaType.APPLICATION_XML_VALUE)
    public ContractOption getContractOption(@PathVariable String id){
        ContractOption contractOption = XMLService.getPlayerContractOption(Integer.valueOf(id));
        if(contractOption == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return contractOption;
    }

    @GetMapping( value = "/players", produces = MediaType.APPLICATION_XML_VALUE)
    public PlayersList get(){
        PlayersList players = XMLService.getPlayers();
        if(players.getPlayers() == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return players;
    }

    @GetMapping(value = "/players/citizenship/{citizenship}/foot/{foot}", produces = MediaType.APPLICATION_XML_VALUE)
    public PlayersList get(@PathVariable String citizenship, @PathVariable String foot){
        PlayersList players = XMLService.getPlayers(citizenship, foot);
        if(players.getPlayers() == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return players;
    }

    @GetMapping(value = "/players/{fullName}", produces = MediaType.APPLICATION_XML_VALUE)
    public PlayersList get(@PathVariable String fullName){
        PlayersList players = XMLService.getPlayers(fullName);
        if(players.getPlayers() == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return players;
    }

    @PostMapping(value ="/players/add",  produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_XML_VALUE},consumes={MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<String> create (@RequestBody @Valid Player player) throws IOException, TransformerException {
        return XMLService.addPlayer(player);
    }

    @DeleteMapping(value ="/players/{id}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> delete(@PathVariable Integer id) throws TransformerException {
        return XMLService.removePlayer(id);
    }
}