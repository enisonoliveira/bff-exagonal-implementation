package br.com.delegation.bff.core.dto;

import org.springframework.stereotype.Component;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Component
public class UserRequest {

    @NotNull(message = "O nome não pode ser nulo")
    @Size(min = 2, message = "O nome deve ter pelo menos 2 caracteres")
    private String name;

    @NotNull(message = "O email não pode ser nulo")
    @Email(message = "O email deve ser válido")
    private String email;

    // Getters e Setters

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
