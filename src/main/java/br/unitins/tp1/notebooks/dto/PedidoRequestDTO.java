package br.unitins.tp1.notebooks.dto;

import java.util.List;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PedidoRequestDTO(
    
    @NotNull(message = "O ID do cliente não pode ser nulo.")
    Long clienteId,

    @NotNull(message = "A lista de itens não pode ser nula.")
    @Size(min = 1, message = "O pedido deve conter pelo menos um item.")
    List<ItemPedidoRequestDTO> itens
    
) {}
