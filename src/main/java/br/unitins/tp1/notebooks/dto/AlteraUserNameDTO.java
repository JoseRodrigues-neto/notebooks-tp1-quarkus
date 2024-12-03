package br.unitins.tp1.notebooks.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AlteraUserNameDTO(
   @NotBlank(message = "A senha não pode estar vazia.")
   @Size(min = 3, message = "A senha deve ter no mínimo 3 caracteres.")
   String  senha,
    @NotBlank(message = "O username não pode estar vazio.")
    @Size(min = 3, max = 15, message = "O username deve ter entre 3 e 15 caracteres.")
   String novoUserName
) {
    
}
