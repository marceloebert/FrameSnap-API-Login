package com.fiap.framesnap.crosscutting.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class RestClientTest {

    @Mock
    private RestTemplate restTemplate;

    private RestClient restClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        restClient = new RestClient(restTemplate);
    }

    @Test
    void shouldMakePostRequest() {
        // Arrange
        String url = "http://test.com/api";
        Object request = new Object();
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer token");
        
        ResponseEntity<String> expectedResponse = new ResponseEntity<>("success", HttpStatus.OK);
        when(restTemplate.exchange(
            eq(url),
            eq(HttpMethod.POST),
            any(HttpEntity.class),
            eq(String.class)
        )).thenReturn(expectedResponse);

        // Act
        ResponseEntity<String> response = restClient.post(url, request, String.class, headers);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("success", response.getBody());
        verify(restTemplate).exchange(
            eq(url),
            eq(HttpMethod.POST),
            any(HttpEntity.class),
            eq(String.class)
        );
    }

    @Test
    void shouldMakeGetRequest() {
        // Arrange
        String url = "http://test.com/api";
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer token");
        
        ResponseEntity<String> expectedResponse = new ResponseEntity<>("success", HttpStatus.OK);
        when(restTemplate.exchange(
            eq(url),
            eq(HttpMethod.GET),
            any(HttpEntity.class),
            eq(String.class)
        )).thenReturn(expectedResponse);

        // Act
        ResponseEntity<String> response = restClient.get(url, String.class, headers);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("success", response.getBody());
        verify(restTemplate).exchange(
            eq(url),
            eq(HttpMethod.GET),
            any(HttpEntity.class),
            eq(String.class)
        );
    }

    @Test
    void shouldMakePutRequest() {
        // Arrange
        String url = "http://test.com/api";
        Object request = new Object();
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer token");
        
        ResponseEntity<String> expectedResponse = new ResponseEntity<>("success", HttpStatus.OK);
        when(restTemplate.exchange(
            eq(url),
            eq(HttpMethod.PUT),
            any(HttpEntity.class),
            eq(String.class)
        )).thenReturn(expectedResponse);

        // Act
        ResponseEntity<String> response = restClient.put(url, request, String.class, headers);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("success", response.getBody());
        verify(restTemplate).exchange(
            eq(url),
            eq(HttpMethod.PUT),
            any(HttpEntity.class),
            eq(String.class)
        );
    }

    @Test
    void shouldMakeDeleteRequest() {
        // Arrange
        String url = "http://test.com/api";
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer token");
        
        ResponseEntity<Void> expectedResponse = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        when(restTemplate.exchange(
            eq(url),
            eq(HttpMethod.DELETE),
            any(HttpEntity.class),
            eq(Void.class)
        )).thenReturn(expectedResponse);

        // Act
        ResponseEntity<Void> response = restClient.delete(url, headers);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(restTemplate).exchange(
            eq(url),
            eq(HttpMethod.DELETE),
            any(HttpEntity.class),
            eq(Void.class)
        );
    }

    @Test
    void shouldHandleNullHeaders() {
        // Arrange
        String url = "http://test.com/api";
        Object request = new Object();
        
        ResponseEntity<String> expectedResponse = new ResponseEntity<>("success", HttpStatus.OK);
        when(restTemplate.exchange(
            eq(url),
            eq(HttpMethod.POST),
            any(HttpEntity.class),
            eq(String.class)
        )).thenReturn(expectedResponse);

        // Act
        ResponseEntity<String> response = restClient.post(url, request, String.class, null);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("success", response.getBody());
        verify(restTemplate).exchange(
            eq(url),
            eq(HttpMethod.POST),
            any(HttpEntity.class),
            eq(String.class)
        );
    }

    @Test
    void shouldHandleEmptyHeaders() {
        // Arrange
        String url = "http://test.com/api";
        Object request = new Object();
        Map<String, String> headers = new HashMap<>();
        
        ResponseEntity<String> expectedResponse = new ResponseEntity<>("success", HttpStatus.OK);
        when(restTemplate.exchange(
            eq(url),
            eq(HttpMethod.POST),
            any(HttpEntity.class),
            eq(String.class)
        )).thenReturn(expectedResponse);

        // Act
        ResponseEntity<String> response = restClient.post(url, request, String.class, headers);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("success", response.getBody());
        verify(restTemplate).exchange(
            eq(url),
            eq(HttpMethod.POST),
            any(HttpEntity.class),
            eq(String.class)
        );
    }

    @Test
    void shouldHandleNullRequest() {
        // Arrange
        String url = "http://test.com/api";
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer token");
        
        ResponseEntity<String> expectedResponse = new ResponseEntity<>("success", HttpStatus.OK);
        when(restTemplate.exchange(
            eq(url),
            eq(HttpMethod.POST),
            any(HttpEntity.class),
            eq(String.class)
        )).thenReturn(expectedResponse);

        // Act
        ResponseEntity<String> response = restClient.post(url, null, String.class, headers);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("success", response.getBody());
        verify(restTemplate).exchange(
            eq(url),
            eq(HttpMethod.POST),
            any(HttpEntity.class),
            eq(String.class)
        );
    }
} 