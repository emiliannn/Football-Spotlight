package com.example.firstapi.service;

import com.example.firstapi.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class XMLService {
    Document doc;
    File file;

    //Creating XPath
    XPathFactory xpathfactory = XPathFactory.newInstance();
    XPath xpath = xpathfactory.newXPath();




    public XMLService() throws XPathExpressionException {

        try {
            // creating a constructor of file class and
            // parsing an XML file
            file = new File(
                    "D:\\IACD\\IACD - UVT\\AN 2\\XML\\FINAL-PROJECT\\FirstAPI\\DB.xml");

            // Defines a factory API that enables
            // applications to obtain a parser that produces
            // DOM object trees from XML documents.
            DocumentBuilderFactory dbf
                    = DocumentBuilderFactory.newInstance();

            // we are creating an object of builder to parse
            // the xml file.
            DocumentBuilder db = dbf.newDocumentBuilder();
            doc = db.parse(file);

			/*here normalize method Puts all Text nodes in
			the full depth of the sub-tree underneath this
			Node, including attribute nodes, into a "normal"
			form where only structure separates
			Text nodes, i.e., there are neither adjacent
			Text nodes nor empty Text nodes. */
            doc.getDocumentElement().normalize();


//            System.out.println(
//                    "Root element: "
//                            + doc.getDocumentElement().getNodeName());
        }
        // This exception block catches all the exception
        // raised.
        // For example if we try to access a element by a
        // TagName that is not there in the XML etc.
        catch (Exception e) {
            System.out.println(e);
        }

    }


    public ContractOption getPlayerContractOption(int playerID){
        Object nodes;
        ContractOption contractOption = new ContractOption();

        try {
            XPathExpression expr = xpath.compile("//contract[@id=//players/player[@id='"+playerID+"']/@contractID]/contractOption");
            nodes = expr.evaluate(doc, XPathConstants.NODESET);
            nodes = (NodeList) nodes;
            for (int i = 0; i < ((NodeList) nodes).getLength(); i++) {
                Node node = ((NodeList) nodes).item(i);
                if (node.getNodeType()
                        == Node.ELEMENT_NODE) {
                    Element tElement = (Element) node;
                    contractOption.setContractOption(tElement.getFirstChild().getNodeValue());
                    return contractOption;
                }

            }
        }catch (XPathExpressionException  e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public PlayersList getPlayers() {
        String dateOfBirth;
        PlayersList players = new PlayersList();

        try {
            NodeList nodes = doc.getElementsByTagName("player");
            // Iterate through all the nodes in NodeList
            // using for loop.
            for (int i = 0; i < nodes.getLength(); ++i) {
                Node node = nodes.item(i);
//                System.out.println("\nNode Name :"
//                        + node.getNodeName());
                if (node.getNodeType()
                        == Node.ELEMENT_NODE) {
                    Element tElement = (Element) node;

                    Player player = new Player();
                    player.setContractID(tElement.getAttributeNode("contractID").getNodeValue());
                    player.setId(Integer.parseInt(tElement.getAttributeNode("id").getNodeValue()));
                    player.setFullName(tElement.getFirstChild().getNextSibling().getFirstChild().getNodeValue());
                    dateOfBirth = tElement.getFirstChild().getNextSibling().getNextSibling().getNextSibling().getFirstChild().getNodeValue();
                    player.setDateOfBirth(new SimpleDateFormat("dd/MM/yyyy").parse(dateOfBirth));
                    player.setAge(Integer.parseInt(tElement.getFirstChild().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getFirstChild().getNodeValue()));
                    player.setHeight(Float.parseFloat(tElement.getFirstChild().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getFirstChild().getNodeValue()));
                    player.setCitizenship(tElement.getFirstChild().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getFirstChild().getNodeValue());
                    player.setPosition(tElement.getFirstChild().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getFirstChild().getNodeValue());
                    player.setFoot(tElement.getFirstChild().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getFirstChild().getNodeValue());
                    player.setOutfitter(tElement.getFirstChild().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getFirstChild().getNodeValue());
                    players.getPlayers().add(player);
                }
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
        return players;
    }

    public PlayersList getPlayers(String fullName)  {
        String dateOfBirth;
        PlayersList players = new PlayersList();
        Object nodes;

        try {
            XPathExpression expr = xpath.compile("//players/player[fullName='" + fullName + "']");
            nodes = expr.evaluate(doc, XPathConstants.NODESET);
            nodes = (NodeList) nodes;
            for (int i = 0; i < ((NodeList) nodes).getLength(); i++) {
                Node node = ((NodeList) nodes).item(i);
//                System.out.println("\nNode Name :"
//                        + node.getNodeName());
                if (node.getNodeType()
                        == Node.ELEMENT_NODE) {
                    Element tElement = (Element) node;
                    Player player = new Player();
                    player.setContractID(tElement.getAttributeNode("contractID").getNodeValue());
                    player.setId(Integer.parseInt(tElement.getAttributeNode("id").getNodeValue()));
                    player.setFullName(tElement.getFirstChild().getNextSibling().getFirstChild().getNodeValue());
                    dateOfBirth = tElement.getFirstChild().getNextSibling().getNextSibling().getNextSibling().getFirstChild().getNodeValue();
                    player.setDateOfBirth(new SimpleDateFormat("dd/MM/yyyy").parse(dateOfBirth));
                    player.setAge(Integer.parseInt(tElement.getFirstChild().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getFirstChild().getNodeValue()));
                    player.setHeight(Float.parseFloat(tElement.getFirstChild().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getFirstChild().getNodeValue()));
                    player.setCitizenship(tElement.getFirstChild().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getFirstChild().getNodeValue());
                    player.setPosition(tElement.getFirstChild().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getFirstChild().getNodeValue());
                    player.setFoot(tElement.getFirstChild().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getFirstChild().getNodeValue());
                    player.setOutfitter(tElement.getFirstChild().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getFirstChild().getNodeValue());
                    players.getPlayers().add(player);
                }

            }
        }catch (XPathExpressionException | ParseException e) {
            throw new RuntimeException(e);
        }

        return players;
    }

    public PlayersList getPlayers(String citizenship, String foot)  {
        String dateOfBirth;
        PlayersList players = new PlayersList();
        Object nodes;

        try {
            XPathExpression expr = xpath.compile("//players/player[citizenship='" + citizenship + "' and  foot = '" + foot + "' ]");
            nodes = expr.evaluate(doc, XPathConstants.NODESET);
            nodes = (NodeList) nodes;
            for (int i = 0; i < ((NodeList) nodes).getLength(); i++) {

                Node node = ((NodeList) nodes).item(i);
//                System.out.println("\nNode Name :"
//                        + node.getNodeName());
                if (node.getNodeType()
                        == Node.ELEMENT_NODE) {
                    Element tElement = (Element) node;
                    Player player = new Player();
                    player.setContractID(tElement.getAttributeNode("contractID").getNodeValue());
                    player.setId(Integer.parseInt(tElement.getAttributeNode("id").getNodeValue()));
                    player.setFullName(tElement.getFirstChild().getNextSibling().getFirstChild().getNodeValue());
                    dateOfBirth = tElement.getFirstChild().getNextSibling().getNextSibling().getNextSibling().getFirstChild().getNodeValue();
                    player.setDateOfBirth(new SimpleDateFormat("dd/MM/yyyy").parse(dateOfBirth));
                    player.setAge(Integer.parseInt(tElement.getFirstChild().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getFirstChild().getNodeValue()));
                    player.setHeight(Float.parseFloat(tElement.getFirstChild().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getFirstChild().getNodeValue()));
                    player.setCitizenship(tElement.getFirstChild().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getFirstChild().getNodeValue());
                    player.setPosition(tElement.getFirstChild().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getFirstChild().getNodeValue());
                    player.setFoot(tElement.getFirstChild().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getFirstChild().getNodeValue());
                    player.setOutfitter(tElement.getFirstChild().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getFirstChild().getNodeValue());
                    players.getPlayers().add(player);
                }

            }
        }catch (XPathExpressionException | ParseException e) {
            throw new RuntimeException(e);
        }

        return players;
    }

    public ResponseEntity<String> addPlayer(Player player) throws TransformerException {
        Element playersElement = (Element) doc.getElementsByTagName("players").item(0);
        Attr contractIDAttr = doc.createAttribute("contractID");
        contractIDAttr.setValue(String.valueOf(player.getContractID()));
        Attr idAttr = doc.createAttribute("id");
        idAttr.setValue(String.valueOf(player.getId()));
        Element playerElement = doc.createElement( "player");
        Element fullNameElement = doc.createElement( "fullName");
        fullNameElement.appendChild(doc.createTextNode(player.getFullName()));
        Element dateOfBirthElement = doc.createElement( "dateOfBirth");
        dateOfBirthElement.appendChild(doc.createTextNode(String.valueOf(player.getDateOfBirth())));
        Element ageElement = doc.createElement( "age");
        ageElement.appendChild(doc.createTextNode(String.valueOf(player.getAge())));
        Element heightElement = doc.createElement( "height");
        heightElement.appendChild(doc.createTextNode(String.valueOf(player.getHeight())));
        Element citizenshipElement = doc.createElement( "citizenship");
        citizenshipElement.appendChild(doc.createTextNode(String.valueOf(player.getCitizenship())));
        Element positionElement = doc.createElement( "position");
        positionElement.appendChild(doc.createTextNode(String.valueOf(player.getPosition())));
        Element footElement = doc.createElement( "foot");
        footElement.appendChild(doc.createTextNode(String.valueOf(player.getFoot())));
        Element outfitterElement = doc.createElement( "outfitter");
        outfitterElement.appendChild(doc.createTextNode(String.valueOf(player.getOutfitter())));


        playerElement.appendChild(fullNameElement);
        playerElement.appendChild(ageElement);
        playerElement.appendChild(heightElement);
        playerElement.appendChild(citizenshipElement);
        playerElement.appendChild(positionElement);
        playerElement.appendChild(footElement);
        playerElement.appendChild(outfitterElement);
        playerElement.setAttributeNode(idAttr);
        playerElement.setAttributeNode(contractIDAttr);
        playersElement.appendChild(playerElement);



        // Write to disk
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(file);
        transformer.transform(source, result);


        return  ResponseEntity.ok("Success!");
    }


    public ResponseEntity<String> removePlayer(Integer id) throws TransformerException {
        Object nodes;
        try {
            XPathExpression expr = xpath.compile("//players/player[@id='" + id + "']");
            nodes = expr.evaluate(doc, XPathConstants.NODESET);
            nodes = (NodeList) nodes;
            for (int i = 0; i < ((NodeList) nodes).getLength(); i++) {
                Node node = ((NodeList) nodes).item(i);
//                System.out.println("\nNode Name :"
//                        + node.getNodeName());
                if (node.getNodeType()
                        == Node.ELEMENT_NODE) {
                    Element tElement = (Element) node;
                    Element playersElement = (Element) doc.getElementsByTagName("players").item(0);
                    playersElement.removeChild(tElement);

                    // Write to disk
                    TransformerFactory transformerFactory = TransformerFactory.newInstance();
                    Transformer transformer = transformerFactory.newTransformer();

                    DOMSource source = new DOMSource(doc);
                    StreamResult result = new StreamResult(file);
                    transformer.transform(source, result);
                }

            }
        }catch (XPathExpressionException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok("Success!");
    }

    public ResponseEntity<String> updateContract(Contract contract) throws TransformerException {
        Object nodes;
        try {
            XPathExpression expr = xpath.compile("//contract[@id='" + contract.getId() + "']");
            nodes = expr.evaluate(doc, XPathConstants.NODESET);
            for (int i = 0; i < ((NodeList) nodes).getLength(); i++) {
                Node node = ((NodeList) nodes).item(i);
//                System.out.println("\nNode Name :"
//                        + node.getNodeName());
                if (node.getNodeType()
                        == Node.ELEMENT_NODE) {
                    Element c = (Element) node;
                    Element contractsElement = (Element) doc.getElementsByTagName("contracts").item(0);
                    contractsElement.removeChild(c);

                    Attr currentClubID = doc.createAttribute("currentClubID");
                    currentClubID.setValue(String.valueOf(contract.getCurrentClubID()));
                    Attr idAttr = doc.createAttribute("id");
                    idAttr.setValue(String.valueOf(contract.getId()));
                    Attr previousClubID = doc.createAttribute("previousClubID");
                    previousClubID.setValue(String.valueOf(contract.getPreviousClubID()));

                    Element joinedElement = doc.createElement( "joined");
                    joinedElement.appendChild(doc.createTextNode(String.valueOf(contract.getJoined())));
                    Element contractExpiresElement = doc.createElement( "contractExpires");
                    contractExpiresElement.appendChild(doc.createTextNode(String.valueOf(contract.getContractExpires())));
                    Element contractOptionElement = doc.createElement( "contractOption");
                    contractOptionElement.appendChild(doc.createTextNode(contract.getContractOption()));
                    Element contractElement = doc.createElement( "contract");

                    contractElement.setAttributeNode(currentClubID);
                    contractElement.setAttributeNode(idAttr);
                    contractElement.setAttributeNode(previousClubID);
                    contractElement.appendChild(joinedElement);
                    contractElement.appendChild(contractExpiresElement);
                    contractElement.appendChild(contractOptionElement);

                    contractsElement.appendChild(contractElement);

                    // Write to disk
                    TransformerFactory transformerFactory = TransformerFactory.newInstance();
                    Transformer transformer = transformerFactory.newTransformer();

                    DOMSource source = new DOMSource(doc);
                    StreamResult result = new StreamResult(file);
                    transformer.transform(source, result);
                }

            }
        }catch (XPathExpressionException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok("Success!");
    }

    public ManagersList getManagers() {
        String dateOfBirth;
        ManagersList managers = new ManagersList();
        Object nodes;

        try {
            XPathExpression expr = xpath.compile("//managers/manager");
            nodes = expr.evaluate(doc, XPathConstants.NODESET);
            nodes = (NodeList) nodes;
            for (int i = 0; i < ((NodeList) nodes).getLength(); i++) {

                Node node = ((NodeList) nodes).item(i);
//                System.out.println("\nNode Name :"
//                        + node.getNodeName());
                if (node.getNodeType()
                        == Node.ELEMENT_NODE) {
                    Element tElement = (Element) node;
                    Manager manager = new Manager();
                    manager.setId(Integer.valueOf(tElement.getAttributeNode("id").getNodeValue()));
                    manager.setFullName(tElement.getFirstChild().getNextSibling().getFirstChild().getNodeValue());
                    managers.getManagers().add(manager);
                }

            }
        }catch (XPathExpressionException  e) {
            throw new RuntimeException(e);
        }

        return managers;

    }

    public ResponseEntity<String> updateManager(Integer id, Manager manager) throws XPathExpressionException, TransformerException {

        XPathExpression expr = xpath.compile("//manager[@id='" + id + "']");
        Object nodes = expr.evaluate(doc, XPathConstants.NODESET);
        Element managerToBeUpdated = (Element) ((NodeList) nodes).item(0);
        managerToBeUpdated.setAttribute("id", String.valueOf(manager.getId()));
        managerToBeUpdated.getFirstChild().getNextSibling().getFirstChild().setTextContent(manager.getFullName());

        // Write the modified content back to the file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(file.toURI()));
        transformer.transform(source, result);

        return ResponseEntity.ok("Success!");

    }
}