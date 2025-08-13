package com.example.firstapi.ApacheCamel.routes.active;

import com.example.firstapi.ApacheCamel.processors.XML_JSON_Processor;
import com.example.firstapi.ApacheCamel.processors.XML_JSON_Processor_old;
import com.example.firstapi.service.ExistService;
import com.example.firstapi.service.XSLTService;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class XML_JSON_Route extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("timer:trigger?period=10000")
                .process(exchange -> {
                    int randomController = (int) (Math.random() * 2) + 1;

                    exchange.getIn().setHeader("controller", "controller" + randomController);
                })
                .to("direct:chooseController");

        from("direct:chooseController")
                .choice()
                .when(simple("${header.controller} == 'controller1'"))
                .to("direct:xmlInput1")
                .when(simple("${header.controller} == 'controller2'"))
                .to("direct:xmlInput2")
                .otherwise()
                .log("Invalid controller specified: ${header.controller}")
                .end();

        // Route for XML input 1
        from("direct:xmlInput1")
                .setBody().method(new XSLTService(), "retrievePlayers")
                .process(new XML_JSON_Processor())
                .to("log:output");

        // Route for XML input 2
        from("direct:xmlInput2")
                .setBody().method(new ExistService(), "getFootballClubs")
                .process(new XML_JSON_Processor())
                .to("log:output");
    }
}

