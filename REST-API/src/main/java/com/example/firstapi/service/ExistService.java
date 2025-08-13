package com.example.firstapi.service;

import com.example.firstapi.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.exist.xmldb.EXistResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.xmldb.api.base.*;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.StringReader;

@Service
public class ExistService {
    ExistDBOperationsService existDBOperationsService;
    String collectionPath = "my-data";

    public ExistService(){
        this.existDBOperationsService = new ExistDBOperationsService();
    }

    public ManagersList getManagers() throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException, JsonProcessingException, JAXBException {
        String XQuery = "for $x in doc('my-data/DB.xml')/database/managers/manager return $x";
        ResourceSet resourceSet = existDBOperationsService.executeXQuery(collectionPath, XQuery);
        ResourceIterator i = resourceSet.getIterator();
        Resource res = null;

        XmlMapper xmlMap = new XmlMapper();
        ManagersList managers = new ManagersList();

        while(i.hasMoreResources()) {
                try {
                    res = i.nextResource();

                    Manager manager = xmlMap.readValue(res.getContent().toString(), Manager.class);
                    managers.getManagers().add(manager);

                } finally {
                    //dont forget to cleanup resources
                    try { ((EXistResource)res).freeResources(); } catch(XMLDBException xe) {xe.printStackTrace();}
                }
            }
        return managers;
    }

    public FootballClubs getFootballClubs() throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException, JsonProcessingException, JAXBException {
        String XQuery = "for $x in doc('my-data/DB.xml')/database/footballClubs/footballClub return $x";
        ResourceSet resourceSet = existDBOperationsService.executeXQuery(collectionPath, XQuery);
        ResourceIterator i = resourceSet.getIterator();
        Resource res = null;

        XmlMapper xmlMap = new XmlMapper();
        FootballClubs footballClubs = new FootballClubs();

        while(i.hasMoreResources()) {
            try {
                res = i.nextResource();

                FootballClub footballClub = xmlMap.readValue(res.getContent().toString(), FootballClub.class);
                footballClubs.getFootballClubs().add(footballClub);

            } finally {
                //dont forget to cleanup resources
                try { ((EXistResource)res).freeResources(); } catch(XMLDBException xe) {xe.printStackTrace();}
            }
        }
        return footballClubs;
    }

    public ResponseEntity<String> updateManager(Manager manager) throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException, JsonProcessingException {
        String newValue = new XmlMapper().writeValueAsString(manager);
        String XQuery = "let $manager:= doc('my-data/DB.xml')//managers/manager[@id="+manager.getId()+"]\n" +

                " return update replace $manager with " + newValue ;

        existDBOperationsService.executeXQuery(collectionPath, XQuery);


        return ResponseEntity.ok("Succes!");
    }

    public ResponseEntity<String> addContract(String contract) throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String XQuery = "let $contracts:= doc('my-data/DB.xml')//contracts" +
                " return update insert " + contract + " into $contracts";

        existDBOperationsService.executeXQuery(collectionPath, XQuery);

