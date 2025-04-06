package com.fiap.framesnap.infrastructure.authentication.controller.mapper;

import com.fiap.framesnap.entities.authentication.User;
import com.fiap.framesnap.infrastructure.authentication.controller.dto.UserRequest;
import com.fiap.framesnap.infrastructure.authentication.controller.dto.UserResponse;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserDTOMapperTest {

    private final UserDTOMapper mapper = new UserDTOMapper();

    @Test
    void shouldMapUserRequestToUser() {
        // Arrange
        String email = "test@example.com";
        String password = "password123";
        UserRequest request = new UserRequest(email, password);

        // Act
        User user = mapper.toUser(request);

        // Assert
        assertNotNull(user);
        assertNull(user.getId());
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
    }

    @Test
    void shouldMapUserToUserResponse() {
        // Arrange
        UUID id = UUID.randomUUID();
        String email = "test@example.com";
        String password = "password123";
        User user = new User(id, email, password);

        // Act
        UserResponse response = mapper.toUserResponse(user);

        // Assert
        assertNotNull(response);
        assertEquals(id, response.getId());
        assertEquals(email, response.getEmail());
    }
} 