package com.example.firstapi.ApacheCamel.routes.active;

import com.example.firstapi.model.Player;
import com.example.firstapi.service.ExistService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.xmldb.api.base.XMLDBException;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Component
public class CamelEndpointUpdatePlayer extends  RouteBuilder{

//working below
@Autowired
private ObjectMapper objectMapper;
private final ExistService existService;
    public CamelEndpointUpdatePlayer() {
        this.existService = new ExistService();
    }
    @Override
    public void configure() throws Exception {
        from("servlet:///json/players/update?httpMethodRestrict=POST")
                .unmarshal().json(JsonLibrary.Jackson, Player.class)
                .to("direct:updatePlayer");
        from("direct:updatePlayer")
                .process(this::processUpdatePlayer);
    }
    private void processUpdatePlayer(Exchange exchange) throws JsonProcessingException, XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Player player = exchange.getIn().getBody(Player.class);
        existService.updatePlayer(player);
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