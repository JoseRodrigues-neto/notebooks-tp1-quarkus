package br.unitins.tp1.notebooks.service;

import br.unitins.tp1.notebooks.dto.ItemPedidoRequestDTO;
import br.unitins.tp1.notebooks.modelo.ItemPedido;

import java.util.List;

public interface ItemPedidoService {

    ItemPedido create(ItemPedidoRequestDTO itemPedidoDTO); // Cria um novo ItemPedido

    ItemPedido findById(Long id); // Busca um ItemPedido pelo ID

    void update(Long id, ItemPedidoRequestDTO itemPedidoDTO); // Atualiza um ItemPedido existente

    void delete(Long id); // Remove um ItemPedido pelo ID

    List<ItemPedido> listAll(); // Lista todos os ItemPedidos
}
