package br.unitins.tp1.notebooks.service;

import br.unitins.tp1.notebooks.modelo.FormaPagamento;
import br.unitins.tp1.notebooks.modelo.Pedido;
import jakarta.validation.Valid;

public interface PagamentoService {
    
     String processarPagamento(Pedido pedido,@Valid  FormaPagamento formaPagamento);
}