        return ResponseEntity.ok("Succes!");
    }

    public ContractOption getPlayerContractOption(Integer playerID) throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException, JsonProcessingException {
        String XQuery = "let $db := doc('my-data/DB.xml') let $contractOption:= $db//contract[@id=$db//players/player["+ playerID.toString() +"]/@contractID]/contractOption return if($contractOption)then <contract><contractOption>{data($contractOption)}</contractOption></contract> else <contract><contractOption>empty</contractOption></contract>";

        ResourceSet resourceSet = existDBOperationsService.executeXQuery(collectionPath, XQuery);
        ResourceIterator i = resourceSet.getIterator();
        Resource res = null;

        XmlMapper xmlMap = new XmlMapper();

        ContractOption contractOption = new ContractOption();
        while (i.hasMoreResources()) {
            try {
                res = i.nextResource();

                contractOption = xmlMap.readValue(res.getContent().toString(), ContractOption.class);


            } finally {
                //dont forget to cleanup resources
                try {
                    ((EXistResource) res).freeResources();
                } catch (XMLDBException xe) {
                    xe.printStackTrace();
                }
            }
        }
        return contractOption;
    }

    public PlayersList getPlayers() throws XMLDBException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String XQuery = "for $x in doc('my-data/DB.xml')//players/player return $x";
        ResourceSet resourceSet = existDBOperationsService.executeXQuery(collectionPath, XQuery);
        ResourceIterator i = resourceSet.getIterator();
        Resource res = null;

        XmlMapper xmlMap = new XmlMapper();
        PlayersList players = new PlayersList();

        while(i.hasMoreResources()) {
            try {
                res = i.nextResource();

                StringReader stringReader = new StringReader(res.getContent().toString());
                Player player = xmlMap.readValue(stringReader, Player.class);
                players.getPlayers().add(player);

            } finally {
                //dont forget to cleanup resources
                try { ((EXistResource)res).freeResources(); } catch(XMLDBException xe) {xe.printStackTrace();}
            }
        }
        return players;
    }


    public PlayersAdditionalInfoList getPlayersAdditionalInfo() throws XMLDBException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String XQuery = "let $players := doc('my-data/DB.xml')//players/player\n" +
                "let $contracts := doc('my-data/DB.xml')/database/contracts\n" +
                "let $clubs := doc('my-data/DB.xml')/database/footballClubs\n" +
                "let $managers := doc('my-data/DB.xml')/database/managers\n" +
                "\n" +
                "for $player in $players\n" +
                "let $player-contractID := $player/@contractID\n" +
                "let $contract := $contracts/contract[@id = $player-contractID]\n" +
                "let $currentClubID := $contract/@currentClubID\n" +
                "let $previousClubID := $contract/@previousClubID\n" +
                "let $currentClub := $clubs/footballClub[@id = $currentClubID]\n" +
                "let $previousClub := $clubs/footballClub[@id = $previousClubID]\n" +
                "let $managerID := $currentClub/@managerID\n" +
                "let $manager := $managers/manager[@id = $managerID]\n" +
                "\n" +
                "return\n" +
                "<player id=\"{$player/@id}\">\n" +
                "    {$player/*}\n" +
                "    <currentClub>{$currentClub/name/text()}</currentClub>\n" +
                "    <previousClub>{$previousClub/name/text()}</previousClub>\n" +
                "    <manager>{$manager/fullName/text()}</manager>\n" +
                "</player>\n";
        ResourceSet resourceSet = existDBOperationsService.executeXQuery(collectionPath, XQuery);
        ResourceIterator i = resourceSet.getIterator();
        Resource res = null;

        XmlMapper xmlMap = new XmlMapper();
        PlayersAdditionalInfoList playersAdditionalInfoList  = new PlayersAdditionalInfoList();

        while(i.hasMoreResources()) {
            try {
                res = i.nextResource();

                StringReader stringReader = new StringReader(res.getContent().toString());
                PlayerAdditionalInfo playerAdditionalInfo = xmlMap.readValue(stringReader, PlayerAdditionalInfo.class);
                playersAdditionalInfoList.getPlayers().add(playerAdditionalInfo);

            } finally {
                //dont forget to cleanup resources
                try { ((EXistResource)res).freeResources(); } catch(XMLDBException xe) {xe.printStackTrace();}
            }
        }
        return playersAdditionalInfoList;
    }













    public PlayersList getPlayers(String citizenship, String foot) throws XMLDBException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException{
        String XQuery = "let $db:= doc('my-data/DB.xml')\n" +
                "for $player in $db//players/player \n" +
                "where $player/citizenship = '"+citizenship+"' and $player/foot = '"+foot+"'\n" +
                "return $player";
        ResourceSet resourceSet = existDBOperationsService.executeXQuery(collectionPath, XQuery);
        ResourceIterator i = resourceSet.getIterator();
        Resource res = null;

        XmlMapper xmlMap = new XmlMapper();
        PlayersList players = new PlayersList();

        while(i.hasMoreResources()) {
            try {
                res = i.nextResource();

                StringReader stringReader = new StringReader(res.getContent().toString());
                Player player = xmlMap.readValue(stringReader, Player.class);
                players.getPlayers().add(player);

            } finally {
                //dont forget to cleanup resources
                try { ((EXistResource)res).freeResources(); } catch(XMLDBException xe) {xe.printStackTrace();}
            }
        }
        return players;
    }











    public PlayersList getPlayers(String fullName) throws XMLDBException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException{
        String XQuery = "let $db:= doc('my-data/DB.xml')\n" +
                "for $player in $db//players/player \n" +
                "where $player/fullName = '"+fullName+"' return $player";
        ResourceSet resourceSet = existDBOperationsService.executeXQuery(collectionPath, XQuery);
        ResourceIterator i = resourceSet.getIterator();
        Resource res = null;

        XmlMapper xmlMap = new XmlMapper();
        PlayersList players = new PlayersList();

        while(i.hasMoreResources()) {
            try {
                res = i.nextResource();

                StringReader stringReader = new StringReader(res.getContent().toString());
                Player player = xmlMap.readValue(stringReader, Player.class);
                players.getPlayers().add(player);

            } finally {
                //dont forget to cleanup resources
                try { ((EXistResource)res).freeResources(); } catch(XMLDBException xe) {xe.printStackTrace();}
            }
        }
        return players;
    }

    public ResponseEntity<String> addFootballClub(FootballClub footballClub) throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException, JsonProcessingException {
        String newValue = new XmlMapper().writeValueAsString(footballClub);
        String XQuery = "let $footballClubs:= doc('my-data/DB.xml')/database/footballClubs \n" +
                "return update insert \n" +
                newValue +
                "into $footballClubs";


        existDBOperationsService.executeXQuery(collectionPath, XQuery);

        return ResponseEntity.ok("{\"result\":\"Success!\"}");
    }


    public ResponseEntity<String> addManager(Manager manager) throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException, JsonProcessingException {
        String newValue = new XmlMapper().writeValueAsString(manager);
        String XQuery = "let $managers:= doc('my-data/DB.xml')/database/managers \n" +
                "return update insert \n" +
                newValue +
                "into $managers";


        existDBOperationsService.executeXQuery(collectionPath, XQuery);

        return ResponseEntity.ok("Succes!");
    }


    public ResponseEntity<String> addPlayer(Player player) throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException, JsonProcessingException {
        String newValue = new XmlMapper().writeValueAsString(player);
        String XQuery = "let $players:= doc('my-data/DB.xml')/players \n" +
                "return update insert \n" +
                newValue +
                "into $players";


        existDBOperationsService.executeXQuery(collectionPath, XQuery);

        return ResponseEntity.ok("Succes!");
    }

    public ResponseEntity<String> addContract(Contract contract) throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException, JsonProcessingException {
        String newValue = new XmlMapper().writeValueAsString(contract);
        String XQuery = "let $contracts:= doc('my-data/DB.xml')//contracts" +
                " return update insert " + newValue + " into $contracts";

        existDBOperationsService.executeXQuery(collectionPath, XQuery);

        return ResponseEntity.ok("Succes!");
    }





    public ResponseEntity<String> addPlayer(String player) throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String XQuery = "let $players:= doc('my-data/DB.xml')//players \n" +
                "return update insert \n" +
                player +
                "into $players";


        existDBOperationsService.executeXQuery(collectionPath, XQuery);

        return ResponseEntity.ok("Succes!");
    }

    public ResponseEntity<String> updatePlayer(Player player) throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException, JsonProcessingException {
        String newValue = new XmlMapper().writeValueAsString(player);
        String XQuery = "let $doc := doc('my-data/DB.xml')\n" +
                "let $player := $doc//player[@id="+player.getId()+"]\n" +
                "return update replace $player with" + newValue;




        existDBOperationsService.executeXQuery(collectionPath, XQuery);

        return ResponseEntity.ok("Succes!");
    }


    public ResponseEntity<String> removePlayer(Integer id) throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String XQuery = "for $player in doc('DB.xml')//players/player \n" +
                "where $player/@id = " + id.toString() +
                " return update delete $player";


        existDBOperationsService.executeXQuery(collectionPath, XQuery);

        return ResponseEntity.ok("Succes!");
    }

    public User getUser(int id) throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException, JsonProcessingException {
        String XQuery = "let $user := doc('my-data/DB.xml')/database/users/user[@id =" + id + "]  return $user";

        ResourceSet resourceSet = existDBOperationsService.executeXQuery(collectionPath, XQuery);
        ResourceIterator i = resourceSet.getIterator();
        Resource res = null;

        XmlMapper xmlMap = new XmlMapper();

        User user = new User();
        while (i.hasMoreResources()) {
            try {
                res = i.nextResource();

                user = xmlMap.readValue(res.getContent().toString(), User.class);


            } finally {
                //dont forget to cleanup resources
                try {
                    ((EXistResource) res).freeResources();
                } catch (XMLDBException xe) {
                    xe.printStackTrace();
                }
            }
        }
        return user;
    }

    public ResponseEntity<String> deletePlayer(String id) throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String XQuery = "declare namespace xmldb=\"http://exist-db.org/xquery/xmldb\";\n" +
                "declare namespace util=\"http://exist-db.org/xquery/util\";\n" +
                "let $doc := doc('my-data/DB.xml')\n" +
                "let $player-id := " + id + " \n" +
                "let $player-contractID := $doc//players/player[@id=@player-id]/@contractID\n" +
                "\n" +
                "let $delete-player := update delete $doc//players/player[@id=$player-id]\n" +
                "let $delete-contract := update delete $doc//contracts/contract[@id=$player-contractID]\n" +
                "\n" +
                "return\n" +
                "<result>\n" +
                "    <message>Player and contract deleted successfully</message>\n" +
                "</result>\n";


        existDBOperationsService.executeXQuery(collectionPath, XQuery);

        return ResponseEntity.ok("Succes!");
    }

    public ResponseEntity<String> updateUser(User user) throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException, JsonProcessingException {
        String newValuee = new XmlMapper().writeValueAsString(user);
        String XQuery = "let $user:= doc('my-data/DB.xml')//users/user[@id="+user.getId()+"]\n" +

                " return update replace $user with " + newValuee ;

        existDBOperationsService.executeXQuery(collectionPath, XQuery);


        return ResponseEntity.ok("Succes!");
    }

    public ResponseEntity<String> deleteManager(String id) throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String XQuery = "declare namespace xmldb=\"http://exist-db.org/xquery/xmldb\";\n" +
                "declare namespace util=\"http://exist-db.org/xquery/util\";\n" +
                "\n" +
                "let $doc := doc('my-data/DB.xml')\n" +
                "let $manager-id :=" + id + " \n" +
                "let $manager-contractID := $doc//managers/manager[@id=$manager-id]/@contractID\n" +
                "\n" +
                "let $delete-manager := update delete $doc//managers/manager[@id=$manager-id]\n" +
                "let $delete-contract := update delete $doc//contracts/contract[@id=$manager-contractID]\n" +
                "\n" +
                "return\n" +
                "<result>\n" +
                "    <message>Manager and contract deleted successfully</message>\n" +
                "</result>\n";

        existDBOperationsService.executeXQuery(collectionPath, XQuery);

        return ResponseEntity.ok("Succes!");
    }
}
