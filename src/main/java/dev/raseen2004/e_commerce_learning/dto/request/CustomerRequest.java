package dev.raseen2004.e_commerce_learning.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerRequest {
    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email")
    private String email;

    @NotBlank(message = "Phone number is required")
    private String phone;
    
    @NotBlank(message = "Password is required")
    @Size(
        min = 8,
        max = 14,
        message = "Passsword must be between 8 and 14 characters"
    )
    private String password;
}
