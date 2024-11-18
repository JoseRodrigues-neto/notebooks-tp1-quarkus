package br.unitins.tp1.notebooks.service;

import br.unitins.tp1.notebooks.dto.PedidoRequestDTO;
import br.unitins.tp1.notebooks.modelo.Pedido;  // Agora retorna Pedido
import java.util.List;

public interface PedidoService {

 
    Pedido create(PedidoRequestDTO pedidoDTO);  

   
    Pedido findById(Long id);

   
    void update(Long id, PedidoRequestDTO pedidoDTO);

     
    void delete(Long id);

  
    List<Pedido> listAll();
}
