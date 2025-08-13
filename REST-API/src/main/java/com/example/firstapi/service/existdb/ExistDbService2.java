//package com.example.firstapi.service.existdb;
//
//import org.xmldb.api.DatabaseManager;
//import org.xmldb.api.base.*;
//import org.xmldb.api.modules.CollectionManagementService;
//import org.springframework.stereotype.Service;
//
//@Service
//public class ExistDbService2 {
//
//    String collection = "my-db";
//    String resource = "D:\\IACD\\IACD - UVT\\AN 2\\XML\\FINAL-PROJECT\\FirstAPI\\DB.xml";
//
//
//
//    public void createCol() throws ClassNotFoundException, XMLDBException, InstantiationException, IllegalAccessException {
//        String col = "my-db";
//        String res = "D:\\IACD\\IACD - UVT\\AN 2\\XML\\FINAL-PROJECT\\FirstAPI\\DB.xml";
//
//        final String driver = "org.exist.xmldb.DatabaseImpl";
//
//        // initialize database driver
//        Class cl = Class.forName("org.exist.xmldb.DatabaseImpl");
//        Database database = (Database) cl.newInstance();
//        database.setProperty("create-database", "true");
//        DatabaseManager.registerDatabase(database);
//
//
//
//        Collection collection =
//                DatabaseManager.getCollection("xmldb:exist://localhost:8080/exist/xmlrpc/db/first-collection", "admin", "admin");
//
//        // Create the Collection
//        if (collection == null) {
//            Collection parent =
//                    DatabaseManager.getCollection("xmldb:exist://localhost:8080/exist/xmlrpc/db", "admin", "admin");
//            CollectionManagementService cms = (CollectionManagementService) parent.getService("CollectionManagementService", "1.0");
//            collection = cms.createCollection("my-collection");
//        }
//
//
//        // Store the XML file
//        String xml = "<my-xml-document><first>1</first></my-xml-document>";
//        Resource resource = collection.createResource("my-xml-file.xml", "XMLResource");
//        resource.setContent(xml);
//        collection.storeResource(resource);
//    }
//
//
//
//}
