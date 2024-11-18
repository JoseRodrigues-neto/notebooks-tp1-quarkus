package br.unitins.tp1.notebooks.dto;

import java.util.List;

public record PedidoRequestDTO(
        Long clienteId,               
        List<ItemPedidoRequestDTO> itens 
) {}
