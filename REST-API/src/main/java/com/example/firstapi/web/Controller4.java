package com.example.firstapi.web;


import com.example.firstapi.model.*;
import com.example.firstapi.service.ExistService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.xmldb.api.base.XMLDBException;

import javax.validation.Valid;
import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;


//eXist-db controller
@RestController
public class Controller4 {
    private final ExistService existService;

    Controller4(ExistService existService){

        this.existService = existService;
    }


    @PostMapping(value = "exist/update/user/",  consumes={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> updateUser(@RequestBody User user) throws XPathExpressionException, TransformerException, XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException, JsonProcessingException {
        return existService.updateUser(user);
    }
    
    @PutMapping(value = "exist/update/manager/",  consumes={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> updateManager(@RequestBody Manager manager) throws XPathExpressionException, TransformerException, XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException, JsonProcessingException {
        return existService.updateManager(manager);
    }


    @GetMapping(value = "json/exist/managers", produces = MediaType.APPLICATION_JSON_VALUE)
    public ManagersList getManagers() throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException, JsonProcessingException, JAXBException {
        ManagersList managers = (ManagersList) existService.getManagers();
        if(managers.getManagers() == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return managers;
    }


    @GetMapping(value = "json/exist/playersInfo", produces = MediaType.APPLICATION_JSON_VALUE)
    public PlayersAdditionalInfoList getplayersAdditionalInfo() throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException, IOException, JAXBException {
        PlayersAdditionalInfoList players = (PlayersAdditionalInfoList) existService.getPlayersAdditionalInfo();
        if(players.getPlayers() == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return players;
    }

    @DeleteMapping(value ="json/exist/delete/players/{id}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> deletePlayer(@PathVariable String id) throws TransformerException, XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        return existService.deletePlayer(id);
    }

    @DeleteMapping(value ="json/exist/delete/managers/{id}", consumes={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> deleteManager(@PathVariable String id) throws TransformerException, XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        return existService.deleteManager(id);
    }

    @GetMapping(value = "json/exist/footballClubs", produces = MediaType.APPLICATION_JSON_VALUE)
    public FootballClubs getFootballClubs() throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException, JsonProcessingException, JAXBException {
        FootballClubs footballClubs = (FootballClubs) existService.getFootballClubs();
        if(footballClubs.getFootballClubs() == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return footballClubs;
    }

//    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping(value ="/json/exist/contracts/add", consumes={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> addContract (@RequestBody  Contract contract) throws IOException, TransformerException, XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        return existService.addContract(contract);
    }


    @PostMapping(value ="/json/exist/footballClubs/add",  consumes={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> addfootballClub (@RequestBody @Valid FootballClub footballClub) throws IOException, TransformerException, XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        return existService.addFootballClub(footballClub);
    }
    @PostMapping(value ="/json/exist/players/add", consumes={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> addPlayer (@RequestBody @Valid Player player) throws IOException, TransformerException, XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        return existService.addPlayer(player);
    }

    @PostMapping(value ="/json/exist/managers/add",  consumes={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> addManager (@RequestBody @Valid Manager manager) throws IOException, TransformerException, XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        return existService.addManager(manager);
    }



    @GetMapping(value = "json/exist/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUser(@PathVariable int id) throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException, JsonProcessingException, JAXBException {
        User user = (User) existService.getUser(id);
        if(user == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return user;
    }

}