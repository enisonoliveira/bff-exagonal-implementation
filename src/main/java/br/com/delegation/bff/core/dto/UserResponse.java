package br.com.delegation.bff.core.dto;

public class UserResponse{
    private String name;
    private String email;
    
    // Construtores, Getters e Setters
    public UserResponse(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
