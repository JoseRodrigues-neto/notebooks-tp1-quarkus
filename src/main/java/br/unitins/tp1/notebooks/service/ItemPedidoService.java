package br.unitins.tp1.notebooks.service;

import br.unitins.tp1.notebooks.dto.ItemPedidoRequestDTO;
import br.unitins.tp1.notebooks.modelo.ItemPedido;
import jakarta.validation.Valid;

import java.util.List;

public interface ItemPedidoService {

    ItemPedido create(@Valid ItemPedidoRequestDTO itemPedidoDTO);  

    ItemPedido findById(Long id); 

    void update(Long id,@Valid  ItemPedidoRequestDTO itemPedidoDTO);  

    void delete(Long id);  

    List<ItemPedido> listAll();  
}
