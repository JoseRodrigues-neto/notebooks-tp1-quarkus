package br.unitins.tp1.notebooks.service;

import br.unitins.tp1.notebooks.dto.PedidoRequestDTO;
import br.unitins.tp1.notebooks.dto.PedidoResponseDTO;
import br.unitins.tp1.notebooks.modelo.Pedido;  
import jakarta.validation.Valid;

import java.util.List;

public interface PedidoService {

    Pedido create(@Valid PedidoRequestDTO pedidoDTO);  
    Pedido findById(Long id);
    Pedido findByPedido(Long id);
    void delete(Long id);
    List<Pedido> listAll();
    Pedido alterarStatusPedido(Long pedidoId, Long statusId);
    List<PedidoResponseDTO> findMyPedidos();
    List<PedidoResponseDTO> findByCliente(Long idCliente);
    Pedido cancelarPedido(Long pedidoId);

}
