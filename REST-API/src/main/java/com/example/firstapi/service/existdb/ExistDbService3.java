//package com.example.firstapi.service.existdb;
//
//import org.xmldb.api.base.*;
//import org.xmldb.api.modules.*;
//
//import javax.xml.transform.OutputKeys;
//import org.exist.xmldb.EXistResource;
//import org.xmldb.api.DatabaseManager;
//
//import org.xmldb.api.modules.XPathQueryService;
//
//import org.springframework.stereotype.Service;
//
//@Service
//public class ExistDbService3 {
//
//    String collection = "db/first-collection";
//    String resource = "DB.xml";
//    private static String URI = "xmldb:exist://localhost:8080/exist/xmlrpc";
//
//
//    public void getByXPath() throws ClassNotFoundException, XMLDBException, InstantiationException, IllegalAccessException {
//        final String driver = "org.exist.xmldb.DatabaseImpl";
//        // initialize database driver
//        Class cl = Class.forName(driver);
//        Database database = (Database) cl.newInstance();
//        database.setProperty("create-database", "true");
//        DatabaseManager.registerDatabase(database);
//
//        Collection col = null;
//        try {
//            col = DatabaseManager.getCollection(URI + collection);
//            XPathQueryService xpqs = (XPathQueryService)col.getService("XPathQueryService", "1.0");
//            xpqs.setProperty("indent", "yes");
//
//            ResourceSet result = xpqs.query("//exide");
//            ResourceIterator i = result.getIterator();
//            Resource res = null;
//            while(i.hasMoreResources()) {
//                try {
//                    res = i.nextResource();
//                    System.out.println(res.getContent());
//                } finally {
//                    //dont forget to cleanup resources
//                    try { ((EXistResource)res).freeResources(); } catch(XMLDBException xe) {xe.printStackTrace();}
//                }
//            }
//        } finally {
//            //dont forget to cleanup
//            if(col != null) {
//                try { col.close(); } catch(XMLDBException xe) {xe.printStackTrace();}
//            }
//        }
//    }
//
//
//    public void retrieveExample() throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
//        final String driver = "org.exist.xmldb.DatabaseImpl";
//
//        // initialize database driver
//        Class cl = Class.forName(driver);
//        Database database = (Database) cl.newInstance();
//        database.setProperty("create-database", "true");
//        DatabaseManager.registerDatabase(database);
//
//        Collection col = null;
//        XMLResource res = null;
//        try {
//            // get the collection
//            col = DatabaseManager.getCollection(URI + collection);
//            col.setProperty(OutputKeys.INDENT, "no");
//            res = (XMLResource)col.getResource(resource);
//
//            if(res == null) {
//                System.out.println("document not found!");
//            } else {
//                System.out.println(res.getContent());
//            }
//        } finally {
//            //dont forget to clean up!
//
//            if(res != null) {
//                try { ((EXistResource)res).freeResources(); } catch(XMLDBException xe) {xe.printStackTrace();}
//            }
//
//            if(col != null) {
//                try { col.close(); } catch(XMLDBException xe) {xe.printStackTrace();}
//            }
//        }
//    }
//
//
//
//}
