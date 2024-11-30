package br.unitins.tp1.notebooks.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record FuncionarioRequestDTO(

    @NotBlank(message = "O username não pode estar vazio.")
    @Size(max = 15, message = "O username deve ter no máximo 15 caracteres.")
    String username,

    @NotBlank(message = "O nome não pode estar vazio.")
    @Size(max = 50, message = "O nome deve ter no máximo 50 caracteres.")
    String nome,

    @NotBlank(message = "O email não pode estar vazio.")
    String email,

    @NotBlank(message = "A senha não pode estar vazia.")
    @Size(min = 4, message = "A senha deve ter no mínimo 4 caracteres.")
    String senha,

    @NotBlank(message = "A matrícula não pode estar vazia.")
    @Pattern(regexp = "\\d+", message = "A matrícula deve conter apenas números.")
    @Size(max = 20, message = "A matrícula deve ter no máximo 20 caracteres.")
    String matricula,

    @NotBlank(message = "O cargo não pode estar vazio.")
    @Size(max = 50, message = "O cargo deve ter no máximo 50 caracteres.")
    String cargo
) {}
