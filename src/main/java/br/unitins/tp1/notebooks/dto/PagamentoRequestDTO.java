package br.unitins.tp1.notebooks.dto;

import jakarta.validation.constraints.NotNull;

public record PagamentoRequestDTO(

@NotNull(message = "O ID do pedido não pode ser nulo.")
Long pedidoId
) {}
