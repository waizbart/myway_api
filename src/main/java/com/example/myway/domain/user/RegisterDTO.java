package com.example.myway.domain.user;

public record RegisterDTO(String login, String password, UserRole role, String name, Integer age) {
}
