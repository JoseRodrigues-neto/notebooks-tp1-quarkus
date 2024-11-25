package br.unitins.tp1.notebooks.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoriaRequestDTO(
    @NotBlank(message = "O nome não pode estar vazio.")  
    @Size(max = 50, message = "O nome deve ter no máximo 50 caracteres.")  
    String nome,

    @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres.")  
    String descricao
) {
}
