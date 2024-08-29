package com.service.auth;

import com.service.auth.models.User;
import com.service.auth.repositories.UserRepository;
import com.service.auth.services.AuthService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AuthApplicationTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthService authService;

    @Test
    void testRegisterUser() {
        User user = new User("testuser", "password", "testuser@example.com");
        
        when(userRepository.save(any(User.class))).thenReturn(user);
        
        ResponseEntity<?> response = authService.registerUser(user);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testLoginUser() {
        User user = new User("testuser", "password", "testuser@example.com");
        
        when(userRepository.findByUsername("testuser")).thenReturn(java.util.Optional.of(user));
        
        ResponseEntity<?> response = authService.loginUser("testuser", "password");
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
