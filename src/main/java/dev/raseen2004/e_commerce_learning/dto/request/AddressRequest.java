package dev.raseen2004.e_commerce_learning.dto.request;

import lombok.Data;

@Data
public class AddressRequest {

    private String houseNo;

    private String street;

    private String city;

    private String state;

    private String country;

    private String postalCode;
}