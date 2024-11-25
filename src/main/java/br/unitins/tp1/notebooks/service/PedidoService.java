package br.unitins.tp1.notebooks.service;

import br.unitins.tp1.notebooks.dto.PedidoRequestDTO;
import br.unitins.tp1.notebooks.dto.PedidoResponseDTO;
import br.unitins.tp1.notebooks.modelo.Pedido;  // Agora retorna Pedido
import br.unitins.tp1.notebooks.modelo.StatusPedido;

import java.util.List;

public interface PedidoService {

    Pedido create(PedidoRequestDTO pedidoDTO);  
    Pedido findById(Long id);
    void update(Long id, PedidoRequestDTO pedidoDTO);
    void delete(Long id);
    List<Pedido> listAll();
    Pedido alterarStatusPedido(Long pedidoId, Long statusId);

    List<PedidoResponseDTO> findMyPedidos();
    List<PedidoResponseDTO> findByCliente(Long idCliente);
}
