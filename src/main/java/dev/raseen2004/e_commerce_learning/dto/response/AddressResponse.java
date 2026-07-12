package dev.raseen2004.e_commerce_learning.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressResponse {

    private Long id;

    private String houseNo;

    private String street;

    private String city;

    private String state;

    private String country;

    private String postalCode;
}