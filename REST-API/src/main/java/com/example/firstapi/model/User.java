package com.example.firstapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;


@JacksonXmlRootElement(localName = "user")
@JsonPropertyOrder({ "id", "fullName" })
public class User {
    @JacksonXmlProperty(isAttribute = true)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    @JsonProperty("fullName")
    @JacksonXmlProperty(localName = "fullName")
    private String fullName;


    @JsonProperty("mode")
    @JacksonXmlProperty(localName = "mode")
    private String mode;
}




