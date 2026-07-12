package dev.raseen2004.e_commerce_learning.dto.request;

import lombok.Data;

@Data
public class CustomerRequest {
    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private String password;
}
