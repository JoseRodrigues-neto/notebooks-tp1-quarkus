package br.unitins.tp1.notebooks.dto;

 
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record ClienteRequestDTO(
    @NotBlank(message = "O CPF não pode estar vazio.")
    @Pattern(regexp = "\\d{11}", message = "O CPF deve conter exatamente 11 dígitos.")
    String cpf,

    @NotBlank(message = "O username não pode estar vazio.")
    @Size(min = 3, max = 15, message = "O username deve ter entre 3 e 15 caracteres.")
    String username,

    @NotBlank(message = "O nome não pode estar vazio.")
    @Size(max = 50, message = "O nome deve ter no máximo 100 caracteres.")
    String nome,

    @NotBlank(message = "O email não pode estar vazio.")
    String email,

    @NotBlank(message = "A senha não pode estar vazia.")
    @Size(min = 4, message = "A senha deve ter no mínimo 4 caracteres.")
    String senha,

    @NotBlank(message = "O telefone não pode estar vazio.")
    String telefone,

    @NotBlank(message = "O endereço não pode estar vazio.")
    @Size(max = 250, message = "O endereço deve ter no máximo 250 caracteres.")
    String endereco,

    @Past(message = "A data de nascimento deve ser uma data passada.")
    LocalDate dataNascimento
) {}
 