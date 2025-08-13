package com.example.secondapi.service;


import com.example.api.second_api.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.w3c.dom.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;
import java.io.File;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

@Service
public class XMLService {
    Document doc;
    File file;

    //Creating XPath
    XPathFactory xpathfactory = XPathFactory.newInstance();
    XPath xpath = xpathfactory.newXPath();




    XMLService() throws XPathExpressionException {

        try {
            // creating a constructor of file class and
            // parsing an XML file
            file = new File(
                    "D:\\IACD\\IACD - UVT\\AN 2\\XML\\FINAL-PROJECT\\SecondAPI\\DB.xml");

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





    private static GregorianCalendar toGregorianCalendar(Date date) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(date);
        return gregorianCalendar;
    }

    public ContractOptionResponse getPlayerContractOption(int playerID){
        Object nodes;

        try {
            XPathExpression expr = xpath.compile("//contract[@id=//players/player[@id='"+playerID+"']/@contractID]/contractOption");
            nodes = expr.evaluate(doc, XPathConstants.NODESET);
            nodes = (NodeList) nodes;
            for (int i = 0; i < ((NodeList) nodes).getLength(); i++) {
                Node node = ((NodeList) nodes).item(i);
                if (node.getNodeType()
                        == Node.ELEMENT_NODE) {
                    Element tElement = (Element) node;
                    ContractOptionResponse contractOptionResponse = new ContractOptionResponse();
                    contractOptionResponse.setContractOption(tElement.getFirstChild().getNodeValue());
                    return contractOptionResponse;
                }

            }
        }catch (XPathExpressionException  e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public Players getPlayers() throws DatatypeConfigurationException {


        String dateOfBirth;
//        List <Player> players = new ArrayList<>();

        // Create a DatatypeFactory object
        DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
        Players players = new Players();

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

                    PlayerType player = new PlayerType();
                    player.setContractID(Integer.parseInt(tElement.getAttributeNode("contractID").getNodeValue()));
                    player.setId(Integer.parseInt(tElement.getAttributeNode("id").getNodeValue()));
                    player.setFullName(tElement.getFirstChild().getNextSibling().getFirstChild().getNodeValue());
                    dateOfBirth = tElement.getFirstChild().getNextSibling().getNextSibling().getNextSibling().getFirstChild().getNodeValue();

                    XMLGregorianCalendar xmlGregorianCalendarDate =
                            datatypeFactory.newXMLGregorianCalendar(toGregorianCalendar(new SimpleDateFormat("dd/MM/yyyy").parse(dateOfBirth)));

                    player.setDateOfBirth(xmlGregorianCalendarDate);
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

    public Players getPlayers(String fullName) throws DatatypeConfigurationException {
        String dateOfBirth;
        Players players = new Players();
        Object nodes;
        DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
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
                    PlayerType player = new PlayerType();
                    player.setContractID(Integer.parseInt(tElement.getAttributeNode("contractID").getNodeValue()));
                    player.setId(Integer.parseInt(tElement.getAttributeNode("id").getNodeValue()));
                    player.setFullName(tElement.getFirstChild().getNextSibling().getFirstChild().getNodeValue());
                    dateOfBirth = tElement.getFirstChild().getNextSibling().getNextSibling().getNextSibling().getFirstChild().getNodeValue();
                    XMLGregorianCalendar xmlGregorianCalendarDate =
                            datatypeFactory.newXMLGregorianCalendar(toGregorianCalendar(new SimpleDateFormat("dd/MM/yyyy").parse(dateOfBirth)));
                    player.setDateOfBirth(xmlGregorianCalendarDate);
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

    public Players getPlayers(String citizenship, String foot) throws DatatypeConfigurationException {
        String dateOfBirth;
        Object nodes;
        DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
        Players players = new Players();
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
                    PlayerType player = new PlayerType();
                    player.setContractID(Integer.parseInt(tElement.getAttributeNode("contractID").getNodeValue()) );
                    player.setId( Integer.parseInt(tElement.getAttributeNode("id").getNodeValue()));
                    player.setFullName(tElement.getFirstChild().getNextSibling().getFirstChild().getNodeValue());
                    dateOfBirth = tElement.getFirstChild().getNextSibling().getNextSibling().getNextSibling().getFirstChild().getNodeValue();
                    XMLGregorianCalendar xmlGregorianCalendarDate =
                            datatypeFactory.newXMLGregorianCalendar(toGregorianCalendar(new SimpleDateFormat("dd/MM/yyyy").parse(dateOfBirth)));
                    player.setDateOfBirth(xmlGregorianCalendarDate);
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

    public ServerResponse addPlayer(PlayerType player) throws TransformerException {
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
        playersElement.appendChild(playerElement);
        playersElement.setAttributeNode(contractIDAttr);
        playersElement.setAttributeNode(idAttr);

        // Write to disk
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(file);
        transformer.transform(source, result);

        ServerResponse sr = new ServerResponse();
        sr.setStatus("200");
        return sr;
    }


    public ServerResponse removePlayer(Integer id) throws TransformerException {
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

        ServerResponse sr = new ServerResponse();
        sr.setStatus("200");
        return sr;
    }

    public ServerResponse updateContract(UpdateContract contract) throws TransformerException {
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
        ServerResponse sr = new ServerResponse();
        sr.setStatus("200");
        return sr;
    }



    public Managers getManagers() throws DatatypeConfigurationException {
        String dateOfBirth;
        Managers managers = new Managers();
        Object nodes;


        try {
            XPathExpression expr = xpath.compile("//managers/manager");
            nodes = expr.evaluate(doc, XPathConstants.NODESET);
            for (int i = 0; i < ((NodeList) nodes).getLength(); i++) {
                Node node = ((NodeList) nodes).item(i);
                if (node.getNodeType()
                        == Node.ELEMENT_NODE) {
                    Element tElement = (Element) node;
                    ManagerType manager = new ManagerType();
                    manager.setId(Integer.valueOf(tElement.getAttributeNode("id").getNodeValue()));
                    manager.setManagerName(tElement.getFirstChild().getFirstChild().getNodeValue());
                    managers.getManagers().add(manager);
                }

            }
        }catch (XPathExpressionException  e) {
            throw new RuntimeException(e);
        }

        return managers;
    }

    public ServerResponse updateManager(UpdateManager manager) throws XPathExpressionException, TransformerException {

        XPathExpression expr = xpath.compile("//manager[@id='" + manager.getId() + "']");
        Object nodes = expr.evaluate(doc, XPathConstants.NODESET);
        Element managerToBeUpdated = (Element) ((NodeList) nodes).item(0);
        managerToBeUpdated.setAttribute("id", String.valueOf(manager.getId()));
        managerToBeUpdated.getFirstChild().getFirstChild().setTextContent(manager.getManagerName());

        // Write the modified content back to the file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(file.toURI()));
        transformer.transform(source, result);

        ServerResponse sr = new ServerResponse();
        sr.setStatus("200");
        return sr;
    }
}