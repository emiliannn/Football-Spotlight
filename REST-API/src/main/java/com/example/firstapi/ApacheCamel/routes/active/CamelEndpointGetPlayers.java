package com.example.firstapi.ApacheCamel.routes.active;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import com.example.firstapi.ApacheCamel.processors.XML_JSON_Processor;
import com.example.firstapi.service.ExistService;

import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Component
public class CamelEndpointGetPlayers extends  RouteBuilder{


        @Override
        public void configure() throws Exception {

            from("servlet:///json/players?httpMethodRestrict=GET")
                    .to("direct:xmlToJSON");

            from("seda:xmlToJSON")
                    .setBody().method(new ExistService(), "getPlayers")
                    .process(new XML_JSON_Processor());

        }
















}
















//      @Override
//      public void configure() throws Exception {
//          // REST endpoint for XML to JSON conversion
//          rest("/convert")
//                  .get()
//                  .produces("application/json")
//                  .to("direct:xmlToJSON");
//
//          // Route for XML to JSON conversion
//          from("direct:xmlToJSON")
//                  .setBody().method(new XSLTService(), "retrievePlayers")
//                  .process(new XML_JSON_Processor());
//      }