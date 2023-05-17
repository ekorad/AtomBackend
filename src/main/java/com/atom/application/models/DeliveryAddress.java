package com.atom.application.models;

import javax.persistence.Embeddable;

@Embeddable
public class DeliveryAddress {
    
    private String code;
    private String address;


    
    public DeliveryAddress(String code, String address) {
        this.code = code;
        this.address = address;
    }

    public DeliveryAddress() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


}
