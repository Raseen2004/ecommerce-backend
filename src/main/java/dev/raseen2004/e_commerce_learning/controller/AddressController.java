package dev.raseen2004.e_commerce_learning.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.raseen2004.e_commerce_learning.dto.request.AddressRequest;
import dev.raseen2004.e_commerce_learning.dto.response.AddressResponse;
import dev.raseen2004.e_commerce_learning.service.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;

    @PostMapping("/customers/{customerId}/addresses")
    public ResponseEntity<AddressResponse> addAddress(
        @PathVariable Long customerId, 
        @Valid @RequestBody AddressRequest request
    ) {
        AddressResponse response = addressService.addAddress(customerId, request);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(response);
    }

    @GetMapping("/addresses/{id}")
    public ResponseEntity<AddressResponse> getAddressById(
            @PathVariable Long id) { 
        return ResponseEntity.ok(
            addressService.getAddressById(id)
        );
    }

    @GetMapping("/customers/{customerId}/addresses")
    public ResponseEntity<List<AddressResponse>> getAddressesByCustomerId(
        @PathVariable Long customerId
    ) {
        return ResponseEntity.ok(
            addressService.getAddressesByCustomerId(customerId)
        );
    }

    @PutMapping("/addresses/{id}")
    public ResponseEntity<AddressResponse> updateAddress(
        @PathVariable Long id,
        @Valid @RequestBody AddressRequest request
    ) {
        return ResponseEntity.ok(
            addressService.updateAddress(id, request)
        );
    }

    @DeleteMapping("/addresses/{id}")
    public ResponseEntity<Void> deleteAddress(
            @PathVariable Long id) {

        addressService.deleteAddress(id);

        return ResponseEntity.noContent().build();
    }
}
