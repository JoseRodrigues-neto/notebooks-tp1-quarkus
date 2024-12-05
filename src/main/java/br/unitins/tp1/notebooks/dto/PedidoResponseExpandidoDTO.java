package br.unitins.tp1.notebooks.dto;

import br.unitins.tp1.notebooks.modelo.Pedido;
import br.unitins.tp1.notebooks.modelo.StatusPedido;
import java.time.LocalDate;
import java.util.List;

public record PedidoResponseExpandidoDTO(
        Long id,  
        StatusPedido status,                    
       ClienteResponseDTO cliente,      
       List<ItemPedidoResponseExpandidoDTO> itens,  
       String tipoPagamento,
        LocalDate dataPedido,              
        Double valorTotal
           
) {
 
    public static PedidoResponseExpandidoDTO valueOf(Pedido pedido) {
        return new PedidoResponseExpandidoDTO(
                pedido.getId(),
                pedido.getStatus(),
               ClienteResponseDTO.valueOf(pedido.getCliente()),  
                pedido.getItens().stream()
                        .map(ItemPedidoResponseExpandidoDTO::valueOf)      
                        .toList(),
                pedido.getTipoPagamento(),
                pedido.getDataPedido(),
                pedido.getValorTotal()
        );
    }
}
