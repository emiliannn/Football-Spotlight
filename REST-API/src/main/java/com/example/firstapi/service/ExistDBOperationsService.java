package com.example.firstapi.service;

import org.exist.xmldb.EXistResource;
import org.springframework.stereotype.Service;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.*;
import org.xmldb.api.modules.CollectionManagementService;
import org.xmldb.api.modules.XMLResource;
import org.xmldb.api.modules.XPathQueryService;
import org.xmldb.api.modules.XQueryService;

import javax.xml.transform.OutputKeys;
import java.io.File;

@Service
public class ExistDBOperationsService {
//    String collection = "my-db";
//    String resource = "D:\\IACD\\IACD - UVT\\AN 2\\XML\\FINAL-PROJECT\\FirstAPI\\DB.xml";

    final String driver = "org.exist.xmldb.DatabaseImpl";
    private static String URI = "xmldb:exist://localhost:8080/exist/xmlrpc";

    private static void registerDB() throws ClassNotFoundException, XMLDBException, InstantiationException, IllegalAccessException {
        // initialize database driver
        Class cl = Class.forName("org.exist.xmldb.DatabaseImpl");
        Database database = (Database) cl.newInstance();
        database.setProperty("create-database", "true");
        DatabaseManager.registerDatabase(database);
    }

    private static Collection getOrCreateCollection(String collectionUri) throws XMLDBException {
        return getOrCreateCollection(collectionUri, 0);
    }

    private static Collection getOrCreateCollection(String collectionUri, int pathSegmentOffset) throws XMLDBException {

        Collection col = DatabaseManager.getCollection(URI  , "admin", "admin");
        if(col == null) {
            if(collectionUri.startsWith("/")) {
                collectionUri = collectionUri.substring(1);
            }

            String pathSegments[] = collectionUri.split("/");
            if(pathSegments.length > 0) {

                StringBuilder path = new StringBuilder();
                for(int i = 0; i <= pathSegmentOffset; i++) {
                    path.append("/" + pathSegments[i]);
                }

                Collection start = DatabaseManager.getCollection(URI + path);
                if(start == null) {
                    //collection does not exist, so create
                    String parentPath = path.substring(0, path.lastIndexOf("/"));
                    Collection parent = DatabaseManager.getCollection(URI + parentPath);
                    CollectionManagementService mgt = (CollectionManagementService) parent.getService("CollectionManagementService", "1.0");
                    col = mgt.createCollection(pathSegments[pathSegmentOffset]);
                    col.close();
                    parent.close();
                } else {
                    start.close();
                }
            }
            return getOrCreateCollection(collectionUri, ++pathSegmentOffset);
        } else {
            return col;
        }
    }





    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void storeResource(String collectionPath, String resourcePath) throws ClassNotFoundException, XMLDBException, InstantiationException, IllegalAccessException {

        registerDB();


        Collection col = null;
        XMLResource res = null;
        try {
            col = getOrCreateCollection(collectionPath);

            // create new XMLResource; an id will be assigned to the new resource
            res = (XMLResource)col.createResource(null, "XMLResource");
            File f = new File(resourcePath);
            if(!f.canRead()) {
                System.out.println("cannot read file " + resourcePath);
                return;
            }

            res.setContent(f);
            System.out.print("storing document " + res.getId() + "...");
            col.storeResource(res);
            System.out.println("ok.");
        } finally {
            //dont forget to cleanup
            if(res != null) {
                try { ((EXistResource)res).freeResources(); } catch(XMLDBException xe) {xe.printStackTrace();}
            }

            if(col != null) {
                try { col.close(); } catch(XMLDBException xe) {xe.printStackTrace();}
            }
        }

    }

    public void executeXPath(String collectionPath, String XPathQuery) throws ClassNotFoundException, XMLDBException, InstantiationException, IllegalAccessException {

        registerDB();

        Collection col = null;

        try {
            col = DatabaseManager.getCollection(URI + collectionPath);
            XPathQueryService xpqs = (XPathQueryService)col.getService("XPathQueryService", "1.0");
            xpqs.setProperty("indent", "yes");

            ResourceSet result = xpqs.query(XPathQuery);
            ResourceIterator i = result.getIterator();
            Resource res = null;
            while(i.hasMoreResources()) {
                try {
                    res = i.nextResource();

                    ///////////////////////////////////////////////////////////////!!!!!!!!!!RESULT!!!!!!!!!!!!!!
                    System.out.println(res.getContent());
                    ///////////////////////////////////////////////////////////////!!!!!!!!!!RESULT!!!!!!!!!!!!!!
                } finally {
                    //dont forget to cleanup resources
                    try { ((EXistResource)res).freeResources(); } catch(XMLDBException xe) {xe.printStackTrace();}
                }
            }
        } finally {
            //dont forget to cleanup
            if(col != null) {
                try { col.close(); } catch(XMLDBException xe) {xe.printStackTrace();}
            }
        }
    }

    public void retrieveResource(String collectionPath, String resourceName) throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        registerDB();

        Collection col = null;
        XMLResource res = null;
        try {
            // get the collection
            col = DatabaseManager.getCollection(URI + collectionPath);
            col.setProperty(OutputKeys.INDENT, "no");
            res = (XMLResource)col.getResource(resourceName);

            if(res == null) {
                System.out.println("document not found!");
            } else {

                ////////////////////////////////Result!!
                System.out.println(res.getContent());
                ////////////////////////////////Result!!

            }
        } finally {
            //dont forget to clean up!

            if(res != null) {
                try { ((EXistResource)res).freeResources(); } catch(XMLDBException xe) {xe.printStackTrace();}
            }

            if(col != null) {
                try { col.close(); } catch(XMLDBException xe) {xe.printStackTrace();}
            }
        }
    }

    public ResourceSet executeXQuery(String collectionPath, String XQuery) throws ClassNotFoundException, XMLDBException, InstantiationException, IllegalAccessException {

        registerDB();

        Collection col = null;

        ResourceSet result = null;
        try {
            col = DatabaseManager.getCollection(URI + collectionPath);
            XQueryService xqs = (XQueryService) col.getService("XQueryService",
                    "1.0");
            xqs.setProperty("indent", "yes");

            CompiledExpression compiled = xqs.compile(XQuery);
            result = xqs.execute(compiled);
            return result;
//            ResourceIterator i = result.getIterator();
//            Resource res = null;
//            while(i.hasMoreResources()) {
//                try {
//                    res = i.nextResource();
//
//                    ////////////////////////////////////RESULT!!!!!
////                    System.out.println(res.getContent());
////                    return res.getContent();
//
//                    ////////////////////////////////////RESULT!!!!!
//
//                } finally {
//                    //dont forget to cleanup resources
//                    try { ((EXistResource)res).freeResources(); } catch(XMLDBException xe) {xe.printStackTrace();}
//                }
//            }
        } finally {

//            //dont forget to cleanup
//            if (col != null) {
//                try {
//                    col.close();
//                } catch (XMLDBException xe) {
//                    xe.printStackTrace();
//                }
//            }

        }
    }

}
