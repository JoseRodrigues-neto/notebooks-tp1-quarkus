package br.unitins.tp1.notebooks.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ItemPedidoRequestDTO(

    @NotNull(message = "O ID do notebook não pode estar vazio.")
    Long notebookId,

    @Min(value = 1, message = "A quantidade deve ser no mínimo 1.")
    int quantidade
) {}
