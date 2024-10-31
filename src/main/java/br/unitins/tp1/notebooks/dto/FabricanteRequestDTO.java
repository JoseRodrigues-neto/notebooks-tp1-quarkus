package br.unitins.tp1.notebooks.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record FabricanteRequestDTO(

    @NotBlank(message = "O nome do fabricante é obrigatório.")
    @Size(max = 100, message = "O nome do fabricante deve ter no máximo 100 caracteres.")
    String nome,

    @NotBlank(message = "O país de origem é obrigatório.")
    @Size(max = 100, message = "O país de origem deve ter no máximo 100 caracteres.")
    String paisOrigem

) {}
