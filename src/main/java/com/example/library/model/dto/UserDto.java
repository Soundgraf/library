package com.example.library.model.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link com.example.library.model.entity.User} entity
 */
@Data
@Builder
public class UserDto implements Serializable {
    private final Long id;
    private final String username;
    private final String password;
    private final boolean active;
}