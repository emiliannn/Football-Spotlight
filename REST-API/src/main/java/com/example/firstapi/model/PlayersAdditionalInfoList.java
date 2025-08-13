package com.example.firstapi.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.ArrayList;
import java.util.List;


@JacksonXmlRootElement(localName = "root")

public class PlayersAdditionalInfoList {
    @JacksonXmlElementWrapper(localName = "players")
    @JacksonXmlProperty(localName = "players")
    private List<PlayerAdditionalInfo> players = new ArrayList<>();

    public List<PlayerAdditionalInfo> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerAdditionalInfo> players) {
        this.players = players;
    }
}