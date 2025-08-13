package com.example.firstapi.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.ArrayList;
import java.util.List;

@JacksonXmlRootElement(localName = "root")

public class FootballClubs {
    @JacksonXmlElementWrapper(localName = "footballClubs")
    @JacksonXmlProperty(localName = "footballClubs")
    private List<FootballClub> footballClubs = new ArrayList<>();

    public List<FootballClub> getFootballClubs() {
        return footballClubs;
    }

    public void setFootballClubs(List<FootballClub> footballClubs) {
        this.footballClubs = footballClubs;
    }
}
