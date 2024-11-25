package br.unitins.tp1.notebooks.service;

import br.unitins.tp1.notebooks.modelo.FormaPagamento;
import br.unitins.tp1.notebooks.modelo.Pedido;

public interface PagamentoService {
    
     String processarPagamento(Pedido pedido, FormaPagamento formaPagamento);
}
