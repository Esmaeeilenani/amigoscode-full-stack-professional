package com.amigoscode.auth;

import com.amigoscode.customer.Customer;
import com.amigoscode.customer.CustomerDTO;
import com.amigoscode.customer.CustomerMapper;
import com.amigoscode.jwt.JWTUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;

    private final JWTUtil jwtUtil;

    public AuthService(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    public AuthResponse authenticate(String username, String password) {


        try {
            Authentication authenticate = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
            Customer customer = (Customer) authenticate.getPrincipal();

            CustomerDTO customerDTO = CustomerMapper.toDto(customer);
            String token = jwtUtil.issueToken(customerDTO.getEmail(), "ROLE_USER");

            return new AuthResponse(token);

        } catch (BadCredentialsException exception) {
            throw new InsufficientAuthenticationException(exception.getMessage());
        }
    }
}
