package br.unitins.tp1.notebooks.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioBasicoRequestDTO(

    @NotBlank(message = "O nome não pode estar vazio.")
    @Size(max = 50, message = "O nome deve ter no máximo 100 caracteres.")
    String nome,

    @NotBlank(message = "O e-mail não pode ser vazio.")
    @Email(message = "O e-mail deve ser válido.")
    String email
) {}
