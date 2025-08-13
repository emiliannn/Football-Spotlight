package com.example.firstapi.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@JacksonXmlRootElement(localName = "contract")

public class Contract {
    @JacksonXmlProperty(localName = "joined")

    @NotEmpty
    Date joined;
    @JacksonXmlProperty(localName = "contractExpires")

    @NotEmpty
    Date contractExpires;
    @JacksonXmlProperty(localName = "contractOption")

    String contractOption;
    @JacksonXmlProperty(isAttribute = true)
    int previousClubID;
    @JacksonXmlProperty(isAttribute = true)

    private int currentClubID;
    @JacksonXmlProperty(isAttribute = true)

    private String id;



//    public Contract(int currentClubID, int id, int previousClubID) {
//
//        this.currentClubID = currentClubID;
//        this.id = id;
//        this.previousClubID = previousClubID;
//    }


    public Date getJoined() {
        return joined;
    }

    public void setJoined(Date joined) {
        this.joined = joined;
    }

    public Date getContractExpires() {
        return contractExpires;
    }

    public void setContractExpires(Date contractExpires) {
        this.contractExpires = contractExpires;
    }

    public String getContractOption() {
        return contractOption;
    }

    public void setContractOption(String contractOption) {
        this.contractOption = contractOption;
    }

    public int getPreviousClubID() {
        return previousClubID;
    }

    public void setPreviousClubID(int previousClubID) {
        this.previousClubID = previousClubID;
    }

    public int getCurrentClubID() {
        return currentClubID;
    }

    public void setCurrentClubID(int currentClubID) {
        this.currentClubID = currentClubID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



}

