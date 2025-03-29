package com.fiap.framesnap;

import com.fiap.framesnap.crosscutting.security.PasswordEncoder;
import com.fiap.framesnap.crosscutting.exception.NotFoundException;
import com.fiap.framesnap.crosscutting.exception.ValidationErrorResponse;
import com.fiap.framesnap.crosscutting.exception.Violation;
import com.fiap.framesnap.infrastructure.authentication.controller.mapper.UserDTOMapper;
import com.fiap.framesnap.infrastructure.authentication.controller.mapper.LoginMapper;
import com.fiap.framesnap.infrastructure.authentication.controller.dto.UserRequest;
import com.fiap.framesnap.infrastructure.authentication.controller.dto.UserResponse;
import com.fiap.framesnap.infrastructure.authentication.controller.dto.LoginResponse;
import com.fiap.framesnap.entities.authentication.User;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class LanchoneteApplicationTests {

    @Test
    void testUserDTOMapper() {
        UserDTOMapper mapper = new UserDTOMapper();
        
        // Teste de conversão de UserRequest para User
        UserRequest request = new UserRequest("test@example.com", "password123");
        User user = mapper.toUser(request);
        
        assertNull(user.getId());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("password123", user.getPassword());

        // Teste de conversão de User para UserResponse
        UUID userId = UUID.randomUUID();
        User userWithId = new User(userId, "test@example.com", "password123");
        UserResponse response = mapper.toUserResponse(userWithId);
        
        assertEquals(userId, response.getId());
        assertEquals("test@example.com", response.getEmail());
    }

    @Test
    void testLoginMapper() {
        String token = "test-token";
        LoginResponse response = LoginMapper.toResponse(token);
        
        assertNotNull(response);
        assertEquals(token, response.getToken());
    }

    @Test
    void testPasswordEncoder() {
        PasswordEncoder encoder = new PasswordEncoder();
        
        // Teste de codificação
        String password = "testPassword123";
        String encodedPassword = encoder.encode(password);
        
        assertNotNull(encodedPassword);
        assertNotEquals(password, encodedPassword);
        assertTrue(encodedPassword.matches("[a-f0-9]+")); // Verifica se é um hash hexadecimal

        // Teste de verificação
        assertTrue(encoder.matches(password, encodedPassword));
        assertFalse(encoder.matches("wrongPassword", encodedPassword));
    }

    @Test
    void testNotFoundException() {
        String message = "Resource not found";
        NotFoundException exception = new NotFoundException(message);
        
        assertEquals(message, exception.getMessage());
        
        // Teste do construtor padrão
        NotFoundException defaultException = new NotFoundException();
        assertEquals("Not found", defaultException.getMessage());
    }

    @Test
    void testValidationErrorResponse() {
        ValidationErrorResponse response = new ValidationErrorResponse();
        
        // Teste de adição de violação
        response.add(new Violation("test error"));
        assertEquals(1, response.getViolations().size());
        assertEquals("test error", response.getViolations().get(0).getMessage());
        
        // Teste de adição de múltiplas violações
        response.addAll(new Violation[]{
            new Violation("error 1"),
            new Violation("error 2")
        });
        assertEquals(3, response.getViolations().size());
    }
}
