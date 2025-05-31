package com.example.task.services;

import com.example.task.dtos.AuthRequest;
import com.example.task.dtos.AuthResponse;
import com.example.task.dtos.RegisterRequest;
import com.example.task.models.JwtToken;
import com.example.task.models.User;
import com.example.task.repositories.JwtTokenRepository;
import com.example.task.repositories.UserRepository;
import com.example.task.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtUtil tokenProvider;

    private final JwtTokenRepository jwtTokenRepository;


    public AuthResponse login(AuthRequest loginRequest) {


        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String jwt = tokenProvider.generateToken(authentication);
        JwtToken jwtToken = new JwtToken();
        jwtToken.setToken(jwt);
        jwtToken.setUser(user);
        jwtTokenRepository.save(jwtToken);

        user.setJwtToken(jwtToken);
        userRepository.save(user);



        return new AuthResponse(jwt, user.getUsername(), user.getName());
    }

    public ResponseEntity<Object> register(RegisterRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new RuntimeException("Username is already taken!");
        }



        User newUser = new User(); // Uses the @NoArgsConstructor
        newUser.setUsername(signUpRequest.getUsername());
        newUser.setPassword(passwordEncoder.encode(signUpRequest.getPassword())); // IMPORTANT: Encode password!
        newUser.setName(signUpRequest.getName());

        userRepository.save(newUser);

        return ResponseEntity.ok().build();
    }
}
