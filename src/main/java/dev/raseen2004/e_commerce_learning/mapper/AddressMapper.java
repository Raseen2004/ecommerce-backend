package dev.raseen2004.e_commerce_learning.mapper;

import dev.raseen2004.e_commerce_learning.dto.request.AddressRequest;
import dev.raseen2004.e_commerce_learning.dto.response.AddressResponse;
import dev.raseen2004.e_commerce_learning.entity.Address;

public class AddressMapper {
    public static Address toEntity(AddressRequest request) {
        return Address.builder()
            .houseNo(request.getHouseNo())
            .street(request.getStreet())
            .city(request.getCity())
            .state(request.getState())
            .country(request.getCountry())
            .postalCode(request.getPostalCode())
            .build();
    }

    public static AddressResponse tnResponse(Address address) {
        return AddressResponse.builder()
            .id(address.getId())
            .houseNo(address.getHouseNo())
            .street(address.getStreet())
            .city(address.getCity())
            .state(address.getState())
            .country(address.getCountry())
            .postalCode(address.getPostalCode())
            .build();

    }
}
