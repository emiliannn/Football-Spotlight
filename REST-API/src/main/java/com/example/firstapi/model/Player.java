package com.example.firstapi.model;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import javax.validation.constraints.NotEmpty;

import java.util.Date;

@JacksonXmlRootElement(localName = "player")
public class Player {

    @JacksonXmlProperty(localName = "fullName")
    @NotEmpty
    String fullName;
    @JacksonXmlProperty(localName = "dateOfBirth")

    Date dateOfBirth;
    @JacksonXmlProperty(localName = "age")

    int age;
    @JacksonXmlProperty(localName = "height")

    Float height;
    @JacksonXmlProperty(localName = "citizenship")

    String citizenship;
    @JacksonXmlProperty(localName = "position")

    String position;
    @JacksonXmlProperty(localName = "foot")

    String foot;
    @JacksonXmlProperty(localName = "outfitter")

    String outfitter;

    @JacksonXmlProperty(isAttribute = true)
    String contractID;
    @JacksonXmlProperty(isAttribute = true)
    int id;

//    public Player(int contractID, int id){
//        this.contractID = contractID;
//        this.id = id;
//    }


    public String getContractID() {
        return contractID;
    }

    public void setContractID(String contractID) {
        this.contractID = contractID;
    }

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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getFoot() {
        return foot;
    }

    public void setFoot(String foot) {
        this.foot = foot;
    }

    public String getOutfitter() {
        return outfitter;
    }

    public void setOutfitter(String outfitter) {
        this.outfitter = outfitter;
    }




}