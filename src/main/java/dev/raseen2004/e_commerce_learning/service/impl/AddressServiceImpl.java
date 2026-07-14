package dev.raseen2004.e_commerce_learning.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.raseen2004.e_commerce_learning.dto.request.AddressRequest;
import dev.raseen2004.e_commerce_learning.dto.response.AddressResponse;
import dev.raseen2004.e_commerce_learning.entity.Address;
import dev.raseen2004.e_commerce_learning.entity.Customer;
import dev.raseen2004.e_commerce_learning.exception.ResourceNotFoundException;
import dev.raseen2004.e_commerce_learning.mapper.AddressMapper;
import dev.raseen2004.e_commerce_learning.repository.AddressRepository;
import dev.raseen2004.e_commerce_learning.repository.CustomerRepository;
import dev.raseen2004.e_commerce_learning.service.AddressService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final CustomerRepository customerRepository;

    @Override
    public AddressResponse addAddress(Long customerId, AddressRequest request) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer not found with id: " + customerId));
        Address address = AddressMapper.toEntity(request);

        address.setCustomer(customer);

        Address savedAddress = addressRepository.save(address);

        return AddressMapper.toResponse(savedAddress);
    }

    @Override
    @Transactional(readOnly = true)
    public AddressResponse getAddressById(Long id) {
        Address address = findAddress(id);

        return AddressMapper.toResponse(address);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AddressResponse> getAddressesByCustomerId(
            Long customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new ResourceNotFoundException(
                    "Customer not found with id: " + customerId);
        }

        return addressRepository.findByCustomerId(customerId)
                .stream()
                .map(AddressMapper::toResponse)
                .toList();
    }

    @Override
    public AddressResponse updateAddress(
            Long id,
            AddressRequest request) {
        Address address = findAddress(id);

        address.setHouseNo(request.getHouseNo());
        address.setStreet(request.getStreet());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setCountry(request.getCountry());
        address.setPostalCode(request.getPostalCode());

        Address savedAddress = addressRepository.save(address);

        return AddressMapper.toResponse(savedAddress);
    }

    @Override
    public void deleteAddress(Long id) {

        Address address = findAddress(id);

        addressRepository.delete(address);
    }

    private Address findAddress(Long id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with id:" + id));
    }
}
