package br.unitins.tp1.notebooks.dto;

import br.unitins.tp1.notebooks.modelo.ItemPedido;
import br.unitins.tp1.notebooks.modelo.Notebook;

public record ItemPedidoResponseDTO(
     Long id, 
      Notebook notebook,
       int quantidade,
        double preco            

) {
 
    public static ItemPedidoResponseDTO valueOf(ItemPedido itemPedido) {
        return new ItemPedidoResponseDTO(
                itemPedido.getId(),
                itemPedido.getNotebook(),
                itemPedido.getQuantidade(),
                itemPedido.getPreco()
        );
    }
}
