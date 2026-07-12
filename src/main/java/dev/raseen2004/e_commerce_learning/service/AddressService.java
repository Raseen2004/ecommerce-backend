package dev.raseen2004.e_commerce_learning.service;

import java.util.List;

import dev.raseen2004.e_commerce_learning.dto.request.AddressRequest;
import dev.raseen2004.e_commerce_learning.dto.response.AddressResponse;

public interface AddressService {
    AddressResponse addAddress(AddressRequest request);

    AddressResponse getAddressById(Long id);

    List<AddressResponse> getAddressesByCustomerId(Long customerId);

    AddressResponse updateAddress(Long id, AddressRequest request);

    void deleteAdress(Long id);
}
