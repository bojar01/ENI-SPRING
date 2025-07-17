package fr.eni.ludotheque.rest;

import fr.eni.ludotheque.bll.AuthService;
import fr.eni.ludotheque.bo.User;
import fr.eni.ludotheque.dto.AuthRequestDTO;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ApiResponse<String> login(@RequestBody AuthRequestDTO loginRequest) {
        // Authenticate the user and generate a JWT token
        String jwtToken = authService.login(loginRequest.getUsername(), loginRequest.getPassword());
        // Return the token in the response
        return new ApiResponse<>(true, "Login successful", jwtToken);
    }

    @PostMapping("/register")
    private ApiResponse<String> register(@RequestBody AuthRequestDTO authRequest) {
        User user = authService.register(authRequest.getUsername(), authRequest.getPassword());

        return new ApiResponse<>(true, "User registered successfully", user.getUsername());
    }
}
