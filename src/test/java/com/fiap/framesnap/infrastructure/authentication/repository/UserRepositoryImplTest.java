package com.fiap.framesnap.infrastructure.authentication.repository;

import com.fiap.framesnap.entities.authentication.User;
import com.fiap.framesnap.infrastructure.authentication.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserRepositoryImplTest {

    @Mock
    private JpaUserRepository jpaUserRepository;

    private UserRepositoryImpl userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userRepository = new UserRepositoryImpl(jpaUserRepository);
    }

    @Test
    void shouldRegisterUser() {
        // Arrange
        UUID userId = UUID.randomUUID();
        String email = "test@example.com";
        String password = "password123";
        
        User user = new User(null, email, password);
        UserEntity userEntity = new UserEntity(userId, email, password);
        
        when(jpaUserRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        // Act
        User result = userRepository.register(user);

        // Assert
        assertNotNull(result);
        assertEquals(userId, result.getId());
        assertEquals(email, result.getEmail());
        assertEquals(password, result.getPassword());
        verify(jpaUserRepository).save(any(UserEntity.class));
    }

    @Test
    void shouldFindUserByEmail() {
        // Arrange
        UUID userId = UUID.randomUUID();
        String email = "test@example.com";
        String password = "password123";
        
        UserEntity userEntity = new UserEntity(userId, email, password);
        when(jpaUserRepository.findByEmail(email)).thenReturn(Optional.of(userEntity));

        // Act
        Optional<User> result = userRepository.findByEmail(email);

        // Assert
        assertTrue(result.isPresent());
        User user = result.get();
        assertEquals(userId, user.getId());
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
        verify(jpaUserRepository).findByEmail(email);
    }

    @Test
    void shouldReturnEmptyWhenUserNotFound() {
        // Arrange
        String email = "nonexistent@example.com";
        when(jpaUserRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Act
        Optional<User> result = userRepository.findByEmail(email);

        // Assert
        assertFalse(result.isPresent());
        verify(jpaUserRepository).findByEmail(email);
    }

    @Test
    void shouldReturnEmptyStringForLogin() {
        // Act
        String result = userRepository.login("test@example.com", "password123");

        // Assert
        assertEquals("", result);
    }
} 