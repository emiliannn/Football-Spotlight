package com.example.firstapi.config;

import com.example.firstapi.model.Player;
import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.apache.camel.model.dataformat.JaxbDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.filter.CorsFilter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;


@Configuration
public class CamelConfig {

//    @Autowired
//    private  CamelContext camelContext;
//
//    @Bean
//    ServletRegistrationBean servletRegistrationBean() {
//        String contextPath = "/ecommapp";
//        ServletRegistrationBean servlet = new ServletRegistrationBean
//                (new CamelHttpTransportServlet(), contextPath+"/*");
//        servlet.setName("CamelServlet");
//        return servlet;
//    }
//
//    @Bean
//    ProducerTemplate producerTemplate() {
//        return camelContext.createProducerTemplate();
//    }
//
//    @Bean
//    ConsumerTemplate consumerTemplate() {
//        return camelContext.createConsumerTemplate();
//    }



    ////////////////////////////////////////////////////

//    @Bean
//    public JaxbDataFormat jaxbDataFormat() throws JAXBException, JAXBException {
//        JAXBContext context = JAXBContext.newInstance(Player.class); // replace 'Player.class' with your classes
//        JaxbDataFormat jaxb = new JaxbDataFormat();
//        jaxb.setContextPath(context.toString());
//        return jaxb;
//    }

    @Bean
    public ServletRegistrationBean camelServletRegistration() {
        ServletRegistrationBean registration = new ServletRegistrationBean(new CamelHttpTransportServlet(), "/api/*");
        registration.setName("CamelServlet");
        return registration;
    }

    @Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        // Replace with your actual origin or pattern
        config.addAllowedOriginPattern("http://localhost:4200");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(0);
        return bean;
    }


}