package com.example.firstapi;

import com.example.firstapi.ApacheCamel.routes.active.*;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FirstApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FirstApiApplication.class, args);
		// create a CamelContext
		try (CamelContext camel = new DefaultCamelContext()) {

			// add routes which can be inlined as anonymous inner class
			// (to keep all code in a single java file for this basic example)
//			camel.addRoutes(new WriteEveryNSecondsRoute());
			camel.addRoutes(new CamelEndpointGetPlayers());
			camel.addRoutes(new CamelEndpointAddPlayer());
			camel.addRoutes(new CamelEndpointUpdatePlayer());
			camel.addRoutes(new XML_JSON_Route());
			camel.addRoutes(new WriteInFolderPlayers());
			camel.addRoutes(new FileTransferRoute());
//			camel.addRoutes(new CamelEndpointGetPlayersAdditionalInfo());
//			camel.addRoutes(new XML_JSON_Camel_servelet());
//			camel.addRoutes(new JettyRouteAbandoned());
//			camel.addRoutes(new WriteEveryNSecondsRoute());

			// start is not blocking
			camel.start();

			// so run for 10 seconds
//			Thread.sleep(10_000);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

}
