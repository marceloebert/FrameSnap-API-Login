package com.fiap.framesnap.infrastructure.authentication.controller.mapper;

import com.fiap.framesnap.infrastructure.authentication.controller.dto.LoginResponse;

public class LoginMapper {
    public static LoginResponse toResponse(String token) {
        if (token == null) {
            return null;
        }
        return new LoginResponse(token);
    }
}
