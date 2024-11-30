package br.unitins.tp1.notebooks.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AlteraSenhaDTO(
    
    @NotBlank(message = "A senha não pode estar vazia.")
    @NotBlank String userName,

    @NotBlank(message = "A senha não pode estar vazia.")
    @NotBlank String senhaAntiga,
     @NotBlank(message = "A senha não pode estar vazia.")
    @Size(min = 4, message = "A senha deve ter no mínimo 4 caracteres.")
    @NotBlank String senhaNova
) {}
