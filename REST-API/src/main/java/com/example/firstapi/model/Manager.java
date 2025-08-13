package com.example.firstapi.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.Date;

@JacksonXmlRootElement(localName = "manager")
@JsonPropertyOrder({ "id", "fullName" })
public class Manager {
    @JacksonXmlProperty(isAttribute = true)

    private int contractID;

    public int getContractID() {
        return contractID;
    }

    public void setContractID(int contractID) {
        this.contractID = contractID;
    }

    @JacksonXmlProperty(isAttribute = true)
    private int id;

    @JsonProperty("fullName")
    @JacksonXmlProperty(localName = "fullName")
    private String fullName;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @JsonProperty("dateOfBirth")
    @JacksonXmlProperty(localName = "dateOfBirth")
    Date dateOfBirth;

    @JsonProperty("age")
    @JacksonXmlProperty(localName = "age")
     int age;


    @JsonProperty("nationality")
    @JacksonXmlProperty(localName = "nationality")
    String nationality;


    @JsonProperty("role")
    @JacksonXmlProperty(localName = "role")
    String role;




    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
