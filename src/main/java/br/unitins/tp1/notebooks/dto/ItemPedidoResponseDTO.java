package br.unitins.tp1.notebooks.dto;

import br.unitins.tp1.notebooks.modelo.ItemPedido;
 

public record ItemPedidoResponseDTO(
      String notebook,
       int quantidade,
        double preco            

) {
 
    public static ItemPedidoResponseDTO valueOf(ItemPedido itemPedido) {
        return new ItemPedidoResponseDTO(
                itemPedido.getNotebook().getModelo(),
                itemPedido.getQuantidade(),
                itemPedido.getPreco()
        );
    }
}
