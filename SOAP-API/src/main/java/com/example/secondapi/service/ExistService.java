package com.example.secondapi.service;


import com.example.api.second_api.ManagerType;
import com.example.api.second_api.Managers;
import com.example.api.second_api.PlayerType;
import com.example.api.second_api.Players;
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
public class ExistService {
    ExistDBOperationsService existDBOperationsService;
    String collectionPath = "my-data";

    ExistService(ExistDBOperationsService existDBOperationsService){
        this.existDBOperationsService = existDBOperationsService;
    }


    public Managers getManagers() throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException, JsonProcessingException, JAXBException {
        String XQuery = "for $x in doc('my-data/DB2.xml')//managers/manager return $x";
        ResourceSet resourceSet = existDBOperationsService.executeXQuery(collectionPath, XQuery);
        ResourceIterator i = resourceSet.getIterator();
        Resource res = null;

        XmlMapper xmlMap = new XmlMapper();
        Managers managers = new Managers();

        while(i.hasMoreResources()) {
                try {
                    res = i.nextResource();

                    ManagerType manager = xmlMap.readValue(res.getContent().toString(), ManagerType.class);
                    managers.getManagers().add(manager);

                } finally {
                    //dont forget to cleanup resources
                    try { ((EXistResource)res).freeResources(); } catch(XMLDBException xe) {xe.printStackTrace();}
                }
            }
        return managers;
    }

    public ResponseEntity<String> updateManager(Integer id, String manager) throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String XQuery = "let $manager:= doc('my-data/DB2.xml')//managers/manager[@id="+id+"]\n" +
                " return update insert " + manager + " into $manager";

        existDBOperationsService.executeXQuery(collectionPath, XQuery);

        return ResponseEntity.ok("Succes!");
    }

    public ResponseEntity<String> addContract(String contract) throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String XQuery = "let $contracts:= doc('my-data/DB2.xml')//contracts" +
                " return update insert " + contract + " into $contracts";

        existDBOperationsService.executeXQuery(collectionPath, XQuery);

        return ResponseEntity.ok("Succes!");
    }

//    public  getPlayerContractOption(Integer playerID) throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException, JsonProcessingException {
//        String XQuery = "let $db := doc('my-data/DB2.xml') let $contractOption:= $db//contract[@id=$db//players/player["+ playerID.toString() +"]/@contractID]/contractOption return if($contractOption)then <contract><contractOption>{data($contractOption)}</contractOption></contract> else <contract><contractOption>empty</contractOption></contract>";
//
//        ResourceSet resourceSet = existDBOperationsService.executeXQuery(collectionPath, XQuery);
//        ResourceIterator i = resourceSet.getIterator();
//        Resource res = null;
//
//        XmlMapper xmlMap = new XmlMapper();
//
//        ContractOption contractOption = new ContractOption();
//        while (i.hasMoreResources()) {
//            try {
//                res = i.nextResource();
//
//                contractOption = xmlMap.readValue(res.getContent().toString(), ContractOption.class);
//
//
//            } finally {
//                //dont forget to cleanup resources
//                try {
//                    ((EXistResource) res).freeResources();
//                } catch (XMLDBException xe) {
//                    xe.printStackTrace();
//                }
//            }
//        }
//        return contractOption;
//    }

    public Players getPlayers() throws XMLDBException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String XQuery = "for $x in doc('my-data/DB2.xml')//players/player return $x";
        ResourceSet resourceSet = existDBOperationsService.executeXQuery(collectionPath, XQuery);
        ResourceIterator i = resourceSet.getIterator();
        Resource res = null;

        XmlMapper xmlMap = new XmlMapper();
        Players players = new Players();

        while(i.hasMoreResources()) {
            try {
                res = i.nextResource();

                StringReader stringReader = new StringReader(res.getContent().toString());
                PlayerType player = xmlMap.readValue(stringReader, PlayerType.class);
                players.getPlayers().add(player);

            } finally {
                //dont forget to cleanup resources
                try { ((EXistResource)res).freeResources(); } catch(XMLDBException xe) {xe.printStackTrace();}
            }
        }
        return players;
    }










    ///////////nd method to get players.
//    public Resource retrievePlayers(String citizenship, String foot) throws XMLDBException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
//        String XQuery = "let $players:= doc('my-data/DB2.xml')//players\n" +
//                "where  $players/player/citizenship='"+ citizenship + "' and $players/player/foot='" + foot + "'\n" +
//                "    return $players";
//        ResourceSet resourceSet = existDBOperationsService.executeXQuery(collectionPath, XQuery);
//        ResourceIterator i = resourceSet.getIterator();
//        Resource res = i.nextResource();
//        return res;
//    }


//    public PlayersList getPlayers(String citizenship, String foot) throws XMLDBException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException{
//        String XQuery = "let $db:= doc('my-data/DB2.xml')\n" +
//                "for $player in $db//players/player \n" +
//                "where $player/citizenship = '"+citizenship+"' and $player/foot = '"+foot+"'\n" +
//                "return $player";
//        ResourceSet resourceSet = existDBOperationsService.executeXQuery(collectionPath, XQuery);
//        ResourceIterator i = resourceSet.getIterator();
//        Resource res = null;
//
//        XmlMapper xmlMap = new XmlMapper();
//        PlayersList players = new PlayersList();
//
//        while(i.hasMoreResources()) {
//            try {
//                res = i.nextResource();
//
//                StringReader stringReader = new StringReader(res.getContent().toString());
//                Player player = xmlMap.readValue(stringReader, Player.class);
//                players.getPlayers().add(player);
//
//            } finally {
//                //dont forget to cleanup resources
//                try { ((EXistResource)res).freeResources(); } catch(XMLDBException xe) {xe.printStackTrace();}
//            }
//        }
//        return players;
//    }











    public Players getPlayers(String fullName) throws XMLDBException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException{
        String XQuery = "let $db:= doc('my-data/DB2.xml')\n" +
                "for $player in $db//players/player \n" +
                "where $player/fullName = '"+fullName+"' return $player";
        ResourceSet resourceSet = existDBOperationsService.executeXQuery(collectionPath, XQuery);
        ResourceIterator i = resourceSet.getIterator();
        Resource res = null;

        XmlMapper xmlMap = new XmlMapper();
        Players players = new Players();

        while(i.hasMoreResources()) {
            try {
                res = i.nextResource();

                StringReader stringReader = new StringReader(res.getContent().toString());
                PlayerType player = xmlMap.readValue(stringReader, PlayerType.class);
                players.getPlayers().add(player);

            } finally {
                //dont forget to cleanup resources
                try { ((EXistResource)res).freeResources(); } catch(XMLDBException xe) {xe.printStackTrace();}
            }
        }
        return players;
    }

//    public ResponseEntity<String> addPlayer(String player) throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
//        String XQuery = "let $players:= doc('my-data/DB2.xml')//players \n" +
//                "return update insert \n" +
//                player +
//                "into $players";
//
//
//        existDBOperationsService.executeXQuery(collectionPath, XQuery);
//
//        return ResponseEntity.ok("Succes!");
//    }
//

}
