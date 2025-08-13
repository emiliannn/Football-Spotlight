package com.example.firstapi.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import javax.validation.constraints.NotEmpty;
import java.util.Date;



import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "footballClub")
@JsonPropertyOrder({ "id", "name", "squadSize", "averageAge", "stadium", "manager" })
public class FootballClub {
    @JacksonXmlProperty(isAttribute = true)
    private int id;

    @JsonProperty("name")
    @JacksonXmlProperty(localName = "name")
    private String name;

    @JsonProperty("squadSize")
    @JacksonXmlProperty(localName = "squadSize")
    private int squadSize;

    @JsonProperty("averageAge")
    @JacksonXmlProperty(localName = "averageAge")
    private double averageAge;

    @JsonProperty("stadium")
    @JacksonXmlProperty(localName = "stadium")
    private String stadium;

    @JsonProperty("manager")
    @JacksonXmlProperty(localName = "manager")
    private String manager;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSquadSize() {
        return squadSize;
    }

    public void setSquadSize(int squadSize) {
        this.squadSize = squadSize;
    }

    public double getAverageAge() {
        return averageAge;
    }

    public void setAverageAge(double averageAge) {
        this.averageAge = averageAge;
    }

    public String getStadium() {
        return stadium;
    }

    public void setStadium(String stadium) {
        this.stadium = stadium;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    @JsonProperty("managerID")
    @JacksonXmlProperty(isAttribute = true, localName = "managerID")
    private int managerID;

    public int getManagerID() {
        return managerID;
    }

     public void setManagerID(int managerID) {
         this.managerID = managerID;
     }
}
