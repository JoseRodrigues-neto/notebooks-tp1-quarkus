package br.unitins.tp1.notebooks.dto;

import br.unitins.tp1.notebooks.modelo.ItemPedido;
import br.unitins.tp1.notebooks.modelo.Notebook;

public record ItemPedidoResponseExpandidoDTO(
     Long id, 
      Notebook notebook,
       int quantidade,
        double preco            
) {
 
    public static ItemPedidoResponseExpandidoDTO valueOf(ItemPedido itemPedido) {
        return new ItemPedidoResponseExpandidoDTO(
                itemPedido.getId(),
                itemPedido.getNotebook(),
                itemPedido.getQuantidade(),
                itemPedido.getPreco()
        );
    }
}
