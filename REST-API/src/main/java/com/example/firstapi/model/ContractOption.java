package com.example.firstapi.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "contract")

public class ContractOption {
    @JacksonXmlProperty(localName = "contractOption")
    private String contractOption;

    public String getContractOption() {
        return contractOption;
    }

    public void setContractOption(String contractOption) {
        this.contractOption = contractOption;
    }
}
