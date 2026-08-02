package dev.raseen2004.e_commerce_learning.service;

import dev.raseen2004.e_commerce_learning.dto.auth.AuthResponse;
import dev.raseen2004.e_commerce_learning.dto.auth.LoginRequest;
import dev.raseen2004.e_commerce_learning.dto.auth.RegisterRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}
