package com.example.myway.domain.user;

public record UserResponseDTO(Long id, String name, String email, Integer age) {
    public UserResponseDTO(User user){
        this(user.getId(), user.getName(), user.getEmail(), user.getAge());
    }
}
